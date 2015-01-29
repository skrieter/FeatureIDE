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

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.prop4j.Implies;
import org.prop4j.Literal;
import org.prop4j.Node;
import org.prop4j.NodeReader;
import org.prop4j.Not;

import de.ovgu.featureide.fm.core.Constraint;


/**
 * Tests for class Constraint and method "isSimple". 
 * 
 * Note: In part, tests depend on correctness and parsed Node structure of NodeReader.
 * 
 * @author Arthur Hammer
 */
@RunWith(Parameterized.class)
public class ConstraintTest {
	
	// Constraints and expected test result
	private static final Object[] constraints = new Object[]{
			"f implies g", true,
			"f implies not g", true, 
			"a implies b", true,
			"a implies not b", true,
			"f implies not not g", false,
			"(not f) implies g", false,
			"not (f implies g)", false,
			"f iff g", false,
			"f or g", false,
			"f and g", false,
			"not f", false,
			"f", false,
			"not f or g", false,
			"f or not g", false,
			"f implies (a and b and c and d)", false,
			"true", false,
			"false", false,
			new Implies("f", "g"), true,
			new Implies("f", new Not("g")), true,
			new Implies("f", new Literal("g")), true,
			new Implies("f", new Not(new Literal("g"))), true,
			new Implies(new Literal("f"), new Not("g")), true,
			new Implies(new Literal("f"), new Literal("g")), true,
			new Implies(new Literal("f"), new Not(new Literal("g"))), true
	};
	
	private Constraint constraint;
	private boolean expected;
	
	public ConstraintTest(Constraint constraint, boolean expected) {
		this.constraint = constraint;
		this.expected = expected;
	}
	
	@Parameters(name = "{0}")
	public static Collection<Object[]> getModels() {
		Collection<Object[]> params = new ArrayList<Object[]>();
		NodeReader reader = new NodeReader();
		
		for (int i = 0; i < constraints.length; i += 2) {
			if (constraints[i] instanceof String) {
				constraints[i] = reader.stringToNode((String) constraints[i]); 
			}
			
			Constraint constraint = new Constraint(null, (Node) constraints[i]);
			params.add(new Object[]{constraint, constraints[i+1]});
		}
		
		// 1 more test case for negative literal
		Literal negative = new Literal("g");
		negative.positive = false;
		params.add(new Object[]{new Constraint(null, new Implies("f", negative)), true});
		
		return params;
	}
	
	@Test
	public void testIsSimple() {
		assertEquals(constraint.isSimple(), expected);
	}
}
