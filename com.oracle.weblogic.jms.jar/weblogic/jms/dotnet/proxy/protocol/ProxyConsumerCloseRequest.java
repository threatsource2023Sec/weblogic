package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public final class ProxyConsumerCloseRequest extends ProxyRequest {
   private static final int EXTVERSION = 1;
   private static final int _HAS_SEQUENCENUMBER = 2;
   private long sequenceNumber;

   public ProxyConsumerCloseRequest() {
   }

   public ProxyConsumerCloseRequest(long sequenceNumber) {
      this.versionFlags = new MarshalBitMask(1);
      this.sequenceNumber = sequenceNumber;
      if (sequenceNumber != 0L) {
         this.versionFlags.setBit(2);
      }

   }

   public long getSequenceNumber() {
      return this.sequenceNumber;
   }

   public int getMarshalTypeCode() {
      return 12;
   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags.marshal(mw);
      if (this.sequenceNumber != 0L) {
         mw.writeLong(this.sequenceNumber);
      }

   }

   public void unmarshal(MarshalReader mr) {
      this.versionFlags = new MarshalBitMask();
      this.versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(this.versionFlags.getVersion(), 1, 1);
      if (this.versionFlags.isSet(2)) {
         this.sequenceNumber = mr.readLong();
      }

   }
}
