package weblogic.jms.backend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.common.ConsumerReconnectInfo;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;
import weblogic.store.PersistentHandle;

public final class BEConsumerCreateRequest extends Request implements Externalizable {
   static final long serialVersionUID = 3890402952967020905L;
   private static final int EXTVERSION61 = 1;
   private static final int EXTVERSION92 = 2;
   private static final int EXTVERSION1033 = 3;
   private static final int EXTVERSION = 3;
   private static final int VERSION_MASK = 255;
   private static final int CLIENT_ID_MASK = 256;
   private static final int NAME_MASK = 512;
   private static final int SELECTOR_MASK = 1024;
   private static final int SUBJECT_MASK = 2048;
   private static final int RECONNECT_MASK = 4096;
   private static final int JMS2_MASK = 8192;
   private static final int ISDURABLE_MASK = 16384;
   private JMSID beConnId;
   private JMSID consumerId;
   private String clientId;
   private String name;
   private boolean durable;
   private JMSID destinationId;
   private String selector;
   private boolean noLocal;
   private int messagesMaximum;
   private int flag;
   private long redeliveryDelay;
   private String subject;
   private ConsumerReconnectInfo consumerReconnectInfo;
   private int clientIdPolicy;
   private int subscriptionSharingPolicy;
   private transient PersistentHandle persistentHandle;

   public BEConsumerCreateRequest(JMSID beConnId, JMSID sessionId, JMSID consumerId, String clientId, String name, boolean isDurable, JMSID destinationId, String selector, boolean noLocal, int messagesMaximum, int flag, long redeliveryDelay, String subject, ConsumerReconnectInfo consumerReconnectInfo) {
      this(beConnId, sessionId, consumerId, clientId, 0, name, isDurable, destinationId, selector, noLocal, messagesMaximum, flag, redeliveryDelay, subject, consumerReconnectInfo, 0);
   }

   public BEConsumerCreateRequest(JMSID beConnId, JMSID sessionId, JMSID consumerId, String clientId, int clientIdPolicy, String name, boolean isDurable, JMSID destinationId, String selector, boolean noLocal, int messagesMaximum, int flag, long redeliveryDelay, String subject, ConsumerReconnectInfo consumerReconnectInfo, int subscriptionSharingPolicy) {
      super(sessionId, 10256);
      this.clientIdPolicy = 0;
      this.subscriptionSharingPolicy = 0;
      this.beConnId = beConnId;
      this.consumerId = consumerId;
      this.clientId = clientId;
      this.name = name;
      this.durable = isDurable;
      this.destinationId = destinationId;
      this.selector = selector;
      this.noLocal = noLocal;
      this.messagesMaximum = messagesMaximum;
      this.flag = flag;
      this.redeliveryDelay = redeliveryDelay;
      this.subject = subject;
      this.consumerReconnectInfo = consumerReconnectInfo;
      this.clientIdPolicy = clientIdPolicy;
      this.subscriptionSharingPolicy = subscriptionSharingPolicy;
   }

   public final JMSID getConsumerId() {
      return this.consumerId;
   }

   public final String getClientId() {
      return this.clientId;
   }

   final void setClientId(String clientId) {
      this.clientId = clientId;
   }

   public final String getName() {
      return this.name;
   }

   public final boolean isDurable() {
      return this.durable;
   }

   final void setName(String name) {
      this.name = name;
   }

   public final JMSID getDestinationId() {
      return this.destinationId;
   }

   final String getSelector() {
      return this.selector;
   }

   public final boolean getNoLocal() {
      return this.noLocal;
   }

   public void setNoLocal(boolean noLocal) {
      this.noLocal = noLocal;
   }

   public final int getMessagesMaximum() {
      return this.messagesMaximum;
   }

   public final String getSubject() {
      return this.subject;
   }

   public final int getFlag() {
      return this.flag;
   }

   public final long getRedeliveryDelay() {
      return this.redeliveryDelay;
   }

   final PersistentHandle getPersistentHandle() {
      return this.persistentHandle;
   }

   final void setPersistentHandle(PersistentHandle persistentHandle) {
      this.persistentHandle = persistentHandle;
   }

   public ConsumerReconnectInfo getConsumerReconnectInfo() {
      return this.consumerReconnectInfo;
   }

   void setClientIdPolicy(int clientIdPolicy) {
      this.clientIdPolicy = clientIdPolicy;
   }

   public final int getClientIdPolicy() {
      return this.clientIdPolicy;
   }

   void setSubscriptionSharingPolicy(int subscriptionSharingPolicy) {
      this.subscriptionSharingPolicy = subscriptionSharingPolicy;
   }

   public final int getSubscriptionSharingPolicy() {
      return this.subscriptionSharingPolicy;
   }

   public final void setConsumerReconnectInfo(ConsumerReconnectInfo consumerReconnectInfo) {
      this.consumerReconnectInfo = consumerReconnectInfo;
   }

   public int remoteSignature() {
      return 18;
   }

   public boolean isServerToServer() {
      return true;
   }

   public Response createResponse() {
      return new BEConsumerCreateResponse();
   }

   public BEConsumerCreateRequest() {
      this.clientIdPolicy = 0;
      this.subscriptionSharingPolicy = 0;
   }

   private byte getVersionForWrite(Object o) throws IOException {
      if (o instanceof PeerInfoable) {
         PeerInfo pi = ((PeerInfoable)o).getPeerInfo();
         if (pi.compareTo(PeerInfo.VERSION_1221) < 0) {
            this.checkJMS2ForWriteToOldPeer(pi);
         }

         if (pi.compareTo(PeerInfo.VERSION_61) < 0) {
            throw JMSUtilities.versionIOException(0, 1, 2);
         }

         if (pi.compareTo(PeerInfo.VERSION_920) < 0) {
            return 1;
         }

         if (pi.compareTo(PeerInfo.VERSION_1033) < 0) {
            return 3;
         }
      } else {
         this.checkJMS2ForWriteToOldPeer((PeerInfo)null);
      }

      return 3;
   }

   private void checkJMS2ForWriteToOldPeer(PeerInfo pi) throws IOException {
      if (!this.isDurable() && this.name != null) {
         throw new IOException("Unsupported operation to back-end server version " + (pi == null ? " < [" + PeerInfo.VERSION_1221 + "]: " : "[" + pi + "]: ") + " create nondurable subscription with name " + this.name);
      } else if (this.isDurable() && this.clientId == null) {
         throw new IOException("Unsupported operation to back-end server version " + (pi == null ? " < [" + PeerInfo.VERSION_1221 + "]: " : "[" + pi + "]: ") + " create durable subscription with name " + this.name + " with null client id");
      }
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      int version = this.getVersionForWrite(out) & 255;
      int mask;
      if (version >= 2 && this.consumerReconnectInfo != null) {
         mask = version | 4096;
      } else {
         mask = version;
      }

      if (this.clientId != null) {
         mask |= 256;
      }

      if (this.name != null) {
         mask |= 512;
      }

      if (this.selector != null) {
         mask |= 1024;
      }

      if (this.subject != null) {
         mask |= 2048;
      }

      mask |= 8192;
      if (this.durable) {
         mask |= 16384;
      }

      out.writeInt(mask);
      super.writeExternal(out);
      this.beConnId.writeExternal(out);
      this.consumerId.writeExternal(out);
      this.destinationId.writeExternal(out);
      if (this.clientId != null) {
         out.writeUTF(this.clientId);
      }

      if (this.name != null) {
         out.writeUTF(this.name);
      }

      if (this.selector != null) {
         out.writeUTF(this.selector);
      }

      out.writeBoolean(this.noLocal);
      out.writeInt(this.messagesMaximum);
      out.writeInt(this.flag);
      out.writeLong(this.redeliveryDelay);
      if (this.subject != null) {
         out.writeUTF(this.subject);
      }

      if ((mask & 4096) != 0) {
         this.consumerReconnectInfo.writeExternal(out);
      }

      if (version >= 3) {
         out.writeInt(this.clientIdPolicy);
         out.writeInt(this.subscriptionSharingPolicy);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1 && 2 != version && version != 3) {
         throw JMSUtilities.versionIOException(version, 1, 3);
      } else {
         super.readExternal(in);
         this.beConnId = new JMSID();
         this.beConnId.readExternal(in);
         this.consumerId = new JMSID();
         this.consumerId.readExternal(in);
         this.destinationId = new JMSID();
         this.destinationId.readExternal(in);
         this.name = null;
         this.clientId = null;
         if ((mask & 256) != 0) {
            this.clientId = in.readUTF();
         }

         if ((mask & 512) != 0) {
            this.name = in.readUTF();
         }

         if ((mask & 1024) != 0) {
            this.selector = in.readUTF();
         }

         if ((mask & 8192) != 0) {
            this.durable = (mask & 16384) != 0;
         } else {
            this.durable = this.name != null;
         }

         this.noLocal = in.readBoolean();
         this.messagesMaximum = in.readInt();
         this.flag = in.readInt();
         this.redeliveryDelay = in.readLong();
         if ((mask & 2048) != 0) {
            this.subject = in.readUTF();
         }

         if ((mask & 4096) != 0) {
            this.consumerReconnectInfo = new ConsumerReconnectInfo();
            this.consumerReconnectInfo.readExternal(in);
         }

         if (version >= 3) {
            this.clientIdPolicy = in.readInt();
            this.subscriptionSharingPolicy = in.readInt();
         }

      }
   }
}
