package de.ovgu.featureide.cide.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

import de.ovgu.featureide.cide.dialogs.MarkWithFeatureDialog;
import de.ovgu.featureide.core.cide.ColorXmlManager;

public class ClearAllFeaturesAction implements IObjectActionDelegate {
	
	public ITextEditor activeEditor = null;
	ColorXmlManager colorXmlManager;
	MarkWithFeatureDialog markWithFeatureDialog = new MarkWithFeatureDialog();

	/*Clear all annotations from the active file*/
	public void run(IAction action) {

		FileEditorInput input = (FileEditorInput) activeEditor.getEditorInput();
		IFile file = input.getFile();
		IProject activeProject = file.getProject();

		String activeProjectPath = activeProject.getLocation().toFile().getAbsolutePath();
		String activeProjectPathToFile = file.getLocation().toFile().getAbsolutePath();

		this.colorXmlManager = new ColorXmlManager(activeProjectPath);

		this.colorXmlManager.removeAllAnnotations(activeProjectPathToFile);
		
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}


	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		if (targetPart instanceof ITextEditor) {
			activeEditor = (ITextEditor) targetPart;
		}
	}
}