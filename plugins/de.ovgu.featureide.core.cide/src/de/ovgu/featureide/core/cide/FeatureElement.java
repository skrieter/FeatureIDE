package de.ovgu.featureide.core.cide;

import java.util.ArrayList;
import java.util.List;

public class FeatureElement {
	
	private String id;
	private List<SelectionElement> selections;
	
	public FeatureElement(){
		selections = new ArrayList<SelectionElement>();
	}
	
	public void addSelectionElement(SelectionElement le){
		selections.add(le);
	}
	
	public void addAllSelections(List<SelectionElement> les){
		selections.addAll(les);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<SelectionElement> getSelections() {
		return selections;
	}

}

	