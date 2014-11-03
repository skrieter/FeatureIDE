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
package de.ovgu.featureide.ui.views.collaboration.filter;

import org.eclipse.jface.action.IAction;

/**
 * TODO description
 * 
 * @author Christopher Kruczek
 */
public enum Filter {
	
	SHOW_FIELDS(new FilterAction("Show Fields")),
	SHOW_METHODS(new FilterAction("Show Methods")),
	SHOW_METHOD_CONTRACTS(new FilterAction("Show Method Contracts")),
	SHOW_CLASS_INVARIANTS(new FilterAction("Show Class Invariants")),
	HIDE_PARAMETERS(new FilterAction("Hide Parameter/Types")),
	PUBLIC(new FilterAction("Public")),
	PRIVATE(new FilterAction("Private")),
	PROTECTED(new FilterAction("Protected")),
	DEFAULT(new FilterAction("Default")),
	SELECT_ALL(new FilterAction("Select All")),
	DESELECT_ALL(new FilterAction("Deselect All"));
	
	private IAction filterAction;
	
	Filter(IAction filterAction){
		this.filterAction = filterAction;
		
	}
	
	public IAction getFilterAction(){
		return this.filterAction;
	}
	
	
}
