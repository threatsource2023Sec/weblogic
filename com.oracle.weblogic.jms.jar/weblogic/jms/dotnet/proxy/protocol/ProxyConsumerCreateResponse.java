package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public class ProxyConsumerCreateResponse extends ProxyResponse {
   private static final int EXTVERSION = 1;
   private long id;

   public ProxyConsumerCreateResponse(long id) {
      this.id = id;
   }

   public long getConsumerId() {
      return this.id;
   }

   public ProxyConsumerCreateResponse() {
   }

   public int getMarshalTypeCode() {
      return 14;
   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags = new MarshalBitMask(1);
      this.versionFlags.marshal(mw);
      mw.writeLong(this.id);
   }

   public void unmarshal(MarshalReader mr) {
      this.versionFlags = new MarshalBitMask();
      this.versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(this.versionFlags.getVersion(), 1, 1);
      this.id = mr.readLong();
   }
}
