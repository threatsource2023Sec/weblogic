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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

class MultipleRoleAssignmentPolicyRegistryProxy implements RoleAssignmentPolicyRegistryProxy {
   private List finders = Collections.synchronizedList(new ArrayList());

   public MultipleRoleAssignmentPolicyRegistryProxy() {
   }

   public MultipleRoleAssignmentPolicyRegistryProxy(SingleRoleAssignmentPolicyRegistryProxy singleProxy) {
      this.register(singleProxy.getFinder());
   }

   public void register(PolicyFinder finder) {
      this.finders.add(finder instanceof ApplicableRoleAssignmentPolicyFinder ? (ApplicableRoleAssignmentPolicyFinder)finder : new ApplicableRoleAssignmentPolicyFinderWrapper(finder));
   }

   public Map findRoleAssignmentPolicy(EvaluationCtx context) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      Map map = new HashMap();
      Iterator it = this.finders.iterator();

      while(true) {
         Map imap;
         do {
            if (!it.hasNext()) {
               return !map.isEmpty() ? map : null;
            }

            imap = ((ApplicableRoleAssignmentPolicyFinder)it.next()).findRoleAssignmentPolicy(context);
         } while(imap == null);

         Set records;
         Object collector;
         for(Iterator var5 = imap.entrySet().iterator(); var5.hasNext(); ((Set)collector).addAll(records)) {
            Map.Entry entry = (Map.Entry)var5.next();
            String role = (String)entry.getKey();
            records = (Set)entry.getValue();
            collector = (Set)map.get(role);
            if (collector == null) {
               collector = new HashSet();
               map.put(role, collector);
            }
         }
      }
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
         Set psr = ((ApplicableRoleAssignmentPolicyFinder)it.next()).getAllPolicies();
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
         Set psr = ((ApplicableRoleAssignmentPolicyFinder)it.next()).getAllPolicySets();
         if (psr != null) {
            l.addAll(psr);
         }
      }

      return !l.isEmpty() ? l : null;
   }
}
