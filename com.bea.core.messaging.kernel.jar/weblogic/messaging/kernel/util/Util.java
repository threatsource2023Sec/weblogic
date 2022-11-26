package weblogic.messaging.kernel.util;

import java.util.HashSet;
import weblogic.messaging.MessagingLogger;

public class Util {
   private static final HashSet loggedSAFAgents = new HashSet();

   public static void logSAFUpgradeWarning(String agentConfigName) {
      synchronized(loggedSAFAgents) {
         if (!loggedSAFAgents.contains(agentConfigName)) {
            loggedSAFAgents.add(agentConfigName);
            MessagingLogger.logWarningSAFStoreUpgradeConflict(agentConfigName);
         }
      }
   }
}
