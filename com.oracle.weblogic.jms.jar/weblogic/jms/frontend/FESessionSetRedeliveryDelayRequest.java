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

public final class FESessionSetRedeliveryDelayRequest extends Request implements Externalizable {
   static final long serialVersionUID = 2214133860017221888L;
   private long redeliveryDelay;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;

   public FESessionSetRedeliveryDelayRequest(JMSID sessionId, long redeliveryDelay) {
      super(sessionId, 7176);
      this.redeliveryDelay = redeliveryDelay;
   }

   public final long getRedeliveryDelay() {
      return this.redeliveryDelay;
   }

   public int remoteSignature() {
      return 18;
   }

   public Response createResponse() {
      return VoidResponse.THE_ONE;
   }

   public FESessionSetRedeliveryDelayRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
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
