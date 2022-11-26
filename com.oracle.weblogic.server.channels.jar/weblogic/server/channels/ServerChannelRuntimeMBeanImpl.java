package weblogic.server.channels;

import java.util.Iterator;
import java.util.Map;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.ServerChannelRuntimeMBean;
import weblogic.management.runtime.ServerConnectionRuntime;
import weblogic.management.runtime.SocketRuntime;
import weblogic.protocol.ServerChannel;
import weblogic.utils.collections.ConcurrentWeakHashMap;

public class ServerChannelRuntimeMBeanImpl extends RuntimeMBeanDelegate implements ServerChannelRuntimeMBean {
   private final Map connectedSockets = new ConcurrentWeakHashMap();
   private final ServerChannel channel;
   private long pastBytesSent;
   private long pastBytesReceived;
   private long pastMessagesSent;
   private long pastMessagesReceived;
   private long acceptCount;

   ServerChannelRuntimeMBeanImpl(ServerChannelImpl sc) throws ManagementException {
      super(sc.getChannelName().replace(':', '_'));
      this.channel = sc;
   }

   public void addServerConnectionRuntime(ServerConnectionRuntime conn) {
      this.connectedSockets.put(conn.getSocketRuntime(), conn);
      ++this.acceptCount;
   }

   public void removeServerConnectionRuntime(SocketRuntime sock) {
      ServerConnectionRuntime runtime = (ServerConnectionRuntime)this.connectedSockets.remove(sock);
      if (runtime != null) {
         this.pastBytesReceived += runtime.getBytesReceivedCount();
         this.pastBytesSent += runtime.getBytesSentCount();
         this.pastMessagesSent += runtime.getMessagesSentCount();
         this.pastMessagesReceived += runtime.getMessagesReceivedCount();
      }

   }

   public ServerConnectionRuntime[] getServerConnectionRuntimes() {
      return (ServerConnectionRuntime[])this.connectedSockets.values().toArray(new ServerConnectionRuntime[this.connectedSockets.size()]);
   }

   public long getConnectionsCount() {
      return (long)this.connectedSockets.size();
   }

   public long getBytesReceivedCount() {
      long bytes = this.pastBytesReceived;

      ServerConnectionRuntime scr;
      for(Iterator var3 = this.connectedSockets.values().iterator(); var3.hasNext(); bytes += scr.getBytesReceivedCount()) {
         scr = (ServerConnectionRuntime)var3.next();
      }

      return bytes;
   }

   public long getAcceptCount() {
      return this.acceptCount;
   }

   public long getBytesSentCount() {
      long bytes = this.pastBytesSent;

      ServerConnectionRuntime scr;
      for(Iterator var3 = this.connectedSockets.values().iterator(); var3.hasNext(); bytes += scr.getBytesSentCount()) {
         scr = (ServerConnectionRuntime)var3.next();
      }

      return bytes;
   }

   public String getChannelName() {
      return this.channel.getChannelName();
   }

   public String getAssociatedVirtualTargetName() {
      return this.channel.getAssociatedVirtualTargetName();
   }

   public long getMessagesReceivedCount() {
      long m = this.pastMessagesReceived;

      for(Iterator i = this.connectedSockets.values().iterator(); i.hasNext(); m += ((ServerConnectionRuntime)i.next()).getMessagesReceivedCount()) {
      }

      return m;
   }

   public long getMessagesSentCount() {
      long m = this.pastMessagesSent;

      ServerConnectionRuntime scr;
      for(Iterator var3 = this.connectedSockets.values().iterator(); var3.hasNext(); m += scr.getMessagesSentCount()) {
         scr = (ServerConnectionRuntime)var3.next();
      }

      return m;
   }

   public String getPublicURL() {
      return this.channel.getProtocolPrefix() + "://" + this.channel.getPublicAddress() + ":" + this.channel.getPublicPort();
   }
}
