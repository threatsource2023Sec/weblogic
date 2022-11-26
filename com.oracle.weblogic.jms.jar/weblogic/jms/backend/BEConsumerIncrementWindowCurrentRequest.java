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

public final class BEConsumerIncrementWindowCurrentRequest extends Request implements Externalizable {
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int CLIENTRESPONSILBEFORACKNOWLEDGE = 256;
   static final long serialVersionUID = 7671234897567968715L;
   private int windowIncrement;
   private transient boolean clientResponsibleForAcknowledge;

   public BEConsumerIncrementWindowCurrentRequest(JMSID consumerId, int windowIncrement, boolean clientResponsibleForAcknowledge) {
      super(consumerId, 10513);
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
      return true;
   }

   public Response createResponse() {
      return VoidResponse.THE_ONE;
   }

   public BEConsumerIncrementWindowCurrentRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
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
