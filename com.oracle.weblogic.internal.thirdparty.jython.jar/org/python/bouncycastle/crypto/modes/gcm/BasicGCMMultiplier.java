package org.python.bouncycastle.crypto.modes.gcm;

public class BasicGCMMultiplier implements GCMMultiplier {
   private int[] H;

   public void init(byte[] var1) {
      this.H = GCMUtil.asInts(var1);
   }

   public void multiplyH(byte[] var1) {
      int[] var2 = GCMUtil.asInts(var1);
      GCMUtil.multiply(var2, this.H);
      GCMUtil.asBytes(var2, var1);
   }
}
