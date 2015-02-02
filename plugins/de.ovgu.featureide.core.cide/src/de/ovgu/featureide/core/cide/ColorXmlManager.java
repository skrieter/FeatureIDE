package de.ovgu.featureide.core.cide;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ColorXmlManager {

	private File m_xmlFile;
	private String m_xmlFileName = "ColorAnnotations.xml";
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

	private void createXml() {
		try {
			m_rootElement = m_doc.createElement("root");
			m_doc.appendChild(m_rootElement);
			m_rootElement.appendChild(m_doc.createElement("files"));
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			DOMSource source = new DOMSource(m_doc);

			StreamResult result = new StreamResult(m_xmlFile);
			transformer.transform(source, result);

		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	private void writeXml() {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			DOMSource source = new DOMSource(m_doc);

			StreamResult output = new StreamResult(m_xmlFile);
			transformer.transform(source, output);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addAnnotation(String activeProjectPath, Integer startLine, Integer endLine, String feature) {

		javax.xml.xpath.XPathFactory factory = javax.xml.xpath.XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		try {
			String fileXPath = "root/files/file[@path='" + activeProjectPath + "']";
			String featureXPath = "feature[@id='" + feature + "']";
			String linesXPath = "line[@startline='" + startLine.toString() + "' and @endline='" + endLine.toString() + "']";

			XPathExpression expression = xpath.compile(fileXPath);
			Node fileNode = (Node) expression.evaluate(m_doc, XPathConstants.NODE);
			fileNode = appendFileElement(fileNode, activeProjectPath);

			expression = xpath.compile(featureXPath);
			Node featureElementNode = (Node) expression.evaluate(fileNode, XPathConstants.NODE);
			featureElementNode = appendFeatureElement(fileNode, featureElementNode, feature);

			expression = xpath.compile(linesXPath);
			Element lineElement = (Element) expression.evaluate(featureElementNode, XPathConstants.NODE);
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

	private Node appendLineElement(Node featureElementParent, Node lineElement, String startLine, String endLine) {
		if (lineElement == null) {
			lineElement = appendElement(featureElementParent, lineElement, "line");
			setAttributeContent((Element) lineElement, "startline", startLine);
			setAttributeContent((Element) lineElement, "endline", endLine);
		}

		return lineElement;

	}

	private Node appendFeatureElement(Node fileElementParent, Node featureElement, String featureName) {
		if (featureElement == null) {
			featureElement = appendElement(fileElementParent, featureElement, "feature");
			setAttributeContent((Element) featureElement, "id", featureName);
		}

		return featureElement;
	}

	private Node appendFileElement(Node fileElement, String pathName) {
		if (fileElement == null) {
			Node filesElement = m_rootElement.getElementsByTagName("files").item(0);
			fileElement = appendElement(filesElement, fileElement, "file");
			setAttributeContent((Element) fileElement, "path", pathName);
		}
		return fileElement;
	}

	private void setAttributeContent(Element xmlElement, String attributeName, Object content) {
		if (!xmlElement.hasAttribute(attributeName)) {
			xmlElement.setAttribute(attributeName, content.toString());
		}
	}

	private void removeElement(Node nodeToRemove) {
		nodeToRemove.getParentNode().removeChild(nodeToRemove);
	}

	// get a list of FeatureElement's for the given path
	public List<FeatureElement> getFeatureForPath(String path, boolean alsoFetchLines) {

		List<FeatureElement> featureList = new ArrayList<FeatureElement>();

		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		try {
			String featureForPathXPath = "root/files/file[@path='" + path + "']/feature";
			XPathExpression expr = xpath.compile(featureForPathXPath);
			NodeList featureNodeList = (NodeList) expr.evaluate(m_doc, XPathConstants.NODESET);

			for (int i = 0; i < featureNodeList.getLength(); i++) {
				FeatureElement fe = new FeatureElement();
				Node featureNode = featureNodeList.item(i);
				Element featureIdElement = (Element) featureNode;
				String featureId = featureIdElement.getAttribute("id");
				fe.setId(featureId);
				if (alsoFetchLines == true) {
					fe.addAllLines(getLinesForFeature(featureId, path));
				}
				featureList.add(fe);
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return featureList;

	}

	// get a list of LineElement's for feature which contains startline and endline
	public List<LineElement> getLinesForFeature(String featureName, String path) {

		List<LineElement> leList = new ArrayList<LineElement>();

		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		try {
			String linesForFeatureXPath = "root/files/file[@path='" + path + "']/feature[@id='" + featureName + "']/line";
			XPathExpression expr = xpath.compile(linesForFeatureXPath);
			NodeList lines = (NodeList) expr.evaluate(m_doc, XPathConstants.NODESET);

			for (int i = 0; i < lines.getLength(); i++) {
				LineElement le = new LineElement();
				Node lineNode = lines.item(i);
				Element lineElement = (Element) lineNode;
				le.setStartLine(lineElement.getAttribute("startline"));
				le.setEndLine(lineElement.getAttribute("endline"));
				leList.add(le);
			}

		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return leList;

	}

	public void deleteAnnotaion(String activeProjectPath, Integer markedStartLine, Integer markedEndLine, String feature) {
		javax.xml.xpath.XPathFactory factory = javax.xml.xpath.XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		try {
			String lineXPath = "root/files/file[@path='" + activeProjectPath + "']/feature[@id='" + feature + "']/line";
			XPathExpression expression = xpath.compile(lineXPath);
			NodeList lineNodes = (NodeList) expression.evaluate(m_doc, XPathConstants.NODESET);

			for (int i = 0; i < lineNodes.getLength(); i++) {
				Node lineElement = lineNodes.item(i);
				int endLineAttribute = Integer.parseInt(lineElement.getAttributes().item(0).getNodeValue());
				int startLineAttribute = Integer.parseInt(lineElement.getAttributes().item(1).getNodeValue());

				// remove area from startLine to endLine
				if (startLineAttribute == markedStartLine && endLineAttribute == markedEndLine) {
					removeElement(lineElement);
				}

				// remove start line(s)
				if (startLineAttribute == markedStartLine && endLineAttribute > markedEndLine) {
					markedEndLine++;
					lineElement.getAttributes().item(1).setTextContent(markedEndLine.toString());
				}

				// remove end line(s)
				if (startLineAttribute < markedStartLine && endLineAttribute == markedEndLine) {
					markedStartLine--;
					lineElement.getAttributes().item(0).setTextContent(markedStartLine.toString());
				}

				if (startLineAttribute < markedStartLine && endLineAttribute > markedEndLine) {
					markedStartLine--;
					markedEndLine++;
					lineElement.getAttributes().item(0).setTextContent(markedStartLine.toString());

					String featureXPath = "root/files/file[@path='" + activeProjectPath + "']/feature[@id='" + feature + "']";
					XPathExpression expr = xpath.compile(featureXPath);
					Node featureNode = (Node) expr.evaluate(m_doc, XPathConstants.NODE);
					Element element = m_doc.createElement("line");
					element.setAttribute("startline", markedEndLine.toString());
					element.setAttribute("endline", String.valueOf(endLineAttribute));
					featureNode.appendChild(element);

				}	

			}

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		writeXml();

	}

	// remove all annotaions from the selected feature 
	public void deleteFeatureAnnotation(String activeProjectPath, Integer markedStartLine, Integer markedEndLine, String feature) {

		javax.xml.xpath.XPathFactory factory = javax.xml.xpath.XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		try {
			String featureXPath = "root/files/file[@path='" + activeProjectPath + "']/feature[@id='" + feature + "']";
			XPathExpression expression = xpath.compile(featureXPath);
			Node featureNode = (Node) expression.evaluate(m_doc, XPathConstants.NODE);
			removeElement(featureNode);

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		writeXml();

	}
	
	// remove annotations of all features 
	public void removeAllAnnotations(String activeProjectPath) {

		javax.xml.xpath.XPathFactory factory = javax.xml.xpath.XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		try {
			String featureXPath = "root/files/file[@path='" + activeProjectPath + "']/feature";
			XPathExpression expression = xpath.compile(featureXPath);
			NodeList featureNodes = (NodeList) expression.evaluate(m_doc, XPathConstants.NODESET);
			for (int i = 0; i < featureNodes.getLength(); i++) {
				removeElement(featureNodes.item(i));
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		writeXml();
	}

	public Document getParsedDocument() {
		// TODO: test ob das Document wirklich geparsed wurde
		// wenn nicht, this.readXml
		// if(m_doc != null)
		return m_doc;

	}
}
