package weblogic.security;

import java.security.AccessController;
import java.util.HashMap;
import weblogic.management.ManagementException;
import weblogic.management.configuration.SecurityConfigurationMBean;
import weblogic.management.internal.DefaultJMXPolicyManager;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.RealmRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.ServerSecurityRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.ConsumptionException;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

public final class SecurityRuntime extends RuntimeMBeanDelegate implements ServerSecurityRuntimeMBean {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private RealmRuntimeMBean defaultRealmRuntime;
   private HashMap realmRuntimes = new HashMap();

   private static RuntimeAccess getRuntimeAccess() {
      return ManagementService.getRuntimeAccess(kernelId);
   }

   public SecurityRuntime(SecurityConfigurationMBean newSecCfg) throws ManagementException {
      super(getRuntimeAccess().getServerName(), getRuntimeAccess().getServerRuntime(), true, newSecCfg);
      getRuntimeAccess().getServerRuntime().setServerSecurityRuntime(this);
   }

   public synchronized RealmRuntimeMBean getDefaultRealmRuntime() {
      return (RealmRuntimeMBean)this.realmRuntimes.get(SecurityServiceManager.getAdministrativeRealmName());
   }

   public boolean isJACCEnabled() {
      return SecurityServiceManager.isJACCEnabled();
   }

   public synchronized RealmRuntimeMBean[] getRealmRuntimes() {
      Object[] list = this.realmRuntimes.values().toArray();
      RealmRuntimeMBean[] ret = new RealmRuntimeMBean[list.length];

      for(int lcv = 0; lcv < list.length; ++lcv) {
         ret[lcv] = (RealmRuntimeMBean)list[lcv];
      }

      return ret;
   }

   public synchronized RealmRuntimeMBean lookupRealmRuntime(String realmName) {
      return (RealmRuntimeMBean)this.realmRuntimes.get(realmName);
   }

   public synchronized void addRealmRuntime(RealmRuntimeMBean realmRuntime) {
      this.realmRuntimes.put(realmRuntime.getName(), realmRuntime);
   }

   public synchronized void removeRealmRuntime(RealmRuntimeMBean realmRuntime) throws ManagementException {
      if (realmRuntime != null) {
         this.realmRuntimes.remove(realmRuntime.getName());
         unregister((RuntimeMBeanDelegate)realmRuntime);
      }

   }

   public boolean checkRole(String roleName) throws ManagementException {
      if (roleName != null && !roleName.isEmpty()) {
         String[] roles = new String[]{roleName};
         return this.checkRole(roles);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public boolean checkRole(String[] roleNames) throws ManagementException {
      if (roleNames != null && roleNames.length != 0) {
         AuthenticatedSubject subject = SecurityServiceManager.getCurrentSubject(kernelId);
         return SubjectUtils.isUserInAdminRoles(subject, roleNames);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public synchronized void registerRestart(String realmName, RealmRuntimeMBean realmRuntimeMBean) throws ManagementException {
      if (realmRuntimeMBean != null) {
         this.addChild((RuntimeMBeanDelegate)realmRuntimeMBean);
         register((RuntimeMBeanDelegate)realmRuntimeMBean);
         this.realmRuntimes.put(realmName, realmRuntimeMBean);
      }

   }

   public synchronized void resetDefaultPolicies() throws ManagementException {
      try {
         DefaultJMXPolicyManager.reset();
      } catch (ConsumptionException var2) {
         throw new ManagementException(var2);
      }
   }

   private static void register(RuntimeMBeanDelegate runtimeMBean) throws ManagementException {
      try {
         runtimeMBean.register();
      } catch (Exception var2) {
         throw new ManagementException(var2.toString(), var2);
      }
   }

   private static void unregister(RuntimeMBeanDelegate runtimeMBean) throws ManagementException {
      try {
         runtimeMBean.unregister();
      } catch (Exception var2) {
         throw new ManagementException(var2.toString(), var2);
      }
   }
}
