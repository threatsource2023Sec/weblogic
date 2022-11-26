package org.python.bouncycastle.pqc.crypto.rainbow;

public class RainbowPrivateKeyParameters extends RainbowKeyParameters {
   private short[][] A1inv;
   private short[] b1;
   private short[][] A2inv;
   private short[] b2;
   private int[] vi;
   private Layer[] layers;

   public RainbowPrivateKeyParameters(short[][] var1, short[] var2, short[][] var3, short[] var4, int[] var5, Layer[] var6) {
      super(true, var5[var5.length - 1] - var5[0]);
      this.A1inv = var1;
      this.b1 = var2;
      this.A2inv = var3;
      this.b2 = var4;
      this.vi = var5;
      this.layers = var6;
   }

   public short[] getB1() {
      return this.b1;
   }

   public short[][] getInvA1() {
      return this.A1inv;
   }

   public short[] getB2() {
      return this.b2;
   }

   public short[][] getInvA2() {
      return this.A2inv;
   }

   public Layer[] getLayers() {
      return this.layers;
   }

   public int[] getVi() {
      return this.vi;
   }
}
