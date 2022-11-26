package org.cryptacular.asn;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.cryptacular.EncodingException;
import org.cryptacular.util.PemUtil;

public class PublicKeyDecoder implements ASN1Decoder {
   public AsymmetricKeyParameter decode(byte[] encoded, Object... args) {
      try {
         return PemUtil.isPem(encoded) ? PublicKeyFactory.createKey(PemUtil.decode(encoded)) : PublicKeyFactory.createKey((new ASN1InputStream(encoded)).readObject().getEncoded());
      } catch (IOException var4) {
         throw new EncodingException("ASN.1 decoding error", var4);
      }
   }
}
