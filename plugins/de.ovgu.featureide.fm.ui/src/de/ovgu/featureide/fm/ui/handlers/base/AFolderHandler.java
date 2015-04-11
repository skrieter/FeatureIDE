/* FeatureIDE - A Framework for Feature-Oriented Software Development
 * Copyright (C) 2005-2015  FeatureIDE team, University of Magdeburg, Germany
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
package de.ovgu.featureide.fm.ui.handlers.base;

import org.eclipse.core.resources.IFolder;

/**
 * Abstract class for handlers that work on folders.
 * 
 * @author Sebastian Krieter
 */
public abstract class AFolderHandler extends ASelectionHandler {

	/**
	 * This method is called for every folder in the current selection.
	 * 
	 * @param folder the current folder handle.
	 */
	protected abstract void singleAction(IFolder folder);

	@Override
	protected void singleAction(Object element) {
		final IFolder project = (IFolder) SelectionWrapper.checkClass(element, IFolder.class);
		if (project != null) {
			singleAction(project);
		}
	}
}
