package weblogic.management.runtime;

import java.io.Serializable;

public interface ServerConnectionRuntime extends Serializable {
   long getBytesReceivedCount();

   long getBytesSentCount();

   long getConnectTime();

   long getMessagesReceivedCount();

   long getMessagesSentCount();

   SocketRuntime getSocketRuntime();
}
