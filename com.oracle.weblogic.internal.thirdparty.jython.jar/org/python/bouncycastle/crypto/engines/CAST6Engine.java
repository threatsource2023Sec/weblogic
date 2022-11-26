package org.python.bouncycastle.crypto.engines;

public final class CAST6Engine extends CAST5Engine {
   protected static final int ROUNDS = 12;
   protected static final int BLOCK_SIZE = 16;
   protected int[] _Kr = new int[48];
   protected int[] _Km = new int[48];
   protected int[] _Tr = new int[192];
   protected int[] _Tm = new int[192];
   private int[] _workingKey = new int[8];

   public String getAlgorithmName() {
      return "CAST6";
   }

   public void reset() {
   }

   public int getBlockSize() {
      return 16;
   }

   protected void setKey(byte[] var1) {
      int var2 = 1518500249;
      int var3 = 1859775393;
      int var4 = 19;
      byte var5 = 17;

      int var7;
      for(int var6 = 0; var6 < 24; ++var6) {
         for(var7 = 0; var7 < 8; ++var7) {
            this._Tm[var6 * 8 + var7] = var2;
            var2 += var3;
            this._Tr[var6 * 8 + var7] = var4;
            var4 = var4 + var5 & 31;
         }
      }

      byte[] var10 = new byte[64];
      var7 = var1.length;
      System.arraycopy(var1, 0, var10, 0, var7);

      int var8;
      for(var8 = 0; var8 < 8; ++var8) {
         this._workingKey[var8] = this.BytesTo32bits(var10, var8 * 4);
      }

      for(var8 = 0; var8 < 12; ++var8) {
         int var9 = var8 * 2 * 8;
         int[] var10000 = this._workingKey;
         var10000[6] ^= this.F1(this._workingKey[7], this._Tm[var9], this._Tr[var9]);
         var10000 = this._workingKey;
         var10000[5] ^= this.F2(this._workingKey[6], this._Tm[var9 + 1], this._Tr[var9 + 1]);
         var10000 = this._workingKey;
         var10000[4] ^= this.F3(this._workingKey[5], this._Tm[var9 + 2], this._Tr[var9 + 2]);
         var10000 = this._workingKey;
         var10000[3] ^= this.F1(this._workingKey[4], this._Tm[var9 + 3], this._Tr[var9 + 3]);
         var10000 = this._workingKey;
         var10000[2] ^= this.F2(this._workingKey[3], this._Tm[var9 + 4], this._Tr[var9 + 4]);
         var10000 = this._workingKey;
         var10000[1] ^= this.F3(this._workingKey[2], this._Tm[var9 + 5], this._Tr[var9 + 5]);
         var10000 = this._workingKey;
         var10000[0] ^= this.F1(this._workingKey[1], this._Tm[var9 + 6], this._Tr[var9 + 6]);
         var10000 = this._workingKey;
         var10000[7] ^= this.F2(this._workingKey[0], this._Tm[var9 + 7], this._Tr[var9 + 7]);
         var9 = (var8 * 2 + 1) * 8;
         var10000 = this._workingKey;
         var10000[6] ^= this.F1(this._workingKey[7], this._Tm[var9], this._Tr[var9]);
         var10000 = this._workingKey;
         var10000[5] ^= this.F2(this._workingKey[6], this._Tm[var9 + 1], this._Tr[var9 + 1]);
         var10000 = this._workingKey;
         var10000[4] ^= this.F3(this._workingKey[5], this._Tm[var9 + 2], this._Tr[var9 + 2]);
         var10000 = this._workingKey;
         var10000[3] ^= this.F1(this._workingKey[4], this._Tm[var9 + 3], this._Tr[var9 + 3]);
         var10000 = this._workingKey;
         var10000[2] ^= this.F2(this._workingKey[3], this._Tm[var9 + 4], this._Tr[var9 + 4]);
         var10000 = this._workingKey;
         var10000[1] ^= this.F3(this._workingKey[2], this._Tm[var9 + 5], this._Tr[var9 + 5]);
         var10000 = this._workingKey;
         var10000[0] ^= this.F1(this._workingKey[1], this._Tm[var9 + 6], this._Tr[var9 + 6]);
         var10000 = this._workingKey;
         var10000[7] ^= this.F2(this._workingKey[0], this._Tm[var9 + 7], this._Tr[var9 + 7]);
         this._Kr[var8 * 4] = this._workingKey[0] & 31;
         this._Kr[var8 * 4 + 1] = this._workingKey[2] & 31;
         this._Kr[var8 * 4 + 2] = this._workingKey[4] & 31;
         this._Kr[var8 * 4 + 3] = this._workingKey[6] & 31;
         this._Km[var8 * 4] = this._workingKey[7];
         this._Km[var8 * 4 + 1] = this._workingKey[5];
         this._Km[var8 * 4 + 2] = this._workingKey[3];
         this._Km[var8 * 4 + 3] = this._workingKey[1];
      }

   }

   protected int encryptBlock(byte[] var1, int var2, byte[] var3, int var4) {
      int[] var5 = new int[4];
      int var6 = this.BytesTo32bits(var1, var2);
      int var7 = this.BytesTo32bits(var1, var2 + 4);
      int var8 = this.BytesTo32bits(var1, var2 + 8);
      int var9 = this.BytesTo32bits(var1, var2 + 12);
      this.CAST_Encipher(var6, var7, var8, var9, var5);
      this.Bits32ToBytes(var5[0], var3, var4);
      this.Bits32ToBytes(var5[1], var3, var4 + 4);
      this.Bits32ToBytes(var5[2], var3, var4 + 8);
      this.Bits32ToBytes(var5[3], var3, var4 + 12);
      return 16;
   }

   protected int decryptBlock(byte[] var1, int var2, byte[] var3, int var4) {
      int[] var5 = new int[4];
      int var6 = this.BytesTo32bits(var1, var2);
      int var7 = this.BytesTo32bits(var1, var2 + 4);
      int var8 = this.BytesTo32bits(var1, var2 + 8);
      int var9 = this.BytesTo32bits(var1, var2 + 12);
      this.CAST_Decipher(var6, var7, var8, var9, var5);
      this.Bits32ToBytes(var5[0], var3, var4);
      this.Bits32ToBytes(var5[1], var3, var4 + 4);
      this.Bits32ToBytes(var5[2], var3, var4 + 8);
      this.Bits32ToBytes(var5[3], var3, var4 + 12);
      return 16;
   }

   protected final void CAST_Encipher(int var1, int var2, int var3, int var4, int[] var5) {
      int var6;
      int var7;
      for(var6 = 0; var6 < 6; ++var6) {
         var7 = var6 * 4;
         var3 ^= this.F1(var4, this._Km[var7], this._Kr[var7]);
         var2 ^= this.F2(var3, this._Km[var7 + 1], this._Kr[var7 + 1]);
         var1 ^= this.F3(var2, this._Km[var7 + 2], this._Kr[var7 + 2]);
         var4 ^= this.F1(var1, this._Km[var7 + 3], this._Kr[var7 + 3]);
      }

      for(var6 = 6; var6 < 12; ++var6) {
         var7 = var6 * 4;
         var4 ^= this.F1(var1, this._Km[var7 + 3], this._Kr[var7 + 3]);
         var1 ^= this.F3(var2, this._Km[var7 + 2], this._Kr[var7 + 2]);
         var2 ^= this.F2(var3, this._Km[var7 + 1], this._Kr[var7 + 1]);
         var3 ^= this.F1(var4, this._Km[var7], this._Kr[var7]);
      }

      var5[0] = var1;
      var5[1] = var2;
      var5[2] = var3;
      var5[3] = var4;
   }

   protected final void CAST_Decipher(int var1, int var2, int var3, int var4, int[] var5) {
      int var6;
      int var7;
      for(var6 = 0; var6 < 6; ++var6) {
         var7 = (11 - var6) * 4;
         var3 ^= this.F1(var4, this._Km[var7], this._Kr[var7]);
         var2 ^= this.F2(var3, this._Km[var7 + 1], this._Kr[var7 + 1]);
         var1 ^= this.F3(var2, this._Km[var7 + 2], this._Kr[var7 + 2]);
         var4 ^= this.F1(var1, this._Km[var7 + 3], this._Kr[var7 + 3]);
      }

      for(var6 = 6; var6 < 12; ++var6) {
         var7 = (11 - var6) * 4;
         var4 ^= this.F1(var1, this._Km[var7 + 3], this._Kr[var7 + 3]);
         var1 ^= this.F3(var2, this._Km[var7 + 2], this._Kr[var7 + 2]);
         var2 ^= this.F2(var3, this._Km[var7 + 1], this._Kr[var7 + 1]);
         var3 ^= this.F1(var4, this._Km[var7], this._Kr[var7]);
      }

      var5[0] = var1;
      var5[1] = var2;
      var5[2] = var3;
      var5[3] = var4;
   }
}
