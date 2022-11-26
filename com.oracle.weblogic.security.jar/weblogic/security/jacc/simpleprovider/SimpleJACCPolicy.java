package weblogic.security.jacc.simpleprovider;

import java.security.AccessController;
import java.security.CodeSource;
import java.security.NoSuchAlgorithmException;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Policy;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import javax.security.jacc.PolicyContext;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.security.SecurityLogger;

public final class SimpleJACCPolicy extends Policy {
   private Policy defaultPolicy;
   private static DebugLogger jaccDebugLogger = DebugLogger.getDebugLogger("DebugSecurityJACCPolicy");

   public SimpleJACCPolicy() {
      if (jaccDebugLogger.isDebugEnabled()) {
         this.log("SimpleJACCPolicy no arg constructor");
      }

      try {
         this.defaultPolicy = (Policy)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws PrivilegedActionException {
               try {
                  return Policy.getInstance("JavaPolicy", (Policy.Parameters)null);
               } catch (NoSuchAlgorithmException var2) {
                  throw new PrivilegedActionException(var2);
               } catch (Exception var3) {
                  throw new PrivilegedActionException(var3);
               }
            }
         });
      } catch (PrivilegedActionException var2) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("Failed to create a policy instance: " + var2.getException());
         }

         throw new RuntimeException(SecurityLogger.getUnableToCreatePolicyInstance("JavaPolicy", var2.getException()), var2.getException());
      }
   }

   public PermissionCollection getPermissions(CodeSource codeSource) {
      PermissionCollection permCol = null;
      Policy policy = null;
      String contextId = PolicyContext.getContextID();
      policy = this.getPolicyConfigurationPolicyForContext(contextId);
      PermissionCollection policyPerms = policy.getPermissions(codeSource);
      permCol = this.removeExcludedPermissions(contextId, policyPerms);
      return permCol;
   }

   public PermissionCollection getPermissions(ProtectionDomain domain) {
      PermissionCollection permCol = null;
      Policy policy = null;
      String contextId = PolicyContext.getContextID();
      policy = this.getPolicyConfigurationPolicyForContext(contextId);
      PermissionCollection policyPerms = policy.getPermissions(domain);
      permCol = this.removeExcludedPermissions(contextId, policyPerms);
      return permCol;
   }

   public boolean implies(ProtectionDomain domain, Permission permission) {
      if (jaccDebugLogger.isDebugEnabled()) {
         this.log("SimpleJACCPolicy.implies " + permission);
      }

      String contextId = PolicyContext.getContextID();
      Policy pcPolicy = this.getPolicyConfigurationPolicyForContext(contextId);
      Permissions excludedPermissions = this.getExcludedPermissionsForContext(contextId);
      boolean canAccess = pcPolicy.implies(domain, permission);
      if (jaccDebugLogger.isDebugEnabled()) {
         this.log("SimpleJACCPolicy.implies " + (!canAccess ? "denied " : "granted") + " policy: " + (pcPolicy == this.defaultPolicy ? "default" : (contextId == null ? " null" : contextId)) + " " + permission);
      }

      if (canAccess && excludedPermissions != null) {
         if (this.shouldExclude(permission, excludedPermissions)) {
            canAccess = false;
         }

         if (jaccDebugLogger.isDebugEnabled()) {
            this.log("SimpleJACCPolicy.implies " + (!canAccess ? "denied " : "granted") + permission + " after applying excluded Permissions");
         }
      }

      return canAccess;
   }

   public void refresh() {
      if (jaccDebugLogger.isDebugEnabled()) {
         this.log("SimpleJACCPolicy.refresh");
      }

      this.defaultPolicy.refresh();
      Collection coll = PolicyConfigurationFactoryImpl.getPolicyConfigurationImpls();
      if (coll != null) {
         Iterator it = coll.iterator();

         while(it.hasNext()) {
            PolicyConfigurationImpl pci = (PolicyConfigurationImpl)it.next();
            if (pci != null) {
               pci.refresh();
            }
         }
      }

   }

   private Policy getPolicyConfigurationPolicyForContext(String contextId) {
      Policy policy = null;
      if (contextId != null) {
         PolicyConfigurationImpl pci = PolicyConfigurationFactoryImpl.getPolicyConfigurationImpl(contextId);
         if (pci != null) {
            policy = pci.getPolicy();
         }
      }

      if (policy == null) {
         policy = this.defaultPolicy;
      }

      return policy;
   }

   private Permissions getExcludedPermissionsForContext(String contextId) {
      Permissions perms = null;
      if (contextId != null) {
         PolicyConfigurationImpl pci = PolicyConfigurationFactoryImpl.getPolicyConfigurationImpl(contextId);
         if (pci != null) {
            perms = pci.getExcludedPermissions();
         }
      }

      return perms;
   }

   private PermissionCollection removeExcludedPermissions(String contextId, PermissionCollection grantedPerms) {
      boolean someExcluded = false;
      Permissions excludedPermissions = this.getExcludedPermissionsForContext(contextId);
      PermissionCollection resultingPermCol = null;
      Permission granted = null;
      if (excludedPermissions != null && excludedPermissions.elements().hasMoreElements()) {
         Enumeration e = grantedPerms.elements();

         while(e.hasMoreElements()) {
            granted = (Permission)e.nextElement();
            if (!this.shouldExclude(granted, excludedPermissions)) {
               if (resultingPermCol == null) {
                  resultingPermCol = new Permissions();
               }

               ((PermissionCollection)resultingPermCol).add(granted);
            } else {
               someExcluded = true;
            }
         }
      }

      if (!someExcluded) {
         resultingPermCol = grantedPerms;
      }

      return (PermissionCollection)resultingPermCol;
   }

   private boolean shouldExclude(Permission granted, Permissions excludedPermissions) {
      boolean shouldExclude = false;
      if (excludedPermissions != null && excludedPermissions.elements().hasMoreElements()) {
         if (!excludedPermissions.implies(granted)) {
            Enumeration e = excludedPermissions.elements();

            while(e.hasMoreElements() || shouldExclude) {
               Permission excludedPerm = (Permission)e.nextElement();
               if (granted.implies(excludedPerm)) {
                  shouldExclude = true;
                  this.log("SimpleJACCPolicy excluding granted: " + granted + " implies: " + excludedPerm);
                  break;
               }
            }
         } else {
            shouldExclude = true;
            this.log("SimpleJACCPolicy excluding excludedPermissions implies: " + granted);
         }
      }

      return shouldExclude;
   }

   private void log(String msg) {
      System.out.println(msg);
   }
}
