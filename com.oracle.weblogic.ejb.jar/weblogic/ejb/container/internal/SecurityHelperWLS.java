package weblogic.ejb.container.internal;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.MethodInfo;
import weblogic.ejb.container.interfaces.NoSuchRoleException;
import weblogic.ejb.spi.BusinessObject;
import weblogic.ejb20.interfaces.PrincipalNotFoundException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.AuthorizationManager;
import weblogic.security.service.AuthorizationManagerDeployHandle;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.DeployHandleCreationException;
import weblogic.security.service.EJBResource;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.ResourceCreationException;
import weblogic.security.service.ResourceRemovalException;
import weblogic.security.service.RoleCreationException;
import weblogic.security.service.RoleManager;
import weblogic.security.service.RoleManagerDeployHandle;
import weblogic.security.service.RoleRemovalException;
import weblogic.security.service.SecurityApplicationInfo;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.security.utils.ResourceIDDContextWrapper;

final class SecurityHelperWLS {
   private static final int SYSTEM_REALM = 0;
   private static final int APP_REALM = 1;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final DebugLogger debugLogger;
   private final String sysRealmName;
   private final String appRealmName;
   private SecurityApplicationInfo securityAppInfo;
   private RoleManager appRoleManager;
   private AuthorizationManager appAuthManager;
   private RoleManager sysRoleManager;
   private AuthorizationManager sysAuthManager;
   private RoleManagerDeployHandle roleMgrHandle;
   private AuthorizationManagerDeployHandle authMgrHandle;
   private boolean fullDelegation;
   private boolean customRoles;
   private EJBResource ejbRoleResource;

   SecurityHelperWLS(String appRealm, String sysRealm) {
      this.appRealmName = appRealm;
      this.sysRealmName = sysRealm;
   }

   void setupApplicationInfo(SecurityApplicationInfo securityAppInfo) {
      this.securityAppInfo = securityAppInfo;
      String model = securityAppInfo.getSecurityDDModel();
      this.customRoles = model.equals("CustomRoles") || model.equals("CustomRolesAndPolicies");
      this.fullDelegation = SecurityServiceManager.isFullAuthorizationDelegationRequired(this.appRealmName != null ? this.appRealmName : this.sysRealmName, securityAppInfo);
   }

   void deployRoles(DeploymentInfo di, Map roleMap, int realm) throws DeployHandleCreationException, NoSuchRoleException, RoleCreationException {
      if (debugLogger.isDebugEnabled()) {
         debug("Deploying Roles for Application Id: '" + di.getApplicationId() + "', Module Id: '" + di.getModuleId() + "'  there are: '" + roleMap.size() + "' roles for the module.");
      }

      RoleManager roleManager = this.obtainRM(realm);
      this.roleMgrHandle = roleManager.startDeployRoles(this.securityAppInfo);
      this.ejbRoleResource = this.createEJBResource(di);
      Iterator var5 = roleMap.entrySet().iterator();

      while(var5.hasNext()) {
         Map.Entry entry = (Map.Entry)var5.next();
         String roleName = (String)entry.getKey();
         String[] principals = (String[])entry.getValue();

         try {
            if (!"**".equals(roleName) || di.isAnyAuthUserRoleDefinedInDD() || principals != null && principals.length != 0) {
               if (debugLogger.isDebugEnabled()) {
                  debug("Deploying role: " + roleName + " with principals: " + Arrays.toString(principals));
               }

               roleManager.deployRole(this.roleMgrHandle, this.ejbRoleResource, roleName, principals);
            } else {
               if (debugLogger.isDebugEnabled()) {
                  debug("Deploying the ** role  with the 'users' group ");
               }

               roleManager.deployRole(this.roleMgrHandle, this.ejbRoleResource, roleName, new String[]{"users"});
            }
         } catch (RoleCreationException var10) {
            throw new NoSuchRoleException("registerEjbRolesAndUsers: Exception while attempting to deploy Security Role: " + var10.toString());
         }
      }

      roleManager.endDeployRoles(this.roleMgrHandle);
      if (debugLogger.isDebugEnabled()) {
         debug("Done with role deployment for Application Id: '" + di.getApplicationId() + "', Module Id: '" + di.getModuleId() + "'");
      }

   }

   void unDeployRoles(DeploymentInfo di, int realm) {
      this.createEJBResource(di);
      RoleManager roleManager = this.obtainRM(realm);
      if (this.roleMgrHandle != null) {
         try {
            roleManager.undeployAllRoles(this.roleMgrHandle);
         } catch (RoleRemovalException var5) {
            EJBLogger.logFailedToUndeploySecurityRole(di.getApplicationId() + " - " + di.getModuleId(), var5);
         }

      }
   }

   void beginPolicyRegistration() throws DeployHandleCreationException {
      AuthorizationManager authManager = this.obtainAM(1);
      this.authMgrHandle = authManager.startDeployPolicies(this.securityAppInfo);
   }

   void endPolicyRegistration() throws ResourceCreationException {
      AuthorizationManager authManager = this.obtainAM(1);
      authManager.endDeployPolicies(this.authMgrHandle);
   }

   void deployPolicies(List checked, List unchecked, List excluded, SecurityHelper securityHelper, int realm) throws PrincipalNotFoundException {
      AuthorizationManager authManager = this.obtainAM(realm);
      if (checked != null) {
         boolean deployed = this.deployOptimizedPolicy(checked, securityHelper, authManager);
         if (!deployed) {
            Iterator var8 = checked.iterator();

            while(var8.hasNext()) {
               MethodDescriptor md = (MethodDescriptor)var8.next();
               this.deployPolicy(md, securityHelper, authManager);
            }
         }
      }

      Iterator var10;
      MethodDescriptor md;
      if (unchecked != null) {
         var10 = unchecked.iterator();

         while(var10.hasNext()) {
            md = (MethodDescriptor)var10.next();
            this.deployPolicy(md, securityHelper, authManager);
         }
      }

      if (excluded != null) {
         var10 = excluded.iterator();

         while(var10.hasNext()) {
            md = (MethodDescriptor)var10.next();
            this.deployPolicy(md, securityHelper, authManager);
         }
      }

   }

   private boolean deployOptimizedPolicy(List checked, SecurityHelper securityHelper, AuthorizationManager authManager) throws PrincipalNotFoundException {
      if (checked != null && !checked.isEmpty()) {
         List businessObjectMethods = new ArrayList();
         MethodDescriptor first = null;
         Iterator var6 = checked.iterator();

         while(var6.hasNext()) {
            MethodDescriptor md = (MethodDescriptor)var6.next();
            if (first == null) {
               first = md;
            } else {
               if (!first.getEjbName().equals(md.getEjbName())) {
                  return false;
               }

               Set roles1 = first.getMethodInfo().getSecurityRoleNames();
               Set roles2 = md.getMethodInfo().getSecurityRoleNames();
               if (!roles1.equals(roles2)) {
                  if (first.getMethod().getDeclaringClass() == BusinessObject.class) {
                     businessObjectMethods.add(first);
                     first = md;
                  } else {
                     if (md.getMethod().getDeclaringClass() != BusinessObject.class) {
                        return false;
                     }

                     businessObjectMethods.add(md);
                  }
               }
            }
         }

         String appName = first.getApplicationName();
         String moduleId = first.getModuleId();
         String ejbName = first.getEjbName();
         EJBResource ejbRes = this.createEJBResource(appName, moduleId, ejbName);
         Iterator var10 = checked.iterator();

         while(var10.hasNext()) {
            MethodDescriptor md = (MethodDescriptor)var10.next();
            md.setSecurityHelper(securityHelper);
            md.setEJBResource(SecurityHelper.createEJBResource(md));
         }

         if (debugLogger.isDebugEnabled()) {
            debug("Register optimized EJB Role restrictions for application: '" + appName + "', moduleId: '" + moduleId + "', ejbName: '" + ejbName);
         }

         MethodInfo methodInfo = first.getMethodInfo();
         this.deployPolicy(ejbRes, methodInfo.getSecurityRoleNames(), this.fullDelegation || methodInfo.hasRoles(), methodInfo.getUnchecked(), methodInfo.getIsExcluded(), authManager);
         Iterator var18 = businessObjectMethods.iterator();

         while(var18.hasNext()) {
            MethodDescriptor md = (MethodDescriptor)var18.next();
            this.deployPolicy(md, securityHelper, authManager);
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean deployPolicy(MethodDescriptor md, SecurityHelper securityHelper, AuthorizationManager authManager) throws PrincipalNotFoundException {
      md.setSecurityHelper(securityHelper);
      EJBResource ejbRes = SecurityHelper.createEJBResource(md);
      md.setEJBResource(ejbRes);
      MethodInfo methodInfo = md.getMethodInfo();
      if (debugLogger.isDebugEnabled()) {
         debug("Registering EJB Role restrictions for appName: '" + md.getApplicationName() + "', moduleId: '" + md.getModuleId() + "', ejbName: '" + md.getEjbName() + "', methodName: '" + methodInfo.getMethodName() + "', methodInterface: '" + methodInfo.getMethodInterfaceType());
      }

      return this.deployPolicy(ejbRes, methodInfo.getSecurityRoleNames(), this.fullDelegation || methodInfo.hasRoles(), methodInfo.getUnchecked(), methodInfo.getIsExcluded(), authManager);
   }

   boolean deployPolicy(EJBResource ejbRes, Set roles, boolean needsSecurityCheck, boolean isUnchecked, boolean isExcluded, AuthorizationManager authManager) throws PrincipalNotFoundException {
      try {
         boolean var7;
         if (!needsSecurityCheck) {
            var7 = false;
            return var7;
         }

         try {
            if (roles.isEmpty()) {
               if (debugLogger.isDebugEnabled()) {
                  debug("Count of restrictable roles in policy = " + roles.size() + ", so skipping authManager.deployPolicy");
               }

               var7 = true;
               return var7;
            }

            authManager.deployPolicy(this.authMgrHandle, ejbRes, (String[])roles.toArray(new String[roles.size()]));
         } catch (ResourceCreationException var18) {
            throw new PrincipalNotFoundException("Exception while attempting to deploy Security Policy:  " + var18.toString());
         }
      } finally {
         try {
            if (isUnchecked) {
               if (debugLogger.isDebugEnabled()) {
                  debug("Deploying an unchecked policy");
               }

               authManager.deployUncheckedPolicy(this.authMgrHandle, ejbRes);
            } else if (isExcluded) {
               if (debugLogger.isDebugEnabled()) {
                  debug("Deploying an excluded policy");
               }

               authManager.deployExcludedPolicy(this.authMgrHandle, ejbRes);
            }
         } catch (ResourceCreationException var17) {
            throw new PrincipalNotFoundException("Exception while attempting to deploy Unchecked or Excluded Security Policy:  " + var17.toString());
         }

      }

      if (debugLogger.isDebugEnabled()) {
         debug("Registered EJB Role restrictions with Policy Manager");
      }

      return true;
   }

   void unDeployAllPolicies() {
      this.unDeployAllPolicies(1);
   }

   void unDeployAllPolicies(int realm) {
      if (this.authMgrHandle != null) {
         AuthorizationManager authManager = this.obtainAM(realm);

         try {
            authManager.undeployAllPolicies(this.authMgrHandle);
         } catch (ResourceRemovalException var4) {
            EJBLogger.logFailedToUndeploySecurityPolicy("All EJBs in Application", var4);
         }

      }
   }

   boolean isAccessAllowed(EJBResource res, ContextHandler ch) {
      return this.isAccessAllowed(res, ch, 1);
   }

   boolean isAccessAllowed(EJBResource res, ContextHandler ch, int realm) {
      AuthorizationManager authManager = this.obtainAM(realm);
      AuthenticatedSubject currSubject = SecurityHelper.getCurrentSubject();
      if (debugLogger.isDebugEnabled()) {
         debug("Checking Method Permission for ejb: '" + res + "' with Subject: " + currSubject);
      }

      return authManager.isAccessAllowed(currSubject, res, new ResourceIDDContextWrapper(ch));
   }

   boolean isCallerInRole(EJBResource res, AuthenticatedSubject subject, String roleName) {
      return this.isCallerInRole(res, subject, roleName, 1);
   }

   boolean isCallerInRole(EJBResource res, AuthenticatedSubject subject, String roleName, int realm) {
      RoleManager roleManager = this.obtainRM(realm);
      Map securityRoles = roleManager.getRoles(subject, res, new ResourceIDDContextWrapper());
      if (securityRoles != null && !securityRoles.isEmpty()) {
         if (debugLogger.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder();
            Iterator it = securityRoles.keySet().iterator();

            while(it.hasNext()) {
               sb.append((String)it.next()).append(", ");
            }

            debug("isCallerInRole:  check securityRoles for resource; '" + res + "', subject: '" + subject + ", candidate role name '" + roleName + "'roles mapped to this subject are: '" + sb.toString() + "''  isCallerInRole returns " + SecurityServiceManager.isUserInRole(subject, roleName, securityRoles));
         }

         return SecurityServiceManager.isUserInRole(subject, roleName, securityRoles);
      } else {
         if (debugLogger.isDebugEnabled()) {
            debug("isCallerInRole:  securityRoles for resource; '" + res + "', Caller subject: '" + subject + ", role name '" + roleName + "' there are no roles mapped to this subject.'  isCallerInRole returns false");
         }

         return false;
      }
   }

   boolean fullyDelegateSecurityCheck() {
      return this.fullDelegation;
   }

   private RoleManager obtainRM(int realm) {
      switch (realm) {
         case 0:
            if (this.sysRoleManager != null) {
               return this.sysRoleManager;
            }

            this.sysRoleManager = (RoleManager)SecurityServiceManager.getSecurityService(KERNEL_ID, this.sysRealmName, ServiceType.ROLE);
            return this.sysRoleManager;
         case 1:
            if (this.appRoleManager != null) {
               return this.appRoleManager;
            }

            this.appRoleManager = (RoleManager)SecurityServiceManager.getSecurityService(KERNEL_ID, this.appRealmName, ServiceType.ROLE);
            return this.appRoleManager;
         default:
            throw new IllegalArgumentException("Unknown realm type: " + realm);
      }
   }

   private AuthorizationManager obtainAM(int realm) {
      switch (realm) {
         case 0:
            if (this.sysAuthManager != null) {
               return this.sysAuthManager;
            }

            this.sysAuthManager = (AuthorizationManager)SecurityServiceManager.getSecurityService(KERNEL_ID, this.sysRealmName, ServiceType.AUTHORIZE);
            return this.sysAuthManager;
         case 1:
            if (this.appAuthManager != null) {
               return this.appAuthManager;
            }

            this.appAuthManager = (AuthorizationManager)SecurityServiceManager.getSecurityService(KERNEL_ID, this.appRealmName, ServiceType.AUTHORIZE);
            return this.appAuthManager;
         default:
            throw new IllegalArgumentException("Unknown realm type: " + realm);
      }
   }

   private EJBResource createEJBResource(DeploymentInfo di) {
      return new EJBResource(di.getApplicationId(), di.getModuleId(), (String)null, (String)null, (String)null, (String[])null);
   }

   private EJBResource createEJBResource(String appName, String moduleId, String ejbName) {
      return new EJBResource(appName, moduleId, ejbName, (String)null, (String)null, (String[])null);
   }

   private static void debug(String s) {
      debugLogger.debug("[SecurityHelperWLS] " + s);
   }

   static {
      debugLogger = EJBDebugService.securityLogger;
   }
}
