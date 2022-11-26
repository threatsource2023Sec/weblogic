package weblogic.security.service.internal;

import java.security.AccessController;
import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.AdminResource;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.RoleManager;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.utils.ResourceIDDContextWrapper;

public class SubjectRoleDelegateImpl implements SubjectRoleDelegate {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String ADMIN = "Admin";
   private static final String[] ADMIN_ROLE_ARRAY = new String[]{"Admin"};

   public boolean isUserAnAdministrator(Subject subject) {
      checkSubjectNonNull(subject);
      return this.isUserAnAdministrator(AuthenticatedSubject.getFromSubject(subject));
   }

   public boolean isUserAnAdministrator(AuthenticatedSubject subject) {
      return this.isUserInAdminRoles(subject, ADMIN_ROLE_ARRAY);
   }

   public boolean isAdminPrivilegeEscalation(AuthenticatedSubject currentSubject, AuthenticatedSubject requestedSubject) {
      return !SecurityServiceManager.isKernelIdentity(currentSubject) && !this.isUserAnAdministrator(currentSubject) ? this.checkAdminPrivilegeEscalation(currentSubject, requestedSubject, SecurityServiceManager.getContextSensitiveRealmName()) : false;
   }

   public boolean isAdminPrivilegeEscalation(AuthenticatedSubject currentSubject, String principalName, String realmName) {
      if (!SecurityServiceManager.isKernelIdentity(currentSubject) && !this.isUserAnAdministrator(currentSubject)) {
         String realmToUse = realmName != null ? realmName : SecurityServiceManager.getContextSensitiveRealmName();
         PrincipalAuthenticator pa = SecurityServiceManager.getPrincipalAuthenticator(kernelId, realmToUse);
         AuthenticatedSubject requestedSubject = null;

         try {
            requestedSubject = pa.impersonateIdentity(principalName);
         } catch (LoginException var8) {
            throw new IllegalArgumentException("Invalid principal name: " + principalName, var8);
         } catch (Exception var9) {
            throw new IllegalArgumentException("Invalid principal name: " + principalName, var9);
         }

         return this.checkAdminPrivilegeEscalation(currentSubject, requestedSubject, realmToUse);
      } else {
         return false;
      }
   }

   public boolean doesUserHaveAnyAdminRoles(AuthenticatedSubject subject) {
      String realmName = SecurityServiceManager.getAdministrativeRealmName();
      AdminResource resource = new AdminResource("AdminChannel", (String)null, (String)null);
      return SecurityServiceManager.getAuthorizationManager(kernelId, realmName).isAccessAllowed(subject, resource, new ResourceIDDContextWrapper());
   }

   public boolean isUserInAdminRoles(AuthenticatedSubject subject, String[] roleNames) {
      checkSubjectNonNull(subject);
      if (SecurityServiceManager.isKernelIdentity(subject)) {
         return true;
      } else {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         RoleManager rm = SecurityServiceManager.getRoleManager(kernelId, SecurityServiceManager.getAdministrativeRealmName());
         AdminResource res = new AdminResource("Configuration", (String)null, (String)null);
         Map userRoles = rm.getRoles(subject, res, new ResourceIDDContextWrapper());
         int length = roleNames.length;

         for(int i = 0; i < length; ++i) {
            if (SecurityServiceManager.isUserInRole(subject, roleNames[i], userRoles)) {
               return true;
            }
         }

         return false;
      }
   }

   private static void checkSubjectNonNull(Object subject) {
      if (subject == null) {
         throw new AssertionError(SecurityLogger.getIllegalNullSubject());
      }
   }

   private boolean checkAdminPrivilegeEscalation(AuthenticatedSubject currentSubject, AuthenticatedSubject requestedSubject, String realmToUse) {
      String adminRealm = SecurityServiceManager.getAdministrativeRealmName();
      Map userRoles = null;
      boolean isDomainRealm = adminRealm.equals(realmToUse);
      boolean isCurrentSubjectPartitionAdmin = false;
      boolean isRequesteSubjectPartitionAdmin = false;
      boolean isRequestedSubjectDomainAdmin = false;
      RoleManager rm = SecurityServiceManager.getRoleManager(kernelId, realmToUse);
      AdminResource res = new AdminResource("Configuration", (String)null, (String)null);
      if (!isDomainRealm) {
         userRoles = rm.getRoles(currentSubject, res, new ResourceIDDContextWrapper());
         if (SecurityServiceManager.isUserInRole(currentSubject, "Admin", userRoles)) {
            isCurrentSubjectPartitionAdmin = true;
         }
      }

      userRoles = rm.getRoles(requestedSubject, res, new ResourceIDDContextWrapper());
      if (SecurityServiceManager.isUserInRole(requestedSubject, "Admin", userRoles)) {
         isRequesteSubjectPartitionAdmin = !isDomainRealm;
         isRequestedSubjectDomainAdmin = isDomainRealm;
      }

      if (!isDomainRealm) {
         isRequestedSubjectDomainAdmin = this.isUserAnAdministrator(requestedSubject);
      }

      if (!isRequesteSubjectPartitionAdmin && !isRequestedSubjectDomainAdmin) {
         return false;
      } else {
         return !isCurrentSubjectPartitionAdmin || isRequestedSubjectDomainAdmin;
      }
   }
}
