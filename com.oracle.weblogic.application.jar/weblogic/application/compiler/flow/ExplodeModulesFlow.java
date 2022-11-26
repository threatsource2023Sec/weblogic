package weblogic.application.compiler.flow;

import java.io.File;
import java.io.IOException;
import weblogic.application.compiler.AppcUtils;
import weblogic.application.compiler.CompilerCtx;
import weblogic.application.compiler.ModuleState;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.metadatacache.ClassInfoFinderMetadataEntry;
import weblogic.j2ee.J2EELogger;
import weblogic.utils.compiler.ToolFailureException;
import weblogic.utils.jars.JarFileUtils;
import weblogic.utils.jars.VirtualJarFile;

public final class ExplodeModulesFlow extends CompilerFlow {
   private final boolean explodeLibraries;

   public ExplodeModulesFlow(CompilerCtx ctx) {
      this(ctx, false);
   }

   public ExplodeModulesFlow(CompilerCtx ctx, boolean explodeLibraries) {
      super(ctx);
      this.explodeLibraries = explodeLibraries;
   }

   public void compile() throws ToolFailureException {
      this.maybeExplodeModules(this.ctx.getModules());
   }

   public void cleanup() throws ToolFailureException {
      ToolsModule[] modules = this.ctx.getModules();
      if (!this.ctx.getOpts().hasOption("nopackage")) {
         this.packageModules(modules);
      }

   }

   private void packageModules(ToolsModule[] modules) throws ToolFailureException {
      for(int i = 0; i < modules.length; ++i) {
         if (this.ctx.getModuleState(modules[i]).needsPackaging()) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Creating output archive: " + this.ctx.getModuleState(modules[i]).getOutputFileName());
            }

            ModuleState moduleState = this.ctx.getModuleState(modules[i]);
            AppcUtils.createOutputArchive(moduleState.getOutputFileName(), moduleState.getOutputDir());
            File outputFile = new File(moduleState.getOutputFileName());
            ClassInfoFinderMetadataEntry metadataEntry = new ClassInfoFinderMetadataEntry(outputFile, moduleState.getCacheDir(), outputFile.getAbsolutePath());
            metadataEntry.updateTimestamp(new File(metadataEntry.getCacheDir(), ".cache.ser"));
         }
      }

   }

   private void maybeExplodeModules(ToolsModule[] modules) throws ToolFailureException {
      for(int i = 0; i < modules.length; ++i) {
         if (this.ctx.getModuleState(modules[i]).isArchive() && (!this.ctx.getModuleState(modules[i]).isLibrary() || this.explodeLibraries)) {
            this.explodeModule(modules[i]);
         }
      }

   }

   private void explodeModule(ToolsModule module) throws ToolFailureException {
      String dirName = this.ctx.getTempDir().getName() + "_" + module.getURI().replace(File.separatorChar, '_');
      File moduleDir = AppcUtils.makeOutputDir(dirName, this.ctx.getOutputDir(), true);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Expanding " + this.ctx.getModuleState(module).getVirtualJarFile().getName() + " into " + moduleDir);
      }

      this.expandJarFileIntoDirectory(this.ctx.getModuleState(module).getVirtualJarFile(), moduleDir);
      this.ctx.getModuleState(module).setOutputDir(moduleDir);
      this.ctx.getModuleState(module).setNeedsPackaging(true);
   }

   private void expandJarFileIntoDirectory(VirtualJarFile vjf, File dir) throws ToolFailureException {
      try {
         JarFileUtils.extract(vjf, dir);
      } catch (IOException var4) {
         throw new ToolFailureException(J2EELogger.logAppcErrorCopyingFilesLoggable(dir.getAbsolutePath(), var4.toString()).getMessage(), var4);
      }
   }
}
