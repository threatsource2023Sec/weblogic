package org.python.bouncycastle.crypto.digests;

import org.python.bouncycastle.crypto.ExtendedDigest;

public class ShortenedDigest implements ExtendedDigest {
   private ExtendedDigest baseDigest;
   private int length;

   public ShortenedDigest(ExtendedDigest var1, int var2) {
      if (var1 == null) {
         throw new IllegalArgumentException("baseDigest must not be null");
      } else if (var2 > var1.getDigestSize()) {
         throw new IllegalArgumentException("baseDigest output not large enough to support length");
      } else {
         this.baseDigest = var1;
         this.length = var2;
      }
   }

   public String getAlgorithmName() {
      return this.baseDigest.getAlgorithmName() + "(" + this.length * 8 + ")";
   }

   public int getDigestSize() {
      return this.length;
   }

   public void update(byte var1) {
      this.baseDigest.update(var1);
   }

   public void update(byte[] var1, int var2, int var3) {
      this.baseDigest.update(var1, var2, var3);
   }

   public int doFinal(byte[] var1, int var2) {
      byte[] var3 = new byte[this.baseDigest.getDigestSize()];
      this.baseDigest.doFinal(var3, 0);
      System.arraycopy(var3, 0, var1, var2, this.length);
      return this.length;
   }

   public void reset() {
      this.baseDigest.reset();
   }

   public int getByteLength() {
      return this.baseDigest.getByteLength();
   }
}
