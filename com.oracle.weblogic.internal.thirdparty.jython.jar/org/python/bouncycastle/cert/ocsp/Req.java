package org.python.bouncycastle.cert.ocsp;

import org.python.bouncycastle.asn1.ocsp.Request;
import org.python.bouncycastle.asn1.x509.Extensions;

public class Req {
   private Request req;

   public Req(Request var1) {
      this.req = var1;
   }

   public CertificateID getCertID() {
      return new CertificateID(this.req.getReqCert());
   }

   public Extensions getSingleRequestExtensions() {
      return this.req.getSingleRequestExtensions();
   }
}
