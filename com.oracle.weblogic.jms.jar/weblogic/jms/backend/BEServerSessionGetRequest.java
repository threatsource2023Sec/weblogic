package weblogic.jms.backend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;

public final class BEServerSessionGetRequest extends Request implements Externalizable {
   static final long serialVersionUID = -5838190690108762415L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private JMSID serverSessionPoolId;

   public BEServerSessionGetRequest(JMSID backEndId, JMSID serverSessionPoolId) {
      super(backEndId, 12302);
      this.serverSessionPoolId = serverSessionPoolId;
   }

   public final JMSID getServerSessionPoolId() {
      return this.serverSessionPoolId;
   }

   public int remoteSignature() {
      return 18;
   }

   public boolean isServerToServer() {
      return true;
   }

   public Response createResponse() {
      return new BEServerSessionGetResponse();
   }

   public BEServerSessionGetRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int mask = 1;
      out.writeInt(mask);
      super.writeExternal(out);
      this.serverSessionPoolId.writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         this.serverSessionPoolId = new JMSID();
         this.serverSessionPoolId.readExternal(in);
      }
   }
}
