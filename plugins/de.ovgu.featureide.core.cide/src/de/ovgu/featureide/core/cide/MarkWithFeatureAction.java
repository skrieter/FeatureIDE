package de.ovgu.featureide.core.cide;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;

import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.texteditor.ITextEditor;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

//import de.ovgu.featureide.ui.editors.annotation.ColorAnnotationModel;

public class MarkWithFeatureAction implements IEditorActionDelegate,
		IViewActionDelegate {

	private ITextEditor activeEditor = null;
	private boolean highlighting = true;

	public void run(IAction action) {
		/*
		 * highlighting = !highlighting; if (activeEditor != null) {
		 * ColorAnnotationModel.setHighlighting(highlighting, activeEditor); }
		 */

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("root");
			doc.appendChild(rootElement);

			// file elements
			Element file = doc.createElement("file");
			rootElement.appendChild(file);

			// set path attribute to file element
			Attr path = doc.createAttribute("path");
			path.setValue("features");
			file.setAttributeNode(path);

			// set filename attribute to file element
			Attr filename = doc.createAttribute("filename");
			filename.setValue("Add.java");
			file.setAttributeNode(filename);

			// line elements
			Element line = doc.createElement("line");
			file.appendChild(line);

			// set number attribute to line element
			Attr number = doc.createAttribute("number");
			number.setValue("5");
			line.setAttributeNode(number);

			// set feature attribute to line element
			Attr feature = doc.createAttribute("feature");
			feature.setValue("F1");
			line.setAttributeNode(feature);

			// shorten way
			// staff.setAttribute("id", "1");


			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(
					new File(
							"C:\\Projekt\\EclipseNew\\runtime-EclipseApplication\\Test\\ColorAnnotations2.xml"));

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
