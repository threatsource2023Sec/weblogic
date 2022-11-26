package weblogic.cluster;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;
import weblogic.rmi.spi.HostID;
import weblogic.utils.StackTraceUtils;

public final class NAKHBI implements Externalizable {
   private static final long serialVersionUID = 1807772120988360177L;
   HostID memID;
   MulticastSessionId multicastSessionId;
   long seqNum;
   int fragNum;
   String serverVersion;

   NAKHBI(HostID memID, MulticastSessionId multicastSessionId, long seqNum, int fragNum) {
      this.memID = memID;
      this.multicastSessionId = multicastSessionId;
      this.seqNum = seqNum;
      this.fragNum = fragNum;
   }

   public String toString() {
      return "NAKHBI server:" + this.memID + " multicastSessionId:" + this.multicastSessionId + " seqNum:" + this.seqNum + " fragNum:" + this.fragNum;
   }

   public boolean equals(Object item) {
      if (item == null) {
         return false;
      } else {
         try {
            NAKHBI other = (NAKHBI)item;
            return this.memID.equals(other.memID) && this.multicastSessionId.equals(other.multicastSessionId);
         } catch (ClassCastException var3) {
            return false;
         }
      }
   }

   public int hashCode() {
      return this.multicastSessionId.hashCode();
   }

   public void writeExternal(ObjectOutput oo) throws IOException {
      WLObjectOutput out = (WLObjectOutput)oo;
      out.writeObjectWL(this.memID);
      out.writeObject(this.multicastSessionId);
      out.writeLong(this.seqNum);
      out.writeInt(this.fragNum);
      out.writeString(UpgradeUtils.getInstance().getLocalServerVersion());
   }

   public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
      WLObjectInput in = (WLObjectInput)oi;
      this.memID = (HostID)in.readObjectWL();
      this.multicastSessionId = (MulticastSessionId)in.readObject();
      this.seqNum = in.readLong();
      this.fragNum = in.readInt();

      try {
         this.serverVersion = in.readString();
      } catch (IOException var4) {
         if (ClusterDebugLogger.isDebugEnabled()) {
            ClusterDebugLogger.debug("[UPGRADE] serverVerion not available in NAKBHI!" + StackTraceUtils.throwable2StackTrace(var4));
         }
      }

   }

   public NAKHBI() {
   }
}
