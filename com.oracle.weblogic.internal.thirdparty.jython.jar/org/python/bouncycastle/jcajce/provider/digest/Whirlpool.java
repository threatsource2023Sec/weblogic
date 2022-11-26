package org.python.bouncycastle.jcajce.provider.digest;

import org.python.bouncycastle.crypto.CipherKeyGenerator;
import org.python.bouncycastle.crypto.digests.WhirlpoolDigest;
import org.python.bouncycastle.crypto.macs.HMac;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseMac;

public class Whirlpool {
   private Whirlpool() {
   }

   public static class Digest extends BCMessageDigest implements Cloneable {
      public Digest() {
         super(new WhirlpoolDigest());
      }

      public Object clone() throws CloneNotSupportedException {
         Digest var1 = (Digest)super.clone();
         var1.digest = new WhirlpoolDigest((WhirlpoolDigest)this.digest);
         return var1;
      }
   }

   public static class HashMac extends BaseMac {
      public HashMac() {
         super(new HMac(new WhirlpoolDigest()));
      }
   }

   public static class KeyGenerator extends BaseKeyGenerator {
      public KeyGenerator() {
         super("HMACWHIRLPOOL", 512, new CipherKeyGenerator());
      }
   }

   public static class Mappings extends DigestAlgorithmProvider {
      private static final String PREFIX = Whirlpool.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("MessageDigest.WHIRLPOOL", PREFIX + "$Digest");
         this.addHMACAlgorithm(var1, "WHIRLPOOL", PREFIX + "$HashMac", PREFIX + "$KeyGenerator");
      }
   }
}
