package org.python.bouncycastle.pqc.crypto.mceliece;

import org.python.bouncycastle.pqc.math.linearalgebra.GF2Matrix;
import org.python.bouncycastle.pqc.math.linearalgebra.GF2mField;
import org.python.bouncycastle.pqc.math.linearalgebra.GoppaCode;
import org.python.bouncycastle.pqc.math.linearalgebra.Permutation;
import org.python.bouncycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;
import org.python.bouncycastle.pqc.math.linearalgebra.PolynomialRingGF2m;

public class McElieceCCA2PrivateKeyParameters extends McElieceCCA2KeyParameters {
   private int n;
   private int k;
   private GF2mField field;
   private PolynomialGF2mSmallM goppaPoly;
   private Permutation p;
   private GF2Matrix h;
   private PolynomialGF2mSmallM[] qInv;

   public McElieceCCA2PrivateKeyParameters(int var1, int var2, GF2mField var3, PolynomialGF2mSmallM var4, Permutation var5, String var6) {
      this(var1, var2, var3, var4, GoppaCode.createCanonicalCheckMatrix(var3, var4), var5, var6);
   }

   public McElieceCCA2PrivateKeyParameters(int var1, int var2, GF2mField var3, PolynomialGF2mSmallM var4, GF2Matrix var5, Permutation var6, String var7) {
      super(true, var7);
      this.n = var1;
      this.k = var2;
      this.field = var3;
      this.goppaPoly = var4;
      this.h = var5;
      this.p = var6;
      PolynomialRingGF2m var8 = new PolynomialRingGF2m(var3, var4);
      this.qInv = var8.getSquareRootMatrix();
   }

   public int getN() {
      return this.n;
   }

   public int getK() {
      return this.k;
   }

   public int getT() {
      return this.goppaPoly.getDegree();
   }

   public GF2mField getField() {
      return this.field;
   }

   public PolynomialGF2mSmallM getGoppaPoly() {
      return this.goppaPoly;
   }

   public Permutation getP() {
      return this.p;
   }

   public GF2Matrix getH() {
      return this.h;
   }

   public PolynomialGF2mSmallM[] getQInv() {
      return this.qInv;
   }
}
