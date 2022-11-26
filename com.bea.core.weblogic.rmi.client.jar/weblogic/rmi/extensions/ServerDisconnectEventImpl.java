package weblogic.rmi.extensions;

public final class ServerDisconnectEventImpl extends DisconnectEventImpl implements ServerDisconnectEvent {
   private final String serverName;

   public ServerDisconnectEventImpl(Exception e, String serverName) {
      super(e);
      this.serverName = serverName;
   }

   public String getServerName() {
      return this.serverName;
   }
}
