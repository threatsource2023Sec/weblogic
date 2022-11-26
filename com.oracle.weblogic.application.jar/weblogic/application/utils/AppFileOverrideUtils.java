package weblogic.application.utils;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import weblogic.application.ModuleException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.utils.classloaders.AbstractClassFinder;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.IndexedDirectoryClassFinder;
import weblogic.utils.classloaders.NullClassFinder;
import weblogic.utils.classloaders.Source;
import weblogic.utils.enumerations.EmptyEnumerator;

public final class AppFileOverrideUtils {
   private static final String OVERRIDE_SUBDIRECTORY = "AppFileOverrides";
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugAppContainer");

   private AppFileOverrideUtils() {
   }

   public static void addFinderIfRequired(AppDeploymentMBean duMBean, GenericClassLoader gcl) throws ModuleException {
      if (ManagementUtils.isRuntimeAccessAvailable()) {
         if (duMBean != null && gcl != null && duMBean.getLocalPlanDir() != null) {
            File theDirFile = new File(duMBean.getLocalPlanDir(), "AppFileOverrides");
            if (!theDirFile.isDirectory()) {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("Application File Override is not enabled for " + duMBean.getApplicationName());
               }

            } else {
               try {
                  String theDir = null;
                  if (DEBUG.isDebugEnabled()) {
                     theDir = duMBean.getLocalPlanDir() + File.separator + "AppFileOverrides";
                     DEBUG.debug("Application File Overrides enabled for " + duMBean.getApplicationName() + ", overrides located at: " + theDir);
                  }

                  gcl.addClassFinderFirst(new AppFileOverrideFinder(new IndexedDirectoryClassFinder(theDirFile), theDir));
               } catch (IOException var4) {
                  throw new ModuleException(var4);
               }
            }
         }
      }
   }

   public static ClassFinder getFinderIfRequired(AppDeploymentMBean duMBean, String moduleURI) throws ModuleException {
      if (duMBean != null && duMBean.getLocalPlanDir() != null) {
         File theDirFile;
         if (moduleURI == null) {
            theDirFile = new File(duMBean.getLocalPlanDir(), "AppFileOverrides");
         } else {
            theDirFile = new File(duMBean.getLocalPlanDir() + File.separator + "AppFileOverrides", moduleURI);
         }

         if (!theDirFile.isDirectory()) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Application File Override is not enabled for " + duMBean.getApplicationName() + (moduleURI == null ? "" : ", moduleURI: " + moduleURI));
            }

            return null;
         } else {
            try {
               String theDir = null;
               if (DEBUG.isDebugEnabled()) {
                  theDir = duMBean.getLocalPlanDir() + File.separator + "AppFileOverrides" + (moduleURI == null ? "" : File.separator + moduleURI);
                  DEBUG.debug("Application File Override finder returned for " + duMBean.getApplicationName() + ", overrides located at: " + theDir);
               }

               return new AppFileOverrideFinder(new IndexedDirectoryClassFinder(theDirFile), theDir);
            } catch (IOException var4) {
               throw new ModuleException(var4);
            }
         }
      } else {
         return null;
      }
   }

   private static final class AppFileOverrideFinder extends AbstractClassFinder {
      private final ClassFinder delegate;
      private final String debugDir;

      public AppFileOverrideFinder(ClassFinder theClassFinder, String debugDir) {
         this.delegate = theClassFinder;
         this.debugDir = debugDir;
      }

      public Source getSource(String name) {
         if (name.endsWith(".class")) {
            return null;
         } else {
            Source result = this.delegate.getSource(name);
            if (AppFileOverrideUtils.DEBUG.isDebugEnabled()) {
               AppFileOverrideUtils.DEBUG.debug("getSource(" + name + ") found " + result);
               if (this.debugDir != null) {
                  File test = new File(this.debugDir + File.separator + name);
                  AppFileOverrideUtils.DEBUG.debug(" resource file: " + this.debugDir + File.separator + name + ", exists = " + test.exists());
               }
            }

            return result;
         }
      }

      public Enumeration getSources(String name) {
         return name.endsWith(".class") ? EmptyEnumerator.EMPTY : this.delegate.getSources(name);
      }

      public Source getClassSource(String name) {
         return null;
      }

      public String getClassPath() {
         return "";
      }

      public ClassFinder getManifestFinder() {
         return NullClassFinder.NULL_FINDER;
      }

      public Enumeration entries() {
         return EmptyEnumerator.EMPTY;
      }

      public void close() {
         this.delegate.close();
      }
   }
}
