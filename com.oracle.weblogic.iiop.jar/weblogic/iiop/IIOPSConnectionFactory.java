package weblogic.iiop;

import java.io.IOException;
import java.net.InetAddress;
import org.glassfish.hk2.api.Rank;
import org.jvnet.hk2.annotations.Service;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.ior.InetAddressHelper;
import weblogic.protocol.Protocol;
import weblogic.protocol.ServerChannel;

@Service
@Rank(20)
public class IIOPSConnectionFactory implements ConnectionFactory {
   public boolean claimsIOR(IOR ior) {
      return ior.isSecure();
   }

   public Protocol getProtocol() {
      return ProtocolHandlerIIOPS.PROTOCOL_IIOPS;
   }

   public Connection createConnection(IOR ior, ServerChannel serverChannel) throws IOException {
      String host = ior.getProfile().getSecureHost();
      if (host == null) {
         host = ior.getProfile().getHost();
      }

      return this.createMuxable(InetAddressHelper.getByName(host), ior.getProfile().getSecurePort(), serverChannel).getConnection();
   }

   private MuxableSocketIIOP createMuxable(InetAddress address, int port, ServerChannel serverChannel) throws IOException {
      MuxableSocketIIOPS muxable = new MuxableSocketIIOPS(serverChannel);
      muxable.connect(address, port);
      muxable.registerClientSocket(muxable.getSocket());
      return muxable;
   }
}
