package org.python.bouncycastle.crypto.engines;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.python.bouncycastle.crypto.AsymmetricBlockCipher;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.DataLengthException;
import org.python.bouncycastle.crypto.params.ElGamalKeyParameters;
import org.python.bouncycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.ElGamalPublicKeyParameters;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.util.BigIntegers;

public class ElGamalEngine implements AsymmetricBlockCipher {
   private ElGamalKeyParameters key;
   private SecureRandom random;
   private boolean forEncryption;
   private int bitSize;
   private static final BigInteger ZERO = BigInteger.valueOf(0L);
   private static final BigInteger ONE = BigInteger.valueOf(1L);
   private static final BigInteger TWO = BigInteger.valueOf(2L);

   public void init(boolean var1, CipherParameters var2) {
      if (var2 instanceof ParametersWithRandom) {
         ParametersWithRandom var3 = (ParametersWithRandom)var2;
         this.key = (ElGamalKeyParameters)var3.getParameters();
         this.random = var3.getRandom();
      } else {
         this.key = (ElGamalKeyParameters)var2;
         this.random = new SecureRandom();
      }

      this.forEncryption = var1;
      BigInteger var4 = this.key.getParameters().getP();
      this.bitSize = var4.bitLength();
      if (var1) {
         if (!(this.key instanceof ElGamalPublicKeyParameters)) {
            throw new IllegalArgumentException("ElGamalPublicKeyParameters are required for encryption.");
         }
      } else if (!(this.key instanceof ElGamalPrivateKeyParameters)) {
         throw new IllegalArgumentException("ElGamalPrivateKeyParameters are required for decryption.");
      }

   }

   public int getInputBlockSize() {
      return this.forEncryption ? (this.bitSize - 1) / 8 : 2 * ((this.bitSize + 7) / 8);
   }

   public int getOutputBlockSize() {
      return this.forEncryption ? 2 * ((this.bitSize + 7) / 8) : (this.bitSize - 1) / 8;
   }

   public byte[] processBlock(byte[] var1, int var2, int var3) {
      if (this.key == null) {
         throw new IllegalStateException("ElGamal engine not initialised");
      } else {
         int var4 = this.forEncryption ? (this.bitSize - 1 + 7) / 8 : this.getInputBlockSize();
         if (var3 > var4) {
            throw new DataLengthException("input too large for ElGamal cipher.\n");
         } else {
            BigInteger var5 = this.key.getParameters().getP();
            byte[] var6;
            BigInteger var11;
            if (this.key instanceof ElGamalPrivateKeyParameters) {
               var6 = new byte[var3 / 2];
               byte[] var17 = new byte[var3 / 2];
               System.arraycopy(var1, var2, var6, 0, var6.length);
               System.arraycopy(var1, var2 + var6.length, var17, 0, var17.length);
               BigInteger var18 = new BigInteger(1, var6);
               BigInteger var19 = new BigInteger(1, var17);
               ElGamalPrivateKeyParameters var20 = (ElGamalPrivateKeyParameters)this.key;
               var11 = var18.modPow(var5.subtract(ONE).subtract(var20.getX()), var5).multiply(var19).mod(var5);
               return BigIntegers.asUnsignedByteArray(var11);
            } else {
               if (var2 == 0 && var3 == var1.length) {
                  var6 = var1;
               } else {
                  var6 = new byte[var3];
                  System.arraycopy(var1, var2, var6, 0, var3);
               }

               BigInteger var7 = new BigInteger(1, var6);
               if (var7.compareTo(var5) >= 0) {
                  throw new DataLengthException("input too large for ElGamal cipher.\n");
               } else {
                  ElGamalPublicKeyParameters var8 = (ElGamalPublicKeyParameters)this.key;
                  int var9 = var5.bitLength();

                  BigInteger var10;
                  for(var10 = new BigInteger(var9, this.random); var10.equals(ZERO) || var10.compareTo(var5.subtract(TWO)) > 0; var10 = new BigInteger(var9, this.random)) {
                  }

                  var11 = this.key.getParameters().getG();
                  BigInteger var12 = var11.modPow(var10, var5);
                  BigInteger var13 = var7.multiply(var8.getY().modPow(var10, var5)).mod(var5);
                  byte[] var14 = var12.toByteArray();
                  byte[] var15 = var13.toByteArray();
                  byte[] var16 = new byte[this.getOutputBlockSize()];
                  if (var14.length > var16.length / 2) {
                     System.arraycopy(var14, 1, var16, var16.length / 2 - (var14.length - 1), var14.length - 1);
                  } else {
                     System.arraycopy(var14, 0, var16, var16.length / 2 - var14.length, var14.length);
                  }

                  if (var15.length > var16.length / 2) {
                     System.arraycopy(var15, 1, var16, var16.length - (var15.length - 1), var15.length - 1);
                  } else {
                     System.arraycopy(var15, 0, var16, var16.length - var15.length, var15.length);
                  }

                  return var16;
               }
            }
         }
      }
   }
}
