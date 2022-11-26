package com.bea.security.xacml.combinator;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.Policy;
import com.bea.common.security.xacml.policy.PolicyIdReference;
import com.bea.common.security.xacml.policy.PolicySet;
import com.bea.common.security.xacml.policy.PolicySetIdReference;
import com.bea.common.security.xacml.policy.PolicySetMember;
import com.bea.security.xacml.Configuration;
import com.bea.security.xacml.EvaluationPlanException;
import com.bea.security.xacml.InvalidReferenceException;
import com.bea.security.xacml.PolicyEvaluator;
import com.bea.security.xacml.PolicyStoreException;
import com.bea.security.xacml.combinator.standard.StandardPolicyCombinerLibrary;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class StandardPolicyCombiners implements PolicyCombinerEvaluatorFactory {
   private List libraries = Collections.synchronizedList(new ArrayList());

   public StandardPolicyCombiners() throws URISyntaxException {
      this.register(new StandardPolicyCombinerLibrary());
   }

   public void register(SimplePolicyCombinerEvaluatorLibrary library) {
      this.libraries.add(library);
   }

   public PolicyEvaluator createCombiner(URI policyCombiningAlgId, List policiesPolicySetsAndReferences, List cparms, List pparms, List psparms, Collection designatorMatches, Configuration config, Iterator otherFactories) throws InvalidReferenceException, EvaluationPlanException, URISyntaxException {
      List policyEvals = new ArrayList();
      PolicyEvaluator pe;
      if (policiesPolicySetsAndReferences != null) {
         for(Iterator it = policiesPolicySetsAndReferences.iterator(); it.hasNext(); policyEvals.add(pe)) {
            PolicySetMember next = (PolicySetMember)it.next();

            try {
               if (next instanceof PolicySet) {
                  pe = config.getPolicyEvaluatorRegistry().getEvaluator((PolicySet)next, designatorMatches, config);
               } else if (next instanceof Policy) {
                  pe = config.getPolicyEvaluatorRegistry().getEvaluator((Policy)next, designatorMatches, config);
               } else if (next instanceof PolicySetIdReference) {
                  PolicySetIdReference ir = (PolicySetIdReference)next;
                  PolicySet ps = (PolicySet)config.getPolicyRegistry().find(ir);
                  if (ps == null) {
                     throw new InvalidReferenceException("Policy set not found: " + ir.getReference());
                  }

                  pe = config.getPolicyEvaluatorRegistry().getEvaluator(ps, designatorMatches, config);
               } else {
                  if (!(next instanceof PolicyIdReference)) {
                     throw new InvalidReferenceException("Unknown policy set member type");
                  }

                  PolicyIdReference ir = (PolicyIdReference)next;
                  Policy p = (Policy)config.getPolicyRegistry().find(ir);
                  if (p == null) {
                     throw new InvalidReferenceException("Policy not found: " + ir.getReference());
                  }

                  pe = config.getPolicyEvaluatorRegistry().getEvaluator(p, designatorMatches, config);
               }
            } catch (PolicyStoreException var15) {
               throw new InvalidReferenceException(var15);
            } catch (DocumentParseException var16) {
               throw new EvaluationPlanException(var16);
            }
         }
      }

      PolicyEvaluator result = null;
      Iterator it = this.libraries.iterator();

      while(it.hasNext()) {
         result = ((SimplePolicyCombinerEvaluatorLibrary)it.next()).createCombiner(policyCombiningAlgId, policyEvals);
         if (result != null) {
            break;
         }
      }

      return result;
   }
}
