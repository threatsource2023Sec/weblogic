package org.python.bouncycastle.pqc.jcajce.spec;

import java.security.spec.AlgorithmParameterSpec;
import org.python.bouncycastle.pqc.math.linearalgebra.PolynomialRingGF2;

public class McElieceCCA2KeyGenParameterSpec implements AlgorithmParameterSpec {
   public static final String SHA1 = "SHA-1";
   public static final String SHA224 = "SHA-224";
   public static final String SHA256 = "SHA-256";
   public static final String SHA384 = "SHA-384";
   public static final String SHA512 = "SHA-512";
   public static final int DEFAULT_M = 11;
   public static final int DEFAULT_T = 50;
   private final int m;
   private final int t;
   private final int n;
   private int fieldPoly;
   private final String digest;

   public McElieceCCA2KeyGenParameterSpec() {
      this(11, 50, "SHA-256");
   }

   public McElieceCCA2KeyGenParameterSpec(int var1) {
      this(var1, "SHA-256");
   }

   public McElieceCCA2KeyGenParameterSpec(int var1, String var2) {
      if (var1 < 1) {
         throw new IllegalArgumentException("key size must be positive");
      } else {
         int var3 = 0;

         int var4;
         for(var4 = 1; var4 < var1; ++var3) {
            var4 <<= 1;
         }

         this.t = (var4 >>> 1) / var3;
         this.m = var3;
         this.n = var4;
         this.fieldPoly = PolynomialRingGF2.getIrreduciblePolynomial(var3);
         this.digest = var2;
      }
   }

   public McElieceCCA2KeyGenParameterSpec(int var1, int var2) {
      this(var1, var2, "SHA-256");
   }

   public McElieceCCA2KeyGenParameterSpec(int var1, int var2, String var3) {
      if (var1 < 1) {
         throw new IllegalArgumentException("m must be positive");
      } else if (var1 > 32) {
         throw new IllegalArgumentException("m is too large");
      } else {
         this.m = var1;
         this.n = 1 << var1;
         if (var2 < 0) {
            throw new IllegalArgumentException("t must be positive");
         } else if (var2 > this.n) {
            throw new IllegalArgumentException("t must be less than n = 2^m");
         } else {
            this.t = var2;
            this.fieldPoly = PolynomialRingGF2.getIrreduciblePolynomial(var1);
            this.digest = var3;
         }
      }
   }

   public McElieceCCA2KeyGenParameterSpec(int var1, int var2, int var3) {
      this(var1, var2, var3, "SHA-256");
   }

   public McElieceCCA2KeyGenParameterSpec(int var1, int var2, int var3, String var4) {
      this.m = var1;
      if (var1 < 1) {
         throw new IllegalArgumentException("m must be positive");
      } else if (var1 > 32) {
         throw new IllegalArgumentException(" m is too large");
      } else {
         this.n = 1 << var1;
         this.t = var2;
         if (var2 < 0) {
            throw new IllegalArgumentException("t must be positive");
         } else if (var2 > this.n) {
            throw new IllegalArgumentException("t must be less than n = 2^m");
         } else if (PolynomialRingGF2.degree(var3) == var1 && PolynomialRingGF2.isIrreducible(var3)) {
            this.fieldPoly = var3;
            this.digest = var4;
         } else {
            throw new IllegalArgumentException("polynomial is not a field polynomial for GF(2^m)");
         }
      }
   }

   public int getM() {
      return this.m;
   }

   public int getN() {
      return this.n;
   }

   public int getT() {
      return this.t;
   }

   public int getFieldPoly() {
      return this.fieldPoly;
   }

   public String getDigest() {
      return this.digest;
   }
}
