package weblogic.security.providers.saml.registry;

import weblogic.management.utils.InvalidParameterException;

public interface SAMLPartner {
   String getPartnerId();

   boolean isEnabled();

   void setEnabled(boolean var1);

   String getDescription();

   void setDescription(String var1);

   void validate() throws InvalidParameterException;
}
