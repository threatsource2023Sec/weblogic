package weblogic.application.internal.flow;

import java.io.File;
import java.io.IOException;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.internal.ClassLoaders;
import weblogic.application.internal.classloading.ShareabilityException;
import weblogic.application.internal.classloading.ShareabilityManager;
import weblogic.application.utils.PersistenceUtils;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.wl.ShareableBean;
import weblogic.management.DeploymentException;
import weblogic.utils.classloaders.JarClassFinder;
import weblogic.utils.classloaders.MultiClassFinder;

public final class LibraryDirectoryFlow extends BaseFlow {
   public LibraryDirectoryFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void prepare() throws DeploymentException {
      ApplicationBean appBean = this.appCtx.getApplicationDD();
      String applib = appBean != null ? appBean.getLibraryDirectory() : null;
      MultiClassFinder findersForSharedApp = new MultiClassFinder();
      MultiClassFinder libDirFindersForInstance = new MultiClassFinder();
      if (this.processAppLib(applib)) {
         try {
            File[] rootFiles = PersistenceUtils.getApplicationRoots(this.appCtx.getAppClassLoader(), this.appCtx.getApplicationId(), false);

            for(int i = 0; i < rootFiles.length; ++i) {
               File libraryDirectory = new File(rootFiles[i], applib);
               if (libraryDirectory.isDirectory()) {
                  File[] files = libraryDirectory.listFiles();
                  if (this.appCtx.checkShareability()) {
                     ShareableBean[] sBeans = null;
                     if (this.appCtx.getWLApplicationDD() != null) {
                        sBeans = this.appCtx.getWLApplicationDD().getClassLoading().getShareables();
                     }

                     ShareabilityManager sManager = new ShareabilityManager(sBeans);
                     if (files != null) {
                        for(int count = files.length - 1; count >= 0; --count) {
                           if (files[count].getName().endsWith(".jar")) {
                              JarClassFinder finder = new JarClassFinder(files[count]);
                              sManager.sortJarClassFinderConstituents(finder, true, findersForSharedApp, libDirFindersForInstance, true);
                           }
                        }
                     }
                  } else if (files != null) {
                     for(int count = files.length - 1; count >= 0; --count) {
                        if (files[count].getName().endsWith(".jar")) {
                           libDirFindersForInstance.addFinder(new JarClassFinder(files[count]));
                        }
                     }
                  }
               }
            }

            if (libDirFindersForInstance.size() > 0) {
               this.appCtx.getAppClassLoader().addClassFinderFirst(libDirFindersForInstance);
            }

            if (this.appCtx.checkShareability() && this.appCtx.wasSharedAppClassLoaderCreated() && findersForSharedApp.size() > 0) {
               if (this.isDebugEnabled()) {
                  this.debug("Number of finders found and added from lib dir for the shared application class loader: " + findersForSharedApp.size());
               }

               ClassLoaders.instance.getSharedAppClassLoader(this.appCtx.getPartialApplicationId(false), (String)null).addClassFinderFirst(findersForSharedApp);
            }

            this.appCtx.addAppAnnotationScanningClassPathFirst(libDirFindersForInstance.getClassPath());
            this.appCtx.addAppAnnotationScanningClassPathFirst(findersForSharedApp.getClassPath());
         } catch (IOException var13) {
            throw new DeploymentException(var13);
         } catch (ShareabilityException var14) {
            throw new DeploymentException(var14);
         }
      }

   }

   private boolean processAppLib(String applib) {
      return applib != null && !applib.trim().isEmpty();
   }
}
