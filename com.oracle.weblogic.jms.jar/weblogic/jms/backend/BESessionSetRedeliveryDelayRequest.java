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

public final class BESessionSetRedeliveryDelayRequest extends Request implements Externalizable {
   static final long serialVersionUID = -7588339131355122643L;
   private long redeliveryDelay;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;

   public BESessionSetRedeliveryDelayRequest(JMSID sessionId, long redeliveryDelay) {
      super(sessionId, 14096);
      this.redeliveryDelay = redeliveryDelay;
   }

   public final long getRedeliveryDelay() {
      return this.redeliveryDelay;
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

   public BESessionSetRedeliveryDelayRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int mask = 1;
      out.writeInt(mask);
      super.writeExternal(out);
      out.writeLong(this.redeliveryDelay);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         this.redeliveryDelay = in.readLong();
      }
   }
}
