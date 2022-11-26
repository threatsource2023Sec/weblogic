package com.bea.security.saml2.providers.registry;

public interface IdPPartner extends Partner {
   String getIdentityProviderNameMapperClassname();

   void setIdentityProviderNameMapperClassname(String var1);

   String getIssuerURI();

   void setIssuerURI(String var1);

   boolean isVirtualUserEnabled();

   void setVirtualUserEnabled(boolean var1);

   boolean isProcessAttributes();

   void setProcessAttributes(boolean var1);

   boolean isWantAssertionsSigned();

   void setWantAssertionsSigned(boolean var1);
}
