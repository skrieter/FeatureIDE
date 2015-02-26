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

	protected void createXml() {
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

	public void addAnnotation(String activeProjectPath, Integer offset, Integer offsetEnd, String feature) {
		
		javax.xml.xpath.XPathFactory factory = javax.xml.xpath.XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		try {
			String fileXPath = "root/files/file[@path='" + activeProjectPath + "']";
			String featureXPath = "feature[@id='" + feature + "']";
			String selectionsXPath = "selection[@offset='" + offset.toString() + "' and @offsetEnd='" + offsetEnd.toString() + "']";

			XPathExpression expression = xpath.compile(fileXPath);
			Node fileNode = (Node) expression.evaluate(m_doc, XPathConstants.NODE);
			fileNode = appendFileElement(fileNode, activeProjectPath);

			expression = xpath.compile(featureXPath);
			Node featureElementNode = (Node) expression.evaluate(fileNode, XPathConstants.NODE);
			featureElementNode = appendFeatureElement(fileNode, featureElementNode, feature);

			expression = xpath.compile(selectionsXPath);
			Element selectionElement = (Element) expression.evaluate(featureElementNode, XPathConstants.NODE);
			appendSelectionElement(featureElementNode, selectionElement, offset.toString(), offsetEnd.toString());

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

	private Node appendSelectionElement(Node featureElementParent, Node selectionElement, String startLine, String endLine) {
		if (selectionElement == null) {
			selectionElement = appendElement(featureElementParent, selectionElement, "selection");
			setAttributeContent((Element) selectionElement, "offset", startLine);
			setAttributeContent((Element) selectionElement, "offsetEnd", endLine);
		}

		return selectionElement;
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
					fe.addAllSelections(getLinesForFeature(featureId, path));
				}
				featureList.add(fe);
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return featureList;

	}

	// get a list of SelectionElement's for feature which contains offset and offsetEnd
	public List<SelectionElement> getLinesForFeature(String featureName, String path) {
		List<SelectionElement> leList = new ArrayList<SelectionElement>();

		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		try {
			String linesForFeatureXPath = "root/files/file[@path='" + path + "']/feature[@id='" + featureName + "']/selection";
			XPathExpression expr = xpath.compile(linesForFeatureXPath);
			NodeList selections = (NodeList) expr.evaluate(m_doc, XPathConstants.NODESET);

			for (int i = 0; i < selections.getLength(); i++) {
				SelectionElement le = new SelectionElement();
				Node selectionNode = selections.item(i);
				Element selectionElement = (Element) selectionNode;
				le.setOffset(selectionElement.getAttribute("offset"));
				le.setOffsetEnd(selectionElement.getAttribute("offsetEnd"));
				leList.add(le);
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return leList;
	}

	public void deleteAnnotaion(String activeProjectPath, Integer markedOffset, Integer markedOffsetEnd, String feature) {
		javax.xml.xpath.XPathFactory factory = javax.xml.xpath.XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		try {
			String lineXPath = "root/files/file[@path='" + activeProjectPath + "']/feature[@id='" + feature + "']/selection";
			XPathExpression expression = xpath.compile(lineXPath);
			NodeList selectionNodes = (NodeList) expression.evaluate(m_doc, XPathConstants.NODESET);

			String featureXPath = "root/files/file[@path='" + activeProjectPath + "']/feature[@id='" + feature + "']";
			XPathExpression expr = xpath.compile(featureXPath);
			Node featureNode = (Node) expr.evaluate(m_doc, XPathConstants.NODE);

			for (int i = 0; i < selectionNodes.getLength(); i++) {
				Node selectionElement = selectionNodes.item(i);
				int offsetEndAttribute = Integer.parseInt(selectionElement.getAttributes().getNamedItem("offsetEnd").getNodeValue());
				int offsetAttribute = Integer.parseInt(selectionElement.getAttributes().getNamedItem("offset").getNodeValue());

				// remove area from offset to offsetEnd
				if (offsetAttribute == markedOffset && offsetEndAttribute == markedOffsetEnd) {
					removeNode(selectionElement);
				}

				// remove offset(s)
				if (offsetAttribute == markedOffset && offsetEndAttribute > markedOffsetEnd) {
					markedOffsetEnd++;
					selectionElement.getAttributes().getNamedItem("offset").setTextContent(markedOffsetEnd.toString());
				}

				// remove offsetEnd(s)
				if (offsetAttribute < markedOffset && offsetEndAttribute == markedOffsetEnd) {
					markedOffset--;
					selectionElement.getAttributes().getNamedItem("offsetEnd").setTextContent(markedOffset.toString());
				}
				// remove area between offset and offsetEnd
				if (offsetAttribute < markedOffset && offsetEndAttribute > markedOffsetEnd) {
					selectionElement.getAttributes().getNamedItem("offsetEnd").setTextContent(markedOffset.toString());

					Element element = m_doc.createElement("selection");
					element.setAttribute("offset", markedOffsetEnd.toString());
					element.setAttribute("offsetEnd", String.valueOf(offsetEndAttribute));
					featureNode.appendChild(element);
				}
			}
			// update selectionNodes
			selectionNodes = (NodeList) expression.evaluate(m_doc, XPathConstants.NODESET);

			if (selectionNodes.getLength() == 0) {
				removeNode(featureNode);
			}

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		writeXml();
	}

	// remove all annotations from the selected feature
	public void deleteFeatureAnnotation(String activeProjectPath, String feature) {
		javax.xml.xpath.XPathFactory factory = javax.xml.xpath.XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		try {
			String featureXPath = "root/files/file[@path='" + activeProjectPath + "']/feature[@id='" + feature + "']";
			XPathExpression expression = xpath.compile(featureXPath);
			Node featureNode = (Node) expression.evaluate(m_doc, XPathConstants.NODE);
			removeNode(featureNode);
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
				removeNode(featureNodes.item(i));
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		writeXml();
	}

	private void removeNode(Node nodeToRemove) {
		nodeToRemove.getParentNode().removeChild(nodeToRemove);
	}

	private boolean compareAndMergeNodes(Node node1, Node node2) {
		// node1
		int offset1 = Integer.parseInt(node1.getAttributes().getNamedItem("offset").getTextContent()); 
		int offsetEnd1 = Integer.parseInt(node1.getAttributes().getNamedItem("offsetEnd").getTextContent());
		// node2
		int offset2 = Integer.parseInt(node2.getAttributes().getNamedItem("offset").getTextContent());
		int offsetEnd2 = Integer.parseInt(node2.getAttributes().getNamedItem("offsetEnd").getTextContent());

		// New marked area above or below
		if ((offsetEnd1) == (offset2)) {
			node1.getAttributes().getNamedItem("offsetEnd").setTextContent(String.valueOf(offsetEnd2));
			removeNode(node2);
			return true;
		}
		// New marked area inside
		if (offset2 >= offset1 && offsetEnd2 <= offsetEnd1) {
			removeNode(node2);
			return true;
		}
		// New marked area overlap
		if (offsetEnd2 > offset1 && offsetEnd2 <= offsetEnd1){
			node1.getAttributes().getNamedItem("offset").setTextContent(String.valueOf(offset2));
			removeNode(node2);
			return true;
		}
		return false;
	}

	public boolean mergeSelections(String activeProjectPath, String feature) {
		javax.xml.xpath.XPathFactory factory = javax.xml.xpath.XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		try {
			String selectionXPath = "root/files/file[@path='" + activeProjectPath + "']/feature[@id='" + feature + "']/selection";
			XPathExpression expression = xpath.compile(selectionXPath);
			NodeList selections = (NodeList) expression.evaluate(m_doc, XPathConstants.NODESET);
			for (int i = 0; i < selections.getLength(); i++) {
				Node node1 = selections.item(i);
				for (int j = 0; j < selections.getLength(); j++) {
					Node node2 = selections.item(j);
					if (!node1.equals(node2)) {
						if (compareAndMergeNodes(node1, node2)) {
							writeXml();
							return true;
						}

					}
				}
			}

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Document getParsedDocument() {
		// TODO: test ob das Document wirklich geparsed wurde
		// wenn nicht, this.readXml
		// if(m_doc != null)
		return m_doc;

	}
}
