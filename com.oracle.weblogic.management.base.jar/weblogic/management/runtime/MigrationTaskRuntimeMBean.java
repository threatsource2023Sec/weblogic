package weblogic.management.runtime;

import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;

public interface MigrationTaskRuntimeMBean extends TaskRuntimeMBean {
   int STATUS_INPROGRESS = 0;
   int STATUS_DONE = 1;
   int STATUS_FAILED = 2;
   int STATUS_AWAITING_IS_SOURCE_DOWN = 3;
   int STATUS_AWAITING_IS_DESTINATION_DOWN = 4;
   int STATUS_CANCELED = 5;

   int getStatusCode();

   boolean isRunning();

   boolean isTerminal();

   boolean isWaitingForUser();

   void continueWithSourceServerDown(boolean var1);

   void continueWithDestinationServerDown(boolean var1);

   ServerMBean getSourceServer();

   ServerMBean getDestinationServer();

   MigratableTargetMBean getMigratableTarget();

   boolean isJTA();
}
