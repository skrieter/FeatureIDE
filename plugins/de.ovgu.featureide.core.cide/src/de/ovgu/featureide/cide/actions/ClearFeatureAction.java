package de.ovgu.featureide.cide.actions;

import java.util.ArrayList;

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

import de.ovgu.featureide.cide.dialogs.ClearFeatureDialog;
import de.ovgu.featureide.core.cide.ColorXmlManager;

public class ClearFeatureAction implements IEditorActionDelegate, IViewActionDelegate {

	public ITextEditor activeEditor = null;
	ColorXmlManager colorXmlManager;
	ClearFeatureDialog clearFeatureDialog = new ClearFeatureDialog();

	public void run(IAction action) {
		ISelectionProvider selectionProvider = activeEditor.getSelectionProvider();
		ISelection selection = selectionProvider.getSelection();
		ITextSelection textSelection = (ITextSelection) selection;

		Integer offset = Integer.valueOf(textSelection.getOffset());
		Integer offsetEnd = Integer.valueOf(textSelection.getLength())+Integer.valueOf(textSelection.getOffset());

		FileEditorInput input = (FileEditorInput) activeEditor.getEditorInput();
		IFile file = input.getFile();
		IProject activeProject = file.getProject();

		String activeProjectPath = activeProject.getLocation().toFile().getAbsolutePath();
		String activeProjectPathToFile = file.getLocation().toFile().getAbsolutePath();

		this.colorXmlManager = new ColorXmlManager(activeProjectPath);

		ArrayList<String> features = clearFeatureDialog.open(activeEditor, activeProjectPathToFile, this.colorXmlManager.getParsedDocument());
		if (features != null) {
			for (String feature : features) {
				this.colorXmlManager.deleteFeatureAnnotation(activeProjectPathToFile, offset, offsetEnd, feature);
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
