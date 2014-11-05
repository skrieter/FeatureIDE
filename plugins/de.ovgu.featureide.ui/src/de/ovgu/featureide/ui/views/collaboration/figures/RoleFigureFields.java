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
package de.ovgu.featureide.ui.views.collaboration.figures;

import java.util.List;

import org.eclipse.draw2d.Figure;

import de.ovgu.featureide.core.fstmodel.FSTField;
import de.ovgu.featureide.ui.views.collaboration.GUIDefaults;
import de.ovgu.featureide.ui.views.collaboration.filter.IFilterTriggerListener;

/**
 * TODO description
 * 
 * @author Christopher Kruczek
 */
public class RoleFigureFields extends Figure implements GUIDefaults, IFilterTriggerListener {
	private List<FSTField> fields;
	
	public RoleFigureFields(List<FSTField> fields){
		this.fields = fields;
	}
	@Override
	public void filterTriggered() {
		
	}
	
}
