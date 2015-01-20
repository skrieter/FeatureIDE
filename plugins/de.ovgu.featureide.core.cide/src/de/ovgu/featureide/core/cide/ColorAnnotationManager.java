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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			final IFile inputFile = ((FileEditorInput) activeEditor
					.getEditorInput()).getFile();
			
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

	public void colorXmlReader() {

	}

}
