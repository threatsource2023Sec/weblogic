package weblogic.management.runtime;

import weblogic.management.ManagementException;

public interface ServerSecurityRuntimeMBean extends RuntimeMBean {
   RealmRuntimeMBean getDefaultRealmRuntime();

   boolean isJACCEnabled();

   RealmRuntimeMBean[] getRealmRuntimes();

   RealmRuntimeMBean lookupRealmRuntime(String var1);

   void addRealmRuntime(RealmRuntimeMBean var1);

   void removeRealmRuntime(RealmRuntimeMBean var1) throws ManagementException;

   String getName();

   boolean checkRole(String var1) throws ManagementException;

   boolean checkRole(String[] var1) throws ManagementException;

   void resetDefaultPolicies() throws ManagementException;
}
