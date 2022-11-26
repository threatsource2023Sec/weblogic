package org.glassfish.hk2.osgiresourcelocator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
   public void start(BundleContext context) throws Exception {
      ServiceLoaderImpl serviceLoader = new ServiceLoaderImpl();
      serviceLoader.trackBundles();
      ServiceLoader.initialize(serviceLoader);
      ResourceFinderImpl resourceFinder = new ResourceFinderImpl();
      ResourceFinder.initialize(resourceFinder);
   }

   public void stop(BundleContext context) throws Exception {
      ServiceLoader.reset();
   }
}
