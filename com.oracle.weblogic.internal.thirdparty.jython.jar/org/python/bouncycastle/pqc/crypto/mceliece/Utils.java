package org.python.bouncycastle.pqc.crypto.mceliece;

import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.digests.SHA1Digest;
import org.python.bouncycastle.crypto.digests.SHA224Digest;
import org.python.bouncycastle.crypto.digests.SHA256Digest;
import org.python.bouncycastle.crypto.digests.SHA384Digest;
import org.python.bouncycastle.crypto.digests.SHA512Digest;

class Utils {
   static Digest getDigest(String var0) {
      if (var0.equals("SHA-1")) {
         return new SHA1Digest();
      } else if (var0.equals("SHA-224")) {
         return new SHA224Digest();
      } else if (var0.equals("SHA-256")) {
         return new SHA256Digest();
      } else if (var0.equals("SHA-384")) {
         return new SHA384Digest();
      } else if (var0.equals("SHA-512")) {
         return new SHA512Digest();
      } else {
         throw new IllegalArgumentException("unrecognised digest algorithm: " + var0);
      }
   }
}
