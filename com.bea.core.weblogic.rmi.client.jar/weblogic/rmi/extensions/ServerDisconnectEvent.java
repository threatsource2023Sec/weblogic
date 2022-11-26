package weblogic.rmi.extensions;

public interface ServerDisconnectEvent extends DisconnectEvent {
   String getServerName();
}
