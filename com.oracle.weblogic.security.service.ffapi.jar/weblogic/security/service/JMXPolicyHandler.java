package weblogic.security.service;

import com.bea.common.security.service.PolicyConsumerService;

public class JMXPolicyHandler {
   private AuthorizationPolicyHandler handler = null;
   private PolicyConsumerService.PolicyCollectionHandler policyCollectionHandler = null;

   JMXPolicyHandler(AuthorizationPolicyHandler handler) {
      this.handler = handler;
   }

   public JMXPolicyHandler(PolicyConsumerService.PolicyCollectionHandler handler) {
      this.policyCollectionHandler = handler;
   }

   public void setPolicy(JMXResource resource, String[] roleNames) throws ConsumptionException {
      if (this.policyCollectionHandler != null) {
         this.policyCollectionHandler.setPolicy(resource, roleNames);
      } else {
         this.handler.setPolicy(resource, roleNames);
      }
   }

   public void setUncheckedPolicy(JMXResource resource) throws ConsumptionException {
      if (this.policyCollectionHandler != null) {
         this.policyCollectionHandler.setUncheckedPolicy(resource);
      } else {
         this.handler.setUncheckedPolicy(resource);
      }
   }

   public void done() throws ConsumptionException {
      if (this.policyCollectionHandler != null) {
         this.policyCollectionHandler.done();
      } else {
         this.handler.done();
      }
   }
}
