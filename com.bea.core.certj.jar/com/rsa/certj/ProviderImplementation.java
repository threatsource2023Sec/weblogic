package com.rsa.certj;

import com.rsa.certj.x.c;

/** @deprecated */
public abstract class ProviderImplementation {
   private String name;
   /** @deprecated */
   protected CertJ certJ;
   /** @deprecated */
   protected final c context;

   /** @deprecated */
   public ProviderImplementation(CertJ var1, String var2) throws InvalidParameterException {
      if (var1 == null) {
         throw new InvalidParameterException("ProviderImplementation.ProviderImplementation: certJ cannot be null.");
      } else if (var2 == null) {
         throw new InvalidParameterException("ProviderImplementation.ProviderImplementation: name has to be a non-null String.");
      } else {
         this.certJ = var1;
         this.name = var2;
         this.context = CertJInternalHelper.context(var1);
      }
   }

   /** @deprecated */
   public String getName() {
      return this.name;
   }

   /** @deprecated */
   public void unregister() {
   }

   /** @deprecated */
   public String toString() {
      return "Provider named " + this.name;
   }
}
