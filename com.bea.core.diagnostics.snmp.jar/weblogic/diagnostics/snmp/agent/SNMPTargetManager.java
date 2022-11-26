package weblogic.diagnostics.snmp.agent;

public interface SNMPTargetManager {
   void addV1TrapDestination(String var1, String var2, int var3, int var4, int var5, String var6, String var7) throws SNMPAgentToolkitException;

   void addV2TrapDestination(String var1, String var2, int var3, int var4, int var5, String var6, String var7) throws SNMPAgentToolkitException;

   void addV3TrapDestination(String var1, String var2, int var3, int var4, int var5, String var6, String var7, int var8) throws SNMPAgentToolkitException;
}
