package de.ovgu.featureide.core.cide;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.ovgu.featureide.core.CorePlugin;
import de.ovgu.featureide.core.IFeatureProject;
import de.ovgu.featureide.core.builder.preprocessor.PPComposerExtensionClass;
import de.ovgu.featureide.core.fstmodel.FSTClass;
import de.ovgu.featureide.core.fstmodel.preprocessor.FSTDirective;
import de.ovgu.featureide.core.fstmodel.preprocessor.FSTDirectiveCommand;
import de.ovgu.featureide.core.fstmodel.preprocessor.PPModelBuilder;
import de.ovgu.featureide.fm.core.ColorList;
import de.ovgu.featureide.fm.core.ColorschemeTable;
import de.ovgu.featureide.fm.core.Feature;

public class CIDEModelBuilder extends PPModelBuilder {
	
	ColorAnnotationManager colorAnnotationManager = new ColorAnnotationManager(featureProject);

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
		LinkedList<FSTDirective> directives = new LinkedList<FSTDirective>();
		// für jede Zeile prüfen, ob dafür ein(e) Feature/Farbe festgelegt wurde
		int lineNumber = 1;
		int id = 0;
		for (String s : lines) {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			try {
				DocumentBuilder db = dbf.newDocumentBuilder();
				File f = new File(featureProject.getProject().getLocation().toFile().getAbsolutePath(), "ColorAnnotations.xml");
				Document doc = db.parse(f);
				Integer  integerLineNumber = Integer.valueOf(lineNumber);
			String featureName = getFeatureForLine(lineNumber, doc);
			if(featureName!=null){
				FSTDirective d = new FSTDirective();
				d.setCommand(FSTDirectiveCommand.COLOR);
				d.setFeatureName(featureName);
				d.setLine(lineNumber);
				d.setStartLine(lineNumber-1, 0);
				d.setEndLine(lineNumber, 0);
				d.setId(id++);
				d.setExpression("At line:" +
						" "+integerLineNumber.toString());
				directives.add(d);
			}
				lineNumber++;
			} catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			} catch (SAXException se) {
				se.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}

		return directives;
	}

	private String getFeatureForLine(int lineNumber, Document dom) {
		// get the root element
		Element docEle = dom.getDocumentElement();

		// get a nodelist of elements
		NodeList nl = docEle.getElementsByTagName("file");
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {

				Element el = (Element) nl.item(i);
				System.out.println("path: " + el.getAttribute("path"));
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
	
	@Override
	protected List<String> getFeatureNames(String expression) {
		expression = expression.replaceAll("[(]", "");
		List<String> featureNameList = new LinkedList<String>();
		featureNameList.add(expression.replaceAll("[)]", "").trim());
		return featureNameList;
	}

}
