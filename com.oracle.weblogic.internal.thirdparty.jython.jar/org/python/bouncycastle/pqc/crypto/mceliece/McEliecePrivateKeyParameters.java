package org.python.bouncycastle.pqc.crypto.mceliece;

import org.python.bouncycastle.pqc.math.linearalgebra.GF2Matrix;
import org.python.bouncycastle.pqc.math.linearalgebra.GF2mField;
import org.python.bouncycastle.pqc.math.linearalgebra.GoppaCode;
import org.python.bouncycastle.pqc.math.linearalgebra.Permutation;
import org.python.bouncycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;
import org.python.bouncycastle.pqc.math.linearalgebra.PolynomialRingGF2m;

public class McEliecePrivateKeyParameters extends McElieceKeyParameters {
   private String oid;
   private int n;
   private int k;
   private GF2mField field;
   private PolynomialGF2mSmallM goppaPoly;
   private GF2Matrix sInv;
   private Permutation p1;
   private Permutation p2;
   private GF2Matrix h;
   private PolynomialGF2mSmallM[] qInv;

   public McEliecePrivateKeyParameters(int var1, int var2, GF2mField var3, PolynomialGF2mSmallM var4, Permutation var5, Permutation var6, GF2Matrix var7) {
      super(true, (McElieceParameters)null);
      this.k = var2;
      this.n = var1;
      this.field = var3;
      this.goppaPoly = var4;
      this.sInv = var7;
      this.p1 = var5;
      this.p2 = var6;
      this.h = GoppaCode.createCanonicalCheckMatrix(var3, var4);
      PolynomialRingGF2m var8 = new PolynomialRingGF2m(var3, var4);
      this.qInv = var8.getSquareRootMatrix();
   }

   public McEliecePrivateKeyParameters(int var1, int var2, byte[] var3, byte[] var4, byte[] var5, byte[] var6, byte[] var7, byte[] var8, byte[][] var9) {
      super(true, (McElieceParameters)null);
      this.n = var1;
      this.k = var2;
      this.field = new GF2mField(var3);
      this.goppaPoly = new PolynomialGF2mSmallM(this.field, var4);
      this.sInv = new GF2Matrix(var5);
      this.p1 = new Permutation(var6);
      this.p2 = new Permutation(var7);
      this.h = new GF2Matrix(var8);
      this.qInv = new PolynomialGF2mSmallM[var9.length];

      for(int var10 = 0; var10 < var9.length; ++var10) {
         this.qInv[var10] = new PolynomialGF2mSmallM(this.field, var9[var10]);
      }

   }

   public int getN() {
      return this.n;
   }

   public int getK() {
      return this.k;
   }

   public GF2mField getField() {
      return this.field;
   }

   public PolynomialGF2mSmallM getGoppaPoly() {
      return this.goppaPoly;
   }

   public GF2Matrix getSInv() {
      return this.sInv;
   }

   public Permutation getP1() {
      return this.p1;
   }

   public Permutation getP2() {
      return this.p2;
   }

   public GF2Matrix getH() {
      return this.h;
   }

   public PolynomialGF2mSmallM[] getQInv() {
      return this.qInv;
   }
}
