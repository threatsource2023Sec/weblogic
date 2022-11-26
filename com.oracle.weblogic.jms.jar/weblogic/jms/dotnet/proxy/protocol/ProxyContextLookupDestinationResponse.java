package weblogic.jms.dotnet.proxy.protocol;

import javax.jms.Destination;
import javax.jms.JMSException;
import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public class ProxyContextLookupDestinationResponse extends ProxyResponse {
   private static final int EXTVERSION = 1;
   private ProxyDestinationImpl destination;

   public ProxyContextLookupDestinationResponse(Destination dest) throws JMSException {
      this.destination = new ProxyDestinationImpl(dest);
   }

   public final String getName() {
      return this.destination.getName();
   }

   public final ProxyDestinationImpl getDestination() {
      return this.destination;
   }

   public ProxyContextLookupDestinationResponse() {
   }

   public int getMarshalTypeCode() {
      return 6;
   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags = new MarshalBitMask(1);
      this.versionFlags.marshal(mw);
      this.destination.marshal(mw);
   }

   public void unmarshal(MarshalReader mr) {
      this.versionFlags = new MarshalBitMask();
      this.versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(this.versionFlags.getVersion(), 1, 1);
      this.destination = new ProxyDestinationImpl();
      this.destination.unmarshal(mr);
   }
}
