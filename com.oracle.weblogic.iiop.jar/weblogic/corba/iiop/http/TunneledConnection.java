package weblogic.corba.iiop.http;

import java.io.IOException;
import weblogic.iiop.Connection;
import weblogic.iiop.EndPoint;
import weblogic.iiop.protocol.ListenPoint;
import weblogic.iiop.server.ServerEndPointImpl;
import weblogic.iiop.utils.Clock;
import weblogic.management.runtime.ServerConnectionRuntime;
import weblogic.management.runtime.SocketRuntime;
import weblogic.protocol.AsyncOutgoingMessage;
import weblogic.protocol.MessageReceiverStatistics;
import weblogic.protocol.MessageSenderStatistics;
import weblogic.protocol.ServerChannel;
import weblogic.security.acl.UserInfo;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.server.channels.ServerChannelImpl;
import weblogic.server.channels.ServerConnectionRuntimeImpl;

abstract class TunneledConnection extends Connection implements MessageSenderStatistics, MessageReceiverStatistics {
   private static Clock clock = new Clock() {
      public long currentTimeMillis() {
         return System.currentTimeMillis();
      }
   };
   private final String connectionId = TunneledConnectionManager.getNextID();
   private final long connectTime;
   private ServerChannel serverChannel;
   private SocketRuntime socketRuntime;
   private long lastMessageReceivedTime;
   private ListenPoint listenPoint;
   private boolean closed;
   private long messagesSent;
   private long bytesSent;
   private long messagesReceived;
   private long bytesReceived;

   public TunneledConnection(SocketRuntime socketRuntime, ServerChannel serverChannel) {
      this.connectTime = clock.currentTimeMillis();
      this.listenPoint = null;
      this.messagesSent = 0L;
      this.bytesSent = 0L;
      this.messagesReceived = 0L;
      this.bytesReceived = 0L;
      this.socketRuntime = socketRuntime;
      this.serverChannel = serverChannel;
   }

   protected void addServerConnectionRuntime() {
      ServerConnectionRuntime runtime = new ServerConnectionRuntimeImpl(this, this, this.socketRuntime);
      if (this.serverChannel instanceof ServerChannelImpl && ((ServerChannelImpl)this.serverChannel).getRuntime() != null) {
         ((ServerChannelImpl)this.serverChannel).getRuntime().addServerConnectionRuntime(runtime);
      }

   }

   protected void removeServerConnectionRuntime() {
      if (this.serverChannel instanceof ServerChannelImpl && ((ServerChannelImpl)this.serverChannel).getRuntime() != null) {
         ((ServerChannelImpl)this.serverChannel).getRuntime().removeServerConnectionRuntime(this.socketRuntime);
      }

   }

   protected void incrementBytesReceived(int numBytes) {
      this.bytesReceived += (long)numBytes;
   }

   protected void incrementMessagedReceived() {
      ++this.messagesReceived;
   }

   protected void updateMessageSentStatistics(AsyncOutgoingMessage msg) throws IOException {
      ++this.messagesSent;
      this.bytesSent += (long)msg.getLength();
   }

   protected void recordLastMessageReceiptTime() {
      this.lastMessageReceivedTime = clock.currentTimeMillis();
   }

   protected long getClientTimeoutMsec() {
      return (long)(this.getClientTimeoutSecs() * 1000);
   }

   protected long getMsecSinceLastMessageReceived() {
      return clock.currentTimeMillis() - this.lastMessageReceivedTime;
   }

   protected static long getCurrentTimeMsec() {
      return clock.currentTimeMillis();
   }

   protected boolean clientTimeoutDetected() {
      return !this.ignoreTimeouts() && this.getMsecSinceLastMessageReceived() > this.getClientTimeoutMsec();
   }

   final void checkIsDead() {
      if (this.clientTimeoutDetected()) {
         this.handleClientTimeout();
      }

   }

   protected abstract void handleClientTimeout();

   protected abstract int getClientTimeoutSecs();

   protected boolean ignoreTimeouts() {
      return this.getClientTimeoutSecs() == 0;
   }

   protected long getLastMessageReceivedTime() {
      return this.lastMessageReceivedTime;
   }

   protected synchronized void closeConnection() {
      TunneledConnectionManager.removeFromActiveConnections(this.connectionId, this.getChannel());
      this.closed = true;
   }

   protected String getConnectionId() {
      return this.connectionId;
   }

   public final long getMessagesSentCount() {
      return this.messagesSent;
   }

   public final long getBytesSentCount() {
      return this.bytesSent;
   }

   public final long getMessagesReceivedCount() {
      return this.messagesReceived;
   }

   public final long getBytesReceivedCount() {
      return this.bytesReceived;
   }

   public final long getConnectTime() {
      return this.connectTime;
   }

   public EndPoint createEndPoint() {
      return new ServerEndPointImpl(this);
   }

   public ListenPoint getListenPoint() {
      return this.listenPoint;
   }

   public void setListenPoint(ListenPoint newkey) {
      this.listenPoint = newkey;
   }

   public AuthenticatedSubject getUser() {
      return null;
   }

   public void authenticate(UserInfo ui) {
   }

   public Object getTxContext() {
      return null;
   }

   public void setTxContext(Object tx) {
   }

   protected boolean isSecure() {
      return this.serverChannel.supportsTLS();
   }

   public ServerChannel getChannel() {
      return this.serverChannel;
   }

   public boolean isClosed() {
      return this.closed;
   }
}
