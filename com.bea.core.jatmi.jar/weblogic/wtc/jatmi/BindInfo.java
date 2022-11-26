package weblogic.wtc.jatmi;

public final class BindInfo {
   private String bind_host;
   private short bind_port;
   private short bind_supports;
   private short bind_requires;

   public BindInfo(String aHost, short aPort, short aSupports, short aRequires) {
      this.bind_host = new String(aHost);
      this.bind_port = aPort;
      this.bind_supports = aSupports;
      this.bind_requires = aRequires;
   }

   public String getHost() {
      return this.bind_host;
   }

   public void setHost(String newHost) {
      this.bind_host = new String(newHost);
   }

   public short getPort() {
      return this.bind_port;
   }

   public void setPort(short newPort) {
      this.bind_port = newPort;
   }

   public short getSSLSupports() {
      return this.bind_supports;
   }

   public void setSSLSupports(short newSupports) {
      this.bind_supports = newSupports;
   }

   public short getSSLRequires() {
      return this.bind_requires;
   }

   public void setSSLRequires(short newRequires) {
      this.bind_requires = newRequires;
   }
}
