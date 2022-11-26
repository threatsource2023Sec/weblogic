package org.python.bouncycastle.crypto.engines;

import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.DataLengthException;
import org.python.bouncycastle.crypto.OutputLengthException;
import org.python.bouncycastle.crypto.StreamCipher;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithIV;

public class VMPCEngine implements StreamCipher {
   protected byte n = 0;
   protected byte[] P = null;
   protected byte s = 0;
   protected byte[] workingIV;
   protected byte[] workingKey;

   public String getAlgorithmName() {
      return "VMPC";
   }

   public void init(boolean var1, CipherParameters var2) {
      if (!(var2 instanceof ParametersWithIV)) {
         throw new IllegalArgumentException("VMPC init parameters must include an IV");
      } else {
         ParametersWithIV var3 = (ParametersWithIV)var2;
         if (!(var3.getParameters() instanceof KeyParameter)) {
            throw new IllegalArgumentException("VMPC init parameters must include a key");
         } else {
            KeyParameter var4 = (KeyParameter)var3.getParameters();
            this.workingIV = var3.getIV();
            if (this.workingIV != null && this.workingIV.length >= 1 && this.workingIV.length <= 768) {
               this.workingKey = var4.getKey();
               this.initKey(this.workingKey, this.workingIV);
            } else {
               throw new IllegalArgumentException("VMPC requires 1 to 768 bytes of IV");
            }
         }
      }
   }

   protected void initKey(byte[] var1, byte[] var2) {
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

   public int processBytes(byte[] var1, int var2, int var3, byte[] var4, int var5) {
      if (var2 + var3 > var1.length) {
         throw new DataLengthException("input buffer too short");
      } else if (var5 + var3 > var4.length) {
         throw new OutputLengthException("output buffer too short");
      } else {
         for(int var6 = 0; var6 < var3; ++var6) {
            this.s = this.P[this.s + this.P[this.n & 255] & 255];
            byte var7 = this.P[this.P[this.P[this.s & 255] & 255] + 1 & 255];
            byte var8 = this.P[this.n & 255];
            this.P[this.n & 255] = this.P[this.s & 255];
            this.P[this.s & 255] = var8;
            this.n = (byte)(this.n + 1 & 255);
            var4[var6 + var5] = (byte)(var1[var6 + var2] ^ var7);
         }

         return var3;
      }
   }

   public void reset() {
      this.initKey(this.workingKey, this.workingIV);
   }

   public byte returnByte(byte var1) {
      this.s = this.P[this.s + this.P[this.n & 255] & 255];
      byte var2 = this.P[this.P[this.P[this.s & 255] & 255] + 1 & 255];
      byte var3 = this.P[this.n & 255];
      this.P[this.n & 255] = this.P[this.s & 255];
      this.P[this.s & 255] = var3;
      this.n = (byte)(this.n + 1 & 255);
      return (byte)(var1 ^ var2);
   }
}
