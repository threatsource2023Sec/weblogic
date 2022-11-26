package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public final class ProxyConsumerSetListenerRequest extends ProxyRequest {
   private static final int EXTVERSION = 1;
   private static final int _HAS_LISTENER = 1;
   private static final int _HAS_SEQUENCE_NUMBER = 2;
   private boolean hasListener;
   private long sequenceNumber = 0L;
   private long listenerServiceId;

   public ProxyConsumerSetListenerRequest(boolean hasListener, long listenerServiceId, long sequenceNumber) {
      this.hasListener = hasListener;
      this.sequenceNumber = sequenceNumber;
      this.listenerServiceId = listenerServiceId;
   }

   public boolean getHasListener() {
      return this.hasListener;
   }

   public long getSequenceNumber() {
      return this.sequenceNumber;
   }

   public long getListenerServiceId() {
      return this.listenerServiceId;
   }

   public ProxyConsumerSetListenerRequest() {
   }

   public int getMarshalTypeCode() {
      return 18;
   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags = new MarshalBitMask(1);
      if (this.hasListener) {
         this.versionFlags.setBit(1);
      }

      if (this.sequenceNumber != 0L) {
         this.versionFlags.setBit(2);
      }

      this.versionFlags.marshal(mw);
      if (this.hasListener) {
         mw.writeLong(this.listenerServiceId);
      }

      if (this.sequenceNumber != 0L) {
         mw.writeLong(this.sequenceNumber);
      }

   }

   public void unmarshal(MarshalReader mr) {
      this.versionFlags = new MarshalBitMask();
      this.versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(this.versionFlags.getVersion(), 1, 1);
      if (this.versionFlags.isSet(1)) {
         this.hasListener = true;
         this.listenerServiceId = mr.readLong();
      }

      if (this.versionFlags.isSet(2)) {
         this.sequenceNumber = mr.readLong();
      }

   }
}
