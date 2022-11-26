package org.cryptacular.asn;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo;
import org.bouncycastle.asn1.pkcs.PBEParameter;
import org.bouncycastle.asn1.pkcs.PBES2Parameters;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.cryptacular.EncodingException;
import org.cryptacular.pbe.EncryptionScheme;
import org.cryptacular.pbe.PBES1Algorithm;
import org.cryptacular.pbe.PBES1EncryptionScheme;
import org.cryptacular.pbe.PBES2EncryptionScheme;

public class PKCS8PrivateKeyDecoder extends AbstractPrivateKeyDecoder {
   protected byte[] decryptKey(byte[] encrypted, char[] password) {
      EncryptedPrivateKeyInfo ki = EncryptedPrivateKeyInfo.getInstance(this.tryConvertPem(encrypted));
      AlgorithmIdentifier alg = ki.getEncryptionAlgorithm();
      Object scheme;
      if (PKCSObjectIdentifiers.id_PBES2.equals(alg.getAlgorithm())) {
         scheme = new PBES2EncryptionScheme(PBES2Parameters.getInstance(alg.getParameters()), password);
      } else {
         scheme = new PBES1EncryptionScheme(PBES1Algorithm.fromOid(alg.getAlgorithm().getId()), PBEParameter.getInstance(alg.getParameters()), password);
      }

      return ((EncryptionScheme)scheme).decrypt(ki.getEncryptedData());
   }

   protected AsymmetricKeyParameter decodeASN1(byte[] encoded) {
      try {
         return PrivateKeyFactory.createKey((new ASN1InputStream(encoded)).readObject().getEncoded());
      } catch (IOException var3) {
         throw new EncodingException("ASN.1 decoding error", var3);
      }
   }
}
