package weblogic.jms.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.dispatcher.Response;

public final class JMSDestinationCreateResponse extends Response implements Externalizable {
   static final long serialVersionUID = -5700193800061807432L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int DESTINATION_MASK = 1792;
   private static final int DESTINATION_SHIFT = 8;
   private DestinationImpl destination;

   public JMSDestinationCreateResponse(DestinationImpl destination) {
      this.destination = destination;
   }

   public final DestinationImpl getDestination() {
      return this.destination;
   }

   public JMSDestinationCreateResponse() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      int flag = 1;
      flag |= Destination.getDestinationType(this.destination, 8);
      out.writeInt(flag);
      super.writeExternal(out);
      this.destination.writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         if ((mask & 1792) != 0) {
            this.destination = Destination.createDestination((byte)((mask & 1792) >>> 8), in);
         } else if (((PeerInfoable)in).getPeerInfo().compareTo(PeerInfo.VERSION_70) < 0) {
            this.destination = new DestinationImpl();
            this.destination.readExternal(in);
         }

      }
   }
}
