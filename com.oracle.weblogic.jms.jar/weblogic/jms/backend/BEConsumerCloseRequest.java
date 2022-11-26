package weblogic.jms.backend;

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

public final class BEConsumerCloseRequest extends Request implements Externalizable {
   static final long serialVersionUID = -785311104852028751L;
   private static final int EXTVERSION_PRE_1221 = 1;
   private static final int EXTVERSION_1221 = 2;
   private static final int EXTVERSION = 2;
   private static final int VERSION_MASK = 255;
   private static final int IS_LAST_SEQUENCE_NUMBER_FIRST_NOT_SEEN = 256;
   private long lastSequenceNumber;
   private boolean isLastSequenceNumberFirstNotSeen;
   private long firstSequenceNumberNotSeen;

   public BEConsumerCloseRequest(JMSID consumerId, long lastSequenceNumber, long firstSequenceNumberNotSeen) {
      super(consumerId, 10001);
      this.lastSequenceNumber = lastSequenceNumber;
      this.firstSequenceNumberNotSeen = firstSequenceNumberNotSeen;
      this.isLastSequenceNumberFirstNotSeen = firstSequenceNumberNotSeen != 0L;
   }

   public final long getLastSequenceNumber() {
      return this.firstSequenceNumberNotSeen != 0L ? this.firstSequenceNumberNotSeen : this.lastSequenceNumber;
   }

   public final boolean isLastSequenceNumberFirstNotSeen() {
      return this.isLastSequenceNumberFirstNotSeen;
   }

   public int remoteSignature() {
      return 18;
   }

   public boolean isServerToServer() {
      return true;
   }

   public Response createResponse() {
      return VoidResponse.THE_ONE;
   }

   public BEConsumerCloseRequest() {
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
      long lastseq = this.lastSequenceNumber;
      if (peerVersion >= 2 && this.firstSequenceNumberNotSeen != 0L) {
         mask = peerVersion | 256;
         lastseq = this.firstSequenceNumberNotSeen;
      }

      out.writeInt(mask);
      super.writeExternal(out);
      out.writeLong(lastseq);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 2 && version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 2);
      } else {
         super.readExternal(in);
         this.lastSequenceNumber = in.readLong();
         this.isLastSequenceNumberFirstNotSeen = (mask & 256) != 0;
      }
   }
}
