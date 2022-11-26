package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public final class ProxyProducerSendResponse extends ProxyResponse {
   private static final int EXTVERSION = 1;
   private static final int _HAS_DELIVERY_MODE = 1;
   private static final int _HAS_PRIORITY = 2;
   private static final int _HAS_TIME_STAMP = 3;
   private static final int _HAS_EXPIRATION = 4;
   private static final int _HAS_TIME_TO_DELIVER = 5;
   private static final int _HAS_FLOW_CONTROL = 6;
   private static final int _HAS_REDELIVERY_LIMIT = 7;
   private static final int _HAS_WLMESSAGEID = 8;
   private String messageId;
   private int deliveryMode = -1;
   private int priority;
   private int seed;
   private long timestamp;
   private int counter;
   private long expirationTime;
   private long deliveryTime;
   private int redeliveryLimit;
   private long flowControlTime;

   public ProxyProducerSendResponse(String messageId) {
      this.messageId = messageId;
      this.versionFlags = new MarshalBitMask(1);
   }

   public ProxyProducerSendResponse(int seed, int counter) {
      this.seed = seed;
      this.counter = counter;
      this.versionFlags = new MarshalBitMask(1);
      this.versionFlags.setBit(8);
   }

   public String getMessageId() {
      return this.messageId != null ? this.messageId : "ID:<" + this.seed + "." + this.timestamp + "." + this.counter + ">";
   }

   public void setDeliveryMode(int deliveryMode) {
      this.versionFlags.setBit(1);
      this.deliveryMode = deliveryMode;
   }

   public int getDeliveryMode() {
      return !this.versionFlags.isSet(1) ? -1 : this.deliveryMode;
   }

   public void setPriority(int priority) {
      this.versionFlags.setBit(2);
      this.priority = priority;
   }

   public int getPriority() {
      return !this.versionFlags.isSet(2) ? -1 : this.priority;
   }

   public void setExpirationTime(long expirationTime) {
      this.versionFlags.setBit(4);
      this.expirationTime = expirationTime;
   }

   public long getExpirationTime() {
      return !this.versionFlags.isSet(4) ? -1L : this.expirationTime;
   }

   public void setTimestamp(long timestamp) {
      this.versionFlags.setBit(3);
      this.timestamp = timestamp;
   }

   public long getTimestamp() {
      return !this.versionFlags.isSet(3) ? -1L : this.timestamp;
   }

   public void setDeliveryTime(long timeToDeliver) {
      this.versionFlags.setBit(5);
      this.deliveryTime = timeToDeliver;
   }

   public long getTimeToDeliver() {
      return !this.versionFlags.isSet(5) ? -1L : this.deliveryTime;
   }

   public void setRedeliveryLimit(int redeliveryLimit) {
      this.versionFlags.setBit(7);
      this.redeliveryLimit = redeliveryLimit;
   }

   public int getRedeliveryLimit() {
      return !this.versionFlags.isSet(7) ? 0 : this.redeliveryLimit;
   }

   public void setFlowControlTime(long flowControlTime) {
      if (flowControlTime != 0L) {
         this.versionFlags.setBit(6);
      }

      this.flowControlTime = flowControlTime;
   }

   public long getFlowControlTime() {
      return !this.versionFlags.isSet(6) ? 0L : this.flowControlTime;
   }

   public ProxyProducerSendResponse() {
   }

   public int getMarshalTypeCode() {
      return 26;
   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags.marshal(mw);
      if (this.versionFlags.isSet(8)) {
         mw.writeInt(this.seed);
         mw.writeInt(this.counter);
      } else {
         mw.writeString(this.messageId);
      }

      if (this.versionFlags.isSet(1)) {
         mw.writeInt(this.deliveryMode);
      }

      if (this.versionFlags.isSet(2)) {
         mw.writeInt(this.priority);
      }

      if (this.versionFlags.isSet(4)) {
         mw.writeLong(this.expirationTime);
      }

      if (this.versionFlags.isSet(3)) {
         mw.writeLong(this.timestamp);
      }

      if (this.versionFlags.isSet(5)) {
         mw.writeLong(this.deliveryTime);
      }

      if (this.versionFlags.isSet(7)) {
         mw.writeInt(this.redeliveryLimit);
      }

      if (this.versionFlags.isSet(6)) {
         mw.writeLong(this.flowControlTime);
      }

   }

   public void unmarshal(MarshalReader mr) {
      MarshalBitMask versionFlags = new MarshalBitMask();
      versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(versionFlags.getVersion(), 1, 1);
      if (versionFlags.isSet(8)) {
         this.seed = mr.readInt();
         this.counter = mr.readInt();
      } else {
         this.messageId = mr.readString();
      }

      if (versionFlags.isSet(1)) {
         this.deliveryMode = mr.readInt();
      }

      if (versionFlags.isSet(2)) {
         this.priority = mr.readInt();
      }

      if (versionFlags.isSet(4)) {
         this.expirationTime = mr.readLong();
      }

      if (versionFlags.isSet(3)) {
         this.timestamp = mr.readLong();
      }

      if (versionFlags.isSet(5)) {
         this.deliveryTime = mr.readLong();
      }

      if (versionFlags.isSet(7)) {
         this.redeliveryLimit = mr.readInt();
      }

      if (versionFlags.isSet(6)) {
         this.flowControlTime = mr.readLong();
      }

   }
}
