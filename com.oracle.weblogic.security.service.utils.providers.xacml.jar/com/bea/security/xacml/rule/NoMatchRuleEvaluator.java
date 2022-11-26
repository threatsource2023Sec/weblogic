package com.bea.security.xacml.rule;

import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.RuleDecision;
import com.bea.security.xacml.RuleEvaluator;

public class NoMatchRuleEvaluator implements RuleEvaluator {
   private static final NoMatchRuleEvaluator PERMIT = new NoMatchRuleEvaluator(true);
   private static final NoMatchRuleEvaluator DENY = new NoMatchRuleEvaluator(false);
   private boolean hasPermitEffect;

   public static NoMatchRuleEvaluator getInstance(boolean hasPermitEffect) {
      return hasPermitEffect ? PERMIT : DENY;
   }

   private NoMatchRuleEvaluator(boolean hasPermitEffect) {
      this.hasPermitEffect = hasPermitEffect;
   }

   public RuleDecision evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
      return RuleDecision.getNotApplicableDecision();
   }

   public boolean hasPermitEffect() {
      return this.hasPermitEffect;
   }
}
