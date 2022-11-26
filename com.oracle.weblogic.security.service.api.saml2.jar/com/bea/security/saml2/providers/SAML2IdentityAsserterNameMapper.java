package com.bea.security.saml2.providers;

import weblogic.security.service.ContextHandler;

public interface SAML2IdentityAsserterNameMapper {
   String mapNameInfo(SAML2NameMapperInfo var1, ContextHandler var2);
}
