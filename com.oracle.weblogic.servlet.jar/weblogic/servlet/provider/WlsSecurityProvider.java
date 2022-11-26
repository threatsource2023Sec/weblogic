package weblogic.servlet.provider;

import java.io.IOException;
import java.security.AccessController;
import java.security.PermissionCollection;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.util.Map;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.security.jacc.PolicyContext;
import javax.security.jacc.PolicyContextException;
import javax.security.jacc.PolicyContextHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.security.RealmMBean;
import weblogic.rjvm.LocalRJVM;
import weblogic.security.Salt;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.jacc.CommonPolicyContextHandler;
import weblogic.security.jacc.DelegatingPolicyContextHandler;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceException;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SupplementalPolicyObject;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.security.utils.SSLCertUtility;
import weblogic.security.utils.SSLCipherUtility;
import weblogic.security.utils.SSLSetup;
import weblogic.servlet.security.internal.WebAppContextHandler;
import weblogic.servlet.security.internal.WebAppContextHandlerData;
import weblogic.servlet.spi.SecurityProvider;
import weblogic.servlet.spi.SubjectHandle;
import weblogic.servlet.spi.WebServerRegistry;

public class WlsSecurityProvider implements SecurityProvider {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final SubjectHandle KERNEL_HANDLE;
   private static final SubjectHandle ANON_HANDLE;

   public final boolean getEnforceStrictURLPattern() {
      return SecurityServiceManager.getEnforceStrictURLPattern();
   }

   public final boolean getEnforceValidBasicAuthCredentials() {
      return SecurityServiceManager.getEnforceValidBasicAuthCredentials();
   }

   public void pushSubject(SubjectHandle subject) {
      SecurityServiceManager.checkKernelPermission();
      SecurityServiceManager.pushSubject(KERNEL_ID, toAuthSubject(subject));
   }

   public void popSubject() {
      SecurityServiceManager.checkKernelPermission();
      SecurityServiceManager.popSubject(KERNEL_ID);
   }

   public SubjectHandle getAnonymousSubject() {
      return ANON_HANDLE;
   }

   public SubjectHandle getKernelSubject() {
      SecurityServiceManager.checkKernelPermission();
      return KERNEL_HANDLE;
   }

   public Object unwrapSubject(SubjectHandle subject) {
      return toAuthSubject(subject);
   }

   public SubjectHandle wrapSubject(Object obj) {
      return toSubjectHandle((AuthenticatedSubject)obj);
   }

   public SubjectHandle wrapSubject(Object obj, Object data) {
      return data == null ? toSubjectHandle((AuthenticatedSubject)obj) : toSubjectHandle((AuthenticatedSubject)obj, (Map)data);
   }

   public boolean isUserAnonymous(SubjectHandle subject) {
      return SubjectUtils.isUserAnonymous(toAuthSubject(subject));
   }

   public String getUsername(SubjectHandle subject) {
      return SubjectUtils.getUsername(toAuthSubject(subject));
   }

   public Principal getUserPrincipal(SubjectHandle subject) {
      return SubjectUtils.getUserPrincipal(toAuthSubject(subject));
   }

   public boolean registerSEPermissions(String[] deployCodeBases, PermissionCollection permissions, String ddGrant) throws SecurityServiceException {
      SecurityServiceManager.checkKernelPermission();
      return SupplementalPolicyObject.registerSEPermissions(KERNEL_ID, deployCodeBases, permissions, ddGrant, "weblogic.xml", "WEB", "EE_WEB");
   }

   public void setJavaSecurityPolicies(String[] filenames, String grantStatement) {
      SecurityServiceManager.checkKernelPermission();
      SupplementalPolicyObject.setPoliciesFromGrantStatement(KERNEL_ID, filenames, grantStatement, "WEB");
   }

   public void removeJavaSecurityPolices(String[] filenames) {
      SecurityServiceManager.checkKernelPermission();
      SupplementalPolicyObject.removePolicies(KERNEL_ID, filenames);
   }

   public boolean isUserAnAdministrator(SubjectHandle subject) {
      return SubjectUtils.isUserAnAdministrator(toAuthSubject(subject));
   }

   public boolean isUserInAdminRoles(SubjectHandle subject, String[] roles) {
      return SubjectUtils.isUserInAdminRoles(toAuthSubject(subject), roles);
   }

   public boolean isAdminPrivilegeEscalation(SubjectHandle currentSubject, SubjectHandle requestedSubject) {
      return SubjectUtils.isAdminPrivilegeEscalation(toAuthSubject(currentSubject), toAuthSubject(requestedSubject));
   }

   public byte[] getRandomBytesFromSalt(int length) {
      return Salt.getRandomBytes(length);
   }

   public String getRealmAuthMethods() {
      DomainMBean domainmbean = WebServerRegistry.getInstance().getManagementProvider().getDomainMBean();
      RealmMBean realm = domainmbean.getSecurityConfiguration().getDefaultRealm();
      return realm != null ? realm.getAuthMethods() : null;
   }

   public SecureRandom getSecureRandom() {
      return LocalRJVM.getLocalRJVM().getSecureRandom();
   }

   public PrincipalAuthenticator getSecurityService(String realmName) {
      SecurityServiceManager.checkKernelPermission();
      return (PrincipalAuthenticator)SecurityServiceManager.getSecurityService(KERNEL_ID, realmName, ServiceType.AUTHENTICATION);
   }

   public boolean isJaccEnabled() {
      return SecurityServiceManager.isJACCEnabled();
   }

   public Object runAs(SubjectHandle subject, PrivilegedAction action, AuthenticatedSubject kernelId) {
      AuthenticatedSubject as = subject == null ? kernelId : toAuthSubject(subject);
      return SecurityServiceManager.runAs(kernelId, as, action);
   }

   public Object runAs(AuthenticatedSubject as, PrivilegedAction action, AuthenticatedSubject kernelId) {
      return SecurityServiceManager.runAs(kernelId, as, action);
   }

   public Object runAs(SubjectHandle subject, PrivilegedExceptionAction action, AuthenticatedSubject kernelId) throws PrivilegedActionException {
      AuthenticatedSubject as = subject == null ? kernelId : toAuthSubject(subject);
      return SecurityServiceManager.runAs(kernelId, as, action);
   }

   public Object runAs(AuthenticatedSubject as, PrivilegedExceptionAction action, AuthenticatedSubject kernelId) throws PrivilegedActionException {
      return SecurityServiceManager.runAs(kernelId, as, action);
   }

   public Object runAsForUserCode(AuthenticatedSubject as, PrivilegedAction action, AuthenticatedSubject kernelId) {
      return SecurityServiceManager.runAsForUserCode(kernelId, as, action);
   }

   public boolean isKernelIdentity(SubjectHandle subject) {
      return SecurityServiceManager.isKernelIdentity(toAuthSubject(subject));
   }

   public SubjectHandle getCurrentSubject() {
      SecurityServiceManager.checkKernelPermission();
      return toSubjectHandle(SecurityServiceManager.getCurrentSubject(KERNEL_ID));
   }

   public boolean areWebAppFilesCaseInsensitive() {
      return SecurityServiceManager.areWebAppFilesCaseInsensitive();
   }

   public String getDefaultRealmName() {
      return SecurityServiceManager.getDefaultRealmName();
   }

   public boolean isSamlApp(String contextPath) {
      return contextPath.equals("/samlits_ba") || contextPath.equals("/samlits_cc") || contextPath.equals("/samlacs") || contextPath.equals("/samlars") || contextPath.equals("/saml2");
   }

   public static final ContextHandler getContextHandler(HttpServletRequest request, HttpServletResponse response) {
      return new WebAppContextHandler(request, response);
   }

   public static SubjectHandle toSubjectHandle(AuthenticatedSubject subject) {
      return new WlsSubjectHandle(subject);
   }

   public static SubjectHandle toSubjectHandle(AuthenticatedSubject subject, Map associatedData) {
      return new WlsSubjectHandle(subject, associatedData);
   }

   public static AuthenticatedSubject toAuthSubject(SubjectHandle handle) {
      return handle == null ? null : ((WlsSubjectHandle)handle).getAuthSubject();
   }

   public void initializeJACC() throws DeploymentException {
      PolicyContextHandler commonCtxHdlr = new CommonPolicyContextHandler();
      String[] webAppKeys = WebAppContextHandlerData.getKeys();
      PolicyContextHandler webAppCtxHdlr = new DelegatingPolicyContextHandler(webAppKeys);

      try {
         PolicyContext.registerHandler("javax.security.auth.Subject.container", commonCtxHdlr, true);

         for(int i = 0; i < webAppKeys.length; ++i) {
            PolicyContext.registerHandler(webAppKeys[i], webAppCtxHdlr, true);
         }

      } catch (PolicyContextException var5) {
         throw new DeploymentException(var5);
      }
   }

   public Object[] getSSLAttributes(SSLSocket sslSocket) {
      Object[] ret = new Object[4];
      SSLSession sslSession = sslSocket.getSession();
      ret[0] = sslSession;
      String cipher = sslSession.getCipherSuite();
      ret[1] = cipher;
      int keySize = SSLCipherUtility.getKeySize(cipher);
      if (keySize >= 0) {
         ret[2] = new Integer(keySize);
      } else if (SSLSetup.isDebugEnabled()) {
         SSLSetup.debug(1, "SSLCipherUtility.getKeySize returned " + keySize + " for cipher suite \"" + cipher + "\".");
      }

      try {
         Certificate[] peerChain = sslSession.getPeerCertificates();
         if (peerChain != null) {
            ret[3] = SSLCertUtility.toJavaX5092(peerChain);
         }
      } catch (IOException var7) {
         SSLSetup.info("Warning: Problem processing peer certificates. Please run with debug mode turned ON at warning level for more details.");
         if (SSLSetup.isDebugEnabled()) {
            SSLSetup.debug(2, var7, "Exception processing peer certificates: " + var7.getMessage());
         }
      }

      return ret;
   }

   static {
      KERNEL_HANDLE = toSubjectHandle(KERNEL_ID);
      ANON_HANDLE = toSubjectHandle(SubjectUtils.getAnonymousSubject());
   }
}
