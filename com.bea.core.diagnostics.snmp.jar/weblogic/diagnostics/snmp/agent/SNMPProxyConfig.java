package weblogic.diagnostics.snmp.agent;

public interface SNMPProxyConfig {
   String getAddress();

   void setAddress(String var1);

   String getCommunity();

   void setCommunity(String var1);

   String getOidRoot();

   void setOidRoot(String var1);

   int getPort();

   void setPort(int var1);

   String getProxyName();

   void setProxyName(String var1);

   int getSecurityLevel();

   void setSecurityLevel(int var1);

   String getSecurityName();

   void setSecurityName(String var1);

   long getTimeoutMillis();

   void setTimeoutMillis(long var1);
}
