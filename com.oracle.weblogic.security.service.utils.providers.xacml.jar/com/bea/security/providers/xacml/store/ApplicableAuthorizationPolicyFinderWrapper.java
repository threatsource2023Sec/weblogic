package com.bea.security.providers.xacml.store;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.AbstractPolicy;
import com.bea.common.security.xacml.policy.IdReference;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.PolicyStoreException;
import com.bea.security.xacml.store.PolicyFinder;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

class ApplicableAuthorizationPolicyFinderWrapper implements ApplicableAuthorizationPolicyFinder {
   private PolicyFinder inner;

   public ApplicableAuthorizationPolicyFinderWrapper(PolicyFinder inner) {
      this.inner = inner;
   }

   public Set findAuthorizationPolicy(EvaluationCtx context) throws DocumentParseException, PolicyStoreException, URISyntaxException {
      Set out = new HashSet();
      Set pri = this.inner.getAllPolicies();
      if (pri != null) {
         out.addAll(pri);
      }

      Set psri = this.inner.getAllPolicySets();
      if (psri != null) {
         out.addAll(psri);
      }

      return !out.isEmpty() ? out : null;
   }

   public AbstractPolicy find(IdReference reference) throws DocumentParseException, PolicyStoreException, URISyntaxException {
      return this.inner.find(reference);
   }

   public AbstractPolicy find(IdReference reference, Iterator otherFinders) throws DocumentParseException, PolicyStoreException, URISyntaxException {
      return this.inner.find(reference, otherFinders);
   }

   public Set getAllPolicies() throws DocumentParseException, PolicyStoreException, URISyntaxException {
      return this.inner.getAllPolicies();
   }

   public Set getAllPolicySets() throws DocumentParseException, PolicyStoreException, URISyntaxException {
      return this.inner.getAllPolicySets();
   }
}
