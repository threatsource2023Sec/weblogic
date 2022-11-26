package weblogic.jms.dotnet.proxy.protocol;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;
import weblogic.jms.extensions.WLMessageProducer;

public class ProxyProducerCreateResponse extends ProxyResponse {
   private static final int EXTVERSION = 1;
   private static final int _HAS_EXTENSIONS = 1;
   private static final int _HAS_DEFAULT_UOO = 2;
   private static final int _HAS_COMPRESSION_THRESHOLD = 3;
   private long id;
   private int deliveryMode;
   private int priority;
   private long timeToLive;
   private long timeToDeliver;
   private long sendTimeOut;
   private String unitOfOrder;
   private long compressionThreshold;
   private boolean hasExtensions;

   public ProxyProducerCreateResponse(long id, MessageProducer producer) throws JMSException {
      this.id = id;
      this.deliveryMode = producer.getDeliveryMode();
      this.priority = producer.getPriority();
      this.timeToLive = producer.getTimeToLive();
      if (producer instanceof WLMessageProducer) {
         this.hasExtensions = true;
         this.timeToDeliver = ((WLMessageProducer)producer).getTimeToDeliver();
         this.sendTimeOut = ((WLMessageProducer)producer).getSendTimeout();
         this.unitOfOrder = ((WLMessageProducer)producer).getUnitOfOrder();
         this.compressionThreshold = (long)((WLMessageProducer)producer).getCompressionThreshold();
      }

   }

   public long getProducerId() {
      return this.id;
   }

   public int getDeliveryMode() {
      return this.deliveryMode;
   }

   public int getPriority() {
      return this.priority;
   }

   public long getTimeToLive() {
      return this.timeToLive;
   }

   public long getTimeToDeliver() {
      return this.timeToDeliver;
   }

   public long getSendTimeOut() {
      return this.sendTimeOut;
   }

   public String getUnitOfOrder() {
      return this.unitOfOrder;
   }

   public long getCompressionThreshold() {
      return this.compressionThreshold;
   }

   public ProxyProducerCreateResponse() {
   }

   public int getMarshalTypeCode() {
      return 24;
   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags = new MarshalBitMask(1);
      if (this.hasExtensions) {
         this.versionFlags.setBit(1);
      }

      if (this.unitOfOrder != null) {
         this.versionFlags.setBit(2);
      }

      if (this.compressionThreshold != 2147483647L) {
         this.versionFlags.setBit(3);
      }

      this.versionFlags.marshal(mw);
      mw.writeLong(this.id);
      mw.writeInt(this.deliveryMode);
      mw.writeInt(this.priority);
      mw.writeLong(this.timeToLive);
      if (this.hasExtensions) {
         mw.writeLong(this.timeToDeliver);
         mw.writeLong(this.sendTimeOut);
         if (this.unitOfOrder != null) {
            mw.writeString(this.unitOfOrder);
         }

         if (this.compressionThreshold != 2147483647L) {
            mw.writeLong(this.compressionThreshold);
         }
      }

   }

   public void unmarshal(MarshalReader mr) {
      this.versionFlags = new MarshalBitMask();
      this.versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(this.versionFlags.getVersion(), 1, 1);
      this.id = mr.readLong();
      this.deliveryMode = mr.readInt();
      this.priority = mr.readInt();
      this.timeToLive = mr.readLong();
      if (this.versionFlags.isSet(1)) {
         this.timeToDeliver = mr.readLong();
         this.sendTimeOut = mr.readLong();
         if (this.versionFlags.isSet(2)) {
            this.unitOfOrder = mr.readString();
         }

         if (this.versionFlags.isSet(3)) {
            this.compressionThreshold = mr.readLong();
         }
      }

   }
}
