package org.cryptacular.spec;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.GOST3411Digest;
import org.bouncycastle.crypto.digests.MD2Digest;
import org.bouncycastle.crypto.digests.MD4Digest;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.digests.RIPEMD128Digest;
import org.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.bouncycastle.crypto.digests.RIPEMD256Digest;
import org.bouncycastle.crypto.digests.RIPEMD320Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.digests.SHA224Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA384Digest;
import org.bouncycastle.crypto.digests.SHA3Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.digests.TigerDigest;
import org.bouncycastle.crypto.digests.WhirlpoolDigest;

public class DigestSpec implements Spec {
   private final String algorithm;
   private final int size;

   public DigestSpec(String algName) {
      if (algName == null) {
         throw new IllegalArgumentException("Algorithm name is required.");
      } else {
         this.algorithm = algName;
         this.size = -1;
      }
   }

   public DigestSpec(String algName, int digestSize) {
      if (algName == null) {
         throw new IllegalArgumentException("Algorithm name is required.");
      } else {
         this.algorithm = algName;
         if (digestSize < 0) {
            throw new IllegalArgumentException("Digest size must be positive.");
         } else {
            this.size = digestSize;
         }
      }
   }

   public String getAlgorithm() {
      return this.algorithm;
   }

   public int getSize() {
      return this.size;
   }

   public Digest newInstance() {
      Object digest;
      if ("GOST3411".equalsIgnoreCase(this.algorithm)) {
         digest = new GOST3411Digest();
      } else if ("MD2".equalsIgnoreCase(this.algorithm)) {
         digest = new MD2Digest();
      } else if ("MD4".equalsIgnoreCase(this.algorithm)) {
         digest = new MD4Digest();
      } else if ("MD5".equalsIgnoreCase(this.algorithm)) {
         digest = new MD5Digest();
      } else if (!"RIPEMD128".equalsIgnoreCase(this.algorithm) && !"RIPEMD-128".equalsIgnoreCase(this.algorithm)) {
         if (!"RIPEMD160".equalsIgnoreCase(this.algorithm) && !"RIPEMD-160".equalsIgnoreCase(this.algorithm)) {
            if (!"RIPEMD256".equalsIgnoreCase(this.algorithm) && !"RIPEMD-256".equalsIgnoreCase(this.algorithm)) {
               if (!"RIPEMD320".equalsIgnoreCase(this.algorithm) && !"RIPEMD-320".equalsIgnoreCase(this.algorithm)) {
                  if (!"SHA1".equalsIgnoreCase(this.algorithm) && !"SHA-1".equalsIgnoreCase(this.algorithm)) {
                     if (!"SHA224".equalsIgnoreCase(this.algorithm) && !"SHA-224".equalsIgnoreCase(this.algorithm)) {
                        if (!"SHA256".equalsIgnoreCase(this.algorithm) && !"SHA-256".equalsIgnoreCase(this.algorithm)) {
                           if (!"SHA384".equalsIgnoreCase(this.algorithm) && !"SHA-384".equalsIgnoreCase(this.algorithm)) {
                              if (!"SHA512".equalsIgnoreCase(this.algorithm) && !"SHA-512".equalsIgnoreCase(this.algorithm)) {
                                 if (!"SHA3".equalsIgnoreCase(this.algorithm) && !"SHA-3".equalsIgnoreCase(this.algorithm)) {
                                    if ("Tiger".equalsIgnoreCase(this.algorithm)) {
                                       digest = new TigerDigest();
                                    } else {
                                       if (!"Whirlpool".equalsIgnoreCase(this.algorithm)) {
                                          throw new IllegalStateException("Unsupported digest algorithm " + this.algorithm);
                                       }

                                       digest = new WhirlpoolDigest();
                                    }
                                 } else {
                                    digest = new SHA3Digest(this.size);
                                 }
                              } else {
                                 digest = new SHA512Digest();
                              }
                           } else {
                              digest = new SHA384Digest();
                           }
                        } else {
                           digest = new SHA256Digest();
                        }
                     } else {
                        digest = new SHA224Digest();
                     }
                  } else {
                     digest = new SHA1Digest();
                  }
               } else {
                  digest = new RIPEMD320Digest();
               }
            } else {
               digest = new RIPEMD256Digest();
            }
         } else {
            digest = new RIPEMD160Digest();
         }
      } else {
         digest = new RIPEMD128Digest();
      }

      return (Digest)digest;
   }

   public String toString() {
      return this.algorithm;
   }
}
