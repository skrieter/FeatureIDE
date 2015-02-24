package de.ovgu.featureide.cide.actions;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.internal.ui.javaeditor.JavaEditor;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditor;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

public class HideUnmarkedCodeAction implements IEditorActionDelegate, IViewActionDelegate {

	public ITextEditor activeEditor = null;

	public void run(IAction action) {
		
		IDocument doc = getCurrentEditorContent();

		ITextSelection sel = (ITextSelection) activeEditor.getSelectionProvider().getSelection();
		ITypeRoot typeRoot = JavaUI.getEditorInputTypeRoot(activeEditor.getEditorInput());
		ICompilationUnit icu = (ICompilationUnit) typeRoot.getAdapter(ICompilationUnit.class);
		
		JavaEditor javaEditor = (JavaEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		ProjectionViewer projectionViewer = (ProjectionViewer) javaEditor.getViewer();
		ProjectionAnnotation projectionAnnotation = new ProjectionAnnotation();

		projectionAnnotation.setRangeIndication(true);
		ProjectionAnnotationModel projectionAnnotationModel = projectionViewer.getProjectionAnnotationModel();
		projectionAnnotationModel.connect(doc);
		Position position = new Position(sel.getOffset(), sel.getLength());
		projectionAnnotationModel.addAnnotation(projectionAnnotation, position);
		System.out.println(projectionAnnotationModel.getPosition(projectionAnnotation));
		
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

	public void init(IViewPart view) {
	}

	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if (targetEditor instanceof ITextEditor) {
			activeEditor = (ITextEditor) targetEditor;
		}
	}
}
