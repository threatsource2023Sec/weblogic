package weblogic.application.internal.flow;

import java.security.AccessController;
import java.util.HashMap;
import java.util.Map;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.SecurityRole;
import weblogic.application.internal.FlowContext;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.J2EELogger;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.SecurityRoleBean;
import weblogic.j2ee.descriptor.wl.ApplicationSecurityRoleAssignmentBean;
import weblogic.j2ee.descriptor.wl.SecurityBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.logging.Loggable;
import weblogic.management.DeploymentException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.ApplicationResource;
import weblogic.security.service.DeployHandleCreationException;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.ResourceBase;
import weblogic.security.service.RoleCreationException;
import weblogic.security.service.RoleManager;
import weblogic.security.service.RoleManagerDeployHandle;
import weblogic.security.service.RoleRemovalException;
import weblogic.security.service.SecurityApplicationInfo;
import weblogic.security.service.SecurityApplicationInfoImpl;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.security.spi.ApplicationInfo.ComponentType;
import weblogic.utils.StringUtils;

public final class SecurityRoleFlow extends BaseFlow {
   private RoleManagerDeployHandle handle;
   private RoleManager roleManager = null;
   private static AuthenticatedSubject kernelId = null;
   private SecurityApplicationInfo secInfo = null;
   private String realmName = null;
   private static final SecurityRole NOOP_MAPPING = new SecurityRole((String[])null);
   private final boolean useJACC;

   public SecurityRoleFlow(FlowContext appCtx) {
      super(appCtx);
      this.useJACC = appCtx.getSecurityProvider().isJACCEnabled();
   }

   public void prepare() throws DeploymentException {
      if (!this.useJACC) {
         if (SecurityServiceManager.isSecurityServiceInitialized()) {
            this.realmName = this.appCtx.getApplicationSecurityRealmName();
            if (this.realmName == null) {
               this.realmName = this.getDefaultRealmName();
               this.appCtx.setApplicationSecurityRealmName(this.realmName);
            }

            this.checkForRealmNameInDD();
            if (this.roleManager == null) {
               this.initSecurityService();
            }

            this.secInfo = new SecurityApplicationInfoImpl(this.appCtx.getAppDeploymentMBean(), ComponentType.APPLICATION, this.appCtx.getApplicationName());

            try {
               this.handle = this.roleManager.startDeployRoles(this.secInfo);

               try {
                  this.deployRoles();
               } finally {
                  this.roleManager.endDeployRoles(this.handle);
               }

            } catch (DeployHandleCreationException var6) {
               throw new DeploymentException(var6);
            } catch (RoleCreationException var7) {
               throw new DeploymentException(var7);
            }
         }
      }
   }

   public void unprepare() throws DeploymentException {
      if (!this.useJACC) {
         if (SecurityServiceManager.isSecurityServiceInitialized()) {
            this.undeployRoles();
         }
      }
   }

   private void initSecurityService() {
      this.roleManager = (RoleManager)SecurityServiceManager.getSecurityService(this.getKernelID(), this.realmName, ServiceType.ROLE);
   }

   private String[] getSecurityRoleNames() {
      String[] result = null;
      ApplicationBean dd = this.appCtx.getApplicationDD();
      SecurityRoleBean[] roles = dd != null ? dd.getSecurityRoles() : null;
      if (roles != null && roles.length != 0) {
         result = new String[roles.length];

         for(int i = 0; i < roles.length; ++i) {
            result[i] = roles[i].getRoleName();
         }
      }

      return result;
   }

   private Map getSecurityRoleAssignments() {
      Map result = new HashMap();
      WeblogicApplicationBean wldd = this.appCtx.getWLApplicationDD();
      if (wldd == null) {
         return result;
      } else {
         SecurityBean security = wldd.getSecurity();
         if (security == null) {
            return result;
         } else {
            ApplicationSecurityRoleAssignmentBean[] ra = security.getSecurityRoleAssignments();
            if (ra != null) {
               for(int i = 0; i < ra.length; ++i) {
                  String role = ra[i].getRoleName();
                  String[] principalNames = ra[i].getPrincipalNames();
                  if (ra[i].getExternallyDefined() != null) {
                     result.put(role, new SecurityRole());
                  } else {
                     result.put(role, new SecurityRole(principalNames));
                  }
               }
            }

            return result;
         }
      }
   }

   private AuthenticatedSubject getKernelID() {
      if (kernelId == null) {
         kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      }

      return kernelId;
   }

   private String getDefaultRealmName() {
      String realmName = SecurityServiceManager.getDefaultRealmName();
      return realmName;
   }

   private void checkForRealmNameInDD() {
      WeblogicApplicationBean wldd = this.appCtx.getWLApplicationDD();
      if (wldd != null) {
         SecurityBean security = wldd.getSecurity();
         if (security != null) {
            String tmp = security.getRealmName();
            if (tmp != null) {
               J2EELogger.logRealmNameInDDIgnoredWarning(tmp);
            }
         }
      }

   }

   private boolean isCompatibilitySecMode() {
      return SecurityServiceManager.getRoleMappingBehavior(this.realmName, this.secInfo) == 0;
   }

   private boolean isApplicationSecMode() {
      return SecurityServiceManager.getRoleMappingBehavior(this.realmName, this.secInfo) == 1;
   }

   private boolean isExternallyDefinedSecMode() {
      return SecurityServiceManager.getRoleMappingBehavior(this.realmName, this.secInfo) == 2;
   }

   private void deployRoles() throws DeploymentException {
      String[] roles = this.getSecurityRoleNames();
      Map roleAssignments = this.getSecurityRoleAssignments();
      if (roles == null && roleAssignments != null) {
         roles = (String[])((String[])roleAssignments.keySet().toArray(new String[roleAssignments.size()]));
      }

      if (roles != null) {
         if (!this.isCompatibilitySecMode()) {
            this.appCtx.setAppLevelRoleMappings(roleAssignments);
         }

         ResourceBase resource = new ApplicationResource(this.appCtx.getApplicationId());
         if (this.isCompatibilitySecMode()) {
            this.deployRolesAllowEmptyRoleMapping(resource, roles, roleAssignments);
         } else if (this.isApplicationSecMode()) {
            this.deployRolesAllowEmptyRoleMapping(resource, roles, roleAssignments);
         } else {
            if (!this.isExternallyDefinedSecMode()) {
               throw new AssertionError("Unknown security mode");
            }

            this.deployRolesNoEmptyRoleMapping(resource, roles, roleAssignments);
         }

      }
   }

   private void deployRolesAllowEmptyRoleMapping(ResourceBase resource, String[] roles, Map roleAssignments) throws DeploymentException {
      this.deployRoles(resource, roles, roleAssignments, true);
   }

   private void deployRolesNoEmptyRoleMapping(ResourceBase resource, String[] roles, Map roleAssignments) throws DeploymentException {
      this.deployRoles(resource, roles, roleAssignments, false);
   }

   private void deployRoles(ResourceBase resource, String[] roles, Map roleAssignments, boolean allowEmptyRoleMapping) throws DeploymentException {
      boolean isAnyAuthenticatedUserRoleInDD = false;
      boolean isEmptyPrincipalMapping = false;

      for(int i = 0; i < roles.length; ++i) {
         if (roles[i] != null) {
            SecurityRole sr = (SecurityRole)roleAssignments.get(roles[i]);
            if (sr == null) {
               sr = NOOP_MAPPING;
            }

            if (!sr.isExternallyDefined()) {
               String[] principals = sr.getPrincipalNames();
               if ("**".equals(roles[i])) {
                  isAnyAuthenticatedUserRoleInDD = true;
                  if (principals == null || principals.length == 0) {
                     isEmptyPrincipalMapping = true;
                  }
               }

               if (principals == null) {
                  if (!allowEmptyRoleMapping) {
                     continue;
                  }

                  principals = new String[0];
               }

               this.deployRole(resource, roles[i], principals);
            }
         }
      }

      if (!isAnyAuthenticatedUserRoleInDD && isEmptyPrincipalMapping) {
         this.deployRole(resource, "**", new String[]{"users"});
      }

   }

   private void deployRole(ResourceBase resource, String role, String[] mappings) throws DeploymentException {
      try {
         this.roleManager.deployRole(this.handle, resource, role, mappings);
      } catch (RoleCreationException var6) {
         Loggable l = J2EELogger.logCouldNotDeployRoleLoggable(role, ApplicationVersionUtils.getDisplayName(this.appCtx.getApplicationId()), var6);
         l.log();
         throw new DeploymentException(l.getMessage());
      }
   }

   private void undeployRoles() throws DeploymentException {
      try {
         this.roleManager.undeployAllRoles(this.handle);
      } catch (RoleRemovalException var2) {
         throw new DeploymentException(var2);
      }
   }

   private class SecurityRoleAssignmentUpdateListener implements BeanUpdateListener {
      private final ApplicationContextInternal appCtx;
      private final SecurityApplicationInfo secInfo;
      private final RoleManager roleManager;

      private SecurityRoleAssignmentUpdateListener(ApplicationContextInternal appCtx, SecurityApplicationInfo secInfo, RoleManager roleManager) {
         this.appCtx = appCtx;
         this.secInfo = secInfo;
         this.roleManager = roleManager;
      }

      void registerListeners(ApplicationContextInternal appCtx, SecurityApplicationInfo secInfo, RoleManager roleManager) {
         WeblogicApplicationBean wldd = appCtx.getWLApplicationDD();
         if (wldd != null) {
            SecurityBean sec = wldd.getSecurity();
            if (sec != null) {
               ApplicationSecurityRoleAssignmentBean[] roleAssignments = sec.getSecurityRoleAssignments();
               if (roleAssignments != null && roleAssignments.length != 0) {
                  for(int i = 0; i < roleAssignments.length; ++i) {
                     ((DescriptorBean)roleAssignments[i]).addBeanUpdateListener(SecurityRoleFlow.this.new SecurityRoleAssignmentUpdateListener(appCtx, secInfo, roleManager));
                  }

               }
            }
         }
      }

      public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
         if (SecurityRoleFlow.this.isDebugEnabled()) {
            SecurityRoleFlow.this.debug("** prepareUpdate called with event " + event);
         }

      }

      public void activateUpdate(BeanUpdateEvent event) {
         if (SecurityRoleFlow.this.isDebugEnabled()) {
            SecurityRoleFlow.this.debug("** activateUpdate called with event " + event);
         }

         ApplicationSecurityRoleAssignmentBean roleAssignment = (ApplicationSecurityRoleAssignmentBean)event.getProposedBean();
         if (SecurityRoleFlow.this.isDebugEnabled()) {
            SecurityRoleFlow.this.debug("** new principals " + StringUtils.join(roleAssignment.getPrincipalNames(), ","));
         }

         try {
            RoleManagerDeployHandle handle = this.roleManager.startDeployRoles(this.secInfo);

            try {
               ResourceBase resource = new ApplicationResource(this.appCtx.getApplicationId());
               this.roleManager.deployRole(handle, resource, roleAssignment.getRoleName(), roleAssignment.getPrincipalNames());
            } finally {
               this.roleManager.endDeployRoles(handle);
            }
         } catch (Exception var9) {
            Loggable l = J2EELogger.logCouldNotDeployRoleLoggable(roleAssignment.getRoleName(), ApplicationVersionUtils.getDisplayName(this.appCtx.getApplicationId()), var9);
            l.log();
         }

      }

      public void rollbackUpdate(BeanUpdateEvent event) {
         if (SecurityRoleFlow.this.isDebugEnabled()) {
            SecurityRoleFlow.this.debug("** rollbackUpdate called with event " + event);
         }

      }
   }
}
