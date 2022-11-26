package com.rsa.certj;

/** @deprecated */
public final class FIPS140Role {
   /** @deprecated */
   public static final FIPS140Role CRYPTO_OFFICER_ROLE = new FIPS140Role("CRYPTO_OFFICER", 10);
   /** @deprecated */
   public static final FIPS140Role USER_ROLE = new FIPS140Role("USER", 11);
   private int role;
   private String name;

   private FIPS140Role(String var1, int var2) {
      this.name = var1;
      this.role = var2;
   }

   /** @deprecated */
   public String getName() {
      return this.name;
   }

   /** @deprecated */
   public int getValue() {
      return this.role;
   }

   /** @deprecated */
   public String toString() {
      return this.name;
   }

   /** @deprecated */
   public int hashCode() {
      return this.role;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (!(var1 instanceof FIPS140Role)) {
         return false;
      } else {
         return this.role == ((FIPS140Role)var1).role;
      }
   }

   /** @deprecated */
   public static FIPS140Role lookup(int var0) {
      return var0 == CRYPTO_OFFICER_ROLE.role ? CRYPTO_OFFICER_ROLE : USER_ROLE;
   }
}
