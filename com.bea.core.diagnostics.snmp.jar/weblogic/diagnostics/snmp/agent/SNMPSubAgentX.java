package weblogic.diagnostics.snmp.agent;

public interface SNMPSubAgentX {
   void createMIBModule(String var1, String var2, String var3, String var4, String var5) throws SNMPAgentToolkitException;

   String outputMIBModule() throws SNMPAgentToolkitException;

   void deleteAllSNMPTableRows();

   void shutdown();
}
