package weblogic.servlet.security;

import java.security.AccessController;
import java.security.Principal;
import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.jacc.PolicyContextHandlerData;
import weblogic.security.service.AuthorizationManager;
import weblogic.security.service.AuthorizationManagerDeployHandle;
import weblogic.security.service.DeployHandleCreationException;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.ResourceBase;
import weblogic.security.service.ResourceCreationException;
import weblogic.security.service.ResourceRemovalException;
import weblogic.security.service.RoleCreationException;
import weblogic.security.service.RoleManager;
import weblogic.security.service.RoleManagerDeployHandle;
import weblogic.security.service.RoleRemovalException;
import weblogic.security.service.SecurityApplicationInfo;
import weblogic.security.service.SecurityApplicationInfoImpl;
import weblogic.security.service.SecurityService;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.URLResource;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.security.spi.ApplicationInfo.ComponentType;
import weblogic.security.utils.ResourceIDDContextWrapper;
import weblogic.servlet.provider.WlsSubjectHandle;
import weblogic.servlet.security.css.CSSServletCallbackHandler;
import weblogic.servlet.security.internal.ServletCallbackHandler;
import weblogic.servlet.security.internal.ServletSecurityServices;
import weblogic.servlet.security.internal.WebAppContextHandler;
import weblogic.servlet.security.internal.WebAppContextHandlerData;
import weblogic.servlet.spi.SubjectHandle;

public class CSSServletSecurityServices implements ServletSecurityServices {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public ServletSecurityServices.ApplicationServices createApplicationSecurity(String realm, AppDeploymentMBean mbean, String contextPath) {
      SecurityServiceManager.checkKernelPermission();
      return new CSSApplicationServices(realm, mbean, contextPath);
   }

   public String getDefaultRealmName() {
      return SecurityServiceManager.getDefaultRealmName();
   }

   public boolean isCompatibilitySecMode(int securityMode) {
      return securityMode == 0;
   }

   public boolean isApplicationSecMode(int securityMode) {
      return securityMode == 1;
   }

   public boolean isExternallyDefinedSecMode(int securityMode) {
      return securityMode == 2;
   }

   public boolean isJACCEnabled() {
      return SecurityServiceManager.isJACCEnabled();
   }

   public void addToPrivateCredentials(SubjectHandle subject, Object credential) {
      addToPrivateCredentials(toAuthSubject(subject), credential);
   }

   private static void addToPrivateCredentials(AuthenticatedSubject subject, Object credentials) {
      SecurityServiceManager.checkKernelPermission();
      subject.getPrivateCredentials(KERNEL_ID).add(credentials);
   }

   private static AuthenticatedSubject toAuthSubject(SubjectHandle subject) {
      return ((WlsSubjectHandle)subject).getAuthSubject();
   }

   public Subject toSubject(SubjectHandle handle) {
      return toAuthSubject(handle).getSubject();
   }

   public SubjectHandle toSubjectHandle(Subject subject) {
      return new WlsSubjectHandle(AuthenticatedSubject.getFromSubject(subject));
   }

   public ServletCallbackHandler createCallbackHandler(String username, Object credential, HttpServletRequest req, HttpServletResponse res) {
      return new CSSServletCallbackHandler(username, credential, req, res);
   }

   public PolicyContextHandlerData createContextHandlerData(HttpServletRequest request) {
      return new WebAppContextHandlerData(request);
   }

   public Principal[] getPrincipals(SubjectHandle handle) {
      if (handle == null) {
         return new Principal[0];
      } else {
         AuthenticatedSubject subject = ((WlsSubjectHandle)handle).getAuthSubject();
         Principal[] principals;
         if (subject != null) {
            principals = new Principal[subject.getPrincipals().size()];
            subject.getPrincipals().toArray(principals);
         } else {
            principals = new Principal[0];
         }

         return principals;
      }
   }

   public class CSSApplicationServices implements ServletSecurityServices.ApplicationServices {
      private PrincipalAuthenticator pa;
      private AuthorizationManager authManager;
      private RoleManager roleManager;
      private RoleManagerDeployHandle roleMgrHandle;
      private AuthorizationManagerDeployHandle authMgrHandle;
      private SecurityApplicationInfo secureAppInfo;
      private String realmName;

      private CSSApplicationServices(String realmName, AppDeploymentMBean mbean, String contextPath) {
         this.realmName = realmName;
         this.secureAppInfo = new SecurityApplicationInfoImpl(mbean, ComponentType.WEBAPP, contextPath);
         this.pa = (PrincipalAuthenticator)this.getService(realmName, ServiceType.AUTHENTICATION);
         this.authManager = (AuthorizationManager)this.getService(realmName, ServiceType.AUTHORIZE);
         this.roleManager = (RoleManager)this.getService(realmName, ServiceType.ROLE);
      }

      private SecurityService getService(String realmName, SecurityService.ServiceType serviceType) {
         return SecurityServiceManager.getSecurityService(CSSServletSecurityServices.KERNEL_ID, realmName, serviceType);
      }

      public int getRoleMappingBehavior() {
         return SecurityServiceManager.getRoleMappingBehavior(this.realmName, this.secureAppInfo);
      }

      public String getSecurityModelType() {
         return this.secureAppInfo.getSecurityDDModel();
      }

      public boolean isFullDelegation() {
         return SecurityServiceManager.isFullAuthorizationDelegationRequired(this.realmName, this.secureAppInfo);
      }

      public void destroyServletAuthenticationFilters(Filter[] filters) {
         this.pa.destroyServletAuthenticationFilters(filters);
      }

      public Filter[] getServletAuthenticationFilters(ServletContext ctx) throws DeploymentException {
         try {
            return this.pa.getServletAuthenticationFilters(ctx);
         } catch (ServletException var3) {
            throw new DeploymentException(var3);
         }
      }

      public Map getAssertionsEncodingMap() {
         return this.pa.getAssertionsEncodingMap();
      }

      public Map[] getAssertionsEncodingPrecedence() {
         return this.pa.getAssertionsEncodingPrecedence();
      }

      public boolean doesTokenTypeRequireBase64Decoding(String tokenType) {
         return this.pa.doesTokenTypeRequireBase64Decoding(tokenType);
      }

      public boolean doesTokenRequireBase64Decoding(Object token) {
         return this.pa.doesTokenRequireBase64Decoding(token);
      }

      public void startDeployment() throws DeploymentException {
         try {
            this.authMgrHandle = this.authManager.startDeployPolicies(this.secureAppInfo);
            this.roleMgrHandle = this.roleManager.startDeployRoles(this.secureAppInfo);
         } catch (DeployHandleCreationException var2) {
            throw new DeploymentException(var2);
         }
      }

      public void endRoleAndPolicyDeployments() throws DeploymentException {
         try {
            this.authManager.endDeployPolicies(this.authMgrHandle);
            this.roleManager.endDeployRoles(this.roleMgrHandle);
         } catch (ResourceCreationException var2) {
            throw new DeploymentException(var2);
         } catch (RoleCreationException var3) {
            throw new DeploymentException(var3);
         }
      }

      public void deployRole(String roleName, String[] mappings, String appId, String contextPath) throws DeploymentException {
         ResourceBase resource = new URLResource(appId, contextPath, SecurityServiceManager.getEnforceStrictURLPattern() ? "/" : "/*", (String)null, (String)null);

         try {
            this.roleManager.deployRole(this.roleMgrHandle, resource, roleName, mappings);
         } catch (RoleCreationException var7) {
            throw new DeploymentException(var7);
         }
      }

      public void undeployAllPolicies() throws DeploymentException {
         if (this.authMgrHandle != null) {
            try {
               this.authManager.undeployAllPolicies(this.authMgrHandle);
            } catch (ResourceRemovalException var2) {
               throw new DeploymentException(var2);
            }
         }
      }

      public void undeployAllRoles() throws DeploymentException {
         if (this.roleMgrHandle != null) {
            try {
               this.roleManager.undeployAllRoles(this.roleMgrHandle);
            } catch (RoleRemovalException var2) {
               throw new DeploymentException(var2);
            }
         }
      }

      public boolean isSubjectInRole(String roleName, SubjectHandle subject, String appId, String contextPath, HttpServletRequest request, HttpServletResponse response) {
         ResourceBase resource = new URLResource(appId, contextPath, SecurityServiceManager.getEnforceStrictURLPattern() ? "/" : "/*", (String)null, (String)null);
         AuthenticatedSubject authenticatedSubject = CSSServletSecurityServices.toAuthSubject(subject);
         Map securityRoles = this.roleManager.getRoles(authenticatedSubject, resource, new ResourceIDDContextWrapper(new WebAppContextHandler(request, response)));
         return securityRoles != null && SecurityServiceManager.isUserInRole(authenticatedSubject, roleName, securityRoles);
      }

      public boolean hasPermission(String requestMethodType, String relativeRequestPath, String appId, String contextPath, SubjectHandle subject, HttpServletRequest request, HttpServletResponse response) {
         ResourceBase resource = new URLResource(appId, contextPath, relativeRequestPath, requestMethodType, (String)null);
         return this.authManager.isAccessAllowed(CSSServletSecurityServices.toAuthSubject(subject), resource, new ResourceIDDContextWrapper(new WebAppContextHandler(request, response)));
      }

      public void deployUncheckedPolicy(String resourceId, String method, String appId, String contextPath) throws DeploymentException {
         try {
            this.authManager.deployUncheckedPolicy(this.authMgrHandle, new URLResource(appId, contextPath, resourceId, method, (String)null));
         } catch (ResourceCreationException var6) {
            throw new DeploymentException(var6);
         }
      }

      public void deployExcludedPolicy(String resourceId, String method, String appId, String contextPath) throws DeploymentException {
         try {
            this.authManager.deployExcludedPolicy(this.authMgrHandle, new URLResource(appId, contextPath, resourceId, method, (String)null));
         } catch (ResourceCreationException var6) {
            throw new DeploymentException(var6);
         }
      }

      public void deployPolicy(String resourceId, String method, String[] roles, String appId, String contextPath) throws DeploymentException {
         try {
            this.authManager.deployPolicy(this.authMgrHandle, new URLResource(appId, contextPath, resourceId, method, (String)null), roles);
         } catch (ResourceCreationException var7) {
            throw new DeploymentException(var7);
         }
      }

      public SubjectHandle assertIdentity(String tokenType, Object token, HttpServletRequest request, HttpServletResponse response) throws LoginException {
         return new WlsSubjectHandle(this.pa.assertIdentity(tokenType, token, new WebAppContextHandler(request, response)));
      }

      public SubjectHandle authenticate(CallbackHandler handler, HttpServletRequest request, HttpServletResponse response) throws LoginException {
         return new WlsSubjectHandle(this.pa.authenticate(handler, new WebAppContextHandler(request, response)));
      }

      public SubjectHandle authenticate(CallbackHandler handler) throws LoginException {
         return new WlsSubjectHandle(this.pa.authenticate(handler));
      }

      public SubjectHandle impersonateIdentity(String user, HttpServletRequest request, HttpServletResponse response) throws LoginException {
         return new WlsSubjectHandle(this.pa.impersonateIdentity(user, new WebAppContextHandler(request, response)));
      }

      // $FF: synthetic method
      CSSApplicationServices(String x1, AppDeploymentMBean x2, String x3, Object x4) {
         this(x1, x2, x3);
      }
   }
}
