package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public final class ProxyConnectionSetClientIdRequest extends ProxyRequest {
   private static final int EXTVERSION = 1;
   private static final int _HAS_CLIENT_ID = 1;
   private String clientId;

   public ProxyConnectionSetClientIdRequest(String clientId) {
      this.clientId = clientId;
   }

   public final String getClientId() {
      return this.clientId;
   }

   public ProxyConnectionSetClientIdRequest() {
   }

   public int getMarshalTypeCode() {
      return 11;
   }

   public void unmarshal(MarshalReader mr) {
      this.versionFlags = new MarshalBitMask();
      this.versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(this.versionFlags.getVersion(), 1, 1);
      if (this.versionFlags.isSet(1)) {
         this.clientId = mr.readString();
      }

   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags = new MarshalBitMask(1);
      if (this.clientId != null) {
         this.versionFlags.setBit(1);
      }

      this.versionFlags.marshal(mw);
      if (this.versionFlags.isSet(1)) {
         mw.writeString(this.clientId);
      }

   }
}
