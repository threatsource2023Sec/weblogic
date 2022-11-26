package weblogic.diagnostics.snmp.agent;

public interface SNMPProxyManager {
   void addProxyAgent(String var1, String var2, int var3, String var4, String var5, String var6, int var7, long var8) throws SNMPAgentToolkitException;

   void removeProxyAgent(String var1) throws SNMPAgentToolkitException;

   void cleanup() throws SNMPAgentToolkitException;
}
