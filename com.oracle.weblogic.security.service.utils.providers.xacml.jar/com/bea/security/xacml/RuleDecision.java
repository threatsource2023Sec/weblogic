package com.bea.security.xacml;

public class RuleDecision {
   public static final int PERMIT = 0;
   public static final int DENY = 1;
   public static final int NOT_APPLICABLE = 2;
   private int decision;
   private static final RuleDecision PERMIT_DECISION = new RuleDecision(0);
   private static final RuleDecision DENY_DECISION = new RuleDecision(1);
   private static final RuleDecision NOT_APPLICABLE_DECISION = new RuleDecision(2);

   public static RuleDecision getPermitDecision() {
      return PERMIT_DECISION;
   }

   public static RuleDecision getDenyDecision() {
      return DENY_DECISION;
   }

   public static RuleDecision getNotApplicableDecision() {
      return NOT_APPLICABLE_DECISION;
   }

   private RuleDecision(int decision) {
      this.decision = decision;
   }

   public int getDecisionValue() {
      return this.decision;
   }
}
