package de.ovgu.featureide.cide.actions;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;
import org.eclipse.ui.texteditor.IDocumentProvider;

import de.ovgu.featureide.cide.dialogs.SetFileColorDialog;
import de.ovgu.featureide.core.cide.ColorXmlManager;

public class SetFileColorAction implements IViewActionDelegate {

	ColorXmlManager colorXmlManager;
	SetFileColorDialog setFileColorDialog = new SetFileColorDialog();
	IFile file = null;
	String path = null;
	IDocument doc = null;
	public void run(IAction action) {

		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (!(window.getSelectionService().getSelection() instanceof TextSelection)) {
			IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
			Object firstElement = selection.getFirstElement();
			if (firstElement instanceof IAdaptable) {
				this.file = (IFile) ((IAdaptable) firstElement).getAdapter(IFile.class);
				this.path = file.getLocation().toFile().getAbsolutePath();
				IDocumentProvider provider = new TextFileDocumentProvider();
				try {
					provider.connect(file);
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.doc = provider.getDocument(file);
			}

			IProject activeProject = file.getProject();
			String activeProjectPath = activeProject.getLocation().toFile().getAbsolutePath();
			String activeProjectPathToFile = file.getLocation().toFile().getAbsolutePath();

			this.colorXmlManager = new ColorXmlManager(activeProjectPath);

			ArrayList<String> features = setFileColorDialog.open(file);

			if (features != null) {
				for (String feature : features) {
					this.colorXmlManager.addAnnotation(activeProjectPathToFile, 0, doc.getLength(), feature);
					while (this.colorXmlManager.mergeLines(activeProjectPathToFile, feature));
				}
			}
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

	public void init(IViewPart view) {
	}
}
