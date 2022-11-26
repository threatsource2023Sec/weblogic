package weblogic.jms.backend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.common.JMSBrowserCreateResponse;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;

public final class BEBrowserCreateRequest extends Request implements Externalizable {
   static final long serialVersionUID = 7172028677958484592L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int SELECTOR_MASK = 256;
   private JMSID destinationId;
   private String selector;

   public BEBrowserCreateRequest(JMSID sessionId, JMSID destinationId, String selector) {
      super(sessionId, 8464);
      this.destinationId = destinationId;
      this.selector = selector;
   }

   public final JMSID getDestinationId() {
      return this.destinationId;
   }

   public final String getSelector() {
      return this.selector;
   }

   public int remoteSignature() {
      return 18;
   }

   public boolean isServerToServer() {
      return true;
   }

   public Response createResponse() {
      return new JMSBrowserCreateResponse();
   }

   public BEBrowserCreateRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int mask = 1;
      if (this.selector != null) {
         mask |= 256;
      }

      out.writeInt(mask);
      super.writeExternal(out);
      this.destinationId.writeExternal(out);
      if (this.selector != null) {
         out.writeUTF(this.selector);
      }

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
         if ((mask & 256) != 0) {
            this.selector = in.readUTF();
         }

      }
   }
}
