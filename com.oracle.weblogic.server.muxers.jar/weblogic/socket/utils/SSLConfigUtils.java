package weblogic.socket.utils;

import javax.net.ssl.SSLEngine;
import weblogic.kernel.Kernel;
import weblogic.socket.SocketLogger;

public class SSLConfigUtils {
   private static final String TLS_REJECT_CLIENT_INIT_RENEGOTIATION = "jdk.tls.rejectClientInitiatedRenegotiation";
   private static final boolean IS_JDK_CLIENT_INIT_SECURE_RENEGOTIATION_PROPERTY_SET = System.getProperty("jdk.tls.rejectClientInitiatedRenegotiation") != null;

   public static void configureClientInitSecureRenegotiation(SSLEngine sslEngine, boolean clientInitSecureRenegotiation) {
      if (!IS_JDK_CLIENT_INIT_SECURE_RENEGOTIATION_PROPERTY_SET) {
         if (sslEngine != null && !sslEngine.getUseClientMode()) {
            if (!clientInitSecureRenegotiation) {
               sslEngine.getSession().invalidate();
            }

            sslEngine.setEnableSessionCreation(clientInitSecureRenegotiation);
            if (isLoggable()) {
               SocketLogger.logDebug(clientInitSecureRenegotiation ? "Enabled" : "Disabled TLS client initiated secure renegotiation.");
            }
         }
      } else if (isLoggable()) {
         SocketLogger.logDebug("TLS client initiated secure renegotiation setting is configured with -Djdk.tls.rejectClientInitiatedRenegotiation");
      }

   }

   private static boolean isLoggable() {
      return Kernel.DEBUG && Kernel.getDebug().getDebugMuxer();
   }
}
