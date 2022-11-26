package org.python.bouncycastle.pqc.jcajce.spec;

import java.security.InvalidParameterException;
import java.security.spec.AlgorithmParameterSpec;
import org.python.bouncycastle.pqc.math.linearalgebra.PolynomialRingGF2;

public class McElieceKeyGenParameterSpec implements AlgorithmParameterSpec {
   public static final int DEFAULT_M = 11;
   public static final int DEFAULT_T = 50;
   private int m;
   private int t;
   private int n;
   private int fieldPoly;

   public McElieceKeyGenParameterSpec() {
      this(11, 50);
   }

   public McElieceKeyGenParameterSpec(int var1) {
      if (var1 < 1) {
         throw new IllegalArgumentException("key size must be positive");
      } else {
         this.m = 0;

         for(this.n = 1; this.n < var1; ++this.m) {
            this.n <<= 1;
         }

         this.t = this.n >>> 1;
         this.t /= this.m;
         this.fieldPoly = PolynomialRingGF2.getIrreduciblePolynomial(this.m);
      }
   }

   public McElieceKeyGenParameterSpec(int var1, int var2) throws InvalidParameterException {
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
         }
      }
   }

   public McElieceKeyGenParameterSpec(int var1, int var2, int var3) {
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
}
