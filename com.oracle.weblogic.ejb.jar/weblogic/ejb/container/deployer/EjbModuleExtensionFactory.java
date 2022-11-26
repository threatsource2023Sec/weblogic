package weblogic.ejb.container.deployer;

import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.AnnotationProcessingException;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.ModuleExtension;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.ModuleExtensionFactory;
import weblogic.descriptor.Descriptor;

public class EjbModuleExtensionFactory extends BaseModuleExtensionFactory implements ModuleExtensionFactory {
   public ModuleExtension create(ModuleExtensionContext extCtx, ApplicationContextInternal appCtx, Module module, Descriptor standardDD) throws ModuleException {
      try {
         return !this.hasEJBDescriptor(extCtx) && !this.hasAnnotatedEJBs(extCtx, standardDD, true) ? null : new EjbModuleExtension(extCtx, appCtx, module);
      } catch (IllegalArgumentException var6) {
         throw new ModuleException(var6.getMessage());
      } catch (AnnotationProcessingException var7) {
         throw new ModuleException(var7);
      }
   }

   public ModuleType[] getSupportedModuleTypes() {
      return new ModuleType[]{ModuleType.WAR};
   }
}
