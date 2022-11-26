package weblogic.iiop;

import java.io.IOException;
import java.net.InetAddress;
import org.glassfish.hk2.api.Rank;
import org.jvnet.hk2.annotations.Service;
import weblogic.iiop.ior.IOR;
import weblogic.protocol.Protocol;
import weblogic.protocol.ServerChannel;
import weblogic.socket.SocketMuxer;

@Service
@Rank(10)
public class IIOPConnectionFactory implements ConnectionFactory {
   public boolean claimsIOR(IOR ior) {
      return !ior.isSecure();
   }

   public Protocol getProtocol() {
      return ProtocolHandlerIIOP.PROTOCOL_IIOP;
   }

   public Connection createConnection(IOR ior, ServerChannel serverChannel) throws IOException {
      return this.createMuxable(ior.getProfile().getHostAddress(), ior.getProfile().getPort(), serverChannel).getConnection();
   }

   private MuxableSocketIIOP createMuxable(InetAddress address, int port, ServerChannel serverChannel) throws IOException {
      MuxableSocketIIOP muxable = new MuxableSocketIIOP(serverChannel);
      muxable.connect(address, port);
      SocketMuxer.getMuxer().register(muxable);
      SocketMuxer.getMuxer().read(muxable);
      return muxable;
   }
}
