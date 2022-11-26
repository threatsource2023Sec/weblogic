package weblogic.ejb.container.deployer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.SecurityRole;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.compliance.EJBComplianceTextFormatter;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.ISecurityHelper;
import weblogic.ejb.container.interfaces.NoSuchRoleException;
import weblogic.ejb.container.interfaces.SecurityRoleMapping;
import weblogic.ejb.container.internal.MethodDescriptor;
import weblogic.ejb.container.internal.SecurityHelper;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.ejb20.interfaces.PrincipalNotFoundException;
import weblogic.logging.Loggable;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.SecurityApplicationInfo;
import weblogic.security.service.SecurityApplicationInfoImpl;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.spi.ApplicationInfo.ComponentType;
import weblogic.t3.srvr.ServerRuntime;

final class RuntimeHelper {
   private static final DebugLogger debugLogger;
   private final SecurityHelper helper;
   private final DeploymentInfo di;
   private final int roleMappingBehavior;
   private final ApplicationContextInternal appCtx;
   private Map principal2Subject;

   RuntimeHelper(DeploymentInfo dinfo, ApplicationContextInternal appCtx, AuthenticatedSubject kernelId) {
      this.di = dinfo;
      this.appCtx = appCtx;
      SecurityApplicationInfo securityAppInfo = new SecurityApplicationInfoImpl(appCtx.getAppDeploymentMBean(), ComponentType.EJB, this.di.getModuleId());
      this.helper = SecurityHelper.newInstanceFor(this.di, kernelId);
      this.helper.setupApplicationInfo(appCtx, securityAppInfo);
      this.roleMappingBehavior = SecurityServiceManager.getRoleMappingBehavior(this.di.getSecurityRealmName(), securityAppInfo);
   }

   ISecurityHelper getSecurityHelper() {
      return this.helper;
   }

   AuthenticatedSubject getRunAsSubject(String runAsPrincipalName) throws PrincipalNotFoundException {
      if (runAsPrincipalName == null) {
         return null;
      } else {
         if (this.principal2Subject == null) {
            this.principal2Subject = new HashMap();
         }

         AuthenticatedSubject s = (AuthenticatedSubject)this.principal2Subject.get(runAsPrincipalName);
         if (s == null) {
            s = this.helper.getSubjectForPrincipal(runAsPrincipalName);
            this.principal2Subject.put(runAsPrincipalName, s);
         }

         return s;
      }
   }

   void checkRunAsPrivileges(BeanInfo bi) throws WLDeploymentException {
      AuthenticatedSubject deployer = this.appCtx.getDeploymentInitiator();
      if (!SubjectUtils.isUserAnAdministrator(deployer) && (ServerRuntime.theOne().getStateVal() != 1 || !SubjectUtils.isUserAnonymous(deployer))) {
         this.checkRunAsPrivilege(deployer, bi.getRunAsPrincipalName(), "run", bi);
         this.checkRunAsPrivilege(deployer, bi.getCreateAsPrincipalName(), "create", bi);
         this.checkRunAsPrivilege(deployer, bi.getRemoveAsPrincipalName(), "remove", bi);
         this.checkRunAsPrivilege(deployer, bi.getPassivateAsPrincipalName(), "passivate", bi);
      }

   }

   private void checkRunAsPrivilege(AuthenticatedSubject deployer, String principalName, String type, BeanInfo bi) throws WLDeploymentException {
      if (principalName != null) {
         Loggable l;
         try {
            AuthenticatedSubject subject = this.helper.getSubjectForPrincipal(principalName);
            if (subject != null && SubjectUtils.isAdminPrivilegeEscalation(deployer, subject)) {
               l = EJBLogger.logAttemptToBumpUpPrivilegesWithRunAsLoggable(bi.getDisplayName(), type);
               throw new WLDeploymentException(l.getMessageText());
            }
         } catch (PrincipalNotFoundException var7) {
            l = EJBLogger.logRunAsPrincipalNotFoundLoggable(bi.getDisplayName(), type, principalName);
            throw new WLDeploymentException(l.getMessageText());
         }
      }
   }

   void registerRoleRefs(String ejbName, Map secRoleRefs) throws WLDeploymentException {
      this.helper.registerRoleRefs(ejbName, secRoleRefs, this.di);
   }

   boolean processUncheckedExcludedMethod(MethodDescriptor md) throws WLDeploymentException {
      return this.helper.processUncheckedExcludedMethod(md);
   }

   void deployRoles() throws WLDeploymentException {
      SecurityRoleMapping mapping = this.di.getDeploymentRoles();
      if (!mapping.getSecurityRoleNames().isEmpty()) {
         this.helper.deployRoles(this.di, mapping, this.appCtx, this.roleMappingBehavior);
      }

   }

   void unDeployRoles() {
      SecurityRoleMapping mapping = this.di.getDeploymentRoles();
      if (!mapping.getSecurityRoleNames().isEmpty()) {
         this.helper.unDeployRoles(this.di);
      }

   }

   void activate() {
      this.helper.activate();
   }

   void deactivate() {
      this.helper.deactivate();
   }

   boolean isUserPrincipal(String principalName) {
      if (principalName == null) {
         return false;
      } else {
         try {
            return this.helper.getSubjectForPrincipal(principalName) != null;
         } catch (PrincipalNotFoundException var3) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("[RuntimeHelper] \"" + principalName + "\" is failed to be authenticated. ", var3);
            }

            return false;
         }
      }
   }

   void deployAllPolicies() throws WLDeploymentException {
      this.helper.deployAllPolicies();
   }

   String getRunAsPrincipalFromRoleMapping(String ejbName, String runAsRole, SecurityRoleMapping moduleRoleMap) throws WLDeploymentException {
      if (debugLogger.isDebugEnabled()) {
         debug("attempting to get the run-as principal for run-as role " + runAsRole + " from a security-role assignment for the role.");
      }

      String candidate = null;
      switch (this.roleMappingBehavior) {
         case 0:
            Collection principals = null;

            try {
               principals = moduleRoleMap.getSecurityRolePrincipalNames(runAsRole);
            } catch (NoSuchRoleException var10) {
               throw new AssertionError("Expected role in mapping");
            }

            for(Iterator var11 = principals.iterator(); var11.hasNext(); candidate = null) {
               String principal = (String)var11.next();
               candidate = principal;
               if (this.isUserPrincipal(principal)) {
                  EJBLogger.logRunAsPrincipalChosenFromSecurityRoleAssignment(ejbName, runAsRole, principal);
                  break;
               }
            }

            if (candidate == null) {
               throw new WLDeploymentException(EJBComplianceTextFormatter.getInstance().COULD_NOT_DETERMINE_RUN_AS_PRINCIPAL_FROM_ROLE_ASSIGNMENT(ejbName, runAsRole));
            } else {
               return candidate;
            }
         case 1:
         case 2:
            Collection modulePrincipals = null;

            try {
               modulePrincipals = moduleRoleMap.getSecurityRolePrincipalNames(runAsRole);
            } catch (NoSuchRoleException var9) {
               throw new AssertionError("Expected role in mapping");
            }

            if (!modulePrincipals.isEmpty()) {
               candidate = (String)modulePrincipals.iterator().next();
               EJBLogger.logRunAsPrincipalChosenFromSecurityRoleAssignment(ejbName, runAsRole, candidate);
               return candidate;
            } else {
               String[] appPrincipals = null;
               SecurityRole sr = this.appCtx.getSecurityRole(runAsRole);
               if (sr != null) {
                  appPrincipals = sr.getPrincipalNames();
               }

               if (appPrincipals != null && appPrincipals.length > 0) {
                  candidate = appPrincipals[0];
                  EJBLogger.logRunAsPrincipalChosenFromSecurityRoleAssignment(ejbName, runAsRole, candidate);
                  return candidate;
               } else {
                  if (this.roleMappingBehavior == 1) {
                     throw new WLDeploymentException(EJBComplianceTextFormatter.getInstance().COULD_NOT_DETERMINE_RUN_AS_PRINCIPAL_FROM_ROLE_ASSIGNMENT(ejbName, runAsRole));
                  }

                  if (debugLogger.isDebugEnabled()) {
                     debug("setting run-as principal equal to the role name for run-as role " + runAsRole);
                  }

                  return runAsRole;
               }
            }
         default:
            throw new AssertionError("Unexpected role mapping behavior: " + this.roleMappingBehavior);
      }
   }

   private static void debug(String s) {
      debugLogger.debug("[RuntimeHelper] " + s);
   }

   static {
      debugLogger = EJBDebugService.securityLogger;
   }
}
