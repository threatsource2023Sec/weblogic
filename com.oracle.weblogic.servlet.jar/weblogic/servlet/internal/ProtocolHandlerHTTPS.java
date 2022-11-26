package weblogic.servlet.internal;

import java.io.IOException;
import java.net.Socket;
import javax.servlet.http.HttpServletRequest;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolHandler;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannel;
import weblogic.server.channels.ServerChannelImpl;
import weblogic.servlet.http2.MuxableSocketHTTP2;
import weblogic.socket.MuxableSocket;
import weblogic.socket.WeblogicSSLSocket;
import weblogic.utils.io.Chunk;

public class ProtocolHandlerHTTPS extends ProtocolHandlerHTTP {
   private static final ProtocolHandler theOne = new ProtocolHandlerHTTPS();
   private static final String PROTOCOL_NAME = "HTTPS";
   public static final Protocol PROTOCOL_HTTPS = ProtocolManager.createProtocol((byte)3, "https", "https", true, getProtocolHandler());

   public static ProtocolHandler getProtocolHandler() {
      return theOne;
   }

   public Protocol getProtocol() {
      return PROTOCOL_HTTPS;
   }

   public final ServerChannel getDefaultServerChannel() {
      return ProtocolHandlerHTTPS.ChannelInitializer.CHANNEL;
   }

   public MuxableSocket createSocket(Chunk head, Socket s, ServerChannel networkChannel) throws IOException {
      if (s instanceof WeblogicSSLSocket) {
         WeblogicSSLSocket sslSocket = (WeblogicSSLSocket)s;
         if ("h2".equals(sslSocket.getALPNProtocol())) {
            try {
               return new MuxableSocketHTTP2(head, s, networkChannel, (HttpServletRequest)null, (byte[])null, true);
            } catch (Exception var6) {
               throw new IOException(var6);
            }
         }
      }

      return new MuxableSocketHTTP(head, s, true, networkChannel);
   }

   private static final class ChannelInitializer {
      private static final ServerChannel CHANNEL;

      static {
         CHANNEL = ServerChannelImpl.createDefaultServerChannel(ProtocolHandlerHTTPS.PROTOCOL_HTTPS);
      }
   }
}
