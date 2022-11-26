package weblogic.security.service;

import com.bea.common.security.service.RoleConsumerService;

public class ConsumableRoleHandler {
   private RoleConsumerService.RoleCollectionHandler roleCollectionHandler = null;

   public ConsumableRoleHandler(RoleConsumerService.RoleCollectionHandler handler) {
      this.roleCollectionHandler = handler;
   }

   public void setRole(ConsumableResource resource, String roleName, String[] userAndGroupNames) throws ConsumptionException {
      this.roleCollectionHandler.setRole(resource, roleName, userAndGroupNames);
   }

   public void done() throws ConsumptionException {
      this.roleCollectionHandler.done();
   }
}
