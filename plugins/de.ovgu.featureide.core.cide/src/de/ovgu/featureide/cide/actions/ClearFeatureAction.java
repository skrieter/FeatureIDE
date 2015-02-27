package de.ovgu.featureide.cide.actions;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

import de.ovgu.featureide.cide.dialogs.ClearFeatureDialog;
import de.ovgu.featureide.core.cide.ColorXmlManager;


public class ClearFeatureAction implements IObjectActionDelegate {

	public ITextEditor activeEditor = null;
	ColorXmlManager colorXmlManager;
	ClearFeatureDialog clearFeatureDialog = new ClearFeatureDialog();

	public void run(IAction action) {
	
		FileEditorInput input = (FileEditorInput) activeEditor.getEditorInput();
		IFile file = input.getFile();
		IProject activeProject = file.getProject();

		String activeProjectPath = activeProject.getLocation().toFile().getAbsolutePath();
		String activeProjectPathToFile = file.getLocation().toFile().getAbsolutePath();

		this.colorXmlManager = new ColorXmlManager(activeProjectPath);

		ArrayList<String> features = clearFeatureDialog.open(activeEditor, activeProjectPathToFile, this.colorXmlManager.getParsedDocument());
		if (features != null) {
			for (String feature : features) {
				this.colorXmlManager.deleteFeatureAnnotation(activeProjectPathToFile, feature);
			}
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		if (targetPart instanceof ITextEditor) {
			activeEditor = (ITextEditor) targetPart;
		}
	}
}
