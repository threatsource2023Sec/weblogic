package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public final class ProxyRemoveSubscriptionRequest extends ProxyRequest {
   private String name;
   private static final int EXTVERSION = 1;

   public ProxyRemoveSubscriptionRequest(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public ProxyRemoveSubscriptionRequest() {
   }

   public int getMarshalTypeCode() {
      return 27;
   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags = new MarshalBitMask(1);
      this.versionFlags.marshal(mw);
      mw.writeString(this.name);
   }

   public void unmarshal(MarshalReader mr) {
      MarshalBitMask versionFlags = new MarshalBitMask();
      versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(versionFlags.getVersion(), 1, 1);
      this.name = mr.readString();
   }
}
