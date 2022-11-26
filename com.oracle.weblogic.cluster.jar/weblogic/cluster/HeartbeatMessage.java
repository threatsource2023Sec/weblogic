package weblogic.cluster;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;
import weblogic.rmi.spi.HostID;

final class HeartbeatMessage implements GroupMessage, Externalizable {
   private static final boolean DEBUG = ClusterDebugLogger.isDebugEnabled();
   private static final long serialVersionUID = -7010984889884629879L;
   private static final long MAX_TIME_DIFF = 60000L;
   private long diff;
   ArrayList items;

   HeartbeatMessage(ArrayList items) {
      this.items = items;
   }

   public void execute(HostID memberID) {
      ClusterMessagesManager.theOne().receiveHeartbeat(memberID, this);
      if (this.diff > 60000L) {
         ClusterLogger.logMachineTimesOutOfSync(memberID.objectToString(), this.diff / 1000L);
      }

   }

   public String toString() {
      return "Heartbeat with " + this.items.size() + " items: " + this.items;
   }

   public void writeExternal(ObjectOutput oo) throws IOException {
      ((WLObjectOutput)oo).writeArrayList(this.items);
      if (DEBUG) {
         oo.writeLong(System.currentTimeMillis());
      }

   }

   public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
      this.items = ((WLObjectInput)oi).readArrayList();
      if (DEBUG) {
         long remoteServerTime = oi.readLong();
         long currentTime = System.currentTimeMillis();
         this.diff = Math.abs(remoteServerTime - currentTime);
      }

   }

   public HeartbeatMessage() {
   }
}
