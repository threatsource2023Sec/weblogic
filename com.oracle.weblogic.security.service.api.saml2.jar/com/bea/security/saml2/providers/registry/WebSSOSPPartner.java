package com.bea.security.saml2.providers.registry;

import java.security.cert.X509Certificate;
import java.util.List;

public interface WebSSOSPPartner extends WebSSOPartner, SPPartner {
   boolean isWantAuthnRequestsSigned();

   void setWantAuthnRequestsSigned(boolean var1);

   IndexedEndpoint[] getAssertionConsumerService();

   void setAssertionConsumerService(IndexedEndpoint[] var1);

   X509Certificate getAssertionEncryptionCert();

   void setAssertionEncryptionCert(X509Certificate var1);

   List getEncryptionAlgorithms();

   void setEncryptionAlgorithms(List var1);
}
