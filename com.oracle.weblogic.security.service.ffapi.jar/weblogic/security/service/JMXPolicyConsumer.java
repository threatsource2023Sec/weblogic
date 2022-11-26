package weblogic.security.service;

import com.bea.common.security.service.PolicyConsumerService;
import weblogic.security.spi.Resource;

public class JMXPolicyConsumer {
   private static Resource[] resources = new Resource[]{new JMXResource((String)null, (String)null, (String)null, (String)null)};
   private AuthorizationManager am = null;
   private PolicyConsumerService policyConsumerService = null;

   JMXPolicyConsumer(AuthorizationManager am) {
      this.am = am;
   }

   public JMXPolicyConsumer(PolicyConsumerService policyConsumerService) {
      this.policyConsumerService = policyConsumerService;
   }

   public JMXPolicyHandler getJMXPolicyHandler(String name, String version, String timeStamp) throws ConsumptionException {
      AuthorizationPolicyHandler handler;
      if (this.policyConsumerService != null) {
         handler = null;
         PolicyConsumerService.PolicyCollectionHandler handler = this.policyConsumerService.getPolicyCollectionHandler(name, version, timeStamp, resources);
         return handler != null ? new JMXPolicyHandler(handler) : null;
      } else {
         handler = this.am.getAuthorizationPolicyHandler(name, version, timeStamp, resources);
         return handler != null ? new JMXPolicyHandler(handler) : null;
      }
   }
}
