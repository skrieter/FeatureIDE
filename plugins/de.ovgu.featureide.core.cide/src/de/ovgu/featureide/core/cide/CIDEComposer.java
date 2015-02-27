package de.ovgu.featureide.core.cide;

import java.util.ArrayList;
import java.util.LinkedList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import de.ovgu.featureide.cide.CIDECorePlugin;
import de.ovgu.featureide.core.CorePlugin;
import de.ovgu.featureide.core.IFeatureProject;
import de.ovgu.featureide.core.builder.IComposerExtensionClass;
import de.ovgu.featureide.core.builder.preprocessor.PPComposerExtensionClass;
import de.ovgu.featureide.fm.core.configuration.Configuration;
import de.ovgu.featureide.fm.core.configuration.ConfigurationWriter;

public class CIDEComposer extends PPComposerExtensionClass {
	protected static final String CIDE_NATURE = "de.ovgu.cide.core.CIDEProjectNature";
	private static final ArrayList<String[]> TEMPLATES = createTemplates();
	CIDEModelBuilder cideModelBuilder;

	public CIDEComposer() {
		super("CIDE"); // only with PP
	}

	@Override
	public boolean initialize(IFeatureProject project) {
		boolean supSuccess = super.initialize(project);
		cideModelBuilder = new CIDEModelBuilder(project);
		buildFSTModel();

		prepareFullBuild(null);
		return supSuccess && cideModelBuilder != null;
	}

	public void performFullBuild(IFile config) {
		System.out.println("starting CIDE build!!!");
		if (!prepareFullBuild(config))
			return;
		try {
			preprocessSourceFiles(featureProject.getBuildFolder());
		} catch (CoreException e) {
			CIDECorePlugin.getDefault().logError(e);
		}

		if (cideModelBuilder != null)
			cideModelBuilder.buildModel();
	}

	protected void preprocessSourceFiles(IFolder buildFolder) throws CoreException {
		LinkedList<String> args = new LinkedList<String>();
		for (String feature : activatedFeatures) {
			args.add("-" +feature);
		}
		runCIDE(args, featureProject.getSourceFolder(), buildFolder);
	}

	protected void runCIDE(LinkedList<String> featureArgs, IFolder sourceFolder, IFolder buildFolder) {
		LinkedList<String> packageArgs = new LinkedList<String>(featureArgs);
		boolean added = false;
		try {
			createBuildFolder(buildFolder);
			for (final IResource res : sourceFolder.members()) {
				if (res instanceof IFolder) {
					runCIDE(featureArgs, (IFolder) res, buildFolder.getFolder(res.getName()));
				} else if (res instanceof IFile) {
					added = true;
					packageArgs.add(res.getRawLocation().toOSString());
				}
			}
		} catch (CoreException e) {
			CIDECorePlugin.getDefault().logError(e);
		}
		if (!added) {
			return;
		}
		// add output directory
		packageArgs.add(buildFolder.getRawLocation().toOSString());

		// CommandLine syntax:
		// -FEATURE1 -FEATURE2 ... File1 File2 ... outputDirectory
		runCIDE(packageArgs);
	}

	protected void runCIDE(LinkedList<String> args) {
		// run CIDEConfiguration
		CIDEConfiguration cideConfiguration = new CIDEConfiguration();
		cideConfiguration.main(args.toArray(new String[0]), featureProject);
	}

	protected void createBuildFolder(IFolder buildFolder) throws CoreException {
		if (!buildFolder.exists()) {
			buildFolder.create(true, true, null);
		}
		buildFolder.refreshLocal(IResource.DEPTH_ZERO, null);
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

	private static ArrayList<String[]> createTemplates() {
		ArrayList<String[]> list = new ArrayList<String[]>(1);
		list.add(JAVA_TEMPLATE);
		return list;
	}

	@Override
	public void addCompiler(IProject project, String sourcePath, String configPath, String buildPath) {
		super.addCompiler(project, sourcePath, configPath, buildPath);
		addNature(project, CIDE_NATURE);
		addClasspathFile(project, sourcePath);
	}

	@Override
	public boolean needColor() {
		return true;
	}

	@Override
	public void buildFSTModel() {
		cideModelBuilder.buildModel();
	}

	@Override
	public void buildConfiguration(IFolder folder, Configuration configuration, String configurationName) {
		try {
			if (!folder.exists()) {
				folder.create(true, false, null);
			}
			IFile configurationFile = folder.getFile(configurationName + "." + getConfigurationExtension());
			ConfigurationWriter writer = new ConfigurationWriter(configuration);
			writer.saveToFile(configurationFile);
			copyNotComposedFiles(configuration, folder);
		} catch (CoreException e) {
			CorePlugin.getDefault().logError(e);
		}
	}
}
