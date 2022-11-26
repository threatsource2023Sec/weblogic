package com.bea.security.saml2.providers.registry;

public interface MetadataPartner extends Partner {
   String getEntityID();

   void setEntityID(String var1);

   String getContactPersonGivenName();

   void setContactPersonGivenName(String var1);

   String getContactPersonSurName();

   void setContactPersonSurName(String var1);

   String getContactPersonType();

   void setContactPersonType(String var1);

   String getContactPersonCompany();

   void setContactPersonCompany(String var1);

   String getContactPersonTelephoneNumber();

   void setContactPersonTelephoneNumber(String var1);

   String getContactPersonEmailAddress();

   void setContactPersonEmailAddress(String var1);

   String getOrganizationName();

   void setOrganizationName(String var1);

   String getOrganizationURL();

   void setOrganizationURL(String var1);

   String getErrorURL();

   void setErrorURL(String var1);
}
