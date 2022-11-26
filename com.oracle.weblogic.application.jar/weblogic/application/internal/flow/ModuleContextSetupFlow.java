package weblogic.application.internal.flow;

import java.io.File;
import java.io.IOException;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.internal.DeploymentModuleContext;
import weblogic.application.utils.EarUtils;
import weblogic.management.DeploymentException;
import weblogic.utils.jars.VirtualJarFile;

public class ModuleContextSetupFlow extends BaseFlow {
   public ModuleContextSetupFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void prepare() throws DeploymentException {
      super.prepare();
      this.setupModuleContexts(this.appCtx.getApplicationModules());
   }

   public void start(String[] uris) throws DeploymentException {
      super.start(uris);
      this.setupModuleContexts(this.appCtx.getStartingModules());
   }

   private void setupModuleContexts(Module[] modules) throws DeploymentException {
      Module[] var2 = modules;
      int var3 = modules.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Module module = var2[var4];
         DeploymentModuleContext modCtx = this.appCtx.getModuleAttributes(module.getId()).getModuleContext();
         modCtx.setupWLDirectories();

         try {
            modCtx.setupVirtualJarFile();
         } catch (IOException var19) {
         }

         if (!this.appCtx.isEar()) {
            File altDDFile = EarUtils.getExternalAltDDFile(this.appCtx);
            if (altDDFile != null) {
               modCtx.setAltDDFile(altDDFile);
            }
         } else {
            VirtualJarFile earVjf = null;

            try {
               earVjf = this.appCtx.getApplicationFileManager().getVirtualJarFile();
               modCtx.setAltDDFile(EarUtils.resolveAltDD(this.appCtx.getApplicationDD(), earVjf, modCtx.getURI()));
            } catch (IOException var17) {
               throw new DeploymentException(var17);
            } finally {
               try {
                  if (earVjf != null) {
                     earVjf.close();
                  }
               } catch (IOException var16) {
               }

            }
         }
      }

   }

   public void unprepare() throws DeploymentException {
      super.unprepare();
      this.cleanupModuleContexts(this.appCtx.getApplicationModules());
   }

   public void stop(String[] uris) throws DeploymentException {
      super.stop(uris);
      this.cleanupModuleContexts(this.appCtx.getStoppingModules());
   }

   private void cleanupModuleContexts(Module[] modules) throws DeploymentException {
      Module[] var2 = modules;
      int var3 = modules.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Module module = var2[var4];
         DeploymentModuleContext modCtx = this.appCtx.getModuleAttributes(module.getId()).getModuleContext();
         if (modCtx != null) {
            modCtx.cleanup();
         }
      }

   }
}
