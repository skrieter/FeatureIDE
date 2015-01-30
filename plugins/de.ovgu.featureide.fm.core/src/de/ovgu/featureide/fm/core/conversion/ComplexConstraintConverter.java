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
import org.prop4j.Implies;
import org.prop4j.Literal;
import org.prop4j.Node;
import org.prop4j.Not;
import org.prop4j.Or;

import de.ovgu.featureide.fm.core.Constraint;
import de.ovgu.featureide.fm.core.Feature;
import de.ovgu.featureide.fm.core.FeatureModel;
import de.ovgu.featureide.fm.core.editing.NodeCreator;

// product-preserving
// configuration-preserving

// TODO: Test all models and time how much longer statistics collecting takes (see: BFeatureModelAnalyser)
//       time for input model, time for output model
// TODO: Download auto-generated example models from http://featureide.cs.ovgu.de (bottom) and test
// TODO: documentation

/**
 * A class to convert feature models with arbitrary cross-tree constraints
 * into models with only simple constraints but describing the same set of 
 * product variants. The conversion is a refactoring.
 * 
 * A constraint is simple if it has the form "f => g" or "f => not g" where f 
 * and g are features.
 * 
 * In the following, "feature model" generally refers to the instance variable 
 * "fm" that is set in the "convert" method. When using any of the protected 
 * methods outside of the context of "convert", subclasses and external callers 
 * must ensure that "fm" is set correctly.   
 * 
 * @author Arthur Hammer
 */
public class ComplexConstraintConverter {

	protected FeatureModel fm;
	protected boolean useCNF;
	
	public ComplexConstraintConverter() {
		this(true);
	}
	
	public ComplexConstraintConverter(boolean useCNF) {
		this.useCNF = useCNF; 
	}

	public FeatureModel convert(FeatureModel model) {
		if (model == null) {
			throw new IllegalArgumentException("Feature model cannot be null");
		}

		fm = model.deepClone();
		fm.getAnalyser().runCalculationAutomatically = false;
		List<Node> complexConstraints = getComplexConstraints();
		
		if (fm.getRoot() == null || fm.getFeatures().isEmpty() || complexConstraints.isEmpty()) {
			return fm;
		}
		
		removeConstraints(complexConstraints);	
		And complexFormula = new And(complexConstraints);
		Feature formulaFeature = convertFormula(complexFormula, (this.useCNF ? "CNF" : "DNF"));
		restructureRootToAnd();
		fm.getRoot().addChild(formulaFeature);
		
		return fm;
	}
	
	public FeatureModel convertNaive(FeatureModel model) {
		model = model.deepClone(); 
		fm = new FeatureModel();
		fm.getAnalyser().runCalculationAutomatically = false;
		
		Feature root = model.getRoot();
		Feature newRoot = createFeature(root.getName(), root.isAbstract(), false, true);
		fm.setRoot(newRoot);
		
		for (Feature feature: model.getFeatures()) {
			createFeatureUnderRoot(feature.getName(), feature.isAbstract());
		}
		
		Node formula = NodeCreator.createNodes(model);
		Node[] children = formula.getChildren(); // TODO: empty etc.		
		
		// NodeCreator appends three uneccessary nodes
		if (children.length >= 3) {
			formula.setChildren(Arrays.copyOf(children, children.length - 3));
		}
			
		Feature cnfFeature = convertFormula(formula, "CNF");
		newRoot.addChild(cnfFeature);

		return fm;
	}
	
	protected Node normalizeNormalForm(Node normalForm) {
		if (normalForm instanceof Or && useCNF) {
			return new And(normalForm);
		}
		if (normalForm instanceof And && !useCNF) {
			return new Or(normalForm);
		}
		if (normalForm instanceof Literal) {
			if (useCNF) {
				return new And(new Or(normalForm));
			}
			else {
				return new Or(new And(normalForm));
			}
		}
		
		return normalForm;	
	}
	
	protected Feature convertFormula(Node formula, String name) {
		Node normalForm = this.useCNF ? formula.toCNF() : formula.toDNF();
		normalForm.simplify();
		normalForm = normalizeNormalForm(normalForm);
		System.out.println("NormalForm: " + normalForm);
		return convertNormalForm(normalForm, name);
	}
	
	// TODO: TRUE/FALSE cases
	// TODO: What if cnf is only a constant true/false? (or an OR of a constant true/false)
	protected Feature convertNormalForm(Node normalForm, String name) {
		Node[] clauses = normalForm.getChildren();
		Feature normalFormFeature = createAbstractFeature(name, true, this.useCNF);
		
		for (int i = 0; i < clauses.length; i++) {
			Feature clauseFeature = convertClause(clauses[i], "" + (i+1));
			normalFormFeature.addChild(clauseFeature);
		}
		
		return normalFormFeature;
	}
	
	protected Feature convertClause(Node clause, String name) {
		Feature clauseFeature = createAbstractFeature("Clause" + name, true, !this.useCNF);	
		Node[] literals = (clause instanceof Literal) ? new Node[]{clause} : clause.getChildren();
		
		for (int i = 0; i < literals.length; i++) {
			if (! (literals[i] instanceof Literal)) {
				throw new IllegalArgumentException("Node in clause is not a literal: " + literals[i] + "of type: " + literals[i].getClass());
			}
			
			Literal literal = (Literal) literals[i];
			Feature originalFeature = fm.getFeature((String) literal.var);

			// make sure literal not constant true or false
			if (originalFeature == null) {
				throw new IllegalArgumentException("No corresponding feature for literal in formula: " + literal);
			}
			
			if (this.useCNF) {
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

	protected void addSimpleConstraint(Feature f, Feature g, boolean requires) {
		Node implies = new Implies(f, (requires ? g : new Not(g)));
		fm.addConstraint(new Constraint(fm, implies));
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
			Feature newRoot = createAbstractFeature("__NewRoot__", false, true);
			newRoot.addChild(root);
			root.setMandatory(true);			
			fm.setRoot(newRoot);
		}
	}
}