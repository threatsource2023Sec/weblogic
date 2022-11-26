package org.python.bouncycastle.crypto.digests;

import org.python.bouncycastle.util.Memoable;
import org.python.bouncycastle.util.Pack;

public class SM3Digest extends GeneralDigest {
   private static final int DIGEST_LENGTH = 32;
   private static final int BLOCK_SIZE = 16;
   private int[] V = new int[8];
   private int[] inwords = new int[16];
   private int xOff;
   private int[] W = new int[68];
   private int[] W1 = new int[64];
   private static final int[] T = new int[64];

   public SM3Digest() {
      this.reset();
   }

   public SM3Digest(SM3Digest var1) {
      super((GeneralDigest)var1);
      this.copyIn(var1);
   }

   private void copyIn(SM3Digest var1) {
      System.arraycopy(var1.V, 0, this.V, 0, this.V.length);
      System.arraycopy(var1.inwords, 0, this.inwords, 0, this.inwords.length);
      this.xOff = var1.xOff;
   }

   public String getAlgorithmName() {
      return "SM3";
   }

   public int getDigestSize() {
      return 32;
   }

   public Memoable copy() {
      return new SM3Digest(this);
   }

   public void reset(Memoable var1) {
      SM3Digest var2 = (SM3Digest)var1;
      super.copyIn(var2);
      this.copyIn(var2);
   }

   public void reset() {
      super.reset();
      this.V[0] = 1937774191;
      this.V[1] = 1226093241;
      this.V[2] = 388252375;
      this.V[3] = -628488704;
      this.V[4] = -1452330820;
      this.V[5] = 372324522;
      this.V[6] = -477237683;
      this.V[7] = -1325724082;
      this.xOff = 0;
   }

   public int doFinal(byte[] var1, int var2) {
      this.finish();
      Pack.intToBigEndian(this.V[0], var1, var2 + 0);
      Pack.intToBigEndian(this.V[1], var1, var2 + 4);
      Pack.intToBigEndian(this.V[2], var1, var2 + 8);
      Pack.intToBigEndian(this.V[3], var1, var2 + 12);
      Pack.intToBigEndian(this.V[4], var1, var2 + 16);
      Pack.intToBigEndian(this.V[5], var1, var2 + 20);
      Pack.intToBigEndian(this.V[6], var1, var2 + 24);
      Pack.intToBigEndian(this.V[7], var1, var2 + 28);
      this.reset();
      return 32;
   }

   protected void processWord(byte[] var1, int var2) {
      int var10000 = (var1[var2] & 255) << 24;
      ++var2;
      var10000 |= (var1[var2] & 255) << 16;
      ++var2;
      var10000 |= (var1[var2] & 255) << 8;
      ++var2;
      int var3 = var10000 | var1[var2] & 255;
      this.inwords[this.xOff] = var3;
      ++this.xOff;
      if (this.xOff >= 16) {
         this.processBlock();
      }

   }

   protected void processLength(long var1) {
      if (this.xOff > 14) {
         this.inwords[this.xOff] = 0;
         ++this.xOff;
         this.processBlock();
      }

      while(this.xOff < 14) {
         this.inwords[this.xOff] = 0;
         ++this.xOff;
      }

      this.inwords[this.xOff++] = (int)(var1 >>> 32);
      this.inwords[this.xOff++] = (int)var1;
   }

   private int P0(int var1) {
      int var2 = var1 << 9 | var1 >>> 23;
      int var3 = var1 << 17 | var1 >>> 15;
      return var1 ^ var2 ^ var3;
   }

   private int P1(int var1) {
      int var2 = var1 << 15 | var1 >>> 17;
      int var3 = var1 << 23 | var1 >>> 9;
      return var1 ^ var2 ^ var3;
   }

   private int FF0(int var1, int var2, int var3) {
      return var1 ^ var2 ^ var3;
   }

   private int FF1(int var1, int var2, int var3) {
      return var1 & var2 | var1 & var3 | var2 & var3;
   }

   private int GG0(int var1, int var2, int var3) {
      return var1 ^ var2 ^ var3;
   }

   private int GG1(int var1, int var2, int var3) {
      return var1 & var2 | ~var1 & var3;
   }

   protected void processBlock() {
      int var1;
      for(var1 = 0; var1 < 16; ++var1) {
         this.W[var1] = this.inwords[var1];
      }

      int var2;
      int var3;
      int var4;
      int var5;
      for(var1 = 16; var1 < 68; ++var1) {
         var2 = this.W[var1 - 3];
         var3 = var2 << 15 | var2 >>> 17;
         var4 = this.W[var1 - 13];
         var5 = var4 << 7 | var4 >>> 25;
         this.W[var1] = this.P1(this.W[var1 - 16] ^ this.W[var1 - 9] ^ var3) ^ var5 ^ this.W[var1 - 6];
      }

      for(var1 = 0; var1 < 64; ++var1) {
         this.W1[var1] = this.W[var1] ^ this.W[var1 + 4];
      }

      var1 = this.V[0];
      var2 = this.V[1];
      var3 = this.V[2];
      var4 = this.V[3];
      var5 = this.V[4];
      int var6 = this.V[5];
      int var7 = this.V[6];
      int var8 = this.V[7];

      int var9;
      int var10;
      int var11;
      int var12;
      int var13;
      int var14;
      int var15;
      for(var9 = 0; var9 < 16; ++var9) {
         var10 = var1 << 12 | var1 >>> 20;
         var11 = var10 + var5 + T[var9];
         var12 = var11 << 7 | var11 >>> 25;
         var13 = var12 ^ var10;
         var14 = this.FF0(var1, var2, var3) + var4 + var13 + this.W1[var9];
         var15 = this.GG0(var5, var6, var7) + var8 + var12 + this.W[var9];
         var4 = var3;
         var3 = var2 << 9 | var2 >>> 23;
         var2 = var1;
         var1 = var14;
         var8 = var7;
         var7 = var6 << 19 | var6 >>> 13;
         var6 = var5;
         var5 = this.P0(var15);
      }

      for(var9 = 16; var9 < 64; ++var9) {
         var10 = var1 << 12 | var1 >>> 20;
         var11 = var10 + var5 + T[var9];
         var12 = var11 << 7 | var11 >>> 25;
         var13 = var12 ^ var10;
         var14 = this.FF1(var1, var2, var3) + var4 + var13 + this.W1[var9];
         var15 = this.GG1(var5, var6, var7) + var8 + var12 + this.W[var9];
         var4 = var3;
         var3 = var2 << 9 | var2 >>> 23;
         var2 = var1;
         var1 = var14;
         var8 = var7;
         var7 = var6 << 19 | var6 >>> 13;
         var6 = var5;
         var5 = this.P0(var15);
      }

      int[] var10000 = this.V;
      var10000[0] ^= var1;
      var10000 = this.V;
      var10000[1] ^= var2;
      var10000 = this.V;
      var10000[2] ^= var3;
      var10000 = this.V;
      var10000[3] ^= var4;
      var10000 = this.V;
      var10000[4] ^= var5;
      var10000 = this.V;
      var10000[5] ^= var6;
      var10000 = this.V;
      var10000[6] ^= var7;
      var10000 = this.V;
      var10000[7] ^= var8;
      this.xOff = 0;
   }

   static {
      int var0;
      int var1;
      for(var0 = 0; var0 < 16; ++var0) {
         var1 = 2043430169;
         T[var0] = var1 << var0 | var1 >>> 32 - var0;
      }

      for(var0 = 16; var0 < 64; ++var0) {
         var1 = var0 % 32;
         int var2 = 2055708042;
         T[var0] = var2 << var1 | var2 >>> 32 - var1;
      }

   }
}
