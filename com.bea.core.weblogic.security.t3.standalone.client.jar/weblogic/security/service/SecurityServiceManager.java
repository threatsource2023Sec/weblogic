package weblogic.security.service;

import com.bea.core.security.managers.CEO;
import com.bea.core.security.managers.NotInitializedException;
import com.bea.core.security.managers.NotSupportedException;
import java.util.Map;
import java.util.Set;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.AuthenticatedUser;

public final class SecurityServiceManager extends SecurityManager {
   public static final String defaultRealmName = "weblogicDEFAULT";
   private static AuthenticatedSubject kernelIdentity = getKernelIdentity();

   public SecurityServiceManager(AuthenticatedSubject kernelID) {
   }

   public static boolean isSecurityServiceInitialized() {
      try {
         CEO.getManager();
         return true;
      } catch (NotInitializedException var1) {
         return false;
      }
   }

   public static SecurityService getSecurityService(AuthenticatedSubject kernelID, String realmName, SecurityService.ServiceType type) throws InvalidParameterException, NotYetInitializedException {
      if (SecurityService.ServiceType.AUDIT.equals(type)) {
         throw new NotSupportedException();
      } else if (SecurityService.ServiceType.AUTHENTICATION.equals(type)) {
         return new PrincipalAuthenticator();
      } else if (SecurityService.ServiceType.AUTHORIZE.equals(type)) {
         throw new NotSupportedException();
      } else if (SecurityService.ServiceType.BULKAUTHORIZE.equals(type)) {
         throw new NotSupportedException();
      } else if (SecurityService.ServiceType.BULKROLE.equals(type)) {
         throw new NotSupportedException();
      } else if (SecurityService.ServiceType.CERTPATH.equals(type)) {
         throw new NotSupportedException();
      } else if (SecurityService.ServiceType.CREDENTIALMANAGER.equals(type)) {
         throw new NotSupportedException();
      } else if (SecurityService.ServiceType.KEYMANAGER.equals(type)) {
         throw new NotSupportedException();
      } else if (SecurityService.ServiceType.PROFILE.equals(type)) {
         throw new NotSupportedException();
      } else if (SecurityService.ServiceType.ROLE.equals(type)) {
         throw new NotSupportedException();
      } else {
         throw new InvalidParameterException();
      }
   }

   public static PrincipalAuthenticator getPrincipalAuthenticator(AuthenticatedSubject kernelID, String realmName) throws InvalidParameterException, NotYetInitializedException {
      return (PrincipalAuthenticator)getSecurityService(kernelID, realmName, SecurityService.ServiceType.AUTHENTICATION);
   }

   public static SecurityService getBulkAuthorizationManager(AuthenticatedSubject kernelID, String realmName) throws InvalidParameterException, NotYetInitializedException {
      return getSecurityService(kernelID, realmName, SecurityService.ServiceType.BULKAUTHORIZE);
   }

   public static SecurityService getBulkRoleManager(AuthenticatedSubject kernelID, String realmName) throws InvalidParameterException, NotYetInitializedException {
      return getSecurityService(kernelID, realmName, SecurityService.ServiceType.BULKROLE);
   }

   public static boolean doesRealmExist(String realmName) throws InvalidParameterException, NotYetInitializedException {
      throw new NotSupportedException();
   }

   public static AuthenticatedSubject getASFromAU(AuthenticatedUser user) {
      if (user == null) {
         return SubjectUtils.getAnonymousSubject();
      } else if (user instanceof AuthenticatedSubject) {
         return getASFromWire((AuthenticatedSubject)user);
      } else {
         AuthenticatedSubject subject = null;
         subject = new AuthenticatedSubject(user);
         subject.getPublicCredentials().add(user);
         return subject;
      }
   }

   public static AuthenticatedSubject getSealedSubjectFromWire(AuthenticatedSubject kernelId, AuthenticatedUser user) {
      AuthenticatedSubject subject = getASFromAU(user);
      return subject;
   }

   public static AuthenticatedSubject getASFromAUInServerOrClient(AuthenticatedUser user) {
      throw new NotSupportedException();
   }

   private static AuthenticatedSubject getASFromAUInServer(AuthenticatedUser user) {
      throw new NotSupportedException();
   }

   public static AuthenticatedSubject getASFromWire(AuthenticatedSubject as) {
      return as;
   }

   public static AuthenticatedSubject sendASToWire(AuthenticatedSubject as) {
      return as;
   }

   public static AuthenticatedUser convertToAuthenticatedUser(AuthenticatedUser user) {
      throw new NotSupportedException();
   }

   private static AuthenticatedUser getAuthenticatedUserFromPrincipals(Set principals) {
      throw new NotSupportedException();
   }

   public static boolean isFullAuthorizationDelegationRequired(String realmName, SecurityApplicationInfo appInfo) {
      throw new NotSupportedException();
   }

   public static AuthenticatedSubject getServerIdentity(AuthenticatedSubject kernelID) {
      throw new NotSupportedException();
   }

   public static boolean isTrustedServerIdentity(AuthenticatedSubject id) {
      throw new NotSupportedException();
   }

   public static AuthenticatedSubject seal(AuthenticatedSubject kernelID, AuthenticatedSubject as) {
      throw new NotSupportedException();
   }

   public void initialize(AuthenticatedSubject kernelID) {
      throw new NotSupportedException();
   }

   public void stop() {
      throw new NotSupportedException();
   }

   /** @deprecated */
   @Deprecated
   public static boolean isAnonymousAdminLookupEnabled() {
      throw new NotSupportedException();
   }

   public static boolean getEnforceStrictURLPattern() {
      throw new NotSupportedException();
   }

   public static boolean getEnforceValidBasicAuthCredentials() {
      throw new NotSupportedException();
   }

   public static AuthenticatedSubject getCurrentSubjectForWire(AuthenticatedSubject kernelID) {
      AuthenticatedSubject as = getCurrentSubject(kernelID);
      return sendASToWire(as);
   }

   public static boolean isKernelIdentity(AuthenticatedSubject s) {
      throw new NotSupportedException();
   }

   public static boolean isServerIdentity(AuthenticatedSubject s) {
      return false;
   }

   public static void checkKernelIdentity(AuthenticatedSubject s) {
      throw new NotSupportedException();
   }

   public static boolean isUserInRole(AuthenticatedSubject user, String role, Map userRoles) {
      throw new NotSupportedException();
   }

   public static String getDefaultRealmName() {
      throw new NotSupportedException();
   }

   public static boolean isApplicationVersioningSupported(String realmName) {
      throw new NotSupportedException();
   }

   public static void initJava2Security() {
      throw new NotSupportedException();
   }

   public static boolean isJACCEnabled() {
      throw new NotSupportedException();
   }
}
