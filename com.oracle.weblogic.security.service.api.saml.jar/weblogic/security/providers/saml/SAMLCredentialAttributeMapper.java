package weblogic.security.providers.saml;

import java.util.Collection;
import javax.security.auth.Subject;
import weblogic.security.service.ContextHandler;

public interface SAMLCredentialAttributeMapper {
   Collection mapAttributes(Subject var1, ContextHandler var2);
}
