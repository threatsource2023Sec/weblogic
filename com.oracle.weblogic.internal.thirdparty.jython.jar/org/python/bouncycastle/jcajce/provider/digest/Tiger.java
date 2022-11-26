package org.python.bouncycastle.jcajce.provider.digest;

import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.iana.IANAObjectIdentifiers;
import org.python.bouncycastle.crypto.CipherKeyGenerator;
import org.python.bouncycastle.crypto.digests.TigerDigest;
import org.python.bouncycastle.crypto.macs.HMac;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import org.python.bouncycastle.jcajce.provider.symmetric.util.PBESecretKeyFactory;

public class Tiger {
   private Tiger() {
   }

   public static class Digest extends BCMessageDigest implements Cloneable {
      public Digest() {
         super(new TigerDigest());
      }

      public Object clone() throws CloneNotSupportedException {
         Digest var1 = (Digest)super.clone();
         var1.digest = new TigerDigest((TigerDigest)this.digest);
         return var1;
      }
   }

   public static class HashMac extends BaseMac {
      public HashMac() {
         super(new HMac(new TigerDigest()));
      }
   }

   public static class KeyGenerator extends BaseKeyGenerator {
      public KeyGenerator() {
         super("HMACTIGER", 192, new CipherKeyGenerator());
      }
   }

   public static class Mappings extends DigestAlgorithmProvider {
      private static final String PREFIX = Tiger.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("MessageDigest.TIGER", PREFIX + "$Digest");
         var1.addAlgorithm("MessageDigest.Tiger", PREFIX + "$Digest");
         this.addHMACAlgorithm(var1, "TIGER", PREFIX + "$HashMac", PREFIX + "$KeyGenerator");
         this.addHMACAlias(var1, "TIGER", IANAObjectIdentifiers.hmacTIGER);
         var1.addAlgorithm("SecretKeyFactory.PBEWITHHMACTIGER", PREFIX + "$PBEWithMacKeyFactory");
      }
   }

   public static class PBEWithHashMac extends BaseMac {
      public PBEWithHashMac() {
         super(new HMac(new TigerDigest()), 2, 3, 192);
      }
   }

   public static class PBEWithMacKeyFactory extends PBESecretKeyFactory {
      public PBEWithMacKeyFactory() {
         super("PBEwithHmacTiger", (ASN1ObjectIdentifier)null, false, 2, 3, 192, 0);
      }
   }

   public static class TigerHmac extends BaseMac {
      public TigerHmac() {
         super(new HMac(new TigerDigest()));
      }
   }
}
