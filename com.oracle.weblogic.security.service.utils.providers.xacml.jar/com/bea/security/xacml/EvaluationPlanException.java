package com.bea.security.xacml;

import com.bea.common.security.xacml.XACMLException;

public class EvaluationPlanException extends XACMLException {
   public EvaluationPlanException(Throwable cause) {
      super(cause);
   }

   public EvaluationPlanException(String msg) {
      super(msg);
   }
}
