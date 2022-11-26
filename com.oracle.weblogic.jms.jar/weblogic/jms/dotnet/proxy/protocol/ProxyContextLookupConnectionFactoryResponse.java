package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public class ProxyContextLookupConnectionFactoryResponse extends ProxyResponse {
   private static final int EXTVERSION = 1;
   private long connectionFactoryId;

   public ProxyContextLookupConnectionFactoryResponse(long connectionFactoryId) {
      this.connectionFactoryId = connectionFactoryId;
   }

   public final long getConnectionFactoryId() {
      return this.connectionFactoryId;
   }

   public ProxyContextLookupConnectionFactoryResponse() {
   }

   public int getMarshalTypeCode() {
      return 4;
   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags = new MarshalBitMask(1);
      this.versionFlags.marshal(mw);
      mw.writeLong(this.connectionFactoryId);
   }

   public void unmarshal(MarshalReader mr) {
      this.versionFlags = new MarshalBitMask();
      this.versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(this.versionFlags.getVersion(), 1, 1);
      this.connectionFactoryId = mr.readLong();
   }
}
