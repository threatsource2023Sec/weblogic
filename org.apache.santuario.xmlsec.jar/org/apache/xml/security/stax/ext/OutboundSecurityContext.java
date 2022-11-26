package org.apache.xml.security.stax.ext;

import java.util.List;
import org.apache.xml.security.stax.securityToken.SecurityTokenProvider;

public interface OutboundSecurityContext extends SecurityContext {
   void registerSecurityTokenProvider(String var1, SecurityTokenProvider var2);

   SecurityTokenProvider getSecurityTokenProvider(String var1);

   List getRegisteredSecurityTokenProviders();
}
