package weblogic.servlet.internal;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;
import org.jvnet.hk2.annotations.Service;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolHandler;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannel;
import weblogic.server.channels.ServerChannelImpl;
import weblogic.servlet.http2.MuxableSocketHTTP2;
import weblogic.socket.MuxableSocket;
import weblogic.utils.io.Chunk;

@Service(
   name = "http"
)
public class ProtocolHandlerHTTP implements ProtocolHandler {
   private static final ProtocolHandler theOne = new ProtocolHandlerHTTP();
   public static final Protocol PROTOCOL_HTTP = ProtocolManager.createProtocol((byte)1, "http", "http", false, getProtocolHandler());
   private static final byte[] PREFACE_BYTES;

   public static ProtocolHandler getProtocolHandler() {
      return theOne;
   }

   public ServerChannel getDefaultServerChannel() {
      return ProtocolHandlerHTTP.ChannelInitializer.CHANNEL;
   }

   public int getHeaderLength() {
      return 0;
   }

   public int getPriority() {
      return Integer.MAX_VALUE;
   }

   public Protocol getProtocol() {
      return PROTOCOL_HTTP;
   }

   public boolean claimSocket(Chunk head) {
      return true;
   }

   public MuxableSocket createSocket(Chunk head, Socket s, ServerChannel networkChannel) throws IOException {
      if (head.end >= 14) {
         byte[] buf = head.buf;

         for(int i = 0; i < 14; ++i) {
            if (buf[i] != PREFACE_BYTES[i]) {
               return new MuxableSocketHTTP(head, s, false, networkChannel);
            }
         }

         try {
            return new MuxableSocketHTTP2(head, s, networkChannel, (HttpServletRequest)null, (byte[])null, false);
         } catch (Exception var6) {
            throw new IOException(var6);
         }
      } else {
         return new MuxableSocketHTTP(head, s, false, networkChannel);
      }
   }

   static {
      PREFACE_BYTES = "PRI * HTTP/2.0".getBytes(StandardCharsets.ISO_8859_1);
   }

   private static final class ChannelInitializer {
      private static final ServerChannel CHANNEL;

      static {
         CHANNEL = ServerChannelImpl.createDefaultServerChannel(ProtocolHandlerHTTP.PROTOCOL_HTTP);
      }
   }
}
