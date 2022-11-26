package weblogic.wtc.corba.internal;

import com.bea.core.jatmi.common.ntrace;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMISocketFactory;
import weblogic.iiop.ProtocolHandlerIIOP;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelManager;

public final class ORBSocketFactory extends RMISocketFactory {
   boolean isWTCObject;

   public ORBSocketFactory() {
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocketFactory");
      }

      this.isWTCObject = true;
      if (traceEnabled) {
         ntrace.doTrace("]/ORBSocketFactory");
      }

   }

   public Socket createSocket(String host, int port) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/ORBSocketFactory/createSocket/" + host + "/" + port);
         ntrace.doTrace("]/ORBSocketFactory/createSocket");
      }

      try {
         ServerChannel sc = ServerChannelManager.findLocalServerChannel(ProtocolHandlerIIOP.PROTOCOL_IIOP);
         String localHost = sc.getPublicAddress();
         int plainPort = sc.getPublicPort();
         if (plainPort == port && localHost != null && host != null && localHost.equals(host)) {
            this.isWTCObject = false;
         }
      } catch (Exception var7) {
      }

      return (Socket)(!this.isWTCObject ? new Socket(host, port) : new ORBSocket(host, port));
   }

   public ServerSocket createServerSocket(int port) throws IOException {
      return new ServerSocket(port);
   }
}
