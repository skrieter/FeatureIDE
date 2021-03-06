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
package de.ovgu.featureide.core.mpl.io;

import java.nio.file.Paths;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import de.ovgu.featureide.core.mpl.InterfaceProject;
import de.ovgu.featureide.core.mpl.MPLPlugin;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.base.impl.FMFactoryManager;
import de.ovgu.featureide.fm.core.configuration.Configuration;
import de.ovgu.featureide.fm.core.io.IOConstants;
import de.ovgu.featureide.fm.core.io.manager.ConfigurationManager;
import de.ovgu.featureide.fm.core.io.manager.FileReader;
import de.ovgu.featureide.fm.core.io.xml.XmlFeatureModelReader;

/**
 * Reads certain local files from the project folder.
 * 
 * @author Sebastian Krieter
 */
public final class FileLoader {

	public static void loadConfiguration(InterfaceProject interfaceProject) {
		try {
			final IFile configFile = interfaceProject.getFeatureProjectReference().getCurrentConfiguration();
			final FileReader<Configuration> reader = new FileReader<>();
			reader.setObject(interfaceProject.getConfiguration());
			reader.setPath(Paths.get(configFile.getLocationURI()));
			reader.setFormat(ConfigurationManager.getFormat(configFile.getName()));
		} catch (Exception e) {
			MPLPlugin.getDefault().logError(e);
		}
	}

	public static IFeatureModel loadFeatureModel(IProject project) {
		try {
			IFeatureModel featureModel = FMFactoryManager.getFactory().createFeatureModel();
			XmlFeatureModelReader reader = new XmlFeatureModelReader(featureModel);
			reader.readFromFile(project.getFile(IOConstants.FILENAME_MODEL).getLocation().toFile());
			return featureModel;
		} catch (Exception e) {
			MPLPlugin.getDefault().logError(e);
			return null;
		}
	}
}
