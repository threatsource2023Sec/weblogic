package weblogic.security.service;

import com.bea.common.security.service.PolicyConsumerService;
import weblogic.security.spi.Resource;

public class GenericPolicyConsumer {
   private boolean resTypeSet = false;
   private Resource[] resources = null;
   private PolicyConsumerService policyConsumerService = null;

   public GenericPolicyConsumer(PolicyConsumerService policyConsumerService) {
      this.policyConsumerService = policyConsumerService;
   }

   public boolean isEnabled() {
      return this.policyConsumerService == null ? false : this.policyConsumerService.isPolicyConsumerAvailable();
   }

   public synchronized void setConsumableResourceType(ConsumableResource rootResource) {
      if (this.resTypeSet) {
         throw new IllegalStateException("Resource type already set");
      } else {
         this.resources = new Resource[]{rootResource};
         this.resTypeSet = true;
      }
   }

   public ConsumablePolicyHandler getConsumablePolicyHandler(String name, String version, String timeStamp) throws ConsumptionException {
      if (!this.resTypeSet) {
         throw new IllegalStateException("Resource type not set");
      } else {
         PolicyConsumerService.PolicyCollectionHandler handler = null;
         handler = this.policyConsumerService.getPolicyCollectionHandler(name, version, timeStamp, this.resources);
         return handler != null ? new ConsumablePolicyHandler(handler) : null;
      }
   }
}
