package weblogic.security.providers.saml;

import java.util.Collection;
import weblogic.security.service.ContextHandler;

public interface SAMLIdentityAssertionAttributeMapper {
   void mapAttributeInfo(Collection var1, ContextHandler var2);
}
