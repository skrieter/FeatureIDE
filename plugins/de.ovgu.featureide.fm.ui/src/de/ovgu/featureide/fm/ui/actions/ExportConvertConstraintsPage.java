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
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
	
	private static final String METHOD_LABEL = "Method:";
	private static final String METHOD_TOOLTIP = "Which method to use when converting to a product-equivalent model without complex constraints.";
	
	private static final String COMBO_CNF_LABEL = "Conjunctive Normal Form (CNF) (recommended)";
	private static final String COMBO_CNF_NAIVE_LABEL = "CNF Naive";
	private static final String COMBO_DNF_LABEL = "Disjunctive Normal Form (DNF)";
	private static final String COMBO_DNF_NAIVE_LABEL = "DNF Naive";
	
	private static final String REDUCE_CONFIGS_LABEL = "Reduce configurations:";
	private static final String REDUCE_CONFIGS_TOOLTIP = "Whether to attempt to reduce the number of configurations. May result in larger number of constraints. (Only for CNF methods)";
	private static final String PRESERVE_CONFIGS_LABEL = "Preserve configurations:";
	private static final String PRESERVE_CONFIGS_TOOLTIP = "Whether to preserve the exact number of configurations. May result in large number of additional features and constraints. (Only for CNF methods)";
	
	
	
	private IFile inputModelFile;
	private Combo methodCombo;
	
	protected Text fileName;
	protected ConversionMethod selectedMethod;
	protected boolean reduceConfigurations = false;
	protected boolean preserveConfigurations = false;
	
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
		labelGenerate.setText(METHOD_LABEL);
		labelGenerate.setToolTipText(METHOD_TOOLTIP);
		
		methodCombo = new Combo(composite, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
		methodCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		methodCombo.add(COMBO_CNF_LABEL);
		methodCombo.add(COMBO_CNF_NAIVE_LABEL);
		methodCombo.add(COMBO_DNF_LABEL);
		methodCombo.add(COMBO_DNF_NAIVE_LABEL);
		methodCombo.setText(COMBO_CNF_LABEL);
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
		
		final Label reduceConfigsLabel = new Label(composite, SWT.NULL);
		reduceConfigsLabel.setText(REDUCE_CONFIGS_LABEL);
		reduceConfigsLabel.setToolTipText(REDUCE_CONFIGS_TOOLTIP);
		final Button reduceConfigsButton = new Button(composite, SWT.CHECK);
		reduceConfigsButton.setToolTipText(REDUCE_CONFIGS_TOOLTIP);
		reduceConfigsButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		reduceConfigsButton.setSelection(reduceConfigurations);
		
		final Label preserveConfigsLabel = new Label(composite, SWT.NULL);
		preserveConfigsLabel.setText(PRESERVE_CONFIGS_LABEL);
		preserveConfigsLabel.setToolTipText(PRESERVE_CONFIGS_TOOLTIP);
		final Button preserveConfigsButton = new Button(composite, SWT.CHECK);
		preserveConfigsButton.setToolTipText(PRESERVE_CONFIGS_TOOLTIP);
		preserveConfigsButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		preserveConfigsButton.setSelection(reduceConfigurations);
		
		// Add listeners
		methodCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				ConversionMethod[] methods = new ConversionMethod[]{ConversionMethod.CNF, ConversionMethod.CNF_NAIVE, ConversionMethod.DNF, ConversionMethod.DNF_NAIVE};
				int selection = methodCombo.getSelectionIndex();
				selectedMethod = methods[selection];
				boolean useCNF = selection < 2;
				reduceConfigsButton.setEnabled(useCNF); 
				reduceConfigsLabel.setEnabled(useCNF);
				preserveConfigsButton.setEnabled(useCNF);
				preserveConfigsLabel.setEnabled(useCNF);
			}
		});
		
		fileName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				checkFileName();
			}
		});
		
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
		
		reduceConfigsButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				reduceConfigurations = reduceConfigsButton.getSelection();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		preserveConfigsButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				preserveConfigurations = preserveConfigsButton.getSelection();
				reduceConfigsButton.setEnabled(!preserveConfigurations);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
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
