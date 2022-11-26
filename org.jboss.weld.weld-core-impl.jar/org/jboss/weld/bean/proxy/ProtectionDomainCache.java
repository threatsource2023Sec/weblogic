package org.jboss.weld.bean.proxy;

import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.ProtectionDomain;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.jboss.weld.bootstrap.api.helpers.AbstractBootstrapService;

public class ProtectionDomainCache extends AbstractBootstrapService {
   private static final Permission ACCESS_DECLARED_MEMBERS_PERMISSION = new RuntimePermission("accessDeclaredMembers");
   private final ConcurrentMap proxyProtectionDomains = new ConcurrentHashMap();

   ProtectionDomain getProtectionDomainForProxy(ProtectionDomain domain) {
      if (domain.getCodeSource() == null) {
         return this.create(domain);
      } else {
         ProtectionDomain proxyProtectionDomain = (ProtectionDomain)this.proxyProtectionDomains.get(domain.getCodeSource());
         if (proxyProtectionDomain == null) {
            proxyProtectionDomain = this.create(domain);
            ProtectionDomain existing = (ProtectionDomain)this.proxyProtectionDomains.putIfAbsent(domain.getCodeSource(), proxyProtectionDomain);
            if (existing != null) {
               proxyProtectionDomain = existing;
            }
         }

         return proxyProtectionDomain;
      }
   }

   private ProtectionDomain create(ProtectionDomain domain) {
      if (domain.implies(ACCESS_DECLARED_MEMBERS_PERMISSION)) {
         return domain;
      } else {
         PermissionCollection permissions = domain.getPermissions();
         PermissionCollection proxyPermissions = new Permissions();
         if (permissions != null) {
            Enumeration permissionElements = permissions.elements();

            while(permissionElements.hasMoreElements()) {
               proxyPermissions.add((Permission)permissionElements.nextElement());
            }
         }

         proxyPermissions.add(ACCESS_DECLARED_MEMBERS_PERMISSION);
         return new ProtectionDomain(domain.getCodeSource(), proxyPermissions);
      }
   }

   public void cleanupAfterBoot() {
      this.proxyProtectionDomains.clear();
   }
}
