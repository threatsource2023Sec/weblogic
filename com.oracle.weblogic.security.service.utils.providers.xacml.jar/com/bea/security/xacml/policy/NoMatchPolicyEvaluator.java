package com.bea.security.xacml.policy;

import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.PolicyDecision;
import com.bea.security.xacml.PolicyEvaluator;

public class NoMatchPolicyEvaluator implements PolicyEvaluator {
   private static final NoMatchPolicyEvaluator INSTANCE = new NoMatchPolicyEvaluator();

   public static NoMatchPolicyEvaluator getInstance() {
      return INSTANCE;
   }

   private NoMatchPolicyEvaluator() {
   }

   public PolicyDecision evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
      return PolicyDecision.getNotApplicableDecision();
   }
}
