package org.python.bouncycastle.jcajce.provider.asymmetric.ecgost;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import org.python.bouncycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.python.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.python.bouncycastle.crypto.params.ECDomainParameters;
import org.python.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.python.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.python.bouncycastle.jce.provider.BouncyCastleProvider;
import org.python.bouncycastle.jce.spec.ECNamedCurveGenParameterSpec;
import org.python.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.python.bouncycastle.jce.spec.ECParameterSpec;
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.math.ec.ECPoint;

public class KeyPairGeneratorSpi extends KeyPairGenerator {
   Object ecParams = null;
   ECKeyPairGenerator engine = new ECKeyPairGenerator();
   String algorithm = "ECGOST3410";
   ECKeyGenerationParameters param;
   int strength = 239;
   SecureRandom random = null;
   boolean initialised = false;

   public KeyPairGeneratorSpi() {
      super("ECGOST3410");
   }

   public void initialize(int var1, SecureRandom var2) {
      this.strength = var1;
      this.random = var2;
      if (this.ecParams != null) {
         try {
            this.initialize((ECGenParameterSpec)this.ecParams, var2);
         } catch (InvalidAlgorithmParameterException var4) {
            throw new InvalidParameterException("key size not configurable.");
         }
      } else {
         throw new InvalidParameterException("unknown key size.");
      }
   }

   public void initialize(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
      ECParameterSpec var8;
      if (var1 instanceof ECParameterSpec) {
         var8 = (ECParameterSpec)var1;
         this.ecParams = var1;
         this.param = new ECKeyGenerationParameters(new ECDomainParameters(var8.getCurve(), var8.getG(), var8.getN(), var8.getH()), var2);
         this.engine.init(this.param);
         this.initialised = true;
      } else if (var1 instanceof java.security.spec.ECParameterSpec) {
         java.security.spec.ECParameterSpec var9 = (java.security.spec.ECParameterSpec)var1;
         this.ecParams = var1;
         ECCurve var10 = EC5Util.convertCurve(var9.getCurve());
         ECPoint var11 = EC5Util.convertPoint(var10, var9.getGenerator(), false);
         this.param = new ECKeyGenerationParameters(new ECDomainParameters(var10, var11, var9.getOrder(), BigInteger.valueOf((long)var9.getCofactor())), var2);
         this.engine.init(this.param);
         this.initialised = true;
      } else if (!(var1 instanceof ECGenParameterSpec) && !(var1 instanceof ECNamedCurveGenParameterSpec)) {
         if (var1 != null || BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa() == null) {
            if (var1 == null && BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa() == null) {
               throw new InvalidAlgorithmParameterException("null parameter passed but no implicitCA set");
            }

            throw new InvalidAlgorithmParameterException("parameter object not a ECParameterSpec: " + var1.getClass().getName());
         }

         var8 = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
         this.ecParams = var1;
         this.param = new ECKeyGenerationParameters(new ECDomainParameters(var8.getCurve(), var8.getG(), var8.getN(), var8.getH()), var2);
         this.engine.init(this.param);
         this.initialised = true;
      } else {
         String var3;
         if (var1 instanceof ECGenParameterSpec) {
            var3 = ((ECGenParameterSpec)var1).getName();
         } else {
            var3 = ((ECNamedCurveGenParameterSpec)var1).getName();
         }

         ECDomainParameters var4 = ECGOST3410NamedCurves.getByName(var3);
         if (var4 == null) {
            throw new InvalidAlgorithmParameterException("unknown curve name: " + var3);
         }

         this.ecParams = new ECNamedCurveSpec(var3, var4.getCurve(), var4.getG(), var4.getN(), var4.getH(), var4.getSeed());
         java.security.spec.ECParameterSpec var5 = (java.security.spec.ECParameterSpec)this.ecParams;
         ECCurve var6 = EC5Util.convertCurve(var5.getCurve());
         ECPoint var7 = EC5Util.convertPoint(var6, var5.getGenerator(), false);
         this.param = new ECKeyGenerationParameters(new ECDomainParameters(var6, var7, var5.getOrder(), BigInteger.valueOf((long)var5.getCofactor())), var2);
         this.engine.init(this.param);
         this.initialised = true;
      }

   }

   public KeyPair generateKeyPair() {
      if (!this.initialised) {
         throw new IllegalStateException("EC Key Pair Generator not initialised");
      } else {
         AsymmetricCipherKeyPair var1 = this.engine.generateKeyPair();
         ECPublicKeyParameters var2 = (ECPublicKeyParameters)var1.getPublic();
         ECPrivateKeyParameters var3 = (ECPrivateKeyParameters)var1.getPrivate();
         BCECGOST3410PublicKey var5;
         if (this.ecParams instanceof ECParameterSpec) {
            ECParameterSpec var6 = (ECParameterSpec)this.ecParams;
            var5 = new BCECGOST3410PublicKey(this.algorithm, var2, var6);
            return new KeyPair(var5, new BCECGOST3410PrivateKey(this.algorithm, var3, var5, var6));
         } else if (this.ecParams == null) {
            return new KeyPair(new BCECGOST3410PublicKey(this.algorithm, var2), new BCECGOST3410PrivateKey(this.algorithm, var3));
         } else {
            java.security.spec.ECParameterSpec var4 = (java.security.spec.ECParameterSpec)this.ecParams;
            var5 = new BCECGOST3410PublicKey(this.algorithm, var2, var4);
            return new KeyPair(var5, new BCECGOST3410PrivateKey(this.algorithm, var3, var5, var4));
         }
      }
   }
}
