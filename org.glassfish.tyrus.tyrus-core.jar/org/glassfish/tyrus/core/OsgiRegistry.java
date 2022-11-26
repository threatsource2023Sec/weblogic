package org.glassfish.tyrus.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleReference;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.SynchronousBundleListener;

public final class OsgiRegistry implements SynchronousBundleListener {
   private static final String CoreBundleSymbolicNAME = "org.glassfish.jersey.core.jersey-common";
   private static final Logger LOGGER = Logger.getLogger(OsgiRegistry.class.getName());
   private final BundleContext bundleContext;
   private final Map factories = new HashMap();
   private final ReadWriteLock lock = new ReentrantReadWriteLock();
   private static OsgiRegistry instance;
   private Map classToBundleMapping = new HashMap();

   public static synchronized OsgiRegistry getInstance() {
      if (instance == null) {
         ClassLoader classLoader = ReflectionHelper.class.getClassLoader();
         if (classLoader instanceof BundleReference) {
            BundleContext context = FrameworkUtil.getBundle(OsgiRegistry.class).getBundleContext();
            if (context != null) {
               instance = new OsgiRegistry(context);
            }
         }
      }

      return instance;
   }

   public void bundleChanged(BundleEvent event) {
      if (event.getType() == 32) {
         this.register(event.getBundle());
      } else if (event.getType() == 64 || event.getType() == 16) {
         Bundle unregisteredBundle = event.getBundle();
         this.lock.writeLock().lock();

         try {
            this.factories.remove(unregisteredBundle.getBundleId());
            if (unregisteredBundle.getSymbolicName().equals("org.glassfish.jersey.core.jersey-common")) {
               this.bundleContext.removeBundleListener(this);
               this.factories.clear();
            }
         } finally {
            this.lock.writeLock().unlock();
         }
      }

   }

   public Enumeration getPackageResources(String packagePath, ClassLoader classLoader) {
      List result = new LinkedList();
      this.classToBundleMapping.clear();
      Bundle[] var4 = this.bundleContext.getBundles();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Bundle bundle = var4[var6];
         String[] var8 = new String[]{packagePath, "WEB-INF/classes/" + packagePath};
         int var9 = var8.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            String bundlePackagePath = var8[var10];
            Enumeration enumeration = bundle.findEntries(bundlePackagePath, "*", false);
            if (enumeration != null) {
               while(enumeration.hasMoreElements()) {
                  URL url = (URL)enumeration.nextElement();
                  String path = url.getPath();
                  String className = (packagePath + path.substring(path.lastIndexOf(47))).replace('/', '.').replace(".class", "");
                  this.classToBundleMapping.put(className, bundle);
                  result.add(url);
               }
            }
         }

         Enumeration jars = bundle.findEntries("/", "*.jar", true);
         if (jars != null) {
            while(jars.hasMoreElements()) {
               JarInputStream jarInputStream = null;

               try {
                  InputStream inputStream = classLoader.getResourceAsStream(((URL)jars.nextElement()).getPath());
                  jarInputStream = new JarInputStream(inputStream);

                  JarEntry jarEntry;
                  while((jarEntry = jarInputStream.getNextJarEntry()) != null) {
                     String jarEntryName = jarEntry.getName();
                     if (jarEntryName.endsWith(".class") && jarEntryName.contains(packagePath)) {
                        this.classToBundleMapping.put(jarEntryName.replace(".class", "").replace('/', '.'), bundle);
                        result.add(bundle.getResource(jarEntryName));
                     }
                  }
               } catch (Exception var24) {
               } finally {
                  if (jarInputStream != null) {
                     try {
                        jarInputStream.close();
                     } catch (IOException var23) {
                     }
                  }

               }
            }
         }
      }

      return Collections.enumeration(result);
   }

   public Class classForNameWithException(String className) throws ClassNotFoundException {
      Bundle bundle = (Bundle)this.classToBundleMapping.get(className);
      if (bundle == null) {
         throw new ClassNotFoundException(className);
      } else {
         return bundle.loadClass(className);
      }
   }

   public ResourceBundle getResourceBundle(String bundleName) {
      int lastDotIndex = bundleName.lastIndexOf(46);
      String path = bundleName.substring(0, lastDotIndex).replace('.', '/');
      String propertiesName = bundleName.substring(lastDotIndex + 1, bundleName.length()) + ".properties";
      Bundle[] var5 = this.bundleContext.getBundles();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Bundle bundle = var5[var7];
         Enumeration entries = bundle.findEntries(path, propertiesName, false);
         if (entries != null && entries.hasMoreElements()) {
            URL entryUrl = (URL)entries.nextElement();

            try {
               return new PropertyResourceBundle(entryUrl.openStream());
            } catch (IOException var12) {
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.fine("Exception caught when tried to load resource bundle in OSGi");
               }

               return null;
            }
         }
      }

      return null;
   }

   private OsgiRegistry(BundleContext bundleContext) {
      this.bundleContext = bundleContext;
   }

   public void hookUp() {
      this.setOSGiServiceFinderIteratorProvider();
      this.bundleContext.addBundleListener(this);
      this.registerExistingBundles();
   }

   private void registerExistingBundles() {
      Bundle[] var1 = this.bundleContext.getBundles();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Bundle bundle = var1[var3];
         if (bundle.getState() == 4 || bundle.getState() == 8 || bundle.getState() == 32 || bundle.getState() == 16) {
            this.register(bundle);
         }
      }

   }

   private void setOSGiServiceFinderIteratorProvider() {
      ServiceFinder.setIteratorProvider(new OsgiServiceFinder());
   }

   private void register(Bundle bundle) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "checking bundle {0}", bundle.getBundleId());
      }

      this.lock.writeLock().lock();

      Object map;
      try {
         map = (Map)this.factories.get(bundle.getBundleId());
         if (map == null) {
            map = new ConcurrentHashMap();
            this.factories.put(bundle.getBundleId(), map);
         }
      } finally {
         this.lock.writeLock().unlock();
      }

      Enumeration e = bundle.findEntries("META-INF/services/", "*", false);
      if (e != null) {
         while(e.hasMoreElements()) {
            URL u = (URL)e.nextElement();
            String url = u.toString();
            if (!url.endsWith("/")) {
               String factoryId = url.substring(url.lastIndexOf("/") + 1);

               try {
                  ((Map)map).put(factoryId, new BundleSpiProvidersLoader(factoryId, u.toURI(), bundle));
               } catch (URISyntaxException var11) {
               }
            }
         }
      }

   }

   private List locateAllProviders(String serviceName) {
      this.lock.readLock().lock();

      LinkedList var11;
      try {
         List result = new LinkedList();
         Iterator var3 = this.factories.values().iterator();

         while(var3.hasNext()) {
            Map value = (Map)var3.next();
            if (value.containsKey(serviceName)) {
               try {
                  result.addAll((Collection)((Callable)value.get(serviceName)).call());
               } catch (Exception var9) {
               }
            }
         }

         var11 = result;
      } finally {
         this.lock.readLock().unlock();
      }

      return var11;
   }

   private static class BundleSpiProvidersLoader implements Callable {
      private final String spi;
      private final URI spiRegistryUri;
      private final Bundle bundle;

      BundleSpiProvidersLoader(String spi, URI spiRegistryUri, Bundle bundle) {
         this.spi = spi;
         this.spiRegistryUri = spiRegistryUri;
         this.bundle = bundle;
      }

      public List call() throws Exception {
         try {
            if (OsgiRegistry.LOGGER.isLoggable(Level.FINEST)) {
               OsgiRegistry.LOGGER.log(Level.FINEST, "Loading providers for SPI: {0}", this.spi);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(this.spiRegistryUri.toURL().openStream(), "UTF-8"));
            List providerClasses = new ArrayList();

            String providerClassName;
            while((providerClassName = br.readLine()) != null) {
               if (providerClassName.trim().length() != 0) {
                  if (OsgiRegistry.LOGGER.isLoggable(Level.FINEST)) {
                     OsgiRegistry.LOGGER.log(Level.FINEST, "SPI provider: {0}", providerClassName);
                  }

                  providerClasses.add(this.bundle.loadClass(providerClassName));
               }
            }

            br.close();
            return providerClasses;
         } catch (Exception var4) {
            OsgiRegistry.LOGGER.log(Level.WARNING, LocalizationMessages.EXCEPTION_CAUGHT_WHILE_LOADING_SPI_PROVIDERS(), var4);
            throw var4;
         } catch (Error var5) {
            OsgiRegistry.LOGGER.log(Level.WARNING, LocalizationMessages.ERROR_CAUGHT_WHILE_LOADING_SPI_PROVIDERS(), var5);
            throw var5;
         }
      }

      public String toString() {
         return this.spiRegistryUri.toString();
      }

      public int hashCode() {
         return this.spiRegistryUri.hashCode();
      }

      public boolean equals(Object obj) {
         return obj instanceof BundleSpiProvidersLoader ? this.spiRegistryUri.equals(((BundleSpiProvidersLoader)obj).spiRegistryUri) : false;
      }
   }

   private final class OsgiServiceFinder extends ServiceFinder.ServiceIteratorProvider {
      final ServiceFinder.ServiceIteratorProvider defaultIterator;

      private OsgiServiceFinder() {
         this.defaultIterator = new ServiceFinder.DefaultServiceIteratorProvider();
      }

      public Iterator createIterator(final Class serviceClass, final String serviceName, ClassLoader loader, boolean ignoreOnClassNotFound) {
         final List providerClasses = OsgiRegistry.this.locateAllProviders(serviceName);
         return !providerClasses.isEmpty() ? new Iterator() {
            Iterator it = providerClasses.iterator();

            public boolean hasNext() {
               return this.it.hasNext();
            }

            public Object next() {
               Class nextClass = (Class)this.it.next();

               try {
                  return nextClass.newInstance();
               } catch (Exception var4) {
                  ServiceConfigurationError sce = new ServiceConfigurationError(serviceName + ": " + LocalizationMessages.PROVIDER_COULD_NOT_BE_CREATED(nextClass.getName(), serviceClass, var4.getLocalizedMessage()));
                  sce.initCause(var4);
                  throw sce;
               }
            }

            public void remove() {
               throw new UnsupportedOperationException();
            }
         } : this.defaultIterator.createIterator(serviceClass, serviceName, loader, ignoreOnClassNotFound);
      }

      public Iterator createClassIterator(Class service, String serviceName, ClassLoader loader, boolean ignoreOnClassNotFound) {
         final List providerClasses = OsgiRegistry.this.locateAllProviders(serviceName);
         return !providerClasses.isEmpty() ? new Iterator() {
            Iterator it = providerClasses.iterator();

            public boolean hasNext() {
               return this.it.hasNext();
            }

            public Class next() {
               return (Class)this.it.next();
            }

            public void remove() {
               throw new UnsupportedOperationException();
            }
         } : this.defaultIterator.createClassIterator(service, serviceName, loader, ignoreOnClassNotFound);
      }

      // $FF: synthetic method
      OsgiServiceFinder(Object x1) {
         this();
      }
   }
}
