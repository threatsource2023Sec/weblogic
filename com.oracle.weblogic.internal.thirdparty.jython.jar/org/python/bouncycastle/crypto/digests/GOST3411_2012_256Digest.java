package org.python.bouncycastle.crypto.digests;

import org.python.bouncycastle.util.Memoable;

public final class GOST3411_2012_256Digest extends GOST3411_2012Digest {
   private static final byte[] IV = new byte[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

   public GOST3411_2012_256Digest() {
      super(IV);
   }

   public GOST3411_2012_256Digest(GOST3411_2012_256Digest var1) {
      super(IV);
      this.reset(var1);
   }

   public String getAlgorithmName() {
      return "GOST3411-2012-256";
   }

   public int getDigestSize() {
      return 32;
   }

   public int doFinal(byte[] var1, int var2) {
      byte[] var3 = new byte[64];
      super.doFinal(var3, 0);
      System.arraycopy(var3, 32, var1, var2, 32);
      return 32;
   }

   public Memoable copy() {
      return new GOST3411_2012_256Digest(this);
   }
}
