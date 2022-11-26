package weblogic.jms.common;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Hashtable;
import javax.naming.NamingException;
import javax.security.auth.login.LoginException;
import weblogic.management.ManagementException;
import weblogic.security.SimpleCallbackHandler;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.AuthorizationManager;
import weblogic.security.service.JMSResource;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;
import weblogic.security.utils.ResourceIDDContextWrapper;

public class JMSSecurityHelper {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final AbstractSubject anonymous = SubjectManager.getSubjectManager().getAnonymousSubject();
   private PrincipalAuthenticator pa;
   private AuthorizationManager am;
   private static JMSSecurityHelper securityHelper;
   private static Hashtable destinationMap;

   public JMSSecurityHelper() throws ManagementException {
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("Initializing JMS Security Helper");
      }

      String realmName = "weblogicDEFAULT";
      this.pa = (PrincipalAuthenticator)SecurityServiceManager.getSecurityService(KERNEL_ID, realmName, ServiceType.AUTHENTICATION);
      this.am = (AuthorizationManager)SecurityServiceManager.getSecurityService(KERNEL_ID, realmName, ServiceType.AUTHORIZE);
      if (this.pa == null || this.am == null) {
         throw new RuntimeException("Security Services Unavailable");
      }
   }

   public static synchronized JMSSecurityHelper getSecurityHelper() throws ManagementException {
      if (securityHelper == null) {
         securityHelper = new JMSSecurityHelper();
      }

      return securityHelper;
   }

   public static JMSSecurityHelper getJMSSecurityHelper() {
      return securityHelper;
   }

   public void mapDestinationName(String from, String to) {
      if (destinationMap == null) {
         destinationMap = new Hashtable();
      }

      destinationMap.put(from, to);
   }

   public void unmapDestinationName(String from) {
      if (destinationMap != null) {
         destinationMap.remove(from);
      }
   }

   public PrincipalAuthenticator getPrincipalAuthenticator() {
      return this.pa;
   }

   public AuthorizationManager getAuthorizationManager() {
      return this.am;
   }

   public static boolean authenticate(String username, String password) {
      try {
         return getJMSSecurityHelper().getPrincipalAuthenticator().authenticate(new SimpleCallbackHandler(username, password)) != null;
      } catch (LoginException var3) {
         return false;
      }
   }

   public static void checkPermission(JMSResource resource) throws javax.jms.JMSSecurityException {
      AuthenticatedSubject currentSubject = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
      checkPermission(resource, currentSubject);
   }

   public static AuthenticatedSubject getCurrentSubject() {
      AuthenticatedSubject currentSubject = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
      return currentSubject;
   }

   public static void checkPermission(JMSResource resource, AuthenticatedSubject currentSubject) throws javax.jms.JMSSecurityException {
      if (resource != null) {
         if (JMSDebug.JMSConfig.isDebugEnabled()) {
            JMSDebug.JMSConfig.debug("Creating JMS resource for " + resource.getActionName() + " with   applicationName = " + resource.getApplicationName() + ", moduleName = " + resource.getModule() + " and resource name = " + resource.getResourceName() + " and type = " + resource.getDestinationType());
         }

         if (!getJMSSecurityHelper().getAuthorizationManager().isAccessAllowed(currentSubject, resource, new ResourceIDDContextWrapper())) {
            throw new javax.jms.JMSSecurityException("Access denied to resource: " + resource);
         }
      }
   }

   public static String getSimpleAuthenticatedName() {
      return SubjectUtils.getUsername(getCurrentSubject().getSubject());
   }

   public static AuthenticatedSubject authenticatedSubject(String username, String password) throws LoginException {
      return getJMSSecurityHelper().getPrincipalAuthenticator().authenticate(new SimpleCallbackHandler(username, password));
   }

   public static final Object doAsJNDIOperation(AbstractSubject subject, PrivilegedExceptionAction action) throws NamingException, JMSException {
      try {
         return subject.doAs(KERNEL_ID, action);
      } catch (PrivilegedActionException var4) {
         Exception e = var4.getException();
         if (e instanceof NamingException) {
            throw (NamingException)e;
         } else {
            throw new JMSException(e);
         }
      }
   }

   public static final Object doAs(AbstractSubject subject, PrivilegedExceptionAction action) throws javax.jms.JMSException {
      try {
         return subject.doAs(KERNEL_ID, action);
      } catch (PrivilegedActionException var4) {
         Exception e = var4.getException();
         if (e instanceof javax.jms.JMSException) {
            throw (javax.jms.JMSException)e;
         } else {
            throw new JMSException(e);
         }
      }
   }

   public static final boolean isServerIdentity(AuthenticatedSubject subject) {
      return SecurityServiceManager.isKernelIdentity(subject) || SecurityServiceManager.isServerIdentity(subject);
   }

   public static final AbstractSubject getAnonymousSubject() {
      return anonymous;
   }

   public static void pushSubject(AuthenticatedSubject subject) {
      SubjectManager.getSubjectManager().pushSubject(KERNEL_ID, subject);
   }
}
