package com.bea.security.providers.xacml.store;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.XACMLException;
import com.bea.common.security.xacml.policy.AbstractPolicy;
import com.bea.common.security.xacml.policy.Policy;
import com.bea.common.security.xacml.policy.PolicyIdReference;
import com.bea.common.security.xacml.policy.PolicySet;
import com.bea.common.security.xacml.policy.PolicySetIdReference;
import com.bea.common.security.xacml.policy.PolicySetMember;
import com.bea.common.store.bootstrap.GlobalPolicyUpdate;
import com.bea.security.xacml.store.PolicyStore;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class GlobalPolicyUpdateImpl implements GlobalPolicyUpdate {
   private static final String HEADER = "GlobalPolicyUpdateImpl: ";
   private PolicyStore policyStore;
   private LoggerSpi logger;

   public GlobalPolicyUpdateImpl(PolicyStore policyStore, LoggerSpi logger) {
      this.policyStore = policyStore;
      this.logger = logger;
   }

   public void updateGlobalPolicies() throws XACMLException {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("GlobalPolicyUpdateImpl: Entering updateGlobalPolicies()");
      }

      Set updatePolicies = new HashSet();
      Set policies = this.policyStore.readAllPolicies();
      if (policies != null) {
         Iterator var3 = policies.iterator();

         while(var3.hasNext()) {
            Policy policy = (Policy)var3.next();
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("GlobalPolicyUpdateImpl: policy: " + policy.toString());
            }

            if (policy != null && isGlobalPolicy(policy)) {
               if (this.logger.isDebugEnabled()) {
                  this.logger.debug("GlobalPolicyUpdateImpl: Updating global policy: " + getUpdatedPolicy(policy));
               }

               updatePolicies.add(new AbstractPolicy[]{policy, getUpdatedPolicy(policy)});
            }
         }
      }

      Set policySets = this.policyStore.readAllPolicySets();
      if (policySets != null) {
         Iterator var7 = policySets.iterator();

         while(var7.hasNext()) {
            PolicySet policySet = (PolicySet)var7.next();
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("GlobalPolicyUpdateImpl: policyset: " + policySet.toString());
            }

            if (policySet != null && isGlobalPolicy(policySet)) {
               if (this.logger.isDebugEnabled()) {
                  this.logger.debug("GlobalPolicyUpdateImpl: Updating global policyset: " + getUpdatedPolicySet(policySet));
               }

               updatePolicies.add(new AbstractPolicy[]{policySet, getUpdatedPolicySet(policySet)});
            }
         }
      }

      if (!updatePolicies.isEmpty()) {
         this.policyStore.updatePolicies(updatePolicies);
      }

   }

   private static boolean isGlobalPolicy(AbstractPolicy policy) {
      String policyId = policy.getId().toString();
      return policyId.startsWith("urn:bea:xacml:2.0:entitlement:") && policyId.endsWith(":top");
   }

   private static Policy getUpdatedPolicy(Policy policy) throws XACMLException {
      String policyId = getUpdatedPolicyId(policy.getId().toString());

      try {
         return new Policy(new URI(policyId), policy.getTarget(), policy.getCombiningAlgId(), policy.getDescription(), policy.getVersion(), policy.getDefaults(), policy.getCombinerParameters(), policy.getObligations(), policy.getRules(), policy.getRuleCombinerParameters(), policy.getVariableDefinitions());
      } catch (URISyntaxException var3) {
         throw new com.bea.common.security.xacml.URISyntaxException(var3);
      }
   }

   private static PolicySet getUpdatedPolicySet(PolicySet policySet) throws XACMLException {
      String policySetId = getUpdatedPolicyId(policySet.getId().toString());
      List newMembers = new ArrayList();
      List members = policySet.getPoliciesPolicySetsAndReferences();
      Iterator var4 = members.iterator();

      while(var4.hasNext()) {
         PolicySetMember member = (PolicySetMember)var4.next();
         newMembers.add(getUpdatedPolicySetMember(member));
      }

      try {
         return new PolicySet(new URI(policySetId), policySet.getTarget(), policySet.getCombiningAlgId(), policySet.getDescription(), policySet.getVersion(), policySet.getDefaults(), policySet.getCombinerParameters(), policySet.getObligations(), newMembers, policySet.getPolicyCombinerParameters(), policySet.getPolicySetCombinerParameters());
      } catch (URISyntaxException var6) {
         throw new com.bea.common.security.xacml.URISyntaxException(var6);
      }
   }

   private static PolicySetMember getUpdatedPolicySetMember(PolicySetMember member) throws XACMLException {
      if (member instanceof Policy) {
         return getUpdatedPolicy((Policy)member);
      } else if (member instanceof PolicySet) {
         return getUpdatedPolicySet((PolicySet)member);
      } else if (member instanceof PolicyIdReference) {
         return getUpdatedPolicyIdReference((PolicyIdReference)member);
      } else {
         return member instanceof PolicySetIdReference ? getUpdatedPolicySetIdReference((PolicySetIdReference)member) : null;
      }
   }

   private static PolicySetIdReference getUpdatedPolicySetIdReference(PolicySetIdReference policySetIdReference) throws com.bea.common.security.xacml.URISyntaxException {
      String policyId = getUpdatedPolicyId(policySetIdReference.getReference().toString());

      try {
         return new PolicySetIdReference(new URI(policyId), policySetIdReference.getVersion(), policySetIdReference.getEarliestVersion(), policySetIdReference.getLatestVersion());
      } catch (URISyntaxException var3) {
         throw new com.bea.common.security.xacml.URISyntaxException(var3);
      }
   }

   private static PolicyIdReference getUpdatedPolicyIdReference(PolicyIdReference policyIdReference) throws XACMLException {
      String policyId = getUpdatedPolicyId(policyIdReference.getReference().toString());

      try {
         return new PolicyIdReference(new URI(policyId), policyIdReference.getVersion(), policyIdReference.getEarliestVersion(), policyIdReference.getLatestVersion());
      } catch (URISyntaxException var3) {
         throw new com.bea.common.security.xacml.URISyntaxException(var3);
      }
   }

   private static String getUpdatedPolicyId(String policyId) {
      return policyId.endsWith("top") ? policyId.substring(0, policyId.length() - 3) : policyId;
   }

   public static Policy checkAndUpdatePolicy(Policy policy) throws XACMLException {
      return isGlobalPolicy(policy) ? getUpdatedPolicy(policy) : policy;
   }

   public static PolicySet checkAndUpdatePolicySet(PolicySet policySet) throws XACMLException {
      return isGlobalPolicy(policySet) ? getUpdatedPolicySet(policySet) : policySet;
   }
}
