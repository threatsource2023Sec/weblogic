package monfox.toolkit.snmp.v3.usm;

import monfox.toolkit.snmp.engine.SnmpEngineID;

public interface USMSecurityDB {
   USMEngineInfo getEngineInfo(SnmpEngineID var1);

   USMEngineInfo newEngineInfo(SnmpEngineID var1);

   USMLocalizedUserData getUserData(USMEngineInfo var1, String var2);

   void addUserData(USMEngineInfo var1, USMLocalizedUserData var2);

   USMUser getUser(SnmpEngineID var1, String var2);
}
