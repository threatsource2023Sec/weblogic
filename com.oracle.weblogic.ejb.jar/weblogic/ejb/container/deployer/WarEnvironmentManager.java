package weblogic.ejb.container.deployer;

import java.util.Collection;
import java.util.Collections;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.naming.Environment;

public class WarEnvironmentManager implements EnvironmentManager {
   private final String moduleId;
   private final ModuleExtensionContext extCtx;

   WarEnvironmentManager(String moduleId, ModuleExtensionContext extCtx) {
      this.moduleId = moduleId;
      this.extCtx = extCtx;
   }

   public Environment createEnvironmentFor(String name) {
      return this.extCtx.getEnvironment(this.moduleId);
   }

   public Collection getEnvironments() {
      return Collections.singleton(this.extCtx.getEnvironment(this.moduleId));
   }

   public Environment getEnvironmentFor(String name) {
      return this.extCtx.getEnvironment(this.moduleId);
   }

   public void cleanup() {
   }
}
