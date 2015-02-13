package de.ovgu.featureide.core.cide;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class SetFileColorAction implements IViewActionDelegate {

	ColorXmlManager colorXmlManager;
	SetFileColorDialog setFileColorDialog = new SetFileColorDialog();
	IFile file = null;
	String path = null;

	public void run(IAction action) {

		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof IAdaptable) {
			this.file = (IFile) ((IAdaptable) firstElement).getAdapter(IFile.class);
			this.path = file.getLocation().toFile().getAbsolutePath();
		}

		IProject activeProject = file.getProject();
		String activeProjectPath = activeProject.getLocation().toFile().getAbsolutePath();
		String activeProjectPathToFile = file.getLocation().toFile().getAbsolutePath();

		this.colorXmlManager = new ColorXmlManager(activeProjectPath);

		ArrayList<String> features = setFileColorDialog.open(file);
		for (String feature : features) {
			if (feature != null) {
				this.colorXmlManager.addAnnotation(activeProjectPathToFile, 1, getEndline(path), feature);
				while (this.colorXmlManager.mergeLines(activeProjectPathToFile, feature));
			}
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

	public void init(IViewPart view) {
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
