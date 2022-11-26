package org.python.bouncycastle.crypto.digests;

import org.python.bouncycastle.util.Memoable;

public class RIPEMD256Digest extends GeneralDigest {
   private static final int DIGEST_LENGTH = 32;
   private int H0;
   private int H1;
   private int H2;
   private int H3;
   private int H4;
   private int H5;
   private int H6;
   private int H7;
   private int[] X = new int[16];
   private int xOff;

   public RIPEMD256Digest() {
      this.reset();
   }

   public RIPEMD256Digest(RIPEMD256Digest var1) {
      super((GeneralDigest)var1);
      this.copyIn(var1);
   }

   private void copyIn(RIPEMD256Digest var1) {
      super.copyIn(var1);
      this.H0 = var1.H0;
      this.H1 = var1.H1;
      this.H2 = var1.H2;
      this.H3 = var1.H3;
      this.H4 = var1.H4;
      this.H5 = var1.H5;
      this.H6 = var1.H6;
      this.H7 = var1.H7;
      System.arraycopy(var1.X, 0, this.X, 0, var1.X.length);
      this.xOff = var1.xOff;
   }

   public String getAlgorithmName() {
      return "RIPEMD256";
   }

   public int getDigestSize() {
      return 32;
   }

   protected void processWord(byte[] var1, int var2) {
      this.X[this.xOff++] = var1[var2] & 255 | (var1[var2 + 1] & 255) << 8 | (var1[var2 + 2] & 255) << 16 | (var1[var2 + 3] & 255) << 24;
      if (this.xOff == 16) {
         this.processBlock();
      }

   }

   protected void processLength(long var1) {
      if (this.xOff > 14) {
         this.processBlock();
      }

      this.X[14] = (int)(var1 & -1L);
      this.X[15] = (int)(var1 >>> 32);
   }

   private void unpackWord(int var1, byte[] var2, int var3) {
      var2[var3] = (byte)var1;
      var2[var3 + 1] = (byte)(var1 >>> 8);
      var2[var3 + 2] = (byte)(var1 >>> 16);
      var2[var3 + 3] = (byte)(var1 >>> 24);
   }

   public int doFinal(byte[] var1, int var2) {
      this.finish();
      this.unpackWord(this.H0, var1, var2);
      this.unpackWord(this.H1, var1, var2 + 4);
      this.unpackWord(this.H2, var1, var2 + 8);
      this.unpackWord(this.H3, var1, var2 + 12);
      this.unpackWord(this.H4, var1, var2 + 16);
      this.unpackWord(this.H5, var1, var2 + 20);
      this.unpackWord(this.H6, var1, var2 + 24);
      this.unpackWord(this.H7, var1, var2 + 28);
      this.reset();
      return 32;
   }

   public void reset() {
      super.reset();
      this.H0 = 1732584193;
      this.H1 = -271733879;
      this.H2 = -1732584194;
      this.H3 = 271733878;
      this.H4 = 1985229328;
      this.H5 = -19088744;
      this.H6 = -1985229329;
      this.H7 = 19088743;
      this.xOff = 0;

      for(int var1 = 0; var1 != this.X.length; ++var1) {
         this.X[var1] = 0;
      }

   }

   private int RL(int var1, int var2) {
      return var1 << var2 | var1 >>> 32 - var2;
   }

   private int f1(int var1, int var2, int var3) {
      return var1 ^ var2 ^ var3;
   }

   private int f2(int var1, int var2, int var3) {
      return var1 & var2 | ~var1 & var3;
   }

   private int f3(int var1, int var2, int var3) {
      return (var1 | ~var2) ^ var3;
   }

   private int f4(int var1, int var2, int var3) {
      return var1 & var3 | var2 & ~var3;
   }

   private int F1(int var1, int var2, int var3, int var4, int var5, int var6) {
      return this.RL(var1 + this.f1(var2, var3, var4) + var5, var6);
   }

   private int F2(int var1, int var2, int var3, int var4, int var5, int var6) {
      return this.RL(var1 + this.f2(var2, var3, var4) + var5 + 1518500249, var6);
   }

   private int F3(int var1, int var2, int var3, int var4, int var5, int var6) {
      return this.RL(var1 + this.f3(var2, var3, var4) + var5 + 1859775393, var6);
   }

   private int F4(int var1, int var2, int var3, int var4, int var5, int var6) {
      return this.RL(var1 + this.f4(var2, var3, var4) + var5 + -1894007588, var6);
   }

   private int FF1(int var1, int var2, int var3, int var4, int var5, int var6) {
      return this.RL(var1 + this.f1(var2, var3, var4) + var5, var6);
   }

   private int FF2(int var1, int var2, int var3, int var4, int var5, int var6) {
      return this.RL(var1 + this.f2(var2, var3, var4) + var5 + 1836072691, var6);
   }

   private int FF3(int var1, int var2, int var3, int var4, int var5, int var6) {
      return this.RL(var1 + this.f3(var2, var3, var4) + var5 + 1548603684, var6);
   }

   private int FF4(int var1, int var2, int var3, int var4, int var5, int var6) {
      return this.RL(var1 + this.f4(var2, var3, var4) + var5 + 1352829926, var6);
   }

   protected void processBlock() {
      int var1 = this.H0;
      int var2 = this.H1;
      int var3 = this.H2;
      int var4 = this.H3;
      int var5 = this.H4;
      int var6 = this.H5;
      int var7 = this.H6;
      int var8 = this.H7;
      var1 = this.F1(var1, var2, var3, var4, this.X[0], 11);
      var4 = this.F1(var4, var1, var2, var3, this.X[1], 14);
      var3 = this.F1(var3, var4, var1, var2, this.X[2], 15);
      var2 = this.F1(var2, var3, var4, var1, this.X[3], 12);
      var1 = this.F1(var1, var2, var3, var4, this.X[4], 5);
      var4 = this.F1(var4, var1, var2, var3, this.X[5], 8);
      var3 = this.F1(var3, var4, var1, var2, this.X[6], 7);
      var2 = this.F1(var2, var3, var4, var1, this.X[7], 9);
      var1 = this.F1(var1, var2, var3, var4, this.X[8], 11);
      var4 = this.F1(var4, var1, var2, var3, this.X[9], 13);
      var3 = this.F1(var3, var4, var1, var2, this.X[10], 14);
      var2 = this.F1(var2, var3, var4, var1, this.X[11], 15);
      var1 = this.F1(var1, var2, var3, var4, this.X[12], 6);
      var4 = this.F1(var4, var1, var2, var3, this.X[13], 7);
      var3 = this.F1(var3, var4, var1, var2, this.X[14], 9);
      var2 = this.F1(var2, var3, var4, var1, this.X[15], 8);
      var5 = this.FF4(var5, var6, var7, var8, this.X[5], 8);
      var8 = this.FF4(var8, var5, var6, var7, this.X[14], 9);
      var7 = this.FF4(var7, var8, var5, var6, this.X[7], 9);
      var6 = this.FF4(var6, var7, var8, var5, this.X[0], 11);
      var5 = this.FF4(var5, var6, var7, var8, this.X[9], 13);
      var8 = this.FF4(var8, var5, var6, var7, this.X[2], 15);
      var7 = this.FF4(var7, var8, var5, var6, this.X[11], 15);
      var6 = this.FF4(var6, var7, var8, var5, this.X[4], 5);
      var5 = this.FF4(var5, var6, var7, var8, this.X[13], 7);
      var8 = this.FF4(var8, var5, var6, var7, this.X[6], 7);
      var7 = this.FF4(var7, var8, var5, var6, this.X[15], 8);
      var6 = this.FF4(var6, var7, var8, var5, this.X[8], 11);
      var5 = this.FF4(var5, var6, var7, var8, this.X[1], 14);
      var8 = this.FF4(var8, var5, var6, var7, this.X[10], 14);
      var7 = this.FF4(var7, var8, var5, var6, this.X[3], 12);
      var6 = this.FF4(var6, var7, var8, var5, this.X[12], 6);
      int var9 = var1;
      var1 = this.F2(var5, var2, var3, var4, this.X[7], 7);
      var4 = this.F2(var4, var1, var2, var3, this.X[4], 6);
      var3 = this.F2(var3, var4, var1, var2, this.X[13], 8);
      var2 = this.F2(var2, var3, var4, var1, this.X[1], 13);
      var1 = this.F2(var1, var2, var3, var4, this.X[10], 11);
      var4 = this.F2(var4, var1, var2, var3, this.X[6], 9);
      var3 = this.F2(var3, var4, var1, var2, this.X[15], 7);
      var2 = this.F2(var2, var3, var4, var1, this.X[3], 15);
      var1 = this.F2(var1, var2, var3, var4, this.X[12], 7);
      var4 = this.F2(var4, var1, var2, var3, this.X[0], 12);
      var3 = this.F2(var3, var4, var1, var2, this.X[9], 15);
      var2 = this.F2(var2, var3, var4, var1, this.X[5], 9);
      var1 = this.F2(var1, var2, var3, var4, this.X[2], 11);
      var4 = this.F2(var4, var1, var2, var3, this.X[14], 7);
      var3 = this.F2(var3, var4, var1, var2, this.X[11], 13);
      var2 = this.F2(var2, var3, var4, var1, this.X[8], 12);
      var5 = this.FF3(var9, var6, var7, var8, this.X[6], 9);
      var8 = this.FF3(var8, var5, var6, var7, this.X[11], 13);
      var7 = this.FF3(var7, var8, var5, var6, this.X[3], 15);
      var6 = this.FF3(var6, var7, var8, var5, this.X[7], 7);
      var5 = this.FF3(var5, var6, var7, var8, this.X[0], 12);
      var8 = this.FF3(var8, var5, var6, var7, this.X[13], 8);
      var7 = this.FF3(var7, var8, var5, var6, this.X[5], 9);
      var6 = this.FF3(var6, var7, var8, var5, this.X[10], 11);
      var5 = this.FF3(var5, var6, var7, var8, this.X[14], 7);
      var8 = this.FF3(var8, var5, var6, var7, this.X[15], 7);
      var7 = this.FF3(var7, var8, var5, var6, this.X[8], 12);
      var6 = this.FF3(var6, var7, var8, var5, this.X[12], 7);
      var5 = this.FF3(var5, var6, var7, var8, this.X[4], 6);
      var8 = this.FF3(var8, var5, var6, var7, this.X[9], 15);
      var7 = this.FF3(var7, var8, var5, var6, this.X[1], 13);
      var6 = this.FF3(var6, var7, var8, var5, this.X[2], 11);
      var9 = var2;
      var1 = this.F3(var1, var6, var3, var4, this.X[3], 11);
      var4 = this.F3(var4, var1, var6, var3, this.X[10], 13);
      var3 = this.F3(var3, var4, var1, var6, this.X[14], 6);
      var2 = this.F3(var6, var3, var4, var1, this.X[4], 7);
      var1 = this.F3(var1, var2, var3, var4, this.X[9], 14);
      var4 = this.F3(var4, var1, var2, var3, this.X[15], 9);
      var3 = this.F3(var3, var4, var1, var2, this.X[8], 13);
      var2 = this.F3(var2, var3, var4, var1, this.X[1], 15);
      var1 = this.F3(var1, var2, var3, var4, this.X[2], 14);
      var4 = this.F3(var4, var1, var2, var3, this.X[7], 8);
      var3 = this.F3(var3, var4, var1, var2, this.X[0], 13);
      var2 = this.F3(var2, var3, var4, var1, this.X[6], 6);
      var1 = this.F3(var1, var2, var3, var4, this.X[13], 5);
      var4 = this.F3(var4, var1, var2, var3, this.X[11], 12);
      var3 = this.F3(var3, var4, var1, var2, this.X[5], 7);
      var2 = this.F3(var2, var3, var4, var1, this.X[12], 5);
      var5 = this.FF2(var5, var9, var7, var8, this.X[15], 9);
      var8 = this.FF2(var8, var5, var9, var7, this.X[5], 7);
      var7 = this.FF2(var7, var8, var5, var9, this.X[1], 15);
      var6 = this.FF2(var9, var7, var8, var5, this.X[3], 11);
      var5 = this.FF2(var5, var6, var7, var8, this.X[7], 8);
      var8 = this.FF2(var8, var5, var6, var7, this.X[14], 6);
      var7 = this.FF2(var7, var8, var5, var6, this.X[6], 6);
      var6 = this.FF2(var6, var7, var8, var5, this.X[9], 14);
      var5 = this.FF2(var5, var6, var7, var8, this.X[11], 12);
      var8 = this.FF2(var8, var5, var6, var7, this.X[8], 13);
      var7 = this.FF2(var7, var8, var5, var6, this.X[12], 5);
      var6 = this.FF2(var6, var7, var8, var5, this.X[2], 14);
      var5 = this.FF2(var5, var6, var7, var8, this.X[10], 13);
      var8 = this.FF2(var8, var5, var6, var7, this.X[0], 13);
      var7 = this.FF2(var7, var8, var5, var6, this.X[4], 7);
      var6 = this.FF2(var6, var7, var8, var5, this.X[13], 5);
      var9 = var3;
      var1 = this.F4(var1, var2, var7, var4, this.X[1], 11);
      var4 = this.F4(var4, var1, var2, var7, this.X[9], 12);
      var3 = this.F4(var7, var4, var1, var2, this.X[11], 14);
      var2 = this.F4(var2, var3, var4, var1, this.X[10], 15);
      var1 = this.F4(var1, var2, var3, var4, this.X[0], 14);
      var4 = this.F4(var4, var1, var2, var3, this.X[8], 15);
      var3 = this.F4(var3, var4, var1, var2, this.X[12], 9);
      var2 = this.F4(var2, var3, var4, var1, this.X[4], 8);
      var1 = this.F4(var1, var2, var3, var4, this.X[13], 9);
      var4 = this.F4(var4, var1, var2, var3, this.X[3], 14);
      var3 = this.F4(var3, var4, var1, var2, this.X[7], 5);
      var2 = this.F4(var2, var3, var4, var1, this.X[15], 6);
      var1 = this.F4(var1, var2, var3, var4, this.X[14], 8);
      var4 = this.F4(var4, var1, var2, var3, this.X[5], 6);
      var3 = this.F4(var3, var4, var1, var2, this.X[6], 5);
      var2 = this.F4(var2, var3, var4, var1, this.X[2], 12);
      var5 = this.FF1(var5, var6, var9, var8, this.X[8], 15);
      var8 = this.FF1(var8, var5, var6, var9, this.X[6], 5);
      var7 = this.FF1(var9, var8, var5, var6, this.X[4], 8);
      var6 = this.FF1(var6, var7, var8, var5, this.X[1], 11);
      var5 = this.FF1(var5, var6, var7, var8, this.X[3], 14);
      var8 = this.FF1(var8, var5, var6, var7, this.X[11], 14);
      var7 = this.FF1(var7, var8, var5, var6, this.X[15], 6);
      var6 = this.FF1(var6, var7, var8, var5, this.X[0], 14);
      var5 = this.FF1(var5, var6, var7, var8, this.X[5], 6);
      var8 = this.FF1(var8, var5, var6, var7, this.X[12], 9);
      var7 = this.FF1(var7, var8, var5, var6, this.X[2], 12);
      var6 = this.FF1(var6, var7, var8, var5, this.X[13], 9);
      var5 = this.FF1(var5, var6, var7, var8, this.X[9], 12);
      var8 = this.FF1(var8, var5, var6, var7, this.X[7], 5);
      var7 = this.FF1(var7, var8, var5, var6, this.X[10], 15);
      var6 = this.FF1(var6, var7, var8, var5, this.X[14], 8);
      this.H0 += var1;
      this.H1 += var2;
      this.H2 += var3;
      this.H3 += var8;
      this.H4 += var5;
      this.H5 += var6;
      this.H6 += var7;
      this.H7 += var4;
      this.xOff = 0;

      for(int var10 = 0; var10 != this.X.length; ++var10) {
         this.X[var10] = 0;
      }

   }

   public Memoable copy() {
      return new RIPEMD256Digest(this);
   }

   public void reset(Memoable var1) {
      RIPEMD256Digest var2 = (RIPEMD256Digest)var1;
      this.copyIn(var2);
   }
}
