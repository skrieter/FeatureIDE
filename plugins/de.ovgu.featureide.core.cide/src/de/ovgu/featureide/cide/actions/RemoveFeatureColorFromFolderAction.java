package de.ovgu.featureide.cide.actions;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import de.ovgu.featureide.cide.dialogs.SetFolderColorDialog;
import de.ovgu.featureide.core.IFeatureProject;
import de.ovgu.featureide.core.cide.ColorXmlManager;

public class RemoveFeatureColorFromFolderAction implements IEditorActionDelegate, IViewActionDelegate {

	ColorXmlManager colorXmlManager;
	SetFolderColorDialog setFolderColorDialog = new SetFolderColorDialog();
	public ITextEditor activeEditor = null;
	IFolder folder = null;
	IResource[] folderMembers = null;
	IFeatureProject featureProject = null;
	IDocument doc = null;

	public void run(IAction action) {

		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof IAdaptable) {
			this.folder = (IFolder) ((IAdaptable) firstElement).getAdapter(IFolder.class);
			
			try {
				this.folderMembers = folder.members();
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		String activeProjectPath = folder.getProject().getLocation().toFile().getAbsolutePath();
		this.colorXmlManager = new ColorXmlManager(activeProjectPath);

		if (this.folderMembers != null) {
			IFile file = (IFile) this.folderMembers[0];
			ArrayList<String> features = setFolderColorDialog.open(file);

			for (int i = 0; i < this.folderMembers.length; i++) {
				file = (IFile) this.folderMembers[i];
				IDocumentProvider provider = new TextFileDocumentProvider();
				try {
					provider.connect(file);
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.doc = provider.getDocument(file);
				String filePath = this.folderMembers[i].getLocation().toFile().getAbsolutePath();
				if (features != null) {
					for (String feature : features) {
						this.colorXmlManager.deleteFeatureAnnotation(filePath, feature);
					}
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
