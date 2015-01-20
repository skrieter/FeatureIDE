package de.ovgu.featureide.core.cide;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import de.ovgu.featureide.core.IFeatureProject;
import de.ovgu.featureide.core.builder.preprocessor.PPComposerExtensionClass;
import de.ovgu.featureide.core.fstmodel.FSTClass;
import de.ovgu.featureide.core.fstmodel.preprocessor.FSTDirective;
import de.ovgu.featureide.core.fstmodel.preprocessor.PPModelBuilder;

public class CIDEModelBuilder extends PPModelBuilder {
	
	ColorAnnotationManager colorAnnotationManager = new ColorAnnotationManager();
	
	public CIDEModelBuilder(IFeatureProject featureProject) {
		super(featureProject);

	}

	/**
	 * @param folder
	 * @param packageName
	 * @throws CoreException
	 */
	protected void buildModel(IFolder folder, String packageName)
			throws CoreException {
		
		for (IResource res : folder.members()) {
			if (res instanceof IFolder) {
				buildModel((IFolder) res, packageName.isEmpty() ? res.getName()
						: packageName + "/" + res.getName());
			} else if (res instanceof IFile) {
				// String text = getText((IFile)res);
				String className = packageName.isEmpty() ? res.getName()
						: packageName + "/" + res.getName();

				Vector<String> lines = PPComposerExtensionClass
						.loadStringsFromFile((IFile) res);

				boolean classAdded = false;
				for (String feature : featureNames) {
					
					if (/* containsFeature(text, feature) */true) {
						System.err.println("buildModel2 :" + feature + " - "
								+ className);
						model.addRole(feature, className, (IFile) res);
						classAdded = true;
					}
				}
				if (classAdded) {
					LinkedList<FSTDirective> directives = buildModelDirectivesForFile(lines);
					addDirectivesToModel(directives, (IFile) res, className);
				} else {
					// add class without annotations
					model.addClass(new FSTClass(className));
				}
			}
		}
		
	}
	

	@Override
	public LinkedList<FSTDirective> buildModelDirectivesForFile(
			Vector<String> lines) {
		LinkedList<FSTDirective> directives = colorAnnotationManager.colorXmlReader(lines, featureProject);
		return directives;
		
	}
/*
	private String getFeatureForLine(int lineNumber, Document dom) {
		// get the root element
		Element docEle = dom.getDocumentElement();

		// get a nodelist of elements
		NodeList nl = docEle.getElementsByTagName("file");
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {

				Element el = (Element) nl.item(i);
				//System.out.println("path: " + el.getAttribute("path"));
				NodeList n = el.getElementsByTagName("line");

				if (n != null && n.getLength() > 0) {
					for (int j = 0; j < n.getLength(); j++) {
						Element line = (Element) n.item(j);
						if (Integer.parseInt(line.getAttribute("number"))==lineNumber) {
							return line.getAttribute("feature");
						}

					}
				}

			}
		}
		return null;
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
