package com.bea.security.xacml.target;

import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.TargetMatchEvaluator;

public class NoMatchTargetMatchEvaluator implements TargetMatchEvaluator {
   private static final NoMatchTargetMatchEvaluator instance = new NoMatchTargetMatchEvaluator();

   public static NoMatchTargetMatchEvaluator getInstance() {
      return instance;
   }

   private NoMatchTargetMatchEvaluator() {
   }

   public boolean evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
      return false;
   }
}
