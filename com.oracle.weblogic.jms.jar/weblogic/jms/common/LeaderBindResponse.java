package weblogic.jms.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.dispatcher.Response;

public final class LeaderBindResponse extends Response implements Externalizable, Comparable {
   static final long serialVersionUID = -2408521791630650078L;
   private static final byte EXTVERSION1 = 1;
   private static final byte EXTVERSION2 = 2;
   private static final int VERSION_MASK = 255;
   private static final int GO_AHEAD_AND_BIND = 256;
   private static final int HAS_REASON = 512;
   private boolean goAheadAndBind;
   private JMSID leaderJMSID;
   private long leaderSequenceNumber;
   private String reasonForRejection;

   public LeaderBindResponse(boolean goAheadAndBind, JMSID leaderJMSID, long leaderSequenceNumber, String paramReasonForRejection) {
      this.goAheadAndBind = goAheadAndBind;
      this.leaderJMSID = leaderJMSID;
      this.leaderSequenceNumber = leaderSequenceNumber;
      this.reasonForRejection = paramReasonForRejection;
   }

   public LeaderBindResponse(boolean goAheadAndBind, JMSID leaderJMSID, long leaderSequenceNumber) {
      this(goAheadAndBind, leaderJMSID, leaderSequenceNumber, (String)null);
   }

   public JMSID getLeaderJMSID() {
      return this.leaderJMSID;
   }

   public long getLeaderSequenceNumber() {
      return this.leaderSequenceNumber;
   }

   public boolean doBind() {
      return this.goAheadAndBind;
   }

   public String getReasonForRejection() {
      return !this.goAheadAndBind && this.reasonForRejection == null ? "Unknown reason for rejection" : this.reasonForRejection;
   }

   public int compareTo(Object o) {
      LeaderBindResponse lbr = (LeaderBindResponse)o;
      int result = lbr.leaderJMSID.compareTo(this.leaderJMSID);
      if (result < 0) {
         return -1;
      } else if (result > 0) {
         return 1;
      } else if (lbr.leaderSequenceNumber < this.leaderSequenceNumber) {
         return -1;
      } else {
         return lbr.leaderSequenceNumber > this.leaderSequenceNumber ? 1 : 0;
      }
   }

   public boolean equals(Object o) {
      if (o == null) {
         return false;
      } else {
         LeaderBindResponse lbr = (LeaderBindResponse)o;
         if (!lbr.leaderJMSID.equals(this.leaderJMSID)) {
            return false;
         } else {
            return lbr.leaderSequenceNumber == this.leaderSequenceNumber;
         }
      }
   }

   public String toString() {
      return new String("LeaderBindResponse(" + this.goAheadAndBind + ":" + this.leaderJMSID.toString() + ":" + this.leaderSequenceNumber + ")");
   }

   public int hashCode() {
      return this.leaderJMSID.hashCode() ^ (int)(this.leaderSequenceNumber & -1L);
   }

   public LeaderBindResponse() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      byte version = 2;
      if (out instanceof PeerInfoable) {
         PeerInfo pi = ((PeerInfoable)out).getPeerInfo();
         if (pi.getMajor() < 9) {
            version = 1;
         }
      }

      int mask = version;
      if (this.goAheadAndBind) {
         mask = version | 256;
      }

      if (this.reasonForRejection != null) {
         mask |= 512;
      }

      out.writeInt(mask);
      super.writeExternal(out);
      this.leaderJMSID.writeExternal(out);
      out.writeLong(this.leaderSequenceNumber);
      if (version >= 2 && this.reasonForRejection != null) {
         out.writeUTF(this.reasonForRejection);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version >= 1 && version <= 2) {
         this.goAheadAndBind = (mask & 256) != 0;
         super.readExternal(in);
         this.leaderJMSID = new JMSID();
         this.leaderJMSID.readExternal(in);
         this.leaderSequenceNumber = in.readLong();
         if (version >= 2 && (mask & 512) != 0) {
            this.reasonForRejection = in.readUTF();
         }

      } else {
         throw JMSUtilities.versionIOException(version, 1, 2);
      }
   }
}
