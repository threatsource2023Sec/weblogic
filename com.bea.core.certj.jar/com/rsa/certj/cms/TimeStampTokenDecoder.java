package com.rsa.certj.cms;

import com.rsa.jsafe.crypto.FIPS140Context;

/** @deprecated */
public final class TimeStampTokenDecoder extends Decoder {
   /** @deprecated */
   protected TimeStampTokenDecoder(com.rsa.jsafe.cms.Decoder var1, FIPS140Context var2) {
      super(var1, var2);
   }

   /** @deprecated */
   public TimeStampInfo getTimeStampInfo() throws CMSException {
      com.rsa.jsafe.cms.TimeStampTokenDecoder var1 = (com.rsa.jsafe.cms.TimeStampTokenDecoder)this.a;

      try {
         return new TimeStampInfo(var1.getTimeStampInfo());
      } catch (com.rsa.jsafe.cms.CMSException var3) {
         throw new CMSException(var3);
      }
   }
}
