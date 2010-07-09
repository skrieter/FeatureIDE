/* FeatureIDE - An IDE to support feature-oriented software development
 * Copyright (C) 2005-2010  FeatureIDE Team, University of Magdeburg
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see http://www.gnu.org/licenses/.
 *
 * See http://www.fosd.de/featureide/ for further information.
 */
package de.ovgu.featureide.ui.views.collaboration.editparts;

import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import de.ovgu.featureide.ui.views.collaboration.figures.ClassFigure;
import de.ovgu.featureide.ui.views.collaboration.model.Class;
import de.ovgu.featureide.ui.views.collaboration.policy.ClassXYLayoutPolicy;



/**
 * EditPart for Classes
 * 
 * @author Constanze Adler
 */
public class ClassEditPart extends AbstractGraphicalEditPart {

	public ClassEditPart(Class c){
		super();
		setModel(c);	
	}
	
	public Class getClassModel(){
		return (Class) getModel();
	}
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		
		List<?> children = ((ModelEditPart) getParent()).getChildren();
		int count = 0;
		int height=0;
		for (Object o : children){
			if (o instanceof CollaborationEditPart){
				count++;
				height = ((CollaborationEditPart)o).getFigure().getSize().height+8;
			}
		}
		Figure fig = new ClassFigure(getClassModel(),height * (count+1));
		return fig;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ClassXYLayoutPolicy());
	}
	@Override
	protected List<?> getModelChildren(){		
		
		Class c = getClassModel();
		List<?> list = c.getRoles();
		return list;
		
	}
	protected void refreshVisuals() {
		ClassFigure classFigure = (ClassFigure) getFigure();
		Point location = classFigure.getBounds().getLocation();
		Dimension size = classFigure.getBounds().getSize();
		Rectangle constraint = new Rectangle(location, size);
		ModelEditPart modelEditPart = (ModelEditPart) getParent();
		List<?> children = modelEditPart.getChildren();
		int width = 0;
		for (Object o : children){
			if (o instanceof CollaborationEditPart){
				int newWidth = ((CollaborationEditPart) o).getFigure().getSize().width;
				width = (width> newWidth)? width : newWidth; 
			}
		}
		if (children.contains(this)){
			int i = children.indexOf(this);
			EditPart part = (EditPart) children.get(i-1);
			if (part instanceof CollaborationEditPart){
				Point location2 = ((CollaborationEditPart) part).getFigure().getBounds().getLocation();
				Point newLocation = new Point(location2.x + width + 10,location.y);
				constraint = new Rectangle(newLocation, size);
			}
			if (part instanceof ClassEditPart){
				Dimension size2 = ((ClassEditPart) part).getFigure().getBounds().getSize();
				Point location2 = ((ClassEditPart) part).getFigure().getBounds().getLocation();
				Point newLocation = new Point(location2.x + size2.width + 5,location2.y);
				constraint = new Rectangle(newLocation, size);
			}
			
			
		}
	
		modelEditPart.setLayoutConstraint(this, classFigure, constraint);
		classFigure.setBounds(constraint);
	}

	

}