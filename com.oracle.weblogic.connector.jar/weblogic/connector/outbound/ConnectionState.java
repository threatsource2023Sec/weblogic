package weblogic.connector.outbound;

public class ConnectionState {
   private boolean connectionClosed = false;
   private boolean connectionFinalized = false;

   public synchronized void setConnectionClosed(boolean connectionClosed) {
      this.connectionClosed = connectionClosed;
   }

   public synchronized void setConnectionFinalized(boolean connectionFinalized) {
      this.connectionFinalized = connectionFinalized;
   }

   public synchronized boolean isConnectionClosed() {
      return this.connectionClosed;
   }

   public synchronized boolean isConnectionFinalized() {
      return this.connectionFinalized;
   }
}
