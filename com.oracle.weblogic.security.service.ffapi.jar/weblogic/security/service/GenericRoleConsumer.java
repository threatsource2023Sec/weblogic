package weblogic.security.service;

import com.bea.common.security.service.RoleConsumerService;
import weblogic.security.spi.Resource;

public class GenericRoleConsumer {
   private boolean resTypeSet = false;
   private Resource[] resources = null;
   private RoleConsumerService roleConsumerService = null;

   public GenericRoleConsumer(RoleConsumerService roleConsumerService) {
      this.roleConsumerService = roleConsumerService;
   }

   public boolean isEnabled() {
      return this.roleConsumerService == null ? false : this.roleConsumerService.isRoleConsumerAvailable();
   }

   public synchronized void setConsumableResourceType(ConsumableResource rootResource) {
      if (this.resTypeSet) {
         throw new IllegalStateException("Resource type already set");
      } else {
         this.resources = new Resource[]{rootResource};
         this.resTypeSet = true;
      }
   }

   public ConsumableRoleHandler getConsumableRoleHandler(String name, String version, String timeStamp) throws ConsumptionException {
      if (!this.resTypeSet) {
         throw new IllegalStateException("Resource type not set");
      } else {
         RoleConsumerService.RoleCollectionHandler handler = null;
         handler = this.roleConsumerService.getRoleCollectionHandler(name, version, timeStamp, this.resources);
         return handler != null ? new ConsumableRoleHandler(handler) : null;
      }
   }
}
