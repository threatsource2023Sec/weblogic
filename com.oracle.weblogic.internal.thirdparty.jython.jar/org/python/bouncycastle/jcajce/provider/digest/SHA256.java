package org.python.bouncycastle.jcajce.provider.digest;

import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.crypto.CipherKeyGenerator;
import org.python.bouncycastle.crypto.digests.SHA256Digest;
import org.python.bouncycastle.crypto.macs.HMac;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import org.python.bouncycastle.jcajce.provider.symmetric.util.PBESecretKeyFactory;

public class SHA256 {
   private SHA256() {
   }

   public static class Digest extends BCMessageDigest implements Cloneable {
      public Digest() {
         super(new SHA256Digest());
      }

      public Object clone() throws CloneNotSupportedException {
         Digest var1 = (Digest)super.clone();
         var1.digest = new SHA256Digest((SHA256Digest)this.digest);
         return var1;
      }
   }

   public static class HashMac extends BaseMac {
      public HashMac() {
         super(new HMac(new SHA256Digest()));
      }
   }

   public static class KeyGenerator extends BaseKeyGenerator {
      public KeyGenerator() {
         super("HMACSHA256", 256, new CipherKeyGenerator());
      }
   }

   public static class Mappings extends DigestAlgorithmProvider {
      private static final String PREFIX = SHA256.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("MessageDigest.SHA-256", PREFIX + "$Digest");
         var1.addAlgorithm("Alg.Alias.MessageDigest.SHA256", "SHA-256");
         var1.addAlgorithm("Alg.Alias.MessageDigest." + NISTObjectIdentifiers.id_sha256, "SHA-256");
         var1.addAlgorithm("SecretKeyFactory.PBEWITHHMACSHA256", PREFIX + "$PBEWithMacKeyFactory");
         var1.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHHMACSHA-256", "PBEWITHHMACSHA256");
         var1.addAlgorithm("Alg.Alias.SecretKeyFactory." + NISTObjectIdentifiers.id_sha256, "PBEWITHHMACSHA256");
         var1.addAlgorithm("Mac.PBEWITHHMACSHA256", PREFIX + "$HashMac");
         this.addHMACAlgorithm(var1, "SHA256", PREFIX + "$HashMac", PREFIX + "$KeyGenerator");
         this.addHMACAlias(var1, "SHA256", PKCSObjectIdentifiers.id_hmacWithSHA256);
         this.addHMACAlias(var1, "SHA256", NISTObjectIdentifiers.id_sha256);
      }
   }

   public static class PBEWithMacKeyFactory extends PBESecretKeyFactory {
      public PBEWithMacKeyFactory() {
         super("PBEwithHmacSHA256", (ASN1ObjectIdentifier)null, false, 2, 4, 256, 0);
      }
   }
}
