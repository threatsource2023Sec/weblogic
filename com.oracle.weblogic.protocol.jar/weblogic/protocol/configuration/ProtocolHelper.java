package weblogic.protocol.configuration;

import weblogic.protocol.ProtocolManager;

public class ProtocolHelper {
   private static final String INTERNAL_WEB_APP_CONTEXT_PATH = "/bea_wls_internal";

   public static String[] getSupportedProtocols() {
      return ProtocolManager.getProtocols();
   }

   public static final String getInternalWebAppContextPath() {
      return "/bea_wls_internal";
   }

   public static String getCodebase(boolean secure, String address, int port, String appname) {
      StringBuilder sb = new StringBuilder(secure ? "https://" : "http://");
      sb.append(address).append(":").append(port);
      if (!getInternalWebAppContextPath().startsWith("/")) {
         sb.append('/');
      }

      sb.append(getInternalWebAppContextPath()).append("/classes/");
      if (appname != null) {
         sb.append(appname).append('/');
      }

      return sb.toString();
   }
}
