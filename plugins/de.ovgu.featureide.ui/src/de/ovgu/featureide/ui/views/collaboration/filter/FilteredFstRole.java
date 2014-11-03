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

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import de.ovgu.featureide.core.fstmodel.*;
/**
 * TODO description
 * 
 * @author Christopher Kruczek
 */
public class FilteredFstRole {
	private List<FSTMethod> privateMethods = new ArrayList<FSTMethod>();
	private List<FSTMethod> publicMethods = new ArrayList<FSTMethod>();
	private List<FSTField> privateFields= new ArrayList<FSTField>();
	private List<FSTField> publicFields = new ArrayList<FSTField>();
	private EnumSet<Filter> selectedFilter;
	private FSTRole role;
	
	public FilteredFstRole(EnumSet<Filter> filter,FSTRole role) {
		this.selectedFilter = filter;
		this.role = role;
	}
	
	public List<FSTMethod> getPrivateMethods() {
		return privateMethods;
	}
	public List<FSTMethod> getPublicMethods() {
		return publicMethods;
	}
	public List<FSTField> getPrivateFields() {
		return privateFields;
	}
	public List<FSTField> getPublicFields() {
		return publicFields;
	}
	
}
