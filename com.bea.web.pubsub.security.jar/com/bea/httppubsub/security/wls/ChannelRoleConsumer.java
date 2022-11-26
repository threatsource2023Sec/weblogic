package com.bea.httppubsub.security.wls;

import com.bea.common.security.service.RoleConsumerService;
import weblogic.security.service.ConsumptionException;
import weblogic.security.service.WebServiceResource;
import weblogic.security.spi.Resource;

public class ChannelRoleConsumer {
   private static Resource[] resources = new Resource[]{new WebServiceResource((String)null, (String)null, (String)null, (String)null, (String[])null)};
   private RoleConsumerService roleConsumerService = null;

   public ChannelRoleConsumer(RoleConsumerService roleConsumerService) {
      this.roleConsumerService = roleConsumerService;
   }

   public boolean isEnabled() {
      return this.roleConsumerService == null ? false : this.roleConsumerService.isRoleConsumerAvailable();
   }

   public ChannelRoleHandler getRoleHandler(String name, String version, String timeStamp) throws ConsumptionException {
      RoleConsumerService.RoleCollectionHandler handler = null;
      handler = this.roleConsumerService.getRoleCollectionHandler(name, version, timeStamp, resources);
      return handler != null ? new ChannelRoleHandler(handler) : null;
   }
}
