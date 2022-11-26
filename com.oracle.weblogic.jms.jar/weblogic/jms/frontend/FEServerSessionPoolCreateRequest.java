package weblogic.jms.frontend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSServerId;
import weblogic.jms.common.JMSServerSessionPoolCreateResponse;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;

public final class FEServerSessionPoolCreateRequest extends Request implements Externalizable {
   static final long serialVersionUID = 8825493097866359520L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int TRANSACTED_MASK = 256;
   private static final int CLIENT_DATA_MASK = 512;
   private JMSServerId backEndId;
   private int sessionsMaximum;
   private int acknowledgeMode;
   private boolean transacted;
   private String messageListenerClass;
   private Serializable clientData;

   public FEServerSessionPoolCreateRequest(JMSID connectionId, JMSServerId backEndId, int sessionsMaximum, int acknowledgeMode, boolean transacted, String messageListenerClass, Serializable clientData) {
      super(connectionId, 5895);
      this.backEndId = backEndId;
      this.sessionsMaximum = sessionsMaximum;
      this.acknowledgeMode = acknowledgeMode;
      this.transacted = transacted;
      this.messageListenerClass = messageListenerClass;
      this.clientData = clientData;
   }

   public final JMSServerId getBackEndId() {
      return this.backEndId;
   }

   public final int getSessionMaximum() {
      return this.sessionsMaximum;
   }

   public final int getAcknowledgeMode() {
      return this.acknowledgeMode;
   }

   public final boolean isTransacted() {
      return this.transacted;
   }

   public final String getMessageListenerClass() {
      return this.messageListenerClass;
   }

   public final Serializable getClientData() {
      return this.clientData;
   }

   public int remoteSignature() {
      return 18;
   }

   public Response createResponse() {
      return new JMSServerSessionPoolCreateResponse();
   }

   public FEServerSessionPoolCreateRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int mask = 1;
      if (this.transacted) {
         mask |= 256;
      }

      if (this.clientData != null) {
         mask |= 512;
      }

      out.writeInt(mask);
      super.writeExternal(out);
      this.backEndId.writeExternal(out);
      out.writeInt(this.sessionsMaximum);
      out.writeInt(this.acknowledgeMode);
      out.writeUTF(this.messageListenerClass);
      if (this.clientData != null) {
         out.writeObject(this.clientData);
      }

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
         this.sessionsMaximum = in.readInt();
         this.acknowledgeMode = in.readInt();
         this.messageListenerClass = in.readUTF();
         if ((mask & 512) != 0) {
            this.clientData = (Serializable)in.readObject();
         }

         this.transacted = (mask & 256) != 0;
      }
   }
}
