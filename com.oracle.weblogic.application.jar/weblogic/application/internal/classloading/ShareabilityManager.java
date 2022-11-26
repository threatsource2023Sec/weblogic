package weblogic.application.internal.classloading;

import java.io.File;
import weblogic.application.ApplicationConstants;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.wl.ShareableBean;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.DirectoryClassFinder;
import weblogic.utils.classloaders.JarClassFinder;
import weblogic.utils.classloaders.MetadataAttachingFinder;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.classloaders.ZipClassFinder;

public class ShareabilityManager {
   private ShareabilityChecker libDirChecker = null;
   private ShareabilityChecker jarChecker = null;
   private ShareabilityChecker internalJarFileChecker = null;
   private ShareabilityChecker classesDirChecker = null;
   private static final DebugLogger debugger = DebugLogger.getDebugLogger("DebugAppContainer");
   private static final boolean disableSharing = Boolean.getBoolean("weblogic.application.classloading.DisableSharing");

   public ShareabilityManager(ShareableBean[] beans) throws ShareabilityException {
      if (!disableSharing) {
         boolean allShareable = false;
         if (beans != null) {
            ShareableBean[] var3 = beans;
            int var4 = beans.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               ShareableBean bean = var3[var5];
               if ("LIB-DIR".equals(bean.getDir())) {
                  this.libDirChecker = new JarShareabilityChecker(bean);
                  if (debugger.isDebugEnabled()) {
                     debugger.debug("Shareability Manager setup: Found LIB-DIR");
                  }
               } else if ("APP-INF-LIB".equals(bean.getDir())) {
                  this.jarChecker = new JarShareabilityChecker(bean, ApplicationConstants.APP_INF_LIB);
                  if (debugger.isDebugEnabled()) {
                     debugger.debug("Shareability Manager setup: Found APP-INF-LIB");
                  }
               } else if (!"WEB-INF-LIB".equals(bean.getDir())) {
                  if ("APP-INF-CLASSES".equals(bean.getDir())) {
                     this.classesDirChecker = new DirShareabilityChecker(ApplicationConstants.APP_INF_CLASSES);
                     if (debugger.isDebugEnabled()) {
                        debugger.debug("Shareability Manager setup: Found APP-INF-CLASSES");
                     }
                  } else if ("WEB-INF-CLASSES".equals(bean.getDir())) {
                     this.internalJarFileChecker = new JarShareabilityChecker(new String[]{"_wl_cls_gen.jar"}, new String[0], ApplicationConstants.WEB_INF_LIB);
                     this.classesDirChecker = new DirShareabilityChecker(ApplicationConstants.WEB_INF_CLASSES);
                     if (debugger.isDebugEnabled()) {
                        debugger.debug("Shareability Manager setup: Found WEB-INF-CLASSES");
                     }
                  } else {
                     allShareable = true;
                     if (debugger.isDebugEnabled()) {
                        debugger.debug("Shareability Manager setup: Found <shareable> without dir attribute, everything is considered shareable");
                     }
                  }
               } else {
                  String[] excludes = bean.getExcludes();
                  if (excludes == null) {
                     excludes = new String[1];
                  } else {
                     String[] newExcludes = new String[excludes.length + 1];

                     for(int i = 0; i < excludes.length; ++i) {
                        newExcludes[i + 1] = excludes[i];
                     }

                     excludes = newExcludes;
                  }

                  excludes[0] = "_wl_cls_gen.jar";
                  this.jarChecker = new JarShareabilityChecker(bean.getIncludes(), excludes, ApplicationConstants.WEB_INF_LIB);
                  if (debugger.isDebugEnabled()) {
                     debugger.debug("Shareability Manager setup: Found WEB-INF-LIB");
                  }
               }
            }

            if (allShareable) {
               if (beans.length != 1) {
                  throw new ShareabilityException("Invalid configuration:When <shareable> is declared without dir attribute, only one such element is allowed");
               }

               this.libDirChecker = this.jarChecker = this.classesDirChecker = new ShareAll();
            }
         }

      }
   }

   public void sortJarClassFinderConstituents(JarClassFinder jarClassFinder, boolean checkDDConfiguration, MultiClassFinder sharedClassFinder, MultiClassFinder instanceClassFinder, boolean append) {
      jarClassFinder.flatten();
      ClassFinder[] finders = jarClassFinder.getClassFinders();

      for(int i = 0; i <= finders.length - 1; ++i) {
         ClassFinder finder = finders[append ? i : finders.length - 1 - i];
         if (finder instanceof ZipClassFinder && this.isJarShareable((ZipClassFinder)finder, checkDDConfiguration)) {
            if (append) {
               sharedClassFinder.addFinder(finder);
            } else {
               sharedClassFinder.addFinderFirst(finder);
            }

            if (debugger.isDebugEnabled()) {
               debugger.debug("Finder found to be shareable at " + ((ZipClassFinder)finder).getZipFile().getName() + ": " + finder);
            }
         } else if (append) {
            instanceClassFinder.addFinder(finder);
         } else {
            instanceClassFinder.addFinderFirst(finder);
         }
      }

   }

   private boolean isJarShareable(ZipClassFinder finder, boolean consultLibDirChecker) {
      if (disableSharing) {
         return false;
      } else if (finder.isShareable()) {
         if (debugger.isDebugEnabled()) {
            debugger.debug("Jar found to have a shareable manifest directive: " + finder.getZipFile().getName());
         }

         return true;
      } else if (this.libDirChecker != null && consultLibDirChecker) {
         boolean result = this.libDirChecker.doShare(new File(finder.getZipFile().getName()));
         if (debugger.isDebugEnabled()) {
            debugger.debug("Shareability check for jar " + finder.getZipFile().getName() + ": " + result);
         }

         return result;
      } else {
         return false;
      }
   }

   public void extractShareableFinders(MultiClassFinder from, MultiClassFinder to) {
      if (!disableSharing) {
         if (debugger.isDebugEnabled()) {
            debugger.debug("Extracting shareable finders from the given list");
         }

         from.flatten();
         ClassFinder[] finders = from.getClassFinders();

         for(int i = finders.length - 1; i >= 0; --i) {
            ClassFinder finder = finders[i];
            if (finder instanceof MetadataAttachingFinder) {
               finder = ((MetadataAttachingFinder)finder).getFinder();
            }

            if (finder instanceof ZipClassFinder) {
               ZipClassFinder zipFinder = (ZipClassFinder)finder;
               if (zipFinder.isShareable()) {
                  to.addFinderFirst(from.remove(i));
                  if (debugger.isDebugEnabled()) {
                     debugger.debug("Jar found to have a shareable manifest directive " + finder);
                  }
               } else {
                  File file = new File(zipFinder.getZipFile().getName());
                  if (this.jarChecker != null && this.jarChecker.doShare(file)) {
                     if (debugger.isDebugEnabled()) {
                        debugger.debug("Jar found to shareable based on configuration " + file);
                     }

                     to.addFinderFirst(from.remove(i));
                  } else if (this.internalJarFileChecker != null && this.internalJarFileChecker.doShare(file)) {
                     if (debugger.isDebugEnabled()) {
                        debugger.debug("Jar found to shareable based on internal jar configuration " + file);
                     }

                     to.addFinderFirst(from.remove(i));
                  }
               }
            } else if (finder instanceof DirectoryClassFinder) {
               DirectoryClassFinder dirFinder = (DirectoryClassFinder)finder;
               String path = dirFinder.getPath();
               if (path.endsWith(".jar")) {
                  if (this.jarChecker != null && this.jarChecker.doShare(new File(path))) {
                     if (debugger.isDebugEnabled()) {
                        debugger.debug("Jar directory found to shareable based on configuration " + path);
                     }

                     to.addFinderFirst(from.remove(i));
                  } else if (this.internalJarFileChecker != null && this.internalJarFileChecker.doShare(new File(path))) {
                     if (debugger.isDebugEnabled()) {
                        debugger.debug("Jar directory found to shareable based on internal jar configuration " + path);
                     }

                     to.addFinderFirst(from.remove(i));
                  }
               } else if (this.classesDirChecker != null && this.classesDirChecker.doShare(new File(path))) {
                  if (debugger.isDebugEnabled()) {
                     debugger.debug("Classes directory found to shareable based on configuration" + path);
                  }

                  to.addFinderFirst(from.remove(i));
               }
            }
         }

      }
   }
}
