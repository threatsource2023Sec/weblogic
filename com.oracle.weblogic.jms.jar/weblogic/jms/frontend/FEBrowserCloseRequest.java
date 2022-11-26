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

public final class FEBrowserCloseRequest extends Request implements Externalizable {
   static final long serialVersionUID = -20419428128420032L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   static final int STATE = 0;
   static final int CONTINUE = 1;

   public FEBrowserCloseRequest(JMSID browserId) {
      super(browserId, 267);
   }

   public int remoteSignature() {
      return 18;
   }

   public Response createResponse() {
      return VoidResponse.THE_ONE;
   }

   public FEBrowserCloseRequest() {
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
