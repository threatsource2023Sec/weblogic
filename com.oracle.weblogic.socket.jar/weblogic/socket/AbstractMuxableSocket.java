package weblogic.socket;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.Socket;
import javax.net.SocketFactory;
import weblogic.kernel.KernelStatus;
import weblogic.management.runtime.ServerChannelRuntimeProvider;
import weblogic.management.runtime.ServerConnectionRuntime;
import weblogic.protocol.MessageReceiverStatistics;
import weblogic.protocol.MessageSenderStatistics;
import weblogic.protocol.MessageSenderStatisticsSupport;
import weblogic.protocol.ServerChannel;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.Debug;
import weblogic.utils.io.Chunk;

public abstract class AbstractMuxableSocket extends BaseAbstractMuxableSocket implements MessageReceiverStatistics {
   private static final long serialVersionUID = 7960171920400419300L;
   private static final boolean DEBUG = false;
   private final ServerConnectionRuntime runtime;

   protected AbstractMuxableSocket(Chunk headChunk, Socket s, ServerChannel networkChannel) throws IOException {
      this(headChunk, networkChannel);
      this.connect(s);
      this.socket.setTcpNoDelay(true);
      if (KernelStatus.isServer() && networkChannel instanceof ServerChannelRuntimeProvider) {
         ServerChannelRuntimeProvider c = (ServerChannelRuntimeProvider)networkChannel;
         Debug.assertion(c.getRuntime() != null);
         if (c.getRuntime().getConnectionsCount() > (long)networkChannel.getMaxConnectedClients()) {
            throw new MaxConnectionsExceededException(networkChannel.getMaxConnectedClients(), networkChannel.getChannelName());
         }
      }

      for(Chunk tmp = this.head; tmp != null; tmp = tmp.next) {
         this.availBytes += tmp.end;
         this.tail = tmp;
      }

      if (this.availBytes > this.maxMessageSize) {
         throw new MaxMessageSizeExceededException(this.availBytes, this.maxMessageSize, networkChannel.getConfiguredProtocol());
      }
   }

   public void prepareForReuse() {
      this.resetData();
   }

   protected void registerForRuntimeMonitoring(ServerChannel channel, ServerConnectionRuntime runtime) {
      if (channel instanceof ServerChannelRuntimeProvider && ((ServerChannelRuntimeProvider)channel).getRuntime() != null) {
         ((ServerChannelRuntimeProvider)channel).getRuntime().addServerConnectionRuntime(runtime);
      }

   }

   protected AbstractMuxableSocket(Chunk headChunk, ServerChannel networkChannel) {
      super(headChunk, networkChannel);
      this.socketFactory = (SocketFactory)(networkChannel.supportsTLS() ? new ChannelSSLSocketFactory(this.channel) : new ChannelSocketFactory(this.channel));
      ServerConnectionRuntimeFactory serverConnectionRuntimeFactory = (ServerConnectionRuntimeFactory)GlobalServiceLocator.getServiceLocator().getService(ServerConnectionRuntimeFactory.class, new Annotation[0]);
      if (serverConnectionRuntimeFactory != null) {
         this.runtime = serverConnectionRuntimeFactory.createServerConnectionRuntimeImpl((MessageSenderStatistics)null, this, this);
      } else {
         this.runtime = null;
      }

      this.registerForRuntimeMonitoring(this.channel, this.runtime);
   }

   protected AbstractMuxableSocket(ServerChannel networkChannel) {
      this(Chunk.getChunk(), networkChannel);
   }

   protected static void p(String msg) {
      System.out.println("<AbstractMuxableSocket>: " + msg);
   }

   public final void addSenderStatistics(MessageSenderStatistics sender) {
      ((MessageSenderStatisticsSupport)this.runtime).addSender(sender);
   }

   protected void cleanup() {
      super.cleanup();
      if (KernelStatus.isServer() && this.channel instanceof ServerChannelRuntimeProvider) {
         ServerChannelRuntimeProvider c = (ServerChannelRuntimeProvider)this.channel;
         if (c.getRuntime() != null) {
            c.getRuntime().removeServerConnectionRuntime(this);
         }
      }

   }
}
