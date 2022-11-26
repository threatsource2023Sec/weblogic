package com.bea.security.saml2.providers.registry;

import java.security.cert.X509Certificate;

public interface WebSSOPartner extends MetadataPartner, BindingClientPartner {
   IndexedEndpoint[] getArtifactResolutionService();

   X509Certificate getSSOSigningCert();

   void setSSOSigningCert(X509Certificate var1);

   void setArtifactResolutionService(IndexedEndpoint[] var1);

   boolean isArtifactBindingUsePOSTMethod();

   void setArtifactBindingUsePOSTMethod(boolean var1);

   String getArtifactBindingPostForm();

   void setArtifactBindingPostForm(String var1);

   boolean isWantArtifactRequestSigned();

   void setWantArtifactRequestSigned(boolean var1);

   String getPostBindingPostForm();

   void setPostBindingPostForm(String var1);
}
