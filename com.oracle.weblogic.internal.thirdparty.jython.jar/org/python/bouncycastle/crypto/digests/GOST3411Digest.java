package org.python.bouncycastle.crypto.digests;

import org.python.bouncycastle.crypto.BlockCipher;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.ExtendedDigest;
import org.python.bouncycastle.crypto.engines.GOST28147Engine;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithSBox;
import org.python.bouncycastle.util.Arrays;
import org.python.bouncycastle.util.Memoable;
import org.python.bouncycastle.util.Pack;

public class GOST3411Digest implements ExtendedDigest, Memoable {
   private static final int DIGEST_LENGTH = 32;
   private byte[] H = new byte[32];
   private byte[] L = new byte[32];
   private byte[] M = new byte[32];
   private byte[] Sum = new byte[32];
   private byte[][] C = new byte[4][32];
   private byte[] xBuf = new byte[32];
   private int xBufOff;
   private long byteCount;
   private BlockCipher cipher = new GOST28147Engine();
   private byte[] sBox;
   private byte[] K = new byte[32];
   byte[] a = new byte[8];
   short[] wS = new short[16];
   short[] w_S = new short[16];
   byte[] S = new byte[32];
   byte[] U = new byte[32];
   byte[] V = new byte[32];
   byte[] W = new byte[32];
   private static final byte[] C2 = new byte[]{0, -1, 0, -1, 0, -1, 0, -1, -1, 0, -1, 0, -1, 0, -1, 0, 0, -1, -1, 0, -1, 0, 0, -1, -1, 0, 0, 0, -1, -1, 0, -1};

   public GOST3411Digest() {
      this.sBox = GOST28147Engine.getSBox("D-A");
      this.cipher.init(true, new ParametersWithSBox((CipherParameters)null, this.sBox));
      this.reset();
   }

   public GOST3411Digest(byte[] var1) {
      this.sBox = Arrays.clone(var1);
      this.cipher.init(true, new ParametersWithSBox((CipherParameters)null, this.sBox));
      this.reset();
   }

   public GOST3411Digest(GOST3411Digest var1) {
      this.reset(var1);
   }

   public String getAlgorithmName() {
      return "GOST3411";
   }

   public int getDigestSize() {
      return 32;
   }

   public void update(byte var1) {
      this.xBuf[this.xBufOff++] = var1;
      if (this.xBufOff == this.xBuf.length) {
         this.sumByteArray(this.xBuf);
         this.processBlock(this.xBuf, 0);
         this.xBufOff = 0;
      }

      ++this.byteCount;
   }

   public void update(byte[] var1, int var2, int var3) {
      while(this.xBufOff != 0 && var3 > 0) {
         this.update(var1[var2]);
         ++var2;
         --var3;
      }

      while(var3 > this.xBuf.length) {
         System.arraycopy(var1, var2, this.xBuf, 0, this.xBuf.length);
         this.sumByteArray(this.xBuf);
         this.processBlock(this.xBuf, 0);
         var2 += this.xBuf.length;
         var3 -= this.xBuf.length;
         this.byteCount += (long)this.xBuf.length;
      }

      while(var3 > 0) {
         this.update(var1[var2]);
         ++var2;
         --var3;
      }

   }

   private byte[] P(byte[] var1) {
      for(int var2 = 0; var2 < 8; ++var2) {
         this.K[4 * var2] = var1[var2];
         this.K[1 + 4 * var2] = var1[8 + var2];
         this.K[2 + 4 * var2] = var1[16 + var2];
         this.K[3 + 4 * var2] = var1[24 + var2];
      }

      return this.K;
   }

   private byte[] A(byte[] var1) {
      for(int var2 = 0; var2 < 8; ++var2) {
         this.a[var2] = (byte)(var1[var2] ^ var1[var2 + 8]);
      }

      System.arraycopy(var1, 8, var1, 0, 24);
      System.arraycopy(this.a, 0, var1, 24, 8);
      return var1;
   }

   private void E(byte[] var1, byte[] var2, int var3, byte[] var4, int var5) {
      this.cipher.init(true, new KeyParameter(var1));
      this.cipher.processBlock(var4, var5, var2, var3);
   }

   private void fw(byte[] var1) {
      this.cpyBytesToShort(var1, this.wS);
      this.w_S[15] = (short)(this.wS[0] ^ this.wS[1] ^ this.wS[2] ^ this.wS[3] ^ this.wS[12] ^ this.wS[15]);
      System.arraycopy(this.wS, 1, this.w_S, 0, 15);
      this.cpyShortToBytes(this.w_S, var1);
   }

   protected void processBlock(byte[] var1, int var2) {
      System.arraycopy(var1, var2, this.M, 0, 32);
      System.arraycopy(this.H, 0, this.U, 0, 32);
      System.arraycopy(this.M, 0, this.V, 0, 32);

      int var3;
      for(var3 = 0; var3 < 32; ++var3) {
         this.W[var3] = (byte)(this.U[var3] ^ this.V[var3]);
      }

      this.E(this.P(this.W), this.S, 0, this.H, 0);

      for(var3 = 1; var3 < 4; ++var3) {
         byte[] var4 = this.A(this.U);

         int var5;
         for(var5 = 0; var5 < 32; ++var5) {
            this.U[var5] = (byte)(var4[var5] ^ this.C[var3][var5]);
         }

         this.V = this.A(this.A(this.V));

         for(var5 = 0; var5 < 32; ++var5) {
            this.W[var5] = (byte)(this.U[var5] ^ this.V[var5]);
         }

         this.E(this.P(this.W), this.S, var3 * 8, this.H, var3 * 8);
      }

      for(var3 = 0; var3 < 12; ++var3) {
         this.fw(this.S);
      }

      for(var3 = 0; var3 < 32; ++var3) {
         this.S[var3] ^= this.M[var3];
      }

      this.fw(this.S);

      for(var3 = 0; var3 < 32; ++var3) {
         this.S[var3] ^= this.H[var3];
      }

      for(var3 = 0; var3 < 61; ++var3) {
         this.fw(this.S);
      }

      System.arraycopy(this.S, 0, this.H, 0, this.H.length);
   }

   private void finish() {
      Pack.longToLittleEndian(this.byteCount * 8L, this.L, 0);

      while(this.xBufOff != 0) {
         this.update((byte)0);
      }

      this.processBlock(this.L, 0);
      this.processBlock(this.Sum, 0);
   }

   public int doFinal(byte[] var1, int var2) {
      this.finish();
      System.arraycopy(this.H, 0, var1, var2, this.H.length);
      this.reset();
      return 32;
   }

   public void reset() {
      this.byteCount = 0L;
      this.xBufOff = 0;

      int var1;
      for(var1 = 0; var1 < this.H.length; ++var1) {
         this.H[var1] = 0;
      }

      for(var1 = 0; var1 < this.L.length; ++var1) {
         this.L[var1] = 0;
      }

      for(var1 = 0; var1 < this.M.length; ++var1) {
         this.M[var1] = 0;
      }

      for(var1 = 0; var1 < this.C[1].length; ++var1) {
         this.C[1][var1] = 0;
      }

      for(var1 = 0; var1 < this.C[3].length; ++var1) {
         this.C[3][var1] = 0;
      }

      for(var1 = 0; var1 < this.Sum.length; ++var1) {
         this.Sum[var1] = 0;
      }

      for(var1 = 0; var1 < this.xBuf.length; ++var1) {
         this.xBuf[var1] = 0;
      }

      System.arraycopy(C2, 0, this.C[2], 0, C2.length);
   }

   private void sumByteArray(byte[] var1) {
      int var2 = 0;

      for(int var3 = 0; var3 != this.Sum.length; ++var3) {
         int var4 = (this.Sum[var3] & 255) + (var1[var3] & 255) + var2;
         this.Sum[var3] = (byte)var4;
         var2 = var4 >>> 8;
      }

   }

   private void cpyBytesToShort(byte[] var1, short[] var2) {
      for(int var3 = 0; var3 < var1.length / 2; ++var3) {
         var2[var3] = (short)(var1[var3 * 2 + 1] << 8 & '\uff00' | var1[var3 * 2] & 255);
      }

   }

   private void cpyShortToBytes(short[] var1, byte[] var2) {
      for(int var3 = 0; var3 < var2.length / 2; ++var3) {
         var2[var3 * 2 + 1] = (byte)(var1[var3] >> 8);
         var2[var3 * 2] = (byte)var1[var3];
      }

   }

   public int getByteLength() {
      return 32;
   }

   public Memoable copy() {
      return new GOST3411Digest(this);
   }

   public void reset(Memoable var1) {
      GOST3411Digest var2 = (GOST3411Digest)var1;
      this.sBox = var2.sBox;
      this.cipher.init(true, new ParametersWithSBox((CipherParameters)null, this.sBox));
      this.reset();
      System.arraycopy(var2.H, 0, this.H, 0, var2.H.length);
      System.arraycopy(var2.L, 0, this.L, 0, var2.L.length);
      System.arraycopy(var2.M, 0, this.M, 0, var2.M.length);
      System.arraycopy(var2.Sum, 0, this.Sum, 0, var2.Sum.length);
      System.arraycopy(var2.C[1], 0, this.C[1], 0, var2.C[1].length);
      System.arraycopy(var2.C[2], 0, this.C[2], 0, var2.C[2].length);
      System.arraycopy(var2.C[3], 0, this.C[3], 0, var2.C[3].length);
      System.arraycopy(var2.xBuf, 0, this.xBuf, 0, var2.xBuf.length);
      this.xBufOff = var2.xBufOff;
      this.byteCount = var2.byteCount;
   }
}
