package weblogic.application.internal.flow;

import java.util.Map;
import weblogic.application.ApplicationContextInternal;
import weblogic.j2ee.descriptor.wl.WeblogicExtensionBean;
import weblogic.management.DeploymentException;

public final class CustomModuleProviderFlow extends BaseFlow {
   public CustomModuleProviderFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void prepare() throws DeploymentException {
      WeblogicExtensionBean extDD = this.appCtx.getWLExtensionDD();
      Map factoryMap = CustomModuleHelper.initFactories(extDD, this.appCtx.getAppClassLoader());
      if (factoryMap != null) {
         this.appCtx.setCustomModuleFactories(factoryMap);
      }
   }
}
