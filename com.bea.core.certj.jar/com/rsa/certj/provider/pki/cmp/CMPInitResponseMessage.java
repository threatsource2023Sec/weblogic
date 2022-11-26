package com.rsa.certj.provider.pki.cmp;

import com.rsa.certj.spi.pki.PKIStatusInfo;

/** @deprecated */
public final class CMPInitResponseMessage extends CMPCertResponseCommon {
   protected CMPInitResponseMessage(PKIHeader var1, PKIStatusInfo var2) {
      super(1, var1, var2);
   }
}
