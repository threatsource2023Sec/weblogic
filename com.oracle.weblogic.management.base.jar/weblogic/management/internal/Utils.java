package weblogic.management.internal;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;

public final class Utils {
   private static final String DEFAULT_SERVER = "myserver";
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationRuntime");

   public static String extractDomain(String fullWebLogicObjectName) {
      int idx = fullWebLogicObjectName.indexOf(":");
      if (idx < 1) {
         return null;
      } else {
         String result = fullWebLogicObjectName.substring(0, idx);
         return result.equals("null") ? null : result;
      }
   }

   public static String findServerName(DomainMBean domain) {
      String serverName = System.getProperty("weblogic.Name");
      if ("".equals(serverName)) {
         serverName = null;
      }

      if (serverName == null) {
         ServerMBean[] servers = domain.getServers();
         if (servers.length == 1) {
            serverName = servers[0].getName();
         } else {
            String defaultServerName = domain.getAdminServerName();
            if (defaultServerName == null || defaultServerName.length() == 0) {
               defaultServerName = "myserver";
            }

            for(int i = 0; i < servers.length; ++i) {
               if (servers[i].getName().equals(defaultServerName)) {
                  serverName = defaultServerName;
               }
            }
         }
      }

      return serverName;
   }
}
