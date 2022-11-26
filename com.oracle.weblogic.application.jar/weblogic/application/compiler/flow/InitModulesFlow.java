package weblogic.application.compiler.flow;

import java.util.ArrayList;
import weblogic.application.ParentModule;
import weblogic.application.compiler.CompilerCtx;
import weblogic.application.compiler.FactoryException;
import weblogic.application.compiler.ModuleState;
import weblogic.application.compiler.ToolsExtension;
import weblogic.application.compiler.ToolsFactoryManager;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.utils.CustomModuleManager;
import weblogic.application.utils.EarUtils;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.J2EELogger;
import weblogic.j2ee.descriptor.ModuleBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicModuleBean;
import weblogic.utils.compiler.ToolFailureException;

public final class InitModulesFlow extends CompilerFlow {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainerTools");

   public InitModulesFlow(CompilerCtx ctx) {
      super(ctx);
   }

   public void compile() throws ToolFailureException {
      this.ctx.setModules(this.initModules());
      ToolsModule[] var1 = this.ctx.getModules();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         ToolsModule module = var1[var3];
         ModuleState state = this.ctx.createState(module);
         this.ctx.saveState(module, state);
      }

   }

   private ToolsModule[] initModules() throws ToolFailureException {
      this.ctx.setToolsExtensions((ToolsExtension[])ToolsFactoryManager.createToolsExtensions(this.ctx).toArray(new ToolsExtension[0]));
      ModuleList modList = new ModuleList();
      ModuleBean[] modules = this.ctx.getApplicationDD().getModules();
      if (modules != null) {
         for(int i = 0; i < modules.length; ++i) {
            try {
               modList.addModule(ToolsFactoryManager.createModule(modules[i]));
            } catch (FactoryException var8) {
               String message = "Unable to identify module type ";
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug(message + modules[i]);
               }

               J2EELogger.logUnidentifiedModule(EarUtils.getModuleURI(modules[i]));
               throw new ToolFailureException(message, var8);
            }
         }
      }

      WeblogicApplicationBean wlApp = this.ctx.getWLApplicationDD();
      if (wlApp != null) {
         WeblogicModuleBean[] wlModules = wlApp.getModules();
         if (wlModules != null) {
            for(int i = 0; i < wlModules.length; ++i) {
               try {
                  modList.addModule(ToolsFactoryManager.createWLModule(wlModules[i]));
               } catch (FactoryException var7) {
                  J2EELogger.logUnidentifiedWLModule(wlModules[i].getName());
                  throw new ToolFailureException("Unable to identify wl module type " + wlModules[i].getName(), var7);
               }
            }
         }
      }

      if (modList.size() == 0) {
         J2EELogger.logAppcNoModulesFoundLoggable(this.ctx.getSourceName()).log();
         return new ToolsModule[0];
      } else {
         return (ToolsModule[])((ToolsModule[])modList.toArray(new ToolsModule[modList.size()]));
      }
   }

   public void cleanup() {
      ToolsExtension[] var1 = this.ctx.getToolsExtensions();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         ToolsExtension extension = var1[var3];
         extension.cleanup();
      }

   }

   private class ModuleList extends ArrayList {
      private static final long serialVersionUID = 2883469812856650286L;

      private ModuleList() {
      }

      public void addModule(ToolsModule m) {
         if (m != null) {
            if (m instanceof ParentModule) {
               m = new CustomModuleManager((ToolsModule)m);
            }

            super.add(m);
         }
      }

      // $FF: synthetic method
      ModuleList(Object x1) {
         this();
      }
   }
}
