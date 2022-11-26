package weblogic.jms.frontend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.common.ConsumerReconnectInfo;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.DistributedDestinationImpl;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;

public final class FEConsumerCreateRequest extends Request implements Externalizable {
   static final long serialVersionUID = -2345606550673535552L;
   private static final int EXTVERSION_PRE811 = 1;
   private static final int EXTVERSION_PRE920 = 10;
   private static final int EXTVERSION_PRE1033 = 12;
   private static final int EXTVERSION4 = 14;
   private static final int EXTVERSION = 14;
   private static final int VERSION_MASK = 255;
   private static final int _HASCLIENTID = 256;
   private static final int _HASNAME = 512;
   private static final int _HASSELECTOR = 1024;
   private static final int _ISNOLOCAL = 2048;
   private static final int _HASREDELIVERYDELAY = 4096;
   private static final int _HASDISTRIBUTEDDESTINATION = 8192;
   private static final int _HAS_CONSUMER_RECON_INFO = 16384;
   private static final int _JMS2 = 32768;
   private static final int _ISDURABLE = 65536;
   private String clientId;
   private String name;
   private boolean durable;
   private DestinationImpl destination;
   private String selector;
   private boolean noLocal;
   private int messagesMaximum;
   private long redeliveryDelay;
   private transient int numberOfRetries;
   private ConsumerReconnectInfo consumerReconnectInfo;
   private int subscriptionSharingPolicy = -1;

   public FEConsumerCreateRequest(JMSID sessionId, String clientId, String name, boolean isDurable, DestinationImpl destination, String selector, boolean noLocal, int messagesMaximum, long redeliveryDelay, ConsumerReconnectInfo consumerReconnectInfo, int subscriptionSharingPolicy) {
      super(sessionId, 2824);
      this.clientId = clientId;
      this.name = name;
      this.durable = isDurable;
      this.destination = destination;
      this.selector = selector;
      this.noLocal = noLocal;
      this.messagesMaximum = messagesMaximum;
      this.redeliveryDelay = redeliveryDelay;
      this.consumerReconnectInfo = consumerReconnectInfo;
      this.subscriptionSharingPolicy = subscriptionSharingPolicy;
   }

   String getClientId() {
      return this.clientId;
   }

   String getName() {
      return this.name;
   }

   boolean isDurable() {
      return this.durable;
   }

   DestinationImpl getDestination() {
      return this.destination;
   }

   void setDestination(DestinationImpl inDest) {
      this.destination = inDest;
   }

   void setSelector(String selector) {
      this.selector = selector;
   }

   String getSelector() {
      return this.selector;
   }

   boolean getNoLocal() {
      return this.noLocal;
   }

   int getMessagesMaximum() {
      return this.messagesMaximum;
   }

   long getRedeliveryDelay() {
      return this.redeliveryDelay;
   }

   int getSubscriptionSharingPolicy() {
      return this.subscriptionSharingPolicy;
   }

   public int remoteSignature() {
      return 18;
   }

   public Response createResponse() {
      return new FEConsumerCreateResponse();
   }

   int getNumberOfRetries() {
      return this.numberOfRetries;
   }

   void setNumberOfRetries(int nor) {
      this.numberOfRetries = nor;
   }

   public ConsumerReconnectInfo getConsumerReconnectInfo() {
      return this.consumerReconnectInfo;
   }

   public void setConsumerReconnectInfo(ConsumerReconnectInfo cri) {
      this.consumerReconnectInfo = cri;
   }

   public FEConsumerCreateRequest() {
   }

   protected byte getVersion(Object o) throws IOException {
      if (o instanceof PeerInfoable) {
         PeerInfo pi = ((PeerInfoable)o).getPeerInfo();
         if (pi.compareTo(PeerInfo.VERSION_61) < 0) {
            throw JMSUtilities.versionIOException(0, 1, 14);
         }

         if (pi.compareTo(PeerInfo.VERSION_811) < 0) {
            return 1;
         }

         if (pi.compareTo(PeerInfo.VERSION_920) < 0) {
            return 10;
         }

         if (pi.compareTo(PeerInfo.VERSION_1033) < 0) {
            return 12;
         }
      }

      return 14;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      int peerVersion = this.getVersion(out) & 255;
      int flags = peerVersion;
      boolean isClientIdSet = false;
      if (this.name != null) {
         flags = peerVersion | 512;
      }

      if (peerVersion == 1 && this.name != null && this.clientId != null) {
         flags |= 256;
         isClientIdSet = true;
      }

      if (peerVersion >= 12 && this.consumerReconnectInfo != null) {
         flags |= 16384;
      }

      if (this.selector != null) {
         flags |= 1024;
      }

      if (this.noLocal) {
         flags |= 2048;
      }

      flags |= 32768;
      if (this.durable) {
         flags |= 65536;
      }

      if (this.redeliveryDelay != 0L) {
         flags |= 4096;
      }

      if (this.destination instanceof DistributedDestinationImpl) {
         flags |= 8192;
      }

      out.writeInt(flags);
      super.writeExternal(out);
      if (isClientIdSet) {
         out.writeUTF(this.clientId);
      }

      if (this.name != null) {
         out.writeUTF(this.name);
      }

      if (this.selector != null) {
         out.writeUTF(this.selector);
      }

      this.destination.writeExternal(out);
      out.writeInt(this.messagesMaximum);
      if (this.redeliveryDelay != 0L) {
         out.writeLong(this.redeliveryDelay);
      }

      if ((flags & 16384) != 0) {
         this.consumerReconnectInfo.writeExternal(out);
      }

      if (peerVersion >= 14) {
         out.writeInt(this.subscriptionSharingPolicy);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int flags = in.readInt();
      int version = flags & 255;
      if (version >= 1 && version <= 14) {
         super.readExternal(in);
         if ((flags & 256) != 0) {
            this.clientId = in.readUTF();
         }

         if ((flags & 512) != 0) {
            this.name = in.readUTF();
         }

         if ((flags & 1024) != 0) {
            this.selector = in.readUTF();
         }

         if ((flags & 2048) != 0) {
            this.noLocal = true;
         }

         if ((flags & '耀') != 0) {
            this.durable = (flags & 65536) != 0;
         } else {
            this.durable = this.name != null;
         }

         if ((flags & 8192) != 0) {
            this.destination = new DistributedDestinationImpl();
            this.destination.readExternal(in);
         } else {
            this.destination = new DestinationImpl();
            this.destination.readExternal(in);
         }

         this.messagesMaximum = in.readInt();
         if ((flags & 4096) != 0) {
            this.redeliveryDelay = in.readLong();
         }

         if ((flags & 16384) != 0) {
            this.consumerReconnectInfo = new ConsumerReconnectInfo();
            this.consumerReconnectInfo.readExternal(in);
         }

         if (version >= 14) {
            this.subscriptionSharingPolicy = in.readInt();
         }

      } else {
         throw JMSUtilities.versionIOException(version, 1, 14);
      }
   }
}
