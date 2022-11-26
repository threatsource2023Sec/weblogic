package weblogic.osgi.internal;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ApplicationFileManager;
import weblogic.application.Module;
import weblogic.application.ModuleContext;
import weblogic.application.utils.BaseAppDeploymentExtension;
import weblogic.application.utils.TargetUtils;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.OsgiFrameworkReferenceBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.management.DeploymentException;
import weblogic.management.UndeploymentException;
import weblogic.osgi.OSGiBundle;
import weblogic.osgi.OSGiException;
import weblogic.osgi.OSGiServer;
import weblogic.osgi.OSGiServerManager;
import weblogic.osgi.OSGiServerManagerFactory;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFile;

public class OSGiAppDeploymentExtension extends BaseAppDeploymentExtension {
   private final OSGiServerManager manager = OSGiServerManagerFactory.getInstance().getOSGiServerManager();
   private OSGiServer framework;
   private final List associatedBundles = new LinkedList();

   private void startAll() throws OSGiException {
      Iterator var1 = this.associatedBundles.iterator();

      while(var1.hasNext()) {
         OSGiBundle bundle = (OSGiBundle)var1.next();

         try {
            bundle.start();
         } catch (OSGiException var4) {
            if (Logger.isDebugEnabled()) {
               Logger.getLogger().debug("Failed to start bundle " + bundle, var4);
            }
         }
      }

      this.framework.startAllBundles();
   }

   private void deployZipBundlesIntoFramework(OSGiServer framework, VirtualJarFile root, String osgiLib) throws Throwable {
      Iterator zes = root.getEntries("WEB-INF/" + osgiLib + "/");
      if (zes != null) {
         int rootTokens = 4;
         int startLevel = 1;
         boolean initRootTokens = false;

         while(true) {
            ZipEntry entry;
            do {
               String entryName;
               do {
                  if (!zes.hasNext()) {
                     return;
                  }

                  entry = (ZipEntry)zes.next();
                  entryName = entry.getName();
               } while(!entryName.endsWith(".jar"));

               StringTokenizer st = new StringTokenizer(entryName, "/");
               StringTokenizer bundledir = new StringTokenizer(osgiLib, "/");
               int cnt = bundledir.countTokens();
               if (!initRootTokens) {
                  initRootTokens = true;
                  rootTokens = st.countTokens();
               }

               if (st.countTokens() != rootTokens) {
                  break;
               }

               for(int i = 0; i <= cnt; ++i) {
                  st.nextToken();
               }

               String numberedDirectory = st.nextToken();
               if (numberedDirectory.endsWith(".jar")) {
                  break;
               }

               startLevel = Utilities.toNaturalNumber(numberedDirectory);
            } while(startLevel == -1);

            InputStream is = root.getInputStream(entry);
            OSGiBundle bundle = framework.installBundle(is, startLevel);
            this.associatedBundles.add(bundle);
            startLevel = 1;
         }
      }
   }

   private void deployBundlesIntoFramework(ApplicationContextInternal appCtx, OSGiAmalgamated osgiInfo) throws Throwable {
      StringBuilder frameworkName = new StringBuilder();
      if (appCtx.getPartitionName().equals("DOMAIN")) {
         frameworkName = new StringBuilder(osgiInfo.getFrameworkName());
      } else {
         frameworkName.append(osgiInfo.getFrameworkName() + "$" + appCtx.getPartitionName());
      }

      this.framework = this.manager.getOSGiServer(frameworkName.toString());
      if (this.framework == null) {
         throw new DeploymentException("Could not find OSGi framework with name " + frameworkName);
      } else {
         List deployments = osgiInfo.getDeploymentInfo();
         Iterator var5 = deployments.iterator();

         while(var5.hasNext()) {
            SpecificDeploymentInfo deployment = (SpecificDeploymentInfo)var5.next();
            if (deployment.getRootOfApplication() != null) {
               Utilities.deployFileBundlesIntoFramework(this.framework, this.associatedBundles, deployment.getRootOfApplication(), deployment.getOsgiLibName());
            } else {
               this.deployZipBundlesIntoFramework(this.framework, deployment.getEmbeddedApplication(), deployment.getOsgiLibName());
            }
         }

         try {
            this.startAll();
         } catch (OSGiException var7) {
            Utilities.uninstallAll(this.associatedBundles);
            throw var7;
         }
      }
   }

   private void attachToApplicationBundleClassloader(ApplicationContextInternal appCtx, String bsn, String versionRange) throws Throwable {
      ClassFinder osgiFinder = this.framework.getBundleClassFinder(bsn, versionRange, Utilities.getAppCtx(appCtx));
      if (osgiFinder == null) {
         throw new OSGiException("Could not find bundle with Bundle Symbolic Name of:" + bsn);
      } else {
         GenericClassLoader gcl = appCtx.getAppClassLoader();
         gcl.addClassFinder(osgiFinder);
         if (!appCtx.isEar()) {
            Module[] var6 = appCtx.getApplicationModules();
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               Module module = var6[var8];
               ModuleContext mc = appCtx.getModuleContext(module.getId());
               if (mc != null) {
                  GenericClassLoader moduleLoader = mc.getClassLoader();
                  if (moduleLoader != null && !moduleLoader.equals(gcl)) {
                     moduleLoader.addClassFinder(osgiFinder);
                  }
               }
            }
         }

      }
   }

   private static OsgiFrameworkReferenceBean getOsgiInfo(DescriptorBean[] beans) {
      if (beans == null) {
         return null;
      } else {
         DescriptorBean[] var1 = beans;
         int var2 = beans.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            DescriptorBean bean = var1[var3];
            if (bean != null && bean instanceof WeblogicWebAppBean) {
               return ((WeblogicWebAppBean)bean).getOsgiFrameworkReference();
            }
         }

         return null;
      }
   }

   private static OSGiAmalgamated gatherWork(ApplicationContextInternal appCtx) throws OSGiException {
      OSGiAmalgamated retVal = new OSGiAmalgamated();
      ApplicationFileManager afm = appCtx.getApplicationFileManager();
      if (appCtx.isEar()) {
         WeblogicApplicationBean wab = appCtx.getWLApplicationDD();
         if (wab != null) {
            OsgiFrameworkReferenceBean ofb = wab.getOsgiFrameworkReference();
            if (ofb != null) {
               retVal.addDeploymentInfo(ofb, afm.getOutputPath(), (VirtualJarFile)null);
            }
         }
      }

      Module[] var10 = appCtx.getApplicationModules();
      int var11 = var10.length;

      for(int var5 = 0; var5 < var11; ++var5) {
         Module module = var10[var5];
         OsgiFrameworkReferenceBean ofb = getOsgiInfo(module.getDescriptors());
         if (ofb != null) {
            ModuleContext mc = appCtx.getModuleContext(module.getId());
            VirtualJarFile vjf = mc.getVirtualJarFile();
            retVal.addDeploymentInfo(ofb, (File)null, vjf);
         }
      }

      return retVal;
   }

   public void prepare(ApplicationContextInternal appCtx) throws DeploymentException {
      OSGiAmalgamated oa;
      try {
         oa = gatherWork(appCtx);
      } catch (OSGiException var7) {
         throw new DeploymentException(var7);
      }

      if (oa.hasWork()) {
         if (isLocal(appCtx)) {
            try {
               this.deployBundlesIntoFramework(appCtx, oa);
               if (oa.getBsnOfAttachedBundle() != null) {
                  try {
                     this.attachToApplicationBundleClassloader(appCtx, oa.getBsnOfAttachedBundle(), oa.getVersionRangeOfAttachedBundle());
                  } catch (Throwable var6) {
                     try {
                        this.unprepare(appCtx);
                     } catch (Throwable var5) {
                     }

                     throw var6;
                  }
               }

            } catch (Throwable var8) {
               if (Logger.isDebugEnabled()) {
                  Logger.getLogger().debug(var8.getMessage(), var8);
               }

               if (var8 instanceof DeploymentException) {
                  throw (DeploymentException)var8;
               } else {
                  throw new DeploymentException(var8);
               }
            }
         }
      }
   }

   public void unprepare(ApplicationContextInternal appCtx) {
      if (this.framework != null) {
         Iterator var2 = this.associatedBundles.iterator();

         while(var2.hasNext()) {
            OSGiBundle bundle = (OSGiBundle)var2.next();
            bundle.stop();
         }

         try {
            this.unInstallAppBundles();
            this.framework.refreshAllBundles();
            this.associatedBundles.clear();
         } catch (OSGiException var4) {
            if (Logger.isDebugEnabled()) {
               Logger.getLogger().debug("Failed to refresh bundles " + var4);
            }
         } catch (UndeploymentException var5) {
            var5.printStackTrace();
         }

      }
   }

   private void unInstallAppBundles() throws UndeploymentException {
      try {
         Utilities.uninstallAll(this.associatedBundles);
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public String toString() {
      return "OSGiAppDeploymentExtension(" + System.identityHashCode(this) + ")";
   }

   static boolean isLocal(ApplicationContextInternal appCtx) {
      if (appCtx.getBasicDeploymentMBean() != null) {
         return TargetUtils.isDeployedLocally(appCtx.getBasicDeploymentMBean().getTargets());
      } else {
         return appCtx.getSystemResourceMBean() != null ? TargetUtils.isDeployedLocally(appCtx.getSystemResourceMBean().getTargets()) : false;
      }
   }
}
