package weblogic.jms.frontend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Response;

public final class FETemporaryDestinationCreateResponse extends Response implements Externalizable {
   static final long serialVersionUID = 8807310522019572947L;
   private static final byte EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private DestinationImpl destination;

   public FETemporaryDestinationCreateResponse(DestinationImpl destination) {
      this.destination = destination;
   }

   public DestinationImpl getDestination() {
      return this.destination;
   }

   public FETemporaryDestinationCreateResponse() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      out.writeByte(1);
      super.writeExternal(out);
      this.destination.writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      byte version = in.readByte();
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         this.destination = new DestinationImpl();
         this.destination.readExternal(in);
      }
   }
}
