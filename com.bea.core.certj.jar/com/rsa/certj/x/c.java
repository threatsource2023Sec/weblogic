package com.rsa.certj.x;

import com.rsa.certj.FIPS140Mode;
import com.rsa.certj.FIPS140Role;
import com.rsa.jsafe.JSAFE_InvalidUseException;
import com.rsa.jsafe.crypto.CryptoJ;
import com.rsa.jsafe.crypto.FIPS140Context;
import java.io.File;

public class c {
   public final FIPS140Context a;
   public final com.rsa.jsafe.FIPS140Context b;

   c(FIPS140Context var1, com.rsa.jsafe.FIPS140Context var2) {
      this.a = var1;
      this.b = var2;
   }

   public static c a() {
      FIPS140Context var0 = CryptoJ.isFIPS140Compliant() ? CryptoJ.getFIPS140Context() : null;

      com.rsa.jsafe.FIPS140Context var1;
      try {
         var1 = com.rsa.jsafe.CryptoJ.isFIPS140Compliant() ? com.rsa.jsafe.CryptoJ.getFIPS140Context() : null;
      } catch (JSAFE_InvalidUseException var3) {
         var1 = null;
      }

      return new c(var0, var1);
   }

   public static c a(FIPS140Mode var0) {
      return a(var0, d(), (byte[])null, (File)null);
   }

   public static c a(FIPS140Mode var0, FIPS140Role var1) {
      return a(var0, var1, (byte[])null, (File)null);
   }

   public static c a(FIPS140Mode var0, FIPS140Role var1, byte[] var2, File var3) {
      FIPS140Context var4 = b(var0, var1, var2, var3);
      com.rsa.jsafe.FIPS140Context var5 = c(var0, var1, var2, var3);
      return new c(var4, var5);
   }

   public int b() {
      return this.a != null ? this.a.getModeValue() : -1;
   }

   public int c() {
      return this.a != null ? this.a.getRoleValue() : -1;
   }

   static FIPS140Context b(FIPS140Mode var0, FIPS140Role var1, byte[] var2, File var3) {
      FIPS140Context var4 = null;
      if (CryptoJ.isFIPS140Compliant()) {
         if (var0 == null || var1 == null) {
            throw new IllegalArgumentException("Parameters mode or role cannot be null.");
         }

         if (var2 == null) {
            var4 = new FIPS140Context(var0.getValue(), var1.getValue());
         } else if (var3 == null) {
            var4 = new FIPS140Context(var0.getValue(), var1.getValue(), var2);
         } else {
            var4 = new FIPS140Context(var0.getValue(), var1.getValue(), var2, var3);
         }
      }

      return var4;
   }

   static com.rsa.jsafe.FIPS140Context c(FIPS140Mode var0, FIPS140Role var1, byte[] var2, File var3) {
      com.rsa.jsafe.FIPS140Context var4 = null;
      if (CryptoJ.isFIPS140Compliant()) {
         if (var0 == null || var1 == null) {
            throw new IllegalArgumentException("Parameters mode or role cannot be null.");
         }

         try {
            if (var2 == null) {
               var4 = new com.rsa.jsafe.FIPS140Context(var0.getValue(), var1.getValue());
            } else if (var3 == null) {
               var4 = new com.rsa.jsafe.FIPS140Context(var0.getValue(), var1.getValue(), var2);
            } else {
               var4 = new com.rsa.jsafe.FIPS140Context(var0.getValue(), var1.getValue(), var2, var3);
            }
         } catch (JSAFE_InvalidUseException var6) {
            throw new AssertionError("Implementation Error: Mode/Role types must ensure only valid modes and roles are used.");
         }
      }

      return var4;
   }

   static FIPS140Role d() {
      return !CryptoJ.isFIPS140Compliant() ? null : FIPS140Role.lookup(CryptoJ.getFIPS140Context().getRoleValue());
   }
}
