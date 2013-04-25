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
package de.ovgu.featureide.fm.ui.actions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import de.ovgu.featureide.fm.core.FeatureModel;
import de.ovgu.featureide.fm.core.io.FeatureModelReaderIFileWrapper;
import de.ovgu.featureide.fm.core.io.UnsupportedModelException;
import de.ovgu.featureide.fm.core.io.velvet.VelvetFeatureModelWriter;
import de.ovgu.featureide.fm.core.io.xml.XmlFeatureModelReader;
import de.ovgu.featureide.fm.ui.FMUIPlugin;

/**
 * writes the content of a feature model xml file 
 * to a velvet model file
 * 
 * @author Sebastian Krieter
 */
public class ExportVelvetAction implements IObjectActionDelegate {

	private ISelection selection;
	
	@Override
	public void run(IAction action) {
		if (selection instanceof IStructuredSelection) {
			for (Iterator<?> it = ((IStructuredSelection) selection).iterator(); 
					it.hasNext();) {
				Object element = it.next();
				IFile inputFile = null;
				if (element instanceof IFile) {
					inputFile = (IFile) element;
				} else if (element instanceof IAdaptable) {
					inputFile = (IFile) ((IAdaptable) element).getAdapter(
							IFile.class);
				}
				if (inputFile != null) {
					try {
						FileDialog fileDialog = new FileDialog(new Shell(), 
								SWT.SAVE);
						fileDialog.setFileName("model.velvet");
						fileDialog.setOverwrite(true);
						String filepath = fileDialog.open();
						if (filepath == null) return;
						File outputFile = new File(filepath);
						FeatureModel fm = new FeatureModel();	
						FeatureModelReaderIFileWrapper fmReader = new FeatureModelReaderIFileWrapper(new XmlFeatureModelReader(fm));
						fmReader.readFromFile(inputFile);
						VelvetFeatureModelWriter velvetWriter = new VelvetFeatureModelWriter(fm);
						velvetWriter.writeToFile(outputFile);
						inputFile.getProject().refreshLocal(
								IResource.DEPTH_INFINITE, null);  
					} catch (FileNotFoundException e) {
						FMUIPlugin.getDefault().logError(e);
					} catch (UnsupportedModelException e) {
						FMUIPlugin.getDefault().logError(e);
					} catch (CoreException e) {
						FMUIPlugin.getDefault().logError(e);
					}				
				}
			}
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}
	
}