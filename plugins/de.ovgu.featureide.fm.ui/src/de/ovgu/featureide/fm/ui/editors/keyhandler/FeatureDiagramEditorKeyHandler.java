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
package de.ovgu.featureide.fm.ui.editors.keyhandler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.events.KeyEvent;

import de.ovgu.featureide.fm.core.Feature;
import de.ovgu.featureide.fm.core.FeatureModel;
import de.ovgu.featureide.fm.ui.editors.FeatureDiagramEditor;
import de.ovgu.featureide.fm.ui.editors.featuremodel.editparts.FeatureEditPart;
import de.ovgu.featureide.fm.ui.editors.featuremodel.editparts.ModelEditPart;

/**
 * The KeyHandler for the FeatureDiagramEditor.
 * </br>
 * At Manual-Layout: </br>
 * to ensure that actions registered in {@link #createKeyBindings()} will be handled first! default actions will be handled at last!
 * 
 * Handles searching of features in the Tree.
 * </br>
 * At Automatic-Layout: run {@link GraphicalViewerKeyHandler} first
 * 
 * @author Guenter Ulreich
 * @author Andy Koch
 */
public class FeatureDiagramEditorKeyHandler extends KeyHandler implements PropertyChangeListener {

	private static final long timeoutThreshold = 1000;
	private final FeatureModel featureModel;
	private final GraphicalViewerKeyHandler gvKeyHandler;
	private final KeyHandler alternativeKeyHandler;
	private final FeatureDiagramEditor viewer;

	private final ArrayList<String> featureList = new ArrayList<String>();

	private int curIndex;
	private String curSearchString;
	private long lastTime;

	/**
	 * alternativeKeyHandler handles the KeyEvents, if the
	 * GraphicalViewerKeyHandler is active for auto-layout
	 */
	public FeatureDiagramEditorKeyHandler(FeatureDiagramEditor view, FeatureModel featureModel) {
		super();

		this.featureModel = featureModel;
		this.viewer = view;

		this.alternativeKeyHandler = new KeyHandler();
		this.gvKeyHandler = new GraphicalViewerKeyHandler(view);
		this.gvKeyHandler.setParent(alternativeKeyHandler);
		this.setParent(gvKeyHandler);
		this.lastTime = 0;

		resetFeatureList();
		featureModel.addListener(this);
	}

	/**
	 * use {@link GraphicalViewerKeyHandler} first if auto-layout is active handles
	 * the searching on the graph (depth-first, same way as in Outline)
	 */
	@Override
	public boolean keyPressed(KeyEvent e) {
		if (Character.isISOControl(e.character)) {
			if (featureModel.getLayout().hasFeaturesAutoLayout()) {
				return gvKeyHandler.keyPressed(e);
			} else {
				return super.keyPressed(e);
			}
		}

		final long currentTime = System.currentTimeMillis();
		if (currentTime - lastTime > timeoutThreshold) {
			curSearchString = "";
		}
		lastTime = currentTime;

		curIndex = updateIterator();

		if (curSearchString.length() == 1 && curSearchString.charAt(0) == Character.toLowerCase(e.character)) {
			curSearchString = "";
			curIndex = (curIndex + 1) % featureList.size();
		}
		curSearchString += Character.toLowerCase(e.character);

		final int foundIndex = search();
		if (foundIndex >= 0) {
			// select the new feature
			final Feature curFeature = featureModel.getFeature(featureList.get(foundIndex));
			if (curFeature != null) {
				FeatureEditPart part = (FeatureEditPart) viewer.getEditPartRegistry().get(curFeature);
				viewer.setSelection(new StructuredSelection(part));
				viewer.reveal(part);
				curIndex = foundIndex;
			}
		}

		return true;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		resetFeatureList();
	}

	/**
	 * To handle 2 key handlers (otherwise there would be an action loop)</br> {@inheritDoc}
	 */
	@Override
	public void put(KeyStroke keystroke, IAction action) {
		this.alternativeKeyHandler.put(keystroke, action);
		super.put(keystroke, action);
	}

	private void resetFeatureList() {
		featureList.clear();
		featureList.addAll(featureModel.getFeatureNamesPreorder());
		curSearchString = "";
		curIndex = 0;
	}

	private int search() {
		for (int i = curIndex, end = curIndex + featureList.size(); i < end; i++) {
			final String name = featureList.get(i % featureList.size());
			if (name.toLowerCase().startsWith(curSearchString)) {
				return i % featureList.size();
			}
		}
		return -1;
	}

	private int updateIterator() {
		final IStructuredSelection sel = (IStructuredSelection) viewer.getSelection();

		if (sel.size() == 1 && !(sel.getFirstElement() instanceof ModelEditPart)) {
			final Object element = sel.getFirstElement();
			final String featureName;

			if (element instanceof FeatureEditPart) {
				featureName = ((FeatureEditPart) element).getFeature().getName();
			} else if (element instanceof Feature) {
				featureName = ((Feature) element).getName();
			} else {
				return 0;
			}

			return (!featureName.equalsIgnoreCase(featureList.get(curIndex))) ? featureList.indexOf(featureName) : curIndex;
		}
		return 0;
	}

}
