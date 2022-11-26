package weblogic.connector.deploy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.AnnotationProcessingException;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleException;
import weblogic.application.io.ClasspathInfo;
import weblogic.application.io.ExplodedJar;
import weblogic.application.io.ClasspathInfo.ArchiveType;
import weblogic.application.metadatacache.Cache;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.application.utils.PathUtils;
import weblogic.application.utils.annotation.ClassInfoFinder;
import weblogic.application.utils.annotation.ClassInfoFinderFactory;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.common.Debug;
import weblogic.utils.FileUtils;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public class RarArchive {
   private String applicationId;
   private String moduleName;
   private VirtualJarFile originalVj;
   private VirtualJarFile extractedVj;
   private boolean useSysTempDir;
   private File extractToDir;
   private ExplodedJar explodedJarFile = null;
   private String classpath;
   private boolean closed;
   private ClassInfoFinder annotatedClassFinder;
   private ClassFinder classFinder;
   private final ApplicationContextInternal appCtx;
   private final ModuleContext modCtx;
   private static ClasspathInfo RARClasspathInfo = new ClasspathInfo() {
      public String[] getClasspathURIs() {
         return new String[0];
      }

      public String[] getJarURIs() {
         return new String[0];
      }

      public ClasspathInfo.ArchiveType getArchiveType() {
         return ArchiveType.RAR;
      }
   };

   public RarArchive(String applicationId, ApplicationContextInternal appCtx, ModuleContext modCtx, String moduleName, boolean isEmbededInEar, VirtualJarFile vj, boolean useSysTempDir) throws ModuleException, IOException {
      this.applicationId = applicationId;
      this.moduleName = moduleName;
      this.originalVj = vj;
      this.useSysTempDir = useSysTempDir;
      if (Debug.isDeploymentEnabled()) {
         Debug.deployment("RarArchive.<init>: applicationId:" + applicationId + "; moduleName:" + moduleName + "; originalVj:" + this.originalVj);
      }

      this.extractIfNeed();
      this.classFinder = DeployerUtil.getClassFinder(this, isEmbededInEar);
      this.appCtx = appCtx;
      this.modCtx = modCtx;
   }

   public ClassFinder getClassFinder() {
      return this.classFinder;
   }

   public VirtualJarFile getVirtualJarFile() {
      return this.extractedVj;
   }

   public VirtualJarFile getOriginalVirtualJarFile() {
      return this.originalVj;
   }

   public String getOriginalRarFilename() {
      return this.originalVj.getName();
   }

   public void remove() {
      if (this.extractToDir == null) {
         try {
            this.generateTempPath();
         } catch (IOException var2) {
            ConnectorLogger.logCannotExtractRARtoTempDir(this.originalVj.getName(), var2.toString());
         }
      }

      if (this.extractToDir.exists()) {
         File parentDir = this.extractToDir.getParentFile();
         if (this.explodedJarFile != null) {
            this.explodedJarFile.remove();
         }

         if (Debug.isDeploymentEnabled()) {
            Debug.deployment("Remove temporary directory" + this.extractToDir.getAbsolutePath());
         }

         if (parentDir != null && parentDir.list().length == 0) {
            FileUtils.remove(parentDir);
         }
      }

   }

   public synchronized String getClassPath() {
      this.computerClassPath();
      return this.classpath;
   }

   public ClassInfoFinder getAnnotatedClassFinder() {
      if (this.annotatedClassFinder == null) {
         try {
            VirtualJarFile vjf = null;
            File cacheDir = null;
            if (this.modCtx != null) {
               vjf = this.modCtx.getVirtualJarFile();
               cacheDir = this.modCtx.getCacheDir();
            }

            this.annotatedClassFinder = ClassInfoFinderFactory.FACTORY.newInstance(ClassInfoFinderFactory.FACTORY.createParams(this.classFinder).setModuleType(ModuleType.RAR).enableCaching(Cache.AppMetadataCache, vjf, cacheDir));
         } catch (AnnotationProcessingException var3) {
            var3.printStackTrace();
         }
      }

      return this.annotatedClassFinder;
   }

   public synchronized void close() {
      if (!this.closed) {
         this.closed = true;

         try {
            this.originalVj.close();
         } catch (Throwable var4) {
            ConnectorLogger.getExceptionCloseVJarFailed(this.originalVj.getName(), var4.toString());
         }

         if (this.extractedVj != null) {
            try {
               this.extractedVj.close();
            } catch (Throwable var3) {
               ConnectorLogger.getExceptionCloseVJarFailed(this.extractedVj.getName(), var3.toString());
            }
         }

         if (this.classFinder != null) {
            try {
               this.classFinder.close();
            } catch (Throwable var2) {
               ConnectorLogger.getExceptionCloseVJarFailed(this.originalVj.getName() + " classFinder", var2.toString());
            }
         }

      }
   }

   public synchronized boolean isCLosed() {
      return this.closed;
   }

   private void computerClassPath() {
      if (this.classpath == null) {
         String root = this.extractedVj.getName();
         if (!this.extractedVj.isDirectory()) {
            throw new Error("The RAR should always be extracted to a directory but not:" + this.extractedVj.getName());
         }

         ArrayList jars = new ArrayList();
         Iterator iterator = this.extractedVj.entries();

         while(iterator.hasNext()) {
            ZipEntry entry = (ZipEntry)iterator.next();
            if (entry.getName().endsWith(".jar")) {
               jars.add(entry.getName());
            }
         }

         this.classpath = ClassPathUtil.computeClasspath(root, (List)jars);
      }

   }

   protected boolean needExtract() {
      return !this.originalVj.isDirectory();
   }

   private void initializeTempDir() throws ModuleException, IOException {
      this.generateTempPath();
      if (!this.extractToDir.exists() && !this.extractToDir.mkdirs()) {
         String msg = ConnectorLogger.logCannotCreateTempDirDuringExtractionLoggable(this.originalVj.getName(), this.extractToDir.getAbsolutePath()).getMessage();
         throw new ModuleException(msg);
      }
   }

   private void generateTempPath() throws IOException {
      String appStr = ApplicationVersionUtils.replaceDelimiter(this.applicationId, '_');
      String name = ApplicationVersionUtils.replaceDelimiter(this.moduleName, '_');
      String tempPath = PathUtils.generateTempPath((String)null, appStr, name);
      if (this.useSysTempDir) {
         File rootDir = null;
         rootDir = FileUtils.createTempDir("RarArchive_extract_temp");
         rootDir.deleteOnExit();
         this.extractToDir = new File(rootDir.getAbsolutePath(), tempPath);
      } else {
         this.extractToDir = PathUtils.getAppTempDir(tempPath);
      }

   }

   private synchronized void extractIfNeed() throws ModuleException {
      try {
         if (!this.needExtract()) {
            this.extractedVj = this.originalVj;
            if (Debug.isDeploymentEnabled()) {
               Debug.deployment("RarArchive.extractIfNeed: no need to extract:" + this.extractedVj);
            }
         } else {
            this.initializeTempDir();
            this.explodedJarFile = new ExplodedJar(this.applicationId, this.extractToDir, new File(this.originalVj.getName()), RARClasspathInfo);
            this.extractedVj = VirtualJarFactory.createVirtualJar(this.extractToDir);
            Debug.deployment("RarArchive.extractIfNeed: extracted to:" + this.extractedVj + "; from original rar:" + this.originalVj);
         }

      } catch (ModuleException var3) {
         throw var3;
      } catch (Throwable var4) {
         String msg = ConnectorLogger.logCannotExtractRARtoTempDirLoggable(this.originalVj.getName(), var4.toString()).getMessage();
         throw new ModuleException(msg);
      }
   }

   public String toString() {
      return "[Adapter VJar InUse:" + this.getVirtualJarFile().getName() + "; -original VJar:" + this.getOriginalRarFilename() + "; appId:" + this.applicationId + "; moduleName:" + this.moduleName + "]";
   }
}
