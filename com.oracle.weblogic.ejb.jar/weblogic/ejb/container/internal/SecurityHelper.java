package weblogic.ejb.container.internal;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.security.auth.login.LoginException;
import javax.security.jacc.EJBMethodPermission;
import javax.security.jacc.PolicyContextException;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.SecurityRole;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.compliance.EJBComplianceTextFormatter;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.ISecurityHelper;
import weblogic.ejb.container.interfaces.MethodInfo;
import weblogic.ejb.container.interfaces.NoSuchRoleException;
import weblogic.ejb.container.interfaces.SecurityRoleMapping;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.ejb20.interfaces.PrincipalNotFoundException;
import weblogic.logging.Loggable;
import weblogic.security.SubjectUtils;
import weblogic.security.WLSPrincipals;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.DeployHandleCreationException;
import weblogic.security.service.EJBResource;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.ResourceCreationException;
import weblogic.security.service.RoleCreationException;
import weblogic.security.service.SecurityApplicationInfo;
import weblogic.security.service.SecurityManager;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;

public final class SecurityHelper implements ISecurityHelper {
   private static final int SYSTEM_REALM = 0;
   private static final int APP_REALM = 1;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final DebugLogger debugLogger;
   private final boolean jaccEnabled;
   private final SecurityHelperWLS wlsHelper;
   private final SecurityHelperJACC jaccHelper;
   private final String sysRealmName;
   private final String appRealmName;
   private PrincipalAuthenticator appPrincipalAuth;
   private PrincipalAuthenticator sysPrincipalAuth;
   private final Map checkedMethodDescriptors = new HashMap();
   private final Map uncheckedMethodDescriptors = new HashMap();
   private final Map excludedMethodDescriptors = new HashMap();
   private final EJBResource ejbResource;

   private SecurityHelper(DeploymentInfo di) {
      this.appRealmName = di.getSecurityRealmName();
      if (di.getJACCPolicyContextId() != null) {
         this.jaccHelper = new SecurityHelperJACC(di.getJACCPolicyConfig(), di.getJACCPolicyContextId(), di.getJACCCodeSourceURL(), di.getJACCRoleMapper());
         this.jaccEnabled = true;
         this.sysRealmName = null;
         this.wlsHelper = null;
      } else {
         this.sysRealmName = SecurityServiceManager.getDefaultRealmName();
         if (this.sysRealmName == null) {
            throw new RuntimeException("Could not get System Realm Name");
         }

         this.wlsHelper = new SecurityHelperWLS(di.getSecurityRealmName(), this.sysRealmName);
         this.jaccEnabled = false;
         this.jaccHelper = null;
      }

      this.ejbResource = new EJBResource(di.getApplicationId(), di.getModuleId(), (String)null, (String)null, (String)null, (String[])null);
   }

   public static SecurityHelper newInstanceFor(DeploymentInfo di, AuthenticatedSubject kernelId) {
      SecurityServiceManager.checkKernelIdentity(kernelId);
      return new SecurityHelper(di);
   }

   public void pushSecurityContext(ContextHandler ch) {
      if (this.jaccEnabled) {
         this.jaccHelper.pushSecurityContext(ch);
      }

   }

   public void popSecurityContext() {
      if (this.jaccEnabled) {
         this.jaccHelper.popSecurityContext();
      }

   }

   public void setupApplicationInfo(ApplicationContextInternal appCtx, SecurityApplicationInfo sai) {
      if (this.jaccEnabled) {
         this.jaccHelper.setupApplicationInfo(appCtx);
      } else {
         this.wlsHelper.setupApplicationInfo(sai);
      }

   }

   public AuthenticatedSubject getSubjectForPrincipal(String principal) throws PrincipalNotFoundException {
      PrincipalAuthenticator principalAuth = this.obtainPA(1);

      try {
         AuthenticatedSubject s = principalAuth.impersonateIdentity(principal, (ContextHandler)null);
         if (debugLogger.isDebugEnabled()) {
            debug("getSubjectForPrincipal: for Principal: '" + principal + "', Subject is: '" + s.toString() + "'");
         }

         return s;
      } catch (LoginException var4) {
         throw new PrincipalNotFoundException(var4.getMessage(), var4);
      }
   }

   public void deployRoles(DeploymentInfo di, SecurityRoleMapping mapping, ApplicationContextInternal aci, int roleMappingBehavior) throws WLDeploymentException {
      try {
         if (this.jaccEnabled) {
            this.jaccHelper.deployRoles(di, mapping);
         } else {
            Map deployableRoleMapping = this.getDeployableSecurityRoleMapping(mapping, aci, roleMappingBehavior, di);
            this.wlsHelper.deployRoles(di, deployableRoleMapping, 1);
         }

      } catch (RoleCreationException | NoSuchRoleException | DeployHandleCreationException var6) {
         throw new WLDeploymentException("Error occured deploying roles.", var6);
      }
   }

   public void unDeployRoles(DeploymentInfo di) {
      if (this.jaccEnabled) {
         this.jaccHelper.unDeployRoles();
      } else {
         this.wlsHelper.unDeployRoles(di, 1);
      }

   }

   public void registerRoleRefs(String ejbName, Map secRoleRefs, DeploymentInfo di) throws WLDeploymentException {
      if (this.jaccEnabled) {
         try {
            this.jaccHelper.registerRoleRefs(ejbName, secRoleRefs, di);
         } catch (PolicyContextException var5) {
            throw new WLDeploymentException("Error registering role refs. ", var5);
         }
      }

   }

   public void deployAllPolicies() throws WLDeploymentException {
      try {
         if (!this.jaccEnabled) {
            this.wlsHelper.beginPolicyRegistration();
         }

         Iterator var1 = this.getEjbNames().iterator();

         while(var1.hasNext()) {
            String ejbName = (String)var1.next();
            if (debugLogger.isDebugEnabled()) {
               debug("registering policies for EJB: " + ejbName);
            }

            List checked = this.getCheckedMethodDescriptors(ejbName);
            if (debugLogger.isDebugEnabled() && checked != null) {
               debug("registering policies for all " + checked.size() + " checked methods");
            }

            List unchecked = this.getUncheckedMethodDescriptors(ejbName);
            if (debugLogger.isDebugEnabled() && unchecked != null) {
               debug("registering policies for all " + unchecked.size() + " unchecked methods");
            }

            List excluded = this.getExcludedMethodDescriptors(ejbName);
            if (debugLogger.isDebugEnabled() && excluded != null) {
               debug("registering policies for all " + excluded.size() + " excluded methods");
            }

            this.checkPolicies(ejbName, checked, unchecked, excluded);
            this.deployPolicies(checked, unchecked, excluded, 1);
         }

         if (!this.jaccEnabled) {
            this.wlsHelper.endPolicyRegistration();
         }

      } catch (ResourceCreationException | PrincipalNotFoundException | PolicyContextException | DeployHandleCreationException var6) {
         throw new WLDeploymentException("Error deploying policies. ", var6);
      }
   }

   private void checkPolicies(String ejbName, List checked, List unchecked, List excluded) {
      Set methodNames = new HashSet();
      Set alreadyWarned = new HashSet();
      Iterator var7;
      MethodDescriptor md;
      if (checked != null) {
         var7 = checked.iterator();

         while(var7.hasNext()) {
            md = (MethodDescriptor)var7.next();
            methodNames.add(md.getMethodName());
         }
      }

      if (unchecked != null) {
         var7 = unchecked.iterator();

         while(var7.hasNext()) {
            md = (MethodDescriptor)var7.next();
            methodNames.add(md.getMethodName());
         }
      }

      if (excluded != null) {
         var7 = excluded.iterator();

         while(var7.hasNext()) {
            md = (MethodDescriptor)var7.next();
            methodNames.add(md.getMethodName());
         }
      }

      var7 = methodNames.iterator();

      while(var7.hasNext()) {
         String n1 = (String)var7.next();
         Iterator var9 = methodNames.iterator();

         while(var9.hasNext()) {
            String n2 = (String)var9.next();
            if (!alreadyWarned.contains(n1) && !n1.equals(n2) && n1.equalsIgnoreCase(n2)) {
               alreadyWarned.add(n2);
               EJBLogger.logMethodNameIsConflicteddUnderCaseInsensitiveComparison(ejbName, n1, n2);
            }
         }
      }

   }

   private void deployPolicies(List checked, List unchecked, List excluded, int realm) throws PolicyContextException, PrincipalNotFoundException {
      if (this.jaccEnabled) {
         this.jaccHelper.deployPolicies(checked, unchecked, excluded, this);
      } else {
         this.wlsHelper.deployPolicies(checked, unchecked, excluded, this, realm);
      }

   }

   void unDeployAllPolicies() {
      if (!this.jaccEnabled) {
         this.wlsHelper.unDeployAllPolicies();
      }

   }

   public boolean processUncheckedExcludedMethod(MethodDescriptor md) throws WLDeploymentException {
      try {
         if (md.getMethodInfo().getUnchecked()) {
            this.addUncheckedMethod(md);
            createEJBResource(md);
            if (this.jaccEnabled) {
               this.jaccHelper.processUncheckedExcludedMethod(md);
            }

            return true;
         } else if (md.getMethodInfo().getIsExcluded()) {
            this.addExcludedMethod(md);
            createEJBResource(md);
            if (this.jaccEnabled) {
               this.jaccHelper.processUncheckedExcludedMethod(md);
            }

            return true;
         } else {
            this.addCheckedMethod(md);
            return false;
         }
      } catch (PolicyContextException var3) {
         throw new WLDeploymentException("Error processing policy. ", var3);
      }
   }

   public void activate() {
      if (this.jaccEnabled) {
         this.jaccHelper.activate();
      }

   }

   public void deactivate() {
      if (this.jaccEnabled) {
         this.jaccHelper.deactivate();
      } else {
         this.wlsHelper.unDeployAllPolicies();
      }

   }

   boolean fullyDelegateSecurityCheck() {
      return this.jaccEnabled ? true : this.wlsHelper.fullyDelegateSecurityCheck();
   }

   boolean isAccessAllowed(EJBResource res, EJBMethodPermission perm, ContextHandler ch) {
      return this.jaccEnabled ? this.jaccHelper.isAccessAllowed(perm, ch) : this.wlsHelper.isAccessAllowed(res, ch, 1);
   }

   public boolean isCallerInRole(String ejbName, String invokeRoleName, String derefRoleName) {
      AuthenticatedSubject subject = CallerSubjectStack.getCurrentSubject();
      if (subject == null) {
         if (debugLogger.isDebugEnabled()) {
            debug("isCallerInRole: Caller subject is null. isCallerInRole returns false");
         }

         return false;
      } else {
         return this.jaccEnabled ? this.jaccHelper.isCallerInRole(ejbName, subject, invokeRoleName) : this.wlsHelper.isCallerInRole(this.ejbResource, subject, derefRoleName, 1);
      }
   }

   private void addCheckedMethod(MethodDescriptor md) {
      List checked = (List)this.checkedMethodDescriptors.get(md.getEjbName());
      if (checked == null) {
         checked = new ArrayList();
         this.checkedMethodDescriptors.put(md.getEjbName(), checked);
      }

      ((List)checked).add(md);
   }

   private void addUncheckedMethod(MethodDescriptor md) {
      List unchecked = (List)this.uncheckedMethodDescriptors.get(md.getEjbName());
      if (unchecked == null) {
         unchecked = new ArrayList();
         this.uncheckedMethodDescriptors.put(md.getEjbName(), unchecked);
      }

      ((List)unchecked).add(md);
   }

   private void addExcludedMethod(MethodDescriptor md) {
      List excluded = (List)this.excludedMethodDescriptors.get(md.getEjbName());
      if (excluded == null) {
         excluded = new ArrayList();
         this.excludedMethodDescriptors.put(md.getEjbName(), excluded);
      }

      ((List)excluded).add(md);
   }

   private Set getEjbNames() {
      Set ejbNames = new HashSet();
      ejbNames.addAll(this.checkedMethodDescriptors.keySet());
      ejbNames.addAll(this.uncheckedMethodDescriptors.keySet());
      ejbNames.addAll(this.excludedMethodDescriptors.keySet());
      return ejbNames;
   }

   private List getCheckedMethodDescriptors(String ejbName) {
      return (List)this.checkedMethodDescriptors.get(ejbName);
   }

   private List getUncheckedMethodDescriptors(String ejbName) {
      return (List)this.uncheckedMethodDescriptors.get(ejbName);
   }

   private List getExcludedMethodDescriptors(String ejbName) {
      return (List)this.excludedMethodDescriptors.get(ejbName);
   }

   private PrincipalAuthenticator obtainPA(int realm) {
      switch (realm) {
         case 0:
            if (this.sysPrincipalAuth != null) {
               return this.sysPrincipalAuth;
            }

            this.sysPrincipalAuth = (PrincipalAuthenticator)SecurityServiceManager.getSecurityService(KERNEL_ID, this.sysRealmName, ServiceType.AUTHENTICATION);
            return this.sysPrincipalAuth;
         case 1:
            if (this.appPrincipalAuth != null) {
               return this.appPrincipalAuth;
            }

            this.appPrincipalAuth = (PrincipalAuthenticator)SecurityServiceManager.getSecurityService(KERNEL_ID, this.appRealmName, ServiceType.AUTHENTICATION);
            return this.appPrincipalAuth;
         default:
            throw new IllegalArgumentException("Unknown realm type: " + realm);
      }
   }

   static Principal getPrincipalFromSubject(AuthenticatedSubject s) {
      if (s == null) {
         return WLSPrincipals.getAnonymousUserPrincipal();
      } else {
         Principal p = SubjectUtils.getUserPrincipal(s);
         return p != null ? p : WLSPrincipals.getAnonymousUserPrincipal();
      }
   }

   static Principal getCurrentPrincipal() {
      return getPrincipalFromSubject(getCurrentSubject());
   }

   static AuthenticatedSubject getCurrentSubject() {
      return SecurityManager.getCurrentSubject(KERNEL_ID);
   }

   public static void pushAnonymousSubject(AuthenticatedSubject kernelId) {
      pushRunAsSubject(kernelId, SubjectUtils.getAnonymousSubject());
   }

   public static void pushRunAsSubject(AuthenticatedSubject kernelId, AuthenticatedSubject runAsSubject) {
      if (debugLogger.isDebugEnabled()) {
         debug("pushRunAsSubject to push: '" + runAsSubject.toString() + "', currentSubject is: '" + getCurrentSubject() + "' ");
      }

      SecurityManager.pushSubject(kernelId, runAsSubject);
   }

   public static void popRunAsSubject(AuthenticatedSubject kernelId) {
      if (debugLogger.isDebugEnabled()) {
         debug("popRunAsSubject,  subject before pop is: '" + getCurrentSubject() + "'");
      }

      SecurityManager.popSubject(kernelId);
      if (debugLogger.isDebugEnabled()) {
         debug("popRunAsSubject,  subject after  pop is: '" + getCurrentSubject() + "'");
      }

   }

   public static boolean pushSpecificRunAsMaybe(AuthenticatedSubject kernelId, AuthenticatedSubject opSpecificSubject, AuthenticatedSubject runAsSubject) {
      if (opSpecificSubject != null) {
         pushRunAsSubject(kernelId, opSpecificSubject);
         return true;
      } else if (runAsSubject != null) {
         pushRunAsSubject(kernelId, runAsSubject);
         return true;
      } else if (SecurityServiceManager.isKernelIdentity(getCurrentSubject())) {
         pushAnonymousSubject(kernelId);
         return true;
      } else {
         return false;
      }
   }

   static Principal getCallerPrincipal() throws PrincipalNotFoundException {
      Principal p = getPrincipalFromSubject(CallerSubjectStack.getCurrentSubject());
      if (p == null) {
         Loggable l = EJBLogger.logmissingCallerPrincipalLoggable("getCallerPrincipal");
         throw new PrincipalNotFoundException(l.getMessage());
      } else {
         return p;
      }
   }

   public static void pushCallerPrincipal(AuthenticatedSubject kernelId) {
      SecurityServiceManager.checkKernelIdentity(kernelId);
      pushCallerPrincipal();
   }

   public static void popCallerPrincipal(AuthenticatedSubject kernelId) throws PrincipalNotFoundException {
      SecurityServiceManager.checkKernelIdentity(kernelId);
      popCallerPrincipal();
   }

   static void pushCallerPrincipal() {
      AuthenticatedSubject s = getCurrentSubject();
      if (debugLogger.isDebugEnabled()) {
         debug("pushCallerPrincipal to push Subject: '" + s + "'  from which we get principal '" + getPrincipalFromSubject(s) + "'");
      }

      CallerSubjectStack.pushSubject(s);
   }

   static void popCallerPrincipal() throws PrincipalNotFoundException {
      if (debugLogger.isDebugEnabled()) {
         debug("popCallerPrincipal, CallerSubject before pop is: '" + CallerSubjectStack.getCurrentSubject() + "'");
      }

      AuthenticatedSubject s = CallerSubjectStack.popSubject();
      if (s == null) {
         Loggable l = EJBLogger.logmissingCallerPrincipalLoggable("popCallerPrincipal");
         throw new PrincipalNotFoundException(l.getMessage());
      }
   }

   static EJBResource createEJBResource(MethodDescriptor md) {
      String appName = md.getApplicationName();
      String moduleId = md.getModuleId();
      String ejbName = md.getEjbName();
      MethodInfo methodInfo = md.getMethodInfo();
      String[] methodParams = getCanonicalMethodParamNames(md.getMethod());
      if (debugLogger.isDebugEnabled()) {
         StringBuilder sb = new StringBuilder();
         if (methodParams.length > 0) {
            String[] var7 = methodParams;
            int var8 = methodParams.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               String methodParam = var7[var9];
               sb.append(methodParam).append(", ");
            }
         } else {
            sb.append(" NONE ");
         }

         debug("Creating EJBResource: application: '" + appName + "' moduleId: '" + moduleId + "' ejbName: '" + ejbName + "' methodName: '" + methodInfo.getMethodName() + "' interfaceType: '" + methodInfo.getMethodInterfaceType() + "' methodParams:     '" + sb.toString() + "'");
      }

      return new EJBResource(appName, moduleId, ejbName, methodInfo.getMethodName(), methodInfo.getMethodInterfaceType(), methodParams);
   }

   static String[] getCanonicalMethodParamNames(Method m) {
      Class[] params = m.getParameterTypes();
      String[] paramNames = new String[params.length];

      for(int i = 0; i < params.length; ++i) {
         paramNames[i] = params[i].getCanonicalName();
      }

      return paramNames;
   }

   private Map getDeployableSecurityRoleMapping(SecurityRoleMapping moduleRoleMap, ApplicationContextInternal aci, int roleMappingBehavior, DeploymentInfo di) throws NoSuchRoleException, WLDeploymentException {
      switch (roleMappingBehavior) {
         case 0:
            if (debugLogger.isDebugEnabled()) {
               debug("Deployable role map calculated for Compatibility mode");
            }

            return this.getCompatibilitySecurityRoleMapping(moduleRoleMap, di);
         case 1:
            if (debugLogger.isDebugEnabled()) {
               debug("Deployable role map calculated for Application mode");
            }

            return this.getApplicationSecurityRoleMapping(moduleRoleMap, aci, roleMappingBehavior);
         case 2:
            if (debugLogger.isDebugEnabled()) {
               debug("Deployable role map calculated for Externally Defined mode");
            }

            return this.getApplicationSecurityRoleMapping(moduleRoleMap, aci, roleMappingBehavior);
         default:
            throw new AssertionError("Unexpected role mapping behavior: " + roleMappingBehavior);
      }
   }

   private Map getCompatibilitySecurityRoleMapping(SecurityRoleMapping moduleRoleMap, DeploymentInfo di) throws NoSuchRoleException, WLDeploymentException {
      Map roleMap = new HashMap();
      Iterator var4 = moduleRoleMap.getSecurityRoleNames().iterator();

      while(true) {
         while(var4.hasNext()) {
            String role = (String)var4.next();
            if (!moduleRoleMap.isExternallyDefinedRole(role)) {
               if (!moduleRoleMap.isRoleMappedToPrincipals(role) && (!"**".equals(role) || di.isAnyAuthUserRoleDefinedInDD())) {
                  throw new WLDeploymentException(EJBComplianceTextFormatter.getInstance().ROLE_NOT_MAPPED_TO_PRINCIPALS(role));
               }

               Collection principals = moduleRoleMap.getSecurityRolePrincipalNames(role);
               roleMap.put(role, principals.toArray(new String[0]));
            } else if (debugLogger.isDebugEnabled()) {
               debug("Role '" + role + "' is externally defined; skipping deployment");
            }
         }

         return roleMap;
      }
   }

   private Map getApplicationSecurityRoleMapping(SecurityRoleMapping moduleRoleMap, ApplicationContextInternal aci, int roleMappingBehavior) throws NoSuchRoleException {
      Map roleMap = new HashMap();
      Iterator var5 = moduleRoleMap.getSecurityRoleNames().iterator();

      while(true) {
         while(var5.hasNext()) {
            String role = (String)var5.next();
            if (moduleRoleMap.isExternallyDefinedRole(role)) {
               if (debugLogger.isDebugEnabled()) {
                  debug("Role '" + role + "' is externally defined at module level; skipping deployment");
               }
            } else {
               SecurityRole sr = aci.getSecurityRole(role);
               if (!moduleRoleMap.isRoleMappedToPrincipals(role)) {
                  if (sr != null && sr.isExternallyDefined()) {
                     if (debugLogger.isDebugEnabled()) {
                        debug("Role '" + role + "' is externally defined at app level and no module principals defined; skipping deployment");
                     }
                  } else if (sr != null && sr.getPrincipalNames() != null && sr.getPrincipalNames().length != 0) {
                     roleMap.put(role, sr.getPrincipalNames());
                  } else if (roleMappingBehavior == 1) {
                     roleMap.put(role, new String[0]);
                  } else if (debugLogger.isDebugEnabled()) {
                     debug("Role '" + role + "' has no principals defined at app level or module level; skipping deployment");
                  }
               } else {
                  Collection principals;
                  if (sr != null && sr.isExternallyDefined()) {
                     principals = moduleRoleMap.getSecurityRolePrincipalNames(role);
                     roleMap.put(role, principals.toArray(new String[0]));
                  }

                  if (sr != null && sr.getPrincipalNames() != null && sr.getPrincipalNames().length != 0) {
                     Set principals = new HashSet();
                     principals.addAll(moduleRoleMap.getSecurityRolePrincipalNames(role));
                     principals.addAll(Arrays.asList(sr.getPrincipalNames()));
                     roleMap.put(role, principals.toArray(new String[0]));
                  } else {
                     principals = moduleRoleMap.getSecurityRolePrincipalNames(role);
                     roleMap.put(role, principals.toArray(new String[0]));
                  }
               }
            }
         }

         return roleMap;
      }
   }

   private static void debug(String s) {
      debugLogger.debug("[SecurityHelper] " + s);
   }

   static {
      debugLogger = EJBDebugService.securityLogger;
   }
}
