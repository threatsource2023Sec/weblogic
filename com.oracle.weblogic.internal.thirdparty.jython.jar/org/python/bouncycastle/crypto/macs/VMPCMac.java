package org.python.bouncycastle.crypto.macs;

import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.DataLengthException;
import org.python.bouncycastle.crypto.Mac;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithIV;

public class VMPCMac implements Mac {
   private byte g;
   private byte n = 0;
   private byte[] P = null;
   private byte s = 0;
   private byte[] T;
   private byte[] workingIV;
   private byte[] workingKey;
   private byte x1;
   private byte x2;
   private byte x3;
   private byte x4;

   public int doFinal(byte[] var1, int var2) throws DataLengthException, IllegalStateException {
      int var3;
      int var4;
      for(var3 = 1; var3 < 25; ++var3) {
         this.s = this.P[this.s + this.P[this.n & 255] & 255];
         this.x4 = this.P[this.x4 + this.x3 + var3 & 255];
         this.x3 = this.P[this.x3 + this.x2 + var3 & 255];
         this.x2 = this.P[this.x2 + this.x1 + var3 & 255];
         this.x1 = this.P[this.x1 + this.s + var3 & 255];
         this.T[this.g & 31] ^= this.x1;
         this.T[this.g + 1 & 31] ^= this.x2;
         this.T[this.g + 2 & 31] ^= this.x3;
         this.T[this.g + 3 & 31] ^= this.x4;
         this.g = (byte)(this.g + 4 & 31);
         var4 = this.P[this.n & 255];
         this.P[this.n & 255] = this.P[this.s & 255];
         this.P[this.s & 255] = (byte)var4;
         this.n = (byte)(this.n + 1 & 255);
      }

      for(var3 = 0; var3 < 768; ++var3) {
         this.s = this.P[this.s + this.P[var3 & 255] + this.T[var3 & 31] & 255];
         var4 = this.P[var3 & 255];
         this.P[var3 & 255] = this.P[this.s & 255];
         this.P[this.s & 255] = (byte)var4;
      }

      byte[] var6 = new byte[20];

      for(var4 = 0; var4 < 20; ++var4) {
         this.s = this.P[this.s + this.P[var4 & 255] & 255];
         var6[var4] = this.P[this.P[this.P[this.s & 255] & 255] + 1 & 255];
         byte var5 = this.P[var4 & 255];
         this.P[var4 & 255] = this.P[this.s & 255];
         this.P[this.s & 255] = var5;
      }

      System.arraycopy(var6, 0, var1, var2, var6.length);
      this.reset();
      return var6.length;
   }

   public String getAlgorithmName() {
      return "VMPC-MAC";
   }

   public int getMacSize() {
      return 20;
   }

   public void init(CipherParameters var1) throws IllegalArgumentException {
      if (!(var1 instanceof ParametersWithIV)) {
         throw new IllegalArgumentException("VMPC-MAC Init parameters must include an IV");
      } else {
         ParametersWithIV var2 = (ParametersWithIV)var1;
         KeyParameter var3 = (KeyParameter)var2.getParameters();
         if (!(var2.getParameters() instanceof KeyParameter)) {
            throw new IllegalArgumentException("VMPC-MAC Init parameters must include a key");
         } else {
            this.workingIV = var2.getIV();
            if (this.workingIV != null && this.workingIV.length >= 1 && this.workingIV.length <= 768) {
               this.workingKey = var3.getKey();
               this.reset();
            } else {
               throw new IllegalArgumentException("VMPC-MAC requires 1 to 768 bytes of IV");
            }
         }
      }
   }

   private void initKey(byte[] var1, byte[] var2) {
      this.s = 0;
      this.P = new byte[256];

      int var3;
      for(var3 = 0; var3 < 256; ++var3) {
         this.P[var3] = (byte)var3;
      }

      byte var4;
      for(var3 = 0; var3 < 768; ++var3) {
         this.s = this.P[this.s + this.P[var3 & 255] + var1[var3 % var1.length] & 255];
         var4 = this.P[var3 & 255];
         this.P[var3 & 255] = this.P[this.s & 255];
         this.P[this.s & 255] = var4;
      }

      for(var3 = 0; var3 < 768; ++var3) {
         this.s = this.P[this.s + this.P[var3 & 255] + var2[var3 % var2.length] & 255];
         var4 = this.P[var3 & 255];
         this.P[var3 & 255] = this.P[this.s & 255];
         this.P[this.s & 255] = var4;
      }

      this.n = 0;
   }

   public void reset() {
      this.initKey(this.workingKey, this.workingIV);
      this.g = this.x1 = this.x2 = this.x3 = this.x4 = this.n = 0;
      this.T = new byte[32];

      for(int var1 = 0; var1 < 32; ++var1) {
         this.T[var1] = 0;
      }

   }

   public void update(byte var1) throws IllegalStateException {
      this.s = this.P[this.s + this.P[this.n & 255] & 255];
      byte var2 = (byte)(var1 ^ this.P[this.P[this.P[this.s & 255] & 255] + 1 & 255]);
      this.x4 = this.P[this.x4 + this.x3 & 255];
      this.x3 = this.P[this.x3 + this.x2 & 255];
      this.x2 = this.P[this.x2 + this.x1 & 255];
      this.x1 = this.P[this.x1 + this.s + var2 & 255];
      this.T[this.g & 31] ^= this.x1;
      this.T[this.g + 1 & 31] ^= this.x2;
      this.T[this.g + 2 & 31] ^= this.x3;
      this.T[this.g + 3 & 31] ^= this.x4;
      this.g = (byte)(this.g + 4 & 31);
      byte var3 = this.P[this.n & 255];
      this.P[this.n & 255] = this.P[this.s & 255];
      this.P[this.s & 255] = var3;
      this.n = (byte)(this.n + 1 & 255);
   }

   public void update(byte[] var1, int var2, int var3) throws DataLengthException, IllegalStateException {
      if (var2 + var3 > var1.length) {
         throw new DataLengthException("input buffer too short");
      } else {
         for(int var4 = 0; var4 < var3; ++var4) {
            this.update(var1[var2 + var4]);
         }

      }
   }
}
