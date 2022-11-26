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

public final class FEConsumerIncrementWindowCurrentOneWayRequest extends Request implements Externalizable {
   static final long serialVersionUID = 1485628795862457005L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int CLIENTRESPONSIBLEFORACKNOWLEDGE = 256;
   private int windowIncrement;
   private transient boolean clientResponsibleForAcknowledge;

   public FEConsumerIncrementWindowCurrentOneWayRequest(JMSID consumerId, int windowIncrement, boolean clientResponsibleForAcknowledge) {
      super(consumerId, 17418);
      this.windowIncrement = windowIncrement;
      this.clientResponsibleForAcknowledge = clientResponsibleForAcknowledge;
   }

   public final int getWindowIncrement() {
      return this.windowIncrement;
   }

   public final boolean getClientResponsibleForAcknowledge() {
      return this.clientResponsibleForAcknowledge;
   }

   public int remoteSignature() {
      return 64;
   }

   public boolean isServerOneWay() {
      return true;
   }

   public boolean isServerToServer() {
      return false;
   }

   public Response createResponse() {
      return VoidResponse.THE_ONE;
   }

   public FEConsumerIncrementWindowCurrentOneWayRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      int mask = 1;
      if (this.clientResponsibleForAcknowledge) {
         mask |= 256;
      }

      out.writeInt(mask);
      super.writeExternal(out);
      out.writeInt(this.windowIncrement);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         if ((mask & 256) != 0) {
            this.clientResponsibleForAcknowledge = true;
         } else {
            this.clientResponsibleForAcknowledge = false;
         }

         super.readExternal(in);
         this.windowIncrement = in.readInt();
      }
   }
}
