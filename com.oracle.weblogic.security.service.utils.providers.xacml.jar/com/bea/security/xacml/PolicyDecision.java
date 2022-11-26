package com.bea.security.xacml;

import java.util.List;

public class PolicyDecision {
   public static final int PERMIT = 0;
   public static final int DENY = 1;
   public static final int NOT_APPLICABLE = 2;
   private int decision;
   private List obligations;
   private boolean isTargetApplicable;
   private static final PolicyDecision PERMIT_DECISION = new PolicyDecision(0);
   private static final PolicyDecision DENY_DECISION = new PolicyDecision(1);
   private static final PolicyDecision NOT_APPLICABLE_DECISION = new PolicyDecision(2);
   private static final PolicyDecision TARGET_NOT_APPLICABLE_DECISION = new PolicyDecision(2, false);

   public static PolicyDecision getPermitDecision() {
      return PERMIT_DECISION;
   }

   public static PolicyDecision getDenyDecision() {
      return DENY_DECISION;
   }

   public static PolicyDecision getNotApplicableDecision() {
      return NOT_APPLICABLE_DECISION;
   }

   public static PolicyDecision getTargetNotApplicableDecision() {
      return TARGET_NOT_APPLICABLE_DECISION;
   }

   public static PolicyDecision getPermitDecision(List obligations) {
      return new PolicyDecision(0, obligations);
   }

   public static PolicyDecision getDenyDecision(List obligations) {
      return new PolicyDecision(1, obligations);
   }

   private PolicyDecision(int decision) {
      this(decision, (List)null);
   }

   private PolicyDecision(int decision, List obligations) {
      this.isTargetApplicable = true;
      this.decision = decision;
      this.obligations = obligations;
   }

   private PolicyDecision(int decision, boolean isTargetApplicable) {
      this(decision, (List)null);
      this.isTargetApplicable = isTargetApplicable;
   }

   public int getDecisionValue() {
      return this.decision;
   }

   public boolean hasObligations() {
      return this.obligations != null;
   }

   public List getObligations() {
      return this.obligations;
   }

   public boolean isTargetApplicable() {
      return this.isTargetApplicable;
   }
}
