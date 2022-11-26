package weblogic.security.service;

import java.security.AccessController;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Policy;
import java.security.ProtectionDomain;
import weblogic.security.internal.CombiningPermissionCollection;

public final class WLSPolicy extends Policy {
   private Policy original_policy;

   public void init() {
      this.original_policy = (Policy)AccessController.doPrivileged(new SetPolicyAction(this));
      SupplementalPolicyObject.initAppDefaults();
   }

   public PermissionCollection getPermissions(CodeSource codeSource) {
      PermissionCollection basePermissions = this.original_policy.getPermissions(codeSource);
      PermissionCollection extraPermissions = SupplementalPolicyObject.getPolicies(codeSource.getLocation());
      if (basePermissions == null) {
         return extraPermissions;
      } else {
         return (PermissionCollection)(extraPermissions == null ? basePermissions : new CombiningPermissionCollection(basePermissions, false, extraPermissions, true));
      }
   }

   public PermissionCollection getPermissions(ProtectionDomain domain) {
      return this.getPermissions(domain.getCodeSource());
   }

   public boolean implies(ProtectionDomain domain, Permission permission) {
      boolean result = this.original_policy.implies(domain, permission);
      if (!result) {
         PermissionCollection extraPermissions = SupplementalPolicyObject.getPolicies(domain.getCodeSource().getLocation());
         if (extraPermissions != null) {
            result = extraPermissions.implies(permission);
         }
      }

      return result;
   }

   public void refresh() {
      this.original_policy.refresh();
   }
}
