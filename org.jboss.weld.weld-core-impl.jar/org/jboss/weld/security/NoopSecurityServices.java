package org.jboss.weld.security;

import java.security.Principal;
import org.jboss.weld.security.spi.SecurityServices;

public class NoopSecurityServices implements SecurityServices {
   public static final SecurityServices INSTANCE = new NoopSecurityServices();

   private NoopSecurityServices() {
   }

   public Principal getPrincipal() {
      throw new UnsupportedOperationException();
   }

   public void cleanup() {
   }
}
