package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public class ProxyConnectionCreateResponse extends ProxyResponse {
   private static final int EXTVERSION = 1;
   private static final int _HAS_CLIENT_ID = 1;
   private long connectionId;
   private String clientId;
   private int acknowledgePolicy;
   private ProxyConnectionMetaDataImpl metadata;

   public ProxyConnectionCreateResponse(long connectionId, String clientId, int acknowledgePolicy, ProxyConnectionMetaDataImpl metadata) {
      this.connectionId = connectionId;
      this.clientId = clientId;
      this.acknowledgePolicy = acknowledgePolicy;
      this.metadata = metadata;
   }

   public long getConnectionId() {
      return this.connectionId;
   }

   public ProxyConnectionCreateResponse() {
   }

   public int getMarshalTypeCode() {
      return 10;
   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags = new MarshalBitMask(1);
      if (this.clientId != null) {
         this.versionFlags.setBit(1);
      }

      this.versionFlags.marshal(mw);
      mw.writeLong(this.connectionId);
      if (this.clientId != null) {
         mw.writeString(this.clientId);
      }

      mw.writeInt(this.acknowledgePolicy);
      this.metadata.marshal(mw);
   }

   public void unmarshal(MarshalReader mr) {
      this.versionFlags = new MarshalBitMask();
      this.versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(this.versionFlags.getVersion(), 1, 1);
      this.connectionId = mr.readLong();
      if (this.versionFlags.isSet(1)) {
         this.clientId = mr.readString();
      }

      this.acknowledgePolicy = mr.readInt();
      this.metadata = new ProxyConnectionMetaDataImpl();
      this.metadata.unmarshal(mr);
   }
}
