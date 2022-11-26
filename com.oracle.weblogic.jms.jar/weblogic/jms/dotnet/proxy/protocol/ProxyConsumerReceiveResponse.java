package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public class ProxyConsumerReceiveResponse extends ProxyResponse {
   private static final int EXTVERSION = 1;
   private static final int _HAS_MESSAGE = 1;
   private ProxyMessageImpl message;

   public ProxyConsumerReceiveResponse(ProxyMessageImpl message) {
      this.message = message;
   }

   public final ProxyMessageImpl getMessage() {
      return this.message;
   }

   public ProxyConsumerReceiveResponse() {
   }

   public int getMarshalTypeCode() {
      return 17;
   }

   public void marshal(MarshalWriter mw) {
      MarshalBitMask versionFlags = new MarshalBitMask(1);
      if (this.message != null) {
         versionFlags.setBit(1);
      }

      versionFlags.marshal(mw);
      if (this.message != null) {
         mw.writeByte(this.message.getType());
         this.message.marshal(mw);
      }

   }

   public void unmarshal(MarshalReader mr) {
      MarshalBitMask versionFlags = new MarshalBitMask();
      versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(versionFlags.getVersion(), 1, 1);
      if (versionFlags.isSet(1)) {
         byte type = mr.readByte();
         this.message = ProxyMessageImpl.createMessageImpl(type);
         this.message.unmarshal(mr);
      }

   }
}
