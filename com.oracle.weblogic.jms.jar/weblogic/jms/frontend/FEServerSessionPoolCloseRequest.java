package weblogic.jms.frontend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSServerId;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.jms.dispatcher.VoidResponse;
import weblogic.messaging.dispatcher.Response;

public final class FEServerSessionPoolCloseRequest extends Request implements Externalizable {
   static final long serialVersionUID = 4982031104004704411L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private JMSServerId backEndId;
   private JMSID serverSessionPoolId;

   public FEServerSessionPoolCloseRequest(JMSServerId backEndId, JMSID serverSessionPoolId) {
      super((JMSID)null, 5633);
      this.backEndId = backEndId;
      this.serverSessionPoolId = serverSessionPoolId;
   }

   public final JMSServerId getBackEndId() {
      return this.backEndId;
   }

   public final JMSID getServerSessionPoolId() {
      return this.serverSessionPoolId;
   }

   public int remoteSignature() {
      return 18;
   }

   public Response createResponse() {
      return VoidResponse.THE_ONE;
   }

   public FEServerSessionPoolCloseRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int mask = 1;
      out.writeInt(mask);
      super.writeExternal(out);
      this.backEndId.writeExternal(out);
      this.serverSessionPoolId.writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         this.backEndId = new JMSServerId();
         this.backEndId.readExternal(in);
         this.serverSessionPoolId = new JMSID();
         this.serverSessionPoolId.readExternal(in);
      }
   }
}
