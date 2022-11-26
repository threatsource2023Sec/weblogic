package weblogic.application.internal.flow;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import weblogic.application.AppClassLoaderManager;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ApplicationFileManager;
import weblogic.application.SplitDirectoryInfo;
import weblogic.application.io.Ear;
import weblogic.application.io.JarCopyFilter;
import weblogic.application.utils.AppFileOverrideUtils;
import weblogic.application.utils.PathUtils;
import weblogic.management.DeploymentException;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.classloaders.GenericClassLoader;

public final class EarClassLoaderFlow extends BaseFlow {
   private final AppClassLoaderManager appClassLoaderManager = (AppClassLoaderManager)GlobalServiceLocator.getServiceLocator().getService(AppClassLoaderManager.class, new Annotation[0]);

   public EarClassLoaderFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void prepare() throws DeploymentException {
      try {
         File extractDir = PathUtils.getTempDirForAppArchive(this.appCtx.getApplicationId());
         Ear ear = this.createEar(extractDir);
         this.appCtx.setEar(ear);
         AppFileOverrideUtils.addFinderIfRequired(this.appCtx.getAppDeploymentMBean(), this.appCtx.getAppClassLoader());
         this.appCtx.getAppClassLoader().addClassFinder(ear.getClassFinder());
         this.appCtx.addAppAnnotationScanningClassPathFirst(ear.getClassFinder().getClassPath());
      } catch (IOException var3) {
         throw new DeploymentException(var3);
      }
   }

   public void unprepare() throws DeploymentException {
      this.appClassLoaderManager.removeApplicationLoader(this.appCtx.getApplicationId());
      GenericClassLoader gcl = this.appCtx.getAppClassLoader();
      gcl.close();
   }

   public void remove() {
      Ear ear = this.appCtx.getEar();
      if (ear != null) {
         ear.remove();
      }

   }

   private Ear createEar(File extractDir) throws IOException {
      File f = new File(this.appCtx.getStagingPath());
      if (!f.exists()) {
         throw new IOException("Could not read application at " + f.getAbsolutePath());
      } else if (!f.isDirectory()) {
         return this.archivedEar(extractDir, f);
      } else {
         File splitDirProps = new File(f, ".beabuild.txt");
         if (splitDirProps.exists()) {
            this.appCtx.setSplitDir();
            return this.splitDirectory(extractDir, f, splitDirProps);
         } else {
            return this.explodedEAR(extractDir, f);
         }
      }
   }

   private Ear archivedEar(File extractDir, File f) throws IOException {
      this.appCtx.setApplicationPaths(new File[]{extractDir});
      this.appCtx.setupApplicationFileManager(extractDir);
      return new Ear(this.appCtx.getApplicationId(), extractDir, f);
   }

   private Ear splitDirectory(File extractDir, File f, File splitDirProps) throws IOException {
      SplitDirectoryInfo info = new SplitDirectoryInfo(f, splitDirProps);
      this.appCtx.setApplicationPaths(info.getRootDirectories());
      this.appCtx.setApplicationFileManager(this.appCtx.hasApplicationArchive() ? ApplicationFileManager.newInstance(this.appCtx.getApplicationArchive()) : ApplicationFileManager.newInstance(info));
      this.appCtx.setSplitDirectoryInfo(info);
      return new Ear(this.appCtx.getApplicationId(), extractDir, info);
   }

   private Ear explodedEAR(File extractDir, File dir) throws IOException {
      this.appCtx.setApplicationPaths(new File[]{dir});
      ApplicationFileManager afm;
      if (!this.appCtx.hasApplicationArchive()) {
         afm = ApplicationFileManager.newInstance(dir);
      } else {
         afm = ApplicationFileManager.newInstance(this.appCtx.getApplicationArchive());
      }

      this.appCtx.setApplicationFileManager(afm);
      return this.appCtx.isInternalApp() ? new Ear(this.appCtx.getApplicationId(), extractDir, new File[]{dir}, JarCopyFilter.NOCOPY_FILTER) : new Ear(this.appCtx.getApplicationId(), extractDir, new File[]{dir});
   }
}
