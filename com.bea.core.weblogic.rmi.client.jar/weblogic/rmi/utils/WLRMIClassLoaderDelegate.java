package weblogic.rmi.utils;

import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.rmi.server.RMIClassLoaderSpi;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import weblogic.kernel.KernelStatus;
import weblogic.rmi.internal.RMIEnvironment;
import weblogic.utils.Debug;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.Source;

public class WLRMIClassLoaderDelegate extends RMIClassLoaderSpi {
   private static final Map cache = Collections.synchronizedMap(new WeakHashMap());
   private ClassLoaderEnvironment environmentLoader;

   public static final WLRMIClassLoaderDelegate getInstance() {
      return WLRMIClassLoaderDelegate.SingletonMaker.singleton;
   }

   private WLRMIClassLoaderDelegate() {
      try {
         Class envClass = Class.forName("weblogic.rmi.internal.wls.WLSClassLoaderEnvironment");
         this.environmentLoader = (ClassLoaderEnvironment)envClass.newInstance();
      } catch (Throwable var2) {
         this.environmentLoader = new ClassLoaderEnvironment();
      }

   }

   public ClassLoader getClassLoader(String codebase) throws MalformedURLException {
      throw new AssertionError("Not yet implemented");
   }

   public String getClassAnnotation(Class clazz) {
      ClassLoader loader = clazz.getClassLoader();
      if (loader instanceof GenericClassLoader) {
         GenericClassLoader gcl = (GenericClassLoader)loader;
         return gcl.getAnnotation().getAnnotationString();
      } else {
         return null;
      }
   }

   public Class loadProxyClass(String codebase, String[] interfaces, ClassLoader defaultLoader) throws ClassNotFoundException {
      Debug.assertion(defaultLoader instanceof GenericClassLoader, " defaultLoader should be an instanceof GenericClassLoader");
      GenericClassLoader gcl = (GenericClassLoader)defaultLoader;
      List list = new ArrayList();
      RMIURLClassFinder classFinder = getClassFinder(codebase);

      for(int i = 0; i < interfaces.length; ++i) {
         Class clazz = null;

         try {
            clazz = loadClass(interfaces[i], classFinder, gcl);
            list.add(clazz);
         } catch (ClassNotFoundException var10) {
         }
      }

      if (list.size() == 0) {
         throw new ClassNotFoundException("Couldn't load any of the interfaces " + Arrays.asList(interfaces));
      } else {
         Class[] classes = (Class[])((Class[])list.toArray(new Class[list.size()]));
         return Proxy.getProxyClass(gcl, classes);
      }
   }

   public Class loadClass(String codebase, String name, ClassLoader defaultLoader) throws ClassNotFoundException, MalformedURLException {
      Debug.assertion(defaultLoader instanceof GenericClassLoader, "defaultLoader should be instanceof GenericClassLoader");
      GenericClassLoader gcl = (GenericClassLoader)defaultLoader;
      RMIURLClassFinder classFinder = getClassFinder(codebase);
      return loadClass(name, classFinder, gcl);
   }

   public static Class loadClass(String codebase, String annotation, String name, GenericClassLoader gcl) throws ClassNotFoundException {
      try {
         return gcl.loadClass(name);
      } catch (ClassNotFoundException var5) {
         if (KernelStatus.isServer()) {
            return loadClassOnServer(codebase, annotation, name, gcl);
         } else if (codebase == null) {
            throw new ClassNotFoundException("Failed to load class " + name);
         } else {
            RMIURLClassFinder finder = getClassFinder(codebase + annotation + "/");
            return loadClass(name, finder, gcl);
         }
      }
   }

   private static Class loadClassOnServer(String codebase, String annotation, String name, GenericClassLoader gcl) throws ClassNotFoundException {
      if (annotation != null) {
         ClassLoader loader = getInstance().findClassLoader(annotation);
         if (loader == null) {
            throw new ClassNotFoundException("Failed to load class " + name);
         } else if (loader instanceof GenericClassLoader) {
            GenericClassLoader appClassLoader = (GenericClassLoader)loader;
            Source source = appClassLoader.getClassFinder().getClassSource(name);
            if (source == null && RMIEnvironment.getEnvironment().isNetworkClassLoadingEnabled()) {
               RMIURLClassFinder finder = getClassFinder(codebase + annotation + "/");
               return loadClass(name, finder, gcl);
            } else {
               return (new GenericClassLoader(appClassLoader.getClassFinder(), gcl)).loadClass(name);
            }
         } else {
            return loader.loadClass(name);
         }
      } else {
         throw new ClassNotFoundException("Failed to load class " + name);
      }
   }

   private static Class loadClass(String name, RMIURLClassFinder finder, GenericClassLoader gcl) throws ClassNotFoundException {
      try {
         return Class.forName(name, false, gcl);
      } catch (ClassNotFoundException var8) {
         if (finder != null && finder.getClassSource(name) != null) {
            synchronized(gcl) {
               Class var10000;
               try {
                  var10000 = Class.forName(name, false, gcl);
               } catch (ClassNotFoundException var6) {
                  gcl.addClassFinder(finder);
                  return gcl.defineClass(name, finder.getClassSource(name));
               }

               return var10000;
            }
         } else {
            throw new ClassNotFoundException("Failed to load class " + name);
         }
      }
   }

   private static RMIURLClassFinder getClassFinder(String codebase) {
      if (codebase == null) {
         return null;
      } else if (!RMIEnvironment.getEnvironment().isNetworkClassLoadingEnabled()) {
         return null;
      } else {
         RMIURLClassFinder classFinder = (RMIURLClassFinder)cache.get(codebase);
         if (classFinder == null) {
            classFinder = new RMIURLClassFinder(codebase);
            cache.put(codebase, classFinder);
         }

         return classFinder;
      }
   }

   public ClassLoader findClassLoader(String appName) {
      return this.environmentLoader.findLoader(appName);
   }

   public ClassLoader findInterAppClassLoader(String appName, ClassLoader parent) {
      return this.environmentLoader.findInterAppLoader(appName, parent);
   }

   // $FF: synthetic method
   WLRMIClassLoaderDelegate(Object x0) {
      this();
   }

   static class SingletonMaker {
      private static final WLRMIClassLoaderDelegate singleton = new WLRMIClassLoaderDelegate();
   }
}
