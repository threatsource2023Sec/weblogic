package org.python.bouncycastle.cms;

import java.util.HashSet;
import java.util.Set;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class DefaultCMSSignatureEncryptionAlgorithmFinder implements CMSSignatureEncryptionAlgorithmFinder {
   private static final Set RSA_PKCS1d5 = new HashSet();

   public AlgorithmIdentifier findEncryptionAlgorithm(AlgorithmIdentifier var1) {
      return RSA_PKCS1d5.contains(var1.getAlgorithm()) ? new AlgorithmIdentifier(PKCSObjectIdentifiers.rsaEncryption, DERNull.INSTANCE) : var1;
   }

   static {
      RSA_PKCS1d5.add(PKCSObjectIdentifiers.md2WithRSAEncryption);
      RSA_PKCS1d5.add(PKCSObjectIdentifiers.md4WithRSAEncryption);
      RSA_PKCS1d5.add(PKCSObjectIdentifiers.md5WithRSAEncryption);
      RSA_PKCS1d5.add(PKCSObjectIdentifiers.sha1WithRSAEncryption);
      RSA_PKCS1d5.add(OIWObjectIdentifiers.md4WithRSAEncryption);
      RSA_PKCS1d5.add(OIWObjectIdentifiers.md4WithRSA);
      RSA_PKCS1d5.add(OIWObjectIdentifiers.md5WithRSA);
      RSA_PKCS1d5.add(OIWObjectIdentifiers.sha1WithRSA);
      RSA_PKCS1d5.add(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128);
      RSA_PKCS1d5.add(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160);
      RSA_PKCS1d5.add(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256);
   }
}
