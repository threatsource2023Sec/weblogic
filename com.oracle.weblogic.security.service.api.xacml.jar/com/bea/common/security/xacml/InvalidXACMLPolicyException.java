package com.bea.common.security.xacml;

public class InvalidXACMLPolicyException extends XACMLException {
   private static final long serialVersionUID = 572382776770159353L;

   public InvalidXACMLPolicyException(String msg) {
      super(msg);
   }

   public InvalidXACMLPolicyException(Throwable t) {
      super(t);
   }
}
