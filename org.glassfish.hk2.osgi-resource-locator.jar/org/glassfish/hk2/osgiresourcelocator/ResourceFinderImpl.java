package org.glassfish.hk2.osgiresourcelocator;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleReference;

public class ResourceFinderImpl extends ResourceFinder {
   private BundleContext bundleContext;

   public ResourceFinderImpl() {
      ClassLoader cl = this.getClass().getClassLoader();
      if (cl instanceof BundleReference) {
         this.bundleContext = ((BundleReference)BundleReference.class.cast(cl)).getBundle().getBundleContext();
      }

      if (this.bundleContext == null) {
         throw new RuntimeException("There is no bundle context available yet. Instatiate this class in STARTING or ACTIVE state only");
      }
   }

   URL findEntry1(String path) {
      Bundle[] arr$ = this.bundleContext.getBundles();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Bundle bundle = arr$[i$];
         URL url = bundle.getEntry(path);
         if (url != null) {
            return url;
         }
      }

      return null;
   }

   List findEntries1(String path) {
      List urls = new ArrayList();
      Bundle[] arr$ = this.bundleContext.getBundles();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Bundle bundle = arr$[i$];
         URL url = bundle.getEntry(path);
         if (url != null) {
            urls.add(url);
         }
      }

      return urls;
   }
}
