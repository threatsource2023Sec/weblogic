package com.bea.security.saml2.providers;

import javax.security.auth.Subject;
import weblogic.security.service.ContextHandler;

public interface SAML2CredentialNameMapper {
   void setNameQualifier(String var1);

   SAML2NameMapperInfo mapSubject(Subject var1, ContextHandler var2);

   SAML2NameMapperInfo mapName(String var1, ContextHandler var2);
}
