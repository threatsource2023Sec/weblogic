package weblogic.socket;

import java.net.Socket;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;

public interface SNISecureConfigFactory {
   SNISecureConfigFactory NULL_FACTORY = new SNISecureConfigFactory() {
      public SSLEngine createSSLEngine(Socket socket, String sniHost) throws SSLException {
         return null;
      }
   };

   SSLEngine createSSLEngine(Socket var1, String var2) throws SSLException;
}
