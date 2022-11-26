package weblogic.nodemanager.grizzly.portunif;

import org.glassfish.grizzly.filterchain.FilterChain;
import org.glassfish.grizzly.filterchain.FilterChainBuilder;
import org.glassfish.grizzly.filterchain.TransportFilter;
import org.glassfish.grizzly.http.server.AddOn;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.portunif.PUFilter;
import org.glassfish.grizzly.portunif.PUProtocol;
import org.glassfish.grizzly.portunif.ProtocolFinder;
import org.glassfish.grizzly.ssl.SSLBaseFilter;
import weblogic.nodemanager.server.NMServer;

public class PortUnificationAddOn implements AddOn {
   private NMServer nmServer;

   public PortUnificationAddOn(NMServer nmServer) {
      this.nmServer = nmServer;
   }

   public void setup(NetworkListener networkListener, FilterChainBuilder builder) {
      PUFilter puFilter = new PUFilter(false);
      PUProtocol nm = configureNMProtocol(networkListener, puFilter, this.nmServer);
      puFilter.register(nm);
      boolean ssl = this.nmServer.getConfig().isSecureListener();
      int index = builder.indexOfType(ssl ? SSLBaseFilter.class : TransportFilter.class);

      assert index != -1;

      builder.add(index + 1, puFilter);
   }

   static PUProtocol configureNMProtocol(NetworkListener networkListener, PUFilter puFilter, NMServer nmServer) {
      ProtocolFinder nmTcpProtoFinder = new NMProtoFinder();
      FilterChain nmProtocolFilterChain = puFilter.getPUFilterChainBuilder().add(new NMProtoWriteFilter()).add(new NMProtoReadFilter(nmServer)).build();
      return new PUProtocol(nmTcpProtoFinder, nmProtocolFilterChain);
   }
}
