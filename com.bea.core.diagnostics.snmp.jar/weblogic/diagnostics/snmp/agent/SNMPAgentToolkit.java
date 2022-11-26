package weblogic.diagnostics.snmp.agent;

import java.util.List;
import java.util.Map;

public interface SNMPAgentToolkit extends SNMPConstants {
   String getVendorName();

   void startSNMPAgent(int var1) throws SNMPAgentToolkitException;

   void setMaxPortRetryCount(int var1);

   void initializeMasterAgentX(String var1, int var2, String var3) throws SNMPAgentToolkitException;

   void stopSNMPAgent() throws SNMPAgentToolkitException;

   void initializeSNMPAgentToolkit(String var1, String var2) throws SNMPAgentToolkitException;

   void setSNMPCommunity(String var1, String var2) throws SNMPAgentToolkitException;

   void createSNMPMibTables(String var1) throws SNMPAgentToolkitException;

   void addSNMPTableRow(String var1, Map var2) throws SNMPAgentToolkitException;

   void addSNMPTableRow(String var1, String[] var2, Map var3) throws SNMPAgentToolkitException;

   void removeSNMPTableRow(String var1, String[] var2) throws SNMPAgentToolkitException;

   Map getSNMPTablesMetadata(String var1) throws SNMPAgentToolkitException;

   SNMPTargetManager getTargetManager();

   SNMPNotificationManager getSNMPNotificationManager();

   SNMPSubAgentX createSNMPSubAgentX(String var1, String var2) throws SNMPAgentToolkitException;

   SNMPSubAgentX findSNMPSubAgentX(String var1);

   String getSNMPAgentListenAddress();

   int getSNMPAgentUDPPort();

   String getMasterAgentXListenAddress();

   int getMasterAgentXPort();

   SNMPProxyManager getSNMPProxyManager();

   void addModuleIdentityInfo(String var1, String var2, String var3, String var4, String var5, String var6, String var7);

   void createSNMPTable(String var1, String var2, String var3, String var4, List var5) throws SNMPAgentToolkitException;

   void updateSNMPTable(String var1, String var2, String var3) throws SNMPAgentToolkitException;

   void createSNMPColumn(String var1, String var2, Class var3, String var4) throws SNMPAgentToolkitException;

   void updateSNMPColumn(String var1, String var2, String var3) throws SNMPAgentToolkitException;

   void removeSNMPColumn(String var1, String var2) throws SNMPAgentToolkitException;

   void completeTableEdit(String var1) throws SNMPAgentToolkitException;

   String outputMIBModule(String var1) throws SNMPAgentToolkitException;

   long getMaxLastOid(String var1, String var2) throws SNMPAgentToolkitException;
}
