package de.ovgu.featureide.core.cide;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IResource;

import de.ovgu.featureide.core.IFeatureProject;
import de.ovgu.featureide.core.fstmodel.preprocessor.FSTDirective;
import de.ovgu.featureide.core.fstmodel.preprocessor.FSTDirectiveCommand;

public class ColorAnnotationManager {

	private ColorXmlManager m_colorxmlManager;

	FeatureElement featureElement = new FeatureElement();
	LineElement lineElement = new LineElement();

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
	
			for (LineElement line : feature.getLines()) {
				
				FSTDirective d = new FSTDirective();
				d.setFeatureName(feature.getId());
				d.setLine(Integer.parseInt(line.getStartLine()));
				d.setStartLine(Integer.parseInt(line.getStartLine())-1, 0);
				d.setEndLine(Integer.parseInt(line.getEndLine()), 0);
				d.setExpression("At line:  " + Integer.parseInt(line.getStartLine()) + " - " + Integer.parseInt(line.getEndLine()));
				d.setCommand(FSTDirectiveCommand.COLOR);
				d.setId(id++);
				directives.add(d);	
			}
		}
		return directives;	
		
	}
}
