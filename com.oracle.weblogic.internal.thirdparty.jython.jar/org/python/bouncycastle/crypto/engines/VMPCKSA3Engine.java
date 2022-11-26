package org.python.bouncycastle.crypto.engines;

public class VMPCKSA3Engine extends VMPCEngine {
   public String getAlgorithmName() {
      return "VMPC-KSA3";
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

      for(var3 = 0; var3 < 768; ++var3) {
         this.s = this.P[this.s + this.P[var3 & 255] + var1[var3 % var1.length] & 255];
         var4 = this.P[var3 & 255];
         this.P[var3 & 255] = this.P[this.s & 255];
         this.P[this.s & 255] = var4;
      }

      this.n = 0;
   }
}
