package org.python.bouncycastle.operator.bc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.python.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.python.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.python.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.crypto.ExtendedDigest;
import org.python.bouncycastle.crypto.digests.GOST3411Digest;
import org.python.bouncycastle.crypto.digests.MD2Digest;
import org.python.bouncycastle.crypto.digests.MD4Digest;
import org.python.bouncycastle.crypto.digests.MD5Digest;
import org.python.bouncycastle.crypto.digests.RIPEMD128Digest;
import org.python.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.python.bouncycastle.crypto.digests.RIPEMD256Digest;
import org.python.bouncycastle.crypto.digests.SHA1Digest;
import org.python.bouncycastle.crypto.digests.SHA224Digest;
import org.python.bouncycastle.crypto.digests.SHA256Digest;
import org.python.bouncycastle.crypto.digests.SHA384Digest;
import org.python.bouncycastle.crypto.digests.SHA3Digest;
import org.python.bouncycastle.crypto.digests.SHA512Digest;
import org.python.bouncycastle.operator.OperatorCreationException;

public class BcDefaultDigestProvider implements BcDigestProvider {
   private static final Map lookup = createTable();
   public static final BcDigestProvider INSTANCE = new BcDefaultDigestProvider();

   private static Map createTable() {
      HashMap var0 = new HashMap();
      var0.put(OIWObjectIdentifiers.idSHA1, new BcDigestProvider() {
         public ExtendedDigest get(AlgorithmIdentifier var1) {
            return new SHA1Digest();
         }
      });
      var0.put(NISTObjectIdentifiers.id_sha224, new BcDigestProvider() {
         public ExtendedDigest get(AlgorithmIdentifier var1) {
            return new SHA224Digest();
         }
      });
      var0.put(NISTObjectIdentifiers.id_sha256, new BcDigestProvider() {
         public ExtendedDigest get(AlgorithmIdentifier var1) {
            return new SHA256Digest();
         }
      });
      var0.put(NISTObjectIdentifiers.id_sha384, new BcDigestProvider() {
         public ExtendedDigest get(AlgorithmIdentifier var1) {
            return new SHA384Digest();
         }
      });
      var0.put(NISTObjectIdentifiers.id_sha512, new BcDigestProvider() {
         public ExtendedDigest get(AlgorithmIdentifier var1) {
            return new SHA512Digest();
         }
      });
      var0.put(NISTObjectIdentifiers.id_sha3_224, new BcDigestProvider() {
         public ExtendedDigest get(AlgorithmIdentifier var1) {
            return new SHA3Digest(224);
         }
      });
      var0.put(NISTObjectIdentifiers.id_sha3_256, new BcDigestProvider() {
         public ExtendedDigest get(AlgorithmIdentifier var1) {
            return new SHA3Digest(256);
         }
      });
      var0.put(NISTObjectIdentifiers.id_sha3_384, new BcDigestProvider() {
         public ExtendedDigest get(AlgorithmIdentifier var1) {
            return new SHA3Digest(384);
         }
      });
      var0.put(NISTObjectIdentifiers.id_sha3_512, new BcDigestProvider() {
         public ExtendedDigest get(AlgorithmIdentifier var1) {
            return new SHA3Digest(512);
         }
      });
      var0.put(PKCSObjectIdentifiers.md5, new BcDigestProvider() {
         public ExtendedDigest get(AlgorithmIdentifier var1) {
            return new MD5Digest();
         }
      });
      var0.put(PKCSObjectIdentifiers.md4, new BcDigestProvider() {
         public ExtendedDigest get(AlgorithmIdentifier var1) {
            return new MD4Digest();
         }
      });
      var0.put(PKCSObjectIdentifiers.md2, new BcDigestProvider() {
         public ExtendedDigest get(AlgorithmIdentifier var1) {
            return new MD2Digest();
         }
      });
      var0.put(CryptoProObjectIdentifiers.gostR3411, new BcDigestProvider() {
         public ExtendedDigest get(AlgorithmIdentifier var1) {
            return new GOST3411Digest();
         }
      });
      var0.put(TeleTrusTObjectIdentifiers.ripemd128, new BcDigestProvider() {
         public ExtendedDigest get(AlgorithmIdentifier var1) {
            return new RIPEMD128Digest();
         }
      });
      var0.put(TeleTrusTObjectIdentifiers.ripemd160, new BcDigestProvider() {
         public ExtendedDigest get(AlgorithmIdentifier var1) {
            return new RIPEMD160Digest();
         }
      });
      var0.put(TeleTrusTObjectIdentifiers.ripemd256, new BcDigestProvider() {
         public ExtendedDigest get(AlgorithmIdentifier var1) {
            return new RIPEMD256Digest();
         }
      });
      return Collections.unmodifiableMap(var0);
   }

   private BcDefaultDigestProvider() {
   }

   public ExtendedDigest get(AlgorithmIdentifier var1) throws OperatorCreationException {
      BcDigestProvider var2 = (BcDigestProvider)lookup.get(var1.getAlgorithm());
      if (var2 == null) {
         throw new OperatorCreationException("cannot recognise digest");
      } else {
         return var2.get(var1);
      }
   }
}
