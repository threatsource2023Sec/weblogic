package weblogic.diagnostics.snmp.agent;

import java.util.Map;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

public interface SNMPV3AgentToolkit extends SNMPAgentToolkit {
   void startSNMPAgent(String var1, String var2, int var3, String var4, int var5, int var6) throws SNMPAgentToolkitException;

   void setSecurityParams(int var1, int var2, int var3, long var4);

   void addSNMPTableRowForMBeanInstance(String var1, MBeanServerConnection var2, ObjectName var3, Map var4) throws SNMPAgentToolkitException;

   void deleteSNMPTableRowForMBeanInstance(String var1, ObjectName var2) throws SNMPAgentToolkitException;

   SNMPTransportProvider getTransportProvider(int var1);

   SNMPSecurityManager getSNMPSecurityManager();

   void setCommunityBasedAccessEnabled(boolean var1) throws SNMPAgentToolkitException;
}
