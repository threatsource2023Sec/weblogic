package weblogic.cluster;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import weblogic.utils.net.InetAddressHelper;

class MulticastFragmentSocket implements FragmentSocket {
   private static final boolean DEBUG = false;
   private String multicastAddr;
   private InetAddress group;
   private InetAddress interfaceAddr;
   private int port;
   private MulticastSocket sock;
   private boolean blocked;
   private ArrayList blockedPackets;
   private long packetDelay;
   private long lastDelay;
   private long lastSendTime;
   private long fragmentsSentCount;
   private long fragmentsReceivedCount;
   private static final String osName = initOSNameProp();
   private static final boolean isLinux;
   private String clusterName;
   private static final int SO_TIMEOUT = 30000;
   private byte ttl;
   private int bufSize;
   private boolean shutdownPermanent;
   private byte[] fragmentBuffer = new byte['耀'];
   private Object SOCK_INIT_LOCK = new Object();

   MulticastFragmentSocket(String clusterName, String mcastaddr, String interfaceAddress, int port, byte ttl, long packetDelay, int bufSize) throws IOException, UnknownHostException {
      this.clusterName = clusterName;
      this.multicastAddr = mcastaddr;
      this.group = InetAddress.getByName(mcastaddr);
      this.port = port;
      if (interfaceAddress != null) {
         if (ClusterDebugLogger.isDebugEnabled()) {
            ClusterDebugLogger.debug("Setting the interface address to : " + interfaceAddress);
         }

         this.interfaceAddr = InetAddress.getByName(interfaceAddress);
      }

      this.ttl = ttl;
      this.bufSize = bufSize;
      this.blocked = true;
      this.blockedPackets = new ArrayList();
      this.packetDelay = packetDelay;
      this.lastDelay = 0L;
      this.lastSendTime = 0L;
      this.fragmentsSentCount = 0L;
      this.fragmentsReceivedCount = 0L;
   }

   private void initializeMulticastSocket() throws IOException {
      if (this.sock == null) {
         synchronized(this.SOCK_INIT_LOCK) {
            if (this.sock == null) {
               MulticastSocket local_sock = this.createMulticastSocket();
               if (this.interfaceAddr != null) {
                  local_sock.setInterface(this.interfaceAddr);
               }

               local_sock.setTimeToLive(this.ttl);
               local_sock.setSoTimeout(30000);
               if (local_sock.getReceiveBufferSize() < this.bufSize) {
                  local_sock.setReceiveBufferSize(this.bufSize);
               }

               if (local_sock.getSendBufferSize() < this.bufSize) {
                  local_sock.setSendBufferSize(this.bufSize);
               }

               local_sock.joinGroup(this.group);
               this.sock = local_sock;
            }
         }
      }
   }

   private MulticastSocket createMulticastSocket() throws IOException {
      MulticastSocket local_sock = null;
      if (isLinux()) {
         boolean hasIPv6Stack = InetAddressHelper.hasIPv6Stack();
         boolean hasIPv4Stack = InetAddressHelper.hasIPv4Stack();
         boolean isIPv4MulticastAddress = InetAddressHelper.isIPv4MulticastAddress(this.multicastAddr);
         boolean isIPv6MulticastAddress = InetAddressHelper.isIPv6MulticastAddress(this.multicastAddr);
         if (ClusterDebugLogger.isDebugEnabled()) {
            ClusterDebugLogger.debug("********* hasIPv4Stack: " + hasIPv4Stack + " ***********");
            ClusterDebugLogger.debug("********* hasIPv6Stack: " + hasIPv6Stack + " ***********");
            ClusterDebugLogger.debug("********* isIPv4MulticastAddress: " + isIPv4MulticastAddress + " ***********");
            ClusterDebugLogger.debug("********* isIPv6MulticastAddress: " + isIPv6MulticastAddress + " ***********");
         }

         if (hasIPv4Stack && !hasIPv6Stack && isIPv4MulticastAddress || hasIPv6Stack && isIPv6MulticastAddress) {
            try {
               local_sock = new MulticastSocket(new InetSocketAddress(this.multicastAddr, this.port));
            } catch (BindException var7) {
               ClusterExtensionLogger.logFailedToBindMulticastSocketToMulticastGroupAddress(this.clusterName, this.multicastAddr);
               if (ClusterDebugLogger.isDebugEnabled()) {
                  ClusterDebugLogger.debug("Failed to bind to : " + this.multicastAddr, var7);
                  ClusterDebugLogger.debug("Will try binding to IP_ANY...");
               }
            }
         }
      }

      if (local_sock == null) {
         local_sock = new MulticastSocket(this.port);
         if (ClusterDebugLogger.isDebugEnabled()) {
            ClusterDebugLogger.debug("Bound MulticastSocket to socket: " + local_sock);
         }
      }

      return local_sock;
   }

   private static String initOSNameProp() {
      String s = "UNKNOWN";

      try {
         s = System.getProperty("os.name", "UNKNOWN").toLowerCase(Locale.ENGLISH);
      } catch (SecurityException var2) {
      }

      return s;
   }

   public synchronized void send(byte[] buffer, int length) throws IOException {
      if (this.shutdownPermanent) {
         throw new IOException("multicast socket shutdown to enable unicast mode");
      } else {
         DatagramPacket packet = new DatagramPacket(buffer, length, this.group, this.port);
         if (this.blocked) {
            this.blockedPackets.add(packet);
         } else {
            this.sendThrottled(packet);
         }

      }
   }

   public synchronized void start() throws IOException {
      this.initializeMulticastSocket();
      this.blocked = false;
      Iterator blockedPacketsIter = this.blockedPackets.iterator();

      while(blockedPacketsIter.hasNext()) {
         DatagramPacket packet = (DatagramPacket)blockedPacketsIter.next();
         this.sendThrottled(packet);
      }

      this.blockedPackets.clear();
   }

   public void shutdown() {
      try {
         if (this.sock != null) {
            this.sock.leaveGroup(this.group);
            this.sock.close();
         }
      } catch (IOException var2) {
      }

      this.sock = null;
      this.blockedPackets.clear();
   }

   private void sendThrottled(DatagramPacket packet) throws IOException {
      long currentTime = System.currentTimeMillis();
      long delayTime = this.packetDelay - (currentTime - this.lastSendTime);
      if (delayTime <= 0L) {
         this.lastSendTime = currentTime;
      } else {
         try {
            Thread.sleep(delayTime);
         } catch (InterruptedException var7) {
         }

         this.lastSendTime = currentTime + delayTime;
      }

      try {
         if (this.sock == null) {
            this.initializeMulticastSocket();
         }

         this.sock.send(packet);
         if (ClusterMessagesManager.theOne().getSendStartTimestamp() == 0L) {
            ClusterMessagesManager.theOne().flagStartedSending();
         } else if (!ClusterMessagesManager.theOne().getCanReceiveOwnMessages()) {
            ClusterMessagesManager.theOne().handleMissedOwnMessages();
         }
      } catch (IOException var8) {
         if (this.sock != null) {
            this.shutdown();
         }

         throw var8;
      }

      ++this.fragmentsSentCount;
   }

   public byte[] receive() throws InterruptedIOException, IOException {
      DatagramPacket packet = new DatagramPacket(this.fragmentBuffer, this.fragmentBuffer.length);

      try {
         if (this.sock == null) {
            if (this.shutdownPermanent) {
               throw new IOException("multicast socket shutdown to enable unicast mode");
            }

            this.initializeMulticastSocket();
         }

         this.sock.receive(packet);
      } catch (IOException var3) {
         if (this.sock != null) {
            this.shutdown();
         }

         throw var3;
      }

      ++this.fragmentsReceivedCount;
      return packet.getData();
   }

   public long getFragmentsSentCount() {
      return this.fragmentsSentCount;
   }

   public long getFragmentsReceivedCount() {
      return this.fragmentsReceivedCount;
   }

   public void setPacketDelay(long delay) {
      this.packetDelay = delay;
   }

   public synchronized void shutdownPermanent() {
      this.shutdownPermanent = true;
      this.shutdown();
   }

   public static boolean isLinux() {
      return isLinux;
   }

   static {
      isLinux = "linux".equals(osName);
   }
}
