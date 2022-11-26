package weblogic.rmi.facades;

import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import org.jvnet.hk2.annotations.Service;
import weblogic.rmi.client.facades.RmiClientSecurityFacade;
import weblogic.rmi.client.facades.RmiClientSecurityFacadeDelegateImpl;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.security.service.CredentialManager;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.SecurityService;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.utils.LocatorUtilities;

public abstract class RmiSecurityFacade extends RmiClientSecurityFacade {
   public static String getDefaultRealm() {
      return getInstance().doGetDefaultRealm();
   }

   public static PrincipalAuthenticator getPrincipalAuthenticator(AuthenticatedSubject kernelId, String realmName) {
      return getInstance().doGetPrincipalAuthenticator(kernelId, realmName);
   }

   private static RmiSecurityFacadeDelegate getInstance() {
      return RmiSecurityFacade.RmiSecurityFacadeInitializer.instance;
   }

   public static boolean isSecurityServiceInitialized() {
      return getInstance().doIsSecurityServiceInitialized();
   }

   public static AuthenticatedSubject getAnonymousSubject() {
      return getInstance().doGetAnonymousSubject();
   }

   public static boolean isUserAnAdministrator(AuthenticatedSubject subject) {
      return getInstance().doIsUserAnAdministrator(subject);
   }

   public static CredentialManager getCredentialManager(AuthenticatedSubject kernelId, String realmName) {
      return getInstance().doGetCredentialManager(kernelId, realmName);
   }

   public static String getSecurityRealmName(AuthenticatedSubject kernelId) {
      return RmiInvocationFacade.isGlobalPartition(kernelId) ? "weblogicDEFAULT" : RmiInvocationFacade.getCurrentPartitionName(kernelId);
   }

   public static AuthenticatedUser convertToAuthenticatedUser(AuthenticatedSubject subject) {
      return getInstance().doConvertToAuthenticatedUser(subject);
   }

   public static AuthenticatedSubject getASFromAUInServerOrClient(AuthenticatedUser user) {
      return getInstance().doGetASFromAUInServerOrClient(user);
   }

   public static AuthenticatedSubject sendASToWire(AuthenticatedSubject subject) {
      return getInstance().doSendASToWire(subject);
   }

   public static boolean hasAdminRoles(AuthenticatedSubject subject) {
      return getInstance().doDoesUserHaveAnyAdminRoles(subject);
   }

   public static Object runAs(AuthenticatedSubject kernelId, AuthenticatedSubject subject, PrivilegedExceptionAction action) throws PrivilegedActionException {
      return getInstance().doRunAs(kernelId, subject, action);
   }

   @Service
   private static class FFSecurityFacade extends RmiClientSecurityFacadeDelegateImpl implements RmiSecurityFacadeDelegate {
      public String doGetDefaultRealm() {
         return "weblogicDEFAULT";
      }

      public PrincipalAuthenticator doGetPrincipalAuthenticator(AuthenticatedSubject kernelId, String realmName) {
         return (PrincipalAuthenticator)getSecurityService(kernelId, realmName, ServiceType.AUTHENTICATION);
      }

      private static SecurityService getSecurityService(AuthenticatedSubject kernelId, String realmName, SecurityService.ServiceType serviceType) {
         return SecurityServiceManager.getSecurityService(kernelId, realmName, serviceType);
      }

      public boolean doIsKernelIdentity(AuthenticatedSubject subject) {
         return SecurityServiceManager.isKernelIdentity(subject);
      }

      public boolean doIsSecurityServiceInitialized() {
         return SecurityServiceManager.isSecurityServiceInitialized();
      }

      public boolean doIsUserAnAdministrator(AuthenticatedSubject subject) {
         return SubjectUtils.isUserAnAdministrator(subject);
      }

      public CredentialManager doGetCredentialManager(AuthenticatedSubject kernelId, String realmName) {
         return (CredentialManager)getSecurityService(kernelId, realmName, ServiceType.CREDENTIALMANAGER);
      }

      public AuthenticatedUser doConvertToAuthenticatedUser(AuthenticatedSubject subject) {
         return SecurityServiceManager.convertToAuthenticatedUser(subject);
      }

      public AuthenticatedSubject doSendASToWire(AuthenticatedSubject subject) {
         return SecurityServiceManager.sendASToWire(subject);
      }

      public AuthenticatedSubject doGetASFromAUInServerOrClient(AuthenticatedUser user) {
         return SecurityServiceManager.getASFromAUInServerOrClient(user);
      }

      public boolean doDoesUserHaveAnyAdminRoles(AuthenticatedSubject subject) {
         return SubjectUtils.doesUserHaveAnyAdminRoles(subject);
      }

      public Object doRunAs(AuthenticatedSubject kernelId, AuthenticatedSubject subject, PrivilegedExceptionAction action) throws PrivilegedActionException {
         return SecurityServiceManager.runAs(kernelId, subject, action);
      }
   }

   private static final class RmiSecurityFacadeInitializer {
      private static final RmiSecurityFacadeDelegate instance = (RmiSecurityFacadeDelegate)LocatorUtilities.getService(RmiSecurityFacadeDelegate.class);
   }
}
