package com.bea.httppubsub.security.wls;

import com.bea.common.security.service.PolicyConsumerService;
import com.bea.httppubsub.security.ChannelResource;
import weblogic.security.service.ConsumptionException;
import weblogic.security.spi.Resource;

public class ChannelPolicyConsumer {
   private static Resource[] resources = new Resource[]{new ChannelResource((String)null, (String)null, (String)null, (String)null)};
   private PolicyConsumerService policyConsumerService = null;

   public ChannelPolicyConsumer(PolicyConsumerService policyConsumerService) {
      this.policyConsumerService = policyConsumerService;
   }

   public boolean isEnabled() {
      return this.policyConsumerService == null ? false : this.policyConsumerService.isPolicyConsumerAvailable();
   }

   public ChannelPolicyHandler getPolicyHandler(String name, String version, String timeStamp) throws ConsumptionException {
      PolicyConsumerService.PolicyCollectionHandler handler = null;
      handler = this.policyConsumerService.getPolicyCollectionHandler(name, version, timeStamp, resources);
      return handler != null ? new ChannelPolicyHandler(handler) : null;
   }
}
