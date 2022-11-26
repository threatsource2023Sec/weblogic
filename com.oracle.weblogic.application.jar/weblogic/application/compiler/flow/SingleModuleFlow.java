package weblogic.application.compiler.flow;

import java.io.File;
import java.io.IOException;
import weblogic.application.ParentModule;
import weblogic.application.SingleModuleFileManager;
import weblogic.application.compiler.CompilerCtx;
import weblogic.application.compiler.FactoryException;
import weblogic.application.compiler.ModuleState;
import weblogic.application.compiler.ToolsExtension;
import weblogic.application.compiler.ToolsFactoryManager;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.utils.CustomModuleManager;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.j2ee.J2EELogger;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.compiler.ToolFailureException;
import weblogic.utils.jars.JarFileUtils;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public abstract class SingleModuleFlow extends CompilerFlow {
   public SingleModuleFlow(CompilerCtx ctx) {
      super(ctx);
   }

   public void compile() throws ToolFailureException {
      File source = this.ctx.getSourceFile();
      if (this.ctx.getApplicationFileManager() == null) {
         this.ctx.setApplicationFileManager(new SingleModuleFileManager(source));
      }

      Object module;
      ModuleState state;
      try {
         module = ToolsFactoryManager.createStandaloneModule(source, this.ctx.getApplicationArchive());
         state = this.ctx.createState((ToolsModule)module);
         state.setAltDDFile(this.getAltDDFileFromOptions());
         if (module instanceof ParentModule) {
            module = new CustomModuleManager((ToolsModule)module);
         }

         this.ctx.saveState((ToolsModule)module, state);
      } catch (FactoryException var9) {
         J2EELogger.logUnidentifiedApplication(source.getName());
         throw new ToolFailureException("Unable to identify application type for " + source, var9);
      }

      this.ctx.setToolsExtensions((ToolsExtension[])ToolsFactoryManager.createToolsExtensions(this.ctx).toArray(new ToolsExtension[0]));
      this.prepareModule((ToolsModule)module);
      if (source != null) {
         this.ctx.setModules(new ToolsModule[]{(ToolsModule)module});
         ClassFinder finder = ((ToolsModule)module).init(state, this.ctx, this.ctx.getAppClassLoader());
         this.ctx.getModuleState((ToolsModule)module).init(this.ctx.getAppClassLoader(), finder);
         ToolsExtension[] var5 = this.ctx.getToolsExtensions();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            ToolsExtension extension = var5[var7];
            extension.init(this.ctx, this.ctx.getAppClassLoader());
         }

         this.proecessModule((ToolsModule)module);
      }

   }

   public File getAltDDFileFromOptions() {
      if (this.ctx != null && this.ctx.getEar() == null) {
         String altDDFileName = this.ctx.getOpts().getOption("altappdd");
         if (altDDFileName != null && altDDFileName.length() > 0) {
            return new File(altDDFileName);
         }
      }

      return null;
   }

   private void prepareModule(ToolsModule standaloneModule) throws ToolFailureException {
      try {
         if (standaloneModule != null && (standaloneModule.getModuleType().equals(WebLogicModuleType.JMS) || standaloneModule.getModuleType().equals(WebLogicModuleType.JDBC))) {
            this.ctx.getModuleState(standaloneModule).setArchive(false);
            this.ctx.getModuleState(standaloneModule).setOutputDir(this.ctx.getOutputDir());
            this.ctx.getModuleState(standaloneModule).setOutputFileName(this.ctx.getSourceFile().getPath());
         } else {
            VirtualJarFile vModule = VirtualJarFactory.createVirtualJar(this.ctx.getSourceFile());
            this.ctx.getModuleState(standaloneModule).setVirtualJarFile(vModule);
            this.ctx.getModuleState(standaloneModule).setArchive(this.ctx.getSourceFile().isFile());
            this.ctx.getModuleState(standaloneModule).setOutputDir(this.ctx.getOutputDir());
            this.ctx.getModuleState(standaloneModule).setOutputFileName(this.ctx.getOutputDir().getPath());
            if (!this.ctx.getSourceFile().equals(this.ctx.getOutputDir())) {
               JarFileUtils.extract(vModule, this.ctx.getOutputDir());
            }

         }
      } catch (IOException var3) {
         throw new ToolFailureException(var3.getMessage(), var3);
      }
   }

   public void cleanup() {
      ToolsModule[] modules = this.ctx.getModules();
      if (modules.length != 1) {
         throw new AssertionError("SingleModuleMergerFlow can be invoked for standalone modules only");
      } else {
         modules[0].cleanup();
         this.ctx.getModuleState(modules[0]).cleanup();
         ToolsExtension[] var2 = this.ctx.getToolsExtensions();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            ToolsExtension extension = var2[var4];
            extension.cleanup();
         }

      }
   }

   protected abstract void proecessModule(ToolsModule var1) throws ToolFailureException;
}
