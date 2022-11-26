package weblogic.diagnostics.snmp.agent;

public class SNMPProxyConfigImpl implements SNMPProxyConfig {
   private static final long DEFAULT_TIMEOUT = 10000L;
   private String proxyName;
   private String address;
   private int port;
   private String oidRoot;
   private String community;
   private String securityName;
   private int securityLevel = 0;
   private long timeoutMillis = 10000L;

   public String getAddress() {
      return this.address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public String getCommunity() {
      return this.community;
   }

   public void setCommunity(String community) {
      this.community = community;
   }

   public String getOidRoot() {
      return this.oidRoot;
   }

   public void setOidRoot(String oidRoot) {
      this.oidRoot = oidRoot;
   }

   public int getPort() {
      return this.port;
   }

   public void setPort(int port) {
      this.port = port;
   }

   public String getProxyName() {
      return this.proxyName;
   }

   public void setProxyName(String proxyName) {
      this.proxyName = proxyName;
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

   public long getTimeoutMillis() {
      return this.timeoutMillis;
   }

   public void setTimeoutMillis(long timeoutMillis) {
      this.timeoutMillis = timeoutMillis;
   }

   public String toString() {
      return this.proxyName + "@" + this.address + ":" + this.port;
   }
}
