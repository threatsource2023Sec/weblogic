package weblogic.server.channels;

import java.io.ObjectStreamException;
import java.lang.ref.WeakReference;
import weblogic.management.runtime.ServerConnectionRuntime;
import weblogic.management.runtime.SocketRuntime;
import weblogic.protocol.MessageReceiverStatistics;
import weblogic.protocol.MessageSenderStatistics;
import weblogic.protocol.MessageSenderStatisticsSupport;
import weblogic.utils.Debug;

public class ServerConnectionRuntimeImpl implements ServerConnectionRuntime, MessageSenderStatisticsSupport {
   private WeakReference sender;
   private final WeakReference receiver;
   private final WeakReference socket;

   public ServerConnectionRuntimeImpl(MessageSenderStatistics sender, MessageReceiverStatistics receiver, SocketRuntime socket) {
      Debug.assertion(receiver != null);
      this.sender = new WeakReference(sender);
      this.receiver = new WeakReference(receiver);
      this.socket = new WeakReference(socket);
   }

   public long getBytesReceivedCount() {
      MessageReceiverStatistics recv = this.getReceiver();
      return recv == null ? 0L : recv.getBytesReceivedCount();
   }

   public long getBytesSentCount() {
      MessageSenderStatistics send = this.getSender();
      return send == null ? 0L : send.getBytesSentCount();
   }

   public long getConnectTime() {
      MessageReceiverStatistics recv = this.getReceiver();
      return recv == null ? 0L : recv.getConnectTime();
   }

   public long getMessagesReceivedCount() {
      MessageReceiverStatistics recv = this.getReceiver();
      return recv == null ? 0L : recv.getMessagesReceivedCount();
   }

   public long getMessagesSentCount() {
      MessageSenderStatistics send = this.getSender();
      return send == null ? 0L : send.getMessagesSentCount();
   }

   public void addSender(MessageSenderStatistics sender) {
      this.sender = new WeakReference(sender);
   }

   private MessageSenderStatistics getSender() {
      return (MessageSenderStatistics)this.sender.get();
   }

   private MessageReceiverStatistics getReceiver() {
      return (MessageReceiverStatistics)this.receiver.get();
   }

   public SocketRuntime getSocketRuntime() {
      return (SocketRuntime)this.socket.get();
   }

   private Object writeReplace() throws ObjectStreamException {
      return new SerializableConnectionRuntime(this);
   }

   private static class SerializableConnectionRuntime implements ServerConnectionRuntime {
      private long bytesIn;
      private long bytesOut;
      private long msgIn;
      private long msgOut;
      private long connect;
      private SocketRuntime sock;

      private SerializableConnectionRuntime(ServerConnectionRuntime c) {
         this.bytesIn = c.getBytesReceivedCount();
         this.bytesOut = c.getBytesSentCount();
         this.msgIn = c.getMessagesReceivedCount();
         this.msgOut = c.getMessagesSentCount();
         this.connect = c.getConnectTime();
         SocketRuntime s = c.getSocketRuntime();
         if (s != null) {
            this.sock = new SocketRuntimeImpl(s);
         }

      }

      public long getBytesReceivedCount() {
         return this.bytesIn;
      }

      public long getBytesSentCount() {
         return this.bytesOut;
      }

      public long getConnectTime() {
         return this.connect;
      }

      public long getMessagesReceivedCount() {
         return this.msgIn;
      }

      public long getMessagesSentCount() {
         return this.msgOut;
      }

      public SocketRuntime getSocketRuntime() {
         return this.sock;
      }

      // $FF: synthetic method
      SerializableConnectionRuntime(ServerConnectionRuntime x0, Object x1) {
         this(x0);
      }
   }
}
