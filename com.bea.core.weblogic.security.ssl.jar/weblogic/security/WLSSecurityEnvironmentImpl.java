package weblogic.security;

import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import weblogic.logging.LoggingHelper;
import weblogic.socket.ServerThrottle;

public class WLSSecurityEnvironmentImpl extends SecurityEnvironment {
   public Logger getServerLogger() {
      return LoggingHelper.getServerLogger();
   }

   public void decrementOpenSocketCount(SSLSocket sock) {
      ServerThrottle.getServerThrottle().decrementOpenSocketCount();
   }
}
