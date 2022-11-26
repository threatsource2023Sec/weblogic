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

public final class BETemporaryDestinationDestroyRequest extends Request implements Externalizable {
   static final long serialVersionUID = -3727310929055417748L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private JMSID destinationId;

   public BETemporaryDestinationDestroyRequest(JMSID backEndId, JMSID destinationId) {
      super(backEndId, 15118);
      this.destinationId = destinationId;
   }

   public final JMSID getDestinationId() {
      return this.destinationId;
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

   public BETemporaryDestinationDestroyRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int mask = 1;
      out.writeInt(mask);
      super.writeExternal(out);
      this.destinationId.writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         this.destinationId = new JMSID();
         this.destinationId.readExternal(in);
      }
   }
}
