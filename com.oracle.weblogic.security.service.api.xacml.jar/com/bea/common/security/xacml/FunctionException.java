package com.bea.common.security.xacml;

public class FunctionException extends XACMLException {
   private static final long serialVersionUID = 6558797020325961991L;

   public FunctionException(String msg) {
      super(msg);
   }

   public FunctionException(Throwable th) {
      super(th);
   }
}
