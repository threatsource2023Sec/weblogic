package com.rsa.certj.provider.pki.cmp;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.certj.spi.pki.PKIStatusInfo;

/** @deprecated */
public final class CMPConfirmMessage extends CMPResponseCommon {
   CMPConfirmMessage(PKIHeader var1, byte[] var2, int var3) throws CMPException {
      super(19, var1, (PKIStatusInfo)null);

      try {
         EncodedContainer var4 = new EncodedContainer(10551319);
         ASN1Container[] var5 = new ASN1Container[]{var4};
         ASN1.berDecode(var2, var3, var5);
      } catch (ASN_Exception var6) {
         throw new CMPException("CMPConfirmMessage.CMPConfirmMessage: unable to decode pkiConf(", var6);
      }
   }

   static CMPConfirmMessage berDecodeBody(PKIHeader var0, byte[] var1, int var2) throws CMPException {
      return new CMPConfirmMessage(var0, var1, var2);
   }
}
