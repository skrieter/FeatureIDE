package de.ovgu.featureide.cide.dialogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.ovgu.featureide.core.CorePlugin;
import de.ovgu.featureide.core.IFeatureProject;

public class ClearFeatureDialog {

	public ArrayList<String> open(ITextEditor activeEditor, String path, Document doc) {

		Shell shell = null;
		Vector<String> featureList = new Vector<String>();
		IFeatureProject featureProject = null;
		ArrayList<String> returnFeatures = new ArrayList<String>();
		if (activeEditor != null) {
			IFile inputFile = ((FileEditorInput) activeEditor.getEditorInput()).getFile();
			featureProject = CorePlugin.getFeatureProject(inputFile);
		}
		if (featureProject != null) {

			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			try {
				String featureForPathXPath = "root/files/file[@path='" + path + "']/feature/@id";
				XPathExpression expr = xpath.compile(featureForPathXPath);
				NodeList featureNodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
				for (int i = 0; i < featureNodes.getLength(); i++) {
					Node featureNode = featureNodes.item(i);
					String feature = featureNode.getTextContent().toString();
					if (!featureList.contains(feature)) {
						featureList.add(feature);
					}
				}
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Collections.sort(featureList);
		
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
		// Object[] result = dialog.getResult();
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

		if (activeEditor != null) {
			IFile inputFile = ((FileEditorInput) activeEditor.getEditorInput()).getFile();
			featureProject = CorePlugin.getFeatureProject(inputFile);
		}
		if (featureProject != null) {

			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			try {
				String featureForPathXPath = "root/files/file[@path='" + path + "']/feature/@id";
				XPathExpression expr = xpath.compile(featureForPathXPath);
				NodeList featureNodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
				for (int i = 0; i < featureNodes.getLength(); i++) {
					Node featureNode = featureNodes.item(i);
					String feature = featureNode.getTextContent().toString();
					if (!featureList.contains(feature)) {
						featureList.add(feature);
					}
				}
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Collections.sort(featureList);

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
