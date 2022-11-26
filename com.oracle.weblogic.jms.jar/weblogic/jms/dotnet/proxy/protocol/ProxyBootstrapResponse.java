package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public final class ProxyBootstrapResponse extends ProxyResponse {
   private static final int EXTVERSION = 1;
   private long serviceId;

   public ProxyBootstrapResponse(long id) {
      this.serviceId = id;
   }

   public long getServiceId() {
      return this.serviceId;
   }

   public ProxyBootstrapResponse() {
   }

   public int getMarshalTypeCode() {
      return 42;
   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags = new MarshalBitMask(1);
      this.versionFlags.marshal(mw);
      mw.writeLong(this.serviceId);
   }

   public void unmarshal(MarshalReader mr) {
      this.versionFlags = new MarshalBitMask();
      this.versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(this.versionFlags.getVersion(), 1, 1);
      this.serviceId = mr.readLong();
   }
}
