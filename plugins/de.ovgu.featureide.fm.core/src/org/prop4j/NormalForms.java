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
package org.prop4j;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Class to convert Nodes to Conjunctive (CNF) or Disjunctive Normal Form 
 * (DNF). 
 * 
 * Largely based on the "toCNF" and related methods in Node, Or, And, and 
 * Literal.
 * 
 * Adapted mainly to apply simple heuristics to remove unnecessary literals and 
 * clauses when creating the CNF and DNF. It is likely slower as the "toCNF"
 * method in Node, and there is no guarantee the created Node is any simpler.
 *   
 * Uses LinkedHashSets instead of LinkedLists internally. As such, depends on
 * a proper implementation of hashCode in Literal.
 * 
 * In contrast to the original method, resulting Nodes might be null. In the case
 * of CNF, null nodes represent always true formulas. For DNF, always false. 
 *  
 * @author Arthur Hammer
 */


public class NormalForms {

	@SuppressWarnings("unchecked")
	/**
	 * Negative Normal Form (NNF) only contains And, Or and Literal nodes.
	 */
	public static Node toNNF(Node n) {
		n = n.eliminate(Choose.class, Equals.class, Implies.class);
		n = n.eliminate(Not.class);
		n = n.eliminate(AtMost.class, AtLeast.class);
		n = n.eliminate(Not.class);
		return n;
	}
	
	/**
	 * Creates the CNF from a given node. 
	 * Returns null if the conversion detected the CNF to be always true. 
	 */
	public static Node toCNF(Node node) {
		node = toNNF(node);
		node = clausify(node, true);
		// Empty Ands are true
		return (node instanceof And && node.getChildren().length == 0) ? null : node;
	}
	
	/**
	 * Creates the CNF from a given node. 
	 * Returns null if the conversion detected the CNF to be always true. 
	 */
	public static Node toCNF(String formula) {
		return toCNF(new NodeReader().stringToNode(formula));
	}

	/**
	 * Creates the DNF from a given node. 
	 * Returns null if the conversion detected the DNF to be always false. 
	 */
	public static Node toDNF(Node node) {
		node = toNNF(node);
		node = clausify(node, false);
		// Empty Ors are false
		return (node instanceof Or && node.getChildren().length == 0) ? null : node;
	}
	
	/**
	 * Creates the DNF from a given node. 
	 * Returns null if the conversion detected the DNF to be always false. 
	 */
	public static Node toDNF(String formula) {
		return toDNF(new NodeReader().stringToNode(formula));
	}
	

	/**
	 * Recursively creates CNF (DNF respectively) of the nodes' children. 
	 * 
	 * After that, fuses with And children (Or children respectively) such 
	 * that children are only Or and Literal nodes (And and Literal nodes 
	 * respectively).
	 * 
	 * Finally creates the top-level CNF (DNF respectively).
	 *  
	 * The returned node might have no children in which case it is always
	 * true (always false respectively). 
	 */
	private static Node clausify(Node node, boolean useCNF) {
		if (node instanceof Literal) {
			return node;
		}

		Node[] children = node.getChildren();
		// No sets here as arbitrary Nodes (And, Or) do not support hashCode
		List<Node> newChildren = new LinkedList<Node>();
		
		for (int i = 0; i < children.length; i++) {
			Node result = clausify(children[i], useCNF);
			newChildren.add(result);
		}
		
		node.setChildren(newChildren);
		node.fuseWithSimilarChildren();

		// In case node is of type And, we could stop and return the node.
		// However, we wrap the node and try the conversion one more time in
		// hope of removing more duplicate literals and clauses.
		// Same below for Or nodes.
		if (node instanceof And) {
			return useCNF ? createNormalForm(new Node[]{node}, useCNF) : createNormalForm(node.children, useCNF);
		}
		else if (node instanceof Or) {
			return useCNF ? createNormalForm(node.children, useCNF) : createNormalForm(new Node[]{node}, useCNF);
		}
		else {
			throw new IllegalArgumentException("Node must be of type And, Or or Literal.");
		}
	}
	
	/**
	 * The returned node might have no children in which case it is always
	 * true for CNF, always false for DNF. 
	 */
	private static Node createNormalForm(Node[] children, boolean useCNF) {
		Set<Set<Literal>> clauses = new LinkedHashSet<Set<Literal>>();
		clauses.add(new LinkedHashSet<Literal>());
		
		for (Node node : children) {
			Set<Set<Literal>> newClauses = new LinkedHashSet<Set<Literal>>();

			// CNF: Create clauses (Ors of Literals)
			if (useCNF) {
				Node[] orNodes = node instanceof And ? node.getChildren() : new Node[] {node};
				for (Node or : orNodes) {
					Node[] literals = or instanceof Or ? or.getChildren() : new Node[] {or};
					newClauses.add(toClause(literals));
				}
			}
			// DNF: Create clauses (Ands of Literals)
			else {
				Node[] andNodes = (node instanceof Or ? node.getChildren() : new Node[] {node});
				for (Node and : andNodes) {
					Node[] literals = and instanceof And ? and.getChildren() : new Node[] {and};
					newClauses.add(toClause(literals));
				}
			}
			
			clauses = updateClauses(clauses, newClauses);
		}
		
		// Set newly created clauses as new children (Ors for CNF, Ands for DNF)
		Set<Node> newChildren = new LinkedHashSet<Node>();
		for (Set<Literal> clause : clauses) {
			newChildren.add(useCNF ? new Or(clause).clone() : new And(clause).clone());
		}
		
		// Create enclosing Normal Form Node (And for CNF, Or for DNF)
		return useCNF ? new And(newChildren) : new Or(newChildren);
	}

	private static Set<Set<Literal>> updateClauses(Set<Set<Literal>> clauses, Set<Set<Literal>> newClauses) {
		Set<Set<Literal>> updatedClauses = new LinkedHashSet<Set<Literal>>();
		
		for (Set<Literal> oldClause : clauses) {
			for (Set<Literal> newClause : newClauses) {
				if (oldClause.containsAll(newClause)) {
					add(updatedClauses, oldClause);
					continue;
				}
			}
			
			// Combine clauses
			for (Set<Literal> newClause : newClauses) {
				Set<Literal> combinedClause = new LinkedHashSet<Literal>(clone(oldClause));

				for (Literal literal : newClause) {
					Literal negation = (Literal) literal.clone(); 
					negation.flip();
					// Literal and its negation make clause always true (CNF) or always false (DNF)
					if (combinedClause.contains(negation)) {
						combinedClause = null;
						break;
					}
					
					combinedClause.add(literal.clone());
				}

				if (combinedClause != null) {
					add(updatedClauses, combinedClause);
				}
			}
		}
		
		return updatedClauses;
	}
	
	/**
	 * Adds a new clause to an existing set of clauses only if the the new clause
	 * is a proper subset or disjunct from all existing clauses.
	 * In the first case, all superset clauses are removed as they carry
	 * redundant information.
	 */
	private static void add(Set<Set<Literal>> clauses, Set<Literal> newClause) {
		Iterator<Set<Literal>> iter = clauses.iterator();
		while (iter.hasNext()) {
			Set<Literal> clause = iter.next();
			if (newClause.containsAll(clause)) return; 
			if (clause.containsAll(newClause)) iter.remove();
		}

		clauses.add(newClause);
	}

	private static Set<Literal> toClause(Node[] nodes) {
		Set<Literal> clause = new LinkedHashSet<Literal>();
		for (Node node : nodes) {
			if (!(node instanceof Literal)) {
				throw new IllegalArgumentException("Expected literal when creating normal form. Encountered: " + node + ", of class: " + node.getClass());
			}
			clause.add((Literal) node);
		}
		return clause;
	}

	private static Set<Literal> clone(Set<Literal> list) {
		Set<Literal> newList = new LinkedHashSet<Literal>();
		for (Literal literal: list)
			newList.add(literal.clone());
		return newList;
	}
}
