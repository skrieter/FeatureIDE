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

import org.prop4j.Implies;
import org.prop4j.Node;
import org.prop4j.Not;

import de.ovgu.featureide.fm.core.Constraint;
import de.ovgu.featureide.fm.core.Feature;

/**
 * A class to convert feature models with arbitrary cross-tree constraints
 * into models with only simple constraints but describing the same set of 
 * product variants. 
 * 
 * Uses the Conjunctive Normal Form (CNF) when converting.
 * 
 * @author Arthur Hammer
 */
public class ComplexConstraintConverterCNF extends ComplexConstraintConverter {
	
	private boolean useEquivalence;
	
	public ComplexConstraintConverterCNF() {
		this(true);
	}
	
	public ComplexConstraintConverterCNF(boolean useEquivalence) {
		this.useEquivalence = useEquivalence;
	}
	
	public void setUseEquivalence(boolean useEquivalence) {
		this.useEquivalence = useEquivalence;
	}
	
	public boolean getUseEquivalence() {
		return useEquivalence;
	}
	
	@Override
	protected void addSimpleConstraint(Feature f, Feature g, boolean requires) {
		super.addSimpleConstraint(f, g, requires);
		
		// Reduces number of configurations, adds more constraints. 
		// Not applicable for exclude constraints.
		if (requires && useEquivalence) {
			Node implies = new Implies((requires ? g.getName() : new Not(g.getName())), f.getName());
			fm.addConstraint(new Constraint(fm, implies));
		}
	}
}
