/* FeatureIDE - A Framework for Feature-Oriented Software Development
 * Copyright (C) 2005-2016  FeatureIDE team, University of Magdeburg, Germany
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
 * See http://featureide.cs.ovgu.de/ for further information.
 */
package de.ovgu.featureide.fm.core.base.event;

/**
 * Event triggered by changes to a feature model or its elements.
 * 
 * @author Sebastian Krieter
 */
public class FeatureIDEEvent {

	public enum EventType {
		CONSTRAINT_MOVE,
		CONSTRAINT_MODIFY,
		CONSTRAINT_DELETE,
		CONSTRAINT_ADD,
		CONSTRAINT_SELECTED,
		FEATURE_MODIFY,
		FEATURE_DELETE,
		FEATURE_ADD_ABOVE,
		FEATURE_ADD,
		FEATURE_NAME_CHANGED,
		COLOR_CHANGED,
		HIDDEN_CHANGED,
		LOCATION_CHANGED,
		ATTRIBUTE_CHANGED,
		GROUP_TYPE_CHANGED,
		PARENT_CHANGED,
		MANDATORY_CHANGED,
		STRUCTURE_CHANGED,
		LEGEND_LAYOUT_CHANGED,
		MODEL_LAYOUT_CHANGED,
		MODEL_DATA_CHANGED,
		MODEL_DATA_SAVED,
		MODEL_DATA_LOADED,
		REDRAW_DIAGRAM,
		REFRESH_ACTIONS, 
		CHILDREN_CHANGED,
	}
	
	static FeatureIDEEvent[] defaultEvents = new FeatureIDEEvent[EventType.values().length];
	static {
		for (EventType e : EventType.values()) {
			defaultEvents[e.ordinal()] = new FeatureIDEEvent(e);
		}
	}
	
	public static FeatureIDEEvent getDefault(final EventType e) {
		return defaultEvents[e.ordinal()];
	}
	
	private final Object source;
	private final EventType eventType;
	private final Object oldValue;
	private final Object newValue;

	private FeatureIDEEvent(EventType e) {
		this(null, e);
	}
	
	public FeatureIDEEvent(Object source, EventType eventType) {
		this(source, eventType, null, null);
	}

	public FeatureIDEEvent(Object source, EventType eventType, Object oldValue, Object newValue) {
		this.source = source;
		this.eventType = eventType;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public Object getSource() {
		return source;
	}

	public EventType getEventType() {
		return eventType;
	}

	public Object getOldValue() {
		return oldValue;
	}

	public Object getNewValue() {
		return newValue;
	}

	@Override
	public String toString() {
		return "FeatureIDEEvent [source=" + source + ", eventType=" + eventType + ", oldValue=" + oldValue
				+ ", newValue=" + newValue + "]";
	}
	
}
