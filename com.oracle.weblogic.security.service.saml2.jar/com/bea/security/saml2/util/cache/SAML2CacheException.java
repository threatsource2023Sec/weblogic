package com.bea.security.saml2.util.cache;

public class SAML2CacheException extends RuntimeException {
   private static final long serialVersionUID = 303674868904105968L;

   public SAML2CacheException(String msg) {
      super(msg);
   }

   public SAML2CacheException(Throwable e) {
      super(e);
   }

   public SAML2CacheException(String msg, Throwable e) {
      super(msg, e);
   }
}
