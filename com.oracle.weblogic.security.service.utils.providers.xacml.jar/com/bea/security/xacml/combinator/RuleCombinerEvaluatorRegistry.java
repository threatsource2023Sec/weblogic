package com.bea.security.xacml.combinator;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.security.xacml.Configuration;
import com.bea.security.xacml.EvaluationPlanException;
import com.bea.security.xacml.MissingFactoryException;
import com.bea.security.xacml.RuleCombinerEvaluator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class RuleCombinerEvaluatorRegistry {
   private List factories = Collections.synchronizedList(new ArrayList());

   public RuleCombinerEvaluatorRegistry() throws URISyntaxException {
      this.register(new StandardRuleCombiners());
   }

   public void register(RuleCombinerEvaluatorFactory factory) {
      this.factories.add(factory);
   }

   public RuleCombinerEvaluator getEvaluator(URI ruleCombiningAlgId, List rules, List cparms, List rcparms, Collection designatorMatches, Map variables, Configuration config) throws EvaluationPlanException, URISyntaxException {
      RuleCombinerEvaluator e = null;
      ListIterator facIt = this.factories.listIterator();

      do {
         if (!facIt.hasNext()) {
            throw new MissingFactoryException("No factory configured for rule combining algorithm and matching policy: " + ruleCombiningAlgId.toString());
         }

         RuleCombinerEvaluatorFactory pf = (RuleCombinerEvaluatorFactory)facIt.next();
         e = pf.createCombiner(ruleCombiningAlgId, rules, cparms, rcparms, designatorMatches, variables, config, this.factories.listIterator(facIt.nextIndex()));
      } while(e == null);

      return e;
   }
}
