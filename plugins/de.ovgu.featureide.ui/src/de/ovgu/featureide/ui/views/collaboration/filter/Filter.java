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

/**
 * TODO description
 * 
 * @author Christopher Kruczek
 */
public enum Filter {
	
	SHOW_FIELDS("Show Fields"),
	SHOW_METHODS("Show Methods"),
	SHOW_METHOD_CONTRACTS("Show Method Contracts"),
	SHOW_CLASS_INVARIANTS("Show Class Invariants"),
	HIDE_PARAMETERS("Hide Parameter/Types"),
	PUBLIC("Public"),
	PRIVATE("Private"),
	PROTECTED("Protected"),
	DEFAULT("Default"),
	SELECT_ALL("Select All"),
	DESELECT_ALL("Deselect All");
	
	private String filterName;
	
	Filter(String name){
		filterName = name;
	}
	
	public String getName(){
		return filterName;
	}
	
	
}
