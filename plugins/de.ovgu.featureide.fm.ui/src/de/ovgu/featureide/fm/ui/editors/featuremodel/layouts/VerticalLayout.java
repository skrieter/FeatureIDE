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
package de.ovgu.featureide.fm.ui.editors.featuremodel.layouts;

import java.util.Iterator;
import java.util.LinkedList;

import org.eclipse.draw2d.geometry.Point;

import de.ovgu.featureide.fm.core.Feature;
import de.ovgu.featureide.fm.core.FeatureModel;
import de.ovgu.featureide.fm.ui.editors.FeatureUIHelper;
import de.ovgu.featureide.fm.ui.properties.FMPropertyManager;

/**
 * Ordering features from left to right without any intersections or overlapping
 * 
 * @author David Halm
 * @author Patrick Sulkowski
 * @author Sebastian Krieter
 */
public class VerticalLayout extends FeatureDiagramLayoutManager {
	
	private int height = 0;
	private int yOffset = 0;

	public void layoutFeatureModel(FeatureModel featureModel) {
		centerOther(featureModel.getRoot(), 0);
		layout(yOffset, featureModel.getConstraints());
	}

	/**
	 * positions of features that have children are now set from right to left (for each level)
	 * (centered by their children's positions
	 */
	private int centerOther(Feature parent, int level) {
		final LinkedList<Feature> children = parent.getChildren();
		if (children.isEmpty()) {			
			height += FMPropertyManager.getFeatureSpaceY();
			FeatureUIHelper.setLocation(parent, new Point((level * 200) + 20, height));
			return height;
		} else {
			Iterator<Feature> it = children.iterator();
			final int min = centerOther(it.next(), level + 1);
			int max = min;
			while (it.hasNext()) {
				max = centerOther(it.next(), level + 1);
			}
			height += 10;
			final int yPos = (min + max) >> 1;
			FeatureUIHelper.setLocation(parent, new Point((level * 200) + 20, yPos));
			return yPos;
		}
	}

}
