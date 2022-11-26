package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public final class ProxyProducerCreateRequest extends ProxyRequest {
   private static final int EXTVERSION = 1;
   private static final int _HAS_DESTINATION = 1;
   ProxyDestinationImpl destination;

   public ProxyProducerCreateRequest(ProxyDestinationImpl destination) {
      this.destination = destination;
   }

   public final ProxyDestinationImpl getDestination() {
      return this.destination;
   }

   public ProxyProducerCreateRequest() {
   }

   public int getMarshalTypeCode() {
      return 23;
   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags = new MarshalBitMask(1);
      if (this.destination != null) {
         this.versionFlags.setBit(1);
      }

      this.versionFlags.marshal(mw);
      if (this.destination != null) {
         this.destination.marshal(mw);
      }

   }

   public void unmarshal(MarshalReader mr) {
      this.versionFlags = new MarshalBitMask();
      this.versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(this.versionFlags.getVersion(), 1, 1);
      if (this.versionFlags.isSet(1)) {
         this.destination = new ProxyDestinationImpl();
         this.destination.unmarshal(mr);
      }

   }
}
