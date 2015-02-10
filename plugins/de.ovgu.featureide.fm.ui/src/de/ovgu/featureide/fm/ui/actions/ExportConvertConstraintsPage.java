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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import de.ovgu.featureide.fm.ui.actions.ExportConvertConstraintsWizard.ConversionMethod;

/**
 * Wizard Page for exporting models using ComplexConstraintConverter.
 * 
 * @author Arthur Hammer
 */
public class ExportConvertConstraintsPage extends WizardPage {
	
	private static final String LABEL_METHOD = "Method:";
	private static final String TOOLTIP_METHOD = "Which method to use when converting to a product-equivalent model without complex constraints.";
	
	private static final String COMBO_LABEL_CNF = "Conjunctive Normal Form (CNF) (recommended)";
	private static final String COMBO_LABEL_CNF_NAIVE = "CNF Naive";
	private static final String COMBO_LABEL_DNF = "Disjunctive Normal Form (DNF)";
	private static final String COMBO_LABEL_DNF_NAIVE = "DNF Naive";
	
	private IFile inputModelFile;
	private Combo comboMethod;
	protected Text fileName;
	protected ConversionMethod selectedMethod;
	
	protected ExportConvertConstraintsPage(String pageName, IFile inputFile) {
		super(pageName);
		setDescription(pageName);
		this.inputModelFile = inputFile;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout(2, false);
		layout.verticalSpacing = 9;
		composite.setLayout(layout);

		Label labelGenerate = new Label(composite, SWT.NULL);
		labelGenerate.setText(LABEL_METHOD);
		labelGenerate.setToolTipText(TOOLTIP_METHOD);
		
		comboMethod = new Combo(composite, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
		comboMethod.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comboMethod.add(COMBO_LABEL_CNF);
		comboMethod.add(COMBO_LABEL_CNF_NAIVE);
		comboMethod.add(COMBO_LABEL_DNF);
		comboMethod.add(COMBO_LABEL_DNF_NAIVE);
		comboMethod.setText(COMBO_LABEL_CNF);
		selectedMethod = ConversionMethod.CNF;

		Label fileNameLabel = new Label(composite, SWT.NULL);
		fileNameLabel.setText("File name:");
		
		Composite fileComposite = new Composite(composite, SWT.NULL);
		fileComposite.setLayout(new GridLayout(2, false));
		fileComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		fileName = new Text(fileComposite, SWT.BORDER | SWT.SINGLE);
		String modelName = inputModelFile.getLocation().removeFileExtension().toOSString();
		fileName.setText(modelName + "-simple-constraints.xml");	
		fileName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		
		Button browseButton = new Button(fileComposite, SWT.NONE);
		browseButton.setText("Browse...");
		browseButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				String selectedPath = openFileDialog();
				if (selectedPath != null) {
					fileName.setText(selectedPath);
					IPath path = new Path(selectedPath);
					if (path.getFileExtension() == null) {
						fileName.setText(selectedPath + ".xml");
					}
				}
			}
		});
		
		fileName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				checkFileName();
			}
		});
		
		comboMethod.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				ConversionMethod[] methods = new ConversionMethod[]{ConversionMethod.CNF, ConversionMethod.CNF_NAIVE, ConversionMethod.DNF, ConversionMethod.DNF_NAIVE};
				selectedMethod = methods[comboMethod.getSelectionIndex()];
			}
		});

		setControl(composite);
		checkFileName();
	}
	
	protected void checkFileName() {
		String text = fileName.getText();
		IPath path = new Path(text);
		if (path.isEmpty()) {
			updateErrorMessage("File name must be specified.");
			return;
		}
		if (!path.isValidPath(text)) {
			updateErrorMessage(text + " is no valid path.");
			return;
		}
		String fileExtension = path.getFileExtension();
		if (fileExtension == null || !fileExtension.equals("xml")) {
			updateErrorMessage("Exported model file must have xml as file extension.");
			return;
		}
		if (path.toFile().exists()) {
			updateStatusMessage("Selected file already exists. File will be overwritten.");
			return;
		}
		updateErrorMessage(null);
		updateStatusMessage(null);
	}
	
	private void updateErrorMessage(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}
	
	private void updateStatusMessage(String message) {
		setMessage(message);
		setPageComplete(true);
	}
	
	private String openFileDialog() {
		FileDialog fileDialog = new FileDialog(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), SWT.MULTI);

		fileDialog.setFileName("simple-constraints.xml");
		fileDialog.setFilterExtensions(new String[] { "*.xml" });
		fileDialog.setOverwrite(true);
		fileDialog.setFilterPath(inputModelFile.getProject().getLocation().toOSString());
		return fileDialog.open();
	}
}
