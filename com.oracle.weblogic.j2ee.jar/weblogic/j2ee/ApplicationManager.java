package weblogic.j2ee;

import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.rmi.server.RMIClassLoader;
import java.security.AccessControlException;
import java.security.AccessController;
import java.util.Map;
import java.util.WeakHashMap;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.AppClassLoaderManager;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.core.base.api.ClassLoaderService;
import weblogic.descriptor.DescriptorClassLoader;
import weblogic.kernel.KernelStatus;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.URLClassFinder;

@Service(
   name = "Application"
)
public final class ApplicationManager implements ClassLoaderService {
   private static AppClassLoaderManager APPCLASSLOADER_MANAGER_CACHE = null;
   private static final DebugCategory classFinderDebugging = Debug.getCategory("weblogic.ClassFinder");
   private static Map networkLoaders = new WeakHashMap();
   private static String INSTALL_HELP_MSG = ":  This error could indicate that a component was deployed on a  cluster member but not other members of that cluster. Make sure that any component deployed on a server that is part of a cluster is also deployed on all other members of that cluster";

   private static synchronized AppClassLoaderManager getACLM() {
      if (APPCLASSLOADER_MANAGER_CACHE != null) {
         return APPCLASSLOADER_MANAGER_CACHE;
      } else {
         APPCLASSLOADER_MANAGER_CACHE = (AppClassLoaderManager)GlobalServiceLocator.getServiceLocator().getService(AppClassLoaderManager.class, new Annotation[0]);
         return APPCLASSLOADER_MANAGER_CACHE;
      }
   }

   private static ApplicationContextInternal getApplicationContext(String name) {
      return ApplicationAccess.getApplicationAccess().getApplicationContext(name);
   }

   public static ClassLoader getApplicationClassLoader(weblogic.utils.classloaders.Annotation annotation) {
      ApplicationContextInternal appCtx = getApplicationContext(annotation.getApplicationName());
      return appCtx == null ? null : appCtx.getAppClassLoader();
   }

   public Class loadClass(String className, String annotation) throws ClassNotFoundException {
      return this.loadClass(className, annotation, (String)null);
   }

   public Class loadClass(String className, String annotation, boolean useDependencyClassLoader) throws ClassNotFoundException {
      return this.loadClass(className, annotation, (String)null, Thread.currentThread().getContextClassLoader(), useDependencyClassLoader);
   }

   public Class loadClassWithNoDependencyClassLoader(String className, String annotation) throws ClassNotFoundException {
      ClassLoader ccl = Thread.currentThread().getContextClassLoader();
      return !(ccl instanceof GenericClassLoader) && annotation != null ? getACLM().loadApplicationClass(new weblogic.utils.classloaders.Annotation(annotation), className) : this.loadClass(className, annotation, (String)null, ccl);
   }

   public Class loadClass(String className, String annotationString, String codebase) throws ClassNotFoundException {
      ClassLoader ccl = Thread.currentThread().getContextClassLoader();
      return this.loadClass(className, annotationString, codebase, ccl);
   }

   public Class loadClass(String className, String annotationString, String codebase, ClassLoader ccl) throws ClassNotFoundException {
      return this.loadClass(className, annotationString, codebase, ccl, false);
   }

   public Class loadClass(String className, String annotationString, String codebase, ClassLoader ccl, boolean useDependencyClassLoader) throws ClassNotFoundException {
      String msg = null;
      if (classFinderDebugging.isEnabled()) {
         log("loadClass: [" + className + "] [" + annotationString + "] [" + codebase + "] [" + ccl + "]");
      }

      Class loadedClass;
      try {
         loadedClass = Class.forName(className, true, ccl);
         if (classFinderDebugging.isEnabled()) {
            log("found " + loadedClass + " in current classloader: " + ccl);
         }

         if (loadedClass != null) {
            return loadedClass;
         }
      } catch (ClassNotFoundException var10) {
         msg = var10.getMessage();
      }

      if (classFinderDebugging.isEnabled()) {
         log(className + " not found in current classloader [" + ccl + "], looking elsewhere");
      }

      if (annotationString != null && annotationString.length() > 0) {
         weblogic.utils.classloaders.Annotation annotation = new weblogic.utils.classloaders.Annotation(annotationString);
         Class loadedClass = null;
         loadedClass = loadClassFromApplication(className, annotation, ccl, useDependencyClassLoader);
         if (loadedClass != null) {
            return loadedClass;
         }
      }

      try {
         loadedClass = DescriptorClassLoader.loadClass(className);
         if (loadedClass != null) {
            return loadedClass;
         }
      } catch (ClassNotFoundException var9) {
      }

      try {
         loadedClass = loadFromNetwork(ccl, className, annotationString, codebase);
         if (loadedClass != null) {
            return loadedClass;
         }

         if (annotationString == null) {
            loadedClass = Class.forName(className, true, ApplicationManager.class.getClassLoader());
            if (loadedClass != null) {
               return loadedClass;
            }
         }
      } catch (ClassNotFoundException var11) {
         if (KernelStatus.isServer()) {
            msg = var11.getMessage();
         }
      }

      if (classFinderDebugging.isEnabled()) {
         log("Could not find " + className);
      }

      if (msg != null) {
         throw new ClassNotFoundException(msg + INSTALL_HELP_MSG);
      } else {
         throw new ClassNotFoundException(className + INSTALL_HELP_MSG);
      }
   }

   private static Class loadClassFromApplication(String className, weblogic.utils.classloaders.Annotation annotation, ClassLoader ccl, boolean useDependencyClassLoader) {
      String componentName = annotation.getModuleName();
      String appName = annotation.getApplicationName();
      if (classFinderDebugging.isEnabled()) {
         log("loadClass: Looking for " + className + " in app containers");
      }

      if (KernelStatus.isServer()) {
         try {
            Class loadedClass = null;
            if (classFinderDebugging.isEnabled()) {
               log("Looking in new app list");
            }

            loadedClass = loadClassFromNewStyleApplication(className, annotation, appName, componentName, ccl, useDependencyClassLoader);
            if (loadedClass != null) {
               if (classFinderDebugging.isEnabled()) {
                  log("found " + loadedClass);
               }

               return loadedClass;
            }
         } catch (ClassNotFoundException var7) {
         }
      }

      return null;
   }

   private static Class loadClassFromNewStyleApplication(String className, weblogic.utils.classloaders.Annotation annotation, String appName, String componentName, ClassLoader ccl, boolean useDependencyClassLoader) throws ClassNotFoundException {
      GenericClassLoader gcl;
      if (useDependencyClassLoader) {
         gcl = getACLM().findOrCreateInterAppLoader(annotation, ccl);
      } else {
         gcl = getACLM().findOrCreateIntraAppLoader(annotation, ccl);
      }

      if (gcl != null) {
         return Class.forName(className, true, gcl);
      } else {
         throw new ClassNotFoundException("ClassLoader not found for " + componentName);
      }
   }

   private static boolean isNetworkClassLoadingEnabled() {
      if (!KernelStatus.isServer()) {
         return true;
      } else {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         ServerMBean s = ManagementService.getRuntimeAccess(kernelId).getServer();
         return s == null ? false : s.isNetworkClassLoadingEnabled();
      }
   }

   private static Class loadFromNetwork(ClassLoader ccl, String className, String annotationString, String codebase) throws ClassNotFoundException {
      if (className.indexOf("java.lang.") > -1) {
         return null;
      } else if (!isNetworkClassLoadingEnabled()) {
         return null;
      } else {
         if (classFinderDebugging.isEnabled()) {
            log("loadClass: Going to network with annotation " + annotationString + " and codebase " + codebase + " looking for " + className);
         }

         if (ccl instanceof GenericClassLoader && codebase != null) {
            ClassFinder finder = getNetworkFinder(annotationString, codebase);
            GenericClassLoader gcl = (GenericClassLoader)ccl;
            gcl.addClassFinder(finder);
            return Class.forName(className, true, gcl);
         } else {
            GenericClassLoader gcl = getNetworkLoader(ccl, annotationString);
            if (gcl == null && codebase != null) {
               try {
                  gcl = createNetworkLoader(ccl, annotationString, codebase);
                  return Class.forName(className, true, gcl);
               } catch (AccessControlException var9) {
                  try {
                     if (classFinderDebugging.isEnabled()) {
                        log("loadClass: Going to rmi classloader with codebase " + codebase + " looking for " + className);
                     }

                     return RMIClassLoader.loadClass(codebase, className);
                  } catch (MalformedURLException var7) {
                     var7.printStackTrace();
                  } catch (ClassNotFoundException var8) {
                     var8.printStackTrace();
                  }

                  var9.printStackTrace();
                  return null;
               }
            } else {
               return Class.forName(className, true, gcl);
            }
         }
      }
   }

   private static ClassFinder getNetworkFinder(String annotation, String codebase) {
      String url;
      if (annotation != null && annotation.length() > 0) {
         url = codebase + annotation + "/";
      } else {
         url = codebase;
      }

      return new URLClassFinder(url);
   }

   private static synchronized GenericClassLoader getNetworkLoader(ClassLoader cl, String annotation) {
      Map temp = (Map)networkLoaders.get(cl);
      if (temp == null) {
         networkLoaders.put(cl, new WeakHashMap());
         return null;
      } else {
         return (GenericClassLoader)temp.get(annotation);
      }
   }

   private static synchronized GenericClassLoader createNetworkLoader(ClassLoader cl, String annotation, String codebase) {
      GenericClassLoader gcl = getNetworkLoader(cl, annotation);
      if (gcl == null && codebase != null) {
         ClassFinder finder = getNetworkFinder(annotation, codebase);
         gcl = new GenericClassLoader(finder, cl);
         gcl.setAnnotation(new weblogic.utils.classloaders.Annotation(annotation));
         addNetworkLoader(cl, annotation, gcl);
      }

      return gcl;
   }

   private static void addNetworkLoader(ClassLoader ccl, String annotation, GenericClassLoader gcl) {
      Map temp = (Map)networkLoaders.get(ccl);
      if (temp == null) {
         temp = new WeakHashMap();
         networkLoaders.put(ccl, temp);
      }

      ((Map)temp).put(annotation, gcl);
   }

   private static void log(String msg) {
      Debug.say(msg);
      J2EELogger.logDebug(msg);
   }
}
