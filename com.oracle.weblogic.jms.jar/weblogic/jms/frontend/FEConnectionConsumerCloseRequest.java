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

public final class FEConnectionConsumerCloseRequest extends Request implements Externalizable {
   static final long serialVersionUID = -7095833683630182045L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private JMSID connectionConsumerId;

   public FEConnectionConsumerCloseRequest(JMSID connectionId, JMSID connectionConsumerId) {
      super(connectionId, 1287);
      this.connectionConsumerId = connectionConsumerId;
   }

   public final JMSID getConnectionConsumerId() {
      return this.connectionConsumerId;
   }

   public int remoteSignature() {
      return 18;
   }

   public Response createResponse() {
      return VoidResponse.THE_ONE;
   }

   public FEConnectionConsumerCloseRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      int mask = 1;
      out.writeInt(mask);
      super.writeExternal(out);
      this.connectionConsumerId.writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         this.connectionConsumerId = new JMSID();
         this.connectionConsumerId.readExternal(in);
      }
   }
}
