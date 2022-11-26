package weblogic.cluster.migration;

import weblogic.management.configuration.ServerMBean;

public interface MigratableGroupConfig {
   String getName();

   ServerMBean getUserPreferredServer();

   String getPreScript();

   String getPostScript();

   boolean isPostScriptFailureFatal();

   boolean isNonLocalPostAllowed();

   boolean isManualService();

   boolean getRestartOnFailure();

   int getSecondsBetweenRestarts();

   int getNumberOfRestartAttempts();

   boolean isCritical();
}
