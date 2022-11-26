package weblogic.servlet.security.internal;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import javax.security.auth.message.MessagePolicy;
import javax.security.auth.message.config.AuthConfigFactory;
import javax.security.auth.message.config.AuthConfigProvider;
import javax.security.auth.message.config.RegistrationListener;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.SecurityRole;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.i18n.logging.Loggable;
import weblogic.j2ee.descriptor.LoginConfigBean;
import weblogic.j2ee.descriptor.SecurityConstraintBean;
import weblogic.j2ee.descriptor.SecurityRoleBean;
import weblogic.j2ee.descriptor.SecurityRoleRefBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.wl.JASPICProviderBean;
import weblogic.j2ee.descriptor.wl.RunAsRoleAssignmentBean;
import weblogic.j2ee.descriptor.wl.SecurityRoleAssignmentBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.AuthConfigProviderMBean;
import weblogic.management.configuration.AuthModuleMBean;
import weblogic.management.configuration.CustomAuthConfigProviderMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JASPICMBean;
import weblogic.management.configuration.WLSAuthConfigProviderMBean;
import weblogic.management.configuration.WebAppContainerMBean;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelManager;
import weblogic.security.debug.SecurityDebugLogger;
import weblogic.security.jaspic.SecurityServices;
import weblogic.security.jaspic.SecurityServicesImpl;
import weblogic.security.jaspic.SimpleAuthConfigProvider;
import weblogic.security.jaspic.servlet.JaspicSecurityModule;
import weblogic.server.GlobalServiceLocator;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.internal.ProtocolHandlerHTTPS;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.servlet.internal.ServletStubImpl;
import weblogic.servlet.internal.WebAppPartitionManagerInterceptor;
import weblogic.servlet.internal.WebComponentBeanUpdateListener;
import weblogic.servlet.spi.ApplicationSecurity;
import weblogic.servlet.spi.ManagementProvider;
import weblogic.servlet.spi.SecurityProvider;
import weblogic.servlet.spi.SubjectHandle;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.utils.http.HttpParsing;

public abstract class WebAppSecurity {
   protected ServletSecurityContext securityContext;
   protected SecurityModule delegateModule;
   protected final HashSet roleNames;
   protected final HashMap roleMapping;
   protected final HashMap runAsMapping;
   protected boolean isAnyAuthUserRoleDefinedInDD;
   private String loginPage;
   private String errorPage;
   private String authMethod;
   private String cachedAuthType;
   private boolean formAuth;
   protected static final String NONE = "NONE";
   protected static final String INTEGRAL = "INTEGRAL";
   protected static final String CONFIDENTIAL = "CONFIDENTIAL";
   private static final String LAYER_NAME = "HttpServlet";
   private final Filter[] authFilters;
   private final boolean authFiltersPresent;
   private String authFilter;
   private RequestDispatcher authFilterRD;
   protected final ApplicationSecurity appSecurity;
   private final ExternalRoleChecker externalRoleChecker;
   private Boolean changeSessionIdOnReauthentication;
   private String registrationId;
   private boolean jaspicEnabled;
   private RegistrationListener jaspicListener;
   private SecurityServices securityServices;
   private SecurityDebugLogger athnLogger;
   private BeanUpdateListener beanUpdateListener;
   protected boolean isDenyUncoveredMethodsSet;
   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   static final long serialVersionUID = 4217463693944675165L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.servlet.security.internal.WebAppSecurity");
   static final DelegatingMonitor _WLDF$INST_FLD_Servlet_Check_Access_Around_Medium;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;

   protected WebAppSecurity(ServletSecurityContext ctx, ApplicationSecurity as, ExternalRoleChecker roleChecker) throws DeploymentException {
      this(ctx, as, roleChecker, (SecurityServices)GlobalServiceLocator.getServiceLocator().getService(SecurityServicesImpl.class, new Annotation[0]));
   }

   protected WebAppSecurity(ServletSecurityContext ctx, ApplicationSecurity as, ExternalRoleChecker roleChecker, SecurityServices securityServices) throws DeploymentException {
      this.roleNames = new HashSet();
      this.roleMapping = new HashMap();
      this.runAsMapping = new HashMap();
      this.isAnyAuthUserRoleDefinedInDD = false;
      this.loginPage = null;
      this.errorPage = null;
      this.authMethod = null;
      this.cachedAuthType = null;
      this.formAuth = false;
      this.jaspicListener = new HttpServletRegistrationListener();
      this.athnLogger = new SecurityDebugLogger("DebugSecurityAtn");
      this.appSecurity = as;
      this.securityContext = ctx;
      this.authFilters = this.appSecurity.getServletAuthenticationFilters(this.securityContext.getServletContext());
      this.authFiltersPresent = this.authFilters != null && this.authFilters.length > 0;
      this.externalRoleChecker = roleChecker;
      this.securityServices = securityServices;
      this.beanUpdateListener = this.createBeanUpdateListener();
   }

   public static WebAppSecurity createWebAppSecurity(ApplicationContextInternal applicationContext, ServletSecurityContext securityContext, String applicationId, String contextPath, String docroot, ExternalRoleCheckerManager checkerManager) throws DeploymentException {
      ServletSecurityServices services = getSecurityServices();
      AppDeploymentMBean mbean = applicationContext == null ? null : applicationContext.getAppDeploymentMBean();
      if (services != null && services.isJACCEnabled() && securityContext.useJACC(docroot)) {
         JACCSecurity jaccSecurity = new JACCSecurity(getSecurityServices(), mbean, contextPath, applicationId, applicationContext, securityContext.getServerName(), docroot);
         return new WebAppSecurityJacc(securityContext, jaccSecurity, checkerManager);
      } else {
         WLSSecurity wlsSecurity = new WLSSecurity(getSecurityServices(), mbean, contextPath, applicationId, applicationContext == null ? null : applicationContext.getApplicationSecurityRealmName());
         return new WebAppSecurityWLS(securityContext, wlsSecurity, checkerManager);
      }
   }

   boolean isChangeSessionIdOnReauthentication() {
      if (this.changeSessionIdOnReauthentication == null) {
         String changeSessionIdProperty = System.getProperty("changeSessionIdOnAuthentication");
         if (changeSessionIdProperty != null) {
            this.changeSessionIdOnReauthentication = Boolean.valueOf(changeSessionIdProperty);
         } else {
            ManagementProvider mgmtProvider = WebServerRegistry.getInstance().getManagementProvider();
            DomainMBean domainmbean = mgmtProvider.getDomainMBean();
            WebAppContainerMBean webAppContainer = domainmbean.getWebAppContainer();
            this.changeSessionIdOnReauthentication = webAppContainer.isChangeSessionIDOnAuthentication();
         }
      }

      return this.changeSessionIdOnReauthentication;
   }

   public static SecurityProvider getProvider() {
      return WebServerRegistry.getInstance().getSecurityProvider();
   }

   public static ServletSecurityServices getSecurityServices() {
      return WebServerRegistry.getInstance().getSecurityServices();
   }

   public ApplicationSecurity getAppSecurityProvider() {
      return this.appSecurity;
   }

   public final String getLoginPage() {
      return this.loginPage;
   }

   public final String getErrorPage() {
      return this.errorPage;
   }

   public final String getAuthMethod() {
      return this.authMethod;
   }

   public final boolean isFormAuth() {
      return this.formAuth;
   }

   protected boolean isFullSecurityDelegationRequired() {
      return this.appSecurity.isFullSecurityDelegationRequired();
   }

   boolean isNotLastChainedSecurityModule(SecurityModule module) {
      return this.delegateModule instanceof ChainedSecurityModule && !((ChainedSecurityModule)this.delegateModule).isLastChainedSecurityModule(module);
   }

   boolean isLastSecurityModule(SecurityModule module) {
      return this.delegateModule instanceof ChainedSecurityModule ? ((ChainedSecurityModule)this.delegateModule).isLastChainedSecurityModule(module) : true;
   }

   public final void startDeployment() throws DeploymentException {
      this.appSecurity.startRoleAndPolicyDeployments();
   }

   public final void endDeployment() throws DeploymentException {
      this.appSecurity.endRoleAndPolicyDeployments(this.roleMapping);
   }

   protected boolean hasAuthFilters() {
      return this.authFiltersPresent;
   }

   protected abstract void deployRoles() throws DeploymentException;

   protected abstract void mergePolicies(WebAppBean var1, SecurityConstraintBean[] var2) throws DeploymentException;

   protected abstract void deployPolicies() throws DeploymentException;

   public abstract ResourceConstraint getConstraint(HttpServletRequest var1);

   public boolean hasPermission(HttpServletRequest request, HttpServletResponse response, SubjectHandle subject, ResourceConstraint cons) {
      if (this.getSecurityContext().isAdminMode() && this.getSecurityContext().isInternalApp() && this.appSecurity.isRequestSigned(request)) {
         return true;
      } else if (this.getSecurityContext().isAdminMode() && !this.getSecurityContext().isInternalApp() && !WebAppPartitionManagerInterceptor.isPartitionShutdown() && !WebAppPartitionManagerInterceptor.isPartitionSuspending()) {
         return this.checkAdminMode(subject);
      } else {
         if (!this.appSecurity.isFullSecurityDelegationRequired()) {
            if (cons == null || cons.isUnrestricted()) {
               return true;
            }

            if (cons.isForbidden()) {
               return false;
            }

            if (cons.isLoginRequired()) {
               return subject != null;
            }

            if (subject == null) {
               return false;
            }
         } else if (cons != null && cons.isLoginRequired() && subject == null) {
            return false;
         }

         if (subject == null) {
            subject = getProvider().getAnonymousSubject();
         }

         return this.appSecurity.hasPermission(subject, request, response, getRelativeURI(request));
      }
   }

   public abstract boolean isSubjectInRole(SubjectHandle var1, String var2, HttpServletRequest var3, HttpServletResponse var4, ServletConfig var5);

   public abstract void registerRoleRefs(ServletConfig var1) throws DeploymentException;

   protected abstract void deployRoleLink(ServletConfig var1, String var2, String var3) throws DeploymentException;

   protected abstract boolean checkTransport(ResourceConstraint var1, HttpServletRequest var2, HttpServletResponse var3) throws IOException;

   public abstract boolean isSSLRequired(String var1, String var2);

   public void initContextHandler(HttpServletRequest req) {
   }

   public void resetContextHandler() {
   }

   public Subject toSubject(SubjectHandle handle) {
      return getSecurityServices().toSubject(handle);
   }

   public SubjectHandle toSubjectHandle(Subject subject) {
      return getSecurityServices().toSubjectHandle(subject);
   }

   public String getRunAsPrincipalName(String runAsPrincipalName, String roleName) throws DeploymentException {
      if (runAsPrincipalName != null) {
         return runAsPrincipalName;
      } else {
         String identity;
         if ((identity = this.getRunAsIdentity(roleName)) != null) {
            return identity;
         } else if ((identity = this.getFirstPrincipal(roleName)) != null) {
            HTTPLogger.logImplicitMappingForRunAsRole(this.getSecurityContext().getLogContext(), "run-as", roleName, "web.xml", identity);
            return identity;
         } else {
            if (!this.appSecurity.isCompatibilitySecMode()) {
               SecurityRole sr = this.getSecurityContext().getSecurityRole(roleName);
               if (sr != null) {
                  String[] principals = sr.getPrincipalNames();
                  if (!sr.isExternallyDefined() && principals != null && principals.length > 0) {
                     return principals[0];
                  }
               }

               if (this.appSecurity.isApplicationSecMode()) {
                  throw new DeploymentException("Cannot resolve role-Name " + roleName);
               }
            }

            HTTPLogger.logImplicitMappingForRunAsRoleToSelf(this.getSecurityContext().getLogContext(), "run-as", roleName, "web.xml");
            return roleName;
         }
      }
   }

   public void unregisterRolesAndPolicies() {
      this.appSecurity.destroyServletAuthenticationFilters(this.authFilters);
   }

   public void undeploy() {
      this.unregisterRolesAndPolicies();
      this.unregisterJaspicProvider();
   }

   public void unregisterJaspicProvider() {
      AuthConfigFactory factory = AuthConfigFactory.getFactory();
      if (factory != null) {
         factory.detachListener(this.jaspicListener, "HttpServlet", SecurityModule.getAppContextId(this.securityContext));
         if (this.registrationId != null) {
            factory.removeRegistration(this.registrationId);
            this.athnLogger.debug("Removing AuthConfigProvider registration: " + this.registrationId);
         }
      }

   }

   protected boolean isExternalRole(String rolename) {
      return this.externalRoleChecker.isExternalRole(rolename);
   }

   public final void deployPolicyAndRole() throws DeploymentException {
      this.deployPolicies();
      this.deployRoles();
   }

   public final void registerSecurityConstraints(WebAppBean webAppBean, SecurityConstraintBean[] secCons) throws DeploymentException {
      this.mergePolicies(webAppBean, secCons);
   }

   public void registerSecurityRoles(WebAppBean webAppBean, WeblogicWebAppBean wlWebAppBean) throws DeploymentException {
      SecurityRoleBean[] srb = webAppBean.getSecurityRoles();
      if (srb != null) {
         for(int i = 0; i < srb.length; ++i) {
            this.roleNames.add(srb[i].getRoleName());
            if ("**".equals(srb[i].getRoleName())) {
               this.isAnyAuthUserRoleDefinedInDD = true;
            }
         }
      }

      this.roleNames.add("**");
      if (wlWebAppBean != null) {
         this.setRoleMapping(wlWebAppBean.getSecurityRoleAssignments());
         RunAsRoleAssignmentBean[] raras = wlWebAppBean.getRunAsRoleAssignments();
         if (raras != null) {
            for(int i = 0; i < raras.length; ++i) {
               RunAsRoleAssignmentBean rara = raras[i];
               if (this.roleNames.contains(rara.getRoleName())) {
                  this.runAsMapping.put(rara.getRoleName(), rara.getRunAsPrincipalName());
               } else if (!this.isExternalRole(rara.getRoleName())) {
                  Loggable l = HTTPLogger.logUndefinedSecurityRoleLoggable(rara.getRoleName(), "run-as-role-assignment");
                  l.log();
                  throw new DeploymentException(l.getMessage());
               }
            }
         }
      }

   }

   public final void registerDefaultRoleMappingForJSR375(SecurityRoleBean[] srb) {
      if (srb != null) {
         for(int i = 0; i < srb.length; ++i) {
            String roleName = srb[i].getRoleName();
            if (!"**".equals(roleName) && this.roleMapping.get(roleName) == null) {
               this.roleMapping.put(roleName, new String[]{roleName});
            }
         }

      }
   }

   public boolean isRoleNameDeclared(String roleName) {
      if (!"**".equals(roleName)) {
         return this.roleNames.contains(roleName);
      } else {
         return this.roleNames.contains(roleName) && this.isAnyAuthUserRoleDefinedInDD();
      }
   }

   private final void setRoleMapping(SecurityRoleAssignmentBean[] sr) throws DeploymentException {
      if (sr != null) {
         for(int i = 0; i < sr.length; ++i) {
            String role = sr[i].getRoleName();
            if (this.roleNames.contains(role)) {
               if (sr[i].getExternallyDefined() != null) {
                  String[] mapping = new String[]{null};
                  this.roleMapping.put(role, mapping);
               } else if (sr[i].getPrincipalNames() != null && sr[i].getPrincipalNames().length > 0) {
                  this.roleMapping.put(role, sr[i].getPrincipalNames());
               }
            } else if (!this.isExternalRole(role)) {
               Loggable l = HTTPLogger.logBadSecurityRoleInSRALoggable(role);
               l.log();
               throw new DeploymentException(l.getMessage());
            }
         }

      }
   }

   public final String getRunAsIdentity(String rolename) {
      return (String)this.runAsMapping.get(rolename);
   }

   public final String getFirstPrincipal(String rl) {
      String[] principals = (String[])this.roleMapping.get(rl);
      return principals != null && principals.length >= 1 ? principals[0] : null;
   }

   private String initAuthMethod(String am) {
      if (am == null) {
         return "BASIC";
      } else if (am.equalsIgnoreCase("BASIC")) {
         return "BASIC";
      } else if (am.equalsIgnoreCase("FORM")) {
         return "FORM";
      } else if (am.equalsIgnoreCase("CLIENT-CERT")) {
         return "CLIENT_CERT";
      } else if (am.equalsIgnoreCase("DIGEST")) {
         return "DIGEST";
      } else {
         return am.toUpperCase().contains("CLIENT-CERT") ? am.toUpperCase().replaceAll("CLIENT-CERT", "CLIENT_CERT") : am;
      }
   }

   public boolean checkAccess(HttpServletRequest request, HttpServletResponse response, boolean applyAuthFilters, boolean isRecursiveCall) throws IOException, ServletException {
      LocalHolder var5;
      if ((var5 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var5.argsCapture) {
            var5.args = new Object[5];
            Object[] var10000 = var5.args;
            var10000[0] = this;
            var10000[1] = request;
            var10000[2] = response;
            var10000[3] = InstrumentationSupport.convertToObject(applyAuthFilters);
            var10000[4] = InstrumentationSupport.convertToObject(isRecursiveCall);
         }

         InstrumentationSupport.createDynamicJoinPoint(var5);
         InstrumentationSupport.preProcess(var5);
         var5.resetPostBegin();
      }

      boolean var8;
      try {
         var8 = this.checkAccess(request, response, false, applyAuthFilters, isRecursiveCall);
      } catch (Throwable var7) {
         if (var5 != null) {
            var5.th = var7;
            var5.ret = InstrumentationSupport.convertToObject(false);
            InstrumentationSupport.createDynamicJoinPoint(var5);
            InstrumentationSupport.postProcess(var5);
         }

         throw var7;
      }

      if (var5 != null) {
         var5.ret = InstrumentationSupport.convertToObject(var8);
         InstrumentationSupport.createDynamicJoinPoint(var5);
         InstrumentationSupport.postProcess(var5);
      }

      return var8;
   }

   public boolean checkAccess(HttpServletRequest request, HttpServletResponse response, boolean checkAllResources, boolean applyAuthFilters, boolean isRecursiveCall) throws IOException, ServletException {
      RequestDispatcher rd = this.invokePreAuthFilters(request, response);
      boolean authorized = true;

      boolean var14;
      try {
         ResourceConstraint resourceConstraint = checkAllResources ? ResourceConstraint.Holder.ALL_CONSTRAINT : this.getConstraint(request);
         if (this.isFullSecurityDelegationRequired() || resourceConstraint == null || !resourceConstraint.isForbidden()) {
            if (this.delegateModule instanceof JaspicSecurityModule && isRecursiveCall) {
               authorized = this.createDelegateModule(isRecursiveCall).isAuthorized(request, response, resourceConstraint, applyAuthFilters);
               return authorized;
            }

            authorized = this.delegateModule.isAuthorized(request, response, resourceConstraint, applyAuthFilters);
            return authorized;
         }

         if (this.isFormAuth()) {
            String relUri = getRelativeURI(request);
            if (relUri.equals(this.getLoginPage()) || relUri.equals(this.getErrorPage())) {
               authorized = true;
               boolean var10 = authorized;
               return var10;
            }
         }

         response.sendError(403);
         authorized = false;
         var14 = authorized;
      } finally {
         if (rd != null) {
            this.invokePostAuthFilters(request, response, rd, authorized);
         }

         request.removeAttribute("weblogic.auth.result");
      }

      return var14;
   }

   public boolean postCheckAccess(HttpServletResponse response) throws IOException {
      return this.delegateModule.postCheckAccess(response);
   }

   public boolean postInvoke(HttpServletRequest request, HttpServletResponse response, SubjectHandle subject) throws ServletException {
      return this.delegateModule.postInvoke(request, response, subject);
   }

   public HttpServletRequest getWrappedRequest(HttpServletRequest request) throws ServletException {
      return this.delegateModule.getWrappedRequest(request);
   }

   public HttpServletResponse getWrappedResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException {
      return this.delegateModule.getWrappedResponse(request, response);
   }

   public void setLoginConfig(LoginConfigBean ld) {
      if (ld.getFormLoginConfig() != null) {
         if (ld.getFormLoginConfig().getFormLoginPage() != null) {
            this.loginPage = HttpParsing.ensureStartingSlash(ld.getFormLoginConfig().getFormLoginPage());
         }

         if (ld.getFormLoginConfig().getFormErrorPage() != null) {
            this.errorPage = HttpParsing.ensureStartingSlash(ld.getFormLoginConfig().getFormErrorPage());
         }
      }

      this.authMethod = this.initAuthMethod(ld.getAuthMethod());
      this.setCachedAuthType(this.authMethod);
      this.formAuth = this.authMethod.toUpperCase().contains("FORM");
      this.createDelegateModule();
   }

   public void setAuthMethod(String name) {
      this.authMethod = name;
   }

   public void setCachedAuthType(String name) {
      this.cachedAuthType = name;
   }

   public String getCachedAuthType() {
      return this.cachedAuthType;
   }

   public void createDelegateModule() {
      this.delegateModule = SecurityModule.createModule(this.securityContext, this, false);
   }

   public SecurityModule createDelegateModule(boolean isRecursiveCall) {
      return SecurityModule.createModule(this.securityContext, this, isRecursiveCall);
   }

   public void registerJaspicProvider(JASPICMBean jaspicMBean, JASPICProviderBean providerBean) throws DeploymentException {
      this.jaspicEnabled = jaspicMBean.isEnabled();
      AuthConfigFactory factory = AuthConfigFactory.getFactory();
      if (providerBean != null) {
         if (factory != null) {
            if (!providerBean.isEnabled()) {
               this.jaspicEnabled = false;
               this.athnLogger.debug("JASPIC is disabled for this application");
            } else {
               this.instantiateAndRegister(factory, jaspicMBean, providerBean);
               this.athnLogger.debug("Registered JASPIC AuthConfigProvider for application.");
            }
         } else {
            this.athnLogger.debug("AuthConfigFactory.getFactory returned NULL - JASPIC is not functional");
         }

      }
   }

   public boolean isJaspicEnabled() {
      return this.jaspicEnabled;
   }

   public RegistrationListener getJaspicListener() {
      return this.jaspicListener;
   }

   private void instantiateAndRegister(AuthConfigFactory factory, JASPICMBean jaspicMBean, JASPICProviderBean jaspic) throws DeploymentException {
      if (!$assertionsDisabled && factory == null) {
         throw new AssertionError();
      } else {
         String providerName = jaspic.getAuthConfigProviderName();
         if (providerName != null) {
            String appId = SecurityModule.getAppContextId(this.securityContext);
            AuthConfigProviderMBean acpConfig = jaspicMBean.lookupAuthConfigProvider(providerName);
            AuthConfigProvider acp = this.toProvider(acpConfig, appId);
            if (acp != null) {
               this.registrationId = factory.registerConfigProvider(acp, "HttpServlet", appId, (String)null);
               this.athnLogger.debug("registrationId: " + this.registrationId + " has been used to bind an ACP to application.");
            }
         }
      }
   }

   private AuthConfigProvider toProvider(AuthConfigProviderMBean acpConfig, String appId) throws DeploymentException {
      AuthConfigProvider acp = null;
      if (acpConfig instanceof WLSAuthConfigProviderMBean) {
         acp = this.createWLSAuthConfigProvider(acpConfig, appId);
      } else if (acpConfig instanceof CustomAuthConfigProviderMBean) {
         acp = this.createCustomAuthConfigProvider(acpConfig, acp);
         this.athnLogger.debug(acp.getClass().getName() + " is being registered as a Custom AuthConfigProvider.");
      }

      return acp;
   }

   private AuthConfigProvider createCustomAuthConfigProvider(AuthConfigProviderMBean acpConfig, AuthConfigProvider acp) throws DeploymentException {
      try {
         Class clazz = Class.forName(((CustomAuthConfigProviderMBean)acpConfig).getClassName());
         Class[] partypes = new Class[]{Map.class, AuthConfigFactory.class};
         Constructor ct = clazz.getConstructor(partypes);
         Object[] arglist = new Object[]{((CustomAuthConfigProviderMBean)acpConfig).getProperties(), null};
         acp = (AuthConfigProvider)ct.newInstance(arglist);
         return acp;
      } catch (ClassNotFoundException var7) {
         this.athnLogger.debug("Exception caught during Custom ACP creation.", var7);
         throw new DeploymentException("Unable to create AuthConfigProvider", var7);
      } catch (InstantiationException var8) {
         this.athnLogger.debug("Exception caught during Custom ACP creation.", var8);
         throw new DeploymentException("Unable to create AuthConfigProvider", var8);
      } catch (IllegalAccessException var9) {
         this.athnLogger.debug("Exception caught during Custom ACP creation.", var9);
         throw new DeploymentException("Unable to create AuthConfigProvider", var9);
      } catch (SecurityException var10) {
         this.athnLogger.debug("Exception caught during Custom ACP creation.", var10);
         throw new DeploymentException("Unable to create AuthConfigProvider", var10);
      } catch (NoSuchMethodException var11) {
         this.athnLogger.debug("Exception caught during Custom ACP creation.", var11);
         throw new DeploymentException("Unable to create AuthConfigProvider", var11);
      } catch (IllegalArgumentException var12) {
         this.athnLogger.debug("Exception caught during Custom ACP creation.", var12);
         throw new DeploymentException("Unable to create AuthConfigProvider", var12);
      } catch (InvocationTargetException var13) {
         this.athnLogger.debug("Exception caught during Custom ACP creation.", var13);
         throw new DeploymentException("Unable to create AuthConfigProvider", var13);
      }
   }

   private AuthConfigProvider createWLSAuthConfigProvider(AuthConfigProviderMBean acpConfig, String appId) throws DeploymentException {
      SimpleAuthConfigProvider acp = new SimpleAuthConfigProvider(new Properties(), (AuthConfigFactory)null);
      this.athnLogger.debug(acp.getClass().getName() + " is being registered for applicationId: " + appId);
      WLSAuthConfigProviderMBean wlsACP = (WLSAuthConfigProviderMBean)acpConfig;
      AuthModuleMBean authModule = wlsACP.getAuthModule();
      if (authModule == null) {
         this.athnLogger.debug("No modules are configured for use in a registration for applicationId: " + appId);
         throw new DeploymentException("Unable to create AuthConfigProvider for " + appId + " - no modules specified");
      } else {
         SimpleAuthConfigProvider.Configuration configuration = acp.createConfiguration("HttpServlet", appId);
         configuration.addServerAuthModule(authModule.getClassName(), (MessagePolicy)null, (MessagePolicy)null, authModule.getProperties());
         this.athnLogger.debug(authModule.getClassName() + " is configured for use in a registration for applicationId: " + appId);
         return acp;
      }
   }

   public void setAuthRealmName(String authRealmName) {
      this.delegateModule.setAuthRealmBanner(authRealmName);
   }

   protected boolean checkAdminMode(SubjectHandle subject) {
      return subject == null ? false : subject.isInAdminRoles(new String[]{"Admin", "AppTester"});
   }

   protected final String getSecuredURL(HttpServletRequest req, HttpServletResponse rsp, String location) {
      String serverName = req.getServerName();
      int httpsPort = this.getSecurityContext().getFrontEndHTTPSPort();
      if (httpsPort == 0) {
         ServerChannel sslChannel = ServerChannelManager.findLocalServerChannel(ProtocolHandlerHTTPS.PROTOCOL_HTTPS);
         if (sslChannel == null) {
            return null;
         }

         httpsPort = sslChannel.getPublicPort();
      }

      ServletResponseImpl rspi = ServletResponseImpl.getOriginalResponse(rsp);
      String uri = rspi.processProxyPathHeaders(location);
      String queryString = req.getQueryString();
      StringBuffer buf = new StringBuffer();
      if (httpsPort == 443) {
         buf.append("https://").append(serverName).append(uri);
         if (queryString != null && queryString.length() > 1) {
            buf.append("?").append(queryString);
         }
      } else {
         buf.append("https://").append(serverName).append(":");
         buf.append(httpsPort).append(uri);
         if (queryString != null && queryString.length() > 1) {
            buf.append("?").append(queryString);
         }
      }

      return buf.toString();
   }

   public static final String getRelativeURI(HttpServletRequest request) {
      String uri = (String)request.getAttribute("webflow_resource");
      if (uri != null) {
         return uri;
      } else if (request instanceof ServletRequestImpl) {
         return ((ServletRequestImpl)request).getRelativeUri();
      } else {
         uri = ServletRequestImpl.getResolvedURI(request);
         String ctxPath = ServletRequestImpl.getResolvedContextPath(request);
         return ctxPath != null && ctxPath.length() > 0 && uri.startsWith(ctxPath) ? uri.substring(ctxPath.length()) : uri;
      }
   }

   public static String fixupURLPattern(String pattern) {
      if (isDefaultUrlPattern(pattern)) {
         return "/";
      } else {
         return !pattern.startsWith("*.") ? HttpParsing.ensureStartingSlash(pattern) : pattern;
      }
   }

   private static boolean isDefaultUrlPattern(String pattern) {
      if (pattern.length() > 2) {
         return false;
      } else if (getProvider().getEnforceStrictURLPattern()) {
         return pattern.equals("/");
      } else {
         return pattern.equals("*") || pattern.equals("/");
      }
   }

   protected boolean isExternallyDefined(String[] principals) {
      return principals != null && principals.length == 1 && (principals[0] == null || principals[0].length() == 0);
   }

   final String getContextName() {
      return this.getSecurityContext().getContextName() == null ? "Default WebApplication" : this.getSecurityContext().getContextName();
   }

   protected final ServletSecurityContext getSecurityContext() {
      return this.securityContext;
   }

   protected final ServletObjectsFacade getRequestFacade() {
      return this.securityContext.getRequestFacade();
   }

   public final void registerSecurityRoleRef(ServletStubImpl sstub, SecurityRoleRefBean[] secRefs) throws DeploymentException {
      if (secRefs != null) {
         for(int j = 0; j < secRefs.length; ++j) {
            SecurityRoleRefBean sr = secRefs[j];
            String refName = sr.getRoleName();
            String roleName = sr.getRoleLink();
            if (refName != null && roleName != null) {
               this.deployRoleLink(sstub, refName, roleName);
            }
         }

      }
   }

   public FilterChain getAuthFilterChain() {
      return new AuthFilterChain(this.authFilters, this.securityContext);
   }

   private RequestDispatcher getAuthFilterRD() {
      return this.authFilterRD;
   }

   public final String getAuthFilter() {
      return this.authFilter;
   }

   public final void setAuthFilter(String authFilterName) {
      this.authFilter = authFilterName;
      this.authFilterRD = this.getSecurityContext().createAuthFilterRequestDispatcher(authFilterName);
   }

   private RequestDispatcher invokePreAuthFilters(HttpServletRequest request, HttpServletResponse response) {
      RequestDispatcher rd = this.getAuthFilterRD();
      if (rd == null) {
         return null;
      } else {
         request.setAttribute("weblogic.auth.result", new Integer(-1));
         AuthFilterAction action = new AuthFilterAction(request, response, rd);
         Throwable excp = (Throwable)getProvider().getAnonymousSubject().run((PrivilegedAction)action);
         if (excp != null) {
            HTTPLogger.logAuthFilterInvocationFailed(this.getAuthFilter(), "pre-auth", request.getRequestURI(), excp);
         }

         request.removeAttribute("weblogic.auth.result");
         return rd;
      }
   }

   private final void invokePostAuthFilters(HttpServletRequest request, HttpServletResponse response, RequestDispatcher rd, boolean authorized) throws IOException {
      SubjectHandle subject = null;
      if (request.getAttribute("weblogic.auth.result") == null) {
         if (authorized) {
            subject = SecurityModule.getCurrentUser(this.getSecurityContext(), request);
            request.setAttribute("weblogic.auth.result", new Integer(0));
         } else {
            request.setAttribute("weblogic.auth.result", new Integer(1));
         }
      }

      if (subject == null) {
         subject = getProvider().getAnonymousSubject();
      }

      AuthFilterAction action = new AuthFilterAction(request, response, rd);
      Throwable excp = (Throwable)subject.run((PrivilegedAction)action);
      if (excp != null) {
         HTTPLogger.logAuthFilterInvocationFailed(this.getAuthFilter(), "post-auth", request.getRequestURI(), excp);
      }

      Integer ret = (Integer)request.getAttribute("weblogic.auth.result");
      if (ret != null && authorized && ret == 1) {
         this.delegateModule.sendError(request, response);
      }

   }

   void invokeAuthFilterChain(HttpServletRequest request, HttpServletResponse response) throws ServletException {
      if (this.hasAuthFilters()) {
         FilterChain chain = this.getAuthFilterChain();
         ServletAuthenticationFilterAction action = new ServletAuthenticationFilterAction(request, response, chain);
         SubjectHandle subject = (SubjectHandle)AccessController.doPrivileged(new PrivilegedAction() {
            public SubjectHandle run() {
               return WebAppSecurity.getProvider().getKernelSubject();
            }
         });
         Throwable e = (Throwable)subject.run((PrivilegedAction)action);
         if (e != null) {
            throw new ServletException(e);
         }
      }
   }

   public void login(String username, String password, HttpServletRequest request, HttpServletResponse response) throws ServletException {
      try {
         if ("CLIENT_CERT".equals(this.getAuthMethod())) {
            throw new ServletException("client-cert can't support login type for user and password");
         } else {
            SubjectHandle user = SecurityModule.checkAuthenticate(this.getSecurityContext(), request, response, username, password);
            if (user == null) {
               throw new ServletException("Failed to login");
            } else {
               SecurityModule var10000 = this.delegateModule;
               SessionSecurityData session = SecurityModule.getUserSession(request, false);
               this.delegateModule.login(request, user, session);
               this.pushSubject(user);
            }
         }
      } catch (LoginException var7) {
         throw new ServletException(var7);
      }
   }

   public void logout(HttpServletRequest request) {
      SecurityModule var10000 = this.delegateModule;
      SessionSecurityData session = SecurityModule.getUserSession(request, false);
      SecurityModule.logout(this.getSecurityContext(), session);
      if (session != null) {
         String sessionid = session.getIdWithServerInfo();
         this.getSecurityContext().removeAuthUserFromSession(request, sessionid);
      }

      this.popCurrentSubject();
   }

   private void popCurrentSubject() {
      AccessController.doPrivileged(new PrivilegedAction() {
         public Void run() {
            SubjectHandle subject = WebAppSecurity.getProvider().getCurrentSubject();
            if (subject != null && !subject.isAnonymous()) {
               WebAppSecurity.getProvider().popSubject();
               WebAppSecurity.getProvider().pushSubject(WebAppSecurity.getProvider().getAnonymousSubject());
            }

            return null;
         }
      });
   }

   private void pushSubject(final SubjectHandle subject) {
      AccessController.doPrivileged(new PrivilegedAction() {
         public Void run() {
            WebAppSecurity.getProvider().pushSubject(subject);
            return null;
         }
      });
   }

   public void declareRoles(String... roleNames) {
      this.roleNames.addAll(Arrays.asList(roleNames));
   }

   public SecurityServices getJaspicSecurityServices() {
      return this.securityServices;
   }

   public BeanUpdateListener getBeanUpdateListener() {
      return this.beanUpdateListener;
   }

   private BeanUpdateListener createBeanUpdateListener() {
      return new WebComponentBeanUpdateListener() {
         protected void handlePropertyRemove(BeanUpdateEvent.PropertyUpdate prop) {
         }

         protected void handlePropertyChange(BeanUpdateEvent.PropertyUpdate prop, DescriptorBean newBean) {
            this.uptakeJaspicChange(newBean);
         }

         private void uptakeJaspicChange(DescriptorBean newBean) {
            WebAppSecurity.this.unregisterJaspicProvider();
            ManagementProvider mgmtProvider = WebServerRegistry.getInstance().getManagementProvider();
            DomainMBean domain = mgmtProvider.getDomainMBean();

            try {
               WebAppSecurity.this.registerJaspicProvider(domain.getSecurityConfiguration().getJASPIC(), (JASPICProviderBean)newBean);
            } catch (DeploymentException var5) {
               WebAppSecurity.this.athnLogger.debug("Unable to register JASPIC provider for: " + WebAppSecurity.this.registrationId);
            }

            WebAppSecurity.this.createDelegateModule();
         }

         protected void prepareBeanAdd(BeanUpdateEvent.PropertyUpdate prop, DescriptorBean newBean) throws BeanUpdateRejectedException {
         }

         protected void handleBeanAdd(BeanUpdateEvent.PropertyUpdate prop, DescriptorBean newBean) {
         }

         protected void handleBeanRemove(BeanUpdateEvent.PropertyUpdate prop) {
         }
      };
   }

   public boolean isDenyUncoveredMethodsSet() {
      return this.isDenyUncoveredMethodsSet;
   }

   public void setDenyUncoveredMethodsSet(boolean denyUncoveredMethodsSet) {
      this.isDenyUncoveredMethodsSet = denyUncoveredMethodsSet;
   }

   public boolean isAnyAuthUserRoleDefinedInDD() {
      return this.isAnyAuthUserRoleDefinedInDD;
   }

   static {
      _WLDF$INST_FLD_Servlet_Check_Access_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Servlet_Check_Access_Around_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "WebAppSecurity.java", "weblogic.servlet.security.internal.WebAppSecurity", "checkAccess", "(Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;ZZ)Z", 563, "", "", "", InstrumentationSupport.makeMap(new String[]{"Servlet_Check_Access_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, InstrumentationSupport.createValueHandlingInfo("ret", (String)null, false, true), new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("req", "weblogic.diagnostics.instrumentation.gathering.ServletRequestRenderer", false, true), null, null, null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Servlet_Check_Access_Around_Medium};
      $assertionsDisabled = !WebAppSecurity.class.desiredAssertionStatus();
   }

   class HttpServletRegistrationListener implements RegistrationListener {
      public void notify(String layer, String appContext) {
         WebAppSecurity.this.createDelegateModule();
      }
   }

   private static class AuthFilterAction implements PrivilegedAction {
      private HttpServletRequest request;
      private HttpServletResponse response;
      private RequestDispatcher dispatcher;

      AuthFilterAction(HttpServletRequest rq, HttpServletResponse rp, RequestDispatcher rd) {
         this.request = rq;
         this.response = rp;
         this.dispatcher = rd;
      }

      public Object run() {
         try {
            this.dispatcher.include(this.request, this.response);
            return null;
         } catch (Throwable var2) {
            return var2;
         }
      }
   }

   private static class ServletAuthenticationFilterAction implements PrivilegedAction {
      private final HttpServletRequest request;
      private final HttpServletResponse response;
      private final FilterChain chain;

      ServletAuthenticationFilterAction(HttpServletRequest req, HttpServletResponse res, FilterChain ch) {
         this.request = req;
         this.response = res;
         this.chain = ch;
      }

      public Object run() {
         try {
            this.chain.doFilter(this.request, this.response);
            return null;
         } catch (Throwable var2) {
            return var2;
         }
      }
   }
}
