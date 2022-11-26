package com.rsa.certj.cms;

import com.rsa.jsafe.crypto.FIPS140Context;

/** @deprecated */
public final class DigestedDataDecoder extends Decoder {
   DigestedDataDecoder(com.rsa.jsafe.cms.Decoder var1, FIPS140Context var2) {
      super(var1, var2);
   }

   /** @deprecated */
   public boolean verify() throws CMSException {
      com.rsa.jsafe.cms.DigestedDataDecoder var1 = (com.rsa.jsafe.cms.DigestedDataDecoder)this.a;

      try {
         return var1.verify();
      } catch (com.rsa.jsafe.cms.CMSException var3) {
         throw new CMSException(var3);
      }
   }
}
