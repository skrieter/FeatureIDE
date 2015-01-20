package de.ovgu.featureide.core.cide;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.dialogs.ListDialog;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

import de.ovgu.featureide.core.CorePlugin;
import de.ovgu.featureide.core.IFeatureProject;

//import de.ovgu.featureide.ui.editors.annotation.ColorAnnotationModel;

public class MarkWithFeatureAction implements IEditorActionDelegate,
		IViewActionDelegate {

	protected List<String> featureNames = new LinkedList<String>();

	public ITextEditor activeEditor = null;
	Shell parentShel = null;
	ListDialog listDialog = new ListDialog(parentShel);

	public void run(IAction action) {
		
		ColorAnnotationManager colorAnnotationManager = new ColorAnnotationManager(); 
		ISelectionProvider selectionProvider = activeEditor .getSelectionProvider(); 
		ISelection selection = selectionProvider.getSelection();
		colorAnnotationManager.colorXmlWriter(selection, activeEditor); 
		
		
		// Create ListDialog and print the selected feature
		listDialog.setTitle("FeatureDialog");
		listDialog.setMessage("Feature wählen");
		listDialog.setContentProvider(ArrayContentProvider.getInstance());
		listDialog.setLabelProvider(new LabelProvider());

		Vector<String> featureList = new Vector<String>();

		IFeatureProject featureProject = null;
		if (activeEditor != null) {
			IFile inputFile = ((FileEditorInput) activeEditor.getEditorInput()).getFile();
			featureProject = CorePlugin.getFeatureProject(inputFile);
		}
		if (featureProject != null) {
			featureNames = featureProject.getFeatureModel().getConcreteFeatureNames();
		}

		for (String feature : featureNames) {
			featureList.add(feature);
		}

		listDialog.setInput(featureList);
		if (listDialog.open() == Dialog.OK) {
			
			System.out.println("Selected feature: " + Arrays.toString(listDialog.getResult()));
		}

	}


	public void selectionChanged(IAction action, ISelection selection) {
	}

	public void init(IViewPart view) {
	}

	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if (targetEditor instanceof ITextEditor) {
			activeEditor = (ITextEditor) targetEditor;
		}
	}

}
