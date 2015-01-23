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

import java.util.LinkedList;
import java.util.List;

import org.prop4j.And;
import org.prop4j.Implies;
import org.prop4j.Literal;
import org.prop4j.Node;
import org.prop4j.Not;

import de.ovgu.featureide.fm.core.Constraint;
import de.ovgu.featureide.fm.core.Feature;
import de.ovgu.featureide.fm.core.FeatureModel;
import de.ovgu.featureide.fm.core.editing.NodeCreator;

/**
 * A class to convert feature models with arbitrary cross-tree constraints
 * into models with only simple constraints but describing the same set of 
 * product variants. The conversion is a refactoring.
 * 
 * A constraint is simple if it has the form "f => g" or "f => not g" where f 
 * and g are features.
 * 
 * In the following, "feature model" generally refers to the instance provided
 * in the "convert" method.
 * 
 * @author Arthur Hammer
 */
public class ComplexConstraintConverter {

	protected FeatureModel fm = null;
	// TODO: Make this consistent: Either as method argument or as instance variable everywhere
	protected boolean useCNF;
	protected boolean useEquivalence = false; // TEST
	
	public ComplexConstraintConverter() {
		this.useCNF = true;
	}
	
	public ComplexConstraintConverter(boolean useCNF) {
		this.useCNF = useCNF; 
	}

	/**
	 * Converts the feature model to a product-variant-equivalent model 
	 * containing only simple constraints. 
	 * 
	 * The result is the same instance as the input model.
	 * 
	 * @param fm The feature model to convert
	 * @return Feature model with simple constraints. Same instance as fm.  
	 */
	public FeatureModel convert(FeatureModel fm) {
		if (fm == null) {
			throw new NullPointerException("Feature model cannot be null");
		}
		
		this.fm = fm; // So all methods can easily access the model
		List<Node> complexConstraints = getComplexConstraints();

		if (complexConstraints.isEmpty()) {
			return fm;
		}
		
		removeConstraints(complexConstraints);				
		And complexFormula = new And(complexConstraints);
		Node normalForm = this.useCNF ? convertToCNF(complexFormula) : convertToDNF(complexFormula);
		Feature normalFormFeature = convertNormalForm(normalForm, (this.useCNF ? "CNF" : "DNF"), this.useCNF);
	
		if (normalFormFeature != null) {
			restructureRootToAnd();
			this.fm.getRoot().addChild(normalFormFeature);
		}
		
		return fm;
	}
	
	/**
	 * A naive variant of "convert". Converts the feature model into its 
	 * formula representation and rebuilds the model completely from the 
	 * formula. 
	 * 
	 * @param fm The feature model to convert
	 * @return Feature model with simple constraints. Same instance as fm.
	 */
	public FeatureModel convertNaive(FeatureModel fm) {
		this.fm = fm;
		Node fmFormula = NodeCreator.createNodes(this.fm.clone());
		
		Feature root = fm.getRoot();
		root.setAnd(); 
		root.setChildren(new LinkedList<Feature>());
		
		for (Feature feature: fm.getFeatures()) {
			feature.setParent(root);
			feature.setMandatory(false);
			feature.setChildren(new LinkedList<Feature>());
		}
		
		fm.setConstraints(new LinkedList<Constraint>());

		Node normalForm = this.useCNF ? convertToCNF(fmFormula) : convertToDNF(fmFormula);
		Feature normalFormFeature = convertNormalForm(normalForm, (this.useCNF ? "CNF" : "DNF"), this.useCNF);
		root.addChild(normalFormFeature);

		this.fm = null;
		return fm;
	}
	
	/**
	 * Converts a formula in Conjunctive Normal Form (CNF) or Disjunctive 
	 * Normal Form (DNF), given as Node, into a feature hierarchy. 
	 * 
	 * All features created are added to the feature model.
	 * 
	 * All literals in the formula must have corresponding features in the
	 * feature model. If not, an IllegalArgumentException is thrown.
	 * 
	 * @param normalForm The formula in CNF or DNF, as Node, to convert
	 * @param name The name for the new feature representing the formula
	 * @param useCNF Whether the formula is in CNF or in DNF
	 * @return A feature hierarchy representing the formula
	 */
	// TODO: We completely ignore whether Node is instanceof And or Or (or Literal).
	//       We simply use useCNF. Maybe autodetect CNF from AND or stuff and remove useCNF parameter
	// TODO: How strict when parsing the formulas is appropriate?
	// TODO: What if cnf is a single literal?
	// TODO: What if cnf is a single disjunction and useCNF = true?
	// TODO: Return null or throw exceptions for ill-formed formulas?
	// TODO: A million edge cases
	protected Feature convertNormalForm(Node normalForm, String name, boolean useCNF) {
		if (normalForm == null) {
			throw new IllegalArgumentException("Formula cannot be null.");
		}
		
		Node[] clauses = normalForm.getChildren();
		if (clauses == null) {
			throw new IllegalArgumentException("Formula doesn't contain any clauses.");
		}
		
		Feature normalFormFeature = createAbstractFeature(name, true, useCNF);
		for (int i = 0; i < clauses.length; i++) {
			Feature clauseFeature = convertClause(clauses[i], "Clause" + (i+1), useCNF);
			normalFormFeature.addChild(clauseFeature);
		}
		
		return normalFormFeature;
	}
	
	/**
	 * Converts a formula in the form of a clause, given as Node, into a 
	 * feature hierarchy. A clause is either a conjunction or a disjunction of
	 * literals. 
	 * 
	 * Also adds corresponding simple constraints to the feature model involving
	 * pairs of original and newly created features. 
	 * 
	 * All features created are added to the feature model.
	 * 
	 * All literals in the clause must have corresponding features in the
	 * feature model. If not, an IllegalArgumentException is thrown.
	 * 
	 * @param clause The clause to convert
	 * @param name The name for the new feature representing the clause 
	 * @param useCNF Whether the clause is a disjunction (in a CNF) or a conjunction (in a DNF)
	 * @return A feature hierarchy representing the clause
	 */
	protected Feature convertClause(Node clause, String name, boolean useCNF) {
		if (this.fm == null) {
			throw new IllegalArgumentException("Clause cannot be null");
		}
		
		Feature clauseFeature = createAbstractFeature(name, true, !useCNF);		
		Node[] literals = clause.getChildren();

		// Clause can contain only a single literal, i.e. is a literal on its own
		if (literals == null) {
			literals = new Node[]{clause};
		}
		
		for (int i = 0; i < literals.length; i++) {
			if (! (literals[i] instanceof Literal)) {
				throw new IllegalArgumentException("Node in clause is not a literal: " + literals[i] + "of type: " + literals[i].getClass());
			}
			
			Literal literal = (Literal) literals[i];
			Feature originalFeature = getFeature(literal);
			
			if (useCNF) {
				Feature literalFeature = createAbstractFeature(originalFeature.getName() + "-" + name, false, false);
				clauseFeature.addChild(literalFeature);
				addSimpleConstraint(literalFeature, originalFeature, literal.positive);
			}
			else {
				addSimpleConstraint(clauseFeature, originalFeature, literal.positive);
			}
		}
		
		return clauseFeature;
	}
	
	protected Node convertToCNF(Node formula) {
		return formula.toCNF();
	}
	
	protected Node convertToDNF(Node formula) {
		// TODO: Implementation
		return null;
	}
	
	/**
	 * Adds a simple constraint of the form "f => g" (requires) or "f => not g"
	 * (excludes) to the feature model.
	 * 
	 * @param f A feature
	 * @param g A feature
	 * @param requires Type of constraint to add: Whether f requires or f excludes g.
	 */
	protected void addSimpleConstraint(Feature f, Feature g, boolean requires) {
		Node implies = new Implies(f, (requires ? g : new Not(g)));
		this.fm.addConstraint(new Constraint(this.fm, implies));
		
		// TEST
		if (this.useCNF && this.useEquivalence) {
			implies = new Implies((requires ? g : new Not(g)), f);
			this.fm.addConstraint(new Constraint(this.fm, implies));
		}
	}
	
	/**
	 * Finds the corresponding feature in the feature model for a given literal
	 * in a constraint. The corresponding feature is the one with the same name.
	 * 
	 * @param literal A literal
	 * @return The corresponding feature
	 */
	protected Feature getFeature(Literal literal) {
		assert(literal.var instanceof String);
		return this.fm.getFeature((String) literal.var);
	}

	/**
	 * Finds all of the feature model's complex constraints, in the form of 
	 * Nodes.
	 * 
	 * @return A list of complex constraint nodes.
	 */
	protected List<Node> getComplexConstraints() {
		List<Node> complex = new LinkedList<Node>();

		for (Constraint constraint: this.fm.getConstraints()) {
			if (!constraint.isSimple()) {
				complex.add(constraint.getNode());
			}
		}

		return complex;
	}

	/**
	 * Removes a set of constraints, given as Nodes, from the feature model. 
	 * @param constraints List of constraint nodes.
	 */
	protected void removeConstraints(List<Node> constraints) {
		for (Node constraint: constraints) {
			this.fm.removePropositionalNode(constraint);
		}	
	}

	/**
	 * Restructures the root of the feature model such that it is an AND group.
	 * This does not affect the model's set of product variants.
	 */
	protected void restructureRootToAnd() {
		Feature root = this.fm.getRoot();

		if (! root.isAnd()) {
			Feature newRoot = createAbstractFeature("U", false, true);
			newRoot.addChild(root);
			root.setMandatory(true);			
			this.fm.setRoot(newRoot);
		}
	}

	/**
	 * Convenience method to create abstract features. Features are added
	 * to the feature model. In case a feature with a given name already exists,
	 * a unique name will be generated.
	 * 
	 * @param name Unique name for the feature
	 * @param isMandatory Whether the feature is mandatory or optional
	 * @param isAnd Whether the feature is an AND or an OR group
	 *  
	 * @return the resulting feature.
	 */
	protected Feature createAbstractFeature(String name, boolean isMandatory, boolean isAnd) {
		Feature feature = new Feature(this.fm, name);
		feature.setAbstract(true);
		feature.setMandatory(isMandatory);
		
		if (isAnd) {
			feature.setAnd();
		}
		else {
			feature.setOr();
		}
		
		// Although unlikely, a model may already have many features named
		// <name> + <number>. In that case, this loop generates unnecessary 
		// overhead.
		// Maybe use (a prefix of) a UUID instead? Although, this would not be 
		// as readable for the user. 
		int i = 1;
		while (! this.fm.addFeature(feature)) {
			feature.setName(name + " -- " + (i++));
		}

		return feature;
	}
}