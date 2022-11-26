package com.rsa.certj;

/** @deprecated */
public final class FIPS140Mode {
   private static final FIPS140Mode[] LOOKUP = new FIPS140Mode[5];
   /** @deprecated */
   public static final FIPS140Mode FIPS140_MODE = new FIPS140Mode("FIPS140", 0);
   /** @deprecated */
   public static final FIPS140Mode NON_FIPS140_MODE = new FIPS140Mode("NON_FIPS140", 1);
   /** @deprecated */
   public static final FIPS140Mode FIPS140_SSL_MODE = new FIPS140Mode("FIPS140_SSL", 2);
   /** @deprecated */
   public static final FIPS140Mode FIPS140_ECC_MODE = new FIPS140Mode("FIPS140_ECC", 0);
   /** @deprecated */
   public static final FIPS140Mode FIPS140_SSL_ECC_MODE = new FIPS140Mode("FIPS140_SSL_ECC", 2);
   private int mode;
   private String name;

   private FIPS140Mode(String var1, int var2) {
      this.mode = var2;
      this.name = var1;
      LOOKUP[var2] = this;
   }

   /** @deprecated */
   public String getName() {
      return this.name;
   }

   /** @deprecated */
   public int getValue() {
      return this.mode;
   }

   /** @deprecated */
   public String toString() {
      return this.name;
   }

   /** @deprecated */
   public int hashCode() {
      return this.mode;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (!(var1 instanceof FIPS140Mode)) {
         return false;
      } else {
         return this.mode == ((FIPS140Mode)var1).mode;
      }
   }

   /** @deprecated */
   public static FIPS140Mode lookup(int var0) {
      return LOOKUP[var0];
   }
}
