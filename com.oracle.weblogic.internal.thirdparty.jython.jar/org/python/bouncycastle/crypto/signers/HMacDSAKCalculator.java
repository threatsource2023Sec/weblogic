package org.python.bouncycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.macs.HMac;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.util.Arrays;
import org.python.bouncycastle.util.BigIntegers;

public class HMacDSAKCalculator implements DSAKCalculator {
   private static final BigInteger ZERO = BigInteger.valueOf(0L);
   private final HMac hMac;
   private final byte[] K;
   private final byte[] V;
   private BigInteger n;

   public HMacDSAKCalculator(Digest var1) {
      this.hMac = new HMac(var1);
      this.V = new byte[this.hMac.getMacSize()];
      this.K = new byte[this.hMac.getMacSize()];
   }

   public boolean isDeterministic() {
      return true;
   }

   public void init(BigInteger var1, SecureRandom var2) {
      throw new IllegalStateException("Operation not supported");
   }

   public void init(BigInteger var1, BigInteger var2, byte[] var3) {
      this.n = var1;
      Arrays.fill((byte[])this.V, (byte)1);
      Arrays.fill((byte[])this.K, (byte)0);
      byte[] var4 = new byte[(var1.bitLength() + 7) / 8];
      byte[] var5 = BigIntegers.asUnsignedByteArray(var2);
      System.arraycopy(var5, 0, var4, var4.length - var5.length, var5.length);
      byte[] var6 = new byte[(var1.bitLength() + 7) / 8];
      BigInteger var7 = this.bitsToInt(var3);
      if (var7.compareTo(var1) >= 0) {
         var7 = var7.subtract(var1);
      }

      byte[] var8 = BigIntegers.asUnsignedByteArray(var7);
      System.arraycopy(var8, 0, var6, var6.length - var8.length, var8.length);
      this.hMac.init(new KeyParameter(this.K));
      this.hMac.update(this.V, 0, this.V.length);
      this.hMac.update((byte)0);
      this.hMac.update(var4, 0, var4.length);
      this.hMac.update(var6, 0, var6.length);
      this.hMac.doFinal(this.K, 0);
      this.hMac.init(new KeyParameter(this.K));
      this.hMac.update(this.V, 0, this.V.length);
      this.hMac.doFinal(this.V, 0);
      this.hMac.update(this.V, 0, this.V.length);
      this.hMac.update((byte)1);
      this.hMac.update(var4, 0, var4.length);
      this.hMac.update(var6, 0, var6.length);
      this.hMac.doFinal(this.K, 0);
      this.hMac.init(new KeyParameter(this.K));
      this.hMac.update(this.V, 0, this.V.length);
      this.hMac.doFinal(this.V, 0);
   }

   public BigInteger nextK() {
      byte[] var1 = new byte[(this.n.bitLength() + 7) / 8];

      while(true) {
         int var3;
         for(int var2 = 0; var2 < var1.length; var2 += var3) {
            this.hMac.update(this.V, 0, this.V.length);
            this.hMac.doFinal(this.V, 0);
            var3 = Math.min(var1.length - var2, this.V.length);
            System.arraycopy(this.V, 0, var1, var2, var3);
         }

         BigInteger var4 = this.bitsToInt(var1);
         if (var4.compareTo(ZERO) > 0 && var4.compareTo(this.n) < 0) {
            return var4;
         }

         this.hMac.update(this.V, 0, this.V.length);
         this.hMac.update((byte)0);
         this.hMac.doFinal(this.K, 0);
         this.hMac.init(new KeyParameter(this.K));
         this.hMac.update(this.V, 0, this.V.length);
         this.hMac.doFinal(this.V, 0);
      }
   }

   private BigInteger bitsToInt(byte[] var1) {
      BigInteger var2 = new BigInteger(1, var1);
      if (var1.length * 8 > this.n.bitLength()) {
         var2 = var2.shiftRight(var1.length * 8 - this.n.bitLength());
      }

      return var2;
   }
}
