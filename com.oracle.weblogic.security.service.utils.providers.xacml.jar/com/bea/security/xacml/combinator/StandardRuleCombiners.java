package com.bea.security.xacml.combinator;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.Rule;
import com.bea.security.xacml.Configuration;
import com.bea.security.xacml.EvaluationPlanException;
import com.bea.security.xacml.RuleCombinerEvaluator;
import com.bea.security.xacml.combinator.standard.StandardRuleCombinerLibrary;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StandardRuleCombiners implements RuleCombinerEvaluatorFactory {
   private List libraries = Collections.synchronizedList(new ArrayList());

   public StandardRuleCombiners() throws URISyntaxException {
      this.register(new StandardRuleCombinerLibrary());
   }

   public void register(SimpleRuleCombinerEvaluatorLibrary library) {
      this.libraries.add(library);
   }

   public RuleCombinerEvaluator createCombiner(URI ruleCombiningAlgId, List rules, List cparms, List rcparms, Collection designatorMatches, Map variables, Configuration config, Iterator otherFactories) throws EvaluationPlanException, URISyntaxException {
      List ruleEvals = new ArrayList();
      if (rules != null) {
         Iterator r = rules.iterator();

         while(r.hasNext()) {
            ruleEvals.add(config.getRuleEvaluatorRegistry().getEvaluator((Rule)r.next(), designatorMatches, variables, config));
         }
      }

      RuleCombinerEvaluator result = null;
      Iterator it = this.libraries.iterator();

      while(it.hasNext()) {
         result = ((SimpleRuleCombinerEvaluatorLibrary)it.next()).createCombiner(ruleCombiningAlgId, ruleEvals);
         if (result != null) {
            break;
         }
      }

      return result;
   }
}
