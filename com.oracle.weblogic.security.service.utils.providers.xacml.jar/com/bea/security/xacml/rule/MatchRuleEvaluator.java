package com.bea.security.xacml.rule;

import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.RuleDecision;
import com.bea.security.xacml.RuleEvaluator;

public class MatchRuleEvaluator implements RuleEvaluator {
   private static final MatchRuleEvaluator PERMIT = new MatchRuleEvaluator(true);
   private static final MatchRuleEvaluator DENY = new MatchRuleEvaluator(false);
   private RuleDecision decision;

   public static MatchRuleEvaluator getInstance(boolean hasPermitEffect) {
      return hasPermitEffect ? PERMIT : DENY;
   }

   private MatchRuleEvaluator(boolean hasPermitEffect) {
      this.decision = hasPermitEffect ? RuleDecision.getPermitDecision() : RuleDecision.getDenyDecision();
   }

   public RuleDecision evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
      return this.decision;
   }

   public boolean hasPermitEffect() {
      return this.decision == RuleDecision.getPermitDecision();
   }
}
