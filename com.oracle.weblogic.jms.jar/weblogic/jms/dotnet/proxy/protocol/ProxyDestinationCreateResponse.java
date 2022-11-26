package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public final class ProxyDestinationCreateResponse extends ProxyResponse {
   private static final int EXTVERSION = 1;
   private static final int _HAS_DESTINATION = 1;
   private ProxyDestinationImpl destination;

   public ProxyDestinationCreateResponse(ProxyDestinationImpl destination) {
      this.destination = destination;
   }

   public final ProxyDestinationImpl getDestination() {
      return this.destination;
   }

   public ProxyDestinationCreateResponse() {
   }

   public int getMarshalTypeCode() {
      return 21;
   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags = new MarshalBitMask(1);
      if (this.destination != null) {
         this.versionFlags.setBit(1);
      }

      this.versionFlags.marshal(mw);
      this.destination.marshal(mw);
   }

   public void unmarshal(MarshalReader mr) {
      MarshalBitMask versionFlags = new MarshalBitMask();
      versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(versionFlags.getVersion(), 1, 1);
      if (versionFlags.isSet(1)) {
         this.destination = new ProxyDestinationImpl();
         this.destination.unmarshal(mr);
      }

   }
}
