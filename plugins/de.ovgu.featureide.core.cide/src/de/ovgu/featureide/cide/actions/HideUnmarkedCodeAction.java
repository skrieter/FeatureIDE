package de.ovgu.featureide.cide.actions;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.internal.ui.javaeditor.JavaEditor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditor;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.ovgu.featureide.core.cide.ColorXmlManager;

public class HideUnmarkedCodeAction implements IObjectActionDelegate {

	public ITextEditor activeEditor = null;
	ColorXmlManager colorXmlManager;

	public void run(IAction action) {
		
		IDocument doc = getCurrentEditorContent();
		
		FileEditorInput input = (FileEditorInput) activeEditor.getEditorInput();
		IFile file = input.getFile();
		IProject activeProject = file.getProject();

		String activeProjectPath = activeProject.getLocation().toFile().getAbsolutePath();
		String activeProjectPathToFile = file.getLocation().toFile().getAbsolutePath();
		
		JavaEditor javaEditor = (JavaEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		ProjectionViewer projectionViewer = (ProjectionViewer) javaEditor.getViewer();
		
		ProjectionAnnotationModel projectionAnnotationModel = projectionViewer.getProjectionAnnotationModel();
		projectionAnnotationModel.connect(doc);
		
		javax.xml.xpath.XPathFactory factory = javax.xml.xpath.XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		try {
			this.colorXmlManager = new ColorXmlManager(activeProjectPath);
			String selectionXPath = "root/files/file[@path='" + activeProjectPathToFile + "']/feature/selection";
			XPathExpression expression = xpath.compile(selectionXPath);
			NodeList selections = (NodeList) expression.evaluate(colorXmlManager.getParsedDocument(), XPathConstants.NODESET);
			for (int i = 0; i < selections.getLength(); i++) {
					Node selection = selections.item(i);
					int offset = Integer.parseInt(selection.getAttributes().getNamedItem("offset").getTextContent());
					int offsetEnd = Integer.parseInt(selection.getAttributes().getNamedItem("offsetEnd").getTextContent());
					
					ProjectionAnnotation projectionAnnotation = new ProjectionAnnotation();
					projectionAnnotation.setRangeIndication(true);
					Position position = new Position(offset , (offsetEnd - offset));
					projectionAnnotationModel.addAnnotation(projectionAnnotation, position);
					
				}
			

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		
	}

	public IDocument getCurrentEditorContent() {
		IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		IEditorInput input = editor.getEditorInput();
		IDocumentProvider provider = ((AbstractDecoratedTextEditor) editor).getDocumentProvider();
		IDocument document = provider.getDocument(input);
		return document;
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		if (targetPart instanceof ITextEditor) {
			activeEditor = (ITextEditor) targetPart;
		}
	}
}
