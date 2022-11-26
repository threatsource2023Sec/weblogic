package weblogic.jms.frontend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.common.Destination;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;

public final class FEProducerCreateRequest extends Request implements Externalizable {
   static final long serialVersionUID = -1183676357733894095L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int DESTINATION_MASK = 1792;
   private static final int DESTINATION_SHIFT = 8;
   private DestinationImpl destination;

   public FEProducerCreateRequest(JMSID sessionId, DestinationImpl destination) {
      super(sessionId, 4872);
      this.destination = destination;
   }

   public final DestinationImpl getDestination() {
      return this.destination;
   }

   public int remoteSignature() {
      return 18;
   }

   public Response createResponse() {
      return new FEProducerCreateResponse();
   }

   public FEProducerCreateRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      int flag = 1;
      if (this.destination != null) {
         flag |= Destination.getDestinationType(this.destination, 8);
      }

      out.writeInt(flag);
      super.writeExternal(out);
      if (this.destination != null) {
         this.destination.writeExternal(out);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int flag = in.readInt();
      int version = flag & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         if ((flag & 1792) != 0) {
            byte dtype = (byte)((flag & 1792) >>> 8);
            this.destination = Destination.createDestination(dtype, in);
         }

      }
   }
}
