package weblogic.cluster;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;
import weblogic.rmi.spi.HostID;

final class AnnouncementMessage implements GroupMessage, Externalizable {
   private static final long serialVersionUID = -5211845034589522488L;
   private MulticastSessionId multicastSessionId;
   ArrayList items;

   AnnouncementMessage(MulticastSessionId multicastSessionId, ArrayList items) {
      this.multicastSessionId = multicastSessionId;
      this.items = items;
   }

   public MulticastSessionId getMulticastSessionId() {
      return this.multicastSessionId;
   }

   public void execute(HostID memberID) {
      PartitionAwareSenderManager.theOne().findOrCreateAnnouncementManager(this.multicastSessionId).receiveAnnouncement(memberID, this);
   }

   public String toString() {
      return "Announcement[" + this.multicastSessionId + "] numItems:" + this.items.size();
   }

   public void writeExternal(ObjectOutput oo) throws IOException {
      ((WLObjectOutput)oo).writeObject(this.multicastSessionId);
      ((WLObjectOutput)oo).writeArrayList(this.items);
   }

   public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
      this.multicastSessionId = (MulticastSessionId)((WLObjectInput)oi).readObject();
      this.items = ((WLObjectInput)oi).readArrayList();
   }

   public AnnouncementMessage() {
   }
}
