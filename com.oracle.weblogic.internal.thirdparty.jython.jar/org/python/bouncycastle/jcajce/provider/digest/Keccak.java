package org.python.bouncycastle.jcajce.provider.digest;

import org.python.bouncycastle.crypto.CipherKeyGenerator;
import org.python.bouncycastle.crypto.digests.KeccakDigest;
import org.python.bouncycastle.crypto.macs.HMac;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseMac;

public class Keccak {
   private Keccak() {
   }

   public static class Digest224 extends DigestKeccak {
      public Digest224() {
         super(224);
      }
   }

   public static class Digest256 extends DigestKeccak {
      public Digest256() {
         super(256);
      }
   }

   public static class Digest288 extends DigestKeccak {
      public Digest288() {
         super(288);
      }
   }

   public static class Digest384 extends DigestKeccak {
      public Digest384() {
         super(384);
      }
   }

   public static class Digest512 extends DigestKeccak {
      public Digest512() {
         super(512);
      }
   }

   public static class DigestKeccak extends BCMessageDigest implements Cloneable {
      public DigestKeccak(int var1) {
         super(new KeccakDigest(var1));
      }

      public Object clone() throws CloneNotSupportedException {
         BCMessageDigest var1 = (BCMessageDigest)super.clone();
         var1.digest = new KeccakDigest((KeccakDigest)this.digest);
         return var1;
      }
   }

   public static class HashMac224 extends BaseMac {
      public HashMac224() {
         super(new HMac(new KeccakDigest(224)));
      }
   }

   public static class HashMac256 extends BaseMac {
      public HashMac256() {
         super(new HMac(new KeccakDigest(256)));
      }
   }

   public static class HashMac288 extends BaseMac {
      public HashMac288() {
         super(new HMac(new KeccakDigest(288)));
      }
   }

   public static class HashMac384 extends BaseMac {
      public HashMac384() {
         super(new HMac(new KeccakDigest(384)));
      }
   }

   public static class HashMac512 extends BaseMac {
      public HashMac512() {
         super(new HMac(new KeccakDigest(512)));
      }
   }

   public static class KeyGenerator224 extends BaseKeyGenerator {
      public KeyGenerator224() {
         super("HMACKECCAK224", 224, new CipherKeyGenerator());
      }
   }

   public static class KeyGenerator256 extends BaseKeyGenerator {
      public KeyGenerator256() {
         super("HMACKECCAK256", 256, new CipherKeyGenerator());
      }
   }

   public static class KeyGenerator288 extends BaseKeyGenerator {
      public KeyGenerator288() {
         super("HMACKECCAK288", 288, new CipherKeyGenerator());
      }
   }

   public static class KeyGenerator384 extends BaseKeyGenerator {
      public KeyGenerator384() {
         super("HMACKECCAK384", 384, new CipherKeyGenerator());
      }
   }

   public static class KeyGenerator512 extends BaseKeyGenerator {
      public KeyGenerator512() {
         super("HMACKECCAK512", 512, new CipherKeyGenerator());
      }
   }

   public static class Mappings extends DigestAlgorithmProvider {
      private static final String PREFIX = Keccak.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("MessageDigest.KECCAK-224", PREFIX + "$Digest224");
         var1.addAlgorithm("MessageDigest.KECCAK-288", PREFIX + "$Digest288");
         var1.addAlgorithm("MessageDigest.KECCAK-256", PREFIX + "$Digest256");
         var1.addAlgorithm("MessageDigest.KECCAK-384", PREFIX + "$Digest384");
         var1.addAlgorithm("MessageDigest.KECCAK-512", PREFIX + "$Digest512");
         this.addHMACAlgorithm(var1, "KECCAK224", PREFIX + "$HashMac224", PREFIX + "$KeyGenerator224");
         this.addHMACAlgorithm(var1, "KECCAK256", PREFIX + "$HashMac256", PREFIX + "$KeyGenerator256");
         this.addHMACAlgorithm(var1, "KECCAK288", PREFIX + "$HashMac288", PREFIX + "$KeyGenerator288");
         this.addHMACAlgorithm(var1, "KECCAK384", PREFIX + "$HashMac384", PREFIX + "$KeyGenerator384");
         this.addHMACAlgorithm(var1, "KECCAK512", PREFIX + "$HashMac512", PREFIX + "$KeyGenerator512");
      }
   }
}
