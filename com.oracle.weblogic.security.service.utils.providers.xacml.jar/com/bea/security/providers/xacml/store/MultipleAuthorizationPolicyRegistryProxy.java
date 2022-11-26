package com.bea.security.providers.xacml.store;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.AbstractPolicy;
import com.bea.common.security.xacml.policy.IdReference;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.PolicyStoreException;
import com.bea.security.xacml.store.PolicyFinder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

class MultipleAuthorizationPolicyRegistryProxy implements AuthorizationPolicyRegistryProxy {
   private List finders = Collections.synchronizedList(new ArrayList());

   public MultipleAuthorizationPolicyRegistryProxy() {
   }

   public MultipleAuthorizationPolicyRegistryProxy(SingleAuthorizationPolicyRegistryProxy singleProxy) {
      this.register(singleProxy.getFinder());
   }

   public void register(PolicyFinder finder) {
      this.finders.add(finder instanceof ApplicableAuthorizationPolicyFinder ? (ApplicableAuthorizationPolicyFinder)finder : new ApplicableAuthorizationPolicyFinderWrapper(finder));
   }

   public Set findAuthorizationPolicy(EvaluationCtx context) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      Set l = new HashSet();
      Iterator it = this.finders.iterator();

      while(it.hasNext()) {
         Set ir = ((ApplicableAuthorizationPolicyFinder)it.next()).findAuthorizationPolicy(context);
         if (ir != null) {
            l.addAll(ir);
         }
      }

      return !l.isEmpty() ? l : null;
   }

   public AbstractPolicy find(IdReference reference) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      AbstractPolicy pt = null;
      ListIterator finderIt = this.finders.listIterator();

      while(finderIt.hasNext()) {
         PolicyFinder pf = (PolicyFinder)finderIt.next();
         pt = pf.find(reference, this.finders.listIterator(finderIt.nextIndex()));
         if (pt != null) {
            break;
         }
      }

      return pt;
   }

   public Set getAllPolicies() throws DocumentParseException, URISyntaxException, PolicyStoreException {
      Set l = new HashSet();
      Iterator it = this.finders.iterator();

      while(it.hasNext()) {
         Set psr = ((ApplicableAuthorizationPolicyFinder)it.next()).getAllPolicies();
         if (psr != null) {
            l.addAll(psr);
         }
      }

      return !l.isEmpty() ? l : null;
   }

   public Set getAllPolicySets() throws DocumentParseException, URISyntaxException, PolicyStoreException {
      Set l = new HashSet();
      Iterator it = this.finders.iterator();

      while(it.hasNext()) {
         Set psr = ((ApplicableAuthorizationPolicyFinder)it.next()).getAllPolicySets();
         if (psr != null) {
            l.addAll(psr);
         }
      }

      return !l.isEmpty() ? l : null;
   }
}
