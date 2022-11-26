package com.bea.security.saml2.providers.registry;

import java.io.Serializable;

public interface Partner extends Serializable {
   String ASSERTION_TYPE_BEARER = "urn:oasis:names:tc:SAML:2.0:cm:bearer";
   String ASSERTION_TYPE_HOLDER_OF_KEY = "urn:oasis:names:tc:SAML:2.0:cm:holder-of-key";
   String ASSERTION_TYPE_SENDER_VOUCHES = "urn:oasis:names:tc:SAML:2.0:cm:sender-vouches";

   void setName(String var1);

   String getName();

   boolean isEnabled();

   boolean isNameModified();

   void setEnabled(boolean var1);

   String getDescription();

   void setDescription(String var1);

   String[] getAudienceURIs();

   void setAudienceURIs(String[] var1);
}
