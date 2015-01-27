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
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
			/* std-document factory handling */
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

	public void addAnnotation(String activeProjectPath, Integer startLine,
			Integer endLine, String feature) {

		javax.xml.xpath.XPathFactory factory = javax.xml.xpath.XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		try {
			String fileXPath = "root/files/file[@path='" + activeProjectPath + "']";
			String featureXPath = "feature[@id='"+feature+"']";
			String featureAndLinesXPath = "line[@startline='"+startLine.toString()+"' and @endline='"+endLine.toString()+"']";
			
			XPathExpression expression = xpath.compile(fileXPath);
			Node fileNode = (Node) expression.evaluate(m_doc,XPathConstants.NODE);
			fileNode = appendFileElement(fileNode, activeProjectPath);
			
			expression = xpath.compile(featureXPath);
			Node featureElementNode = (Node)expression.evaluate(fileNode, XPathConstants.NODE);
			featureElementNode = appendFeatureElement(fileNode, featureElementNode, feature);
			
			expression = xpath.compile(featureAndLinesXPath);
			Element lineElement = (Element)expression.evaluate(featureElementNode, XPathConstants.NODE);
			appendLineElement(featureElementNode, lineElement, startLine.toString(), endLine.toString());
			
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		writeXml();
	}

	private Node appendElement(Node parent, Node child, String tagName) {
		if (child == null) {
			child = m_doc.createElement(tagName);
			parent.appendChild(child);
			return child;
		}
		return (Element) parent.appendChild(child);
	}
	
	private Node appendLineElement(Node featureElementParent, Node lineElement,String startLine, String endLine){
		if(lineElement == null){
			lineElement = appendElement(featureElementParent, lineElement, "line");
			setAttributeContent((Element)lineElement, "startline", startLine);
			setAttributeContent((Element)lineElement, "endline", endLine);
		}
		
		return lineElement;
		
	}
	
	private Node appendFeatureElement(Node fileElementParent, Node featureElement,String featureName){
		if(featureElement == null){
			featureElement = appendElement(fileElementParent, featureElement, "feature");
			setAttributeContent((Element)featureElement, "id", featureName);
		}
		
		return featureElement;
	}
	
	private Node appendFileElement(Node fileElement,String pathName){
		if(fileElement == null){
			Element filesElement = m_doc.createElement("files");
			m_rootElement.appendChild(filesElement);
			fileElement = appendElement(filesElement, fileElement, "file");
			setAttributeContent((Element)fileElement, "path", pathName);
		}
		return fileElement;
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
