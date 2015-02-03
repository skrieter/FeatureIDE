/* FeatureIDE - A Framework for Feature-Oriented Software Development
 * Copyright (C) 2005-2013  FeatureIDE team, University of Magdeburg, Germany
 *
 * This file is part of FeatureIDE.
 * 
 * FeatureIDE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * FeatureIDE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with FeatureIDE.  If not, see <http://www.gnu.org/licenses/>.
 *
 * See http://www.fosd.de/featureide/ for further information.
 */
package de.ovgu.featureide.fm.core.conversion;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.prop4j.And;
import org.prop4j.NormalForms;
import org.prop4j.Implies;
import org.prop4j.Literal;
import org.prop4j.Node;
import org.prop4j.Not;
import org.prop4j.Or;

import de.ovgu.featureide.fm.core.Constraint;
import de.ovgu.featureide.fm.core.ConstraintAttribute;
import de.ovgu.featureide.fm.core.Feature;
import de.ovgu.featureide.fm.core.FeatureModel;
import de.ovgu.featureide.fm.core.FeatureModelAnalyzer;
import de.ovgu.featureide.fm.core.editing.NodeCreator;

/**
 * A class to convert feature models with arbitrary cross-tree constraints
 * into models with only simple requires and excludes constraints.
 * 
 * A constraint is simple if it has the form "f => g" (requires) or "f => not g"
 * (excludes) where f and g are features.
 * 
 * The resulting model is a refactoring of the input model and as such describes
 * the same set of product variants. Generally though, it does not describe the 
 * same set of feature configurations.
 * 
 * The converter supports converting using Conjunctive (CNF) and Disjunctive 
 * Normal Form (DNF), and a designated and a naive method each.
 * 
 * In the following, "feature model" generally refers to the instance variable 
 * "fm" that is set in the "convert" method. When using any of the protected 
 * methods outside of the context of "convert", subclasses and external callers 
 * must ensure that "fm" is set correctly and all other preconditions hold.   
 * 
 * @author Arthur Hammer
 */
public class ComplexConstraintConverter {

	protected FeatureModel fm;
	protected boolean useCNF;
	protected boolean useEquivalenceConstraints = true; 
	protected boolean cleanInputModel = false;

	public ComplexConstraintConverter() {
		this(true);
	}
	
	/**
	 * Whether to use Conjunctive (CNF) or Disjunctive Normal Form (DNF) when
	 * converting the model. 
	 * @param useCNF Whether to use CNF or DNF
	 */
	public ComplexConstraintConverter(boolean useCNF) {
		this.useCNF = useCNF; 
	}
	
	/**
	 * If set, adds biimplications for requires constraints instead of single 
	 * implications. (Not applicable for excludes constraints or DNF conversion.)
	 * 
	 * Generally reduces the number of feature configurations but adds more
	 * constraints.  
	 * 
	 * Default is true.
	 */
	public void setUseEquivalenceConstraints(boolean useEquivalence) {
		this.useEquivalenceConstraints = useEquivalence;
	}
	
	public boolean setUseEquivalenceConstraints() {
		return useEquivalenceConstraints;
	}

	/**
	 * If set, removes redundant and tautology constraints from the input model
	 * before converting.  If a constraint makes the model void, converts the 
	 * model into a trivial void model.
	 * 
	 * This might result in a smaller output model (number of features and 
	 * number constraints). However, the constraint analysis usually slows down 
	 * the conversion process considerably. 
	 * 
	 * Default is false.
	 */
	public void setCleanInputModel(boolean cleansInputModel) {
		this.cleanInputModel = cleansInputModel;
	}
	
	public boolean getCleanInputModel() {
		return cleanInputModel;
	}
	
	public FeatureModel convert(FeatureModel model) {
		if (model == null) {
			throw new IllegalArgumentException("Feature model cannot be null");
		}

		fm = model.deepClone();
		fm.getAnalyser().runCalculationAutomatically = false;
		
		if (cleanInputModel) {
			cleanupModel();
		}
		
		List<Node> complexConstraints = getComplexConstraints();
		
		if (fm.getRoot() == null || fm.getFeatures().isEmpty() || complexConstraints.isEmpty()) {
			return fm;
		}
		
		removeConstraints(complexConstraints);	
		And complexFormula = new And(complexConstraints);
		Feature formulaFeature = convertFormula(complexFormula, (useCNF ? "CNF" : "DNF"));
		restructureRootToAnd();
		fm.getRoot().addChild(formulaFeature);
		
		return fm;
	}
	
	public FeatureModel convertNaive(FeatureModel model) {
		if (model == null) {
			throw new IllegalArgumentException("Feature model cannot be null");
		}
		
		model = model.deepClone(); 
		fm = new FeatureModel();
		fm.getAnalyser().runCalculationAutomatically = false;
		
		if (cleanInputModel) {
			cleanupModel();
		}
		
		Feature root = model.getRoot();
		Feature newRoot = createFeature(root.getName(), root.isAbstract(), false, true);
		fm.setRoot(newRoot);
		
		for (Feature feature: model.getFeatures()) {
			createFeatureUnderRoot(feature.getName(), feature.isAbstract());
		}
		
		Node formula = NodeCreator.createNodes(model);
		Node[] children = formula.getChildren();		
		formula.setChildren( Arrays.copyOfRange(children, 1, children.length));
		
		// Currently, NodeCreator appends three uneccessary nodes of the form 
		// "True and (not False) and (... or True)".
		// For now, we just chop them off. In the future, dynamic detection of
		// those kinds of nodes might be better.
		if (children.length >= 3) {
			formula.setChildren(Arrays.copyOf(children, children.length - 3));
		}
			
		Feature cnfFeature = convertFormula(formula, (useCNF ? "CNF" : "DNF"));
		newRoot.addChild(cnfFeature);

		return fm;
	}
	
	/**
	 * Removes redundant and tautology constraints from the feature model.
	 * 
	 * This might result in a smaller output model (number of features and 
	 * number constraints). However, the constraint analysis usually slows down 
	 * the conversion process considerably.
	 * 
	 * If a constraint makes the model void, converts the model into a trivial
	 * void model.
	 */
	protected void cleanupModel() {
		// TODO: Can we make use of previous analysis (if any) and skip new one?
		FeatureModelAnalyzer analyzer = fm.getAnalyser();
		
		analyzer.calculateFeatures = true;
		analyzer.calculateConstraints = true;
		analyzer.calculateRedundantConstraints = true;
		analyzer.calculateTautologyConstraints = true;
		
		analyzer.analyzeFeatureModel(null);
		List<Constraint> toRemove = new LinkedList<Constraint>();
		
		for (Constraint c : fm.getConstraints()) {
			ConstraintAttribute attribute = c.getConstraintAttribute();
			
			if (attribute == ConstraintAttribute.REDUNDANT || attribute == ConstraintAttribute.TAUTOLOGY) {
				toRemove.add(c);
			}
			else if (attribute == ConstraintAttribute.VOID_MODEL || attribute == ConstraintAttribute.UNSATISFIABLE ) {
				// Simplifiy model to only contain a single contradicting constraint 
				FeatureModel voidModel = new FeatureModel();
				Feature root = createAbstractFeature(fm.getRoot().getName(), false, false);
				voidModel.setRoot(root);
				voidModel.addConstraint(new Constraint(voidModel, new Implies(root, new Not(root))));
				
				fm = voidModel;
				toRemove.clear();
				return;
			}
		}

		for (Constraint c : toRemove) {
			fm.removeConstraint(c);
		}
	}
	
	protected Feature convertFormula(Node formula, String name) {
//		Node normalForm = formula.clone().toCNF();
		Node normalForm = useCNF ? NormalForms.toCNF(formula) : NormalForms.toDNF(formula);
		normalForm = normalizeNormalForm(normalForm);
		return convertNormalForm(normalForm, name);
	}
	
	/**
	 * Converts a node in CNF or DNF into a Feature. The feature is added to 
	 * the feature model, but is not added as a child to any other feature yet.
	 * 
	 * Also adds corresponding simple constraints to the feature model.
	 * 
	 * This and the following convert method make the following assumptions:
	 * 
	 * - All literals in the formula have a corresponding feature in the
	 *   feature model.
	 * - The formula does not contain representations for logical true/false 
	 *   constants.
	 * - The normal form node has a proper CNF or DNF structure and does not 
	 *   contain null values.
	 *   
	 * The methods do not explicitly check the structure of the normal form.
	 * I.e. if a node is passed in DNF form but useCNF is true, the node will
	 * be treated as a CNF node.
	 * 
	 * @param normalForm
	 * @param name
	 * @return
	 */
	protected Feature convertNormalForm(Node normalForm, String name) {
		Node[] clauses = normalForm.getChildren();
		Feature normalFormFeature = createAbstractFeature(name, true, useCNF);
		
		for (int i = 0; i < clauses.length; i++) {
			Feature clauseFeature = convertClause(clauses[i], "" + (i+1));
			normalFormFeature.addChild(clauseFeature);
		}
		
		return normalFormFeature;
	}
	
	protected Feature convertClause(Node clause, String name) {
		Feature clauseFeature = createAbstractFeature("Clause" + name, true, !useCNF);	
		Node[] literals = (clause instanceof Literal) ? new Node[]{clause} : clause.getChildren();
		
		for (int i = 0; i < literals.length; i++) {
			if (! (literals[i] instanceof Literal)) {
				throw new IllegalArgumentException("Node in clause is not a literal: " + literals[i] + " of type: " + literals[i].getClass());
			}
			
			Literal literal = (Literal) literals[i];
			Feature originalFeature = fm.getFeature(literal.var.toString());
			if (originalFeature == null) {
				throw new IllegalArgumentException("No corresponding feature in model for literal in formula: " + literal);
			}
			
			// Skip creating separate literal feature if clause contains only a single literal
			if (useCNF && literals.length > 1) {
				Feature literalFeature = createAbstractFeature(originalFeature.getName() + " [" + name + "]", false, false);
				clauseFeature.addChild(literalFeature);
				addSimpleConstraint(literalFeature, originalFeature, literal.positive);
			}
			else {
				addSimpleConstraint(clauseFeature, originalFeature, literal.positive);
			}
		}
		
		return clauseFeature;
	}
	
	/**
	 * Restructures a Node such that it is in "full" CNF (Ands of Ors of Literals)
	 * or DNF (Ors of Ands of Literals) form.
	 * 
	 * Only does basic restructuring such as wrapping literals into a clause
	 * and a root node. More complex patterns are not treated and should be 
	 * handled by the caller.
	 * 
	 * @param normalForm
	 * @return
	 */
	protected Node normalizeNormalForm(Node normalForm) {
		// If CNF is null, it is true. As there are no constants for 
		// propositional true and false, add an artifical node "root or not root".
		// If DNF is null, it is false. Add node "root and not root".
		if (normalForm == null) {
			String root = fm.getRoot().getName();
			return useCNF ? 
					new And(new Or(new Literal(root), new Literal(root, false))) : 
						new Or(new And(new Literal(root), new Literal(root, false)));
		}
		if (normalForm instanceof Or && useCNF) {
			return new And(normalForm);
		}
		if (normalForm instanceof And && !useCNF) {
			return new Or(normalForm);
		}
		if (normalForm instanceof Literal) {
			return useCNF ? new And(new Or(normalForm)) : new Or(new And(normalForm));
		}
		
		return normalForm;	
	}

	protected void addSimpleConstraint(Feature f, Feature g, boolean requires) {
		Node fNode = new Literal(f.getName());
		Node gNode = new Literal(g.getName());
		
		Node implies = new Implies(fNode, (requires ? gNode : new Not(gNode)));
		fm.addConstraint(new Constraint(fm, implies));
		
		// biimplications to reduce number of configurations
		if (requires && useCNF && useEquivalenceConstraints) {
			// Clone so constraints can be modified independently
			implies = new Implies(gNode.clone(), fNode.clone());
			fm.addConstraint(new Constraint(fm, implies));
		}
	}
	
	protected List<Node> getComplexConstraints() {
		List<Node> complex = new LinkedList<Node>();

		for (Constraint constraint: fm.getConstraints()) {
			if (!constraint.isSimple()) {
				complex.add(constraint.getNode());
			}
		}

		return complex;
	}

	protected void removeConstraints(List<Node> constraints) {
		for (Node constraint: constraints) {
			fm.removePropositionalNode(constraint);
		}	
	}

	protected Feature createAbstractFeature(String name, boolean isMandatory, boolean isAnd) {
		return createFeature(name, true, isMandatory, isAnd);
	}

	protected Feature createConcreteFeature(String name, boolean isMandatory, boolean isAnd) {
		return createFeature(name, false, isMandatory, isAnd);
	}
	
	/**
	 * Creates a new feature. The feature is added to the feature model, but is
	 * not added as a child to any other feature yet. If a feature with the
	 * chosen name already exists, a unique name is generated.
	 * 
	 * @param name Name of the new feature
	 * @param isAbstract Whether the feature is abstract or not
	 * @param isMandatory Whether the feature is mandatory or optional
	 * @param isAnd Whether the feature is an And- or an Or-group
	 * @return New feature
	 */
	protected Feature createFeature(String name, boolean isAbstract, boolean isMandatory, boolean isAnd) {
		Feature feature = new Feature(fm, name);
		feature.setAbstract(isAbstract);
		feature.setMandatory(isMandatory);
		
		if (isAnd) {
			feature.setAnd();
		}
		else {
			feature.setOr();
		}
		
		// Find unique name
		int i = 1;
		while (!fm.addFeature(feature)) {
			feature.setName(name + "-" + (i++));
		}
	
		return feature;
	}
	
	/**
	 * Adds an optional feature to the root if it does not exist yet in the 
	 * model. Restructures the root to an And-group if necessary.
	 * 
	 * @param name Name of the new feature
	 * @param isAbstract Whether the feature is abstract
	 * @return New feature if it didn't not exist yet, null otherwise.
	 */
	protected Feature createFeatureUnderRoot(String name, boolean isAbstract) {
		if (fm.getFeature(name) == null) {
			restructureRootToAnd();
			Feature newFeature = createFeature(name, isAbstract, false, false);
			fm.getRoot().addChild(newFeature);
			return newFeature;
		}
		
		return null;
	}
	
	protected void restructureRootToAnd() {
		Feature root = fm.getRoot();

		if (!root.isAnd()) {
			Feature newRoot = createAbstractFeature("NewRoot", false, true);
			newRoot.addChild(root);
			root.setMandatory(true);			
			fm.setRoot(newRoot);
		}
	}
}