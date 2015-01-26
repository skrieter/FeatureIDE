package de.ovgu.featureide.core.cide;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.ui.texteditor.ITextEditor;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ColorXmlManager {

	private File m_xmlFile;
	private String m_xmlFileName = "ColorAnnotations.xml";
	// private String m_xmlFullPath = "";
	private String m_projectPath = "";
	private DocumentBuilderFactory m_docFactory;
	private DocumentBuilder m_docBuilder;
	private Document m_doc = null;
	private Element m_rootElement;

	public ColorXmlManager(String projectPath) {
		/*
		 * Path (Projekt) + std-dateinamen --> m_xmlFullPath
		 */
		m_projectPath = projectPath;
		m_xmlFile = new File(m_projectPath, m_xmlFileName);

		try {
			/* std-document facttory handling */
			m_docFactory = DocumentBuilderFactory.newInstance();
			m_docBuilder = m_docFactory.newDocumentBuilder();
			m_doc = m_docBuilder.newDocument();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}

		if (m_xmlFile.exists()) {
			System.out.println("ColorAnnotations.xml already exists");
			this.readXml();
		} else {
			// kann es in Grundstruktur anlegen
			System.out.println("CREATE ColorAnnotations.xml");
			this.createXml();
		}
	}

	public void readXml() {
		try {
			m_doc = m_docBuilder.parse(m_xmlFile);
			m_rootElement = m_doc.getDocumentElement();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Kurzes Beschreibung
	 */
	private void createXml() {
		try {
			m_rootElement = m_doc.createElement("root");
			m_doc.appendChild(m_rootElement);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			DOMSource source = new DOMSource(m_doc);

			StreamResult result = new StreamResult(m_xmlFile);
			transformer.transform(source, result);

		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	public void addAnnotation(String activeProjectPath, Integer startLine, Integer endLine, String feature) {
		Element filesElement = checkOrCreateElement(m_rootElement,"files");
		Element fileElement = getFileNodeByFilename(activeProjectPath);
		if (fileElement == null) {
			// create file element for active file with start and end line numbers
			Element filesParent = checkOrCreateElement(filesElement,"file");
			setAttributeContent(filesParent, "path", activeProjectPath);
			//Element linesElement = checkOrCreateElement(fileElement, "lines");
			
			
			// add selected line
			Element linesParent = checkOrCreateElement(filesParent, "lines");
			Element lineParent = checkOrCreateElement(linesParent, "line");
			setAttributeContent(lineParent, "startLine", startLine.toString());
			setAttributeContent(lineParent, "endLine", endLine.toString());
			setAttributeContent(lineParent, "feature", feature);
			
		} else {
			Element possibleMatchedLine = getLineNodesByStartEndline(startLine, endLine);
			if(possibleMatchedLine == null){
				Element linesParent = checkOrCreateElement(fileElement, "lines");
				Element lineElement = checkOrCreateElement(linesParent, "line");
				//Element linesElement = m_doc.createElement("line");
				//linesParent.appendChild(lineElement);
				lineElement.setAttribute("startLine", startLine.toString());
				lineElement.setAttribute("endLine", endLine.toString());
				lineElement.setAttribute("feature", feature);
			}
			else{
			
				
			}
			// add selected line 
		}

		writeXml();
	}

	private Element checkOrCreateElement(Element parent, String elementTag) {
		NodeList elementList = parent.getElementsByTagName(elementTag);
		if (elementList != null && elementList.getLength() > 0) {
			return (Element) elementList.item(0);
		}
		Element element = m_doc.createElement(elementTag);
		parent.appendChild(element);
		return element;
	}

	private Element getFileNodeByFilename(String activeFile) {
		NodeList fileList = m_doc.getElementsByTagName("file");
		for (int i = 0; i < fileList.getLength();i++){
			Element fileElement = (Element) fileList.item(i);
			if(fileElement.getAttribute("path").equals(activeFile)){
				return fileElement;
			}
		}
		return null;
	}

	private Element getLineNodesByStartEndline(Integer startLine, Integer endLine) {
		
		NodeList lineList = m_doc.getElementsByTagName("line");

		for (int i = 0; i < lineList.getLength();i++){
			Element lineElement = (Element) lineList.item(i);
			if(lineElement.getAttribute("startLine").equals(startLine.toString()) && lineElement.getAttribute("endLine").equals(endLine.toString())){
				return lineElement;
			}
		}
		return null;
	}


	private void setAttributeContent(Element xmlElement, String attributeName,Object content) {
		if (!xmlElement.hasAttribute(attributeName)) {
			xmlElement.setAttribute(attributeName, content.toString());
		}
	}

	private void writeXml() {
		try {
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			DOMSource source = new DOMSource(m_doc);

			StreamResult output = new StreamResult(m_xmlFile);
			transformer.transform(source, output);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Document getParsedDocument() {
		// TODO: test ob das Document wirklich geparsed wurde
		// wenn nicht, this.readXml
		// if(m_doc != null)
		return m_doc;

	}
}
