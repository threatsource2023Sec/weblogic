package com.bea.httppubsub.security.wls;

import com.bea.common.security.service.RoleConsumerService;
import weblogic.security.service.ConsumptionException;
import weblogic.security.spi.Resource;

public class ChannelRoleHandler {
   private RoleConsumerService.RoleCollectionHandler roleCollectionHandler = null;

   public ChannelRoleHandler(RoleConsumerService.RoleCollectionHandler handler) {
      this.roleCollectionHandler = handler;
   }

   public void setRole(Resource resource, String roleName, String[] userAndGroupNames) throws ConsumptionException {
      this.roleCollectionHandler.setRole(resource, roleName, userAndGroupNames);
   }

   public void setRoles(Resource resource, String roleName, String[] userAndGroupNames) throws ConsumptionException {
      this.setRole(resource, roleName, userAndGroupNames);
   }

   public void done() throws ConsumptionException {
      this.roleCollectionHandler.done();
   }
}
