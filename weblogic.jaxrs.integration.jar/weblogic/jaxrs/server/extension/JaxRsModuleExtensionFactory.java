package weblogic.jaxrs.server.extension;

import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.ModuleExtension;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.ModuleExtensionFactory;
import weblogic.descriptor.Descriptor;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.wl.RestWebservicesBean;
import weblogic.servlet.internal.WebAppModule;
import weblogic.servlet.internal.WebBaseModuleExtensionContext;

public class JaxRsModuleExtensionFactory implements ModuleExtensionFactory {
   public ModuleType[] getSupportedModuleTypes() {
      return new ModuleType[]{ModuleType.WAR};
   }

   public final Class[] getSupportedClassLevelAnnotations() {
      return DeployHelper.JAXRS_ANNOTATIONS;
   }

   public ModuleExtension create(ModuleExtensionContext extensionCtx, ApplicationContextInternal appCtx, Module extensibleModule, Descriptor standardDD) throws ModuleException {
      try {
         if (standardDD != null && standardDD.getRootBean() instanceof WebAppBean && extensionCtx instanceof WebBaseModuleExtensionContext && extensibleModule instanceof WebAppModule) {
            WebAppBean webBean = (WebAppBean)standardDD.getRootBean();
            WebBaseModuleExtensionContext context = (WebBaseModuleExtensionContext)extensionCtx;
            WebAppModule webApp = (WebAppModule)extensibleModule;
            if (DeployHelper.isJaxRsApplication(context)) {
               boolean isServletVersion2 = DeployHelper.isServletVersion2x(standardDD);
               JaxRsContainerInitializer.initialize(context, extensionCtx.getTemporaryClassLoader(), webBean, (RestWebservicesBean)null, isServletVersion2);
               if (isServletVersion2) {
                  webApp.forceContainerInitializersLookup();
               }
            }
         }

         return null;
      } catch (Exception var9) {
         throw new ModuleException("Error initializing JAX-RS applications.", var9);
      }
   }
}
