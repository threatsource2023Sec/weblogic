package com.bea.security.providers.xacml.store;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.AbstractPolicy;
import com.bea.common.security.xacml.policy.IdReference;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.PolicyStoreException;
import java.util.Map;
import java.util.Set;

class NullPolicyRegistryProxy implements AuthorizationPolicyRegistryProxy, RoleAssignmentPolicyRegistryProxy {
   public NullPolicyRegistryProxy() {
   }

   public Set findAuthorizationPolicy(EvaluationCtx context) {
      return null;
   }

   public Map findRoleAssignmentPolicy(EvaluationCtx context) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return null;
   }

   public AbstractPolicy find(IdReference reference) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return null;
   }

   public Set getAllPolicies() throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return null;
   }

   public Set getAllPolicySets() throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return null;
   }
}
