package com.bea.common.security.xacml;

public class IOException extends XACMLException {
   private static final long serialVersionUID = -5283891001627598227L;

   public IOException(java.io.IOException cause) {
      super((Throwable)cause);
   }
}
