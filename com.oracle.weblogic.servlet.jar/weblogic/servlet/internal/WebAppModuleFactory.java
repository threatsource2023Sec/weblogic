package weblogic.servlet.internal;

import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.ModuleFactory;
import weblogic.j2ee.descriptor.ModuleBean;

public class WebAppModuleFactory implements ModuleFactory {
   public Module createModule(ModuleBean mBean) throws ModuleException {
      if (mBean.getWeb() != null) {
         String contextRoot = mBean.getWeb().getContextRoot();
         if ("".equals(contextRoot)) {
            contextRoot = mBean.getWeb().getWebUri();
         }

         return new WebAppModule(mBean.getWeb().getWebUri(), contextRoot);
      } else {
         return null;
      }
   }

   public ModuleType[] getSupportedModuleTypes() {
      return new ModuleType[]{ModuleType.WAR};
   }

   public Class[] getSupportedClassLevelAnnotations() {
      return AnnotationProcessingManager.getSupportedClassLevelAnnotations();
   }
}
