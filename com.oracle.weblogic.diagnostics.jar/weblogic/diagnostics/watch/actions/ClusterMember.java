package weblogic.diagnostics.watch.actions;

public class ClusterMember {
   private String name;
   private String state;
   private String listenAddr;
   private int listenPort;

   public ClusterMember() {
   }

   public ClusterMember(String name, String state, String address, int port) {
      this.name = name;
      this.state = state;
      this.listenAddr = address;
      this.listenPort = port;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getState() {
      return this.state;
   }

   public void setState(String state) {
      this.state = state;
   }

   public String getListenAddr() {
      return this.listenAddr;
   }

   public void setListenAddr(String listenAddr) {
      this.listenAddr = listenAddr;
   }

   public int getListenPort() {
      return this.listenPort;
   }

   public void setListenPort(int listenPort) {
      this.listenPort = listenPort;
   }

   public boolean isRunning() {
      return "RUNNING".equals(this.state);
   }

   public boolean isShutdown() {
      return this.state == null || this.state.indexOf("SHUTDOWN") >= 0;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append(this.name).append("  ").append(this.state);
      if (this.listenAddr != null) {
         buf.append(" ").append(this.listenAddr).append(":").append(this.listenPort);
      }

      return buf.toString();
   }
}
