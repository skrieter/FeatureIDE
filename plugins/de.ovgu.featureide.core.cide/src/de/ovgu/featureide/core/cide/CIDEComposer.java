package de.ovgu.featureide.core.cide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import de.ovgu.cide.configuration.CreateConfigurationJob;
import de.ovgu.cide.features.IFeature;
import de.ovgu.cide.fm.guidsl.FeatureAdapter;
import de.ovgu.cide.fm.guidsl.FeatureModelWrapper;
import de.ovgu.featureide.cide.CIDECorePlugin;
import de.ovgu.featureide.core.builder.ComposerExtensionClass;
import de.ovgu.featureide.core.builder.IComposerExtensionClass;
import de.ovgu.featureide.fm.core.Feature;
import de.ovgu.featureide.fm.core.FeatureModel;
import de.ovgu.featureide.fm.core.configuration.Configuration;
import de.ovgu.featureide.fm.core.configuration.ConfigurationReader;

public class CIDEComposer extends ComposerExtensionClass {
	protected static final String CIDE_NATURE = "de.ovgu.cide.core.CIDEProjectNature";
	public CIDEComposer() {
	
	}
		
	public void performFullBuild(IFile config) {
		System.out.println("starting CIDE build!!!");
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		FeatureModel featureModel = featureProject.getFeatureModel();
		Configuration configuration = new Configuration(featureModel);
		ConfigurationReader reader = new ConfigurationReader(configuration);
		try {
			reader.readFromFile(config);
		} catch (CoreException e) {
			CIDECorePlugin.getDefault().logError(e);
		} catch (IOException e) {
			CIDECorePlugin.getDefault().logError(e);
		}

		Set<Feature> fideFeatures = new HashSet<Feature>(
				configuration.getSelectedFeatures());

		Set<IFeature> flist = new HashSet<IFeature>();
		IProject sourceProject = featureProject.getProject();

		for (Feature f : fideFeatures) {
			flist.add(new FeatureAdapter(f, FeatureModelWrapper
					.getInstance(sourceProject)));
		}

		CreateConfigurationJob job = new CreateConfigurationJob(sourceProject,
				flist, "OUTPUT");
		job.schedule();
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
}
