package weblogic.application.internal.flow;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.compiler.utils.ContextUtils;
import weblogic.management.DeploymentException;

public class ModuleContextNameUpdateFlow extends BaseFlow {
   public ModuleContextNameUpdateFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void prepare() throws DeploymentException {
      super.prepare();
      ContextUtils.updateModuleContexts(this.getModuleContextDescriptorBeanMap(this.appCtx.getApplicationModules()), new HashSet());
   }

   private Map getModuleContextDescriptorBeanMap(Module[] modules) {
      Map allDescriptors = new HashMap();
      Module[] var3 = modules;
      int var4 = modules.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Module module = var3[var5];
         if (module != null) {
            allDescriptors.put(this.appCtx.getModuleAttributes(module.getId()).getModuleContext(), module.getDescriptors());
         }
      }

      return allDescriptors;
   }

   public void start(String[] uris) throws DeploymentException {
      super.start(uris);
      Module[] startingModules = this.appCtx.getStartingModules();
      if (startingModules.length > 0) {
         Set assignedNames = new HashSet();
         Module[] var4 = this.appCtx.getApplicationModules();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Module module = var4[var6];
            assignedNames.add(this.appCtx.getModuleAttributes(module.getId()).getModuleContext().getName());
         }

         ContextUtils.updateModuleContexts(this.getModuleContextDescriptorBeanMap(this.appCtx.getStartingModules()), assignedNames);
      }

   }
}
