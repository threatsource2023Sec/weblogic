package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public final class ProxyContextCreateRequest extends ProxyRequest {
   private static final int EXTVERSION = 1;
   private PrimitiveMap env;

   public ProxyContextCreateRequest(PrimitiveMap env) {
      this.env = env;
   }

   public PrimitiveMap getEnvironment() {
      return this.env;
   }

   public ProxyContextCreateRequest() {
   }

   public int getMarshalTypeCode() {
      return 1;
   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags = new MarshalBitMask(1);
      this.versionFlags.marshal(mw);
      this.env.marshal(mw);
   }

   public void unmarshal(MarshalReader mr) {
      this.versionFlags = new MarshalBitMask();
      this.versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(this.versionFlags.getVersion(), 1, 1);
      this.env = new PrimitiveMap();
      this.env.unmarshal(mr);
   }
}
