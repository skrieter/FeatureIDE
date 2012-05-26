/* FeatureIDE - An IDE to support feature-oriented software development
 * Copyright (C) 2005-2011  FeatureIDE Team, University of Magdeburg
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see http://www.gnu.org/licenses/.
 *
 * See http://www.fosd.de/featureide/ for further information.
 */
package de.ovgu.featureide.core.typecheck.helper;

/**
 * TODO description
 * 
 * @author S�nke Holthusen
 */
public class Timer {
	private long _time_passed = 0;
	private long _time_last_start = 0;
	private boolean _started = false;

	public void start() {
		_time_passed = 0;
		_started = true;
		_time_last_start = System.currentTimeMillis();
	}

	public void stop() {
		_time_passed += System.currentTimeMillis() - _time_last_start;
		_started = false;
	}

	public void resume() {
		_started = true;
		_time_last_start = System.currentTimeMillis();
	}

	public void reset() {
		_started = false;
		_time_passed = 0;
		_time_last_start = 0;
	}

	public long getTime() {
		if (!_started) {
			return _time_passed;
		} else {
			return _time_passed
					+ (System.currentTimeMillis() - _time_last_start);
		}
	}
}
