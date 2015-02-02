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
import org.prop4j.And;
import org.prop4j.Equals;
import org.prop4j.Literal;
import org.prop4j.Node;
import org.prop4j.NodeReader;
import org.prop4j.NormalForms;
import org.prop4j.Not;
import org.prop4j.Or;
import org.prop4j.SatSolver;
import org.sat4j.specs.TimeoutException;

/**
 * Tests for class NormalForms, generation of CNF and DNF. 
 * 
 * @author Arthur Hammer
 */
@RunWith(Parameterized.class)
public class NormalFormsTest {
	
	private static final String[] formulas = new String[]{
		"a", "a and b", "a and a", "a or b", "a or a", "a and a and a", 
		"a and not a", "a or not b", "a and not a and a",
		"not (a and not a) or not (a and not a) and (a or not a) and (b and not b)",
		"(a or b) and (a or b) and (a or b or not b) and (a or d or c)",
		"a implies d iff (x and d or e)",
		"a iff d or (c iff (e implies a)) and d",
		"(a and b) or not (a and b)",
		"(a or b) and not (a or b)",
		"(a and not b) or (not a and b)",
		"(a or not b) and (not a or b)",
		"A1 and (A1 iff A2) and (A2 implies C3 or C5 and C6 and C7) and (C3 or C4 or C5 or C6 or C7 or A8 or C9 implies A2) or (A8 iff C10) and not  (C5 iff C3 iff not  A2)",
		"Root and (Root iff A or B or C or D or E) and (A iff A1 or A2) and (A1 implies A11) and (A22 or (E12 and C iff D)) and E11 and not E13 and (A iff E1 iff C)",
	};
	
	private static final Node TRUE = new Or(new Literal("a"), new Not(new Literal("a")));
	private static final Node FALSE = new And(new Literal("a"), new Not(new Literal("a")));
	
	private Node node;
	
	public NormalFormsTest(Node node) {
		this.node = node;
	}
	
	@Parameters(name = "{0}")
	public static Collection<Object[]> getModels() {
		Collection<Object[]> params = new ArrayList<Object[]>();
		NodeReader reader = new NodeReader();
		
		for (int i = 0; i < formulas.length; i++) {
			System.out.println(formulas[i]);
			System.out.println(reader.stringToNode(formulas[i]));
			params.add(new Object[]{reader.stringToNode(formulas[i])});
		}
		
		return params;
	}
	
	@Test
	public void testToCNFEquivalent() throws TimeoutException {
		Node cnf = NormalForms.toCNF(node.clone());
		if (cnf == null) {
			cnf = TRUE;
		}
		
		Node test = new Not(new Equals(cnf, node));
		SatSolver solver = new SatSolver(test, 10000);
		assertFalse(solver.isSatisfiable());
	}
	
	@Test
	public void testToDNFEquivalent() throws TimeoutException {
		Node dnf = NormalForms.toDNF(node.clone());
		if (dnf == null) {
			dnf = FALSE;
		}
		
		Node test = new Not(new Equals(dnf, node));
		SatSolver solver = new SatSolver(test, 10000);
		assertFalse(solver.isSatisfiable());
	}
	
	@Test
	public void testToNNFEquivalent() throws TimeoutException {
		Node nnf = NormalForms.toNNF(node.clone());
		Node test = new Not(new Equals(nnf, node));
		SatSolver solver = new SatSolver(test, 10000);
		assertFalse(solver.isSatisfiable());
	}
	
	@Test
	public void testToCNFStructure() throws TimeoutException {
		Node cnf = NormalForms.toCNF(node.clone());
		// Always true passes
		if (cnf == null) {
			return;
		}

		assertTrue(cnf instanceof And || cnf instanceof Literal);
		
		// Check CNF = conjunction of disjunctions of literals
		if (cnf instanceof And) {
			assertNotNull(cnf.getChildren());
			assertNotEquals(cnf.getChildren().length, 0);
			
			for (Node clause : cnf.getChildren()) {
				assertTrue(clause instanceof Or || clause instanceof Literal);
				if (clause instanceof Or) {
					assertNotNull(clause.getChildren());
					assertNotEquals(clause.getChildren().length, 0);

					for (Node literal : clause.getChildren()) {
						assertTrue(literal instanceof Literal);
					}
				}
			}
		}
	}
	
	@Test
	public void testToDNFStructure() throws TimeoutException {
		Node dnf = NormalForms.toDNF(node.clone());
		// Always false passes
		if (dnf == null) {
			return;
		}

		assertTrue(dnf instanceof Or || dnf instanceof Literal);
		
		// Check DNF = disjunction of conjunctions of literals
		if (dnf instanceof Or) {
			assertNotNull(dnf.getChildren());
			assertNotEquals(dnf.getChildren().length, 0);
			
			for (Node clause : dnf.getChildren()) {
				assertTrue(clause instanceof And || clause instanceof Literal);
				if (clause instanceof And) {
					assertNotNull(clause.getChildren());
					assertNotEquals(clause.getChildren().length, 0);

					for (Node literal : clause.getChildren()) {
						assertTrue(literal instanceof Literal);
					}
				}
			}
		}
	}
}
