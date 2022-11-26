package weblogic.management.runtime;

public interface ServerChannelRuntimeMBean extends RuntimeMBean {
   long getBytesReceivedCount();

   long getBytesSentCount();

   String getChannelName();

   String getAssociatedVirtualTargetName();

   ServerConnectionRuntime[] getServerConnectionRuntimes();

   long getConnectionsCount();

   long getAcceptCount();

   long getMessagesReceivedCount();

   long getMessagesSentCount();

   String getPublicURL();

   void addServerConnectionRuntime(ServerConnectionRuntime var1);

   void removeServerConnectionRuntime(SocketRuntime var1);
}
