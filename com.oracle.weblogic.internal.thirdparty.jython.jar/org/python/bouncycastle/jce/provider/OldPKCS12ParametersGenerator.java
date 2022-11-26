package org.python.bouncycastle.jce.provider;

import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.PBEParametersGenerator;
import org.python.bouncycastle.crypto.digests.MD5Digest;
import org.python.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.python.bouncycastle.crypto.digests.SHA1Digest;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithIV;

class OldPKCS12ParametersGenerator extends PBEParametersGenerator {
   public static final int KEY_MATERIAL = 1;
   public static final int IV_MATERIAL = 2;
   public static final int MAC_MATERIAL = 3;
   private Digest digest;
   private int u;
   private int v;

   public OldPKCS12ParametersGenerator(Digest var1) {
      this.digest = var1;
      if (var1 instanceof MD5Digest) {
         this.u = 16;
         this.v = 64;
      } else if (var1 instanceof SHA1Digest) {
         this.u = 20;
         this.v = 64;
      } else {
         if (!(var1 instanceof RIPEMD160Digest)) {
            throw new IllegalArgumentException("Digest " + var1.getAlgorithmName() + " unsupported");
         }

         this.u = 20;
         this.v = 64;
      }

   }

   private void adjust(byte[] var1, int var2, byte[] var3) {
      int var4 = (var3[var3.length - 1] & 255) + (var1[var2 + var3.length - 1] & 255) + 1;
      var1[var2 + var3.length - 1] = (byte)var4;
      var4 >>>= 8;

      for(int var5 = var3.length - 2; var5 >= 0; --var5) {
         var4 += (var3[var5] & 255) + (var1[var2 + var5] & 255);
         var1[var2 + var5] = (byte)var4;
         var4 >>>= 8;
      }

   }

   private byte[] generateDerivedKey(int var1, int var2) {
      byte[] var3 = new byte[this.v];
      byte[] var4 = new byte[var2];

      for(int var5 = 0; var5 != var3.length; ++var5) {
         var3[var5] = (byte)var1;
      }

      byte[] var13;
      if (this.salt != null && this.salt.length != 0) {
         var13 = new byte[this.v * ((this.salt.length + this.v - 1) / this.v)];

         for(int var6 = 0; var6 != var13.length; ++var6) {
            var13[var6] = this.salt[var6 % this.salt.length];
         }
      } else {
         var13 = new byte[0];
      }

      byte[] var14;
      if (this.password != null && this.password.length != 0) {
         var14 = new byte[this.v * ((this.password.length + this.v - 1) / this.v)];

         for(int var7 = 0; var7 != var14.length; ++var7) {
            var14[var7] = this.password[var7 % this.password.length];
         }
      } else {
         var14 = new byte[0];
      }

      byte[] var15 = new byte[var13.length + var14.length];
      System.arraycopy(var13, 0, var15, 0, var13.length);
      System.arraycopy(var14, 0, var15, var13.length, var14.length);
      byte[] var8 = new byte[this.v];
      int var9 = (var2 + this.u - 1) / this.u;

      for(int var10 = 1; var10 <= var9; ++var10) {
         byte[] var11 = new byte[this.u];
         this.digest.update(var3, 0, var3.length);
         this.digest.update(var15, 0, var15.length);
         this.digest.doFinal(var11, 0);

         int var12;
         for(var12 = 1; var12 != this.iterationCount; ++var12) {
            this.digest.update(var11, 0, var11.length);
            this.digest.doFinal(var11, 0);
         }

         for(var12 = 0; var12 != var8.length; ++var12) {
            var8[var10] = var11[var12 % var11.length];
         }

         for(var12 = 0; var12 != var15.length / this.v; ++var12) {
            this.adjust(var15, var12 * this.v, var8);
         }

         if (var10 == var9) {
            System.arraycopy(var11, 0, var4, (var10 - 1) * this.u, var4.length - (var10 - 1) * this.u);
         } else {
            System.arraycopy(var11, 0, var4, (var10 - 1) * this.u, var11.length);
         }
      }

      return var4;
   }

   public CipherParameters generateDerivedParameters(int var1) {
      var1 /= 8;
      byte[] var2 = this.generateDerivedKey(1, var1);
      return new KeyParameter(var2, 0, var1);
   }

   public CipherParameters generateDerivedParameters(int var1, int var2) {
      var1 /= 8;
      var2 /= 8;
      byte[] var3 = this.generateDerivedKey(1, var1);
      byte[] var4 = this.generateDerivedKey(2, var2);
      return new ParametersWithIV(new KeyParameter(var3, 0, var1), var4, 0, var2);
   }

   public CipherParameters generateDerivedMacParameters(int var1) {
      var1 /= 8;
      byte[] var2 = this.generateDerivedKey(3, var1);
      return new KeyParameter(var2, 0, var1);
   }
}
