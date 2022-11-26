package weblogic.jms.frontend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.common.JMSBrowserGetEnumerationResponse;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;

public final class FEBrowserGetEnumerationRequest extends Request implements Externalizable {
   static final long serialVersionUID = -6730313168744398045L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   static final int START = 0;
   static final int CONTINUE = 1;

   public FEBrowserGetEnumerationRequest(JMSID browserId) {
      super(browserId, 779);
   }

   public int remoteSignature() {
      return 18;
   }

   public Response createResponse() {
      return new JMSBrowserGetEnumerationResponse();
   }

   public FEBrowserGetEnumerationRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      out.writeInt(1);
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
