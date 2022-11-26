package com.bea.security.xacml.target;

import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.TargetMatchEvaluator;

public class NoOpTargetMatchEvaluator implements TargetMatchEvaluator {
   private static final NoOpTargetMatchEvaluator instance = new NoOpTargetMatchEvaluator();

   public static NoOpTargetMatchEvaluator getInstance() {
      return instance;
   }

   private NoOpTargetMatchEvaluator() {
   }

   public boolean evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
      return true;
   }
}
