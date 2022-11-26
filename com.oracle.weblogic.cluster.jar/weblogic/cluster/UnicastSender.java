package weblogic.cluster;

import java.io.IOException;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.WLObjectOutputStream;
import weblogic.utils.io.UnsyncByteArrayOutputStream;

public class UnicastSender extends ClusterMessageSender {
   UnicastSender(MulticastSessionId multicastSessionId, FragmentSocket sock, RecoverListener rl, int size, boolean useHTTPForSD) {
      super(multicastSessionId, sock, rl, size, useHTTPForSD);
   }

   UnicastSender(MulticastSessionId multicastSessionId, FragmentSocket sock, RecoverListener rl, int size, boolean useHTTPForSD, boolean adminSender) {
      super(multicastSessionId, sock, rl, size, useHTTPForSD, adminSender);
   }

   protected void send(ClusterMessageSender.OutgoingMessage om, int startFragNum) throws IOException {
      this.send(om);
      this.sendUnsignedMessageIfInRollingWindowMode(om);
   }

   private void sendUnsignedMessageIfInRollingWindowMode(ClusterMessageSender.OutgoingMessage om) throws IOException {
      if (UpgradeUtils.getInstance().isInRollingWindowMode()) {
         if (this.sendUnsignedHeartbeatMessage(om)) {
            return;
         }

         om.messageVersion = UpgradeUtils.stringfyPeerInfo(PeerInfo.VERSION_122130);
         this.send(om);
      }

   }

   private boolean sendUnsignedHeartbeatMessage(ClusterMessageSender.OutgoingMessage om) throws IOException {
      if (om.groupMessage != null && !isHeartbeatMessage(om.groupMessage)) {
         return false;
      } else {
         om.messageVersion = "0,0,0";
         this.send(om);
         return true;
      }
   }

   protected void send(ClusterMessageSender.OutgoingMessage om) throws IOException {
      UnsyncByteArrayOutputStream baos = new UnsyncByteArrayOutputStream(om.size);
      WLObjectOutputStream oos = ClusterMessagesManager.getOutputStream(baos);
      this.writeHeader(om, oos);
      int bytesThisTime = om.size;
      byte[] plain = this.serializePayload(om, bytesThisTime);
      if (plain != null) {
         if (UpgradeUtils.getInstance().shouldSign(om.messageVersion)) {
            oos.writeObject(EncryptionHelper.sign(plain));
         }

         oos.writeObject(plain);
         oos.flush();
         if (ClusterFragmentsDebugLogger.isDebugEnabled()) {
            ClusterFragmentsDebugLogger.debug("Unicast sending message with senderID:" + this.multicastSessionId + " seqNum:" + om.seqNum + " containing " + bytesThisTime + " bytes out of " + om.size);
         }

         this.sock.send(baos.toRawBytes(), baos.size());
      }

   }

   public String toString() {
      return "UnicastSender-" + this.multicastSessionId;
   }
}
