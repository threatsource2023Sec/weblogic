package com.bea.security.providers.xacml.store;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.AbstractPolicy;
import com.bea.common.security.xacml.policy.IdReference;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.PolicyStoreException;
import com.bea.security.xacml.store.PolicyFinder;
import com.bea.security.xacml.store.PolicyRegistry;
import java.util.Set;

public class AuthorizationPolicyRegistry implements PolicyRegistry {
   private AuthorizationPolicyRegistryProxy proxy = new NullPolicyRegistryProxy();

   public void register(PolicyFinder finder) {
      if (this.proxy instanceof NullPolicyRegistryProxy) {
         this.proxy = new SingleAuthorizationPolicyRegistryProxy((ApplicableAuthorizationPolicyFinder)(finder instanceof ApplicableAuthorizationPolicyFinder ? (ApplicableAuthorizationPolicyFinder)finder : new ApplicableAuthorizationPolicyFinderWrapper(finder)));
      } else {
         if (this.proxy instanceof SingleAuthorizationPolicyRegistryProxy) {
            this.proxy = new MultipleAuthorizationPolicyRegistryProxy((SingleAuthorizationPolicyRegistryProxy)this.proxy);
         }

         ((MultipleAuthorizationPolicyRegistryProxy)this.proxy).register(finder);
      }

   }

   public Set findAuthorizationPolicy(EvaluationCtx context) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return this.proxy.findAuthorizationPolicy(context);
   }

   public AbstractPolicy find(IdReference reference) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return this.proxy.find(reference);
   }

   public Set getAllPolicies() throws URISyntaxException, DocumentParseException, PolicyStoreException {
      return this.proxy.getAllPolicies();
   }

   public Set getAllPolicySets() throws URISyntaxException, DocumentParseException, PolicyStoreException {
      return this.proxy.getAllPolicySets();
   }
}
