package org.cryptacular.generator;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.SecureRandom;
import org.bouncycastle.jcajce.provider.asymmetric.dsa.KeyPairGeneratorSpi;
import org.bouncycastle.jce.spec.ECNamedCurveGenParameterSpec;

public final class KeyPairGenerator {
   private KeyPairGenerator() {
   }

   public static KeyPair generateDSA(SecureRandom random, int bitLength) {
      KeyPairGeneratorSpi generator = new KeyPairGeneratorSpi();
      generator.initialize(bitLength, random);
      return generator.generateKeyPair();
   }

   public static KeyPair generateRSA(SecureRandom random, int bitLength) {
      org.bouncycastle.jcajce.provider.asymmetric.rsa.KeyPairGeneratorSpi generator = new org.bouncycastle.jcajce.provider.asymmetric.rsa.KeyPairGeneratorSpi();
      generator.initialize(bitLength, random);
      return generator.generateKeyPair();
   }

   public static KeyPair generateEC(SecureRandom random, int bitLength) {
      org.bouncycastle.jcajce.provider.asymmetric.ec.KeyPairGeneratorSpi.EC generator = new org.bouncycastle.jcajce.provider.asymmetric.ec.KeyPairGeneratorSpi.EC();
      generator.initialize(bitLength, random);
      return generator.generateKeyPair();
   }

   public static KeyPair generateEC(SecureRandom random, String namedCurve) {
      org.bouncycastle.jcajce.provider.asymmetric.ec.KeyPairGeneratorSpi.EC generator = new org.bouncycastle.jcajce.provider.asymmetric.ec.KeyPairGeneratorSpi.EC();

      try {
         generator.initialize(new ECNamedCurveGenParameterSpec(namedCurve), random);
      } catch (InvalidAlgorithmParameterException var4) {
         throw new IllegalArgumentException("Invalid EC curve " + namedCurve, var4);
      }

      return generator.generateKeyPair();
   }
}
