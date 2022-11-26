package weblogic.jms.frontend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.jms.dispatcher.VoidResponse;
import weblogic.messaging.dispatcher.Response;

public final class FEConsumerCloseRequest extends Request implements Externalizable {
   static final long serialVersionUID = -1696547268688497887L;
   private long lastSequenceNumber;
   private long firstSequenceNumberNotSeen;
   private static final int EXTVERSION_PRE_1221 = 1;
   private static final int EXTVERSION_1221 = 2;
   private static final int EXTVERSION = 2;
   private static final int VERSION_MASK = 255;
   private static final int SEQUENCE_NUMBER_MASK = 256;
   private static final int FIRST_SEQUENCE_NUMBER_NOT_SEEN_MASK = 512;

   public FEConsumerCloseRequest(JMSID consumerId, long lastSequenceNumber, long firstSequenceNumberNotSeen) {
      super(consumerId, 2570);
      this.lastSequenceNumber = lastSequenceNumber;
      this.firstSequenceNumberNotSeen = firstSequenceNumberNotSeen;
   }

   public long getLastSequenceNumber() {
      return this.lastSequenceNumber;
   }

   public long getFirstSequenceNumberNotSeen() {
      return this.firstSequenceNumberNotSeen;
   }

   public int remoteSignature() {
      return 18;
   }

   public Response createResponse() {
      return VoidResponse.THE_ONE;
   }

   public FEConsumerCloseRequest() {
   }

   private byte getVersion(Object o) throws IOException {
      if (o instanceof PeerInfoable) {
         PeerInfo pi = ((PeerInfoable)o).getPeerInfo();
         return (byte)(pi.compareTo(PeerInfo.VERSION_1221) < 0 ? 1 : 2);
      } else {
         return 1;
      }
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      int peerVersion = this.getVersion(out);
      int mask = peerVersion;
      if (this.lastSequenceNumber != 0L) {
         mask = peerVersion | 256;
      }

      if (peerVersion >= 2 && this.firstSequenceNumberNotSeen != 0L) {
         mask |= 512;
      }

      out.writeInt(mask);
      super.writeExternal(out);
      if (this.lastSequenceNumber != 0L) {
         out.writeLong(this.lastSequenceNumber);
      }

      if (peerVersion >= 2 && this.firstSequenceNumberNotSeen != 0L) {
         out.writeLong(this.firstSequenceNumberNotSeen);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 2 && version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 2);
      } else {
         super.readExternal(in);
         if ((mask & 256) != 0) {
            this.lastSequenceNumber = in.readLong();
         }

         if ((mask & 512) != 0) {
            this.firstSequenceNumberNotSeen = in.readLong();
         }

      }
   }
}
