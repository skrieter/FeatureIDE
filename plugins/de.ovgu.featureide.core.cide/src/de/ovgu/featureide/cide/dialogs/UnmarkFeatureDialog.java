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
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
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

public class UnmarkFeatureDialog {

	public ArrayList<String> open(ITextEditor activeEditor, String path, Document doc) {
		ISelectionProvider selectionProvider = activeEditor.getSelectionProvider();
		ISelection selection = selectionProvider.getSelection();
		ITextSelection textSelection = (ITextSelection) selection;

		Integer selectedOffset = Integer.valueOf(textSelection.getOffset());
		Integer selectedOffsetEnd = Integer.valueOf(textSelection.getLength())+Integer.valueOf(textSelection.getOffset());
		
		Shell shell = null;
		Vector<String> featureList = new Vector<String>();
		IFeatureProject featureProject = null;
		ArrayList<String> returnFeatures = new ArrayList<String>();
		if (activeEditor != null) {
			IFile inputFile = ((FileEditorInput) activeEditor.getEditorInput()).getFile();
			featureProject = CorePlugin.getFeatureProject(inputFile);
		}
		if (featureProject != null) {
			
			// get all feature id's from given path
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			try {
				String linePathXPath = "root/files/file[@path='" + path + "']/feature/selection";
				XPathExpression expression = xpath.compile(linePathXPath);
				NodeList selectionNodes = (NodeList) expression.evaluate(doc, XPathConstants.NODESET);

				for (int i = 0; i < selectionNodes.getLength(); i++) {
					Node selectionNode = selectionNodes.item(i);
					Integer offsetEndAttribute = Integer.parseInt(selectionNode.getAttributes().getNamedItem("offsetEnd").getNodeValue());
					Integer offsetAttribute = Integer.parseInt(selectionNode.getAttributes().getNamedItem("offset").getNodeValue());
					// get only feature id's from selected range and add them to featureList
					if (offsetEndAttribute >= selectedOffsetEnd && offsetAttribute <= selectedOffset) {
						String feature = selectionNode.getParentNode().getAttributes().getNamedItem("id").getTextContent();
						featureList.add(feature);
					}
				}
			} catch (XPathExpressionException e) {
				e.printStackTrace();
			}
		}
		
		// sort featurelist
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
		return null;
	}

}
