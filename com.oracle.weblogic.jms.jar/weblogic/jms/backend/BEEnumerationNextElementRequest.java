package weblogic.jms.backend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.common.JMSEnumerationNextElementResponse;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;

public final class BEEnumerationNextElementRequest extends Request implements Externalizable {
   static final long serialVersionUID = -592737172184483876L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   static final int START = 0;
   static final int PAGEIN = 1;

   public BEEnumerationNextElementRequest(JMSID enumerationId) {
      super(enumerationId, 11795);
   }

   public int remoteSignature() {
      return 18;
   }

   public boolean isServerToServer() {
      return true;
   }

   public Response createResponse() {
      return new JMSEnumerationNextElementResponse();
   }

   public BEEnumerationNextElementRequest() {
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
