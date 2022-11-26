package weblogic.security.providers.saml;

import java.util.Collection;
import weblogic.security.service.ContextHandler;

public interface SAMLIdentityAssertionNameMapper {
   String mapNameInfo(SAMLNameMapperInfo var1, ContextHandler var2);

   Collection mapGroupInfo(SAMLNameMapperInfo var1, ContextHandler var2);

   String getGroupAttrName();

   String getGroupAttrNamespace();
}
