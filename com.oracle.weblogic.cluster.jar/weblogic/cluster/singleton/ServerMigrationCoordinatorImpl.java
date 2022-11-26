package weblogic.cluster.singleton;

import java.security.AccessController;
import java.util.Map;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ServerLifeCycleRuntimeMBean;
import weblogic.management.runtime.ServerLifeCycleTaskRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServerLifecycleException;
import weblogic.utils.collections.ConcurrentHashMap;

public final class ServerMigrationCoordinatorImpl implements ServerMigrationCoordinator, MigratableServiceConstants {
   private final Map taskMap = new ConcurrentHashMap();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void migrate(String serverName, String sourceMachine, String destinationMachine, boolean sourceDown, boolean destinationDown) throws ServerMigrationException {
      this.migrate(serverName, sourceMachine, destinationMachine, sourceDown, destinationDown, ServerMigrationCoordinatorImpl.StartingServerState.RUNNING);
   }

   public void migrate(String serverName, String sourceMachine, String destinationMachine, boolean sourceDown, boolean destinationDown, String startingState) throws ServerMigrationException {
      this.migrate(serverName, sourceMachine, destinationMachine, sourceDown, destinationDown, ServerMigrationCoordinatorImpl.StartingServerState.valueOf(startingState));
   }

   public void migrate(String serverName, String sourceMachine, String destinationMachine, boolean sourceDown, boolean destinationDown, StartingServerState startingState) throws ServerMigrationException {
      ServerMigrationTask task = (ServerMigrationTask)this.taskMap.get(serverName);
      if (task == null) {
         task = new ServerMigrationTask(serverName, destinationMachine);
         if (MigrationDebugLogger.isDebugEnabled()) {
            MigrationDebugLogger.debug(serverName + " New Migration Task " + task);
         }

         this.taskMap.put(serverName, task);

         try {
            this.stopServer(sourceDown, task);
            this.startServer(destinationDown, task, startingState);
         } finally {
            this.taskMap.remove(serverName);
         }

      } else {
         throw new ServerMigrationException("Migration operation in progress", (Throwable)null);
      }
   }

   private void stopServer(boolean sourceDown, ServerMigrationTask task) throws ServerMigrationException {
      try {
         task.stopMigratableServer();
      } catch (ServerLifecycleException var4) {
         if (MigrationDebugLogger.isDebugEnabled()) {
            MigrationDebugLogger.debug("Task failed", var4);
         }

         if (!sourceDown) {
            throw new ServerMigrationException("Migration failed trying to stop the server", var4);
         }
      }

      if (MigrationDebugLogger.isDebugEnabled()) {
         MigrationDebugLogger.debug("Stopped server");
      }

   }

   private void startServer(boolean destinationDown, ServerMigrationTask task, StartingServerState startingState) throws ServerMigrationException {
      try {
         task.startMigratableServer(startingState);
      } catch (ServerLifecycleException var5) {
         if (MigrationDebugLogger.isDebugEnabled()) {
            MigrationDebugLogger.debug("Task failed", var5);
         }

         if (!destinationDown) {
            throw new ServerMigrationException("Migration failed trying to start the server", var5);
         }
      }

      if (MigrationDebugLogger.isDebugEnabled()) {
         MigrationDebugLogger.debug("Restarted server on target destination");
      }

   }

   void taskComplete(String serverName) {
      if (MigrationDebugLogger.isDebugEnabled()) {
         MigrationDebugLogger.debug("Removing task " + serverName);
      }

      Object obj = this.taskMap.remove(serverName);
      if (MigrationDebugLogger.isDebugEnabled()) {
         MigrationDebugLogger.debug("Removing task " + obj);
      }

   }

   private static class ServerMigrationTask {
      private final DomainAccess domainAccess;
      private final String serverName;
      private final String destinationMachine;

      private ServerMigrationTask(String serverName, String destinationMachine) {
         this.domainAccess = ManagementService.getDomainAccess(ServerMigrationCoordinatorImpl.kernelId);
         this.serverName = serverName;
         this.destinationMachine = destinationMachine;
      }

      private void stopMigratableServer() throws ServerLifecycleException {
         ServerLifeCycleRuntimeMBean lifeCycleMBean = this.domainAccess.lookupServerLifecycleRuntime(this.serverName);
         ServerLifeCycleTaskRuntimeMBean lifeCycleTask = lifeCycleMBean.shutdown();
         this.waitForTask(lifeCycleTask);
      }

      private void startMigratableServer(StartingServerState startingState) throws ServerLifecycleException {
         ServerLifeCycleRuntimeMBean lifeCycleMBean = this.domainAccess.lookupServerLifecycleRuntime(this.serverName);
         if (MigrationDebugLogger.isDebugEnabled()) {
            MigrationDebugLogger.debug(this.serverName + " is going to be started on " + this.destinationMachine + " at state " + startingState);
         }

         ServerLifeCycleTaskRuntimeMBean lifeCycleTask = null;
         switch (startingState) {
            case RUNNING:
               lifeCycleTask = lifeCycleMBean.start(this.destinationMachine);
               break;
            case ADMIN:
               lifeCycleTask = lifeCycleMBean.startInAdmin(this.destinationMachine);
               break;
            case STANDBY:
               lifeCycleTask = lifeCycleMBean.startInStandby(this.destinationMachine);
         }

         this.waitForTask(lifeCycleTask);
      }

      private synchronized void waitForTask(ServerLifeCycleTaskRuntimeMBean task) {
         while(task.isRunning()) {
            try {
               this.wait(10000L);
            } catch (InterruptedException var3) {
            }
         }

      }

      // $FF: synthetic method
      ServerMigrationTask(String x0, String x1, Object x2) {
         this(x0, x1);
      }
   }

   public static enum StartingServerState {
      RUNNING("RUNNING"),
      ADMIN("ADMIN"),
      STANDBY("STANDBY");

      String displayString;

      private StartingServerState(String displayString) {
         this.displayString = displayString;
      }

      public static StartingServerState StartingServerState(String startingServerState) {
         StartingServerState startingState;
         if (startingServerState != null && startingServerState.isEmpty()) {
            if (RUNNING.displayString.equals(startingServerState)) {
               startingState = RUNNING;
            } else if (ADMIN.displayString.equals(startingServerState)) {
               startingState = ADMIN;
            } else {
               if (!STANDBY.displayString.equals(startingServerState)) {
                  throw new IllegalArgumentException(startingServerState + "is not a valid startup state");
               }

               startingState = STANDBY;
            }
         } else {
            startingState = RUNNING;
         }

         return startingState;
      }
   }
}
