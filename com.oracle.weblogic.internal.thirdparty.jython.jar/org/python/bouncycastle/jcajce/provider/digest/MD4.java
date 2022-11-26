package org.python.bouncycastle.jcajce.provider.digest;

import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.crypto.CipherKeyGenerator;
import org.python.bouncycastle.crypto.digests.MD4Digest;
import org.python.bouncycastle.crypto.macs.HMac;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseMac;

public class MD4 {
   private MD4() {
   }

   public static class Digest extends BCMessageDigest implements Cloneable {
      public Digest() {
         super(new MD4Digest());
      }

      public Object clone() throws CloneNotSupportedException {
         Digest var1 = (Digest)super.clone();
         var1.digest = new MD4Digest((MD4Digest)this.digest);
         return var1;
      }
   }

   public static class HashMac extends BaseMac {
      public HashMac() {
         super(new HMac(new MD4Digest()));
      }
   }

   public static class KeyGenerator extends BaseKeyGenerator {
      public KeyGenerator() {
         super("HMACMD4", 128, new CipherKeyGenerator());
      }
   }

   public static class Mappings extends DigestAlgorithmProvider {
      private static final String PREFIX = MD4.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("MessageDigest.MD4", PREFIX + "$Digest");
         var1.addAlgorithm("Alg.Alias.MessageDigest." + PKCSObjectIdentifiers.md4, "MD4");
         this.addHMACAlgorithm(var1, "MD4", PREFIX + "$HashMac", PREFIX + "$KeyGenerator");
      }
   }
}
