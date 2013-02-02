/*******************************************************************************
 * Copyright (c) 2012, EclipseSource Inc
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Elias Volanakis - initial API and implementation
 *******************************************************************************/
package example.osgi.bundletracker;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

public class Activator implements BundleActivator {
	
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

	private MyBundleTracker bundleTracker;

	public void start(BundleContext context) throws Exception {
		System.out.println("Starting Bundle Tracker");
		int trackStates = Bundle.STARTING | Bundle.STOPPING | Bundle.RESOLVED | Bundle.INSTALLED | Bundle.UNINSTALLED;
		bundleTracker = new MyBundleTracker(context, trackStates, null);
		bundleTracker.open();
	}

	public void stop(BundleContext context) throws Exception {
		System.out.println("Stopping Bundle Tracker");
		bundleTracker.close();
		bundleTracker = null;
	}
	
	private static final class MyBundleTracker extends BundleTracker {

		public MyBundleTracker(BundleContext context, int stateMask,
				BundleTrackerCustomizer customizer) {
			super(context, stateMask, customizer);
		}

		public Object addingBundle(Bundle bundle, BundleEvent event) {
			// Typically we would inspect bundle, to figure out if we want to
			// track it or not. If we don't want to track return null, otherwise
			// return an object.
			print(bundle, event);
			return bundle;
		}

		private void print(Bundle bundle, BundleEvent event) {
			String symbolicName = bundle.getSymbolicName();
			String state = stateAsString(bundle);
			String type = typeAsString(event);
			System.out.println("[BT] " + symbolicName + ", state: " + state + ", event.type: " + type);
		}
		
		public void removedBundle(Bundle bundle, BundleEvent event,
				Object object) {
			print(bundle, event);
		}
		
		public void modifiedBundle(Bundle bundle, BundleEvent event,
				Object object) {
			print(bundle, event);
		}
	}

}
