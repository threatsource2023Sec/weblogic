package weblogic.security.providers.saml;

import javax.security.auth.Subject;
import weblogic.security.service.ContextHandler;

public interface SAMLCredentialNameMapper {
   void setNameQualifier(String var1);

   SAMLNameMapperInfo mapSubject(Subject var1, ContextHandler var2);

   SAMLNameMapperInfo mapName(String var1, ContextHandler var2);
}
