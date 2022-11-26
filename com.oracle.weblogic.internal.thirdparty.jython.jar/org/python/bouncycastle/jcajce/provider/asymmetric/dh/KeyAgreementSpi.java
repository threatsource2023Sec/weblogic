package org.python.bouncycastle.jcajce.provider.asymmetric.dh;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.python.bouncycastle.crypto.DerivationFunction;
import org.python.bouncycastle.crypto.agreement.kdf.DHKEKGenerator;
import org.python.bouncycastle.crypto.util.DigestFactory;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi;
import org.python.bouncycastle.jcajce.spec.UserKeyingMaterialSpec;

public class KeyAgreementSpi extends BaseAgreementSpi {
   private static final BigInteger ONE = BigInteger.valueOf(1L);
   private static final BigInteger TWO = BigInteger.valueOf(2L);
   private BigInteger x;
   private BigInteger p;
   private BigInteger g;
   private BigInteger result;

   public KeyAgreementSpi() {
      super("Diffie-Hellman", (DerivationFunction)null);
   }

   public KeyAgreementSpi(String var1, DerivationFunction var2) {
      super(var1, var2);
   }

   protected byte[] bigIntToBytes(BigInteger var1) {
      int var2 = (this.p.bitLength() + 7) / 8;
      byte[] var3 = var1.toByteArray();
      if (var3.length == var2) {
         return var3;
      } else {
         byte[] var4;
         if (var3[0] == 0 && var3.length == var2 + 1) {
            var4 = new byte[var3.length - 1];
            System.arraycopy(var3, 1, var4, 0, var4.length);
            return var4;
         } else {
            var4 = new byte[var2];
            System.arraycopy(var3, 0, var4, var4.length - var3.length, var3.length);
            return var4;
         }
      }
   }

   protected Key engineDoPhase(Key var1, boolean var2) throws InvalidKeyException, IllegalStateException {
      if (this.x == null) {
         throw new IllegalStateException("Diffie-Hellman not initialised.");
      } else if (!(var1 instanceof DHPublicKey)) {
         throw new InvalidKeyException("DHKeyAgreement doPhase requires DHPublicKey");
      } else {
         DHPublicKey var3 = (DHPublicKey)var1;
         if (var3.getParams().getG().equals(this.g) && var3.getParams().getP().equals(this.p)) {
            BigInteger var4 = ((DHPublicKey)var1).getY();
            if (var4 != null && var4.compareTo(TWO) >= 0 && var4.compareTo(this.p.subtract(ONE)) < 0) {
               this.result = var4.modPow(this.x, this.p);
               if (this.result.compareTo(ONE) == 0) {
                  throw new InvalidKeyException("Shared key can't be 1");
               } else {
                  return var2 ? null : new BCDHPublicKey(this.result, var3.getParams());
               }
            } else {
               throw new InvalidKeyException("Invalid DH PublicKey");
            }
         } else {
            throw new InvalidKeyException("DHPublicKey not for this KeyAgreement!");
         }
      }
   }

   protected byte[] engineGenerateSecret() throws IllegalStateException {
      if (this.x == null) {
         throw new IllegalStateException("Diffie-Hellman not initialised.");
      } else {
         return super.engineGenerateSecret();
      }
   }

   protected int engineGenerateSecret(byte[] var1, int var2) throws IllegalStateException, ShortBufferException {
      if (this.x == null) {
         throw new IllegalStateException("Diffie-Hellman not initialised.");
      } else {
         return super.engineGenerateSecret(var1, var2);
      }
   }

   protected SecretKey engineGenerateSecret(String var1) throws NoSuchAlgorithmException {
      if (this.x == null) {
         throw new IllegalStateException("Diffie-Hellman not initialised.");
      } else {
         byte[] var2 = this.bigIntToBytes(this.result);
         return (SecretKey)(var1.equals("TlsPremasterSecret") ? new SecretKeySpec(trimZeroes(var2), var1) : super.engineGenerateSecret(var1));
      }
   }

   protected void engineInit(Key var1, AlgorithmParameterSpec var2, SecureRandom var3) throws InvalidKeyException, InvalidAlgorithmParameterException {
      if (!(var1 instanceof DHPrivateKey)) {
         throw new InvalidKeyException("DHKeyAgreement requires DHPrivateKey for initialisation");
      } else {
         DHPrivateKey var4 = (DHPrivateKey)var1;
         if (var2 != null) {
            if (var2 instanceof DHParameterSpec) {
               DHParameterSpec var5 = (DHParameterSpec)var2;
               this.p = var5.getP();
               this.g = var5.getG();
            } else {
               if (!(var2 instanceof UserKeyingMaterialSpec)) {
                  throw new InvalidAlgorithmParameterException("DHKeyAgreement only accepts DHParameterSpec");
               }

               this.p = var4.getParams().getP();
               this.g = var4.getParams().getG();
               this.ukmParameters = ((UserKeyingMaterialSpec)var2).getUserKeyingMaterial();
            }
         } else {
            this.p = var4.getParams().getP();
            this.g = var4.getParams().getG();
         }

         this.x = this.result = var4.getX();
      }
   }

   protected void engineInit(Key var1, SecureRandom var2) throws InvalidKeyException {
      if (!(var1 instanceof DHPrivateKey)) {
         throw new InvalidKeyException("DHKeyAgreement requires DHPrivateKey");
      } else {
         DHPrivateKey var3 = (DHPrivateKey)var1;
         this.p = var3.getParams().getP();
         this.g = var3.getParams().getG();
         this.x = this.result = var3.getX();
      }
   }

   protected byte[] calcSecret() {
      return this.bigIntToBytes(this.result);
   }

   public static class DHwithRFC2631KDF extends KeyAgreementSpi {
      public DHwithRFC2631KDF() {
         super("DHwithRFC2631KDF", new DHKEKGenerator(DigestFactory.createSHA1()));
      }
   }
}
