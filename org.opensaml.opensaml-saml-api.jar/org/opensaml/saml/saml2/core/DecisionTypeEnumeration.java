package org.opensaml.saml.saml2.core;

public final class DecisionTypeEnumeration {
   public static final DecisionTypeEnumeration PERMIT = new DecisionTypeEnumeration("Permit");
   public static final DecisionTypeEnumeration DENY = new DecisionTypeEnumeration("Deny");
   public static final DecisionTypeEnumeration INDETERMINATE = new DecisionTypeEnumeration("Indeterminate");
   private String decisionType;

   protected DecisionTypeEnumeration(String newDecisionType) {
      this.decisionType = newDecisionType;
   }

   public String toString() {
      return this.decisionType;
   }
}
