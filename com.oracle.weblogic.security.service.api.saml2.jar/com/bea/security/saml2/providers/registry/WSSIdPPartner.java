package com.bea.security.saml2.providers.registry;

import java.security.cert.X509Certificate;

public interface WSSIdPPartner extends WSSPartner, IdPPartner {
   X509Certificate getAssertionSigningCert();

   void setAssertionSigningCert(X509Certificate var1);
}
