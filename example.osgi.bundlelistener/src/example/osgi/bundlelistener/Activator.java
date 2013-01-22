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
package example.osgi.bundlelistener;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;

public class Activator implements BundleActivator, BundleListener {
	
	private static String typeAsString(BundleEvent event) {
		if (event == null) {
			return "null";
		}
		int type = event.getType();
		switch (type) {
		case BundleEvent.INSTALLED:
			return "INSTALLED";
		case BundleEvent.LAZY_ACTIVATION:
			return "LAZY_ACTIVATION";
		case BundleEvent.RESOLVED:
			return "RESOLVED";
		case BundleEvent.STARTED:
			return "STARTED";
		case BundleEvent.STARTING:
			return "Starting";
		case BundleEvent.STOPPED:
			return "STOPPED";
		case BundleEvent.UNINSTALLED:
			return "UNINSTALLED";
		case BundleEvent.UNRESOLVED:
			return "UNRESOLVED";
		case BundleEvent.UPDATED:
			return "UPDATED";
		default:
			return "unknown event type: " + type;
		}
	}

	public void start(BundleContext context) throws Exception {
		System.out.println("Starting Bundle Listener - " + context.hashCode());
		context.addBundleListener(this);
	}

	public void stop(BundleContext context) throws Exception {
		System.out.println("Stopping Bundle Listener - " + context.hashCode());
		context.removeBundleListener(this);
	}

	public void bundleChanged(BundleEvent event) {
		String symbolicName = event.getBundle().getSymbolicName();
		String type = typeAsString(event);
		System.out.println("BundleChanged: " + symbolicName + ", event.type: " + type);
	}

}
