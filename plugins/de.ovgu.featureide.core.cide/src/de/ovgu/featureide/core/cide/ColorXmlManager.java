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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;


public class ColorXmlManager {
	
	private File m_xmlFile;
	private String m_xmlFileName = "ColorAnnotations.xml";
	//private String m_xmlFullPath = "";
	private String m_projectPath = "";
	
	private DocumentBuilderFactory m_docFactory;
	private DocumentBuilder m_docBuilder;

	private Document m_doc = null;
	
	private Element m_rootElement;
	
	public ColorXmlManager(String projectPath)
	{
		/*
		 * Path (Projekt) + std-dateinamen --> m_xmlFullPath
		 */
		m_projectPath = projectPath;
		m_xmlFile = new File(m_projectPath, m_xmlFileName);
		
		try
		{
			/* std-document facttory handling */
			m_docFactory = DocumentBuilderFactory.newInstance();
			m_docBuilder = m_docFactory.newDocumentBuilder();
			m_doc = m_docBuilder.newDocument();
		}
		catch (ParserConfigurationException pce) 
		{
			pce.printStackTrace();
		}

		if(m_xmlFile.exists())
		{
			System.out.println("ColorAnnotations.xml already exists");
			this.readXml();
		}
		else
		{
			// kann es in Grundstruktur anlegen
			System.out.println("CREATE ColorAnnotations.xml");
			this.createXml();
		}
	}

	/*
	 * 
	 */
	public void readXml() 
	{
		try 
		{
			m_doc = m_docBuilder.parse(m_xmlFile);
			m_rootElement = m_doc.getDocumentElement();
		} 
		catch (SAXException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Kurzes Beschreibung
	 */
	private void createXml() {

		try 
		{
			m_rootElement = m_doc.createElement("root");
			m_doc.appendChild(m_rootElement);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			DOMSource source = new DOMSource(m_doc);

			StreamResult result = new StreamResult(m_xmlFile);
			transformer.transform(source, result);

			System.out.println("create ColorAnnotations.xml");

		} catch (TransformerException tfe) {

			tfe.printStackTrace();
		}

	}

	private void addAnnotation(/*übergebene Informationen (z.B. Linenumber, Feature, Path)*/) {
			
			Node list = m_doc.createElement("list");
			m_rootElement.appendChild(list);
			
			
			
			// ist file für meine datei
				
			
			// wenn nicht
			
			
		    //childnode.setTextContent("12-34-56");
		    
		    // writing xml file
			
			writeXml();
		    
	}
	
	private void writeXml()
	{
		try
		{
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	
			DOMSource source = new DOMSource(m_doc);
	
			StreamResult output = new StreamResult(m_xmlFile);
			transformer.transform(source, output);
		} 
		catch (Exception e) 
		{
		        e.printStackTrace();
	    }
		
	}
		
	
	public Document getParsedDocument()
	{
		// TODO: test ob das Document wirklich geparsed wurde
		// wenn nicht, this.readXml
		//if(m_doc != null)
		return m_doc;
		
	}
}
