package com.bea.security.providers.xacml.store;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.AbstractPolicy;
import com.bea.common.security.xacml.policy.IdReference;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.PolicyStoreException;
import java.util.Iterator;
import java.util.Set;

class SingleAuthorizationPolicyRegistryProxy implements AuthorizationPolicyRegistryProxy {
   private ApplicableAuthorizationPolicyFinder finder;

   public SingleAuthorizationPolicyRegistryProxy(ApplicableAuthorizationPolicyFinder finder) {
      this.finder = finder;
   }

   public ApplicableAuthorizationPolicyFinder getFinder() {
      return this.finder;
   }

   public AbstractPolicy find(IdReference reference) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return this.finder.find(reference, (Iterator)null);
   }

   public Set findAuthorizationPolicy(EvaluationCtx context) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return this.finder.findAuthorizationPolicy(context);
   }

   public Set getAllPolicies() throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return this.finder.getAllPolicies();
   }

   public Set getAllPolicySets() throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return this.finder.getAllPolicySets();
   }
}
