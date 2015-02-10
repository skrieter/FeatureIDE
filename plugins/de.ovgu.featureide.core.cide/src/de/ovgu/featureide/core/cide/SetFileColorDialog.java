package de.ovgu.featureide.core.cide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListSelectionDialog;

import de.ovgu.featureide.core.CorePlugin;
import de.ovgu.featureide.core.IFeatureProject;

public class SetFileColorDialog {

	public ArrayList<String> open(IFile file) {
		
		
		Shell shell = null;
		Vector<String> featureList = new Vector<String>();
		IFeatureProject featureProject = null;
		ArrayList<String> returnFeatures = new ArrayList<String>();
		featureProject = CorePlugin.getFeatureProject(file);

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
		/*
		Shell parentShel = null;
		ListDialog listDialog = new ListDialog(parentShel);
		listDialog.setTitle("FeatureDialog");
		listDialog.setMessage("Choose feature");
		listDialog.setContentProvider(ArrayContentProvider.getInstance());
		listDialog.setLabelProvider(new LabelProvider());

		Vector<String> featureList = new Vector<String>();
		IFeatureProject featureProject = null;

		featureProject = CorePlugin.getFeatureProject(file);

		if (featureProject != null) {
			for (String feature : featureProject.getFeatureModel().getConcreteFeatureNames()) {
				featureList.add(feature);
			}
		}

		listDialog.setInput(featureList);
		if (listDialog.open() == Dialog.OK) {
			System.out.println("Selected feature: " + Arrays.toString(listDialog.getResult()));
			if (listDialog.getResult().length > 0) {
				Object array[] = listDialog.getResult();
				return (String) array[0];
			}
		}

		return null;
*/
	}

}