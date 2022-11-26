package weblogic.cluster;

import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.net.UnknownHostException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.UUID;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.WLObjectInputStream;
import weblogic.common.internal.WLObjectOutputStream;
import weblogic.health.HealthMonitorService;
import weblogic.kernel.Kernel;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.ServerChannelManager;
import weblogic.rjvm.JVMID;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.t3.srvr.SetUIDRendezvous;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.io.UnsyncByteArrayInputStream;
import weblogic.utils.io.UnsyncByteArrayOutputStream;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManagerFactory;
import weblogic.work.WorkManagerImpl;

public class ClusterMessagesManager extends WorkAdapter implements NakedTimerListener, MulticastSessionIDConstants {
   static final int HEARTBEAT_PERIOD_MILLIS = 10000;
   static final int MAX_FRAGMENT_SIZE = Kernel.getConfig().getMTUSize() - 100;
   static final int ONE_KILO_BYTE = 1024;
   private static final int MAX_RETRY_DELAY = 10000;
   private static final int RETRY_DELAY_INCREMENT = 1000;
   private int currentDelay = 0;
   private final TimerManager timerManager;
   private static final String osName = initOSNameProp();
   private static final boolean isLinux;
   private static final AuthenticatedSubject kernelId;
   private static ClusterMessagesManager theMulticastManager;
   private boolean serverResumed = false;
   private FragmentSocket sock;
   private byte[] fragmentBuffer;
   private boolean shutdown;
   private HashMap senders;
   private ArrayList currentHeartbeatItems;
   private ArrayList suspendedSendersLastSeqHBI;
   private ClusterMessageSender heartbeatSender;
   private long messagesLostCount;
   private long resendRequestsCount;
   private int localDomainNameHash;
   private int localClusterNameHash;
   private String localDomainName;
   private String localClusterName;
   private long numForeignFragementsDropped;
   private boolean canReceiveOwnMessages = false;
   private long sendStartTimestamp = 0L;
   private final int port;
   private final String multicastAddress;
   private final String clusterName;
   private final boolean isUnicast;
   private final boolean isDataEncryptionEnabled;
   private final String clusterAddress;

   public static ClusterMessagesManager theOne() {
      return theMulticastManager;
   }

   static void initialize(String mcastaddr, String interfaceAddress, int port, byte ttl, long packetDelay) throws IOException, UnknownHostException {
      theMulticastManager = new ClusterMessagesManager(mcastaddr, interfaceAddress, port, ttl, packetDelay);
   }

   private ClusterMessagesManager(String mcastaddr, String interfaceAddress, int port, byte ttl, long packetDelay) throws IOException, UnknownHostException {
      ClusterMBean clusterMBean = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster();
      this.isUnicast = "unicast".equals(clusterMBean.getClusterMessagingMode());
      int bufSize = clusterMBean.getMulticastBufferSize();
      if (bufSize > 0) {
         bufSize *= 1024;
      } else {
         bufSize = 32768;
      }

      this.sock = FragmentSocketWrapper.getInstance(mcastaddr, interfaceAddress, port, ttl, packetDelay, bufSize);
      this.fragmentBuffer = new byte['耀'];
      this.shutdown = false;
      this.senders = new HashMap();
      this.currentHeartbeatItems = new ArrayList();
      this.suspendedSendersLastSeqHBI = new ArrayList();
      this.heartbeatSender = this.createSender(HEARTBEAT_SENDER_ID, (RecoverListener)null, -1, false);
      this.messagesLostCount = 0L;
      this.resendRequestsCount = 0L;
      this.localDomainName = ManagementService.getRuntimeAccess(kernelId).getDomain().getName();
      this.localDomainNameHash = this.hashCode(this.localDomainName);
      this.localClusterName = clusterMBean.getName();
      this.localClusterNameHash = this.hashCode(this.localClusterName);
      this.clusterAddress = clusterMBean.getClusterAddress();
      this.numForeignFragementsDropped = 0L;
      this.multicastAddress = mcastaddr;
      this.port = port;
      this.clusterName = clusterMBean.getName();
      this.isDataEncryptionEnabled = ClusterService.getClusterServiceInternal().multicastDataEncryptionEnabled();
      this.timerManager = TimerManagerFactory.getTimerManagerFactory().getTimerManager("Heartbeat", WorkManagerFactory.getInstance().getSystem());
   }

   void startListening() throws IOException {
      PrivilegedAction action = new PrivilegedAction() {
         public Object run() {
            try {
               ClusterMessagesManager.this.sock.start();
               return null;
            } catch (IOException var2) {
               return var2;
            }
         }
      };
      IOException e = null;
      if (this.port > 1024) {
         e = (IOException)action.run();
      } else {
         e = (IOException)SetUIDRendezvous.doPrivileged(action);
      }

      if (e != null) {
         throw e;
      } else {
         WorkManagerImpl.executeDaemonTask("weblogic.cluster.MessageReceiver", 5, this);
      }
   }

   void startHeartbeat() {
      if (this.timerManager.isSuspended()) {
         this.timerManager.resume();
      } else {
         this.timerManager.scheduleAtFixedRate(this, 0L, 10000L);
      }

   }

   synchronized void suspendNonAdminMulticastSessions() {
      synchronized(this.senders) {
         Iterator senderItr = this.senders.values().iterator();

         while(senderItr.hasNext()) {
            ClusterMessageSender sender = (ClusterMessageSender)senderItr.next();
            if (!sender.isAdminSender() && sender.isRetryEnabled()) {
               sender.suspend();
               Serializable obj = new LastSeqNumHBI(sender.getSessionID(), 0L, false);
               int index = this.currentHeartbeatItems.indexOf(obj);
               if (index >= 0) {
                  Serializable item = (Serializable)this.currentHeartbeatItems.get(index);
                  if (item != null) {
                     this.removeItem(item);
                     this.suspendedSendersLastSeqHBI.add(item);
                  }
               }
            }
         }

      }
   }

   synchronized void resumeNonAdminMulticastSessions() {
      Iterator lastSeqItr = this.suspendedSendersLastSeqHBI.iterator();

      while(lastSeqItr.hasNext()) {
         LastSeqNumHBI item = (LastSeqNumHBI)lastSeqItr.next();
         this.addItem(item);
      }

      this.suspendedSendersLastSeqHBI.clear();
      synchronized(this.senders) {
         Iterator senderItr = this.senders.values().iterator();

         while(senderItr.hasNext()) {
            ClusterMessageSender sender = (ClusterMessageSender)senderItr.next();
            if (!sender.isAdminSender()) {
               sender.resume();
            }
         }

      }
   }

   void stopHeartbeat() {
      if (!this.timerManager.isSuspended()) {
         try {
            this.heartbeatSender.send(new ShutdownMessage());
         } catch (IOException var3) {
         }

         try {
            this.timerManager.suspend();
         } catch (IllegalStateException var2) {
         }
      }

   }

   void handleMissedOwnMessages() {
      long diff = System.currentTimeMillis() - this.sendStartTimestamp;
      ClusterMBean clusterMBean = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster();
      long time = (long)(10000 * (clusterMBean.getIdlePeriodsUntilTimeout() + 1));
      if (diff > time) {
         ClusterLogger.logMessageCannotReceiveOwnMessages(ManagementService.getRuntimeAccess(kernelId).getServer().getName());
         HealthMonitorService.subsystemFailed("Cluster", "Unable to receive self generated multicast messages");
      }

   }

   void stopListening() {
      this.shutdown = true;
      this.sock.shutdown();
   }

   ClusterMessageSender createSender(MulticastSessionId multicastSessionId, RecoverListener rl, int cacheSize, boolean useHTTPForSD) {
      return this.createSender(multicastSessionId, rl, cacheSize, useHTTPForSD, false);
   }

   ClusterMessageSender createSender(MulticastSessionId multicastSessionId, RecoverListener rl, int cacheSize, boolean useHTTPForSD, boolean adminSender) {
      MulticastSessionId sID = multicastSessionId;
      synchronized(this.senders) {
         ClusterMessageSender ms = null;
         if (multicastSessionId.equals(CUSTOM_MULTICAST_SESSION_ID)) {
            sID = new MulticastSessionId("0", "NO_RESOURCE_GROUP", UUID.randomUUID().toString());
         }

         ms = (ClusterMessageSender)this.senders.get(sID);
         if (ms == null) {
            ms = this.isUnicast ? new UnicastSender(sID, this.sock, rl, cacheSize, useHTTPForSD, adminSender) : new MulticastSender(sID, this.sock, rl, cacheSize, useHTTPForSD, adminSender);
            this.senders.put(sID, ms);
         }

         return (ClusterMessageSender)ms;
      }
   }

   void removeSender(MulticastSessionId multicastSessionId) {
      synchronized(this.senders) {
         if (this.senders.containsKey(multicastSessionId)) {
            ClusterMessageSender var3 = (ClusterMessageSender)this.senders.remove(multicastSessionId);
         }

      }
   }

   ClusterMessageSender findSender(MulticastSessionId multicastSessionId) {
      synchronized(this.senders) {
         return (ClusterMessageSender)this.senders.get(multicastSessionId);
      }
   }

   public synchronized void addItem(Serializable item) {
      this.currentHeartbeatItems.add(item);
   }

   synchronized void removeItem(Serializable item) {
      this.currentHeartbeatItems.remove(item);
   }

   synchronized void replaceItem(Serializable item) {
      this.removeItem(item);
      this.addItem(item);
   }

   void flagStartedSending() {
      this.sendStartTimestamp = System.currentTimeMillis();
   }

   long getSendStartTimestamp() {
      return this.sendStartTimestamp;
   }

   boolean getCanReceiveOwnMessages() {
      return this.canReceiveOwnMessages;
   }

   void receiveHeartbeat(final HostID memberID, final HeartbeatMessage hb) {
      SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedAction() {
         public Object run() {
            Iterator itemsIter;
            if (ClusterHeartbeatsDebugLogger.isDebugEnabled()) {
               ClusterDebugLogger.debug("Received " + hb + " from " + memberID);
               itemsIter = hb.items.iterator();

               while(itemsIter.hasNext()) {
                  ClusterDebugLogger.debug("  " + itemsIter.next());
               }
            }

            itemsIter = hb.items.iterator();

            while(true) {
               while(itemsIter.hasNext()) {
                  Object current = itemsIter.next();
                  if (current instanceof GroupMessage) {
                     ((GroupMessage)current).execute(memberID);
                  } else if (current instanceof LastSeqNumHBI) {
                     LastSeqNumHBI item = (LastSeqNumHBI)current;
                     if (ClusterMessagesManager.this.serverResumed || item.multicastSessionId.equals(MulticastSessionIDConstants.HEARTBEAT_SENDER_ID) || item.multicastSessionId.equals(MulticastSessionIDConstants.ATTRIBUTE_MANAGER_ID) || item.multicastSessionId.equals(MulticastSessionIDConstants.MEMBER_MANAGER_ID)) {
                        RemoteMemberInfo memInfo = MemberManager.theOne().findOrCreate(memberID);

                        try {
                           ClusterMessageReceiver receiver = memInfo.findOrCreateReceiver(item.multicastSessionId, item.useHTTPForSD);
                           receiver.processLastSeqNum(item.lastSeqNum);
                        } finally {
                           MemberManager.theOne().done(memInfo);
                        }
                     }
                  } else if (current instanceof NAKHBI) {
                     NAKHBI itemx = (NAKHBI)current;
                     if (itemx.memID.isLocal()) {
                        synchronized(ClusterMessagesManager.this.senders) {
                           if (ClusterHeartbeatsDebugLogger.isDebugEnabled() && ClusterMessagesManager.this.senders.get(itemx.multicastSessionId) == null) {
                              ClusterDebugLogger.debug("MulticastManager.receiveHeartbeat found invalid message. ignored. : " + itemx);
                              continue;
                           }
                        }

                        ClusterMessageSender sender = ClusterMessagesManager.this.findSender(itemx.multicastSessionId);
                        if (sender != null) {
                           sender.processNAK(itemx.seqNum, itemx.fragNum, itemx.serverVersion);
                        } else if (ClusterHeartbeatsDebugLogger.isDebugEnabled()) {
                           ClusterDebugLogger.debug("Received session " + itemx.multicastSessionId + " has not been initialized yet.");
                        }
                     }
                  }
               }

               return null;
            }
         }
      });
   }

   public void run() {
      while(!this.shutdown) {
         MessageHeader header = null;

         try {
            this.fragmentBuffer = this.sock.receive();
            WLObjectInputStream ois = getInputStream(this.fragmentBuffer);
            header = ClusterMessagesManager.MessageHeader.readMessageHeader(ois);
            if (header.isLocal()) {
               this.canReceiveOwnMessages = true;
            } else if (!this.headerInfoIsInvalid(header)) {
               setStaticClusterAddress(this.clusterAddress, (JVMID)header.memberID);
               MemberManager.theOne().resetTimeout(header.memberID);
               byte[] plain = this.readMessagePayload(ois, header);
               if (plain == null) {
                  return;
               }

               ois = getInputStream(plain);
               MulticastSessionId multicastSessionId = (MulticastSessionId)ois.readObject();
               long seqNum = ois.readLong();
               int fragNum = ois.readInt();
               int size = ois.readInt();
               int offset = ois.readInt();
               boolean isRecover = ois.readBoolean();
               boolean retryEnabled = ois.readBoolean();
               boolean useHTTPForSD = ois.readBoolean();
               byte[] data = ois.readBytes();
               if (this.serverResumed || multicastSessionId.equals(HEARTBEAT_SENDER_ID) || multicastSessionId.equals(ATTRIBUTE_MANAGER_ID) || multicastSessionId.equals(MEMBER_MANAGER_ID)) {
                  RemoteMemberInfo memInfo = MemberManager.theOne().findOrCreate(header.memberID);

                  try {
                     ClusterMessageReceiver receiver = memInfo.findOrCreateReceiver(multicastSessionId, useHTTPForSD);
                     receiver.dispatch(seqNum, fragNum, size, offset, isRecover, retryEnabled, data);
                  } finally {
                     MemberManager.theOne().done(memInfo);
                  }
               }

               this.currentDelay = 0;
            }
         } catch (InterruptedIOException var25) {
         } catch (EOFException var26) {
            ClusterLogger.logMulticastAddressCollision(this.clusterName, this.multicastAddress, this.port + "");
         } catch (OptionalDataException var27) {
            ClusterLogger.logMulticastAddressCollision(this.clusterName, this.multicastAddress, this.port + "");
         } catch (StreamCorruptedException var28) {
            if (header.memberID != null && this.isMessagePayLoadEncrypted()) {
               ClusterLogger.logMessageDigestInvalid(header.memberID.objectToString());
            } else if (this.isUnicast) {
               ClusterExtensionLogger.logUnicastReceiveError(var28);
            } else {
               ClusterLogger.logMulticastReceiveError(var28);
            }
         } catch (IOException var29) {
            if (!this.shutdown) {
               if (this.isUnicast) {
                  ClusterExtensionLogger.logUnicastReceiveError(var29);
               } else {
                  ClusterLogger.logMulticastReceiveError(var29);
               }
            }

            this.delay();
         } catch (Throwable var30) {
            if (!this.shutdown) {
               if (this.isUnicast) {
                  ClusterExtensionLogger.logUnicastReceiveError(var30);
               } else {
                  ClusterLogger.logMulticastReceiveError(var30);
               }
            }
         }
      }

   }

   protected byte[] readMessagePayload(WLObjectInputStream ois, MessageHeader header) throws IOException, ClassNotFoundException {
      byte[] remoteDigest = this.readRemoteDigestIfPresent(ois, header.messageVersion);
      byte[] payload = null;
      byte[] plain = null;

      try {
         byte[] payload = (byte[])((byte[])ois.readObject());
         plain = payload;
         if (this.isMessageSigned(remoteDigest)) {
            if (!EncryptionHelper.verify(payload, remoteDigest)) {
               ClusterLogger.logMessageDigestInvalid(header.memberID.objectToString());
               return null;
            }

            if (ClusterService.getClusterServiceInternal().multicastDataEncryptionEnabled()) {
               plain = EncryptionHelper.decrypt(payload, kernelId);
            }
         }
      } catch (EOFException var7) {
         if (ClusterDebugLogger.isDebugEnabled()) {
            ClusterDebugLogger.debug("Encountered EOFException reading cluster message of version: " + header.messageVersion + " Most likely due to missing digest");
         }
      }

      return plain;
   }

   private boolean isMessageSigned(byte[] remoteDigest) {
      return remoteDigest != null;
   }

   private boolean isMessagePayLoadEncrypted() {
      return this.isDataEncryptionEnabled;
   }

   private byte[] readRemoteDigestIfPresent(WLObjectInputStream ois, String messageVersion) throws IOException, ClassNotFoundException {
      byte[] remoteDigest = null;
      if (this.canReadRemoteDigest(messageVersion)) {
         remoteDigest = (byte[])((byte[])ois.readObject());
      }

      return remoteDigest;
   }

   protected boolean headerInfoIsInvalid(MessageHeader header) throws IOException {
      if (this.isFragmentFromForeignCluster(header.domainNameHash, header.clusterNameHash)) {
         ++this.numForeignFragementsDropped;
         this.logFragmentFromForeignCluster(header.memberID, header.domainNameHash, header.clusterNameHash);
         return true;
      } else {
         return !acceptMessageVersion(header.messageVersion);
      }
   }

   private void logFragmentFromForeignCluster(HostID memberID, int domainNameHash, int clusterNameHash) {
      if (!this.isUnicast) {
         if (domainNameHash != this.localDomainNameHash) {
            if (!isLinux) {
               ClusterLogger.logMultipleDomainsCannotUseSameMulticastAddress2(this.localDomainName);
            }
         } else {
            ClusterLogger.logMultipleClustersCannotUseSameMulticastAddress2(this.localClusterName);
         }
      }

      if (ClusterDebugLogger.isDebugEnabled()) {
         ClusterDebugLogger.debug("dropped fragment from foreign domain/cluster domainhash=" + domainNameHash + " clusterhash=" + clusterNameHash + " id=" + memberID);
      }

   }

   private boolean canReadRemoteDigest(String messageVersion) {
      if (this.isMessagePayLoadEncrypted()) {
         return true;
      } else if (UpgradeUtils.getInstance().acceptGreaterThanOrEqualVersion(messageVersion)) {
         return true;
      } else {
         return UpgradeUtils.getInstance().isHeartbeatMessageVersion(messageVersion) ? UpgradeUtils.getInstance().isSignedHeartbeatMessage(messageVersion) : false;
      }
   }

   private static void setStaticClusterAddress(String clusterAddress, JVMID jvmid) {
      if (clusterAddress != null) {
         String addr = jvmid.getClusterAddress();
         if (addr != null && addr.isEmpty()) {
            jvmid.setClusterAddress(clusterAddress);
         }
      }

   }

   private static boolean acceptMessageVersion(String messageVersion) {
      if (UpgradeUtils.getInstance().acceptVersion(messageVersion)) {
         if (ClusterDebugLogger.isDebugEnabled()) {
            ClusterDebugLogger.debug("[UPGRADE] accepting a group message as the remote version is compatible. message version is " + messageVersion);
         }

         return true;
      } else {
         if (ClusterDebugLogger.isDebugEnabled()) {
            ClusterDebugLogger.debug("[UPGRADE] DROPPING a group message as the remote version is NOT compatible. message version is " + messageVersion);
         }

         return false;
      }
   }

   private void delay() {
      this.currentDelay = Math.min(this.currentDelay + 1000, 10000);

      try {
         Thread.sleep((long)this.currentDelay);
      } catch (InterruptedException var2) {
      }

   }

   MulticastSession getSender(MulticastSessionId multicastSessionId) {
      return (MulticastSession)this.senders.get(multicastSessionId);
   }

   void forceSuspend() {
      this.serverResumed = false;
   }

   void resume() {
      this.serverResumed = true;
   }

   protected boolean isFragmentFromForeignCluster(int domainNameHash, int clusterNameHash) {
      return this.localDomainNameHash != domainNameHash || this.localClusterNameHash != clusterNameHash;
   }

   public int hashCode(String s) {
      int h = 0;

      for(int i = 0; i < s.length(); ++i) {
         h = 31 * h + s.charAt(i);
      }

      return h;
   }

   static WLObjectInputStream getInputStream(byte[] buffer) throws IOException {
      UnsyncByteArrayInputStream isb = new UnsyncByteArrayInputStream(buffer);
      WLObjectInputStream ios = new WLObjectInputStream(isb);
      ios.setReplacer(new MulticastReplacer(LocalServerIdentity.getIdentity()));
      return ios;
   }

   static WLObjectOutputStream getOutputStream(UnsyncByteArrayOutputStream baos) throws IOException {
      return UpgradeUtils.getInstance().getOutputStream(baos, ServerChannelManager.findDefaultLocalServerChannel());
   }

   static WLObjectOutputStream getOutputStream(UnsyncByteArrayOutputStream baos, PeerInfo peerInfo) throws IOException {
      return UpgradeUtils.getInstance().getOutputStream(baos, ServerChannelManager.findDefaultLocalServerChannel(), peerInfo);
   }

   boolean isUnicastMessagingMode() {
      return this.isUnicast;
   }

   long getFragmentsSentCount() {
      return this.sock.getFragmentsSentCount();
   }

   long getFragmentsReceivedCount() {
      return this.sock.getFragmentsReceivedCount();
   }

   long getMulticastMessagesLostCount() {
      return this.messagesLostCount;
   }

   long getResendRequestsCount() {
      return this.resendRequestsCount;
   }

   void incrementMulticastMessagesLostCount(long numLost) {
      this.messagesLostCount += numLost;
   }

   void incrementResendRequestsCount() {
      ++this.resendRequestsCount;
   }

   long getForeignFragmentsDroppedCount() {
      return this.numForeignFragementsDropped;
   }

   public String toString() {
      return "Read Multicast Msg Fragment";
   }

   public void setPacketDelay(long delay) {
      this.sock.setPacketDelay(delay);
   }

   public void timerExpired(Timer timer) {
      try {
         HeartbeatMessage message;
         synchronized(this) {
            message = new HeartbeatMessage((ArrayList)this.currentHeartbeatItems.clone());
         }

         this.heartbeatSender.send(message);
         if (ClusterHeartbeatsDebugLogger.isDebugEnabled()) {
            ClusterHeartbeatsDebugLogger.debug("Sent " + message);
            Iterator itemsIter = message.items.iterator();

            while(itemsIter.hasNext()) {
               ClusterHeartbeatsDebugLogger.debug("  " + itemsIter.next());
            }
         }
      } catch (IOException var6) {
         ClusterLogger.logMulticastSendError(var6);
      }

   }

   private static String initOSNameProp() {
      String s = "UNKNOWN";

      try {
         s = System.getProperty("os.name", "UNKNOWN").toLowerCase(Locale.ENGLISH);
      } catch (SecurityException var2) {
      }

      return s;
   }

   void dumpDiagnosticImageData(XMLStreamWriter xsw) throws XMLStreamException, IOException {
      xsw.writeStartElement("MulticastManager");
      MemberManager memberManager = MemberManager.theOne();
      if (memberManager != null) {
         memberManager.dumpDiagnosticImageData(xsw);
      }

      xsw.writeEndElement();
   }

   static {
      isLinux = "linux".equals(osName);
      kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      theMulticastManager = null;
   }

   static class MessageHeader {
      int domainNameHash;
      int clusterNameHash;
      HostID memberID;
      String messageVersion;

      private MessageHeader(int domainNameHash, int clusterNameHash, HostID memberID, String messageVersion) {
         this.domainNameHash = domainNameHash;
         this.clusterNameHash = clusterNameHash;
         this.memberID = memberID;
         this.messageVersion = messageVersion;
      }

      static MessageHeader readMessageHeader(WLObjectInputStream ois) throws IOException, ClassNotFoundException {
         int domainNameHash = ois.readInt();
         int clusterNameHash = ois.readInt();
         HostID hostID = (HostID)ois.readObjectWL();
         String messageVersion = ois.readString();
         MessageHeader header = new MessageHeader(domainNameHash, clusterNameHash, hostID, messageVersion);
         if (ClusterDebugLogger.isDebugEnabled()) {
            ClusterDebugLogger.debug("MessageHeader: " + header);
         }

         return header;
      }

      boolean isLocal() {
         return this.memberID.isLocal();
      }

      public String toString() {
         return "domainNameHash: " + this.domainNameHash + ", clusterNameHash: " + this.clusterNameHash + ", memberID: " + this.memberID + ", messageVersion: " + this.messageVersion;
      }
   }
}
