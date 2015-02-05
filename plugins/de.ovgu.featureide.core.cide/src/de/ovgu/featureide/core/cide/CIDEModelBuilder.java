package de.ovgu.featureide.core.cide;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.texteditor.ITextEditor;

import de.ovgu.featureide.core.IFeatureProject;
import de.ovgu.featureide.core.builder.preprocessor.PPComposerExtensionClass;
import de.ovgu.featureide.core.fstmodel.FSTClass;
import de.ovgu.featureide.core.fstmodel.preprocessor.FSTDirective;
import de.ovgu.featureide.core.fstmodel.preprocessor.PPModelBuilder;


public class CIDEModelBuilder extends PPModelBuilder {

	ColorAnnotationManager colorAnnotationManager;
	ITextEditor activeEditor = null;
	ColorXmlManager colorXmlManager;
	
	
	//Constructor
	public CIDEModelBuilder(IFeatureProject featureProject) {
		
		super(featureProject);
		this.colorXmlManager = new ColorXmlManager(featureProject.getProject().getLocation().toFile().getAbsolutePath());		
		this.colorAnnotationManager = new ColorAnnotationManager(this.colorXmlManager);
		 
		// if ColorAnnotations.xml doesnt exist -> create
		if (this.colorXmlManager.getParsedDocument()==null){
			this.colorXmlManager.createXml();
		}
	}

	/**
	 * @param folder
	 * @param packageName
	 * @throws CoreException
	 */
	protected void buildModel(IFolder folder, String packageName) throws CoreException {
		
		/* ColorAnnotationVerarbeitung */
		// Anlegen TODO: wird jedes Mal ne angelegt, wenn das Model gebuildet wird, überdenken
		
		if (this.colorXmlManager.getParsedDocument()==null){
			this.colorXmlManager.createXml();
		}
		this.colorXmlManager.readXml();
		
		for (IResource res : folder.members()) {
			if (res instanceof IFolder) {
				buildModel((IFolder) res, packageName.isEmpty() ? res.getName() : packageName + "/" + res.getName());
			} else if (res instanceof IFile) {
				// String text = getText((IFile)res);
				String className = packageName.isEmpty() ? res.getName() : packageName + "/" + res.getName();

				Vector<String> lines = PPComposerExtensionClass.loadStringsFromFile((IFile) res);

				boolean classAdded = false;
				for (String feature : featureNames) {

					if (/* containsFeature(text, feature) */true) {
						System.err.println("buildModel2 :" + feature + " - " + className);
						model.addRole(feature, className, (IFile) res);
						classAdded = true;
					}
				}
				if (classAdded) {
					LinkedList<FSTDirective> directives = buildModelDirectivesForFile(lines,res);
					addDirectivesToModel(directives, (IFile) res, className);
				} else {
					// add class without annotations
					model.addClass(new FSTClass(className));
				}
			}
		}
	}
	
	
	
	public LinkedList<FSTDirective> buildModelDirectivesForFile(Vector<String> lines,IResource res) {
		
		return colorAnnotationManager.getDirectives(lines, featureProject,res);
	}

	/*
	@Override
	public LinkedList<FSTDirective> buildModelDirectivesForFile(Vector<String> lines) {
		
		return colorAnnotationManager.getDirectives(lines, featureProject);
	}
*/
	
	@Override
	protected List<String> getFeatureNames(String expression) {
		expression = expression.replaceAll("[(]", "");
		List<String> featureNameList = new LinkedList<String>();
		featureNameList.add(expression.replaceAll("[)]", "").trim());
		return featureNameList;
	}

}
