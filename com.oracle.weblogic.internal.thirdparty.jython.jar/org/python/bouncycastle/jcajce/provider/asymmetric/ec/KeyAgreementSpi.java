package org.python.bouncycastle.jcajce.provider.asymmetric.ec;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.python.bouncycastle.asn1.x9.X9IntegerConverter;
import org.python.bouncycastle.crypto.BasicAgreement;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.DerivationFunction;
import org.python.bouncycastle.crypto.agreement.ECDHBasicAgreement;
import org.python.bouncycastle.crypto.agreement.ECDHCBasicAgreement;
import org.python.bouncycastle.crypto.agreement.ECMQVBasicAgreement;
import org.python.bouncycastle.crypto.agreement.kdf.ConcatenationKDFGenerator;
import org.python.bouncycastle.crypto.generators.KDF2BytesGenerator;
import org.python.bouncycastle.crypto.params.ECDomainParameters;
import org.python.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.python.bouncycastle.crypto.params.MQVPrivateParameters;
import org.python.bouncycastle.crypto.params.MQVPublicParameters;
import org.python.bouncycastle.crypto.util.DigestFactory;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.python.bouncycastle.jcajce.spec.MQVParameterSpec;
import org.python.bouncycastle.jcajce.spec.UserKeyingMaterialSpec;
import org.python.bouncycastle.jce.interfaces.ECPrivateKey;
import org.python.bouncycastle.jce.interfaces.ECPublicKey;
import org.python.bouncycastle.jce.interfaces.MQVPrivateKey;
import org.python.bouncycastle.jce.interfaces.MQVPublicKey;

public class KeyAgreementSpi extends BaseAgreementSpi {
   private static final X9IntegerConverter converter = new X9IntegerConverter();
   private String kaAlgorithm;
   private ECDomainParameters parameters;
   private BasicAgreement agreement;
   private MQVParameterSpec mqvParameters;
   private BigInteger result;

   protected KeyAgreementSpi(String var1, BasicAgreement var2, DerivationFunction var3) {
      super(var1, var3);
      this.kaAlgorithm = var1;
      this.agreement = var2;
   }

   protected byte[] bigIntToBytes(BigInteger var1) {
      return converter.integerToBytes(var1, converter.getByteLength(this.parameters.getCurve()));
   }

   protected Key engineDoPhase(Key var1, boolean var2) throws InvalidKeyException, IllegalStateException {
      if (this.parameters == null) {
         throw new IllegalStateException(this.kaAlgorithm + " not initialised.");
      } else if (!var2) {
         throw new IllegalStateException(this.kaAlgorithm + " can only be between two parties.");
      } else {
         Object var5;
         if (this.agreement instanceof ECMQVBasicAgreement) {
            ECPublicKeyParameters var4;
            if (!(var1 instanceof MQVPublicKey)) {
               ECPublicKeyParameters var3 = (ECPublicKeyParameters)ECUtils.generatePublicKeyParameter((PublicKey)var1);
               var4 = (ECPublicKeyParameters)ECUtils.generatePublicKeyParameter(this.mqvParameters.getOtherPartyEphemeralKey());
               var5 = new MQVPublicParameters(var3, var4);
            } else {
               MQVPublicKey var8 = (MQVPublicKey)var1;
               var4 = (ECPublicKeyParameters)ECUtils.generatePublicKeyParameter(var8.getStaticKey());
               ECPublicKeyParameters var6 = (ECPublicKeyParameters)ECUtils.generatePublicKeyParameter(var8.getEphemeralKey());
               var5 = new MQVPublicParameters(var4, var6);
            }
         } else {
            if (!(var1 instanceof PublicKey)) {
               throw new InvalidKeyException(this.kaAlgorithm + " key agreement requires " + getSimpleName(ECPublicKey.class) + " for doPhase");
            }

            var5 = ECUtils.generatePublicKeyParameter((PublicKey)var1);
         }

         try {
            this.result = this.agreement.calculateAgreement((CipherParameters)var5);
            return null;
         } catch (final Exception var7) {
            throw new InvalidKeyException("calculation failed: " + var7.getMessage()) {
               public Throwable getCause() {
                  return var7;
               }
            };
         }
      }
   }

   protected void engineInit(Key var1, AlgorithmParameterSpec var2, SecureRandom var3) throws InvalidKeyException, InvalidAlgorithmParameterException {
      if (var2 != null && !(var2 instanceof MQVParameterSpec) && !(var2 instanceof UserKeyingMaterialSpec)) {
         throw new InvalidAlgorithmParameterException("No algorithm parameters supported");
      } else {
         this.initFromKey(var1, var2);
      }
   }

   protected void engineInit(Key var1, SecureRandom var2) throws InvalidKeyException {
      this.initFromKey(var1, (AlgorithmParameterSpec)null);
   }

   private void initFromKey(Key var1, AlgorithmParameterSpec var2) throws InvalidKeyException {
      ECPrivateKeyParameters var4;
      if (this.agreement instanceof ECMQVBasicAgreement) {
         this.mqvParameters = null;
         if (!(var1 instanceof MQVPrivateKey) && !(var2 instanceof MQVParameterSpec)) {
            throw new InvalidKeyException(this.kaAlgorithm + " key agreement requires " + getSimpleName(MQVParameterSpec.class) + " for initialisation");
         }

         ECPrivateKeyParameters var5;
         ECPublicKeyParameters var6;
         if (var1 instanceof MQVPrivateKey) {
            MQVPrivateKey var3 = (MQVPrivateKey)var1;
            var4 = (ECPrivateKeyParameters)ECUtil.generatePrivateKeyParameter(var3.getStaticPrivateKey());
            var5 = (ECPrivateKeyParameters)ECUtil.generatePrivateKeyParameter(var3.getEphemeralPrivateKey());
            var6 = null;
            if (var3.getEphemeralPublicKey() != null) {
               var6 = (ECPublicKeyParameters)ECUtils.generatePublicKeyParameter(var3.getEphemeralPublicKey());
            }
         } else {
            MQVParameterSpec var7 = (MQVParameterSpec)var2;
            var4 = (ECPrivateKeyParameters)ECUtil.generatePrivateKeyParameter((PrivateKey)var1);
            var5 = (ECPrivateKeyParameters)ECUtil.generatePrivateKeyParameter(var7.getEphemeralPrivateKey());
            var6 = null;
            if (var7.getEphemeralPublicKey() != null) {
               var6 = (ECPublicKeyParameters)ECUtils.generatePublicKeyParameter(var7.getEphemeralPublicKey());
            }

            this.mqvParameters = var7;
            this.ukmParameters = var7.getUserKeyingMaterial();
         }

         MQVPrivateParameters var8 = new MQVPrivateParameters(var4, var5, var6);
         this.parameters = var4.getParameters();
         this.agreement.init(var8);
      } else {
         if (!(var1 instanceof PrivateKey)) {
            throw new InvalidKeyException(this.kaAlgorithm + " key agreement requires " + getSimpleName(ECPrivateKey.class) + " for initialisation");
         }

         var4 = (ECPrivateKeyParameters)ECUtil.generatePrivateKeyParameter((PrivateKey)var1);
         this.parameters = var4.getParameters();
         this.ukmParameters = var2 instanceof UserKeyingMaterialSpec ? ((UserKeyingMaterialSpec)var2).getUserKeyingMaterial() : null;
         this.agreement.init(var4);
      }

   }

   private static String getSimpleName(Class var0) {
      String var1 = var0.getName();
      return var1.substring(var1.lastIndexOf(46) + 1);
   }

   protected byte[] calcSecret() {
      return this.bigIntToBytes(this.result);
   }

   public static class CDHwithSHA1KDFAndSharedInfo extends KeyAgreementSpi {
      public CDHwithSHA1KDFAndSharedInfo() {
         super("ECCDHwithSHA1KDF", new ECDHCBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA1()));
      }
   }

   public static class CDHwithSHA224KDFAndSharedInfo extends KeyAgreementSpi {
      public CDHwithSHA224KDFAndSharedInfo() {
         super("ECCDHwithSHA224KDF", new ECDHCBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA224()));
      }
   }

   public static class CDHwithSHA256KDFAndSharedInfo extends KeyAgreementSpi {
      public CDHwithSHA256KDFAndSharedInfo() {
         super("ECCDHwithSHA256KDF", new ECDHCBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA256()));
      }
   }

   public static class CDHwithSHA384KDFAndSharedInfo extends KeyAgreementSpi {
      public CDHwithSHA384KDFAndSharedInfo() {
         super("ECCDHwithSHA384KDF", new ECDHCBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA384()));
      }
   }

   public static class CDHwithSHA512KDFAndSharedInfo extends KeyAgreementSpi {
      public CDHwithSHA512KDFAndSharedInfo() {
         super("ECCDHwithSHA512KDF", new ECDHCBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA512()));
      }
   }

   public static class DH extends KeyAgreementSpi {
      public DH() {
         super("ECDH", new ECDHBasicAgreement(), (DerivationFunction)null);
      }
   }

   public static class DHC extends KeyAgreementSpi {
      public DHC() {
         super("ECDHC", new ECDHCBasicAgreement(), (DerivationFunction)null);
      }
   }

   public static class DHwithSHA1CKDF extends KeyAgreementSpi {
      public DHwithSHA1CKDF() {
         super("ECDHwithSHA1CKDF", new ECDHCBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.createSHA1()));
      }
   }

   public static class DHwithSHA1KDF extends KeyAgreementSpi {
      public DHwithSHA1KDF() {
         super("ECDHwithSHA1KDF", new ECDHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA1()));
      }
   }

   public static class DHwithSHA1KDFAndSharedInfo extends KeyAgreementSpi {
      public DHwithSHA1KDFAndSharedInfo() {
         super("ECDHwithSHA1KDF", new ECDHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA1()));
      }
   }

   public static class DHwithSHA224KDFAndSharedInfo extends KeyAgreementSpi {
      public DHwithSHA224KDFAndSharedInfo() {
         super("ECDHwithSHA224KDF", new ECDHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA224()));
      }
   }

   public static class DHwithSHA256CKDF extends KeyAgreementSpi {
      public DHwithSHA256CKDF() {
         super("ECDHwithSHA256CKDF", new ECDHCBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.createSHA256()));
      }
   }

   public static class DHwithSHA256KDFAndSharedInfo extends KeyAgreementSpi {
      public DHwithSHA256KDFAndSharedInfo() {
         super("ECDHwithSHA256KDF", new ECDHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA256()));
      }
   }

   public static class DHwithSHA384CKDF extends KeyAgreementSpi {
      public DHwithSHA384CKDF() {
         super("ECDHwithSHA384CKDF", new ECDHCBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.createSHA384()));
      }
   }

   public static class DHwithSHA384KDFAndSharedInfo extends KeyAgreementSpi {
      public DHwithSHA384KDFAndSharedInfo() {
         super("ECDHwithSHA384KDF", new ECDHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA384()));
      }
   }

   public static class DHwithSHA512CKDF extends KeyAgreementSpi {
      public DHwithSHA512CKDF() {
         super("ECDHwithSHA512CKDF", new ECDHCBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.createSHA512()));
      }
   }

   public static class DHwithSHA512KDFAndSharedInfo extends KeyAgreementSpi {
      public DHwithSHA512KDFAndSharedInfo() {
         super("ECDHwithSHA512KDF", new ECDHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA512()));
      }
   }

   public static class MQV extends KeyAgreementSpi {
      public MQV() {
         super("ECMQV", new ECMQVBasicAgreement(), (DerivationFunction)null);
      }
   }

   public static class MQVwithSHA1CKDF extends KeyAgreementSpi {
      public MQVwithSHA1CKDF() {
         super("ECMQVwithSHA1CKDF", new ECMQVBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.createSHA1()));
      }
   }

   public static class MQVwithSHA1KDFAndSharedInfo extends KeyAgreementSpi {
      public MQVwithSHA1KDFAndSharedInfo() {
         super("ECMQVwithSHA1KDF", new ECMQVBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA1()));
      }
   }

   public static class MQVwithSHA224CKDF extends KeyAgreementSpi {
      public MQVwithSHA224CKDF() {
         super("ECMQVwithSHA224CKDF", new ECMQVBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.createSHA224()));
      }
   }

   public static class MQVwithSHA224KDFAndSharedInfo extends KeyAgreementSpi {
      public MQVwithSHA224KDFAndSharedInfo() {
         super("ECMQVwithSHA224KDF", new ECMQVBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA224()));
      }
   }

   public static class MQVwithSHA256CKDF extends KeyAgreementSpi {
      public MQVwithSHA256CKDF() {
         super("ECMQVwithSHA256CKDF", new ECMQVBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.createSHA256()));
      }
   }

   public static class MQVwithSHA256KDFAndSharedInfo extends KeyAgreementSpi {
      public MQVwithSHA256KDFAndSharedInfo() {
         super("ECMQVwithSHA256KDF", new ECMQVBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA256()));
      }
   }

   public static class MQVwithSHA384CKDF extends KeyAgreementSpi {
      public MQVwithSHA384CKDF() {
         super("ECMQVwithSHA384CKDF", new ECMQVBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.createSHA384()));
      }
   }

   public static class MQVwithSHA384KDFAndSharedInfo extends KeyAgreementSpi {
      public MQVwithSHA384KDFAndSharedInfo() {
         super("ECMQVwithSHA384KDF", new ECMQVBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA384()));
      }
   }

   public static class MQVwithSHA512CKDF extends KeyAgreementSpi {
      public MQVwithSHA512CKDF() {
         super("ECMQVwithSHA512CKDF", new ECMQVBasicAgreement(), new ConcatenationKDFGenerator(DigestFactory.createSHA512()));
      }
   }

   public static class MQVwithSHA512KDFAndSharedInfo extends KeyAgreementSpi {
      public MQVwithSHA512KDFAndSharedInfo() {
         super("ECMQVwithSHA512KDF", new ECMQVBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA512()));
      }
   }
}
