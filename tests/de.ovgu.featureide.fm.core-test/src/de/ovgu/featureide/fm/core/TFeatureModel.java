/* FeatureIDE - A Framework for Feature-Oriented Software Development
 * Copyright (C) 2005-2015  FeatureIDE team, University of Magdeburg, Germany
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
package de.ovgu.featureide.fm.core;

import static org.junit.Assert.assertSame;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.ovgu.featureide.common.Commons;
import de.ovgu.jcorridore.JCorridore;
import de.ovgu.jcorridore.annotations.Constraint;
import de.ovgu.jcorridore.annotations.Record;

/**
 * Tests for the {@link FeatureModel}.
 * 
 * @author Jens Meinicke
 */
public class TFeatureModel {

	@Record(samples = 10)
	@Constraint(samples = 10, allowedMedianDeviation = 10)
    public void recordGetFeatureName(){
        FeatureModel fm = new FeatureModel();
        Feature feature = new Feature(fm, "test_root");
        fm.addFeature(feature);
        fm.setRoot(feature);
        Feature root = fm.getFeature("test_root");
        assertSame(root, fm.getRoot());
        
        FeatureModel clonedModel = fm.clone();
        Feature root2 = clonedModel.getFeature("test_root");
        
//        assertNotSame(root2, root);
//        assertEquals(root2, clonedModel.getRoot());
        
        assertSame(root2, clonedModel.getRoot());
	}
	
	@Test
    public void testGetFeatureName() {
		List<String> failedMethods = new JCorridore(Commons.getFile("/home/itidbrun/TeamCity/buildAgent/work/featureide/tests/de.ovgu.featureide.fm.core-test/src/recordtest/", "recordtest").getAbsolutePath(),  
				"stored_performances.db").run(TFeatureModel.class);
		Assert.assertEquals(Commons.join("\n", failedMethods), 0, failedMethods.size());
	}
}
