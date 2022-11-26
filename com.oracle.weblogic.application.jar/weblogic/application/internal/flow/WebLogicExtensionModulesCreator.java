package weblogic.application.internal.flow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.application.ApplicationFactoryManager;
import weblogic.application.ExtensionModuleFactory;
import weblogic.application.Module;
import weblogic.application.internal.FlowContext;
import weblogic.management.DeploymentException;

public class WebLogicExtensionModulesCreator implements ModulesCreator {
   private static final ApplicationFactoryManager afm = ApplicationFactoryManager.getApplicationFactoryManager();

   public Module[] create(FlowContext context) throws DeploymentException {
      List modules = new ArrayList();
      this.createWebLogicExtensionModules(context, modules);
      return (Module[])modules.toArray(new Module[modules.size()]);
   }

   private void createWebLogicExtensionModules(FlowContext context, List modules) {
      Iterator iter = afm.getWebLogicExtenstionModuleFactories();

      while(iter.hasNext()) {
         ExtensionModuleFactory factory = (ExtensionModuleFactory)iter.next();
         Module m = factory.createModule(context);
         if (m != null) {
            modules.add(m);
         }
      }

   }
}
