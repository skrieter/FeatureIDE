/* FeatureIDE - A Framework for Feature-Oriented Software Development
 * Copyright (C) 2005-2016  FeatureIDE team, University of Magdeburg, Germany
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
 * See http://featureide.cs.ovgu.de/ for further information.
 */
package org.prop4j.analyses;

import org.prop4j.solver.ISolverProvider;
import org.sat4j.specs.IProblem;
import org.sat4j.tools.ModelIterator;

import de.ovgu.featureide.fm.core.job.WorkMonitor;

/**
 * Finds a solution.
 * 
 * @author Sebastian Krieter
 */
public class FindSolutionAnalysis extends SingleThreadAnalysis<String> {

	public FindSolutionAnalysis(ISolverProvider solver) {
		super(solver);
	}

	public String execute(WorkMonitor monitor) throws Exception {
		StringBuilder out = new StringBuilder();
		IProblem problem = new ModelIterator(solver.getSolver());
		if (!problem.isSatisfiable())
			return null;
		final int[] model = problem.model();

		for (int var : model) {
			if (var > 0) {
				out.append(solver.getSatInstance().getVariableObject(var) + "\n");
			}
		}
		return out.toString();
	}

}
