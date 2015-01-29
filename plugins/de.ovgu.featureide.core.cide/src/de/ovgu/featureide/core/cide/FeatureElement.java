package de.ovgu.featureide.core.cide;

import java.util.ArrayList;
import java.util.List;

public class FeatureElement {
	
	private String id;
	private List<LineElement> lines;
	
	public FeatureElement(){
		lines = new ArrayList<LineElement>();
	}
	
	public void addLineElement(LineElement le){
		lines.add(le);
	}
	
	public void addAllLines(List<LineElement> les){
		lines.addAll(les);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<LineElement> getLines() {
		return lines;
	}

}

	