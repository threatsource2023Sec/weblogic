package weblogic.application.compiler.flow;

import java.io.File;
import java.io.IOException;
import weblogic.application.compiler.CompilerCtx;
import weblogic.application.compiler.LibraryModule;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.compiler.utils.ContextUtils;
import weblogic.application.utils.EarUtils;
import weblogic.j2ee.J2EELogger;
import weblogic.utils.compiler.ToolFailureException;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public final class PrepareModulesFlow extends CompilerFlow {
   public PrepareModulesFlow(CompilerCtx ctx) {
      super(ctx);
   }

   public void compile() throws ToolFailureException {
      this.prepareModules(this.ctx.getModules());
   }

   private void prepareModules(ToolsModule[] mods) throws ToolFailureException {
      for(int i = 0; i < mods.length; ++i) {
         if (this.ctx.isLibraryURI(mods[i].getURI())) {
            this.ctx.getModuleState(mods[i]).markAsLibrary();
            mods[i] = new LibraryModule(mods[i]);
         }

         this.setAltDDFile(mods[i]);
         this.prepareModule(mods[i]);
      }

   }

   private File getAltDDFile(String altDD, VirtualJarFile vjf) {
      return EarUtils.resolveAltDD(vjf, altDD);
   }

   private void setAltDDFile(ToolsModule module) throws ToolFailureException {
      File altDD = null;
      String altDDURI = module.getAltDD();
      if (altDDURI != null) {
         altDD = this.getAltDDFile(altDDURI, this.ctx.getVSource());
         if (altDD == null) {
            throw new ToolFailureException(J2EELogger.logAppcMissingModuleAltDDFileLoggable(module.getAltDD(), module.getURI()).getMessage());
         }
      }

      this.ctx.getModuleState(module).setAltDDFile(altDD);
   }

   private void prepareModule(ToolsModule eModule) throws ToolFailureException {
      String uri = eModule.getURI();
      File module = new File(this.ctx.getOutputDir(), uri);
      File[] moduleRoots;
      if (!module.exists()) {
         if (ContextUtils.isSplitDir(this.ctx)) {
            moduleRoots = this.ctx.getEar().getModuleRoots(uri);
            if (moduleRoots.length > 0) {
               module = this.ctx.getEar().getModuleRoots(uri)[0];
            }
         }

         if (!module.exists() && this.ctx.isLibraryURI(uri)) {
            module = this.ctx.getURILink(uri);
         }
      }

      if (!module.exists()) {
         throw new ToolFailureException(J2EELogger.logAppcCantFindDeclaredModuleLoggable(uri).getMessage());
      } else {
         moduleRoots = null;
         if (!module.getPath().endsWith(".xml")) {
            VirtualJarFile vModule;
            try {
               File[] moduleRoots = this.ctx.getEar().getModuleRoots(uri);
               if (moduleRoots.length > 1 && !moduleRoots[0].isDirectory()) {
                  vModule = VirtualJarFactory.createVirtualJar(moduleRoots[0]);
               } else {
                  vModule = VirtualJarFactory.createVirtualJar(moduleRoots);
               }

               if (moduleRoots.length == 1) {
                  this.ctx.getModuleState(eModule).setArchive(moduleRoots[0].isFile());
               } else {
                  this.ctx.getModuleState(eModule).setArchive(false);
               }
            } catch (IOException var6) {
               throw new ToolFailureException(J2EELogger.logAppcErrorAccessingFileLoggable(uri, var6.toString()).getMessage(), var6);
            }

            this.ctx.getModuleState(eModule).setVirtualJarFile(vModule);
         } else {
            this.ctx.getModuleState(eModule).setArchive(false);
         }

         this.ctx.getModuleState(eModule).setOutputDir(module);
         this.ctx.getModuleState(eModule).setOutputFileName(module.getPath());
      }
   }

   public void cleanup() {
   }
}
