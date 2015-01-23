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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.prop4j.Literal;
import org.prop4j.Node;
import org.prop4j.NodeReader;
import org.prop4j.Not;

import de.ovgu.featureide.fm.core.Constraint;
import de.ovgu.featureide.fm.core.Feature;
import de.ovgu.featureide.fm.core.FeatureModel;

/**
 * Class that converts any propositional formula into an equivalent feature 
 * model representing that formula.
 * 
 * This is a proof-of-concept, the resulting feature model is not 
 * optimized for any particular purpose.
 * 
 * @author Arthur Hammer
 */
public class FormulaConverter {
	
	public FeatureModel convert(String formula) {
		if (formula == null) {
			throw new IllegalArgumentException("Formula cannot be null.");
		}
		
		NodeReader reader = new NodeReader();
		return convert(reader.stringToNode(formula));
	}
	
	public FeatureModel convert(Node formula) {
		if (formula == null) {
			throw new IllegalArgumentException("Formula cannot be null.");
		}
		
		FeatureModel fm = new FeatureModel();
		fm.addConstraint(new Constraint(fm, formula));
		
		Set<String> featureNames = getFeatureNames(formula);
		String rootName = "root";
		
		// Find unique name for root
		int i = 1;
		while (! featureNames.contains(rootName)) {
			rootName = rootName + (i++);
		}		
		
		Feature root = new Feature(fm, rootName);
		root.setAbstract(true);
		root.setAnd();
		fm.setRoot(root);

		for (String name: featureNames) {
			Feature feature = new Feature(fm, name);
			feature.setMandatory(false);
			root.addChild(feature);
			fm.addFeature(feature);
		}
		
		ComplexConstraintConverter converter = new ComplexConstraintConverter();
		return converter.convert(fm);
	}
	
	/**
	 * Finds all feature names from the set of literals in a formula.
	 * Uses a simple Breadth First Search (Level Order) over the Node tree.
	 * 
	 * @param formula Formula to find feature names from
	 * @return Set of feature names corresponding to the formula's literals 
	 */
	protected Set<String> getFeatureNames(Node formula) {
		if (formula == null) {
			throw new IllegalArgumentException("Formula cannot be null.");
		}
		
		LinkedList<Node> toVisit = new LinkedList<Node>();
		Set<String> featureNames = new HashSet<String>();
		Node node;
		
		toVisit.add(new Not(formula)); // Dummy node		
		
		while (! toVisit.isEmpty()) {
			node = toVisit.removeFirst();
			Node[] children = node.getChildren();
			
			if (children != null) {
				for (int i = 0; i < children.length; i++) {
					if (children[i] instanceof Literal) {
						featureNames.add((String) ((Literal) children[i]).var);
					}
					else if (children[i] != null) {
						toVisit.add(children[i]);
					}
				}
			}
		}
		
		return featureNames;
	}
}
