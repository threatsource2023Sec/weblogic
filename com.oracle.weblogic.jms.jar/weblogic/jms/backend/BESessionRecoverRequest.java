package weblogic.jms.backend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSSessionRecoverResponse;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.common.Sequencer;
import weblogic.jms.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;

public final class BESessionRecoverRequest extends Request implements Externalizable {
   static final long serialVersionUID = -7100249057361747189L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int ALGORITHM_90 = 1024;
   private long lastSequenceNumber;
   private int pipelineGeneration;
   private transient Sequencer sequencer;

   public BESessionRecoverRequest(JMSID sessionId, long lastSequenceNumber, Sequencer sequencer, int pipelineGeneration) {
      super(sessionId, 13840);
      this.lastSequenceNumber = lastSequenceNumber;
      this.sequencer = sequencer;
      this.pipelineGeneration = pipelineGeneration;
   }

   final long getLastSequenceNumber() {
      return this.lastSequenceNumber;
   }

   public final Sequencer getSequencer() {
      return this.sequencer;
   }

   final int getPipelineGeneration() {
      return this.pipelineGeneration;
   }

   public int remoteSignature() {
      return 17;
   }

   public boolean isServerToServer() {
      return true;
   }

   public Response createResponse() {
      return new JMSSessionRecoverResponse();
   }

   public BESessionRecoverRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int mask = 1 | this.pipelineGeneration;
      out.writeInt(mask);
      super.writeExternal(out);
      out.writeLong(this.lastSequenceNumber);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         this.lastSequenceNumber = in.readLong();
         this.pipelineGeneration = mask & 15728640;
      }
   }
}
