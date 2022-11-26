package org.python.bouncycastle.cert.crmf.bc;

import java.security.SecureRandom;
import org.python.bouncycastle.cert.crmf.EncryptedValuePadder;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.digests.SHA1Digest;
import org.python.bouncycastle.crypto.generators.MGF1BytesGenerator;
import org.python.bouncycastle.crypto.params.MGFParameters;

public class BcFixedLengthMGF1Padder implements EncryptedValuePadder {
   private int length;
   private SecureRandom random;
   private Digest dig;

   public BcFixedLengthMGF1Padder(int var1) {
      this(var1, (SecureRandom)null);
   }

   public BcFixedLengthMGF1Padder(int var1, SecureRandom var2) {
      this.dig = new SHA1Digest();
      this.length = var1;
      this.random = var2;
   }

   public byte[] getPaddedData(byte[] var1) {
      byte[] var2 = new byte[this.length];
      byte[] var3 = new byte[this.dig.getDigestSize()];
      byte[] var4 = new byte[this.length - this.dig.getDigestSize()];
      if (this.random == null) {
         this.random = new SecureRandom();
      }

      this.random.nextBytes(var3);
      MGF1BytesGenerator var5 = new MGF1BytesGenerator(this.dig);
      var5.init(new MGFParameters(var3));
      var5.generateBytes(var4, 0, var4.length);
      System.arraycopy(var3, 0, var2, 0, var3.length);
      System.arraycopy(var1, 0, var2, var3.length, var1.length);

      int var6;
      for(var6 = var3.length + var1.length + 1; var6 != var2.length; ++var6) {
         var2[var6] = (byte)(1 + this.random.nextInt(255));
      }

      for(var6 = 0; var6 != var4.length; ++var6) {
         var2[var6 + var3.length] ^= var4[var6];
      }

      return var2;
   }

   public byte[] getUnpaddedData(byte[] var1) {
      byte[] var2 = new byte[this.dig.getDigestSize()];
      byte[] var3 = new byte[this.length - this.dig.getDigestSize()];
      System.arraycopy(var1, 0, var2, 0, var2.length);
      MGF1BytesGenerator var4 = new MGF1BytesGenerator(this.dig);
      var4.init(new MGFParameters(var2));
      var4.generateBytes(var3, 0, var3.length);

      int var5;
      for(var5 = 0; var5 != var3.length; ++var5) {
         var1[var5 + var2.length] ^= var3[var5];
      }

      var5 = 0;

      for(int var6 = var1.length - 1; var6 != var2.length; --var6) {
         if (var1[var6] == 0) {
            var5 = var6;
            break;
         }
      }

      if (var5 == 0) {
         throw new IllegalStateException("bad padding in encoding");
      } else {
         byte[] var7 = new byte[var5 - var2.length];
         System.arraycopy(var1, var2.length, var7, 0, var7.length);
         return var7;
      }
   }
}
