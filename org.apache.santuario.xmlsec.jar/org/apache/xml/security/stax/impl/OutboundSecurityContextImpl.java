package org.apache.xml.security.stax.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.xml.security.stax.ext.OutboundSecurityContext;
import org.apache.xml.security.stax.securityToken.SecurityTokenProvider;

public class OutboundSecurityContextImpl extends AbstractSecurityContextImpl implements OutboundSecurityContext {
   private final Map securityTokenProviders = new HashMap();

   public void registerSecurityTokenProvider(String id, SecurityTokenProvider securityTokenProvider) {
      if (id == null) {
         throw new IllegalArgumentException("Id must not be null");
      } else {
         this.securityTokenProviders.put(id, securityTokenProvider);
      }
   }

   public SecurityTokenProvider getSecurityTokenProvider(String id) {
      return (SecurityTokenProvider)this.securityTokenProviders.get(id);
   }

   public List getRegisteredSecurityTokenProviders() {
      return new ArrayList(this.securityTokenProviders.values());
   }
}
