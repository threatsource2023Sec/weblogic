package com.bea.security.xacml;

public class IOException extends PolicyStoreException {
   public IOException(java.io.IOException cause) {
      super((Throwable)cause);
   }

   public IOException(com.bea.common.security.xacml.IOException cause) {
      super((Throwable)cause);
   }
}
