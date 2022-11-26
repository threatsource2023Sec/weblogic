package org.python.bouncycastle.jcajce.provider.digest;

import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.iana.IANAObjectIdentifiers;
import org.python.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.python.bouncycastle.crypto.CipherKeyGenerator;
import org.python.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.python.bouncycastle.crypto.macs.HMac;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import org.python.bouncycastle.jcajce.provider.symmetric.util.PBESecretKeyFactory;

public class RIPEMD160 {
   private RIPEMD160() {
   }

   public static class Digest extends BCMessageDigest implements Cloneable {
      public Digest() {
         super(new RIPEMD160Digest());
      }

      public Object clone() throws CloneNotSupportedException {
         Digest var1 = (Digest)super.clone();
         var1.digest = new RIPEMD160Digest((RIPEMD160Digest)this.digest);
         return var1;
      }
   }

   public static class HashMac extends BaseMac {
      public HashMac() {
         super(new HMac(new RIPEMD160Digest()));
      }
   }

   public static class KeyGenerator extends BaseKeyGenerator {
      public KeyGenerator() {
         super("HMACRIPEMD160", 160, new CipherKeyGenerator());
      }
   }

   public static class Mappings extends DigestAlgorithmProvider {
      private static final String PREFIX = RIPEMD160.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("MessageDigest.RIPEMD160", PREFIX + "$Digest");
         var1.addAlgorithm("Alg.Alias.MessageDigest." + TeleTrusTObjectIdentifiers.ripemd160, "RIPEMD160");
         this.addHMACAlgorithm(var1, "RIPEMD160", PREFIX + "$HashMac", PREFIX + "$KeyGenerator");
         this.addHMACAlias(var1, "RIPEMD160", IANAObjectIdentifiers.hmacRIPEMD160);
         var1.addAlgorithm("SecretKeyFactory.PBEWITHHMACRIPEMD160", PREFIX + "$PBEWithHmacKeyFactory");
         var1.addAlgorithm("Mac.PBEWITHHMACRIPEMD160", PREFIX + "$PBEWithHmac");
      }
   }

   public static class PBEWithHmac extends BaseMac {
      public PBEWithHmac() {
         super(new HMac(new RIPEMD160Digest()), 2, 2, 160);
      }
   }

   public static class PBEWithHmacKeyFactory extends PBESecretKeyFactory {
      public PBEWithHmacKeyFactory() {
         super("PBEwithHmacRIPEMD160", (ASN1ObjectIdentifier)null, false, 2, 2, 160, 0);
      }
   }
}
