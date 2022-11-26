package com.bea.security.saml2.providers.registry;

public interface WebSSOIdPPartner extends WebSSOPartner, IdPPartner {
   boolean isWantAuthnRequestsSigned();

   void setWantAuthnRequestsSigned(boolean var1);

   String[] getRedirectURIs();

   void setRedirectURIs(String[] var1);

   Endpoint[] getSingleSignOnService();

   void setSingleSignOnService(Endpoint[] var1);
}
