package weblogic.servlet.internal;

import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.ModuleExtension;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.ModuleExtensionFactory;
import weblogic.descriptor.Descriptor;

public class WebAppInternalModuleExtensionFactory implements ModuleExtensionFactory {
   public ModuleExtension create(ModuleExtensionContext extensionCtx, ApplicationContextInternal appCtx, Module module, Descriptor standardDD) throws ModuleException {
      return module instanceof WebAppModule ? new WebAppInternalModuleExtension(extensionCtx, appCtx, module) : null;
   }

   public ModuleType[] getSupportedModuleTypes() {
      return new ModuleType[]{ModuleType.WAR};
   }

   public Class[] getSupportedClassLevelAnnotations() {
      return null;
   }
}
