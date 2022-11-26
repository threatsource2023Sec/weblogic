package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.dotnet.proxy.internal.ConsumerProxy;
import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;
import weblogic.jms.dotnet.transport.ReceivedTwoWay;
import weblogic.messaging.dispatcher.CompletionListener;

public final class ProxyConsumerReceiveRequest extends ProxyRequest implements CompletionListener {
   private static final int EXTVERSION = 1;
   private static final int _HAS_TIMEOUT = 1;
   private static final int _IS_TIMEOUT_NEVER = 2;
   private static final int _IS_TIMEOUT_NO_WAIT = 3;
   private long timeout;
   private transient ConsumerProxy consumerProxy;
   private transient ReceivedTwoWay receivedTwoWay;

   public ProxyConsumerReceiveRequest(long timeout) {
      this.timeout = timeout;
   }

   public ProxyConsumerReceiveRequest() {
   }

   public long getTimeout() {
      return this.timeout;
   }

   public ConsumerProxy getConsumerProxy() {
      return this.consumerProxy;
   }

   public void setConsumerProxy(ConsumerProxy consumerProxy) {
      this.consumerProxy = consumerProxy;
   }

   public ReceivedTwoWay getReceivedTwoWay() {
      return this.receivedTwoWay;
   }

   public void setReceivedTwoWay(ReceivedTwoWay receivedTwoWay) {
      this.receivedTwoWay = receivedTwoWay;
   }

   public void onCompletion(Object result) {
      this.consumerProxy.receiveCompletion(result, this.receivedTwoWay);
   }

   public void onException(Throwable throwable) {
      this.consumerProxy.receiveException(throwable, this.receivedTwoWay);
   }

   public int getMarshalTypeCode() {
      return 16;
   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags = new MarshalBitMask(1);
      if (this.timeout == 0L) {
         this.versionFlags.setBit(3);
      } else if (this.timeout == -1L) {
         this.versionFlags.setBit(2);
      } else {
         this.versionFlags.setBit(1);
      }

      this.versionFlags.marshal(mw);
      if (this.versionFlags.isSet(1)) {
         mw.writeLong(this.timeout);
      }

   }

   public void unmarshal(MarshalReader mr) {
      this.versionFlags = new MarshalBitMask();
      this.versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(this.versionFlags.getVersion(), 1, 1);
      if (this.versionFlags.isSet(1)) {
         this.timeout = mr.readLong();
      }

      if (this.versionFlags.isSet(2)) {
         this.timeout = -1L;
      }

      if (this.versionFlags.isSet(3)) {
         this.timeout = 0L;
      }

   }
}
