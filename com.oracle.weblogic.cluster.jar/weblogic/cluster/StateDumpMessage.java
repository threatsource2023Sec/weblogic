package weblogic.cluster;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;
import weblogic.rmi.spi.HostID;

final class StateDumpMessage implements GroupMessage, Externalizable {
   private static final long serialVersionUID = -3278918830125969257L;
   ArrayList offers;
   MulticastSessionId multicastSessionId;
   long currentSeqNum;

   StateDumpMessage(ArrayList offers, MulticastSessionId multicastSessionId, long currentSeqNum) {
      this.offers = offers;
      this.multicastSessionId = multicastSessionId;
      this.currentSeqNum = currentSeqNum;
   }

   public void execute(HostID memberID) {
      PartitionAwareSenderManager.theOne().findOrCreateAnnouncementManager(this.multicastSessionId).receiveStateDump(memberID, this);
   }

   public String toString() {
      return "StateDump numOffers:" + this.offers.size() + " seqNum " + this.currentSeqNum + " for MulticastSessionId: " + this.multicastSessionId;
   }

   public void writeExternal(ObjectOutput oo) throws IOException {
      synchronized(this.offers) {
         ((WLObjectOutput)oo).writeArrayList(this.offers);
      }

      oo.writeObject(this.multicastSessionId);
      oo.writeLong(this.currentSeqNum);
   }

   public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
      this.offers = ((WLObjectInput)oi).readArrayList();
      this.multicastSessionId = (MulticastSessionId)oi.readObject();
      this.currentSeqNum = oi.readLong();
   }

   public StateDumpMessage() {
   }
}
