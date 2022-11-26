package org.python.bouncycastle.crypto.digests;

import org.python.bouncycastle.crypto.Xof;

public class SHAKEDigest extends KeccakDigest implements Xof {
   private static int checkBitLength(int var0) {
      switch (var0) {
         case 128:
         case 256:
            return var0;
         default:
            throw new IllegalArgumentException("'bitLength' " + var0 + " not supported for SHAKE");
      }
   }

   public SHAKEDigest() {
      this(128);
   }

   public SHAKEDigest(int var1) {
      super(checkBitLength(var1));
   }

   public SHAKEDigest(SHAKEDigest var1) {
      super(var1);
   }

   public String getAlgorithmName() {
      return "SHAKE" + this.fixedOutputLength;
   }

   public int doFinal(byte[] var1, int var2) {
      return this.doFinal(var1, var2, this.getDigestSize());
   }

   public int doFinal(byte[] var1, int var2, int var3) {
      int var4 = this.doOutput(var1, var2, var3);
      this.reset();
      return var4;
   }

   public int doOutput(byte[] var1, int var2, int var3) {
      if (!this.squeezing) {
         this.absorb(new byte[]{15}, 0, 4L);
      }

      this.squeeze(var1, var2, (long)var3 * 8L);
      return var3;
   }

   protected int doFinal(byte[] var1, int var2, byte var3, int var4) {
      return this.doFinal(var1, var2, this.getDigestSize(), var3, var4);
   }

   protected int doFinal(byte[] var1, int var2, int var3, byte var4, int var5) {
      if (var5 >= 0 && var5 <= 7) {
         int var6 = var4 & (1 << var5) - 1 | 15 << var5;
         int var7 = var5 + 4;
         if (var7 >= 8) {
            this.oneByte[0] = (byte)var6;
            this.absorb(this.oneByte, 0, 8L);
            var7 -= 8;
            var6 >>>= 8;
         }

         if (var7 > 0) {
            this.oneByte[0] = (byte)var6;
            this.absorb(this.oneByte, 0, (long)var7);
         }

         this.squeeze(var1, var2, (long)var3 * 8L);
         this.reset();
         return var3;
      } else {
         throw new IllegalArgumentException("'partialBits' must be in the range [0,7]");
      }
   }
}
