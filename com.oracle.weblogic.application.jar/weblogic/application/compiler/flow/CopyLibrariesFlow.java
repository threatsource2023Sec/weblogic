package weblogic.application.compiler.flow;

import java.io.File;
import java.io.IOException;
import weblogic.application.ApplicationConstants;
import weblogic.application.compiler.CompilerCtx;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.utils.IOUtils;
import weblogic.j2ee.J2EELogger;
import weblogic.utils.FileUtils;
import weblogic.utils.StringUtils;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.compiler.ToolFailureException;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public final class CopyLibrariesFlow extends CompilerFlow {
   public CopyLibrariesFlow(CompilerCtx ctx) {
      super(ctx);
   }

   public void compile() throws ToolFailureException {
      this.processLibraries(this.ctx.getModules());
      this.copyAdditionalResources();
   }

   public void cleanup() {
   }

   private void processLibraries(ToolsModule[] modules) throws ToolFailureException {
      for(int i = 0; i < modules.length; ++i) {
         if (this.ctx.getModuleState(modules[i]).isLibrary()) {
            this.copyLibrary(modules[i]);
         }
      }

   }

   private void copyLibrary(ToolsModule libraryModule) throws ToolFailureException {
      File outputDir = new File(this.ctx.getOutputDir(), libraryModule.getURI());
      File[] roots = this.getRoots(libraryModule);

      for(int i = 0; i < roots.length; ++i) {
         try {
            if (!roots[i].equals(outputDir)) {
               FileUtils.copyPreservePermissions(roots[i], outputDir);
            }
         } catch (IOException var6) {
            throw new ToolFailureException("Failed to copy library " + roots[i], var6);
         }

         IOUtils.forceClose(this.ctx.getModuleState(libraryModule).getVirtualJarFile());

         try {
            if (this.ctx.getModuleState(libraryModule).getVirtualJarFile() != null) {
               this.ctx.getModuleState(libraryModule).setVirtualJarFile(VirtualJarFactory.createVirtualJar(outputDir));
            }

            this.ctx.getModuleState(libraryModule).setOutputDir(outputDir);
         } catch (IOException var7) {
            throw new ToolFailureException(J2EELogger.logAppcErrorAccessingFileLoggable(outputDir.getAbsolutePath(), var7.toString()).getMessage(), var7);
         }
      }

   }

   private File[] getRoots(ToolsModule libraryModule) {
      VirtualJarFile vjf = this.ctx.getModuleState(libraryModule).getVirtualJarFile();
      return vjf == null ? new File[]{new File(this.ctx.getModuleState(libraryModule).getOutputFileName())} : vjf.getRootFiles();
   }

   private void copyAdditionalResources() throws ToolFailureException {
      ClassFinder cf = this.ctx.getAppClassLoader().getClassFinder();
      String cp = cf.getClassPath();
      File fileDestDir = new File(this.ctx.getOutputDir(), ApplicationConstants.APP_INF_LIB);
      File dirDestDir = new File(this.ctx.getOutputDir(), ApplicationConstants.APP_INF_CLASSES);
      fileDestDir.mkdirs();
      String[] paths = StringUtils.splitCompletely(cp, File.pathSeparator);

      for(int i = 0; i < paths.length; ++i) {
         File f = new File(paths[i]);
         if (f.exists()) {
            try {
               if (f.isFile()) {
                  FileUtils.copyNoOverwritePreservePermissions(f, fileDestDir);
               } else if (!dirDestDir.getCanonicalPath().startsWith(f.getCanonicalPath())) {
                  FileUtils.copyNoOverwritePreservePermissions(f, dirDestDir);
               }
            } catch (IOException var9) {
               throw new ToolFailureException("Failed to copy " + f, var9);
            }
         }
      }

   }
}
