package weblogic.application.compiler;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.xml.stream.XMLStreamException;
import weblogic.application.ApplicationDescriptor;
import weblogic.application.utils.EarUtils;
import weblogic.application.utils.IOUtils;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.EditableDescriptorManager;
import weblogic.descriptor.utils.DescriptorUtils;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.J2EELogger;
import weblogic.logging.Loggable;
import weblogic.utils.BadOptionException;
import weblogic.utils.Debug;
import weblogic.utils.FileUtils;
import weblogic.utils.classloaders.Annotation;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.compiler.ToolFailureException;
import weblogic.utils.jars.JarFileUtils;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public final class AppcUtils {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainerTools");
   public static final String WEBINF_CLASSES;

   private AppcUtils() {
   }

   public static void expandJarFileIntoDirectory(File jf, File dir) throws ToolFailureException {
      VirtualJarFile vjf = null;

      try {
         vjf = VirtualJarFactory.createVirtualJar(jf);
         expandJarFileIntoDirectory(vjf, dir);
      } catch (IOException var7) {
         handleIOException(var7, dir);
      } finally {
         IOUtils.forceClose(vjf);
      }

   }

   public static void expandJarFileIntoDirectory(VirtualJarFile vjf, File dir) throws ToolFailureException {
      try {
         JarFileUtils.extract(vjf, dir);
      } catch (IOException var3) {
         handleIOException(var3, dir);
      }

   }

   private static void handleIOException(IOException ex, File dir) throws ToolFailureException {
      throw new ToolFailureException(J2EELogger.logAppcErrorCopyingFilesLoggable(dir.getAbsolutePath(), ex.toString()).getMessage(), ex);
   }

   public static File makeOutputDir(String dirName, File parent, boolean clean) throws ToolFailureException {
      if (debugLogger.isDebugEnabled()) {
         Debug.assertion(dirName != null);
      }

      File dir = null;
      if (parent != null) {
         dir = new File(parent, dirName);
      } else {
         dir = new File(dirName);
      }

      Loggable l;
      if (!dir.exists()) {
         if (!dir.mkdirs()) {
            l = J2EELogger.logAppcCouldNotCreateDirectoryLoggable(dir.getAbsolutePath());
            throw new ToolFailureException(l.getMessage());
         }
      } else {
         if (!dir.canWrite()) {
            l = J2EELogger.logAppcCanNotWriteToDirectoryLoggable(dir.getAbsolutePath());
            throw new ToolFailureException(l.getMessage());
         }

         if (clean) {
            FileUtils.remove(dir, FileUtils.STAR);
         }
      }

      return dir;
   }

   public static void setDDs(ApplicationDescriptor appDesc, CompilerCtx ctx) throws ToolFailureException {
      try {
         ctx.setApplicationDescriptor(appDesc);
      } catch (IOException var3) {
         EarUtils.handleParsingError(var3, ctx.getSourceName());
      } catch (XMLStreamException var4) {
         EarUtils.handleParsingError(var4, ctx.getSourceName());
      }

   }

   public static void createOutputArchive(String archiveName, File sourceDir) throws ToolFailureException {
      if (!archiveName.endsWith("xml")) {
         File outJar = new File(archiveName);
         File savedJar = null;
         if (outJar.exists()) {
            savedJar = backupJar(outJar);
         }

         try {
            JarFileUtils.createJarFileFromDirectory(archiveName, sourceDir);
            if (savedJar != null) {
               savedJar.delete();
            }

            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Removing working directory: " + sourceDir);
            }

            FileUtils.remove(sourceDir);
         } catch (Exception var6) {
            if (outJar.exists()) {
               outJar.delete();
            }

            Loggable l;
            if (savedJar != null && savedJar.exists()) {
               savedJar.renameTo(outJar);
               l = J2EELogger.logAppcUnableToCreateOutputArchiveRestoreLoggable(outJar.getAbsolutePath(), var6.toString());
               throw new ToolFailureException(l.getMessage(), var6);
            }

            l = J2EELogger.logAppcUnableToCreateOutputArchiveLoggable(outJar.getAbsolutePath(), var6.toString());
            throw new ToolFailureException(l.getMessage(), var6);
         }

         if (debugLogger.isDebugEnabled()) {
            Debug.assertion(!sourceDir.exists());
         }

      }
   }

   private static File backupJar(File jarFile) throws ToolFailureException {
      File saveJar = new File(jarFile + "SAVE");
      Loggable l;
      if (saveJar.exists() && !saveJar.delete()) {
         l = J2EELogger.logAppcUnableToDeleteBackupArchiveLoggable(saveJar.getAbsolutePath());
         throw new ToolFailureException(l.getMessage());
      } else {
         try {
            FileUtils.copyPreservePermissions(jarFile, saveJar);
         } catch (IOException var4) {
            Loggable l = J2EELogger.logAppcUnableToCreateBackupArchiveLoggable(saveJar.getAbsolutePath(), var4.toString());
            throw new ToolFailureException(l.getMessage(), var4);
         }

         if (!jarFile.delete()) {
            l = J2EELogger.logAppcUnableToDeleteArchiveLoggable(jarFile.getAbsolutePath());
            throw new ToolFailureException(l.getMessage());
         } else {
            return saveJar;
         }
      }
   }

   public static GenericClassLoader getClassLoaderForApplication(ClassFinder cf, ToolsContext ctx, String applicationId) {
      GenericClassLoader gcl = getClassLoader(cf, new HashSet(), (CompilerCtx)ctx);
      gcl.setAnnotation(new Annotation(applicationId));
      return gcl;
   }

   public static GenericClassLoader getClassLoaderForModule(ClassFinder cf, ToolsContext ctx, String applicationId, String moduleUri) {
      GenericClassLoader gcl = getClassLoader(cf, new HashSet(), (CompilerCtx)ctx);
      gcl.setAnnotation(new Annotation(applicationId, moduleUri));
      return gcl;
   }

   private static GenericClassLoader getClassLoader(ClassFinder cf, Set exclude, CompilerCtx ctx) {
      String classpathArg = ctx.getClasspathArg();
      GenericClassLoader cl = null;
      if (classpathArg != null) {
         cl = new GenericClassLoader(cf, new GenericClassLoader(new ClasspathClassFinder2(classpathArg, exclude)));
      } else {
         cl = new GenericClassLoader(cf, Thread.currentThread().getContextClassLoader());
      }

      try {
         ctx.getOpts().setOption("classpath", cl.getClassPath());
         return cl;
      } catch (BadOptionException var6) {
         throw new AssertionError(var6);
      }
   }

   public static File getNamedTempDir(String tempDirName, boolean removeExisting) {
      File tmpDir = new File(System.getProperty("java.io.tmpdir"), tempDirName);
      if (removeExisting) {
         FileUtils.remove(tmpDir);
      }

      tmpDir.mkdirs();
      return tmpDir;
   }

   public static void writeDescriptor(File outputDir, String uri, DescriptorBean bean) throws ToolFailureException {
      if (bean != null) {
         EditableDescriptorManager edm = new EditableDescriptorManager();
         File f = new File(outputDir, uri);

         try {
            DescriptorUtils.writeDescriptor(edm, bean, f);
         } catch (IOException var6) {
            throw new ToolFailureException("Unable to write descriptor " + uri + " to " + f.getAbsolutePath(), var6);
         }
      }

   }

   public static VirtualJarFile getVirtualJarFile(File f) throws ToolFailureException {
      VirtualJarFile vjf = null;

      try {
         vjf = VirtualJarFactory.createVirtualJar(f);
         return vjf;
      } catch (IOException var3) {
         throw new ToolFailureException(J2EELogger.logAppcErrorsEncounteredCompilingModuleLoggable(f.getAbsolutePath(), var3.toString()).getMessage(), var3);
      }
   }

   static {
      WEBINF_CLASSES = File.separatorChar + "WEB-INF" + File.separatorChar + "classes";
   }
}
