/*******************************************************************************
 * Copyright (c) 2012-2013, EclipseSource Inc
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Elias Volanakis - initial API and implementation
 *******************************************************************************/
package example.eclipse.bundlewatcher;

import org.eclipse.osgi.baseadaptor.HookConfigurator;
import org.eclipse.osgi.baseadaptor.HookRegistry;
import org.eclipse.osgi.framework.adaptor.BundleWatcher;
import org.osgi.framework.Bundle;

/**
 * <b>IMPORTANT</b>:
 * <ul>
 * <li>needs the project 'org.eclipse.osgi' in workspace (in source form and in
 * the same parent directory as 'example.eclipse.bundlewatcher')</li>
 * <li>add the following flag to the vm properties (or config.ini):<br/>
 * {@code -Dosgi.framework.extensions=example.eclipse.bundlewatcher}</li>
 * </ul>
 * 
 * @see <a
 *      href="http://www.eclipsecon.org/2008/sub/attachments/Equinox_Framework_How_to_get_Hooked.pdf">http://www.eclipsecon.org/2008/sub/attachments/Equinox_Framework_How_to_get_Hooked.pdf</a>
 * @see <a
 *      href="http://www.eclemma.org/research/instrumentingosgi/index.html">http://www.eclemma.org/research/instrumentingosgi/index.html</a>
 * @see <a
 *      href="http://wiki.eclipse.org/Adaptor_Hooks">http://wiki.eclipse.org/Adaptor_Hooks</a>
 */
public class BundleWatcherConfigurator implements HookConfigurator,
		BundleWatcher {

	public BundleWatcherConfigurator() {
		System.out.println("BundleWatcherConfigurator()");
	}

	public void addHooks(HookRegistry hookRegistry) {
		System.out.println("BundleWatcherConfigurator.addHooks()");
		hookRegistry.addWatcher(this);
	}

	public void watchBundle(Bundle bundle, int type) {
		String symbolicName = bundle.getSymbolicName();
		String state = stateAsString(bundle);
		String typeStr = typeAsString(type);
		System.err.println("[BW] " + symbolicName + ", state: " + state
				+ ", type: " + typeStr);
	}

	private static String stateAsString(Bundle bundle) {
		if (bundle == null) {
			return "null";
		}
		int state = bundle.getState();
		switch (state) {
		case Bundle.ACTIVE:
			return "ACTIVE";
		case Bundle.INSTALLED:
			return "INSTALLED";
		case Bundle.RESOLVED:
			return "RESOLVED";
		case Bundle.STARTING:
			return "STARTING";
		case Bundle.STOPPING:
			return "STOPPING";
		case Bundle.UNINSTALLED:
			return "UNINSTALLED";
		default:
			return "unknown bundle state: " + state;
		}
	}

	private static String typeAsString(int eventType) {
		switch (eventType) {
		case START_INSTALLING:
			return "START_INSTALLING";
		case END_INSTALLING:
			return "END_INSTALLING";
		case START_ACTIVATION:
			return "START_ACTIVATION";
		case END_ACTIVATION:
			return "END_ACTIVATION";
		case START_DEACTIVATION:
			return "START_DEACTIVATION";
		case END_DEACTIVATION:
			return "END_DEACTIVATION";
		case START_UNINSTALLING:
			return "START_UNINSTALLING";
		case END_UNINSTALLING:
			return "END_UNINSTALLING";
		default:
			return "unknown bundle watcher event type: " + eventType;
		}
	}
}
