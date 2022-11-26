package com.bea.security.providers.xacml.store;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import com.bea.security.xacml.PolicyStoreException;
import com.bea.security.xacml.store.PolicyStore;

public class DefaultPolicyStoreConfigurator implements PolicyStoreConfigurator {
   private PolicyStoreConfigInfo info;

   public DefaultPolicyStoreConfigurator(PolicyStoreConfigInfo info) {
      this.info = info;
   }

   public PolicyStore newPolicyStore(int type, AttributeRegistry registry) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      BasePolicyStore bps = type == 0 ? new AuthorizationPolicyStore(this.info, registry) : new RoleAssignmentPolicyStore(this.info, registry);
      ((BasePolicyStore)bps).init();
      return (PolicyStore)bps;
   }
}
