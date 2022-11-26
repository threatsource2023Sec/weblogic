package org.python.bouncycastle.jcajce.provider.digest;

import org.python.bouncycastle.crypto.digests.SM3Digest;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;

public class SM3 {
   private SM3() {
   }

   public static class Digest extends BCMessageDigest implements Cloneable {
      public Digest() {
         super(new SM3Digest());
      }

      public Object clone() throws CloneNotSupportedException {
         Digest var1 = (Digest)super.clone();
         var1.digest = new SM3Digest((SM3Digest)this.digest);
         return var1;
      }
   }

   public static class Mappings extends DigestAlgorithmProvider {
      private static final String PREFIX = SM3.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("MessageDigest.SM3", PREFIX + "$Digest");
         var1.addAlgorithm("Alg.Alias.MessageDigest.SM3", "SM3");
         var1.addAlgorithm("Alg.Alias.MessageDigest.1.2.156.197.1.401", "SM3");
      }
   }
}
