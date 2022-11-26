package weblogic.server.channels;

import java.net.UnknownHostException;
import org.jvnet.hk2.annotations.Service;
import weblogic.kernel.NetworkAccessPointMBeanStub;
import weblogic.management.runtime.ServerConnectionRuntime;
import weblogic.management.runtime.SocketRuntime;
import weblogic.protocol.MessageReceiverStatistics;
import weblogic.protocol.MessageSenderStatistics;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerIdentity;
import weblogic.socket.ServerConnectionRuntimeFactory;
import weblogic.socket.SocketRuntimeFactory;

@Service
public class SocketRuntimeFactoryImpl implements SocketRuntimeFactory, ServerConnectionRuntimeFactory {
   public ServerConnectionRuntime createServerConnectionRuntimeImpl(MessageSenderStatistics sender, MessageReceiverStatistics receiver, SocketRuntime socket) {
      return new ServerConnectionRuntimeImpl(sender, receiver, socket);
   }

   public SocketRuntime createSocketRuntimeImpl(SocketRuntime s) {
      return new SocketRuntimeImpl(s);
   }

   public ServerChannel createBootstrapChannel(String protocol) throws UnknownHostException {
      BasicServerChannelImpl c = new BasicServerChannelImpl();
      if (protocol.equalsIgnoreCase("https")) {
         c.flags = (byte)(c.flags | 32);
      }

      c.channelName = "BootstrapChannel";
      c.config = NetworkAccessPointMBeanStub.createBootstrapStub();
      if (c.config.getListenAddress() == null) {
         return null;
      } else {
         c.update((ServerIdentity)null);
         return c;
      }
   }
}
