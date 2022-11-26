package org.python.bouncycastle.crypto.engines;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.DataLengthException;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.params.CramerShoupKeyParameters;
import org.python.bouncycastle.crypto.params.CramerShoupPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.CramerShoupPublicKeyParameters;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.util.BigIntegers;

public class CramerShoupCoreEngine {
   private static final BigInteger ONE = BigInteger.valueOf(1L);
   private CramerShoupKeyParameters key;
   private SecureRandom random;
   private boolean forEncryption;
   private String label = null;

   public void init(boolean var1, CipherParameters var2, String var3) {
      this.init(var1, var2);
      this.label = var3;
   }

   public void init(boolean var1, CipherParameters var2) {
      SecureRandom var3 = null;
      if (var2 instanceof ParametersWithRandom) {
         ParametersWithRandom var4 = (ParametersWithRandom)var2;
         this.key = (CramerShoupKeyParameters)var4.getParameters();
         var3 = var4.getRandom();
      } else {
         this.key = (CramerShoupKeyParameters)var2;
      }

      this.random = this.initSecureRandom(var1, var3);
      this.forEncryption = var1;
   }

   public int getInputBlockSize() {
      int var1 = this.key.getParameters().getP().bitLength();
      return this.forEncryption ? (var1 + 7) / 8 - 1 : (var1 + 7) / 8;
   }

   public int getOutputBlockSize() {
      int var1 = this.key.getParameters().getP().bitLength();
      return this.forEncryption ? (var1 + 7) / 8 : (var1 + 7) / 8 - 1;
   }

   public BigInteger convertInput(byte[] var1, int var2, int var3) {
      if (var3 > this.getInputBlockSize() + 1) {
         throw new DataLengthException("input too large for Cramer Shoup cipher.");
      } else if (var3 == this.getInputBlockSize() + 1 && this.forEncryption) {
         throw new DataLengthException("input too large for Cramer Shoup cipher.");
      } else {
         byte[] var4;
         if (var2 == 0 && var3 == var1.length) {
            var4 = var1;
         } else {
            var4 = new byte[var3];
            System.arraycopy(var1, var2, var4, 0, var3);
         }

         BigInteger var5 = new BigInteger(1, var4);
         if (var5.compareTo(this.key.getParameters().getP()) >= 0) {
            throw new DataLengthException("input too large for Cramer Shoup cipher.");
         } else {
            return var5;
         }
      }
   }

   public byte[] convertOutput(BigInteger var1) {
      byte[] var2 = var1.toByteArray();
      byte[] var3;
      if (!this.forEncryption) {
         if (var2[0] == 0 && var2.length > this.getOutputBlockSize()) {
            var3 = new byte[var2.length - 1];
            System.arraycopy(var2, 1, var3, 0, var3.length);
            return var3;
         }

         if (var2.length < this.getOutputBlockSize()) {
            var3 = new byte[this.getOutputBlockSize()];
            System.arraycopy(var2, 0, var3, var3.length - var2.length, var2.length);
            return var3;
         }
      } else if (var2[0] == 0) {
         var3 = new byte[var2.length - 1];
         System.arraycopy(var2, 1, var3, 0, var3.length);
         return var3;
      }

      return var2;
   }

   public CramerShoupCiphertext encryptBlock(BigInteger var1) {
      CramerShoupCiphertext var2 = null;
      if (!this.key.isPrivate() && this.forEncryption && this.key instanceof CramerShoupPublicKeyParameters) {
         CramerShoupPublicKeyParameters var3 = (CramerShoupPublicKeyParameters)this.key;
         BigInteger var4 = var3.getParameters().getP();
         BigInteger var5 = var3.getParameters().getG1();
         BigInteger var6 = var3.getParameters().getG2();
         BigInteger var7 = var3.getH();
         if (!this.isValidMessage(var1, var4)) {
            return var2;
         }

         BigInteger var8 = this.generateRandomElement(var4, this.random);
         BigInteger var9 = var5.modPow(var8, var4);
         BigInteger var10 = var6.modPow(var8, var4);
         BigInteger var11 = var7.modPow(var8, var4).multiply(var1).mod(var4);
         Digest var12 = var3.getParameters().getH();
         byte[] var13 = var9.toByteArray();
         var12.update(var13, 0, var13.length);
         byte[] var14 = var10.toByteArray();
         var12.update(var14, 0, var14.length);
         byte[] var15 = var11.toByteArray();
         var12.update(var15, 0, var15.length);
         byte[] var16;
         if (this.label != null) {
            var16 = this.label.getBytes();
            var12.update(var16, 0, var16.length);
         }

         var16 = new byte[var12.getDigestSize()];
         var12.doFinal(var16, 0);
         BigInteger var17 = new BigInteger(1, var16);
         BigInteger var18 = var3.getC().modPow(var8, var4).multiply(var3.getD().modPow(var8.multiply(var17), var4)).mod(var4);
         var2 = new CramerShoupCiphertext(var9, var10, var11, var18);
      }

      return var2;
   }

   public BigInteger decryptBlock(CramerShoupCiphertext var1) throws CramerShoupCiphertextException {
      BigInteger var2 = null;
      if (this.key.isPrivate() && !this.forEncryption && this.key instanceof CramerShoupPrivateKeyParameters) {
         CramerShoupPrivateKeyParameters var3 = (CramerShoupPrivateKeyParameters)this.key;
         BigInteger var4 = var3.getParameters().getP();
         Digest var5 = var3.getParameters().getH();
         byte[] var6 = var1.getU1().toByteArray();
         var5.update(var6, 0, var6.length);
         byte[] var7 = var1.getU2().toByteArray();
         var5.update(var7, 0, var7.length);
         byte[] var8 = var1.getE().toByteArray();
         var5.update(var8, 0, var8.length);
         byte[] var9;
         if (this.label != null) {
            var9 = this.label.getBytes();
            var5.update(var9, 0, var9.length);
         }

         var9 = new byte[var5.getDigestSize()];
         var5.doFinal(var9, 0);
         BigInteger var10 = new BigInteger(1, var9);
         BigInteger var11 = var1.u1.modPow(var3.getX1().add(var3.getY1().multiply(var10)), var4).multiply(var1.u2.modPow(var3.getX2().add(var3.getY2().multiply(var10)), var4)).mod(var4);
         if (!var1.v.equals(var11)) {
            throw new CramerShoupCiphertextException("Sorry, that ciphertext is not correct");
         }

         var2 = var1.e.multiply(var1.u1.modPow(var3.getZ(), var4).modInverse(var4)).mod(var4);
      }

      return var2;
   }

   private BigInteger generateRandomElement(BigInteger var1, SecureRandom var2) {
      return BigIntegers.createRandomInRange(ONE, var1.subtract(ONE), var2);
   }

   private boolean isValidMessage(BigInteger var1, BigInteger var2) {
      return var1.compareTo(var2) < 0;
   }

   protected SecureRandom initSecureRandom(boolean var1, SecureRandom var2) {
      return !var1 ? null : (var2 != null ? var2 : new SecureRandom());
   }

   public static class CramerShoupCiphertextException extends Exception {
      private static final long serialVersionUID = -6360977166495345076L;

      public CramerShoupCiphertextException(String var1) {
         super(var1);
      }
   }
}
