package com.bea.security.providers.xacml.store;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.AbstractPolicy;
import com.bea.common.security.xacml.policy.IdReference;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.PolicyStoreException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class SingleRoleAssignmentPolicyRegistryProxy implements RoleAssignmentPolicyRegistryProxy {
   private ApplicableRoleAssignmentPolicyFinder finder;

   public SingleRoleAssignmentPolicyRegistryProxy(ApplicableRoleAssignmentPolicyFinder finder) {
      this.finder = finder;
   }

   public ApplicableRoleAssignmentPolicyFinder getFinder() {
      return this.finder;
   }

   public AbstractPolicy find(IdReference reference) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return this.finder.find(reference, (Iterator)null);
   }

   public Map findRoleAssignmentPolicy(EvaluationCtx context) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return this.finder.findRoleAssignmentPolicy(context);
   }

   public Set getAllPolicies() throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return this.finder.getAllPolicies();
   }

   public Set getAllPolicySets() throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return this.finder.getAllPolicySets();
   }
}
