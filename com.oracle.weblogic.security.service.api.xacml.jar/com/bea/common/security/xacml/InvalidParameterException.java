package com.bea.common.security.xacml;

public class InvalidParameterException extends XACMLException {
   private static final long serialVersionUID = 7419825438522886200L;

   public InvalidParameterException(Throwable e) {
      super(e);
   }

   public InvalidParameterException(String msg) {
      super(msg);
   }
}
