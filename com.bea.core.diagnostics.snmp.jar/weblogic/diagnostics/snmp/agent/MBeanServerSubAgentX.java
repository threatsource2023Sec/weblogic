package weblogic.diagnostics.snmp.agent;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

public interface MBeanServerSubAgentX extends SNMPSubAgentX {
   void addSNMPTableRowForMBeanInstance(MBeanServerConnection var1, String var2, ObjectName var3) throws SNMPAgentToolkitException;

   void deleteSNMPTableRowForMBeanInstance(String var1, ObjectName var2) throws SNMPAgentToolkitException;
}
