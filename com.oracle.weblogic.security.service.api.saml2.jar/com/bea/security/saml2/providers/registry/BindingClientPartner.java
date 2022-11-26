package com.bea.security.saml2.providers.registry;

import java.security.cert.X509Certificate;

public interface BindingClientPartner extends Partner {
   X509Certificate getTransportLayerClientCert();

   void setTransportLayerClientCert(X509Certificate var1);

   String getClientUsername();

   void setClientUsername(String var1);

   String getClientPasswordEncrypted();

   boolean isClientPasswordSet();

   void setClientPassword(String var1);
}
