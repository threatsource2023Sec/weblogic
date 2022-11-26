package com.bea.security.xacml;

public class InvalidReferenceException extends EvaluationPlanException {
   public InvalidReferenceException(Throwable cause) {
      super(cause);
   }

   public InvalidReferenceException(String msg) {
      super(msg);
   }
}
