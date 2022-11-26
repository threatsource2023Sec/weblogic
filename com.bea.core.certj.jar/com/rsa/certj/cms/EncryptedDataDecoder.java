package com.rsa.certj.cms;

import com.rsa.certj.cert.X501Attributes;
import com.rsa.jsafe.JSAFE_SecretKey;
import com.rsa.jsafe.crypto.FIPS140Context;
import com.rsa.jsafe.provider.JsafeJCE;

/** @deprecated */
public final class EncryptedDataDecoder extends Decoder {
   EncryptedDataDecoder(com.rsa.jsafe.cms.Decoder var1, FIPS140Context var2) {
      super(var1, var2);
   }

   /** @deprecated */
   public X501Attributes getUnprotectedAttributes() throws CMSException {
      com.rsa.jsafe.cms.EncryptedDataDecoder var1 = (com.rsa.jsafe.cms.EncryptedDataDecoder)this.a;

      try {
         return com.rsa.certj.cms.a.a(var1.getUnprotectedAttributes());
      } catch (com.rsa.jsafe.cms.CMSException var3) {
         throw new CMSException(var3);
      }
   }

   /** @deprecated */
   public void setSecretKey(JSAFE_SecretKey var1) throws CMSException {
      com.rsa.jsafe.cms.EncryptedDataDecoder var2 = (com.rsa.jsafe.cms.EncryptedDataDecoder)this.a;
      JsafeJCE var3 = com.rsa.certj.cms.a.a(this.b);
      var2.setSecretKey(com.rsa.certj.cms.a.a(var1, var3));
   }
}
