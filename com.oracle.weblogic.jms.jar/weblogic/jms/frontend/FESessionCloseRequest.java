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

public final class FESessionCloseRequest extends Request implements Externalizable {
   static final long serialVersionUID = 4150471430840861064L;
   private long lastSequenceNumber;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int SEQUENCE_NUMBER_MASK = 256;

   public FESessionCloseRequest(JMSID sessionId, long lastSequenceNumber) {
      super(sessionId, 6408);
      this.lastSequenceNumber = lastSequenceNumber;
   }

   final long getLastSequenceNumber() {
      return this.lastSequenceNumber;
   }

   public int remoteSignature() {
      return 18;
   }

   public Response createResponse() {
      return VoidResponse.THE_ONE;
   }

   public FESessionCloseRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      int mask = 1;
      if (this.lastSequenceNumber != 0L) {
         mask |= 256;
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
         if ((mask & 256) != 0) {
            this.lastSequenceNumber = in.readLong();
         }

      }
   }
}
