package weblogic.jms.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.messaging.dispatcher.DispatcherId;

public final class ConsumerReconnectInfo implements Externalizable, Cloneable {
   static final long serialVersionUID = -2345606540693435552L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int _HAS_CLIENT_JMSID = 256;
   private static final int _HAS_SERVER_ID = 512;
   private static final int _HAS_CLIENT_DISPATCHER = 1024;
   private static final int _HAS_SERVER_DISPATCHER = 2048;
   private static final int _HAS_LAST_EXPOSED_MSG_ID = 4096;
   private static final int _HAS_DELAY_SERVER_CLOSE = 8192;
   private static final int _HAS_INVOKABLE_ID = 16384;
   private static final int _HAS_LAST_ACK_MSG_ID = 32768;
   private JMSID clientJMSID;
   private JMSID serverDestId;
   private JMSID invokableID;
   private DispatcherId clientDispatcherId;
   private DispatcherId serverDispatcherId;
   private long delayServerClose;
   private JMSMessageId lastExposedMsgId;
   private JMSMessageId lastAckMsgId;

   public DispatcherId getClientDispatcherId() {
      return this.clientDispatcherId;
   }

   public void setClientDispatcherId(DispatcherId clientDispatcherId) {
      this.clientDispatcherId = clientDispatcherId;
   }

   public JMSID getClientJMSID() {
      return this.clientJMSID;
   }

   public void setClientJMSID(JMSID clientJMSID) {
      this.clientJMSID = clientJMSID;
   }

   public long getDelayServerClose() {
      return this.delayServerClose;
   }

   public void setDelayServerClose(long delayServerClose) {
      this.delayServerClose = delayServerClose;
   }

   public JMSID getInvokableID() {
      return this.invokableID;
   }

   public void setInvokableID(JMSID invokableID) {
      this.invokableID = invokableID;
   }

   public JMSMessageId getLastAckMsgId() {
      return this.lastAckMsgId;
   }

   public void setLastAckMsgId(JMSMessageId lastAckMsgId) {
      this.lastAckMsgId = lastAckMsgId;
   }

   public JMSMessageId getLastExposedMsgId() {
      return this.lastExposedMsgId;
   }

   public void setLastExposedMsgId(JMSMessageId lastExposedMsgId) {
      this.lastExposedMsgId = lastExposedMsgId;
   }

   public JMSID getServerDestId() {
      return this.serverDestId;
   }

   public void setServerDestId(JMSID serverDestId) {
      this.serverDestId = serverDestId;
   }

   public DispatcherId getServerDispatcherId() {
      return this.serverDispatcherId;
   }

   public void setServerDispatcherId(DispatcherId serverDispatcherId) {
      this.serverDispatcherId = serverDispatcherId;
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

   public ConsumerReconnectInfo getClone() {
      try {
         return (ConsumerReconnectInfo)this.clone();
      } catch (CloneNotSupportedException var2) {
         throw new AssertionError(var2);
      }
   }

   public String toString() {
      return "(ConsumerReconnectInfo <clientDispatcherId " + this.clientDispatcherId + "> <clientJMSID " + this.clientJMSID + "> <lastExposedMsgId " + this.lastExposedMsgId + "> <lastAckMsgId " + this.lastAckMsgId + "> <serverDestId " + this.serverDestId + "> <serverDispatcherId " + this.serverDispatcherId + "> <delayServerClose " + this.delayServerClose + ">)";
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int flags = 1;
      if (this.clientJMSID != null) {
         flags |= 256;
      }

      if (this.serverDestId != null) {
         flags |= 512;
      }

      if (this.clientDispatcherId != null) {
         flags |= 1024;
      }

      if (this.serverDispatcherId != null) {
         flags |= 2048;
      }

      if (this.lastExposedMsgId != null) {
         flags |= 4096;
      }

      if (this.lastAckMsgId != null) {
         flags |= 32768;
      }

      if (this.delayServerClose != 0L) {
         flags |= 8192;
      }

      if (this.invokableID != null) {
         flags |= 16384;
      }

      out.writeInt(flags);
      if (this.delayServerClose != 0L) {
         out.writeLong(this.delayServerClose);
      }

      if (this.lastAckMsgId != null) {
         this.lastAckMsgId.writeExternal(out);
      }

      if (this.lastExposedMsgId != null) {
         this.lastExposedMsgId.writeExternal(out);
      }

      if (this.clientJMSID != null) {
         this.clientJMSID.writeExternal(out);
      }

      if (this.serverDestId != null) {
         this.serverDestId.writeExternal(out);
      }

      if (this.clientDispatcherId != null) {
         this.clientDispatcherId.writeExternal(out);
      }

      if (this.serverDispatcherId != null) {
         this.serverDispatcherId.writeExternal(out);
      }

      if (this.invokableID != null) {
         this.invokableID.writeExternal(out);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int flags = in.readInt();
      int version = flags & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         if ((flags & 8192) != 0) {
            this.delayServerClose = in.readLong();
         }

         if ((flags & '耀') != 0) {
            this.lastAckMsgId = new JMSMessageId();
            this.lastAckMsgId.readExternal(in);
         }

         if ((flags & 4096) != 0) {
            this.lastExposedMsgId = new JMSMessageId();
            this.lastExposedMsgId.readExternal(in);
         }

         if ((flags & 256) != 0) {
            this.clientJMSID = new JMSID();
            this.clientJMSID.readExternal(in);
         }

         if ((flags & 512) != 0) {
            this.serverDestId = new JMSID();
            this.serverDestId.readExternal(in);
         }

         if ((flags & 1024) != 0) {
            this.clientDispatcherId = new DispatcherId();
            this.clientDispatcherId.readExternal(in);
         }

         if ((flags & 2048) != 0) {
            this.serverDispatcherId = new DispatcherId();
            this.serverDispatcherId.readExternal(in);
         }

         if ((flags & 16384) != 0) {
            this.invokableID = new JMSID();
            this.invokableID.readExternal(in);
         }

      }
   }
}
