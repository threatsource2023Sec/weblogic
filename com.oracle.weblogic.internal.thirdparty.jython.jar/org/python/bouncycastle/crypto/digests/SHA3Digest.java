package org.python.bouncycastle.crypto.digests;

public class SHA3Digest extends KeccakDigest {
   private static int checkBitLength(int var0) {
      switch (var0) {
         case 224:
         case 256:
         case 384:
         case 512:
            return var0;
         default:
            throw new IllegalArgumentException("'bitLength' " + var0 + " not supported for SHA-3");
      }
   }

   public SHA3Digest() {
      this(256);
   }

   public SHA3Digest(int var1) {
      super(checkBitLength(var1));
   }

   public SHA3Digest(SHA3Digest var1) {
      super(var1);
   }

   public String getAlgorithmName() {
      return "SHA3-" + this.fixedOutputLength;
   }

   public int doFinal(byte[] var1, int var2) {
      this.absorb(new byte[]{2}, 0, 2L);
      return super.doFinal(var1, var2);
   }

   protected int doFinal(byte[] var1, int var2, byte var3, int var4) {
      if (var4 >= 0 && var4 <= 7) {
         int var5 = var3 & (1 << var4) - 1 | 2 << var4;
         int var6 = var4 + 2;
         if (var6 >= 8) {
            this.oneByte[0] = (byte)var5;
            this.absorb(this.oneByte, 0, 8L);
            var6 -= 8;
            var5 >>>= 8;
         }

         return super.doFinal(var1, var2, (byte)var5, var6);
      } else {
         throw new IllegalArgumentException("'partialBits' must be in the range [0,7]");
      }
   }
}
