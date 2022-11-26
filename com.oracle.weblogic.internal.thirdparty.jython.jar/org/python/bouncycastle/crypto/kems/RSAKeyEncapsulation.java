package org.python.bouncycastle.crypto.kems;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.DerivationFunction;
import org.python.bouncycastle.crypto.KeyEncapsulation;
import org.python.bouncycastle.crypto.params.KDFParameters;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.crypto.params.RSAKeyParameters;
import org.python.bouncycastle.util.BigIntegers;

public class RSAKeyEncapsulation implements KeyEncapsulation {
   private static final BigInteger ZERO = BigInteger.valueOf(0L);
   private static final BigInteger ONE = BigInteger.valueOf(1L);
   private DerivationFunction kdf;
   private SecureRandom rnd;
   private RSAKeyParameters key;

   public RSAKeyEncapsulation(DerivationFunction var1, SecureRandom var2) {
      this.kdf = var1;
      this.rnd = var2;
   }

   public void init(CipherParameters var1) throws IllegalArgumentException {
      if (!(var1 instanceof RSAKeyParameters)) {
         throw new IllegalArgumentException("RSA key required");
      } else {
         this.key = (RSAKeyParameters)var1;
      }
   }

   public CipherParameters encrypt(byte[] var1, int var2, int var3) throws IllegalArgumentException {
      if (this.key.isPrivate()) {
         throw new IllegalArgumentException("Public key required for encryption");
      } else {
         BigInteger var4 = this.key.getModulus();
         BigInteger var5 = this.key.getExponent();
         BigInteger var6 = BigIntegers.createRandomInRange(ZERO, var4.subtract(ONE), this.rnd);
         BigInteger var7 = var6.modPow(var5, var4);
         byte[] var8 = BigIntegers.asUnsignedByteArray((var4.bitLength() + 7) / 8, var7);
         System.arraycopy(var8, 0, var1, var2, var8.length);
         return this.generateKey(var4, var6, var3);
      }
   }

   public CipherParameters encrypt(byte[] var1, int var2) {
      return this.encrypt(var1, 0, var2);
   }

   public CipherParameters decrypt(byte[] var1, int var2, int var3, int var4) throws IllegalArgumentException {
      if (!this.key.isPrivate()) {
         throw new IllegalArgumentException("Private key required for decryption");
      } else {
         BigInteger var5 = this.key.getModulus();
         BigInteger var6 = this.key.getExponent();
         byte[] var7 = new byte[var3];
         System.arraycopy(var1, var2, var7, 0, var7.length);
         BigInteger var8 = new BigInteger(1, var7);
         BigInteger var9 = var8.modPow(var6, var5);
         return this.generateKey(var5, var9, var4);
      }
   }

   public CipherParameters decrypt(byte[] var1, int var2) {
      return this.decrypt(var1, 0, var1.length, var2);
   }

   protected KeyParameter generateKey(BigInteger var1, BigInteger var2, int var3) {
      byte[] var4 = BigIntegers.asUnsignedByteArray((var1.bitLength() + 7) / 8, var2);
      this.kdf.init(new KDFParameters(var4, (byte[])null));
      byte[] var5 = new byte[var3];
      this.kdf.generateBytes(var5, 0, var5.length);
      return new KeyParameter(var5);
   }
}
