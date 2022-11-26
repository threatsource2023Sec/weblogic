package weblogic.jms.backend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.jms.dispatcher.VoidResponse;
import weblogic.messaging.dispatcher.Response;

public final class BEConnectionStopRequest extends Request implements Externalizable {
   static final long serialVersionUID = -908887813148387129L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int STOP_FOR_SUSPEND = 256;
   private long startStopSequenceNumber;
   private boolean stopForSuspend;

   public BEConnectionStopRequest(JMSID connectionId, long startStopSequenceNumber, boolean stopForSuspend) {
      super(connectionId, 9743);
      this.startStopSequenceNumber = startStopSequenceNumber;
      this.stopForSuspend = stopForSuspend;
   }

   public final long getStartStopSequenceNumber() {
      return this.startStopSequenceNumber;
   }

   public final boolean isStopForSuspend() {
      return this.stopForSuspend;
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

   public BEConnectionStopRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int mask = 1;
      if (this.stopForSuspend) {
         mask |= 256;
      }

      out.writeInt(mask);
      super.writeExternal(out);
      out.writeLong(this.startStopSequenceNumber);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         this.startStopSequenceNumber = in.readLong();
         if ((mask & 256) != 0) {
            this.stopForSuspend = true;
         }

      }
   }
}
