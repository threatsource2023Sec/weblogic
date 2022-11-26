package weblogic.diagnostics.snmp.agent;

import java.util.List;

public interface SNMPAgentConfig {
   String MIB_BASE_PATH_PROPERTY = "weblogic.diagnostics.snmp.mib.mibBasePath";
   String MIB_MODULES_PROPERTY = "weblogic.diagnostics.snmp.mib.mibModules";

   String getName();

   void setName(String var1);

   String getCommunity();

   void setCommunity(String var1);

   String getMibBasePath();

   void setMibBasePath(String var1);

   String getMibModules();

   void setMibModules(String var1);

   String getUdpListenAddress();

   void setUdpListenAddress(String var1);

   int getUdpListenPort();

   void setUdpListenPort(int var1);

   int getSNMPTrapVersion();

   void setSNMPTrapVersion(int var1);

   boolean isAutomaticTrapsEnabled();

   void setAutomaticTrapsEnabled(boolean var1);

   boolean isInformEnabled();

   void setInformEnabled(boolean var1);

   List getSNMPProxyConfigs();

   void setSNMPProxyConfigs(List var1);

   List getSNMPTrapDestinationConfigs();

   void setSNMPTrapDestinationConfigs(List var1);

   SNMPAgent getSNMPAgent();
}
