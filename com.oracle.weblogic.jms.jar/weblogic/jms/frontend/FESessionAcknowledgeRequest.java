package weblogic.jms.frontend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.jms.dispatcher.VoidResponse;
import weblogic.messaging.dispatcher.Response;

public final class FESessionAcknowledgeRequest extends Request implements Externalizable {
   static final long serialVersionUID = -569536026964306235L;
   private long lastSequenceNumber;
   private int acknowledgePolicy;
   private boolean doCommit;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int ACKNOWLEDGE_POLICY_MASK = 65280;
   private static final int COMMIT_MASK = 65536;
   private static final int SEQUENCE_NUMBER_MASK = 131072;
   private static final int ACKNOWLEDGE_POLICY_SHIFT = 8;
   static final int COMMIT_START = 0;
   static final int ACK_START = 1;
   static final int ACK_FINISH = 2;
   static final int COMMIT_FINISH = 3;

   public FESessionAcknowledgeRequest(JMSID sessionId, long lastSequenceNumber, int acknowledgePolicy, boolean doCommit) {
      super(sessionId, 6152);
      this.lastSequenceNumber = lastSequenceNumber;
      this.acknowledgePolicy = acknowledgePolicy;
      this.doCommit = doCommit;
   }

   long getLastSequenceNumber() {
      return this.lastSequenceNumber;
   }

   boolean doCommit() {
      return this.doCommit;
   }

   public int remoteSignature() {
      return this.doCommit ? 18 : 19;
   }

   public Response createResponse() {
      return VoidResponse.THE_ONE;
   }

   public FESessionAcknowledgeRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      int mask = 1 | this.acknowledgePolicy << 8;
      if (this.doCommit) {
         mask |= 65536;
      }

      if (this.lastSequenceNumber != 0L) {
         mask |= 131072;
      }

      out.writeInt(mask);
      super.writeExternal(out);
      if (this.lastSequenceNumber != 0L) {
         out.writeLong(this.lastSequenceNumber);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         if ((mask & 131072) != 0) {
            this.lastSequenceNumber = in.readLong();
         }

         this.acknowledgePolicy = (mask & '\uff00') >>> 8;
         this.doCommit = (mask & 65536) != 0;
      }
   }
}
