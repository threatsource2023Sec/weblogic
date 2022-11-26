package weblogic.jms.backend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import weblogic.jms.client.JMSConnectionFactory;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSServerSessionPoolCreateResponse;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;

public final class BEServerSessionPoolCreateRequest extends Request implements Externalizable {
   static final long serialVersionUID = 4148932257093406234L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int TRANSACTED_MASK = 256;
   private JMSConnectionFactory connectionFactory;
   private int sessionsMaximum;
   private int acknowledgeMode;
   private boolean transacted;
   private String messageListenerClass;
   private Serializable clientData;

   public BEServerSessionPoolCreateRequest(JMSID backEndId, JMSConnectionFactory connectionFactory, int sessionsMaximum, int acknowledgeMode, boolean transacted, String messageListenerClass, Serializable clientData) {
      super(backEndId, 12814);
      this.connectionFactory = connectionFactory;
      this.sessionsMaximum = sessionsMaximum;
      this.acknowledgeMode = acknowledgeMode;
      this.transacted = transacted;
      this.messageListenerClass = messageListenerClass;
      this.clientData = clientData;
   }

   public final JMSConnectionFactory getConnectionFactory() {
      return this.connectionFactory;
   }

   public final int getSessionsMaximum() {
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

   public boolean isServerToServer() {
      return true;
   }

   public Response createResponse() {
      return new JMSServerSessionPoolCreateResponse();
   }

   public BEServerSessionPoolCreateRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int mask = 1;
      if (this.transacted) {
         mask |= 256;
      }

      out.writeInt(mask);
      super.writeExternal(out);
      this.connectionFactory.writeExternal(out);
      out.writeInt(this.sessionsMaximum);
      out.writeInt(this.acknowledgeMode);
      out.writeUTF(this.messageListenerClass);
      out.writeObject(this.clientData);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         this.connectionFactory = new JMSConnectionFactory();
         this.connectionFactory.readExternal(in);
         this.sessionsMaximum = in.readInt();
         this.messageListenerClass = in.readUTF();
         this.clientData = (Serializable)in.readObject();
         this.transacted = (mask & 256) != 0;
      }
   }
}
