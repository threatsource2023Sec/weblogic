package weblogic.jms.frontend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.jms.ServerSessionPool;
import weblogic.jms.client.JMSServerSessionPool;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.DistributedDestinationImpl;
import weblogic.jms.common.JMSConnectionConsumerCreateResponse;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;

public final class FEConnectionConsumerCreateRequest extends Request implements Externalizable {
   static final long serialVersionUID = 7143098392927142826L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int DURABLE_MASK = 256;
   private static final int SELECTOR_MASK = 512;
   private static final int PERMISSION_MASK = 1024;
   private static final int DISTRIBUTEDDESTINATION_MASK = 8192;
   private ServerSessionPool serverSessionPool;
   private DestinationImpl destination;
   private boolean durable;
   private String messageSelector;
   private int messagesMaximum;
   private boolean checkPermission;

   public FEConnectionConsumerCreateRequest(JMSID connectionId, ServerSessionPool serverSessionPool, DestinationImpl destination, boolean durable, String messageSelector, int messagesMaximum, boolean checkPermission) {
      super(connectionId, 1543);
      this.serverSessionPool = serverSessionPool;
      this.destination = destination;
      this.durable = durable;
      this.messageSelector = messageSelector;
      this.messagesMaximum = messagesMaximum;
      this.checkPermission = checkPermission;
   }

   public final ServerSessionPool getServerSessionPool() {
      return this.serverSessionPool;
   }

   public final DestinationImpl getDestination() {
      return this.destination;
   }

   public final boolean isDurable() {
      return this.durable;
   }

   public final String getMessageSelector() {
      return this.messageSelector;
   }

   public final int getMessagesMaximum() {
      return this.messagesMaximum;
   }

   public final boolean checkPermission() {
      return this.checkPermission;
   }

   public int remoteSignature() {
      return 18;
   }

   public Response createResponse() {
      return new JMSConnectionConsumerCreateResponse();
   }

   public FEConnectionConsumerCreateRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      int mask = 1;
      if (this.durable) {
         mask |= 256;
      }

      if (this.messageSelector != null) {
         mask |= 512;
      }

      if (this.checkPermission) {
         mask |= 1024;
      }

      if (this.destination instanceof DistributedDestinationImpl) {
         mask |= 8192;
      }

      out.writeInt(mask);
      super.writeExternal(out);
      ((JMSServerSessionPool)this.serverSessionPool).writeExternal(out);
      this.destination.writeExternal(out);
      if (this.messageSelector != null) {
         out.writeUTF(this.messageSelector);
      }

      out.writeInt(this.messagesMaximum);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         this.durable = (mask & 256) != 0;
         this.checkPermission = (mask & 1024) != 0;
         this.serverSessionPool = new JMSServerSessionPool();
         ((JMSServerSessionPool)this.serverSessionPool).readExternal(in);
         if ((mask & 8192) != 0) {
            this.destination = new DistributedDestinationImpl();
            this.destination.readExternal(in);
         } else {
            this.destination = new DestinationImpl();
            this.destination.readExternal(in);
         }

         if ((mask & 512) != 0) {
            this.messageSelector = in.readUTF();
         }

         this.messagesMaximum = in.readInt();
      }
   }
}
