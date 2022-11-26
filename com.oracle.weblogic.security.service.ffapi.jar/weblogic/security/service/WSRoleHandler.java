package weblogic.security.service;

import com.bea.common.security.service.RoleConsumerService;

public class WSRoleHandler {
   private RoleConsumerService.RoleCollectionHandler roleCollectionHandler = null;

   public WSRoleHandler(RoleConsumerService.RoleCollectionHandler handler) {
      this.roleCollectionHandler = handler;
   }

   public void setRole(WebServiceResource resource, String roleName, String[] userAndGroupNames) throws ConsumptionException {
      this.roleCollectionHandler.setRole(resource, roleName, userAndGroupNames);
   }

   public void setRoles(WebServiceResource resource, String roleName, String[] userAndGroupNames) throws ConsumptionException {
      this.setRole(resource, roleName, userAndGroupNames);
   }

   public void done() throws ConsumptionException {
      this.roleCollectionHandler.done();
   }
}
