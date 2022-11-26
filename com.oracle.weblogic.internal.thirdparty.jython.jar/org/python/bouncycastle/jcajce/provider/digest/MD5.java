package org.python.bouncycastle.jcajce.provider.digest;

import org.python.bouncycastle.asn1.iana.IANAObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.crypto.CipherKeyGenerator;
import org.python.bouncycastle.crypto.digests.MD5Digest;
import org.python.bouncycastle.crypto.macs.HMac;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseMac;

public class MD5 {
   private MD5() {
   }

   public static class Digest extends BCMessageDigest implements Cloneable {
      public Digest() {
         super(new MD5Digest());
      }

      public Object clone() throws CloneNotSupportedException {
         Digest var1 = (Digest)super.clone();
         var1.digest = new MD5Digest((MD5Digest)this.digest);
         return var1;
      }
   }

   public static class HashMac extends BaseMac {
      public HashMac() {
         super(new HMac(new MD5Digest()));
      }
   }

   public static class KeyGenerator extends BaseKeyGenerator {
      public KeyGenerator() {
         super("HMACMD5", 128, new CipherKeyGenerator());
      }
   }

   public static class Mappings extends DigestAlgorithmProvider {
      private static final String PREFIX = MD5.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("MessageDigest.MD5", PREFIX + "$Digest");
         var1.addAlgorithm("Alg.Alias.MessageDigest." + PKCSObjectIdentifiers.md5, "MD5");
         this.addHMACAlgorithm(var1, "MD5", PREFIX + "$HashMac", PREFIX + "$KeyGenerator");
         this.addHMACAlias(var1, "MD5", IANAObjectIdentifiers.hmacMD5);
      }
   }
}
