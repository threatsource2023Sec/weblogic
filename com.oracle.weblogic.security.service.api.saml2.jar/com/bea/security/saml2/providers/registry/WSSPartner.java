package com.bea.security.saml2.providers.registry;

public interface WSSPartner extends Partner {
   String getConfirmationMethod();

   void setConfirmationMethod(String var1);
}
