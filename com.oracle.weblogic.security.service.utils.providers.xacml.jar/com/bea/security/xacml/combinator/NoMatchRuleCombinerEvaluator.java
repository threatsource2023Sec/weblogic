package com.bea.security.xacml.combinator;

import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.RuleCombinerEvaluator;
import com.bea.security.xacml.RuleDecision;

public class NoMatchRuleCombinerEvaluator implements RuleCombinerEvaluator {
   private static final NoMatchRuleCombinerEvaluator INSTANCE = new NoMatchRuleCombinerEvaluator();

   public static NoMatchRuleCombinerEvaluator getInstance() {
      return INSTANCE;
   }

   private NoMatchRuleCombinerEvaluator() {
   }

   public RuleDecision evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
      return RuleDecision.getNotApplicableDecision();
   }
}
