package weblogic.ejb.spi;

import weblogic.application.ApplicationContextInternal;

public final class DynamicEJBModuleFactory {
   public static DynamicEJBModule createDynamicEJBModule(String moduleId, String uniqueId, ApplicationContextInternal ctx) {
      return new weblogic.ejb.container.deployer.DynamicEJBModule(moduleId, uniqueId, ctx);
   }
}
