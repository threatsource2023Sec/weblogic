package weblogic.diagnostics.snmp.agent;

public interface SNMPTrapDestinationConfig {
   String getHost();

   void setHost(String var1);

   int getPort();

   void setPort(int var1);

   String getCommunity();

   void setCommunity(String var1);

   String getName();

   void setName(String var1);

   int getSecurityLevel();

   void setSecurityLevel(int var1);

   String getSecurityName();

   void setSecurityName(String var1);
}
