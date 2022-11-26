package weblogic.socket;

import javax.net.SocketFactory;
import weblogic.protocol.ServerChannel;
import weblogic.utils.io.Chunk;

public abstract class SSLAbstractMuxableSocket extends BaseAbstractMuxableSocket {
   protected SSLAbstractMuxableSocket(Chunk headChunk, ServerChannel networkChannel) {
      super(headChunk, networkChannel);
      this.socketFactory = (SocketFactory)(networkChannel.supportsTLS() ? new ChannelSSLSocketFactory(this.channel) : new ChannelSocketFactory(this.channel));
   }

   protected static void p(String msg) {
      System.out.println("<SSLAbstractMuxableSocket>: " + msg);
   }
}
