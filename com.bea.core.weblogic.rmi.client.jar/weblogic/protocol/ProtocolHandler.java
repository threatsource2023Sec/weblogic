package weblogic.protocol;

import java.io.IOException;
import java.net.Socket;
import org.jvnet.hk2.annotations.Contract;
import weblogic.socket.MuxableSocket;
import weblogic.utils.io.Chunk;

@Contract
public interface ProtocolHandler {
   ServerChannel getDefaultServerChannel();

   int getHeaderLength();

   int getPriority();

   boolean claimSocket(Chunk var1);

   MuxableSocket createSocket(Chunk var1, Socket var2, ServerChannel var3) throws IOException;

   Protocol getProtocol();
}
