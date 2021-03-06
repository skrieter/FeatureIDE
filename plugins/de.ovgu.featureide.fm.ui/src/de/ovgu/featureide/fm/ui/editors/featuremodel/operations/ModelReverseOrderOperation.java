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
package de.ovgu.featureide.fm.ui.editors.featuremodel.operations;

import static de.ovgu.featureide.fm.core.localization.StringTable.REVERSE_LAYOUT_ORDER;

import java.util.Collections;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

import de.ovgu.featureide.fm.core.base.event.FeatureIDEEvent;
import de.ovgu.featureide.fm.core.base.event.FeatureIDEEvent.EventType;
import de.ovgu.featureide.fm.ui.editors.FeatureUIHelper;
import de.ovgu.featureide.fm.ui.editors.IGraphicalFeature;
import de.ovgu.featureide.fm.ui.editors.IGraphicalFeatureModel;

/**
 * Operation with functionality to reverse the feature model layout order.
 * Enables undo/redo functionality.
 * 
 * @author Fabian Benduhn
 * @author Marcus Pinnecke
 */
public class ModelReverseOrderOperation extends AbstractGraphicalFeatureModelOperation {

	private static final String LABEL = REVERSE_LAYOUT_ORDER;

	public ModelReverseOrderOperation(IGraphicalFeatureModel featureModel) {
		super(featureModel, LABEL);
	}

	@Override
	protected FeatureIDEEvent operation() {
		final IGraphicalFeature root = FeatureUIHelper.getGraphicalRootFeature(graphicalFeatureModel);
		Collections.reverse(FeatureUIHelper.getGraphicalChildren(root));
		if (!graphicalFeatureModel.getLayout().hasFeaturesAutoLayout()) {
			Point mid = root.getLocation().getCopy();
			mid.x += root.getSize().width / 2;
			mid.y += root.getSize().height / 2;
			mirrorFeaturePositions(root, mid, FeatureUIHelper.hasVerticalLayout(graphicalFeatureModel));
		}
		return new FeatureIDEEvent(null, EventType.LOCATION_CHANGED);
	}

	private void mirrorFeaturePositions(IGraphicalFeature feature, Point mid, boolean vertical) {
		if (!feature.getObject().getStructure().isRoot()) {
			Point featureMid = feature.getLocation().getCopy();
			Dimension size = feature.getSize().getCopy();

			if (vertical) {
				featureMid.y += size.height / 2;
				featureMid.y = 2 * mid.y - featureMid.y;
				featureMid.y -= size.height / 2;
			} else {
				featureMid.x += size.width / 2;
				featureMid.x = 2 * mid.x - featureMid.x;
				featureMid.x -= size.width / 2;
			}

			feature.setLocation(featureMid);
		}
		if (feature.getObject().getStructure().hasChildren()) {
			for (IGraphicalFeature child : FeatureUIHelper.getGraphicalChildren(feature)) {
				mirrorFeaturePositions(child, mid, vertical);
			}
		}
	}

	@Override
	protected FeatureIDEEvent inverseOperation() {
		return operation();
	}

}
