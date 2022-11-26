package weblogic.jms.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.dispatcher.Request;
import weblogic.jms.dispatcher.VoidResponse;
import weblogic.messaging.dispatcher.Response;

public final class LeaderBindFailedRequest extends Request implements Externalizable {
   static final long serialVersionUID = 1241072754999225509L;
   private static final byte EXTVERSION1 = 1;
   private static final byte EXTVERSION2 = 2;
   private static final int VERSION_MASK = 255;
   private String jndiName;
   private JMSID leaderID;
   private long sequenceNumber = -1L;

   public LeaderBindFailedRequest(String jndiName, JMSID paramLeaderID, long paramSequenceNumber) {
      super((JMSID)null, 16661);
      this.jndiName = jndiName;
      this.leaderID = paramLeaderID;
      this.sequenceNumber = paramSequenceNumber;
   }

   public int remoteSignature() {
      return 64;
   }

   public boolean isServerOneWay() {
      return true;
   }

   public boolean isServerToServer() {
      return true;
   }

   public Response createResponse() {
      return VoidResponse.THE_ONE;
   }

   public final String getJNDIName() {
      return this.jndiName;
   }

   public final long getSequenceNumber() {
      return this.sequenceNumber;
   }

   public final JMSID getLeaderID() {
      return this.leaderID;
   }

   public String toString() {
      return new String("LeaderBindFailedRequest(" + this.jndiName + ")");
   }

   public LeaderBindFailedRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      byte version = 2;
      if (out instanceof PeerInfoable) {
         PeerInfo pi = ((PeerInfoable)out).getPeerInfo();
         if (pi.getMajor() < 9) {
            version = 1;
         }
      }

      out.writeInt(version);
      super.writeExternal(out);
      out.writeUTF(this.jndiName);
      if (version >= 2) {
         this.leaderID.writeExternal(out);
         out.writeLong(this.sequenceNumber);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version >= 1 && version <= 2) {
         super.readExternal(in);
         this.jndiName = in.readUTF();
         if (version >= 2) {
            this.leaderID = new JMSID();
            this.leaderID.readExternal(in);
            this.sequenceNumber = in.readLong();
         }

      } else {
         throw JMSUtilities.versionIOException(version, 1, 2);
      }
   }
}
