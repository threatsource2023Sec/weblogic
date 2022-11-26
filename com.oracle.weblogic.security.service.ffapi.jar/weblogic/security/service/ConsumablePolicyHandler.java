package weblogic.security.service;

import com.bea.common.security.service.PolicyConsumerService;

public class ConsumablePolicyHandler {
   private PolicyConsumerService.PolicyCollectionHandler policyCollectionHandler = null;

   public ConsumablePolicyHandler(PolicyConsumerService.PolicyCollectionHandler handler) {
      this.policyCollectionHandler = handler;
   }

   public void setPolicy(ConsumableResource resource, String[] roleNames) throws ConsumptionException {
      this.policyCollectionHandler.setPolicy(resource, roleNames);
   }

   public void setUncheckedPolicy(ConsumableResource resource) throws ConsumptionException {
      this.policyCollectionHandler.setUncheckedPolicy(resource);
   }

   public void done() throws ConsumptionException {
      this.policyCollectionHandler.done();
   }
}
