package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public final class ProxySessionRecoverRequest extends ProxyRequest {
   private static final int EXTVERSION = 1;
   private static final int _IS_DO_ROLLBACK = 1;
   private static final int _HAS_SEQUENCENUMBER = 2;
   private boolean doRollback;
   private long sequenceNumber;

   public ProxySessionRecoverRequest(long sequenceNumber, boolean doRollback) {
      this.sequenceNumber = sequenceNumber;
      this.doRollback = doRollback;
   }

   public final boolean isDoRollback() {
      return this.doRollback;
   }

   public final long getSequenceNumber() {
      return this.sequenceNumber;
   }

   public ProxySessionRecoverRequest() {
   }

   public int getMarshalTypeCode() {
      return 32;
   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags = new MarshalBitMask(1);
      if (this.doRollback) {
         this.versionFlags.setBit(1);
      }

      if (this.sequenceNumber != 0L) {
         this.versionFlags.setBit(2);
      }

      this.versionFlags.marshal(mw);
      if (this.sequenceNumber != 0L) {
         mw.writeLong(this.sequenceNumber);
      }

   }

   public void unmarshal(MarshalReader mr) {
      MarshalBitMask versionFlags = new MarshalBitMask();
      versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(versionFlags.getVersion(), 1, 1);
      this.doRollback = versionFlags.isSet(1);
      if (versionFlags.isSet(2)) {
         this.sequenceNumber = mr.readLong();
      }

   }
}
