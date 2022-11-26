package weblogic.jms.backend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.common.JMSBrowserGetEnumerationResponse;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;

public final class BEBrowserGetEnumerationRequest extends Request implements Externalizable {
   static final long serialVersionUID = -5460900869537242019L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;

   public BEBrowserGetEnumerationRequest(JMSID browserId) {
      super(browserId, 8722);
   }

   public int remoteSignature() {
      return 18;
   }

   public boolean isServerToServer() {
      return true;
   }

   public Response createResponse() {
      return new JMSBrowserGetEnumerationResponse();
   }

   public BEBrowserGetEnumerationRequest() {
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
