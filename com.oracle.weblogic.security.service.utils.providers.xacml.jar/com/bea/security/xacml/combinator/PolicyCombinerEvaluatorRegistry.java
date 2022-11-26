package com.bea.security.xacml.combinator;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.security.xacml.Configuration;
import com.bea.security.xacml.EvaluationPlanException;
import com.bea.security.xacml.MissingFactoryException;
import com.bea.security.xacml.PolicyEvaluator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class PolicyCombinerEvaluatorRegistry {
   private List factories = Collections.synchronizedList(new ArrayList());

   public PolicyCombinerEvaluatorRegistry() throws URISyntaxException {
      this.register(new StandardPolicyCombiners());
   }

   public void register(PolicyCombinerEvaluatorFactory factory) {
      this.factories.add(factory);
   }

   public PolicyEvaluator getEvaluator(URI policyCombiningAlgId, List policiesPolicySetsAndReferences, List cparms, List pparms, List psparms, Collection designatorMatches, Configuration config) throws EvaluationPlanException, URISyntaxException {
      PolicyEvaluator e = null;
      ListIterator facIt = this.factories.listIterator();

      do {
         if (!facIt.hasNext()) {
            throw new MissingFactoryException("No factory configured for policy combining algorithm and matching policy set: " + policyCombiningAlgId.toString());
         }

         PolicyCombinerEvaluatorFactory pf = (PolicyCombinerEvaluatorFactory)facIt.next();
         e = pf.createCombiner(policyCombiningAlgId, policiesPolicySetsAndReferences, cparms, pparms, psparms, designatorMatches, config, this.factories.listIterator(facIt.nextIndex()));
      } while(e == null);

      return e;
   }
}
