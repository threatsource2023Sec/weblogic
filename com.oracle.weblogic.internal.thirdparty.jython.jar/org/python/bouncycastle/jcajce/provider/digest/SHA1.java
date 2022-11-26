package org.python.bouncycastle.jcajce.provider.digest;

import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.iana.IANAObjectIdentifiers;
import org.python.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.crypto.CipherKeyGenerator;
import org.python.bouncycastle.crypto.digests.SHA1Digest;
import org.python.bouncycastle.crypto.macs.HMac;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import org.python.bouncycastle.jcajce.provider.symmetric.util.PBESecretKeyFactory;

public class SHA1 {
   private SHA1() {
   }

   public static class Digest extends BCMessageDigest implements Cloneable {
      public Digest() {
         super(new SHA1Digest());
      }

      public Object clone() throws CloneNotSupportedException {
         Digest var1 = (Digest)super.clone();
         var1.digest = new SHA1Digest((SHA1Digest)this.digest);
         return var1;
      }
   }

   public static class HashMac extends BaseMac {
      public HashMac() {
         super(new HMac(new SHA1Digest()));
      }
   }

   public static class KeyGenerator extends BaseKeyGenerator {
      public KeyGenerator() {
         super("HMACSHA1", 160, new CipherKeyGenerator());
      }
   }

   public static class Mappings extends DigestAlgorithmProvider {
      private static final String PREFIX = SHA1.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("MessageDigest.SHA-1", PREFIX + "$Digest");
         var1.addAlgorithm("Alg.Alias.MessageDigest.SHA1", "SHA-1");
         var1.addAlgorithm("Alg.Alias.MessageDigest.SHA", "SHA-1");
         var1.addAlgorithm("Alg.Alias.MessageDigest." + OIWObjectIdentifiers.idSHA1, "SHA-1");
         this.addHMACAlgorithm(var1, "SHA1", PREFIX + "$HashMac", PREFIX + "$KeyGenerator");
         this.addHMACAlias(var1, "SHA1", PKCSObjectIdentifiers.id_hmacWithSHA1);
         this.addHMACAlias(var1, "SHA1", IANAObjectIdentifiers.hmacSHA1);
         var1.addAlgorithm("Mac.PBEWITHHMACSHA", PREFIX + "$SHA1Mac");
         var1.addAlgorithm("Mac.PBEWITHHMACSHA1", PREFIX + "$SHA1Mac");
         var1.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHHMACSHA", "PBEWITHHMACSHA1");
         var1.addAlgorithm("Alg.Alias.SecretKeyFactory." + OIWObjectIdentifiers.idSHA1, "PBEWITHHMACSHA1");
         var1.addAlgorithm("Alg.Alias.Mac." + OIWObjectIdentifiers.idSHA1, "PBEWITHHMACSHA");
         var1.addAlgorithm("SecretKeyFactory.PBEWITHHMACSHA1", PREFIX + "$PBEWithMacKeyFactory");
      }
   }

   public static class PBEWithMacKeyFactory extends PBESecretKeyFactory {
      public PBEWithMacKeyFactory() {
         super("PBEwithHmacSHA", (ASN1ObjectIdentifier)null, false, 2, 1, 160, 0);
      }
   }

   public static class SHA1Mac extends BaseMac {
      public SHA1Mac() {
         super(new HMac(new SHA1Digest()));
      }
   }
}
