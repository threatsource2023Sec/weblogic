package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public final class ProxySessionCloseRequest extends ProxyRequest {
   private static final int EXTVERSION = 1;
   private static final int _HAS_SEQUENCENUMBER = 1;
   private long sequenceNumber;

   public ProxySessionCloseRequest(long sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
   }

   public ProxySessionCloseRequest() {
   }

   public long getSequenceNumber() {
      return this.sequenceNumber;
   }

   public int getMarshalTypeCode() {
      return 29;
   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags = new MarshalBitMask(1);
      if (this.sequenceNumber != 0L) {
         this.versionFlags.setBit(1);
      }

      this.versionFlags.marshal(mw);
      if (this.sequenceNumber != 0L) {
         mw.writeLong(this.sequenceNumber);
      }

   }

   public void unmarshal(MarshalReader mr) {
      this.versionFlags = new MarshalBitMask();
      this.versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(this.versionFlags.getVersion(), 1, 1);
      if (this.versionFlags.isSet(1)) {
         this.sequenceNumber = mr.readLong();
      }

   }
}
