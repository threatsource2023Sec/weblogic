package weblogic.jms.frontend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSSessionRecoverResponse;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;

public final class FESessionRecoverRequest extends Request implements Externalizable {
   static final long serialVersionUID = 3677451811287600209L;
   private int pipelineGeneration;
   private long lastSequenceNumber;
   private boolean doRollback;
   private transient Request childRequests;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int DO_ROLLBACK_MASK = 256;
   private static final int SEQUENCE_NUMBER_MASK = 512;
   private static final int ALGORITHM_90_MASK = 1024;
   static final int ROLLBACK_START = 0;
   static final int RECOVER_START = 1;
   static final int RECOVER_FINISH = 2;
   static final int ROLLBACK_FINISH = 3;

   public FESessionRecoverRequest(JMSID sessionId, long lastSequenceNumber, boolean doRollback, int pipelineGeneration) {
      super(sessionId, 6920);
      this.lastSequenceNumber = lastSequenceNumber;
      this.doRollback = doRollback;
      this.pipelineGeneration = pipelineGeneration;
   }

   final long getLastSequenceNumber() {
      return this.lastSequenceNumber;
   }

   final void setLastSequenceNumber(long lastSequenceNumber) {
      this.lastSequenceNumber = lastSequenceNumber;
   }

   final boolean doRollback() {
      return this.doRollback;
   }

   final int getPipelineGeneration() {
      return this.pipelineGeneration;
   }

   final Request getChildRequests() {
      return this.childRequests;
   }

   final void setChildRequests(Request childRequests) {
      this.childRequests = childRequests;
   }

   public int remoteSignature() {
      return this.doRollback ? 18 : 19;
   }

   public Response createResponse() {
      return new JMSSessionRecoverResponse();
   }

   public FESessionRecoverRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      int mask = 1 | this.pipelineGeneration;
      if (this.doRollback) {
         mask |= 256;
      }

      if (this.lastSequenceNumber != 0L) {
         mask |= 512;
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
         if ((mask & 512) != 0) {
            this.lastSequenceNumber = in.readLong();
         }

         this.doRollback = (mask & 256) != 0;
         this.pipelineGeneration = mask & 15728640;
      }
   }
}
