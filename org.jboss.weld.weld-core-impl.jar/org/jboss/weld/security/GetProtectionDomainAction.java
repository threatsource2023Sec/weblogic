package org.jboss.weld.security;

import java.security.PrivilegedAction;
import java.security.ProtectionDomain;

public final class GetProtectionDomainAction implements PrivilegedAction {
   private final Class clazz;

   public GetProtectionDomainAction(Class clazz) {
      this.clazz = clazz;
   }

   public ProtectionDomain run() {
      return this.clazz.getProtectionDomain();
   }
}
