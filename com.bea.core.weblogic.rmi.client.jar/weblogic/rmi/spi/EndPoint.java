package weblogic.rmi.spi;

import java.io.IOException;
import java.io.ObjectInput;
import java.rmi.Remote;
import weblogic.protocol.Protocol;
import weblogic.protocol.ServerChannel;
import weblogic.rmi.extensions.DisconnectListener;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;

public interface EndPoint {
   HostID getHostID();

   Channel getRemoteChannel();

   ServerChannel getServerChannel();

   boolean isDead();

   boolean isUnresponsive();

   OutboundRequest getOutboundRequest(RemoteReference var1, RuntimeMethodDescriptor var2, String var3, String var4) throws IOException;

   OutboundRequest getOutboundRequest(RemoteReference var1, RuntimeMethodDescriptor var2, String var3, Protocol var4, String var5) throws IOException;

   String getClusterURL(ObjectInput var1);

   boolean addDisconnectListener(Remote var1, DisconnectListener var2);

   boolean removeDisconnectListener(Remote var1, DisconnectListener var2);

   void disconnect();

   void disconnect(String var1, boolean var2);

   long getCreationTime();
}
