package weblogic.jms.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import javax.jms.Message;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.jms.dispatcher.Response;
import weblogic.jms.frontend.FEProducerSendRequest;

public final class JMSProducerSendResponse extends Response implements Externalizable, ProducerSendResponse, AsyncSendResponseInfo {
   static final long serialVersionUID = -2438555459624785305L;
   private static final int VERSION61 = 1;
   private static final int EXTVERSION = 2;
   private static final int VERSION_MASK = 255;
   private static final int DELIVERY_MODE_MASK = 256;
   private static final int PRIORITY_MASK = 512;
   private static final int TIME_TO_LIVE_MASK = 1024;
   private static final int TIME_TO_DELIVER_MASK = 2048;
   private static final int FLOW_CONTROL_MASK = 4096;
   private static final int REDELIVERY_LIMIT_MASK = 8192;
   private static final int UOO90_MEMBER_MASK = 16384;
   private static final int DIABLOSTYLEMESSAGEID_MASK = 32768;
   private int mask;
   private JMSMessageId messageId;
   private int deliveryMode;
   private int priority;
   private long timeToLive;
   private long timeToDeliver;
   private int redeliveryLimit;
   private long flowControlTime;
   private FEProducerSendRequest request;
   private Serializable uooMember;
   private transient Message message;
   private transient long asyncFlowControlTime;

   public JMSProducerSendResponse(JMSMessageId messageId) {
      this.messageId = messageId;
   }

   public void setMessageId(JMSMessageId messageId) {
      this.messageId = messageId;
   }

   public JMSMessageId getMessageId() {
      return this.messageId;
   }

   public void setDeliveryMode(int deliveryMode) {
      this.mask |= 256;
      this.deliveryMode = deliveryMode;
   }

   public int getDeliveryMode() {
      return (this.mask & 256) == 0 ? -1 : this.deliveryMode;
   }

   public void setPriority(int priority) {
      this.mask |= 512;
      this.priority = priority;
   }

   public int getPriority() {
      return (this.mask & 512) == 0 ? -1 : this.priority;
   }

   public void setTimeToLive(long timeToLive) {
      this.mask |= 1024;
      this.timeToLive = timeToLive;
   }

   public long getTimeToLive() {
      return (this.mask & 1024) == 0 ? -1L : this.timeToLive;
   }

   public void setTimeToDeliver(long timeToDeliver) {
      this.mask |= 2048;
      this.timeToDeliver = timeToDeliver;
   }

   public long getTimeToDeliver() {
      return (this.mask & 2048) == 0 ? -1L : this.timeToDeliver;
   }

   public void setRedeliveryLimit(int redeliveryLimit) {
      this.mask |= 8192;
      this.redeliveryLimit = redeliveryLimit;
   }

   public void set90StyleMessageId() {
      this.mask |= 32768;
   }

   public boolean get90StyleMessageId() {
      return (this.mask & '耀') != 0;
   }

   public int getRedeliveryLimit() {
      return (this.mask & 8192) == 0 ? 0 : this.redeliveryLimit;
   }

   public void setNeedsFlowControl(boolean needsFlowControl) {
      if (needsFlowControl) {
         this.mask |= 4096;
      } else {
         this.mask &= -4097;
      }

   }

   public boolean getNeedsFlowControl() {
      return (this.mask & 4096) != 0;
   }

   public void setFlowControlTime(long flowControlTime) {
      this.mask |= 4096;
      this.flowControlTime = flowControlTime;
   }

   public long getFlowControlTime() {
      return (this.mask & 4096) == 0 ? -1L : this.flowControlTime;
   }

   public Message getMessage() {
      return this.message;
   }

   public void setMessage(Message message) {
      this.message = message;
   }

   public long getAsyncFlowControlTime() {
      return this.asyncFlowControlTime;
   }

   public void setAsyncFlowControlTime(long flowControlTime) {
      this.asyncFlowControlTime = flowControlTime;
   }

   public void setRequest(FEProducerSendRequest request) {
      this.request = request;
   }

   public boolean isDispatchOneWay() {
      return this.request == null ? false : this.request.isNoResponse();
   }

   public void setUOOInfo(Serializable uooMember) {
      this.uooMember = uooMember;
      if (uooMember == null) {
         this.mask &= -16385;
      } else {
         this.mask |= 16384;
      }

   }

   public Serializable getUOOInfo() {
      return this.uooMember;
   }

   private int getVersion(ObjectOutput out) throws IOException {
      if (out instanceof PeerInfoable) {
         PeerInfo peerInfo = ((PeerInfoable)out).getPeerInfo();
         if (peerInfo.compareTo(PeerInfo.VERSION_61) < 0) {
            throw new IOException(JMSClientExceptionLogger.logInvalidResponseLoggable(1, 2, peerInfo.toString()).getMessage());
         }

         if (peerInfo.compareTo(PeerInfo.VERSION_70) < 0) {
            return 1;
         }
      }

      return 2;
   }

   public JMSProducerSendResponse() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      int version = this.getVersion(out);
      super.writeExternal(out);
      this.mask = this.mask & -256 | version;
      out.writeInt(this.mask);
      this.messageId.writeExternal(out);
      if ((this.mask & 256) != 0) {
         out.writeInt(this.deliveryMode);
      }

      if ((this.mask & 512) != 0) {
         out.writeInt(this.priority);
      }

      if ((this.mask & 1024) != 0) {
         out.writeLong(this.timeToLive);
      }

      if ((this.mask & 2048) != 0) {
         out.writeLong(this.timeToDeliver);
      }

      if (version == 2) {
         if ((this.mask & 8192) != 0) {
            out.writeInt(this.redeliveryLimit);
         }

         if ((this.mask & 4096) != 0) {
            out.writeLong(this.flowControlTime);
         }
      }

      if ((this.mask & 16384) != 0) {
         out.writeObject(this.uooMember);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
      this.mask = in.readInt();
      int version = this.mask & 255;
      if (version >= 1 && version <= 2) {
         this.messageId = new JMSMessageId();
         this.messageId.readExternal(in);
         if ((this.mask & 256) != 0) {
            this.deliveryMode = in.readInt();
         }

         if ((this.mask & 512) != 0) {
            this.priority = in.readInt();
         }

         if ((this.mask & 1024) != 0) {
            this.timeToLive = in.readLong();
         }

         if ((this.mask & 2048) != 0) {
            this.timeToDeliver = in.readLong();
         }

         if ((this.mask & 8192) != 0) {
            this.redeliveryLimit = in.readInt();
         }

         if ((this.mask & 4096) != 0) {
            this.flowControlTime = in.readLong();
         }

         if ((this.mask & 16384) != 0) {
            this.uooMember = (Serializable)((Serializable)in.readObject());
         }

      } else {
         throw JMSUtilities.versionIOException(version, 1, 2);
      }
   }
}
