package de.ovgu.featureide.core.cide;

import java.util.LinkedList;
import java.util.Vector;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.texteditor.ITextEditor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import de.ovgu.featureide.core.IFeatureProject;
import de.ovgu.featureide.core.fstmodel.preprocessor.FSTDirective;
import de.ovgu.featureide.core.fstmodel.preprocessor.FSTDirectiveCommand;

public class ColorAnnotationManager {
	
	private ColorXmlManager m_colorxmlManager;
	
	public ColorAnnotationManager(ColorXmlManager colorXmlManager)
	{
		m_colorxmlManager = colorXmlManager;
	}


	protected LinkedList<FSTDirective> getDirectives(Vector<String> lines,
			IFeatureProject featureProject) {

		// für jede Zeile prüfen, ob dafür ein(e) Feature/Farbe festgelegt wurde
		int lineNumber = 1;
		int id = 0;
		LinkedList<FSTDirective> directives = new LinkedList<FSTDirective>();
		for (String s : lines) {
				Integer integerLineNumber = Integer.valueOf(lineNumber);
				String featureName = getFeatureForLine(lineNumber, this.m_colorxmlManager.getParsedDocument());
				if (featureName != null) {
					FSTDirective d = new FSTDirective();
					d.setCommand(FSTDirectiveCommand.COLOR);
					d.setFeatureName(featureName);
					d.setLine(lineNumber);
					d.setStartLine(lineNumber - 1, 0);
					d.setEndLine(lineNumber, 0);
					d.setId(id++);
					d.setExpression("At line:" + " "
							+ integerLineNumber.toString());
					directives.add(d);
				}
				lineNumber++;
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
				NodeList n = el.getElementsByTagName("line");
				if (n != null && n.getLength() > 0) {
					for (int j = 0; j < n.getLength(); j++) {
						Element line = (Element) n.item(j);
						if (Integer.parseInt(line.getAttribute("startLine")) == lineNumber) {
							return line.getAttribute("feature");
						}

					}
				}

			}
		}
		return null;
	}

}
