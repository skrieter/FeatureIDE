package de.ovgu.featureide.core.cide;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.ovgu.featureide.cide.CIDECorePlugin;
import de.ovgu.featureide.core.IFeatureProject;

public class CIDEConfiguration {

	static Hashtable<String, Object> symbols = new Hashtable<String, Object>(2);
	int errors = 0;
	int line = 1;
	String inName;
	BufferedReader in;
	PrintWriter out;
	boolean printing = true;
	String source = null;
	String block = null;

	final int EOF = 0;
	final int COMMENT = 1; // text surrounded by /* */ delimiters
	final int CODE = 2; // can just be whitespace

	private IFeatureProject featureProject;

	public CIDEConfiguration() {

	}

	public CIDEConfiguration(String inName, String outName, IFeatureProject featureProject) {
		this.featureProject = featureProject;
		this.inName = inName;
		if (inName == null) {
			in = new BufferedReader(new InputStreamReader(System.in));
		} else {
			try {
				in = new BufferedReader(new FileReader(inName));
			} catch (FileNotFoundException fnf) {
				CIDECorePlugin.getDefault().logWarning("Cannot find input file " + inName);
				errors++;
				return;
			}
		}

		if (outName == null) {
			out = new PrintWriter(System.out);
		} else {
			try {
				out = new PrintWriter(new FileWriter(outName));
			} catch (IOException ioe) {
				CIDECorePlugin.getDefault().logError("Cannot write to file " + outName, ioe);
				errors++;
			}
		}
	}

	public void close() throws IOException {
		in.close();
		out.flush();
		out.close();
	}

	public static int countLines(String filename) throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(filename));
		try {
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = is.read(c)) != -1) {
				empty = false;
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n') {
						++count;
					}
				}
			}
			return (count == 0 && !empty) ? 1 : count;
		} finally {
			is.close();
		}
	}

	public void process(ArrayList<String> activeFeatures) throws IOException {
		// Read all of file into a single stream for easier scanning.
		ColorXmlManager colorXmlManager = new ColorXmlManager(featureProject.getProject().getLocation().toFile().getAbsolutePath());
		StringWriter sw = new StringWriter();
		String lineContent;
		int lineNumber = 1;
		colorXmlManager.readXml();
		while ((lineContent = in.readLine()) != null) {
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			boolean marked = false;
			//boolean written_out = false;
			try {
				//for (int i = 0; i < activeFeatures.size(); i++) {
					String linesAllXPath = "root/files/file[@path='" + inName + "']/feature/line";
					XPathExpression exprall = xpath.compile(linesAllXPath);
					NodeList linesall = (NodeList) exprall.evaluate(colorXmlManager.getParsedDocument(), XPathConstants.NODESET);
					//written_out = false;
					for (int j = 0; j < linesall.getLength(); j++) {
						Node lineNodeAll = linesall.item(j);
						Integer startLine = Integer.parseInt(lineNodeAll.getAttributes().item(1).getTextContent());
						Integer endLine = Integer.parseInt(lineNodeAll.getAttributes().item(0).getTextContent());
						if (lineNumber >= startLine && lineNumber <= endLine) {
							//sw.write(lineContent);
							//out.println(lineContent);
							// markiert
							marked = true;
							break;
						}
						else
						{
							marked = false;
						}
					}

					if(marked)
					{
						boolean written;
						for (int i = 0; i < activeFeatures.size(); i++) {
							written = false;
							String linesForFeatureXPath = "root/files/file[@path='" + inName + "']/feature[@id='"+activeFeatures.get(i)+"']/line";
							XPathExpression expr = xpath.compile(linesForFeatureXPath);
							NodeList lines = (NodeList) expr.evaluate(colorXmlManager.getParsedDocument(), XPathConstants.NODESET);
							for (int k = 0; k < lines.getLength(); k++) {
								Node lineNode = lines.item(k);
								Integer startLineinner = Integer.parseInt(lineNode.getAttributes().item(1).getTextContent());
								Integer endLineinner = Integer.parseInt(lineNode.getAttributes().item(0).getTextContent());
								if (lineNumber >= startLineinner && lineNumber <= endLineinner) {
									sw.write(lineContent);
									out.println(lineContent);
									written = true;
									//written_out = true;
									break;
								}
							}
							if(written) break;
						}
						//if(written_out) break;
					}
					else
					{
						sw.write(lineContent);
						out.println(lineContent);
						//break;
					}
					
				
			} catch (XPathExpressionException e) {
				e.printStackTrace();
			}
			lineNumber++;
		}
		source = sw.toString();
	}

	public static void usage(String msg) {
		CIDECorePlugin.getDefault().logWarning(msg);
	}

	public boolean hasErrors() {
		return (errors > 0);
	}

	public void printErrorCount() {
	}

	public void main(String[] args, IFeatureProject featureProject) {
		this.featureProject = featureProject;
		// Use a dummy object as the hash entry value.
	//	Object obj = new Object();

		// Load symbol definitions
		int iArg = 0;
		//symbols.clear();
		ArrayList<String> activeFeatures = new ArrayList<String>();
		activeFeatures.clear();
		while (iArg < args.length && args[iArg].startsWith("-")) {
			if (args[iArg].startsWith("-")) {
				String symbol = args[iArg].substring(1);
			//	symbols.put(symbol, obj);
				activeFeatures.add(symbol);
			}

			iArg++;
		}

		// Parse file name arguments into an array of input file names and
		// output file names.
		String[] inFiles = new String[Math.max(args.length - iArg - 1, 1)];
		String[] outFiles = new String[inFiles.length];

		if (iArg < args.length) {
			File targetDir = new File(args[args.length - 1]);
			if (targetDir.isDirectory()) {
				int i = 0;
				for (; iArg < args.length - 1; i++, iArg++) {
					inFiles[i] = args[iArg];
					File inFile = new File(args[iArg]);
					File outFile = new File(targetDir, inFile.getName());
					outFiles[i] = outFile.getAbsolutePath();
				}
				if (i == 0) {
					usage("No source files specified.");
				}
			} else {
				inFiles[0] = args[iArg++];
				if (iArg < args.length) {
					outFiles[0] = args[iArg++];
				}
				if (iArg < args.length) {
					usage(args[args.length - 1] + " is not a directory.");
				}
			}
		}

		for (int i = 0; i < inFiles.length; i++) {

			CIDEConfiguration cideConfiguration = new CIDEConfiguration(inFiles[i], outFiles[i], featureProject);
			if (cideConfiguration.hasErrors()) {
				cideConfiguration.printErrorCount();

				// System.exit(munge.errors);
			}

			try {
				System.out.println(countLines(inFiles[i]) + 1);
				cideConfiguration.process(activeFeatures);
				cideConfiguration.close();
			} catch (IOException e) {
				CIDECorePlugin.getDefault().logError(e);
			}

			if (cideConfiguration.hasErrors()) {
				cideConfiguration.printErrorCount();
				// System.exit(munge.errors);
			}
		}

		// System.exit(0);
	}
}
