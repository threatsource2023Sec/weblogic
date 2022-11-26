package weblogic.servlet.internal.utils;

import javax.servlet.http.HttpServletRequest;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.ClusterMemberInfo;
import weblogic.corba.iiop.http.ServletTunnelingSupport;
import weblogic.management.runtime.SocketRuntime;
import weblogic.protocol.ServerChannel;
import weblogic.servlet.internal.ServerHelper;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.VirtualConnection;

@Service
public class ServletTunnelingSupportImpl extends ServletTunnelingSupport {
   public String getServerEntry(HttpServletRequest req, ClusterMemberInfo memberInfo) {
      return ServerHelper.createServerEntry(this.getServerChannel(req).getChannelName(), memberInfo.identity(), "!", false);
   }

   public String getProtocolName(HttpServletRequest req) {
      return this.getServerChannel(req).getProtocol().getProtocolName();
   }

   private ServerChannel getServerChannel(HttpServletRequest req) {
      return ((ServletRequestImpl)req).getServerChannel();
   }

   public ServerChannel getChannel(HttpServletRequest req) {
      return this.getConnection(req).getChannel();
   }

   protected VirtualConnection getConnection(HttpServletRequest req) {
      return ((ServletRequestImpl)req).getConnection();
   }

   public SocketRuntime getSocketRuntime(HttpServletRequest req) {
      return (SocketRuntime)this.getConnection(req).getConnectionHandler().getRawConnection();
   }

   public String getHostAddress(HttpServletRequest req) {
      return this.getConnection(req).getSocket().getInetAddress().getHostAddress();
   }
}
