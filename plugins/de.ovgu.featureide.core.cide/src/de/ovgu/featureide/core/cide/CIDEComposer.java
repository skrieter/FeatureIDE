package de.ovgu.featureide.core.cide;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import de.ovgu.featureide.core.IFeatureProject;
import de.ovgu.featureide.core.builder.ComposerExtensionClass;
import de.ovgu.featureide.core.builder.IComposerExtensionClass;

public class CIDEComposer extends ComposerExtensionClass {
	protected static final String CIDE_NATURE = "de.ovgu.cide.core.CIDEProjectNature";
	CIDEModelBuilder cideModelBuilder;
	
	public CIDEComposer() {

	}
	
	@Override
	public boolean initialize(IFeatureProject project) {
		boolean supSuccess = super.initialize(project);
		cideModelBuilder = new CIDEModelBuilder(project);

	//	prepareFullBuild(null);
	//	annotationChecking();

		return supSuccess && cideModelBuilder != null;
	}
		
	public void performFullBuild(IFile config) {
		System.out.println("starting CIDE build!!!");
		
	}
	
	
	public Mechanism getGenerationMechanism() {
	
		return IComposerExtensionClass.Mechanism.PREPROCESSOR;
	}
	
	@Override
	public boolean hasFeatureFolder() {
		return true;
	}

	@Override
	public boolean createFolderForFeatures() {
		return false;
	}
	
	@Override
	public ArrayList<String[]> getTemplates() {
		return TEMPLATES;
	}
	
private static final ArrayList<String[]> TEMPLATES = createTempltes();
	
	private static ArrayList<String[]> createTempltes() {
		 ArrayList<String[]> list = new  ArrayList<String[]>(1);
		 list.add(JAVA_TEMPLATE);
		 return list;
	}
	
	@Override
	public void addCompiler(IProject project, String sourcePath,
			String configPath, String buildPath) {
		super.addCompiler(project, sourcePath, configPath, buildPath);
		addNature(project, CIDE_NATURE);
		addClasspathFile(project, sourcePath);
	}
	
	@Override
	public boolean needColor() {
		return true;
	}

	@Override
	public void buildFSTModel(){
		cideModelBuilder.buildModel();
		
	}
}
