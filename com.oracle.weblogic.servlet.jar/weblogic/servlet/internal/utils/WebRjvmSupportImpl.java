package weblogic.servlet.internal.utils;

import java.net.InetAddress;
import javax.servlet.http.HttpServletRequest;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.runtime.SocketRuntime;
import weblogic.protocol.ProtocolHandler;
import weblogic.protocol.ServerChannel;
import weblogic.rjvm.WebRjvmSupport;
import weblogic.security.service.ContextHandler;
import weblogic.servlet.internal.ProtocolHandlerHTTP;
import weblogic.servlet.internal.ProtocolHandlerHTTPS;
import weblogic.servlet.internal.ServletRequestImpl;

@Service
public class WebRjvmSupportImpl extends WebRjvmSupport {
   public ProtocolHandler getHttpsProtocolHandler() {
      return ProtocolHandlerHTTPS.getProtocolHandler();
   }

   public ProtocolHandler getHttpProtocolHandler() {
      return ProtocolHandlerHTTP.getProtocolHandler();
   }

   public ServerChannel getChannel(HttpServletRequest req) {
      return ((ServletRequestImpl)req).getConnection().getChannel();
   }

   public SocketRuntime getSocketRuntime(HttpServletRequest req) {
      return (SocketRuntime)((ServletRequestImpl)req).getConnection().getConnectionHandler().getRawConnection();
   }

   public ContextHandler getContextHandler(HttpServletRequest req) {
      return ((ServletRequestImpl)req).getConnection().getContextHandler();
   }

   public InetAddress getSocketLocalAddress(HttpServletRequest req) {
      return ((ServletRequestImpl)req).getConnection().getSocket().getLocalAddress();
   }
}
