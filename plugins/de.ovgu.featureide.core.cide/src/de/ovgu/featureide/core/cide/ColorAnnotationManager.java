package de.ovgu.featureide.core.cide;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.ovgu.featureide.core.CorePlugin;
import de.ovgu.featureide.core.IFeatureProject;
import de.ovgu.featureide.core.fstmodel.preprocessor.FSTDirective;
import de.ovgu.featureide.core.fstmodel.preprocessor.FSTDirectiveCommand;
import de.ovgu.featureide.core.fstmodel.preprocessor.PPModelBuilder;

public class ColorAnnotationManager {


	protected void colorXmlWriter(ISelection selection, ITextEditor activeEditor) {

		Integer startLine = Integer.valueOf(0);

		if (selection instanceof ITextSelection) {
			ITextSelection textSelection = (ITextSelection) selection;
			startLine = textSelection.getStartLine() + 1;
			int endLine = textSelection.getEndLine() + 1;

			System.out.println("********************************************");
			System.out.println("Get Text: " + textSelection.getText());
			System.out.println("Get StartLine: " + startLine);
			System.out.println("Get EndLine: " + endLine);
			System.out.println("Get Length: " + textSelection.getLength());
			System.out.println("Get Offset: " + textSelection.getOffset());
			System.out.println("********************************************");
		}

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			final IFile inputFile = ((FileEditorInput) activeEditor.getEditorInput()).getFile();

			IPath fullPath = inputFile.getFullPath();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("root");
			doc.appendChild(rootElement);

			// file elements
			Element file = doc.createElement("file");
			rootElement.appendChild(file);

			// set path attribute to file element
			Attr path = doc.createAttribute("path");
			path.setValue(fullPath.toString());
			file.setAttributeNode(path);

			// line elements
			Element line = doc.createElement("line");
			file.appendChild(line);

			// set number attribute to line element
			Attr number = doc.createAttribute("number");
			number.setValue(startLine.toString());
			line.setAttributeNode(number);

			// set feature attribute to line element
			Attr feature = doc.createAttribute("feature");
			feature.setValue("F1");
			line.setAttributeNode(feature);

			// shorten way
			// staff.setAttribute("id", "1");

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);

			StreamResult result = new StreamResult(
					new File("C:\\Projekt\\EclipseNew\\runtime-EclipseApplication\\Test\\ColorAnnotations2.xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
			transformer.transform(source, result);

			// Dateien/Zeilen mit Farbinformationen abspeichern
			System.out.println("save to file");
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	public LinkedList<FSTDirective> colorXmlReader(Vector<String> lines, IFeatureProject featureProject) {
		
		// für jede Zeile prüfen, ob dafür ein(e) Feature/Farbe festgelegt wurde
		int lineNumber = 1;
		int id = 0;
		LinkedList<FSTDirective> directives = new LinkedList<FSTDirective>();
		for (String s : lines) {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			try {
				DocumentBuilder db = dbf.newDocumentBuilder();
			
				File f = new File(featureProject.getProject().getLocation().toFile().getAbsolutePath(), "ColorAnnotations2.xml");
				Document doc = db.parse(f);
				Integer integerLineNumber = Integer.valueOf(lineNumber);
				String featureName = getFeatureForLine(lineNumber, doc);
				if (featureName != null) {
					FSTDirective d = new FSTDirective();
					d.setCommand(FSTDirectiveCommand.COLOR);
					d.setFeatureName(featureName);
					d.setLine(lineNumber);
					d.setStartLine(lineNumber - 1, 0);
					d.setEndLine(lineNumber, 0);
					d.setId(id++);
					d.setExpression("At line:" + " " + integerLineNumber.toString());
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
				NodeList n = el.getElementsByTagName("line");

				if (n != null && n.getLength() > 0) {
					for (int j = 0; j < n.getLength(); j++) {
						Element line = (Element) n.item(j);
						if (Integer.parseInt(line.getAttribute("number")) == lineNumber) {
							return line.getAttribute("feature");
						}

					}
				}

			}
		}
		return null;
	}

}
