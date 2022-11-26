package weblogic.application.internal.flow;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.Module;
import weblogic.application.internal.FlowContext;
import weblogic.j2ee.descriptor.wl.CustomModuleBean;
import weblogic.j2ee.descriptor.wl.WeblogicExtensionBean;
import weblogic.management.DeploymentException;

public class CustomModulesCreator implements ModulesCreator {
   public Module[] create(FlowContext context) throws DeploymentException {
      WeblogicExtensionBean extDD = context.getWLExtensionDD();
      CustomModuleBean[] customModules = null;
      if (extDD != null) {
         customModules = extDD.getCustomModules();
      }

      List result = new ArrayList();
      CustomModuleHelper.createCustomModules(ModuleType.EAR, (String)null, (String)null, customModules, result, context.getCustomModuleFactories());
      return (Module[])result.toArray(new Module[result.size()]);
   }
}
