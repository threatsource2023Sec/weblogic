package com.bea.security.xacml;

public class MissingFactoryException extends EvaluationPlanException {
   public MissingFactoryException(Throwable cause) {
      super(cause);
   }

   public MissingFactoryException(String msg) {
      super(msg);
   }
}
