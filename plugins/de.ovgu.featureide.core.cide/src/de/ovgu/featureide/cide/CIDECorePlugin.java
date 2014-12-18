package de.ovgu.featureide.cide;

import org.osgi.framework.BundleContext;

import de.ovgu.featureide.fm.core.AbstractCorePlugin;

public class CIDECorePlugin extends AbstractCorePlugin {
	public static final String PLUGIN_ID = "de.ovgu.featureide.core.cide";
	
	private static CIDECorePlugin plugin;
	@Override
	public String getID() {
	
		return PLUGIN_ID;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}
	
	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static CIDECorePlugin getDefault() {
		return plugin;
	}
}
