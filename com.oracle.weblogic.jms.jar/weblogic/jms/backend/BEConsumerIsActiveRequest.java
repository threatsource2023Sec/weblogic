package weblogic.jms.backend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;

public final class BEConsumerIsActiveRequest extends Request implements Externalizable {
   static final long serialVersionUID = 4530112598474302164L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;

   public BEConsumerIsActiveRequest(JMSID consumerId) {
      super(consumerId, 10769);
   }

   public int remoteSignature() {
      return 32;
   }

   public boolean isServerToServer() {
      return true;
   }

   public Response createResponse() {
      return new BEConsumerIsActiveResponse();
   }

   public BEConsumerIsActiveRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeInt(1);
      super.writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
      }
   }
}
