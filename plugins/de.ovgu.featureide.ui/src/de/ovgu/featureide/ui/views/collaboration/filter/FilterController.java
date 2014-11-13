
/* FeatureIDE  A Framework for FeatureOriented Software Development
 * Copyright (C) 20052013  FeatureIDE team, University of Magdeburg, Germany
 *
 * This file is part of FeatureIDE.
 * 
 * FeatureIDE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * FeatureIDE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with FeatureIDE.  If not, see <http://www.gnu.org/licenses/>.
 *
 * See http://www.fosd.de/featureide/ for further information.
 */
package de.ovgu.featureide.ui.views.collaboration.filter;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.function.Consumer;

/**
 * TODO description
 * 
 * @author Christopher Kruczek
 */
public class FilterController {
	
	private static final EnumSet<Filter> selectedFilter = EnumSet.noneOf(Filter.class);
	
	public static void addSelectedFilter(Filter filter){
		filter.getFilterAction().setChecked(true);
		selectedFilter.add(filter);
		handleSelectAll(filter);
		handleDeselectAll(filter);
		
	}
	public static void addSelectedFilter(EnumSet<Filter> filters){
		for(Filter f : filters){
			addSelectedFilter(f);
		}
	}
	
	public static void unselectFilter(Filter filter){
		filter.getFilterAction().setChecked(false);
		
		selectedFilter.remove(filter);
	}
	
	public static void unselectFilter(EnumSet<Filter> filters){
		for(Filter f : filters){
			unselectFilter(f);
		}
	}
	
	public static boolean isSelected(Filter filter){
		return selectedFilter.contains(filter);
	}
	
	public static boolean isSelected(Filter...filters){
		return selectedFilter.containsAll(Arrays.asList(filters));
	}
	
	public static EnumSet<Filter> getSelectedFilter(){
		return selectedFilter;
		
	}
	
	private static void handleSelectAll(Filter filter){
		if(filter.equals(Filter.SELECT_ALL)){
			selectedFilter.addAll(EnumSet.complementOf(EnumSet.of(Filter.DESELECT_ALL)));
			selectedFilter.forEach(new Consumer<Filter>(){
				@Override
				public void accept(Filter arg0) {
					arg0.getFilterAction().setChecked(true);
					
				}
			});
		}
	}
	
	private static void handleDeselectAll(Filter filter){
		if(filter.equals(Filter.DESELECT_ALL)){
			selectedFilter.forEach(new Consumer<Filter>(){
				@Override
				public void accept(Filter arg0) {
					arg0.getFilterAction().setChecked(false);
					
				}
			});
			selectedFilter.removeAll(EnumSet.allOf(Filter.class));
		}
	}
	
}
