package de.ovgu.featureide.core.cide;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.texteditor.ITextEditor;

//import de.ovgu.featureide.ui.editors.annotation.ColorAnnotationModel;

public class MarkWithFeatureAction implements IEditorActionDelegate,
		IViewActionDelegate {

	SelectFeatureDialog selectFeatureDialog = new SelectFeatureDialog();

	public ITextEditor activeEditor = null;


	public void run(IAction action) {
		
		ColorAnnotationManager colorAnnotationManager = new ColorAnnotationManager(); 
		ISelectionProvider selectionProvider = activeEditor .getSelectionProvider(); 
		ISelection selection = selectionProvider.getSelection();
		colorAnnotationManager.colorXmlWriter(selection, activeEditor); 
		selectFeatureDialog.open(activeEditor);
		
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
