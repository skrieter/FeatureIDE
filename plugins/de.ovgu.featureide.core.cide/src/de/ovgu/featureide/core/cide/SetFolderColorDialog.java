package de.ovgu.featureide.core.cide;

import java.util.Arrays;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListDialog;

import de.ovgu.featureide.core.CorePlugin;
import de.ovgu.featureide.core.IFeatureProject;

public class SetFolderColorDialog {

	public String open(IFile file, String fileName) {

		Shell parentShel = null;
		ListDialog listDialog = new ListDialog(parentShel);
		listDialog.setTitle("FeatureDialog");
		listDialog.setMessage("Choose feature for " + fileName);
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

	}

}