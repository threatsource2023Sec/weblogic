package com.bea.httppubsub.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
   private static BundleContext bundleContext;

   public void start(BundleContext ctx) throws Exception {
      Class var2 = Activator.class;
      synchronized(Activator.class) {
         bundleContext = ctx;
      }
   }

   public void stop(BundleContext ctx) throws Exception {
   }

   public static synchronized BundleContext getBundleContext() {
      return bundleContext;
   }
}
