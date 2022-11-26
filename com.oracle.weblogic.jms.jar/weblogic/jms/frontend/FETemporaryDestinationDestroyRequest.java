package weblogic.jms.frontend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jms.dispatcher.Request;
import weblogic.jms.dispatcher.VoidResponse;
import weblogic.messaging.dispatcher.Response;

public final class FETemporaryDestinationDestroyRequest extends Request implements Externalizable {
   static final long serialVersionUID = 1839581631272219969L;
   private JMSID destinationId;
   private transient JMSDispatcher dispatcher;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   static final int START = 0;
   static final int CONTINUE = 1;

   public FETemporaryDestinationDestroyRequest(JMSID connectionId, JMSID destinationId) {
      super(connectionId, 7431);
      this.destinationId = destinationId;
   }

   public final JMSID getDestinationId() {
      return this.destinationId;
   }

   public final JMSDispatcher getDispatcher() {
      return this.dispatcher;
   }

   public final void setDispatcher(JMSDispatcher dispatcher) {
      this.dispatcher = dispatcher;
   }

   public int remoteSignature() {
      return 18;
   }

   public Response createResponse() {
      return VoidResponse.THE_ONE;
   }

   public FETemporaryDestinationDestroyRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      out.writeInt(1);
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
