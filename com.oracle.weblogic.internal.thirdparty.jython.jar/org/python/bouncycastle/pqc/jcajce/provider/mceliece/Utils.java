package org.python.bouncycastle.pqc.jcajce.provider.mceliece;

import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.python.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.util.DigestFactory;

class Utils {
   static AlgorithmIdentifier getDigAlgId(String var0) {
      if (var0.equals("SHA-1")) {
         return new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, DERNull.INSTANCE);
      } else if (var0.equals("SHA-224")) {
         return new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha224, DERNull.INSTANCE);
      } else if (var0.equals("SHA-256")) {
         return new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256, DERNull.INSTANCE);
      } else if (var0.equals("SHA-384")) {
         return new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha384, DERNull.INSTANCE);
      } else if (var0.equals("SHA-512")) {
         return new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha512, DERNull.INSTANCE);
      } else {
         throw new IllegalArgumentException("unrecognised digest algorithm: " + var0);
      }
   }

   static Digest getDigest(AlgorithmIdentifier var0) {
      if (var0.getAlgorithm().equals(OIWObjectIdentifiers.idSHA1)) {
         return DigestFactory.createSHA1();
      } else if (var0.getAlgorithm().equals(NISTObjectIdentifiers.id_sha224)) {
         return DigestFactory.createSHA224();
      } else if (var0.getAlgorithm().equals(NISTObjectIdentifiers.id_sha256)) {
         return DigestFactory.createSHA256();
      } else if (var0.getAlgorithm().equals(NISTObjectIdentifiers.id_sha384)) {
         return DigestFactory.createSHA384();
      } else if (var0.getAlgorithm().equals(NISTObjectIdentifiers.id_sha512)) {
         return DigestFactory.createSHA512();
      } else {
         throw new IllegalArgumentException("unrecognised OID in digest algorithm identifier: " + var0.getAlgorithm());
      }
   }
}
