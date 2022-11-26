package com.rsa.certj;

import com.rsa.certj.core.util.b;

/** @deprecated */
public final class CertJVersion {
   private CertJVersion() {
   }

   /** @deprecated */
   public static String getVersionString() {
      return b.b();
   }

   /** @deprecated */
   public static String getProductID() {
      return b.c();
   }

   /** @deprecated */
   public static String getBuiltOn() {
      return b.a();
   }

   /** @deprecated */
   public static boolean isEval() {
      return false;
   }

   /** @deprecated */
   public static boolean isFIPS140Compliant() {
      return com.rsa.certj.x.b.b();
   }

   /** @deprecated */
   public static void main(String[] var0) {
      System.out.println(getProductID());
      if (isFIPS140Compliant()) {
         System.out.println("FIPS 140 compliant");
      } else {
         System.out.println("Non-FIPS 140 compliant");
      }

      if (!isEval()) {
         System.out.println("Production (non-evaluation) toolkit");
      }

   }
}
