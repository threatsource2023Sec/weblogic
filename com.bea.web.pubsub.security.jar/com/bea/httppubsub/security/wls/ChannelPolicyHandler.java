package com.bea.httppubsub.security.wls;

import com.bea.common.security.service.PolicyConsumerService;
import weblogic.security.service.ConsumptionException;
import weblogic.security.spi.Resource;

public class ChannelPolicyHandler {
   private PolicyConsumerService.PolicyCollectionHandler policyCollectionHandler = null;

   public ChannelPolicyHandler(PolicyConsumerService.PolicyCollectionHandler handler) {
      this.policyCollectionHandler = handler;
   }

   public void setPolicy(Resource resource, String[] roleNames) throws ConsumptionException {
      this.policyCollectionHandler.setPolicy(resource, roleNames);
   }

   public void setUncheckedPolicy(Resource resource) throws ConsumptionException {
      this.policyCollectionHandler.setUncheckedPolicy(resource);
   }

   public void done() throws ConsumptionException {
      if (this.policyCollectionHandler != null) {
         this.policyCollectionHandler.done();
      }
   }
}
