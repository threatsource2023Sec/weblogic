package weblogic.security.service;

import com.bea.common.security.service.PolicyConsumerService;

public class WSPolicyHandler {
   private PolicyConsumerService.PolicyCollectionHandler policyCollectionHandler = null;

   public WSPolicyHandler(PolicyConsumerService.PolicyCollectionHandler handler) {
      this.policyCollectionHandler = handler;
   }

   public void setPolicy(WebServiceResource resource, String[] roleNames) throws ConsumptionException {
      this.policyCollectionHandler.setPolicy(resource, roleNames);
   }

   public void setUncheckedPolicy(WebServiceResource resource) throws ConsumptionException {
      this.policyCollectionHandler.setUncheckedPolicy(resource);
   }

   public void done() throws ConsumptionException {
      if (this.policyCollectionHandler != null) {
         this.policyCollectionHandler.done();
      }
   }
}
