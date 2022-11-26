package com.rsa.certj.provider.revocation;

import com.rsa.certj.cert.CRL;
import java.util.Vector;

/** @deprecated */
public final class CRLEvidence {
   private CRL crl;
   private Vector certList;
   private Vector crlList;

   /** @deprecated */
   public CRLEvidence(CRL var1, Vector var2, Vector var3) {
      this.crl = var1;
      this.certList = var2;
      this.crlList = var3;
   }

   /** @deprecated */
   public CRL getCRL() {
      return this.crl;
   }

   /** @deprecated */
   public Vector getCertList() {
      return this.certList;
   }

   /** @deprecated */
   public Vector getCRLList() {
      return this.crlList;
   }
}
