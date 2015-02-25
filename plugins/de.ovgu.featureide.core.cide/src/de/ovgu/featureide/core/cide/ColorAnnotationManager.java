package de.ovgu.featureide.core.cide;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;
import org.eclipse.ui.texteditor.IDocumentProvider;

import de.ovgu.featureide.core.IFeatureProject;
import de.ovgu.featureide.core.fstmodel.preprocessor.FSTDirective;
import de.ovgu.featureide.core.fstmodel.preprocessor.FSTDirectiveCommand;

public class ColorAnnotationManager {

	private ColorXmlManager m_colorxmlManager;

	FeatureElement featureElement = new FeatureElement();
	SelectionElement lineElement = new SelectionElement();

	// Constructor
	public ColorAnnotationManager(ColorXmlManager colorXmlManager) {
		m_colorxmlManager = colorXmlManager;
	}

	protected LinkedList<FSTDirective> getDirectives(Vector<String> lines, IFeatureProject featureProject, IResource res) {
		
		LinkedList<FSTDirective> directives = new LinkedList<FSTDirective>();
		int id = 0;
		String pathToFile = res.getLocation().toFile().getAbsolutePath();
		List<FeatureElement> featureList = m_colorxmlManager.getFeatureForPath(pathToFile, true);
		
		for (FeatureElement feature : featureList) {
	
			for (SelectionElement selection : feature.getSelections()) {
				
				FSTDirective d = new FSTDirective();
				d.setFeatureName(feature.getId());
			//	d.setLine(Integer.parseInt(line.getStartLine()));
				IDocumentProvider provider = new TextFileDocumentProvider();
				try {
					provider.connect(res);
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				IDocument document = provider.getDocument(res);
					
				try {
					int startLine = document.getLineOfOffset(Integer.parseInt(selection.getOffset()));
					int endLine = document.getLineOfOffset(Integer.parseInt(selection.getOffsetEnd()));
				
					int startLineOffset = getPrefixLength(Integer.parseInt(selection.getOffset()), document, startLine);
					int endLineOffset = getPrefixLength(Integer.parseInt(selection.getOffsetEnd()), document, endLine);
					
					d.setStartLine(startLine, startLineOffset);
					d.setEndLine(endLine, endLineOffset);
					d.setExpression("StartLine: " + (startLine+1) + "  Offset " + startLineOffset + 
							"\n Endline:  " + (endLine+1) + " OffsetEnd: " + endLineOffset);

				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				d.setCommand(FSTDirectiveCommand.COLOR);
				d.setId(id++);
				directives.add(d);	
				provider.disconnect(res);
			}
		}
		return directives;	
		
	}

	private int getPrefixLength(int offset, IDocument document, int startLine) throws BadLocationException {
		int preLength = 0;
		for(int i = 0; i< startLine; i++){
			preLength+= document.getLineLength(i);
		}
		int startLineOffset = offset - preLength;
		return startLineOffset;
	}
}
