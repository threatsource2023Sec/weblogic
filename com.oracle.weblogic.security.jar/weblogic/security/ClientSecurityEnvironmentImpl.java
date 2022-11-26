package weblogic.security;

import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;

public class ClientSecurityEnvironmentImpl extends SecurityEnvironment {
   public Logger getServerLogger() {
      return null;
   }

   public void decrementOpenSocketCount(SSLSocket sock) {
   }
}
