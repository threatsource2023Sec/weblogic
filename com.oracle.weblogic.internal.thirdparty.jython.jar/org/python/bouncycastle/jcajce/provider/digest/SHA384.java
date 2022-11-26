package org.python.bouncycastle.jcajce.provider.digest;

import org.python.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.crypto.CipherKeyGenerator;
import org.python.bouncycastle.crypto.digests.SHA384Digest;
import org.python.bouncycastle.crypto.macs.HMac;
import org.python.bouncycastle.crypto.macs.OldHMac;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseMac;

public class SHA384 {
   private SHA384() {
   }

   public static class Digest extends BCMessageDigest implements Cloneable {
      public Digest() {
         super(new SHA384Digest());
      }

      public Object clone() throws CloneNotSupportedException {
         Digest var1 = (Digest)super.clone();
         var1.digest = new SHA384Digest((SHA384Digest)this.digest);
         return var1;
      }
   }

   public static class HashMac extends BaseMac {
      public HashMac() {
         super(new HMac(new SHA384Digest()));
      }
   }

   public static class KeyGenerator extends BaseKeyGenerator {
      public KeyGenerator() {
         super("HMACSHA384", 384, new CipherKeyGenerator());
      }
   }

   public static class Mappings extends DigestAlgorithmProvider {
      private static final String PREFIX = SHA384.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("MessageDigest.SHA-384", PREFIX + "$Digest");
         var1.addAlgorithm("Alg.Alias.MessageDigest.SHA384", "SHA-384");
         var1.addAlgorithm("Alg.Alias.MessageDigest." + NISTObjectIdentifiers.id_sha384, "SHA-384");
         var1.addAlgorithm("Mac.OLDHMACSHA384", PREFIX + "$OldSHA384");
         var1.addAlgorithm("Mac.PBEWITHHMACSHA384", PREFIX + "$HashMac");
         this.addHMACAlgorithm(var1, "SHA384", PREFIX + "$HashMac", PREFIX + "$KeyGenerator");
         this.addHMACAlias(var1, "SHA384", PKCSObjectIdentifiers.id_hmacWithSHA384);
      }
   }

   public static class OldSHA384 extends BaseMac {
      public OldSHA384() {
         super(new OldHMac(new SHA384Digest()));
      }
   }
}
