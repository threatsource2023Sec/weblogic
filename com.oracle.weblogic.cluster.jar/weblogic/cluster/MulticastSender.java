package weblogic.cluster;

import java.io.IOException;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.WLObjectOutputStream;
import weblogic.utils.io.UnsyncByteArrayOutputStream;

public final class MulticastSender extends ClusterMessageSender {
   private static final int PAYLOAD_FUDGE_FACTOR = 27;
   private static final short DATA_HEADERS_SIZE = 29;
   private static final int ENCRYPTION_OVERHEAD_SIZE = 34;

   MulticastSender(MulticastSessionId multicastSessionId, FragmentSocket sock, RecoverListener rl, int size, boolean useHTTPForSD) {
      this(multicastSessionId, sock, rl, size, useHTTPForSD, false);
   }

   MulticastSender(MulticastSessionId multicastSessionId, FragmentSocket sock, RecoverListener rl, int size, boolean useHTTPForSD, boolean adminSender) {
      super(multicastSessionId, sock, rl, size, useHTTPForSD, adminSender);
   }

   protected void send(ClusterMessageSender.OutgoingMessage om, int startFragNum) throws IOException {
      this.fragmentAndSend(om, startFragNum);
      this.fragmentAndSendUnsignedMessageIfInRollingWindowMode(om, startFragNum);
   }

   private void fragmentAndSendUnsignedMessageIfInRollingWindowMode(ClusterMessageSender.OutgoingMessage om, int startFragNum) throws IOException {
      if (UpgradeUtils.getInstance().isInRollingWindowMode()) {
         if (this.sendUnsignedHeartbeatMessage(om, startFragNum)) {
            return;
         }

         om.messageVersion = UpgradeUtils.stringfyPeerInfo(PeerInfo.VERSION_122130);
         this.fragmentAndSend(om, startFragNum);
      }

   }

   private boolean sendUnsignedHeartbeatMessage(ClusterMessageSender.OutgoingMessage om, int startFragNum) throws IOException {
      if (om.groupMessage != null && !isHeartbeatMessage(om.groupMessage)) {
         return false;
      } else {
         om.messageVersion = "0,0,0";
         this.fragmentAndSend(om, startFragNum);
         return true;
      }
   }

   private void fragmentAndSend(ClusterMessageSender.OutgoingMessage om, int startFragNum) throws IOException {
      int offset = 0;

      for(int fragNum = 0; offset < om.size; ++fragNum) {
         UnsyncByteArrayOutputStream baos = new UnsyncByteArrayOutputStream(ClusterMessagesManager.MAX_FRAGMENT_SIZE);
         WLObjectOutputStream oos = ClusterMessagesManager.getOutputStream(baos);
         this.writeHeader(om, oos);
         int headerOverheadBytes = baos.size() + 27;
         if (ClusterService.getClusterServiceInternal().multicastDataEncryptionEnabled()) {
            headerOverheadBytes += 34;
         }

         headerOverheadBytes += 56;
         int bytesThisTime = Math.min(ClusterMessagesManager.MAX_FRAGMENT_SIZE - headerOverheadBytes, om.size - offset);
         byte[] plain = this.serializePayload(om, fragNum, offset, startFragNum, bytesThisTime);
         byte[] data = plain;
         if (ClusterService.getClusterServiceInternal().multicastDataEncryptionEnabled()) {
            data = EncryptionHelper.encrypt(plain);
         }

         if (data != null) {
            if (this.shouldSign(om.messageVersion)) {
               oos.writeObject(EncryptionHelper.sign(data));
            }

            oos.writeObject(data);
            oos.flush();
            if (ClusterFragmentsDebugLogger.isDebugEnabled()) {
               ClusterFragmentsDebugLogger.debug("Sending fragment senderID:" + this.multicastSessionId + " seqNum:" + om.seqNum + "fragNum:" + fragNum + " containing " + bytesThisTime + " bytes out of " + om.size);
            }

            this.sock.send(baos.toRawBytes(), baos.size());
         }

         offset += bytesThisTime;
      }

   }

   protected boolean shouldSign(String msgVersion) {
      return UpgradeUtils.getInstance().shouldSign(msgVersion) ? true : ClusterService.getClusterServiceInternal().multicastDataEncryptionEnabled();
   }

   public String toString() {
      return "MulticastSender-" + this.multicastSessionId;
   }
}
