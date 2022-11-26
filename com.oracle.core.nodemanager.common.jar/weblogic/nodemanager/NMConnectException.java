package weblogic.nodemanager;

public class NMConnectException extends NMException {
   private String host;
   private int port;

   public NMConnectException(String msg) {
      this(msg, "", 0);
   }

   public NMConnectException(String msg, String host, int port) {
      super(msg);
      this.host = host;
      this.port = port;
   }

   public String getHost() {
      return this.host;
   }

   public int getPort() {
      return this.port;
   }

   public void setHost(String host) {
      this.host = host;
   }

   public void setPort(int port) {
      this.port = port;
   }
}
