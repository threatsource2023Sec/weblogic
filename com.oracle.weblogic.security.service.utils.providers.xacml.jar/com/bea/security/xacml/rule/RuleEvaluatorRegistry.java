package com.bea.security.xacml.rule;

import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.Rule;
import com.bea.security.xacml.Configuration;
import com.bea.security.xacml.EvaluationPlanException;
import com.bea.security.xacml.MissingFactoryException;
import com.bea.security.xacml.RuleEvaluator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class RuleEvaluatorRegistry {
   private List factories = Collections.synchronizedList(new ArrayList());

   public RuleEvaluatorRegistry() {
      this.register(new StandardRuleEvaluatorFactory());
   }

   public void register(RuleEvaluatorFactory factory) {
      this.factories.add(factory);
   }

   public RuleEvaluator getEvaluator(Rule rule, Collection designatorMatches, Map variables, Configuration config) throws EvaluationPlanException, URISyntaxException {
      RuleEvaluator e = null;
      ListIterator facIt = this.factories.listIterator();

      do {
         if (!facIt.hasNext()) {
            throw new MissingFactoryException("No configured factory for rule: " + rule);
         }

         RuleEvaluatorFactory pf = (RuleEvaluatorFactory)facIt.next();
         e = pf.createRule(rule, designatorMatches, variables, config, this.factories.listIterator(facIt.nextIndex()));
      } while(e == null);

      return e;
   }
}
