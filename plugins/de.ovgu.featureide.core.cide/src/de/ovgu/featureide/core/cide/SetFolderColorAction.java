package de.ovgu.featureide.core.cide;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

public class SetFolderColorAction implements IEditorActionDelegate, IViewActionDelegate {

	ColorXmlManager colorXmlManager;
	SetFolderColorDialog setFolderColorDialog = new SetFolderColorDialog();
	public ITextEditor activeEditor = null;
	IFolder folder = null;
	IResource[] resource = null;

	public void run(IAction action) {

		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof IAdaptable) {
			this.folder = (IFolder) ((IAdaptable) firstElement).getAdapter(IFolder.class);
			try {
				this.resource = folder.members();
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		String activeProjectPath = folder.getProject().getLocation().toFile().getAbsolutePath();
		this.colorXmlManager = new ColorXmlManager(activeProjectPath);

		for (int i = 0; i < this.resource.length; i++) {
			IFile file = (IFile) this.resource[i];
			String filePath = this.resource[i].getLocation().toFile().getAbsolutePath();
			String feature = setFolderColorDialog.open(file, file.getName());
			if (feature != null) {
				this.colorXmlManager.addAnnotation(filePath, 1, getEndline(filePath), feature);
				while (this.colorXmlManager.mergeLines(filePath, feature));
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

	// count lines for a given file
	private int getEndline(String path) {
		BufferedReader reader;
		int lines = 0;
		try {
			reader = new BufferedReader(new FileReader(path));

			while (reader.readLine() != null)
				lines++;
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lines;
	}
}
