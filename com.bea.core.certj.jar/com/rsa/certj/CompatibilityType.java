package com.rsa.certj;

/** @deprecated */
public final class CompatibilityType {
   /** @deprecated */
   public static final CompatibilityType CERTJ_COMPATIBILITY_STRICT_CERT = new CompatibilityType("CERTJ_COMPATIBILITY_STRICT_CERT");
   /** @deprecated */
   public static final CompatibilityType CERTJ_COMPATIBILITY_SCEP = new CompatibilityType("CERTJ_COMPATIBILITY_SCEP");
   /** @deprecated */
   public static final CompatibilityType CERTJ_COMPATIBILITY_EMAIL_AVA_EA = new CompatibilityType("CERTJ_COMPATIBILITY_EMAIL_AVA_EA");
   private String compatibilityPropertyName;

   private CompatibilityType(String var1) {
      this.compatibilityPropertyName = var1;
   }

   /** @deprecated */
   public String toString() {
      return this.compatibilityPropertyName;
   }
}
