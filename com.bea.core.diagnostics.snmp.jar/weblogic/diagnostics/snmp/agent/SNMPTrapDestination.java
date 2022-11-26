package weblogic.diagnostics.snmp.agent;

public class SNMPTrapDestination implements SNMPTrapDestinationConfig {
   private String name;
   private String host = "localhost";
   private int port = 162;
   private String community = "public";
   private String securityName;
   private int securityLevel;

   public SNMPTrapDestination() {
   }

   public SNMPTrapDestination(String name) {
      this.name = name;
   }

   public String getHost() {
      return this.host;
   }

   public void setHost(String host) {
      this.host = host;
   }

   public int getPort() {
      return this.port;
   }

   public void setPort(int port) {
      this.port = port;
   }

   public String getCommunity() {
      return this.community;
   }

   public void setCommunity(String community) {
      this.community = community;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public int getSecurityLevel() {
      return this.securityLevel;
   }

   public void setSecurityLevel(int securityLevel) {
      this.securityLevel = securityLevel;
   }

   public String getSecurityName() {
      return this.securityName;
   }

   public void setSecurityName(String securityName) {
      this.securityName = securityName;
   }

   public String toString() {
      return this.name + "@" + this.host + ":" + this.port;
   }
}
