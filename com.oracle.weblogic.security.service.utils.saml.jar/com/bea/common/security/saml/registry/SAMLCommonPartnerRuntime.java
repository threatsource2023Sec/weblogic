package com.bea.common.security.saml.registry;

public interface SAMLCommonPartnerRuntime {
   String DEFAULT_TARGET = "default";
   String WILDCARD_CHAR = "*";

   int getProfileId();

   String getProfileConfMethodName();

   String getProfileConfMethodURN();

   boolean isWildcardTarget();

   boolean isDefaultTarget();

   String getARSPassword();

   String[] getAudienceURIs();
}
