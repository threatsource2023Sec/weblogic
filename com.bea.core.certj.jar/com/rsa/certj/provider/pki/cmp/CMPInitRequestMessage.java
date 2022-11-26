package com.rsa.certj.provider.pki.cmp;

import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.crmf.CertTemplate;
import com.rsa.certj.crmf.Control;
import com.rsa.certj.crmf.PKIArchiveOptions;

/** @deprecated */
public final class CMPInitRequestMessage extends CMPCertRequestCommon {
   /** @deprecated */
   public CMPInitRequestMessage(CertTemplate var1) throws InvalidParameterException {
      this(var1, (PKIArchiveOptions)null);
   }

   /** @deprecated */
   public CMPInitRequestMessage(CertTemplate var1, PKIArchiveOptions var2) throws InvalidParameterException {
      super(0, var1, (Control)var2);
   }
}
