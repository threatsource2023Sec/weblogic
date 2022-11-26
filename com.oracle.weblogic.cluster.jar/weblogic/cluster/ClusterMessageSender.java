package weblogic.cluster;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.AccessController;
import java.util.Arrays;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.WLObjectOutputStream;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.LocalServerIdentity;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.io.UnsyncByteArrayOutputStream;

public abstract class ClusterMessageSender implements MulticastSession {
   private static final int DEFAULT_CACHE_SIZE = 3;
   private static final int RESEND_BLOCKING_INTERVAL_MILLIS = 2000;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   protected MulticastSessionId multicastSessionId;
   protected FragmentSocket sock;
   private RecoverListener rl;
   private boolean retryEnabled;
   private int cacheSize;
   private OutgoingMessage[] cache;
   private int numMessages;
   private long oldestSeqNum;
   private long lastResendTime;
   private long lastSeqNumResent;
   private int lastFragNumResent;
   private int localDomainNameHash;
   private int localClusterNameHash;
   private final boolean useHTTPForSD;
   private final boolean adminSender;
   private boolean suspended;

   ClusterMessageSender(MulticastSessionId multicastSessionId, FragmentSocket sock, RecoverListener rl, int size, boolean useHTTPForSD) {
      this(multicastSessionId, sock, rl, size, useHTTPForSD, false);
   }

   ClusterMessageSender(MulticastSessionId multicastSessionId, FragmentSocket sock, RecoverListener rl, int size, boolean useHTTPForSD, boolean adminSender) {
      this.multicastSessionId = multicastSessionId;
      this.sock = sock;
      this.rl = rl;
      this.adminSender = adminSender;
      this.suspended = false;
      this.retryEnabled = rl != null;
      if (!this.retryEnabled) {
         this.cacheSize = 1;
      } else if (size > 0) {
         this.cacheSize = size;
      } else {
         this.cacheSize = 3;
      }

      this.cache = new OutgoingMessage[this.cacheSize];

      for(int i = 0; i < this.cacheSize; ++i) {
         this.cache[i] = new OutgoingMessage();
      }

      this.numMessages = 0;
      this.oldestSeqNum = 0L;
      this.lastResendTime = 0L;
      ClusterMBean clusterMBean = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster();
      this.localDomainNameHash = hashCode(ManagementService.getRuntimeAccess(kernelId).getDomain().getName());
      this.localClusterNameHash = hashCode(clusterMBean.getName());
      this.useHTTPForSD = useHTTPForSD;
   }

   public synchronized void send(GroupMessage message) throws IOException {
      if (!this.isAdminSender() && this.suspended) {
         if (ClusterDebugLogger.isDebugEnabled()) {
            ClusterDebugLogger.debug("Not sending message " + message + " as the multicast session is supended");
         }

      } else {
         OutgoingMessage om = this.prepare(message, false);
         if (ClusterDebugLogger.isDebugEnabled()) {
            ClusterDebugLogger.debug("Sending MulticastSessionId:" + this.multicastSessionId + " seqNum:" + om.seqNum + " GroupMessage:" + message + " OutgoingMessageSize:" + om.size);
         }

         this.send(om, 0);
         if (this.retryEnabled) {
            this.lastResendTime = System.currentTimeMillis();
            this.lastSeqNumResent = om.seqNum;
            this.lastFragNumResent = 0;
            ClusterMessagesManager.theOne().replaceItem(new LastSeqNumHBI(this.multicastSessionId, om.seqNum, this.useHTTPForSD));
         }

      }
   }

   synchronized void processNAK(long seqNum, int fragNum, String serverVersion) {
      try {
         long now = System.currentTimeMillis();
         if (seqNum < this.lastSeqNumResent || seqNum == this.lastSeqNumResent && fragNum < this.lastFragNumResent || now - this.lastResendTime > 2000L) {
            this.lastResendTime = now;
            long startSeqNum;
            int startFragNum;
            if (seqNum >= this.oldestSeqNum) {
               if (ClusterDebugLogger.isDebugEnabled()) {
                  ClusterDebugLogger.debug("Resending senderID:" + this.multicastSessionId + " seqNum:" + seqNum + " fragNum:" + fragNum);
               }

               startSeqNum = seqNum;
               startFragNum = fragNum;
               this.lastSeqNumResent = seqNum;
               this.lastFragNumResent = fragNum;
            } else {
               if (this.cache[(int)(this.oldestSeqNum % (long)this.cacheSize)].isRecover) {
                  if (ClusterDebugLogger.isDebugEnabled()) {
                     ClusterDebugLogger.debug("Resending recover senderID:" + this.multicastSessionId + " seqNum:" + this.oldestSeqNum);
                  }
               } else {
                  GroupMessage message = this.rl.createRecoverMessage();
                  if (ClusterDebugLogger.isDebugEnabled()) {
                     ClusterDebugLogger.debug("Sending recover senderID:" + this.multicastSessionId + " seqNum:" + this.oldestSeqNum + " message:" + message);
                  }

                  OutgoingMessage om = this.prepare(message, true);
                  ClusterMessagesManager.theOne().replaceItem(new LastSeqNumHBI(this.multicastSessionId, om.seqNum, this.useHTTPForSD));
               }

               startSeqNum = this.oldestSeqNum;
               startFragNum = 0;
               this.lastSeqNumResent = 0L;
               this.lastFragNumResent = 0;
            }

            for(long i = startSeqNum; i <= this.oldestSeqNum + (long)this.numMessages - 1L; ++i) {
               OutgoingMessage om = this.cache[(int)(i % (long)this.cacheSize)];
               om = om.replace(serverVersion);
               this.send(om, startFragNum);
               startFragNum = 0;
            }
         }
      } catch (IOException var13) {
         if (ClusterDebugLogger.isDebugEnabled()) {
            ClusterLogger.logMulticastSendError(var13);
         } else {
            ClusterLogger.logMulticastSendErrorMsg(var13.getLocalizedMessage());
         }
      }

   }

   public MulticastSessionId getSessionID() {
      return this.multicastSessionId;
   }

   GroupMessage createRecoverMessage() {
      return this.rl == null ? null : this.rl.createRecoverMessage();
   }

   long getCurrentSeqNum() {
      return this.oldestSeqNum + (long)this.numMessages;
   }

   boolean isAdminSender() {
      return this.adminSender;
   }

   boolean isRetryEnabled() {
      return this.retryEnabled;
   }

   synchronized void suspend() {
      this.suspended = true;
   }

   synchronized void resume() {
      this.suspended = false;
   }

   private OutgoingMessage prepare(GroupMessage message, boolean isRecover) throws IOException {
      UnsyncByteArrayOutputStream baos = new UnsyncByteArrayOutputStream();
      WLObjectOutputStream oos = ClusterMessagesManager.getOutputStream(baos);
      oos.writeObjectWL(message);
      String messageVersion = ((UpgradeUtils.PeerInfoableObjectOutput)oos).getClusterVersion();
      oos.flush();
      long thisSeqNum = this.oldestSeqNum + (long)this.numMessages;
      OutgoingMessage om = this.cache[(int)(thisSeqNum % (long)this.cacheSize)];
      if (isHeartbeatMessage(message)) {
         om.set(baos.toRawBytes(), baos.size(), thisSeqNum, isRecover, this.retryEnabled, "0,0,0,0,0", (GroupMessage)null);
      } else {
         om.set(baos.toRawBytes(), baos.size(), thisSeqNum, isRecover, this.retryEnabled, messageVersion, message);
      }

      if (isRecover) {
         this.oldestSeqNum = thisSeqNum;
         this.numMessages = 1;
      } else if (this.numMessages == this.cacheSize) {
         ++this.oldestSeqNum;
      } else {
         ++this.numMessages;
      }

      return om;
   }

   protected static boolean isHeartbeatMessage(GroupMessage message) {
      return message instanceof HeartbeatMessage;
   }

   abstract void send(OutgoingMessage var1, int var2) throws IOException;

   protected void writeHeader(OutgoingMessage om, WLObjectOutputStream oos) throws IOException {
      oos.writeInt(this.localDomainNameHash);
      oos.writeInt(this.localClusterNameHash);
      oos.writeObjectWL(LocalServerIdentity.getIdentity());
      oos.writeString(om.messageVersion);
      oos.flush();
   }

   protected byte[] serializePayload(OutgoingMessage om, int bytesThisTime) throws IOException {
      return this.serializePayload(om, 0, 0, 0, bytesThisTime);
   }

   protected byte[] serializePayload(OutgoingMessage om, int fragNum, int offset, int startFragNum, int bytesThisTime) throws IOException {
      ByteArrayOutputStream baos = new ByteArrayOutputStream(bytesThisTime);
      WLObjectOutputStream out = new WLObjectOutputStream(baos);
      out.writeObject(this.multicastSessionId);
      out.writeLong(om.seqNum);
      out.writeInt(fragNum);
      out.writeInt(om.size);
      out.writeInt(offset);
      out.writeBoolean(om.isRecover);
      out.writeBoolean(om.retryEnabled);
      out.writeBoolean(this.useHTTPForSD);
      if (fragNum >= startFragNum) {
         out.writeBytes(om.message, offset, bytesThisTime);
         out.flush();
         return baos.toByteArray();
      } else {
         return null;
      }
   }

   public static int hashCode(String s) {
      int h = 0;

      for(int i = 0; i < s.length(); ++i) {
         h = 31 * h + s.charAt(i);
      }

      return h;
   }

   static class OutgoingMessage {
      public byte[] message;
      public int size;
      public long seqNum;
      public boolean isRecover;
      public boolean retryEnabled;
      public String messageVersion;
      public GroupMessage groupMessage;

      void set(byte[] message, int size, long seqNum, boolean isRecover, boolean retryEnabled, String clusterVersion, GroupMessage groupMessage) {
         this.message = message;
         this.size = size;
         this.seqNum = seqNum;
         this.isRecover = isRecover;
         this.retryEnabled = retryEnabled;
         this.messageVersion = clusterVersion;
         this.groupMessage = groupMessage;
      }

      void clear() {
         this.message = null;
      }

      public synchronized OutgoingMessage replace(String remoteVersion) {
         WLObjectOutputStream oos = null;

         OutgoingMessage var3;
         try {
            if (remoteVersion == null || this.messageVersion == null) {
               var3 = this;
               return var3;
            }

            if (!remoteVersion.equals(this.messageVersion)) {
               PeerInfo message = PeerInfo.getPeerInfo(this.messageVersion);
               PeerInfo remote = PeerInfo.getPeerInfo(remoteVersion);
               if (ClusterDebugLogger.isDebugEnabled()) {
                  ClusterDebugLogger.debug("[UPGRADE] outgoing message needs replacement?, messageVersion:" + this.messageVersion + ", remoteVersion:" + remoteVersion);
               }

               if (message.compareTo(remote) <= 0) {
                  OutgoingMessage var23 = this;
                  return var23;
               }

               UnsyncByteArrayOutputStream baos = new UnsyncByteArrayOutputStream();
               oos = ClusterMessagesManager.getOutputStream(baos, remote);
               oos.writeObjectWL(this.groupMessage);
               this.messageVersion = ((UpgradeUtils.PeerInfoableObjectOutput)oos).getClusterVersion();
               oos.writeString(this.messageVersion);
               oos.flush();
               this.message = baos.toRawBytes();
               this.size = baos.size();
               if (ClusterDebugLogger.isDebugEnabled()) {
                  ClusterDebugLogger.debug("[UPGRADE] outgoing message is replaced and new messageVersion is " + this.messageVersion);
               }

               OutgoingMessage var6 = this;
               return var6;
            }

            var3 = this;
         } catch (IOException var19) {
            var19.printStackTrace();
            OutgoingMessage var4 = this;
            return var4;
         } finally {
            if (oos != null) {
               try {
                  oos.close();
               } catch (IOException var18) {
               }
            }

         }

         return var3;
      }

      public String toString() {
         return "message:" + Arrays.toString(this.message) + ", size:" + this.size + ", seqNum:" + this.seqNum + ", messageVersion:" + this.messageVersion;
      }
   }
}
