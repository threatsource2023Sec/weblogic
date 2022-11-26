package weblogic.security.SSL;

import weblogic.security.SecurityLogger;
import weblogic.security.utils.SSLSetupLogging;

public class SSLEnabledProtocolVersionsLogging implements SSLEnabledProtocolVersions.LogListener {
   public boolean isDebugEnabled() {
      return SSLSetupLogging.isDebugEnabled();
   }

   public void debug(String message, Throwable e) {
      if (null == e) {
         SSLSetupLogging.debug(0, message);
      } else {
         SSLSetupLogging.debug(0, e, message);
      }

   }

   public void logUnsupportedMinimumProtocolVersion(String requestedMinVersion, String actualMinVersion) {
      SecurityLogger.logUnsupportedSSLMinimumProtocolVersion(requestedMinVersion, actualMinVersion);
   }
}
