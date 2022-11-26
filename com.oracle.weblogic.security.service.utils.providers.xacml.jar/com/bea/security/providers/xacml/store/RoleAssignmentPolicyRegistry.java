package com.bea.security.providers.xacml.store;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.AbstractPolicy;
import com.bea.common.security.xacml.policy.IdReference;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.PolicyStoreException;
import com.bea.security.xacml.store.PolicyFinder;
import com.bea.security.xacml.store.PolicyRegistry;
import java.util.Map;
import java.util.Set;

public class RoleAssignmentPolicyRegistry implements PolicyRegistry {
   private RoleAssignmentPolicyRegistryProxy proxy = new NullPolicyRegistryProxy();

   public void register(PolicyFinder finder) {
      if (this.proxy instanceof NullPolicyRegistryProxy) {
         this.proxy = new SingleRoleAssignmentPolicyRegistryProxy((ApplicableRoleAssignmentPolicyFinder)(finder instanceof ApplicableRoleAssignmentPolicyFinder ? (ApplicableRoleAssignmentPolicyFinder)finder : new ApplicableRoleAssignmentPolicyFinderWrapper(finder)));
      } else {
         if (this.proxy instanceof SingleRoleAssignmentPolicyRegistryProxy) {
            this.proxy = new MultipleRoleAssignmentPolicyRegistryProxy((SingleRoleAssignmentPolicyRegistryProxy)this.proxy);
         }

         ((MultipleRoleAssignmentPolicyRegistryProxy)this.proxy).register(finder);
      }

   }

   public Map findRoleAssignmentPolicy(EvaluationCtx context) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return this.proxy.findRoleAssignmentPolicy(context);
   }

   public AbstractPolicy find(IdReference reference) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return this.proxy.find(reference);
   }

   public Set getAllPolicies() throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return this.proxy.getAllPolicies();
   }

   public Set getAllPolicySets() throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return this.proxy.getAllPolicySets();
   }
}
