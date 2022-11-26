package weblogic.jms.backend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.CompletionRequest;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.jms.dispatcher.VoidResponse;
import weblogic.messaging.dispatcher.Response;

public final class BEOrderUpdateParentRequest extends Request implements Externalizable {
   static final long serialVersionUID = 4890857082204074715L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private BEOrderUpdateRequest orderUpdateRequest;
   private CompletionRequest completionRequest;

   BEOrderUpdateParentRequest(JMSID destinationId, BEOrderUpdateRequest orderUpdateRequest, CompletionRequest completionRequest) {
      super(destinationId, 18178);
      this.orderUpdateRequest = orderUpdateRequest;
      this.completionRequest = completionRequest;
   }

   CompletionRequest getCompletionRequest() {
      return this.completionRequest;
   }

   BEOrderUpdateRequest getOrderUpdate() {
      return this.orderUpdateRequest;
   }

   public int remoteSignature() {
      return 34;
   }

   public boolean isServerToServer() {
      return true;
   }

   public Response createResponse() {
      return VoidResponse.THE_ONE;
   }

   public BEOrderUpdateParentRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int mask = 1;
      out.writeInt(mask);
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
