package weblogic.security.service;

import com.bea.common.security.service.RoleConsumerService;
import weblogic.security.spi.Resource;

public class WSRoleConsumer {
   private static Resource[] resources = new Resource[]{new WebServiceResource((String)null, (String)null, (String)null, (String)null, (String[])null)};
   private RoleConsumerService roleConsumerService = null;

   public WSRoleConsumer(RoleConsumerService roleConsumerService) {
      this.roleConsumerService = roleConsumerService;
   }

   public boolean isEnabled() {
      return this.roleConsumerService == null ? false : this.roleConsumerService.isRoleConsumerAvailable();
   }

   public WSRoleHandler getWSRoleHandler(String name, String version, String timeStamp) throws ConsumptionException {
      RoleConsumerService.RoleCollectionHandler handler = null;
      handler = this.roleConsumerService.getRoleCollectionHandler(name, version, timeStamp, resources);
      return handler != null ? new WSRoleHandler(handler) : null;
   }
}
