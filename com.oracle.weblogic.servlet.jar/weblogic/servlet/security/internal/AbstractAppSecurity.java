package weblogic.servlet.security.internal;

import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.utils.ConnectionSigner;
import weblogic.security.SimpleCallbackHandler;
import weblogic.security.SubjectUtils;
import weblogic.security.auth.login.PasswordCredential;
import weblogic.servlet.spi.ApplicationSecurity;
import weblogic.servlet.spi.SubjectHandle;

public abstract class AbstractAppSecurity implements ApplicationSecurity {
   private final ServletSecurityServices securityServices;
   private final ServletSecurityServices.ApplicationServices applicationServices;
   private final String realmName;
   private final int roleMappingMode;

   protected AbstractAppSecurity(ServletSecurityServices securityServices, AppDeploymentMBean mbean, String contextPath, String realmName) {
      this.securityServices = securityServices;
      this.realmName = realmName != null ? realmName : this.securityServices.getDefaultRealmName();
      this.applicationServices = this.securityServices.createApplicationSecurity(this.realmName, mbean, contextPath);
      this.roleMappingMode = this.applicationServices.getRoleMappingBehavior();
   }

   public SubjectHandle authenticate(CallbackHandler handler, String realmName, HttpServletRequest req, HttpServletResponse res) throws LoginException {
      assert realmName.equals(this.realmName) : "Another realm is already active";

      return this.applicationServices.authenticate(handler, req, res);
   }

   public SubjectHandle authenticateAndSaveCredential(final String username, final Object credential, String realmName, HttpServletRequest req, HttpServletResponse res) throws LoginException {
      assert realmName.equals(this.realmName) : "Another realm is already active";

      ServletCallbackHandler handler = this.securityServices.createCallbackHandler(username, credential, req, res);
      final SubjectHandle subject = this.applicationServices.authenticate(handler, req, res);
      if (subject != null) {
         AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
               AbstractAppSecurity.this.securityServices.addToPrivateCredentials(subject, new PasswordCredential(username, (String)credential));
               return null;
            }
         });
      }

      return subject;
   }

   public SubjectHandle authenticateAndSaveCredential(final String username, final char[] password, String realmName) throws LoginException {
      assert realmName.equals(this.realmName) : "Another realm is already active";

      CallbackHandler handler = new SimpleCallbackHandler(username, password);
      final SubjectHandle subject = this.applicationServices.authenticate(handler);
      if (subject != null) {
         AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
               AbstractAppSecurity.this.securityServices.addToPrivateCredentials(subject, new PasswordCredential(username, new String(password)));
               return null;
            }
         });
      }

      return subject;
   }

   public SubjectHandle assertIdentity(String tokenType, Object token, HttpServletRequest request, HttpServletResponse response) throws LoginException {
      return this.applicationServices.assertIdentity(tokenType, token, request, response);
   }

   public SubjectHandle impersonate(String user, String realmName, HttpServletRequest req, HttpServletResponse res) throws LoginException {
      assert realmName.equals(this.realmName) : "Another realm is already active";

      return this.applicationServices.impersonateIdentity(user, req, res);
   }

   public void populateSubject(Subject subject, SubjectHandle fromHandle) {
      Subject fromSubject = this.securityServices.toSubject(fromHandle);
      SubjectUtils.setFrom(subject, fromSubject);
   }

   public SubjectHandle toSubjectHandle(Subject subject) {
      return this.securityServices.toSubjectHandle(subject);
   }

   public Map getAssertionsEncodingMap() {
      return this.applicationServices.getAssertionsEncodingMap();
   }

   public Map[] getAssertionsEncodingPrecedence() {
      return this.applicationServices.getAssertionsEncodingPrecedence();
   }

   public boolean doesTokenTypeRequireBase64Decoding(String tokenType) {
      return this.applicationServices.doesTokenTypeRequireBase64Decoding(tokenType);
   }

   public boolean doesTokenRequireBase64Decoding(Object token) {
      return this.applicationServices.doesTokenRequireBase64Decoding(token);
   }

   public Filter[] getServletAuthenticationFilters(ServletContext ctx) throws DeploymentException {
      return this.applicationServices.getServletAuthenticationFilters(ctx);
   }

   public boolean isCompatibilitySecMode() {
      return this.securityServices.isCompatibilitySecMode(this.roleMappingMode);
   }

   public boolean isApplicationSecMode() {
      return this.securityServices.isApplicationSecMode(this.roleMappingMode);
   }

   public boolean isExternallyDefinedSecMode() {
      return this.securityServices.isExternallyDefinedSecMode(this.roleMappingMode);
   }

   public void destroyServletAuthenticationFilters(Filter[] filters) {
      this.applicationServices.destroyServletAuthenticationFilters(filters);
   }

   public boolean isRequestSigned(HttpServletRequest request) {
      return ConnectionSigner.isConnectionSigned(request, true);
   }

   public void deployUncheckedPolicy(Permission permission) throws DeploymentException {
   }

   protected ServletSecurityServices getSecurityServices() {
      return this.securityServices;
   }

   protected ServletSecurityServices.ApplicationServices getApplicationServices() {
      return this.applicationServices;
   }
}
