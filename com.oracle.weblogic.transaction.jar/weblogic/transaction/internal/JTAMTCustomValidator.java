package weblogic.transaction.internal;

import weblogic.management.configuration.JTAMigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;

public class JTAMTCustomValidator {
   public static void validateUserPreferredServer(JTAMigratableTargetMBean migratableTarget, ServerMBean userPreferredServer) {
      ServerMBean localServer = (ServerMBean)migratableTarget.getParent();
      if (userPreferredServer != null && !localServer.equals(userPreferredServer)) {
         throw new IllegalArgumentException(TXExceptionLogger.logInvalidUserPreferredServerLoggable(localServer.getName(), userPreferredServer.getName()).getMessage());
      }
   }
}
