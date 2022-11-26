package weblogic.application.internal.flow;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import weblogic.application.AppClassLoaderManager;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ApplicationFileManager;
import weblogic.application.EditableApplicationArchiveFileManager;
import weblogic.application.SingleModuleFileManager;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.utils.AppFileOverrideUtils;
import weblogic.application.utils.PathUtils;
import weblogic.management.DeploymentException;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.FileUtils;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFile;

public final class SingleModuleClassLoaderFlow extends BaseFlow {
   private final AppClassLoaderManager appClassLoaderManager = (AppClassLoaderManager)GlobalServiceLocator.getServiceLocator().getService(AppClassLoaderManager.class, new Annotation[0]);
   File extractDir = null;

   public SingleModuleClassLoaderFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void prepare() throws DeploymentException {
      GenericClassLoader gcl = this.appCtx.getAppClassLoader();
      AppFileOverrideUtils.addFinderIfRequired(this.appCtx.getAppDeploymentMBean(), gcl);
      gcl.setAnnotation(new weblogic.utils.classloaders.Annotation(this.appCtx.getBasicDeploymentMBean().getName()));
      File f = new File(this.appCtx.getStagingPath());
      this.appCtx.setApplicationPaths(new File[]{f});
      if (f.isDirectory() || PathUtils.getArchiveNameWithoutSuffix(f) != null) {
         this.extractDir = PathUtils.getTempDirForAppArchive(this.appCtx.getApplicationId());
      }

      this.appCtx.setApplicationFileManager((ApplicationFileManager)(this.appCtx.hasApplicationArchive() ? new SingleModuleApplicationArchiveFileManager(this.appCtx.getApplicationArchive()) : new SingleModuleFileManager(f, this.extractDir)));
   }

   public void unprepare() {
      this.appClassLoaderManager.removeApplicationLoader(this.appCtx.getApplicationId());
      GenericClassLoader gcl = this.appCtx.getAppClassLoader();
      gcl.close();
   }

   public void remove() {
      if (this.extractDir != null) {
         if (this.extractDir.exists()) {
            FileUtils.remove(this.extractDir);
         }

         File parent = this.extractDir.getParentFile();
         if (parent.exists()) {
            FileUtils.remove(parent);
         }
      }

   }

   private class SingleModuleApplicationArchiveFileManager extends EditableApplicationArchiveFileManager {
      public SingleModuleApplicationArchiveFileManager(ApplicationArchive application) {
         super(application);
      }

      public VirtualJarFile getVirtualJarFile(String compuri) throws IOException {
         return this.getVirtualJarFile();
      }

      public File getSourcePath(String uri) {
         return this.getSourcePath();
      }
   }
}
