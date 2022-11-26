package com.bea.security.xacml.policy;

import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.Policy;
import com.bea.common.security.xacml.policy.PolicySet;
import com.bea.security.xacml.Configuration;
import com.bea.security.xacml.EvaluationPlanException;
import com.bea.security.xacml.MissingFactoryException;
import com.bea.security.xacml.PolicyEvaluator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class PolicyEvaluatorRegistry {
   private List factories = Collections.synchronizedList(new ArrayList());

   public PolicyEvaluatorRegistry() throws URISyntaxException {
      this.register(new StandardPolicyEvaluatorFactory());
   }

   public void register(PolicyEvaluatorFactory factory) {
      this.factories.add(factory);
   }

   public PolicyEvaluator getEvaluator(Policy policy, Collection designatorMatches, Configuration config) throws EvaluationPlanException, URISyntaxException {
      PolicyEvaluator pe = null;
      ListIterator facIt = this.factories.listIterator();

      do {
         if (!facIt.hasNext()) {
            throw new MissingFactoryException("No configured factory for policy: " + policy);
         }

         PolicyEvaluatorFactory pf = (PolicyEvaluatorFactory)facIt.next();
         pe = pf.createPolicy(policy, designatorMatches, config, this.factories.listIterator(facIt.nextIndex()));
      } while(pe == null);

      return pe;
   }

   public PolicyEvaluator getEvaluator(PolicySet policy, Collection designatorMatches, Configuration config) throws EvaluationPlanException, URISyntaxException {
      PolicyEvaluator pe = null;
      ListIterator facIt = this.factories.listIterator();

      do {
         if (!facIt.hasNext()) {
            throw new MissingFactoryException("No configured factory for policy set: " + policy);
         }

         PolicyEvaluatorFactory pf = (PolicyEvaluatorFactory)facIt.next();
         pe = pf.createPolicySet(policy, designatorMatches, config, this.factories.listIterator(facIt.nextIndex()));
      } while(pe == null);

      return pe;
   }
}
