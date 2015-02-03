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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import de.ovgu.featureide.fm.core.FeatureModel;
import de.ovgu.featureide.fm.core.conversion.ComplexConstraintConverter;
import de.ovgu.featureide.fm.core.conversion.ComplexConstraintConverterCNF;
import de.ovgu.featureide.fm.core.conversion.ComplexConstraintConverterDNF;
import de.ovgu.featureide.fm.core.io.FeatureModelReaderIFileWrapper;
import de.ovgu.featureide.fm.core.io.UnsupportedModelException;
import de.ovgu.featureide.fm.core.io.xml.XmlFeatureModelReader;
import de.ovgu.featureide.fm.core.io.xml.XmlFeatureModelWriter;
import de.ovgu.featureide.fm.ui.FMUIPlugin;


/**
 * Wizard for exporting models using ComplexConstraintConverter.
 * 
 * @author Arthur Hammer
 */
public class ExportConvertConstraintsWizard extends Wizard implements INewWizard {

	protected static enum ConversionMethod {
		CNF, CNF_NAIVE, DNF, DNF_NAIVE;
	}

	private IFile inputModelFile; 
	private ExportConvertConstraintsPage page;
	
	public ExportConvertConstraintsWizard(IFile inputFile) {
		this.inputModelFile = inputFile;
	}

	@Override
	public void addPages() {
		setWindowTitle("Export Product-Equivalent Model Without Complex Constraints");
		page = new ExportConvertConstraintsPage("Export feature model to a product-equivalent model including only simple requires and excludes constraints.", inputModelFile);
		addPage(page);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		final ConversionMethod method = page.selectedMethod;
		final String outputPath = page.fileName.getText();
		String jobName = "Exporting product-equivalent model with simple constraints (" + inputModelFile.getName() +")";

		// TODO: AStoppableJob? (how to auto-cancel/kill?)	
		Job job = new Job(jobName) {
			protected IStatus run(IProgressMonitor monitor) {
				FeatureModel model = readModel(inputModelFile);
				ComplexConstraintConverter converter;
				FeatureModel result;
				
				switch(method) {
				case CNF:
					converter = new ComplexConstraintConverterCNF();
					result = converter.convert(model);
					break;
				case CNF_NAIVE:
					converter = new ComplexConstraintConverterCNF();
					result = converter.convertNaive(model);
					break;
				case DNF:
					converter = new ComplexConstraintConverterDNF();
					result = converter.convert(model);
					break;
				case DNF_NAIVE:
					converter = new ComplexConstraintConverterDNF();
					result = converter.convertNaive(model);
					break;
				default:
					return Status.CANCEL_STATUS;
				}
				
				XmlFeatureModelWriter writer = new XmlFeatureModelWriter(result);
				writer.writeToFile(new File(outputPath));
				
				try {
					// TODO: Refresh if result is saved outside of input model project?
					inputModelFile.getProject().refreshLocal(IResource.DEPTH_INFINITE, monitor);
				} catch (CoreException e) {
					FMUIPlugin.getDefault().logError(e);
				}
				return Status.OK_STATUS;
			}
		};

		job.setPriority(Job.INTERACTIVE);
		job.schedule();
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		
	}

	/**
	 * reads the featureModel from file
	 * 
	 * @param inputFile
	 * @return featureModel
	 * @throws UnsupportedModelException
	 * @throws FileNotFoundException
	 */
	private FeatureModel readModel(IFile inputFile) {
		FeatureModel fm = new FeatureModel();
		FeatureModelReaderIFileWrapper fmReader = new FeatureModelReaderIFileWrapper(
				new XmlFeatureModelReader(fm));

		try {
			fmReader.readFromFile(inputFile);
		} catch (FileNotFoundException e) {
			FMUIPlugin.getDefault().logError(e);
		} catch (UnsupportedModelException e) {
			FMUIPlugin.getDefault().logError(e);
		}
		return fm;
	}
}
