package weblogic.security.SSL.jsseadapter;

import java.io.IOException;
import java.net.Socket;

final class JaLayeredSSLSocket extends JaAbstractLayeredSSLSocket {
   JaLayeredSSLSocket(Socket socket, JaSSLContext jaSSLContext, JaSSLParameters sslParameters, boolean autoClose) throws IOException {
      super(socket, jaSSLContext, autoClose);
      this.init(sslParameters, socket.getInputStream(), socket.getOutputStream());
   }
}
