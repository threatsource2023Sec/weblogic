package com.bea.security.providers.xacml.store;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.AbstractPolicy;
import com.bea.common.security.xacml.policy.IdReference;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.PolicyStoreException;
import com.bea.security.xacml.store.PolicyFinder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class ApplicableRoleAssignmentPolicyFinderWrapper implements ApplicableRoleAssignmentPolicyFinder {
   private PolicyFinder inner;

   public ApplicableRoleAssignmentPolicyFinderWrapper(PolicyFinder inner) {
      this.inner = inner;
   }

   public Map findRoleAssignmentPolicy(EvaluationCtx context) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      throw new UnsupportedOperationException();
   }

   public AbstractPolicy find(IdReference reference) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return this.inner.find(reference);
   }

   public AbstractPolicy find(IdReference reference, Iterator otherFinders) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return this.inner.find(reference, otherFinders);
   }

   public Set getAllPolicies() throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return this.inner.getAllPolicies();
   }

   public Set getAllPolicySets() throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return this.inner.getAllPolicySets();
   }
}
