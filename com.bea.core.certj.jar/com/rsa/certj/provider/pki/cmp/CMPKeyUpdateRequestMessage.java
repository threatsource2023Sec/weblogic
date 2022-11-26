package com.rsa.certj.provider.pki.cmp;

import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.crmf.CertTemplate;
import com.rsa.certj.crmf.Controls;
import com.rsa.certj.crmf.PKIArchiveOptions;

/** @deprecated */
public final class CMPKeyUpdateRequestMessage extends CMPCertRequestCommon {
   /** @deprecated */
   public CMPKeyUpdateRequestMessage(CertTemplate var1) throws InvalidParameterException {
      this(var1, (PKIArchiveOptions)null);
   }

   /** @deprecated */
   public CMPKeyUpdateRequestMessage(CertTemplate var1, PKIArchiveOptions var2) throws InvalidParameterException {
      super(7, var1, (Controls)((Controls)null));
   }
}
