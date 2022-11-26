package weblogic.cluster.messaging.internal.server;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Iterator;
import weblogic.cluster.FragmentSocket;
import weblogic.cluster.messaging.internal.Environment;
import weblogic.cluster.messaging.internal.Message;
import weblogic.cluster.messaging.internal.MessageListener;
import weblogic.cluster.messaging.internal.MessageUtils;
import weblogic.cluster.messaging.internal.ServerConfigurationInformation;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class UnicastFragmentSocket implements FragmentSocket, MessageListener {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final boolean DEBUG;
   private boolean blocked = true;
   private ArrayList blockedPackets = new ArrayList();
   private int fragmentsSentCount = 0;
   private int fragmentsReceivedCount = 0;
   private ArrayList receivedMessages = new ArrayList();
   private boolean shutdown;
   private byte[] fragmentBuffer = new byte[0];

   public UnicastFragmentSocket() {
      if (!Environment.isInitialized()) {
         Environment.setLogService(LogServiceImpl.getInstance());
         Environment.initialize(ConfiguredServersMonitorImpl.getInstance(), ConnectionManagerImpl.getInstance(), PropertyServiceImpl.getInstance());
         this.registerRuntime();
      }

      Environment.getGroupManager().setMessageListener(this);
      if (DEBUG) {
         this.debug("initialized UnicastFragmentSocket !");
      }

   }

   private void registerRuntime() {
      try {
         RuntimeMBean parent = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().getClusterRuntime();
         new UnicastMessagingRuntimeMBeanImpl(parent);
      } catch (ManagementException var2) {
         if (DEBUG) {
            var2.printStackTrace();
         }
      }

   }

   private void debug(String s) {
      Environment.getLogService().debug("[UnicastFragmentSocket] " + s);
   }

   public synchronized void start() throws IOException {
      this.shutdown = false;
      this.blocked = false;
      Iterator blockedPacketsIter = this.blockedPackets.iterator();

      while(blockedPacketsIter.hasNext()) {
         Packet packet = (Packet)blockedPacketsIter.next();
         this.send(packet.buffer, packet.length);
      }

      this.blockedPackets.clear();
   }

   public void send(byte[] buffer, int length) throws IOException {
      if (this.blocked) {
         if (DEBUG) {
            this.debug("blocked. adding to blocked packets");
         }

         this.blockedPackets.add(new Packet(buffer, length));
      } else {
         ServerConfigurationInformation info = Environment.getConfiguredServersMonitor().getLocalServerConfiguration();
         byte[] data = new byte[length];
         System.arraycopy(buffer, 0, data, 0, length);
         Message message = MessageUtils.createMessage(data, info.getServerName(), 1L);
         if (DEBUG) {
            this.debug("sending '" + message + "' to local group");
         }

         Environment.getGroupManager().getLocalGroup().send(message);
         ++this.fragmentsSentCount;
      }
   }

   public byte[] receive() throws InterruptedIOException, IOException {
      Message message = null;
      synchronized(this.receivedMessages) {
         while(this.receivedMessages.size() == 0 && !this.shutdown) {
            try {
               if (DEBUG) {
                  this.debug("waiting for a message to arrive ...");
               }

               this.receivedMessages.wait();
            } catch (InterruptedException var5) {
            }
         }

         if (this.shutdown) {
            throw new IOException("unicast receiver is shutdown");
         }

         message = (Message)this.receivedMessages.remove(0);
      }

      int size = message.getData().length;
      if (size > this.fragmentBuffer.length) {
         if (DEBUG) {
            this.debug("Reallocating fragmentBuffer from " + this.fragmentBuffer.length + " to " + size + " bytes.");
         }

         this.fragmentBuffer = new byte[size];
      }

      System.arraycopy(message.getData(), 0, this.fragmentBuffer, 0, size);
      ++this.fragmentsReceivedCount;
      if (DEBUG) {
         this.debug("message '" + message + "' of size " + size + " received");
      }

      return this.fragmentBuffer;
   }

   public void shutdown() {
      if (DEBUG) {
         this.debug("shutdown");
      }

      this.blockedPackets.clear();
      this.shutdown = true;
      synchronized(this.receivedMessages) {
         this.receivedMessages.notify();
      }
   }

   public long getFragmentsSentCount() {
      return (long)this.fragmentsSentCount;
   }

   public long getFragmentsReceivedCount() {
      return (long)this.fragmentsReceivedCount;
   }

   public void setPacketDelay(long delay) {
   }

   public void shutdownPermanent() {
      this.shutdown();
   }

   public void onMessage(Message message) {
      synchronized(this.receivedMessages) {
         this.receivedMessages.add(message);
         this.receivedMessages.notify();
      }
   }

   static {
      DEBUG = Environment.DEBUG;
   }

   private static class Packet {
      private final byte[] buffer;
      private final int length;

      Packet(byte[] buffer, int length) {
         this.buffer = buffer;
         this.length = length;
      }
   }
}
