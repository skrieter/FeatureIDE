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
import de.ovgu.featureide.fm.core.FeatureModel;

/**
 * Class that converts any propositional formula into a product-equivalent 
 * feature model representing that formula.
 * 
 * Each atomic formula will have a concrete feature in the resulting model.
 * Additionally, the model will have intermediate abstract features.
 * 
 * This is a proof-of-concept, the resulting feature model is not optimized 
 * for any particular purpose. The conversion also does not try to preserve 
 * the structure of the formula in the result model.
 * 
 * For now, the formula cannot contain a literal named "__root__".
 * 
 * @author Arthur Hammer
 */
public class FormulaConverter {
	
	// TODO: What if formula is empty/constant true/false?
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
		ComplexConstraintConverter converter = new ComplexConstraintConverter();
		// converter.fm needs to be set before calling any of the converter's internal methods
		converter.fm = fm;
		
		fm.setRoot(converter.createAbstractFeature("__root__", false, true));
		
		for (String name: getFeatureNames(formula)) {
			converter.createFeatureUnderRoot(name, false);
		}
		
		fm.addConstraint(new Constraint(fm, formula));
		return converter.convert(fm);
	}
	
	// Simple Breadth First Search
	private Set<String> getFeatureNames(Node formula) {
		if (formula == null) {
			throw new IllegalArgumentException("Formula cannot be null.");
		}
		
		LinkedList<Node> toVisit = new LinkedList<Node>();
		Set<String> literals = new HashSet<String>();
		Node node;
		
		toVisit.add(new Not(formula)); // Dummy node		
		
		while (! toVisit.isEmpty()) {
			node = toVisit.removeFirst();
			Node[] children = node.getChildren();
			
			if (children != null) {
				for (int i = 0; i < children.length; i++) {
					if (children[i] instanceof Literal) {
						literals.add(((Literal) children[i]).var.toString());
					}
					else if (children[i] != null) {
						toVisit.add(children[i]);
					}
				}
			}
		}
		
		return literals;
	}
}
