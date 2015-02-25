package de.ovgu.featureide.cide.actions;

import java.util.ArrayList;
import java.util.Vector;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.ovgu.featureide.cide.dialogs.UnmarkFeatureDialog;
import de.ovgu.featureide.core.cide.ColorXmlManager;

public class UnmarkFeatureAction implements IEditorActionDelegate, IViewActionDelegate {

	public ITextEditor activeEditor = null;
	ColorXmlManager colorXmlManager;
	UnmarkFeatureDialog selectMarkedFeatureDialog = new UnmarkFeatureDialog();

	public void run(IAction action) {

		ISelectionProvider selectionProvider = activeEditor.getSelectionProvider();
		ISelection selection = selectionProvider.getSelection();
		ITextSelection textSelection = (ITextSelection) selection;

		Integer selectedOffset = Integer.valueOf(textSelection.getOffset());
		Integer selectedOffsetEnd = Integer.valueOf(textSelection.getLength())+Integer.valueOf(textSelection.getOffset());
		
		FileEditorInput input = (FileEditorInput) activeEditor.getEditorInput();
		IFile file = input.getFile();
		IProject activeProject = file.getProject();

		String activeProjectPath = activeProject.getLocation().toFile().getAbsolutePath();
		String activeProjectPathToFile = file.getLocation().toFile().getAbsolutePath();

		this.colorXmlManager = new ColorXmlManager(activeProjectPath);

		Vector<String> featureList = new Vector<String>();
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		
		try {
			String linePathXPath = "root/files/file[@path='" + activeProjectPathToFile + "']/feature/selection";
			XPathExpression expression = xpath.compile(linePathXPath);
			NodeList selectionNodes = (NodeList) expression.evaluate(colorXmlManager.getParsedDocument(), XPathConstants.NODESET);

			for (int i = 0; i < selectionNodes.getLength(); i++) {
				Node selectionNode = selectionNodes.item(i);
				Integer offsetEnd = Integer.parseInt(selectionNode.getAttributes().getNamedItem("offsetEnd").getNodeValue());
				Integer offset = Integer.parseInt(selectionNode.getAttributes().getNamedItem("offset").getNodeValue());

				if (offsetEnd >= selectedOffsetEnd && offset <= selectedOffset) {
					// get feature id and add to featurelist
					String feature = selectionNode.getParentNode().getAttributes().getNamedItem("id").getTextContent();
					featureList.add(feature);
				}
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (featureList.size() == 1) {
			this.colorXmlManager.deleteAnnotaion(activeProjectPathToFile, selectedOffset, selectedOffsetEnd, featureList.elementAt(0));
		} else {
			ArrayList<String> features = selectMarkedFeatureDialog.open(activeEditor, activeProjectPathToFile, this.colorXmlManager.getParsedDocument());
			if (features != null) {
				for (String feature : features) {
					this.colorXmlManager.deleteAnnotaion(activeProjectPathToFile, selectedOffset, selectedOffsetEnd, feature);
				}
			}
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
