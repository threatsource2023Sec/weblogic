package weblogic.jms.backend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.jms.ServerSessionPool;
import weblogic.jms.client.JMSServerSessionPool;
import weblogic.jms.common.JMSConnectionConsumerCreateResponse;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSServerId;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.jms.frontend.FEConnection;
import weblogic.messaging.dispatcher.Response;

public final class BEConnectionConsumerCreateRequest extends Request implements Externalizable {
   static final long serialVersionUID = 7853725251347530328L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int ISDURABLE_MASK = 256;
   private static final int SELECTOR_MASK = 512;
   private static final int STOPPED_MASK = 1024;
   private JMSServerId backEndId;
   private JMSID connectionId;
   private FEConnection connection;
   private JMSID destinationId;
   private ServerSessionPool serverSessionPool;
   private boolean isDurable;
   private String messageSelector;
   private int messagesMaximum;
   private boolean stopped;
   private long startStopSequenceNumber;

   public BEConnectionConsumerCreateRequest(JMSServerId backEndId, JMSID connectionId, ServerSessionPool serverSessionPool, FEConnection connection, JMSID destinationId, boolean isDurable, String messageSelector, int messagesMaximum, boolean stopped, long startStopSequenceNumber) {
      super((JMSID)null, 9218);
      this.backEndId = backEndId;
      this.connectionId = connectionId;
      this.serverSessionPool = serverSessionPool;
      this.connection = connection;
      this.destinationId = destinationId;
      this.isDurable = isDurable;
      this.messageSelector = messageSelector;
      this.messagesMaximum = messagesMaximum;
      this.stopped = stopped;
      this.startStopSequenceNumber = startStopSequenceNumber;
   }

   public final JMSServerId getBackEndId() {
      return this.backEndId;
   }

   public final JMSID getConnectionId() {
      return this.connectionId;
   }

   public final ServerSessionPool getServerSessionPool() {
      return this.serverSessionPool;
   }

   public final FEConnection getConnection() {
      return this.connection;
   }

   public final JMSID getDestinationId() {
      return this.destinationId;
   }

   public final boolean isDurable() {
      return this.isDurable;
   }

   public final String getMessageSelector() {
      return this.messageSelector;
   }

   public final int getMessagesMaximum() {
      return this.messagesMaximum;
   }

   public final boolean isStopped() {
      return this.stopped;
   }

   public final long getStartStopSequenceNumber() {
      return this.startStopSequenceNumber;
   }

   public int remoteSignature() {
      return 18;
   }

   public boolean isServerToServer() {
      return true;
   }

   public Response createResponse() {
      return new JMSConnectionConsumerCreateResponse();
   }

   public BEConnectionConsumerCreateRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int mask = 1;
      if (this.isDurable) {
         mask |= 256;
      }

      if (this.stopped) {
         mask |= 1024;
      }

      if (this.messageSelector != null) {
         mask |= 512;
      }

      out.writeInt(mask);
      super.writeExternal(out);
      this.backEndId.writeExternal(out);
      this.connectionId.writeExternal(out);
      ((JMSServerSessionPool)this.serverSessionPool).writeExternal(out);
      this.destinationId.writeExternal(out);
      if (this.messageSelector != null) {
         out.writeUTF(this.messageSelector);
      }

      out.writeInt(this.messagesMaximum);
      out.writeLong(this.startStopSequenceNumber);
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
         this.connectionId = new JMSID();
         this.connectionId.readExternal(in);
         this.serverSessionPool = new JMSServerSessionPool();
         ((JMSServerSessionPool)this.serverSessionPool).readExternal(in);
         this.destinationId = new JMSID();
         this.destinationId.readExternal(in);
         if ((mask & 512) != 0) {
            this.messageSelector = in.readUTF();
         }

         this.messagesMaximum = in.readInt();
         this.startStopSequenceNumber = in.readLong();
         this.isDurable = (mask & 256) != 0;
         this.stopped = (mask & 1024) != 0;
      }
   }
}
