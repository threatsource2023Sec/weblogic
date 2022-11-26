package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public class ProxyContextCreateResponse extends ProxyResponse {
   private static final int EXTVERSION = 1;
   private long ctxId;

   public ProxyContextCreateResponse(long ctxId) {
      this.ctxId = ctxId;
   }

   public long getContextId() {
      return this.ctxId;
   }

   public ProxyContextCreateResponse() {
   }

   public int getMarshalTypeCode() {
      return 2;
   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags = new MarshalBitMask(1);
      this.versionFlags.marshal(mw);
      mw.writeLong(this.ctxId);
   }

   public void unmarshal(MarshalReader mr) {
      this.versionFlags = new MarshalBitMask();
      this.versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(this.versionFlags.getVersion(), 1, 1);
      this.ctxId = mr.readLong();
   }
}
