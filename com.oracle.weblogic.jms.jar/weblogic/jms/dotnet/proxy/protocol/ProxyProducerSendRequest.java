package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.dotnet.proxy.internal.ProducerProxy;
import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;
import weblogic.jms.dotnet.transport.ReceivedTwoWay;
import weblogic.messaging.dispatcher.CompletionListener;

public final class ProxyProducerSendRequest extends ProxyRequest implements CompletionListener {
   private static final int EXTVERSION = 1;
   private static final int _HAS_TIMEOUT = 1;
   private static final int _HAS_DESTINATION = 2;
   private ProxyMessageImpl message;
   private ProxyDestinationImpl destination;
   private long sendTimeout = -1L;
   private boolean noResponse;
   private transient ProducerProxy producerProxy;
   private transient ReceivedTwoWay receivedTwoWay;

   public ProxyProducerSendRequest(ProxyMessageImpl message, ProxyDestinationImpl destination, long sendTimeout, int compressionThreshold, boolean noResponse) {
      this.message = message;
      this.destination = destination;
      this.sendTimeout = sendTimeout;
      this.noResponse = noResponse;
   }

   public boolean isNoResponse() {
      return this.noResponse;
   }

   public ProxyMessageImpl getMessage() {
      return this.message;
   }

   public ProxyDestinationImpl getDestination() {
      return this.destination;
   }

   public long getSendTimeout() {
      return this.sendTimeout;
   }

   public void setProducerProxy(ProducerProxy producerProxy) {
      this.producerProxy = producerProxy;
   }

   public ReceivedTwoWay getReceivedTwoWay() {
      return this.receivedTwoWay;
   }

   public void setReceivedTwoWay(ReceivedTwoWay receivedTwoWay) {
      this.receivedTwoWay = receivedTwoWay;
   }

   public void onCompletion(Object result) {
      this.producerProxy.receiveCompletion(this, result, this.receivedTwoWay);
   }

   public void onException(Throwable throwable) {
      this.producerProxy.receiveException(throwable, this.receivedTwoWay);
   }

   public ProxyProducerSendRequest() {
   }

   public int getMarshalTypeCode() {
      return 25;
   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags = new MarshalBitMask(1);
      if (this.sendTimeout != -1L) {
         this.versionFlags.setBit(1);
      }

      if (this.destination != null) {
         this.versionFlags.setBit(2);
      }

      this.versionFlags.marshal(mw);
      mw.writeByte(this.message.getType());
      this.message.marshal(mw);
      if (this.destination != null) {
         this.destination.marshal(mw);
      }

      if (this.sendTimeout != -1L) {
         mw.writeLong(this.sendTimeout);
      }

   }

   public void unmarshal(MarshalReader mr) {
      this.versionFlags = new MarshalBitMask();
      this.versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(this.versionFlags.getVersion(), 1, 1);
      byte messageType = mr.readByte();
      this.message = ProxyMessageImpl.createMessageImpl(messageType);
      this.message.unmarshal(mr);
      if (this.versionFlags.isSet(2)) {
         this.destination = new ProxyDestinationImpl();
         this.destination.unmarshal(mr);
      }

      if (this.versionFlags.isSet(1)) {
         this.sendTimeout = mr.readLong();
      }

   }
}
