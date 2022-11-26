package weblogic.diagnostics.utils;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Map;
import weblogic.logging.Loggable;
import weblogic.management.ManagementLogger;
import weblogic.management.NoAccessRuntimeException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.AdminResource;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.RoleManager;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.security.utils.ResourceIDDContextWrapper;

public class SecurityHelper {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static RoleManager roleManager;
   private static AdminResource adminMBeanResource = new AdminResource("Configuration", (String)null, (String)null);
   static final String ADMIN_ROLENAME = "Admin";
   static final String OPERATOR_ROLENAME = "Operator";
   static final String DEPLOYER_ROLENAME = "Deployer";
   static final String[] ALL_ADMIN_ROLES = new String[]{"Admin", "Operator", "Deployer"};

   private SecurityHelper() {
   }

   public static void checkForAdminRole() {
      checkForRole("Admin");
   }

   public static void checkAnyAdminRole() {
      checkForAnyRole(ALL_ADMIN_ROLES);
   }

   public static void checkKernelAccess() {
      AuthenticatedSubject curSubject = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
      if (!SecurityServiceManager.isKernelIdentity(curSubject)) {
         Loggable l = ManagementLogger.logNoAccessForSubjectRoleLoggable(curSubject.toString(), (String)null);
         throw new NoAccessRuntimeException(l.getMessage());
      }
   }

   public static void checkForRole(String roleName) {
      String[] roles = new String[]{roleName};
      checkForAnyRole(roles);
   }

   public static void checkForAnyRole(final String[] roleNames) {
      AuthenticatedSubject curSubject = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
      if (!SecurityServiceManager.isKernelIdentity(curSubject)) {
         final AuthenticatedSubject subject = SecurityServiceManager.seal(KERNEL_ID, curSubject);
         Boolean hasRole = (Boolean)SecurityServiceManager.runAs(KERNEL_ID, KERNEL_ID, new PrivilegedAction() {
            public Boolean run() {
               Map roles = SecurityHelper.getRoleManager().getRoles(subject, SecurityHelper.adminMBeanResource, new ResourceIDDContextWrapper());
               return SecurityHelper.isAllowedRole(roleNames, roles);
            }
         });
         if (!hasRole) {
            Loggable l = ManagementLogger.logNoAccessForSubjectRoleLoggable(subject.toString(), Arrays.toString(roleNames));
            throw new NoAccessRuntimeException(l.getMessage());
         }
      }
   }

   private static RoleManager getRoleManager() {
      return roleManager != null ? roleManager : (roleManager = (RoleManager)SecurityServiceManager.getSecurityService(KERNEL_ID, "weblogicDEFAULT", ServiceType.ROLE));
   }

   static Boolean isAllowedRole(String[] rolesToCheckFor, Map availableRoles) {
      if (availableRoles != null) {
         if (availableRoles.get("Admin") != null) {
            return Boolean.TRUE;
         }

         String[] var2 = rolesToCheckFor;
         int var3 = rolesToCheckFor.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String role = var2[var4];
            if (availableRoles.get(role) != null) {
               return Boolean.TRUE;
            }
         }
      }

      return Boolean.FALSE;
   }
}
