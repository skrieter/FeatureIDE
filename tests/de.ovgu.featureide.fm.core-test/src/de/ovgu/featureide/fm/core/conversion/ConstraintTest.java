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

import static org.junit.Assert.*;

import org.junit.Test;
import org.prop4j.Implies;
import org.prop4j.Literal;
import org.prop4j.Node;
import org.prop4j.NodeReader;
import org.prop4j.Not;

import de.ovgu.featureide.fm.core.Constraint;


/**
 * Tests for class Constraint and method "isSimple". 
 * 
 * @author Arthur Hammer
 */
public class ConstraintTest {
	
	// Note: Tests depend on correctness and Node structure of NodeReader.

	@Test
	public void testIsSimple() {
		String[] simpleStrings = new String[]{
				"f implies g",
				"f implies not g",
				"a implies b",
				"a implies not b"};
		
		Literal negative = new Literal("g");
		negative.positive = false;
		
		Node[] simpleNodes = new Node[] {
				new Implies("f", "g"),
				new Implies("f", new Not("g")),
				new Implies("f", new Literal("g")),
				new Implies("f", new Not(new Literal("g"))),
				new Implies("f", negative),
				new Implies(new Literal("f"), new Not("g")),
				new Implies(new Literal("f"), new Literal("g")),
				new Implies(new Literal("f"), new Not(new Literal("g")))};
		
		NodeReader reader = new NodeReader();
		
		for (int i = 0; i < simpleStrings.length; i++) {
			Constraint c = new Constraint(null, reader.stringToNode(simpleStrings[i]));
			assertTrue(c.isSimple());
		}
		
		for (int i = 0; i < simpleNodes.length; i++) {
			Constraint c = new Constraint(null, simpleNodes[i]);
			assertTrue(c.isSimple());
		}
	}
	
	@Test
	public void testIsComplex() {
		String[] complex = new String[]{
				"f implies not not g",
				"(not f) implies g",
				"not (f implies g)",
				"f iff g",
				"f or g",
				"f and g",
				"not f",
				"f",
				"not f or g",
				"f or not g",
				"f implies (a and b and c and d)",
				"true",
				"false"};
		
		NodeReader reader = new NodeReader();
		
		for (int i = 0; i < complex.length; i++) {
			Constraint c = new Constraint(null, reader.stringToNode(complex[i]));
			assertFalse(c.isSimple());
		}
	}
}
