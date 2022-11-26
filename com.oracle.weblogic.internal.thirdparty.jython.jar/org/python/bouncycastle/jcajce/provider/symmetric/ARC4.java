package org.python.bouncycastle.jcajce.provider.symmetric;

import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.crypto.CipherKeyGenerator;
import org.python.bouncycastle.crypto.engines.RC4Engine;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseStreamCipher;
import org.python.bouncycastle.jcajce.provider.symmetric.util.PBESecretKeyFactory;
import org.python.bouncycastle.jcajce.provider.util.AlgorithmProvider;

public final class ARC4 {
   private ARC4() {
   }

   public static class Base extends BaseStreamCipher {
      public Base() {
         super(new RC4Engine(), 0);
      }
   }

   public static class KeyGen extends BaseKeyGenerator {
      public KeyGen() {
         super("RC4", 128, new CipherKeyGenerator());
      }
   }

   public static class Mappings extends AlgorithmProvider {
      private static final String PREFIX = ARC4.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("Cipher.ARC4", PREFIX + "$Base");
         var1.addAlgorithm("Alg.Alias.Cipher", PKCSObjectIdentifiers.rc4, "ARC4");
         var1.addAlgorithm("Alg.Alias.Cipher.ARCFOUR", "ARC4");
         var1.addAlgorithm("Alg.Alias.Cipher.RC4", "ARC4");
         var1.addAlgorithm("KeyGenerator.ARC4", PREFIX + "$KeyGen");
         var1.addAlgorithm("Alg.Alias.KeyGenerator.RC4", "ARC4");
         var1.addAlgorithm("Alg.Alias.KeyGenerator.1.2.840.113549.3.4", "ARC4");
         var1.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND128BITRC4", PREFIX + "$PBEWithSHAAnd128BitKeyFactory");
         var1.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND40BITRC4", PREFIX + "$PBEWithSHAAnd40BitKeyFactory");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters." + PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC4, "PKCS12PBE");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters." + PKCSObjectIdentifiers.pbeWithSHAAnd40BitRC4, "PKCS12PBE");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND40BITRC4", "PKCS12PBE");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND128BITRC4", "PKCS12PBE");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDRC4", "PKCS12PBE");
         var1.addAlgorithm("Cipher.PBEWITHSHAAND128BITRC4", PREFIX + "$PBEWithSHAAnd128Bit");
         var1.addAlgorithm("Cipher.PBEWITHSHAAND40BITRC4", PREFIX + "$PBEWithSHAAnd40Bit");
         var1.addAlgorithm("Alg.Alias.SecretKeyFactory", PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC4, "PBEWITHSHAAND128BITRC4");
         var1.addAlgorithm("Alg.Alias.SecretKeyFactory", PKCSObjectIdentifiers.pbeWithSHAAnd40BitRC4, "PBEWITHSHAAND40BITRC4");
         var1.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND128BITRC4", "PBEWITHSHAAND128BITRC4");
         var1.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND40BITRC4", "PBEWITHSHAAND40BITRC4");
         var1.addAlgorithm("Alg.Alias.Cipher", PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC4, "PBEWITHSHAAND128BITRC4");
         var1.addAlgorithm("Alg.Alias.Cipher", PKCSObjectIdentifiers.pbeWithSHAAnd40BitRC4, "PBEWITHSHAAND40BITRC4");
      }
   }

   public static class PBEWithSHAAnd128Bit extends BaseStreamCipher {
      public PBEWithSHAAnd128Bit() {
         super(new RC4Engine(), 0, 128, 1);
      }
   }

   public static class PBEWithSHAAnd128BitKeyFactory extends PBESecretKeyFactory {
      public PBEWithSHAAnd128BitKeyFactory() {
         super("PBEWithSHAAnd128BitRC4", PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC4, true, 2, 1, 128, 0);
      }
   }

   public static class PBEWithSHAAnd40Bit extends BaseStreamCipher {
      public PBEWithSHAAnd40Bit() {
         super(new RC4Engine(), 0, 40, 1);
      }
   }

   public static class PBEWithSHAAnd40BitKeyFactory extends PBESecretKeyFactory {
      public PBEWithSHAAnd40BitKeyFactory() {
         super("PBEWithSHAAnd128BitRC4", PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC4, true, 2, 1, 40, 0);
      }
   }
}
