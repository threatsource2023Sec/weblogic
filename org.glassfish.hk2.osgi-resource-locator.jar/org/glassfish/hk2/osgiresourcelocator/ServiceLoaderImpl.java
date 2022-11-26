package org.glassfish.hk2.osgiresourcelocator;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.BundleReference;

public final class ServiceLoaderImpl extends ServiceLoader {
   private ReadWriteLock rwLock = new ReentrantReadWriteLock();
   private BundleListener bundleTracker;
   private BundleContext bundleContext;
   private ProvidersList providersList = new ProvidersList();

   public ServiceLoaderImpl() {
      ClassLoader cl = this.getClass().getClassLoader();
      if (cl instanceof BundleReference) {
         this.bundleContext = this.getBundleContextSecured(((BundleReference)BundleReference.class.cast(cl)).getBundle());
      }

      if (this.bundleContext == null) {
         throw new RuntimeException("There is no bundle context available yet. Instatiate this class in STARTING or ACTIVE state only");
      }
   }

   private BundleContext getBundleContextSecured(final Bundle bundle) {
      return System.getSecurityManager() != null ? (BundleContext)AccessController.doPrivileged(new PrivilegedAction() {
         public BundleContext run() {
            return bundle.getBundleContext();
         }
      }) : bundle.getBundleContext();
   }

   public void trackBundles() {
      assert this.bundleTracker == null;

      this.bundleTracker = new BundleTracker();
      this.bundleContext.addBundleListener(this.bundleTracker);
      Bundle[] arr$ = this.bundleContext.getBundles();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Bundle bundle = arr$[i$];
         this.addProviders(bundle);
      }

   }

   Iterable lookupProviderInstances1(Class serviceClass, ServiceLoader.ProviderFactory factory) {
      if (factory == null) {
         factory = new DefaultFactory();
      }

      List providers = new ArrayList();
      Iterator i$ = this.lookupProviderClasses1(serviceClass).iterator();

      while(i$.hasNext()) {
         Class c = (Class)i$.next();

         try {
            Object providerInstance = ((ServiceLoader.ProviderFactory)factory).make(c, serviceClass);
            if (providerInstance != null) {
               providers.add(providerInstance);
            } else {
               this.debug(factory + " returned null provider instance!!!");
            }
         } catch (Exception var7) {
            var7.printStackTrace();
         }
      }

      return providers;
   }

   Iterable lookupProviderClasses1(Class serviceClass) {
      List providerClasses = new ArrayList();
      this.rwLock.readLock().lock();

      try {
         String serviceName = serviceClass.getName();
         Iterator i$ = this.providersList.getAllProviders().iterator();

         while(true) {
            Bundle bundle;
            List providerNames;
            do {
               ProvidersPerBundle providersPerBundle;
               do {
                  if (!i$.hasNext()) {
                     ArrayList var16 = providerClasses;
                     return var16;
                  }

                  providersPerBundle = (ProvidersPerBundle)i$.next();
                  bundle = this.bundleContext.getBundle(providersPerBundle.getBundleId());
               } while(bundle == null);

               providerNames = (List)providersPerBundle.getServiceToProvidersMap().get(serviceName);
            } while(providerNames == null);

            Iterator i$ = providerNames.iterator();

            while(i$.hasNext()) {
               String providerName = (String)i$.next();

               try {
                  Class providerClass = this.loadClassSecured(bundle, providerName);
                  if (this.isCompatible(providerClass, serviceClass)) {
                     providerClasses.add(providerClass);
                  }
               } catch (ClassNotFoundException var14) {
                  var14.printStackTrace();
               }
            }
         }
      } finally {
         this.rwLock.readLock().unlock();
      }
   }

   private Class loadClassSecured(final Bundle bundle, final String name) throws ClassNotFoundException {
      if (System.getSecurityManager() != null) {
         try {
            return (Class)AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Class run() throws ClassNotFoundException {
                  return bundle.loadClass(name);
               }
            });
         } catch (PrivilegedActionException var4) {
            throw (ClassNotFoundException)ClassNotFoundException.class.cast(var4.getException());
         }
      } else {
         return bundle.loadClass(name);
      }
   }

   private boolean isCompatible(Class providerClass, Class serviceClass) {
      try {
         Class serviceClassSeenByProviderClass = Class.forName(serviceClass.getName(), false, providerClass.getClassLoader());
         boolean isCompatible = serviceClassSeenByProviderClass == serviceClass;
         if (!isCompatible) {
            this.debug(providerClass + " loaded by " + providerClass.getClassLoader() + " sees " + serviceClass + " from " + serviceClassSeenByProviderClass.getClassLoader() + ", where as caller uses " + serviceClass + " loaded by " + serviceClass.getClassLoader());
         }

         return isCompatible;
      } catch (ClassNotFoundException var5) {
         this.debug("Unable to reach " + serviceClass + " from " + providerClass + ", which is loaded by " + providerClass.getClassLoader(), var5);
         return true;
      }
   }

   private List load(InputStream is) throws IOException {
      List providerNames = new ArrayList();

      try {
         Scanner scanner = new Scanner(is);
         String commentPattern = "#";

         while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!line.startsWith("#")) {
               StringTokenizer st = new StringTokenizer(line);
               if (st.hasMoreTokens()) {
                  providerNames.add(st.nextToken());
               }
            }
         }
      } finally {
         is.close();
      }

      return providerNames;
   }

   private void addProviders(Bundle bundle) {
      this.rwLock.writeLock().lock();

      try {
         String SERVICE_LOCATION = "META-INF/services";
         if (bundle.getEntry("META-INF/services") != null) {
            Enumeration entries = bundle.getEntryPaths("META-INF/services");
            if (entries == null) {
               return;
            }

            ProvidersPerBundle providers = new ProvidersPerBundle(bundle.getBundleId());

            while(entries.hasMoreElements()) {
               String entry = (String)entries.nextElement();
               String serviceName = entry.substring("META-INF/services".length() + 1);
               URL url = bundle.getEntry(entry);

               try {
                  InputStream is = url.openStream();
                  List providerNames = this.load(is);
                  this.debug("Bundle = " + bundle + ", serviceName = " + serviceName + ", providerNames = " + providerNames);
                  providers.put(serviceName, providerNames);
               } catch (IOException var13) {
               }
            }

            this.providersList.addProviders(providers);
            return;
         }
      } finally {
         this.rwLock.writeLock().unlock();
      }

   }

   private synchronized void removeProviders(Bundle bundle) {
      this.rwLock.writeLock().lock();

      try {
         this.providersList.removeProviders(bundle.getBundleId());
      } finally {
         this.rwLock.writeLock().unlock();
      }

   }

   private void debug(String s) {
      if (Boolean.valueOf(this.bundleContext.getProperty("org.glassfish.hk2.osgiresourcelocator.debug"))) {
         System.out.println("org.glassfish.hk2.osgiresourcelocator:DEBUG: " + s);
      }

   }

   private void debug(String s, Throwable t) {
      if (Boolean.valueOf(this.bundleContext.getProperty("org.glassfish.hk2.osgiresourcelocator.debug"))) {
         System.out.println("org.glassfish.hk2.osgiresourcelocator:DEBUG: " + s);
         t.printStackTrace(System.out);
      }

   }

   private static class DefaultFactory implements ServiceLoader.ProviderFactory {
      private DefaultFactory() {
      }

      public Object make(Class providerClass, Class serviceClass) throws Exception {
         return serviceClass.isAssignableFrom(providerClass) ? providerClass.newInstance() : null;
      }

      // $FF: synthetic method
      DefaultFactory(Object x0) {
         this();
      }
   }

   private static class ProvidersList {
      private List allProviders;

      private ProvidersList() {
         this.allProviders = new LinkedList();
      }

      void addProviders(ProvidersPerBundle providers) {
         long bundleId = providers.getBundleId();
         int idx = 0;
         Iterator iterator = this.getAllProviders().iterator();

         ProvidersPerBundle providersPerBundle;
         do {
            if (!iterator.hasNext()) {
               this.getAllProviders().add(providers);
               return;
            }

            providersPerBundle = (ProvidersPerBundle)iterator.next();
         } while(providersPerBundle.getBundleId() <= bundleId);

         this.getAllProviders().add(idx, providers);
      }

      void removeProviders(long bundleId) {
         Iterator iterator = this.getAllProviders().iterator();

         ProvidersPerBundle providersPerBundle;
         do {
            if (!iterator.hasNext()) {
               return;
            }

            providersPerBundle = (ProvidersPerBundle)iterator.next();
         } while(providersPerBundle.getBundleId() != bundleId);

         iterator.remove();
      }

      public List getAllProviders() {
         return this.allProviders;
      }

      // $FF: synthetic method
      ProvidersList(Object x0) {
         this();
      }
   }

   private static class ProvidersPerBundle {
      private long bundleId;
      Map serviceToProvidersMap;

      private ProvidersPerBundle(long bundleId) {
         this.serviceToProvidersMap = new HashMap();
         this.bundleId = bundleId;
      }

      public long getBundleId() {
         return this.bundleId;
      }

      public void put(String serviceName, List providerNames) {
         this.serviceToProvidersMap.put(serviceName, providerNames);
      }

      public Map getServiceToProvidersMap() {
         return this.serviceToProvidersMap;
      }

      // $FF: synthetic method
      ProvidersPerBundle(long x0, Object x1) {
         this(x0);
      }
   }

   private class BundleTracker implements BundleListener {
      private BundleTracker() {
      }

      public void bundleChanged(BundleEvent event) {
         Bundle bundle = event.getBundle();
         switch (event.getType()) {
            case 1:
               ServiceLoaderImpl.this.addProviders(bundle);
               break;
            case 8:
               ServiceLoaderImpl.this.removeProviders(bundle);
               ServiceLoaderImpl.this.addProviders(bundle);
               break;
            case 16:
               ServiceLoaderImpl.this.removeProviders(bundle);
         }

      }

      // $FF: synthetic method
      BundleTracker(Object x1) {
         this();
      }
   }
}
