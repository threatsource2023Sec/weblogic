package com.rsa.certj;

/** @deprecated */
public abstract class Provider {
   private String name;
   private int type;

   /** @deprecated */
   public Provider(int var1, String var2) throws InvalidParameterException {
      if (var2 == null) {
         throw new InvalidParameterException("Provider.Provider: name should not be null.");
      } else {
         this.type = var1;
         this.name = var2;
      }
   }

   /** @deprecated */
   public int getType() {
      return this.type;
   }

   /** @deprecated */
   public String getName() {
      return this.name;
   }

   /** @deprecated */
   public ProviderImplementation instantiate(CertJ var1) throws ProviderManagementException {
      throw new ProviderManagementException("Provider.instantiate: Each subclass of Provider should overwrite this method.");
   }
}
