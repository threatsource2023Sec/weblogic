package weblogic.jms.frontend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.DistributedDestinationImpl;
import weblogic.jms.common.JMSBrowserCreateResponse;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jms.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;

public final class FEBrowserCreateRequest extends Request implements Externalizable {
   static final long serialVersionUID = 7024696476454603978L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int SELECTOR_MASK = 256;
   private static final int DISTRIBUTEDDESTINATION_MASK = 8192;
   public static final int START = 0;
   public static final int CONTINUE = 1;
   public static final int CONTINUE_FURTHER = 2;
   private transient JMSDispatcher dispatcher;
   private JMSID connectionId;
   private DestinationImpl destination;
   private String messageSelector;
   private transient int numberOfRetries;
   private static final PeerInfo queueBrowserChildOfSession;

   public FEBrowserCreateRequest(JMSID connectionId, JMSID sessionId, DestinationImpl destination, String messageSelector) {
      super(sessionId, 520);
      this.connectionId = connectionId;
      this.destination = destination;
      this.messageSelector = messageSelector;
   }

   public DestinationImpl getDestination() {
      return this.destination;
   }

   public String getMessageSelector() {
      return this.messageSelector;
   }

   public JMSDispatcher getDispatcher() {
      return this.dispatcher;
   }

   void setDestination(DestinationImpl inDest) {
      this.destination = inDest;
   }

   public void setDispatcher(JMSDispatcher dispatcher) {
      this.dispatcher = dispatcher;
   }

   public int remoteSignature() {
      return 32;
   }

   public Response createResponse() {
      return new JMSBrowserCreateResponse();
   }

   int getNumberOfRetries() {
      return this.numberOfRetries;
   }

   void setNumberOfRetries(int nor) {
      this.numberOfRetries = nor;
   }

   public FEBrowserCreateRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      int mask = 1;
      out = this.getVersionedStream(out);
      PeerInfo peerInfo = ((PeerInfoable)out).getPeerInfo();
      if (peerInfo.compareTo(queueBrowserChildOfSession) < 0) {
         this.setMethodId(519);
         this.setInvocableId(this.connectionId);
      }

      if (this.messageSelector != null) {
         mask |= 256;
      }

      if (this.destination instanceof DistributedDestinationImpl) {
         mask |= 8192;
      }

      out.writeInt(mask);
      super.writeExternal(out);
      this.destination.writeExternal(out);
      if (this.messageSelector != null) {
         out.writeUTF(this.messageSelector);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         if ((mask & 8192) != 0) {
            this.destination = new DistributedDestinationImpl();
            this.destination.readExternal(in);
         } else {
            this.destination = new DestinationImpl();
            this.destination.readExternal(in);
         }

         if ((mask & 256) != 0) {
            this.messageSelector = in.readUTF();
         }

      }
   }

   static {
      queueBrowserChildOfSession = PeerInfo.VERSION_614;
   }
}
