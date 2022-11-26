package org.python.bouncycastle.pqc.crypto.xmss;

import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.Xof;

public final class KeyedHashFunctions {
   private final Digest digest;
   private final int digestSize;

   protected KeyedHashFunctions(Digest var1, int var2) {
      if (var1 == null) {
         throw new NullPointerException("digest == null");
      } else {
         this.digest = var1;
         this.digestSize = var2;
      }
   }

   private byte[] coreDigest(int var1, byte[] var2, byte[] var3) {
      byte[] var4 = new byte[this.digestSize + var2.length + var3.length];
      byte[] var5 = XMSSUtil.toBytesBigEndian((long)var1, this.digestSize);

      int var6;
      for(var6 = 0; var6 < var5.length; ++var6) {
         var4[var6] = var5[var6];
      }

      for(var6 = 0; var6 < var2.length; ++var6) {
         var4[var5.length + var6] = var2[var6];
      }

      for(var6 = 0; var6 < var3.length; ++var6) {
         var4[var5.length + var2.length + var6] = var3[var6];
      }

      this.digest.update(var4, 0, var4.length);
      byte[] var7 = new byte[this.digestSize];
      if (this.digest instanceof Xof) {
         ((Xof)this.digest).doFinal(var7, 0, this.digestSize);
      } else {
         this.digest.doFinal(var7, 0);
      }

      return var7;
   }

   protected byte[] F(byte[] var1, byte[] var2) {
      if (var1.length != this.digestSize) {
         throw new IllegalArgumentException("wrong key length");
      } else if (var2.length != this.digestSize) {
         throw new IllegalArgumentException("wrong in length");
      } else {
         return this.coreDigest(0, var1, var2);
      }
   }

   protected byte[] H(byte[] var1, byte[] var2) {
      if (var1.length != this.digestSize) {
         throw new IllegalArgumentException("wrong key length");
      } else if (var2.length != 2 * this.digestSize) {
         throw new IllegalArgumentException("wrong in length");
      } else {
         return this.coreDigest(1, var1, var2);
      }
   }

   protected byte[] HMsg(byte[] var1, byte[] var2) {
      if (var1.length != 3 * this.digestSize) {
         throw new IllegalArgumentException("wrong key length");
      } else {
         return this.coreDigest(2, var1, var2);
      }
   }

   protected byte[] PRF(byte[] var1, byte[] var2) {
      if (var1.length != this.digestSize) {
         throw new IllegalArgumentException("wrong key length");
      } else if (var2.length != 32) {
         throw new IllegalArgumentException("wrong address length");
      } else {
         return this.coreDigest(3, var1, var2);
      }
   }
}
