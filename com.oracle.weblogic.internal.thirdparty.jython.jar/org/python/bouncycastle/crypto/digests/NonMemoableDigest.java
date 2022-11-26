package org.python.bouncycastle.crypto.digests;

import org.python.bouncycastle.crypto.ExtendedDigest;

public class NonMemoableDigest implements ExtendedDigest {
   private ExtendedDigest baseDigest;

   public NonMemoableDigest(ExtendedDigest var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("baseDigest must not be null");
      } else {
         this.baseDigest = var1;
      }
   }

   public String getAlgorithmName() {
      return this.baseDigest.getAlgorithmName();
   }

   public int getDigestSize() {
      return this.baseDigest.getDigestSize();
   }

   public void update(byte var1) {
      this.baseDigest.update(var1);
   }

   public void update(byte[] var1, int var2, int var3) {
      this.baseDigest.update(var1, var2, var3);
   }

   public int doFinal(byte[] var1, int var2) {
      return this.baseDigest.doFinal(var1, var2);
   }

   public void reset() {
      this.baseDigest.reset();
   }

   public int getByteLength() {
      return this.baseDigest.getByteLength();
   }
}
