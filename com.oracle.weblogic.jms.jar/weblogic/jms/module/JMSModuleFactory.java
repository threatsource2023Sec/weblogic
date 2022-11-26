package weblogic.jms.module;

import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.WeblogicModuleFactory;
import weblogic.j2ee.descriptor.wl.WeblogicModuleBean;
import weblogic.jms.common.JMSDebug;

public class JMSModuleFactory implements WeblogicModuleFactory {
   public Module createModule(WeblogicModuleBean dd) throws ModuleException {
      if ("JMS".equals(dd.getType())) {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("JMSModuleFactory:createModule(WebLogicModuleBean),moduleURI=" + dd.getPath());
         }

         return new JMSModule(dd.getPath(), dd.getName());
      } else {
         return null;
      }
   }

   Module createModule(String path, String moduleName) throws ModuleException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModuleFactory:createModule(String, String), moduleURI = " + path);
      }

      return new JMSModule(path, moduleName);
   }

   public Module createModule(ModuleType type) throws ModuleException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModuleFactory:createModule(ModuleType)");
      }

      return null;
   }
}
