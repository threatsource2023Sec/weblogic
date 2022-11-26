package weblogic.ejb.container.internal;

import java.net.URL;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Policy;
import java.security.Principal;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;
import java.security.cert.Certificate;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.security.jacc.EJBMethodPermission;
import javax.security.jacc.EJBRoleRefPermission;
import javax.security.jacc.PolicyConfiguration;
import javax.security.jacc.PolicyContext;
import javax.security.jacc.PolicyContextException;
import javax.security.jacc.PolicyContextHandler;
import weblogic.application.ApplicationContext;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.MethodInfo;
import weblogic.ejb.container.interfaces.NoSuchRoleException;
import weblogic.ejb.container.interfaces.SecurityRoleMapping;
import weblogic.ejb.container.interfaces.SecurityRoleReference;
import weblogic.j2ee.descriptor.AssemblyDescriptorBean;
import weblogic.j2ee.descriptor.SecurityRoleBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.jacc.CommonPolicyContextHandler;
import weblogic.security.jacc.DelegatingPolicyContextHandler;
import weblogic.security.jacc.PolicyContextHandlerData;
import weblogic.security.jacc.PolicyContextManager;
import weblogic.security.jacc.RoleMapper;
import weblogic.security.service.ContextHandler;
import weblogic.utils.StackTraceUtilsClient;

final class SecurityHelperJACC {
   private static final String DONT_REGISTER_UNCOVERED_METHODS = "weblogic.ejb.container.internal.SecurityHelperJACC.dont_register_uncovered_methods";
   private static final boolean dont_register_uncovered_methods = System.getProperty("weblogic.ejb.container.internal.SecurityHelperJACC.dont_register_uncovered_methods") != null;
   private static final DebugLogger debugLogger;
   private final String jaccPolicyContextId;
   private final CodeSource jaccCodeSource;
   private final PolicyConfiguration jaccPolicyConfig;
   private final RoleMapper jaccRoleMapper;
   private HashSet roleNamesSet = new HashSet();

   SecurityHelperJACC(PolicyConfiguration jaccPolicyConfig, String jaccPolicyContextId, URL codeSoureURL, RoleMapper jaccRoleMapper) {
      this.jaccPolicyConfig = jaccPolicyConfig;
      this.jaccPolicyContextId = jaccPolicyContextId;
      this.jaccRoleMapper = jaccRoleMapper;
      this.jaccCodeSource = new CodeSource(codeSoureURL, (Certificate[])null);
   }

   void pushSecurityContext(ContextHandler ch) {
      PolicyContextManager.setPolicyContext((PolicyContextHandlerData)ch);
      PolicyContextManager.setContextID(this.jaccPolicyContextId);
   }

   void popSecurityContext() {
      PolicyContextManager.resetPolicyContext();
      PolicyContextManager.resetContextID();
   }

   void deployRoles(DeploymentInfo di, SecurityRoleMapping roleMap) throws NoSuchRoleException {
      Collection roleNames = roleMap.getSecurityRoleNames();
      if (debugLogger.isDebugEnabled()) {
         debug("deployRoles(...), Application Id: '" + di.getApplicationId() + "', Module Id: '" + di.getModuleId() + "'  there are: '" + roleNames.size() + "' roles in this jar.");
      }

      if (!roleNames.isEmpty()) {
         Map deployableRoleMap = new HashMap();
         Iterator var5 = roleNames.iterator();

         while(true) {
            while(var5.hasNext()) {
               String roleName = (String)var5.next();
               if (roleMap.isExternallyDefinedRole(roleName)) {
                  if (debugLogger.isDebugEnabled()) {
                     debug("skipping deployment of role: " + roleName + " because it's externally defined");
                  }
               } else if (!roleMap.isRoleMappedToPrincipals(roleName) && !"**".equals(roleName)) {
                  if (debugLogger.isDebugEnabled()) {
                     debug("skipping deployment of role: " + roleName + " because it's not mapped to any principals");
                  }
               } else {
                  Collection principals = roleMap.getSecurityRolePrincipalNames(roleName);
                  if (debugLogger.isDebugEnabled()) {
                     debug("deploying role: " + roleName + " with principals: " + principals);
                  }

                  deployableRoleMap.put(roleName, principals.toArray(new String[0]));
               }
            }

            if (!deployableRoleMap.isEmpty()) {
               this.jaccRoleMapper.addAppRolesToPrincipalMap(deployableRoleMap);
               if (debugLogger.isDebugEnabled()) {
                  debug("Role mapping to add to the RoleMapper for Application Id: '" + di.getApplicationId() + "', Module Id: '" + di.getModuleId() + "'");
               }
            } else if (debugLogger.isDebugEnabled()) {
               debug("No Role mapping to add to the RoleMapper for Application Id: '" + di.getApplicationId() + "', Module Id: '" + di.getModuleId() + "'");
            }

            return;
         }
      }
   }

   void setupApplicationInfo(ApplicationContext appCtx) {
      appCtx.addJACCPolicyConfiguration(this.jaccPolicyConfig);
   }

   void unDeployRoles() {
   }

   void deployPolicies(List checked, List unchecked, List excluded, SecurityHelper securityHelper) throws PolicyContextException {
      Iterator var5;
      MethodDescriptor md;
      if (checked != null) {
         var5 = checked.iterator();

         while(var5.hasNext()) {
            md = (MethodDescriptor)var5.next();
            this.deployPolicy(md, securityHelper);
         }
      }

      if (unchecked != null) {
         var5 = unchecked.iterator();

         while(var5.hasNext()) {
            md = (MethodDescriptor)var5.next();
            this.deployPolicy(md, securityHelper);
         }
      }

      if (excluded != null) {
         var5 = excluded.iterator();

         while(var5.hasNext()) {
            md = (MethodDescriptor)var5.next();
            this.deployPolicy(md, securityHelper);
         }
      }

   }

   boolean deployPolicy(MethodDescriptor md, SecurityHelper securityHelper) throws PolicyContextException {
      MethodInfo methodInfo = md.getMethodInfo();
      Collection roles = methodInfo.getSecurityRoleNames();
      EJBMethodPermission ejbPerm = this.createEJBMethodPermission(md);
      md.setSecurityHelper(securityHelper);
      md.setEJBMethodPermission(ejbPerm);
      String roleName;
      if (roles.isEmpty()) {
         if (debugLogger.isDebugEnabled()) {
            debug(" no policy for " + ejbPerm);
         }

         if (!dont_register_uncovered_methods && !methodInfo.getIsExcluded()) {
            if (debugLogger.isDebugEnabled()) {
               debug("  deploying uncovered method as 'unchecked': '" + ejbPerm + "'");
            }

            this.jaccPolicyConfig.addToUncheckedPolicy(ejbPerm);
         }
      } else {
         for(Iterator var6 = roles.iterator(); var6.hasNext(); this.jaccPolicyConfig.addToRole(roleName, ejbPerm)) {
            roleName = (String)var6.next();
            if (debugLogger.isDebugEnabled()) {
               debug("  next roleName is: '" + roleName + "'");
            }

            if (debugLogger.isDebugEnabled()) {
               debug("registerRolesWithMethod, jaccPolicyConfig.addToRole " + roleName + ", " + ejbPerm);
            }
         }
      }

      if (methodInfo.getUnchecked()) {
         this.jaccPolicyConfig.addToUncheckedPolicy(ejbPerm);
      }

      if (methodInfo.getIsExcluded()) {
         this.jaccPolicyConfig.addToExcludedPolicy(ejbPerm);
      }

      return true;
   }

   void processUncheckedExcludedMethod(MethodDescriptor md) throws PolicyContextException {
      MethodInfo methodInfo = md.getMethodInfo();
      if (methodInfo.getUnchecked()) {
         this.jaccPolicyConfig.addToUncheckedPolicy(this.createEJBMethodPermission(md));
      } else if (methodInfo.getIsExcluded()) {
         this.jaccPolicyConfig.addToExcludedPolicy(this.createEJBMethodPermission(md));
      }

   }

   void registerRoleRefs(String ejbName, Map secRoleRefs, DeploymentInfo di) throws PolicyContextException {
      if (this.roleNamesSet.isEmpty()) {
         AssemblyDescriptorBean ad = di.getEjbDescriptorBean().getEjbJarBean().getAssemblyDescriptor();
         if (ad != null) {
            SecurityRoleBean[] var5 = ad.getSecurityRoles();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               SecurityRoleBean sr = var5[var7];
               this.roleNamesSet.add(sr.getRoleName());
            }
         }

         this.roleNamesSet.add("**");
      }

      Set srrRoleNames = new HashSet();
      Iterator var10 = secRoleRefs.entrySet().iterator();

      while(var10.hasNext()) {
         Map.Entry me = (Map.Entry)var10.next();
         String roleName = (String)me.getKey();
         srrRoleNames.add(roleName);
         this.jaccPolicyConfig.addToRole(((SecurityRoleReference)me.getValue()).getReferencedRole(), new EJBRoleRefPermission(ejbName, roleName));
      }

      var10 = this.roleNamesSet.iterator();

      while(var10.hasNext()) {
         String s = (String)var10.next();
         if (!srrRoleNames.contains(s)) {
            this.jaccPolicyConfig.addToRole(s, new EJBRoleRefPermission(ejbName, s));
         }
      }

   }

   void activate() {
   }

   void deactivate() {
      try {
         this.jaccPolicyConfig.delete();
      } catch (PolicyContextException var2) {
         if (debugLogger.isDebugEnabled()) {
            debug("Error occured deleting PolicyConfiguration - " + StackTraceUtilsClient.throwable2StackTrace(var2));
         }
      }

   }

   boolean isAccessAllowed(EJBMethodPermission perm, ContextHandler ch) {
      AuthenticatedSubject currSubject = SecurityHelper.getCurrentSubject();
      Principal[] principals;
      if (currSubject != null) {
         principals = new Principal[currSubject.getPrincipals().size()];
         currSubject.getPrincipals().toArray(principals);
      } else {
         principals = new Principal[0];
      }

      ProtectionDomain pd = new ProtectionDomain(this.jaccCodeSource, (PermissionCollection)null, (ClassLoader)null, principals);
      boolean ret = false;

      try {
         ret = this.implies(perm, pd);
      } catch (SecurityException var8) {
         EJBLogger.logStackTrace(var8);
         ret = false;
      }

      return ret;
   }

   boolean isCallerInRole(String ejbName, AuthenticatedSubject subject, String roleName) {
      ProtectionDomain pd = this.getProtectionDomainForSubject(subject);
      EJBRoleRefPermission perm = new EJBRoleRefPermission(ejbName, roleName);
      boolean ret = false;

      try {
         ret = this.implies(perm, pd);
         return ret;
      } catch (SecurityException var8) {
         EJBLogger.logStackTrace(var8);
         return false;
      }
   }

   private boolean implies(Permission perm, ProtectionDomain pd) {
      String oldCtx = PolicyContext.getContextID();

      boolean var4;
      try {
         this.setPolicyContext(this.jaccPolicyContextId);
         if (debugLogger.isDebugEnabled()) {
            debug("about to call Policy.getPolicy().implies on ProtectionDomain: " + pd + ", permission: " + perm);
         }

         var4 = Policy.getPolicy().implies(pd, perm);
      } finally {
         this.setPolicyContext(oldCtx);
      }

      return var4;
   }

   private void setPolicyContext(final String ctxID) {
      String oldCtx = PolicyContext.getContextID();
      if (oldCtx != ctxID && (oldCtx == null || ctxID == null || !oldCtx.equals(ctxID))) {
         PolicyContext.setContextID(ctxID);

         try {
            AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Object run() {
                  PolicyContext.setContextID(ctxID);
                  return null;
               }
            });
         } catch (PrivilegedActionException var4) {
            EJBLogger.logStackTraceAndMessage("Unexpected exception setting policy context id", var4.getCause());
         }
      } else if (debugLogger.isDebugEnabled()) {
         debug("#### setPolicyContext(): Policy Context ID was the same: " + oldCtx);
      }

   }

   private ProtectionDomain getProtectionDomainForSubject(AuthenticatedSubject subject) {
      Principal[] principals;
      if (subject != null) {
         principals = new Principal[subject.getPrincipals().size()];
         subject.getPrincipals().toArray(principals);
      } else {
         principals = new Principal[0];
      }

      return new ProtectionDomain(this.jaccCodeSource, (PermissionCollection)null, (ClassLoader)null, principals);
   }

   private EJBMethodPermission createEJBMethodPermission(MethodDescriptor md) {
      String ejbName = md.getEjbName();
      MethodInfo methodInfo = md.getMethodInfo();
      String[] methodParams = SecurityHelper.getCanonicalMethodParamNames(md.getMethod());
      int i;
      if (debugLogger.isDebugEnabled()) {
         StringBuilder sb = new StringBuilder();
         if (methodParams.length > 0) {
            String[] var6 = methodParams;
            i = methodParams.length;

            for(int var8 = 0; var8 < i; ++var8) {
               String mp = var6[var8];
               sb.append(mp).append(", ");
            }
         } else {
            sb.append("");
         }

         debug("Creating EJBMethodPermission: ejbName: '" + ejbName + "' methodName: '" + methodInfo.getMethodName() + "' interfaceType: '" + methodInfo.getMethodInterfaceType() + "' methodParams: '" + sb.toString() + "'");
      }

      short methodType = methodInfo.getMethodDescriptorMethodType();
      if (methodType == 1) {
         return new EJBMethodPermission(ejbName, "");
      } else {
         StringBuilder sb = new StringBuilder(methodInfo.getMethodName());
         sb.append(",").append(methodInfo.getMethodInterfaceType());
         if (methodType == 2) {
            return new EJBMethodPermission(ejbName, sb.toString());
         } else {
            for(i = 0; i < methodParams.length; ++i) {
               if (i == 0) {
                  sb.append(",");
               }

               sb.append(methodParams[i]);
            }

            return new EJBMethodPermission(ejbName, sb.toString());
         }
      }
   }

   private static void debug(String s) {
      debugLogger.debug("[SecurityHelperJACC] " + s);
   }

   static {
      debugLogger = EJBDebugService.securityLogger;
      PolicyContextHandler commonCtxHdlr = new CommonPolicyContextHandler();
      String[] ejbKeys = EJBContextHandler.getKeys();
      PolicyContextHandler ejbCtxHdlr = new DelegatingPolicyContextHandler(ejbKeys);

      try {
         PolicyContext.registerHandler("javax.security.auth.Subject.container", commonCtxHdlr, true);
         String[] var3 = ejbKeys;
         int var4 = ejbKeys.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String ejbKey = var3[var5];
            PolicyContext.registerHandler(ejbKey, ejbCtxHdlr, true);
         }
      } catch (PolicyContextException var7) {
         EJBLogger.logFailedToRegisterPolicyContextHandlers(var7);
      }

   }
}
