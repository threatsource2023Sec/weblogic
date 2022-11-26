package com.oracle.injection.integration;

import com.oracle.injection.InjectionException;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleException;
import weblogic.application.ModuleExtension;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.ModuleExtensionFactory;
import weblogic.application.Type;
import weblogic.application.library.LibraryConstants;
import weblogic.descriptor.Descriptor;

class CDIModuleExtensionFactory implements ModuleExtensionFactory {
   public ModuleExtension create(ModuleExtensionContext extensionCtx, ApplicationContextInternal appCtx, Module extensibleModule, Descriptor standardDD) throws ModuleException {
      if (appCtx.getCdiPolicy().equals("Disabled")) {
         return null;
      } else {
         try {
            ModuleContext modCtx = appCtx.getModuleContext(extensibleModule.getId());
            return CDIUtils.isModuleCdiEnabled(modCtx, extensionCtx, appCtx) ? new CDIModuleExtension(extensionCtx, appCtx, extensibleModule, new DefaultInjectionContainerFactory()) : null;
         } catch (InjectionException var6) {
            throw new ModuleException(var6);
         }
      }
   }

   public ModuleType[] getSupportedModuleTypes() {
      return new ModuleType[]{ModuleType.EJB, ModuleType.WAR, ModuleType.RAR};
   }

   public Class[] getSupportedClassLevelAnnotations() {
      Class[] annotations = new Class[CDIUtils.cdiEnablingAnnotationClasses.size()];
      CDIUtils.cdiEnablingAnnotationClasses.toArray(annotations);
      return annotations;
   }

   public Type[] getAutoRefLibraryTypes() {
      return new Type[0];
   }

   public LibraryConstants.AutoReferrer getAutoReferrerType() {
      return null;
   }
}
