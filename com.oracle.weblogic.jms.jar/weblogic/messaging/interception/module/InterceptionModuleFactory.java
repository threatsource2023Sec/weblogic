package weblogic.messaging.interception.module;

import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.WeblogicModuleFactory;
import weblogic.j2ee.descriptor.wl.WeblogicModuleBean;

public class InterceptionModuleFactory implements WeblogicModuleFactory {
   public Module createModule(WeblogicModuleBean dd) throws ModuleException {
      return "Interception".equals(dd.getType()) ? new InterceptionModule(dd.getPath()) : null;
   }

   public Module createModule(ModuleType type) throws ModuleException {
      return null;
   }
}
