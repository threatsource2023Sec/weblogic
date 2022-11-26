package weblogic.security.service;

import com.bea.common.security.service.PolicyConsumerService;
import weblogic.security.spi.Resource;

public class WSPolicyConsumer {
   private static Resource[] resources = new Resource[]{new WebServiceResource((String)null, (String)null, (String)null, (String)null, (String[])null)};
   private PolicyConsumerService policyConsumerService = null;

   public WSPolicyConsumer(PolicyConsumerService policyConsumerService) {
      this.policyConsumerService = policyConsumerService;
   }

   public boolean isEnabled() {
      return this.policyConsumerService == null ? false : this.policyConsumerService.isPolicyConsumerAvailable();
   }

   public WSPolicyHandler getWSPolicyHandler(String name, String version, String timeStamp) throws ConsumptionException {
      PolicyConsumerService.PolicyCollectionHandler handler = null;
      handler = this.policyConsumerService.getPolicyCollectionHandler(name, version, timeStamp, resources);
      return handler != null ? new WSPolicyHandler(handler) : null;
   }
}
