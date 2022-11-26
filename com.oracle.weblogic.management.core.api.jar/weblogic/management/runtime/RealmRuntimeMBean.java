package weblogic.management.runtime;

import weblogic.management.ManagementException;

public interface RealmRuntimeMBean extends RuntimeMBean {
   UserLockoutManagerRuntimeMBean getUserLockoutManagerRuntime();

   void setUserLockoutManagerRuntime(UserLockoutManagerRuntimeMBean var1);

   AuthenticatorRuntimeMBean[] getAuthenticatorRuntimes();

   AuthenticatorRuntimeMBean lookupAuthenticatorRuntime(String var1);

   void addAuthenticatorRuntime(AuthenticatorRuntimeMBean var1);

   void removeAuthenticatorRuntime(AuthenticatorRuntimeMBean var1) throws ManagementException;

   String getName();
}
