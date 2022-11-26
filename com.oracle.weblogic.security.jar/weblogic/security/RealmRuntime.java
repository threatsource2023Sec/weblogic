package weblogic.security;

import java.security.AccessController;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.management.ManagementException;
import weblogic.management.runtime.AuthenticatorRuntimeMBean;
import weblogic.management.runtime.RealmRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.ServerSecurityRuntimeMBean;
import weblogic.management.runtime.UserLockoutManagerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class RealmRuntime extends RuntimeMBeanDelegate implements RealmRuntimeMBean {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private UserLockoutManagerRuntimeMBean userLockoutManagerRuntime = null;
   private HashMap authenticatorRuntimes = new HashMap();

   public RealmRuntime(String realmName, ServerSecurityRuntimeMBean securityRuntime) throws ManagementException {
      super(realmName, securityRuntime, true);
   }

   public UserLockoutManagerRuntimeMBean getUserLockoutManagerRuntime() {
      return this.userLockoutManagerRuntime;
   }

   public void setUserLockoutManagerRuntime(UserLockoutManagerRuntimeMBean userLockoutManagerRuntime) {
      this.userLockoutManagerRuntime = userLockoutManagerRuntime;
   }

   public synchronized AuthenticatorRuntimeMBean[] getAuthenticatorRuntimes() {
      Object[] list = this.authenticatorRuntimes.values().toArray();
      AuthenticatorRuntimeMBean[] ret = new AuthenticatorRuntimeMBean[list.length];

      for(int lcv = 0; lcv < list.length; ++lcv) {
         ret[lcv] = (AuthenticatorRuntimeMBean)list[lcv];
      }

      return ret;
   }

   public synchronized AuthenticatorRuntimeMBean lookupAuthenticatorRuntime(String providerName) {
      return (AuthenticatorRuntimeMBean)this.authenticatorRuntimes.get(providerName);
   }

   public synchronized void addAuthenticatorRuntime(AuthenticatorRuntimeMBean authenticatorRuntime) {
      this.authenticatorRuntimes.put(authenticatorRuntime.getName(), authenticatorRuntime);
   }

   public synchronized void removeAuthenticatorRuntime(AuthenticatorRuntimeMBean authenticatorRuntime) throws ManagementException {
      if (authenticatorRuntime != null) {
         this.authenticatorRuntimes.remove(authenticatorRuntime.getName());
         unregister((RuntimeMBeanDelegate)authenticatorRuntime);
      }

   }

   public synchronized void registerRestart() throws ManagementException {
      if (this.userLockoutManagerRuntime != null) {
         this.addChild((UserLockoutManagerRuntime)this.userLockoutManagerRuntime);
         register((UserLockoutManagerRuntime)this.userLockoutManagerRuntime);
      }

      if (this.authenticatorRuntimes != null && !this.authenticatorRuntimes.isEmpty()) {
         Iterator var1 = this.authenticatorRuntimes.entrySet().iterator();

         while(var1.hasNext()) {
            Map.Entry entry = (Map.Entry)var1.next();
            AuthenticatorRuntimeMBean authenticatorRuntime = (AuthenticatorRuntimeMBean)entry.getValue();
            if (authenticatorRuntime != null) {
               this.addChild((RuntimeMBeanDelegate)authenticatorRuntime);
               register((RuntimeMBeanDelegate)authenticatorRuntime);
            }
         }
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
