package weblogic.cluster;

import java.io.IOException;
import weblogic.rmi.spi.HostID;

final class AttributeManager implements RecoverListener, MulticastSessionIDConstants {
   private static AttributeManager theAttributeManager = null;
   private final MemberAttributes localAttributes;
   private MulticastSession attributeSender;

   static AttributeManager theOne() {
      return theAttributeManager;
   }

   static void initialize(MemberAttributes localAttributes) throws IOException {
      theAttributeManager = new AttributeManager(localAttributes);
   }

   private AttributeManager(MemberAttributes localAttributes) {
      this.attributeSender = ClusterService.getClusterServiceInternal().createMulticastSession(ATTRIBUTE_MANAGER_ID, this, 1, false);
      this.localAttributes = localAttributes;
   }

   void sendAttributes() throws IOException {
      if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
         ClusterAnnouncementsDebugLogger.debug("Sending local attributes " + this.localAttributes);
      }

      this.attributeSender.send(new AttributesMessage(this.localAttributes));
   }

   void receiveAttributes(HostID memberID, AttributesMessage message) {
      if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
         ClusterAnnouncementsDebugLogger.debug("Received " + message + " from " + memberID);
      }

      RemoteMemberInfo remoteMember = MemberManager.theOne().findOrCreate(memberID);

      try {
         remoteMember.processAttributes(message.attributes);
      } finally {
         MemberManager.theOne().done(remoteMember);
      }

   }

   public GroupMessage createRecoverMessage() {
      return new AttributesMessage(this.localAttributes);
   }

   MemberAttributes getLocalAttributes() {
      return this.localAttributes;
   }
}
