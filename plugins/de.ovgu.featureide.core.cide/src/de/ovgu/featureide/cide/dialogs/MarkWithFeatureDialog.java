package de.ovgu.featureide.cide.dialogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

import de.ovgu.featureide.core.CorePlugin;
import de.ovgu.featureide.core.IFeatureProject;

/*
 * show all features of the feature model at the dialog
 */

public class MarkWithFeatureDialog {

	public ArrayList<String> open(ITextEditor activeEditor) {
		Shell shell = null;
		Vector<String> featureList = new Vector<String>();
		IFeatureProject featureProject = null;
		ArrayList<String> returnFeatures = new ArrayList<String>();
		if (activeEditor != null) {
			IFile inputFile = ((FileEditorInput) activeEditor.getEditorInput()).getFile();
			featureProject = CorePlugin.getFeatureProject(inputFile);
		}
		if (featureProject != null) {
			for (String feature : featureProject.getFeatureModel().getConcreteFeatureNames()) {
				featureList.add(feature);
			}
		}
		ListSelectionDialog dialog = new ListSelectionDialog(shell, featureList, ArrayContentProvider.getInstance(), new LabelProvider(), "Choose feature(s)");
		dialog.setTitle("FeatureDialog");
		if (dialog.open() == Window.OK) {
			System.out.println("Selected feature: " + Arrays.toString(dialog.getResult()));
			if (dialog.getResult().length > 0) {
				Object array[] = dialog.getResult();
				for (int i = 0; i < array.length; i++) {
					returnFeatures.add((String) array[i]);
				}
			
				return returnFeatures;
			}
		}
		return null;
	}

}
