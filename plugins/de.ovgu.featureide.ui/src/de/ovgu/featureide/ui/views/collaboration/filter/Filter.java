 package de.ovgu.featureide.ui.views.collaboration.filter;
 
import org.eclipse.jface.action.IAction;

 /**
  * TODO description
  * 
  */
 public enum Filter {

 	SHOW_FIELDS(new FilterAction("Show Fields")),
 	SHOW_METHODS(new FilterAction("Show Methods")),
 	SHOW_METHOD_CONTRACTS(new FilterAction("Show Method Contracts")),
 	SHOW_CLASS_INVARIANTS(new FilterAction("Show Class Invariants")),
 	HIDE_PARAMETERS(new FilterAction("Hide Parameter/Types")),
 	PUBLIC(new FilterAction("Public")),
 	PRIVATE(new FilterAction("Private")),
 	PROTECTED(new FilterAction("Protected")),
 	DEFAULT(new FilterAction("Default")),
 	SELECT_ALL(new FilterAction("Select All")),
 	DESELECT_ALL(new FilterAction("Deselect All"));
 	
 	private String filterName;
 	private IAction filterAction;
 	
 	Filter(IAction filterAction){
 		this.filterAction = filterAction;
 		
 	}
 	
 	public IAction getFilterAction(){
 		return this.filterAction;
 	}
 }