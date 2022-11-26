package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public final class ProxyContextLookupDestinationRequest extends ProxyRequest {
   private static final int EXTVERSION = 1;
   private String jndiName;

   public ProxyContextLookupDestinationRequest(String name) {
      this.jndiName = name;
   }

   public String getJNDIName() {
      return this.jndiName;
   }

   public ProxyContextLookupDestinationRequest() {
   }

   public int getMarshalTypeCode() {
      return 5;
   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags = new MarshalBitMask(1);
      this.versionFlags.marshal(mw);
      mw.writeString(this.jndiName);
   }

   public void unmarshal(MarshalReader mr) {
      this.versionFlags = new MarshalBitMask();
      this.versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(this.versionFlags.getVersion(), 1, 1);
      this.jndiName = mr.readString();
   }
}
