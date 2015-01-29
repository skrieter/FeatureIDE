/* Prop4J - A Library for Propositional Formulas
 * Copyright (C) 2007-2013  Prop4J team, University of Magdeburg, Germany
 *
 * This file is part of Prop4J.
 * 
 * Prop4J is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Prop4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Prop4J.  If not, see <http://www.gnu.org/licenses/>.
 *
 * See http://www.fosd.de/prop4j/ for further information.
 */
package org.prop4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A constraint that is true iff all child nodes are true.
 * 
 * @author Thomas Thuem
 */
public class And extends Node {

	public And(Object ...children) {
		setChildren(children);
	}
	
	public And(Node[] children) {
		setChildren(children);
	}

	@Override
	protected Node clausify() {
		for (int i = 0; i < children.length; i++)
			children[i] = children[i].clausify();
		fuseWithSimilarChildren();
		return this;
	}
	
	@Override
	protected Node clausifyDNF() {
		for (int i = 0; i < children.length; i++)
			children[i] = children[i].clausifyDNF();
		fuseWithSimilarChildren();
		return createDNF(children);
	}
	
	private Node createDNF(Node[] nodes) {
		LinkedList<LinkedList<Node>> clauses = new LinkedList<LinkedList<Node>>();
		clauses.add(new LinkedList<Node>());
		for (Node or : nodes) {
			LinkedList<Node[]> newClauses = new LinkedList<Node[]>();
			for (Node and : or instanceof Or ? or.children : new Node[] {or})
				newClauses.add(and instanceof And ? and.children : new Node[] {and});
			clauses = updateClauses(clauses, newClauses);
		}
		LinkedList<Node> children = new LinkedList<Node>();
		for (LinkedList<Node> clause : clauses)
			children.add(new And(clause).clone());
		return new Or(children);
	}

	private LinkedList<LinkedList<Node>> updateClauses(LinkedList<LinkedList<Node>> clauses,
			LinkedList<Node[]> newClauses) {
		LinkedList<LinkedList<Node>> updatedClauses = new LinkedList<LinkedList<Node>>();
		for (LinkedList<Node> clause : clauses) {
			boolean intersection = false;
			for (Node[] list : newClauses)
				if (containedIn(list, clause)) {
					intersection = true;
					break;
				}
			if (intersection)
				add(updatedClauses, clause);
			else {
				for (Node[] list : newClauses) {
					LinkedList<Node> newClause = clone(clause);
					for (Node node : list)
						newClause.add(node.clone());
					add(updatedClauses, newClause);
				}
			}
		}
		return updatedClauses;
	}

	private void add(LinkedList<LinkedList<Node>> clauses,
			LinkedList<Node> newClause) {
		for (LinkedList<Node> clause : clauses)
			if (containedIn(clause, newClause))
				return;
		clauses.add(newClause);
	}

	private boolean containedIn(LinkedList<Node> clause, LinkedList<Node> newClause) {
		for (Node node : clause)
			if (!newClause.contains(node))
				return false;
		return true;
	}

	private boolean containedIn(Node[] list, LinkedList<Node> clause) {
		for (Node node : list)
			if (!clause.contains(node))
				return false;
		return true;
	}
	
	protected void collectChildren(Node node, List<Node> nodes) {
		if (node instanceof And) {
			for (Node childNode : node.getChildren()) {
				collectChildren(childNode, nodes);
			}
		} else {
			nodes.add(node);
		}
	}
	
	@Override
	public void simplify() {
		List<Node> nodes = new ArrayList<Node>();
		
		for (int i = 0; i < children.length; i++) {
			collectChildren(children[i], nodes);
		}
		
		int size = nodes.size();
		if (size != children.length) {
			Node[] newChildren = nodes.toArray(new Node[size]);
			setChildren(newChildren);
		}
		
		super.simplify();
	}
	
	@Override
	public Node clone() {
		return new And(clone(children));
	}
}
