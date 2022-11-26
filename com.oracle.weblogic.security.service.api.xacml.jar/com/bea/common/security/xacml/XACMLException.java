package com.bea.common.security.xacml;

public class XACMLException extends Exception {
   private static final long serialVersionUID = -7179861862235798635L;

   protected XACMLException() {
   }

   protected XACMLException(Throwable cause) {
      super(cause);
   }

   protected XACMLException(String msg) {
      super(msg);
   }

   protected XACMLException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
