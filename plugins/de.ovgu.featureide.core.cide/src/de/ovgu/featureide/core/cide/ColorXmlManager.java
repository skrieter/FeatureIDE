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

	public void addAnnotation(String activeProjectPath, Integer startLine,Integer endLine) {

		// prüfen/hinzufügen der <file> Elemente
		NodeList fileList = m_rootElement.getElementsByTagName("file");
	
		if (fileList != null && fileList.getLength() > 0) {

			if (fileList != null && fileList.getLength() > 0) {

				for (int i = 0; i < fileList.getLength(); i++) {
					
					Element fileElement = (Element) fileList.item(i);
					// prüfen/hinzufügen der <line> Elemente
					NodeList lineList = m_rootElement.getElementsByTagName("line");

					if (lineList != null && lineList.getLength() > 0) {
						
						for (int j = 0; j < lineList.getLength(); j++) {
							// Line
							Element lineElement = (Element) lineList.item(j);
							
							// Prüfe ob Attribut schon vorhanden
							if (!lineElement.hasAttribute("startline")) {
								lineElement.setAttribute("startline",startLine.toString());
							}
							if (!lineElement.hasAttribute("endline")) {
								lineElement.setAttribute("endline",endLine.toString());
							}
							if (!lineElement.hasAttribute("feature")) {
								lineElement.setAttribute("feature", "");
							} else {
								//wenn Attribut noch nicht vorhanden dann Anlegen
								if (!lineElement.getAttribute("startline").equals(startLine.toString())) {
									lineElement.setAttribute("startLine",startLine.toString());
								}
								if (!lineElement.getAttribute("endline").equals(endLine.toString())) {
									lineElement.setAttribute("endline",endLine.toString());
								}
								// Hier noch Abfrage für Feature
							}
						}
						// File
						// Prüfe ob Attribut schon vorhanden
						if (!fileElement.hasAttribute("path")) {
							fileElement.setAttribute("path", activeProjectPath);
						} else {
							if (!fileElement.getAttribute("path").equals(activeProjectPath)) {
								fileElement.setAttribute("path",activeProjectPath);
								System.out.println(fileElement.getAttribute("path"));
							}
						}

					} else {
						Element line = m_doc.createElement("line");
						m_rootElement.appendChild(line);
						line.setAttribute("startline", startLine.toString());
						line.setAttribute("endline", endLine.toString());
						line.setAttribute("feature", "");
					}
				}
			}
		}

		else {
			Element file = m_doc.createElement("file");
			m_rootElement.appendChild(file);
			file.setAttribute("path", activeProjectPath);
		}

		/*
		 * Element file = m_doc.createElement("file");
		 * m_rootElement.appendChild(file);
		 * 
		 * if (!file.hasAttribute("path")) { file.setAttribute("path",
		 * activeProjectPath); } else { if
		 * (!file.getAttribute("path").equals(activeProjectPath)) {
		 * file.setAttribute("path", activeProjectPath);
		 * System.out.println(file.getAttribute("path")); } }
		 * 
		 * // add line element Element line = m_doc.createElement("line");
		 * file.appendChild(line);
		 * 
		 * if (!line.hasAttribute("startline")) { line.setAttribute("startline",
		 * startLine.toString()); } if (!line.hasAttribute("endline")) {
		 * line.setAttribute("endline", endLine.toString()); } if
		 * (!line.hasAttribute("feature")) { line.setAttribute("feature", ""); }
		 * 
		 * // ist file für meine datei
		 * 
		 * // wenn nicht
		 * 
		 * // childnode.setTextContent("12-34-56");
		 * 
		 * // writing xml file
		 */
		writeXml();

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
