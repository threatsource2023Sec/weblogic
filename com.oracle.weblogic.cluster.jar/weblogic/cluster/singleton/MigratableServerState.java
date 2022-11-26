package weblogic.cluster.singleton;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.Date;
import java.util.Vector;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.nodemanager.mbean.NodeManagerLifecycleService;
import weblogic.nodemanager.mbean.NodeManagerLifecycleServiceGenerator;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.work.WorkManagerFactory;

final class MigratableServerState {
   private static final boolean DEBUG = MigrationDebugLogger.isDebugEnabled();
   private final ServerMBean server;
   private final Vector candidateMachines;
   private MachineMBean currentMachine;
   private MachineMBean previousMachine;
   private int serverMigrationAttemptsCount = 0;
   private final RestartTask task;
   private MigratableServersMonitorImpl monitor;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public MigratableServerState(ServerMBean server, MachineMBean current, MigratableServersMonitorImpl monitor) {
      this.server = server;
      this.currentMachine = current;
      this.candidateMachines = this.getCandidateMachines(server);
      this.task = new RestartTask(this);
      this.monitor = monitor;
   }

   public String toString() {
      return this.server.getName() + " on " + this.currentMachine.getName();
   }

   private Vector getCandidateMachines(ServerMBean server) {
      Vector mcs = new Vector();
      MachineMBean[] machines = server.getCandidateMachines();
      int i;
      if (machines != null) {
         for(i = 0; i < machines.length; ++i) {
            if (machines[i].getName().equals(this.currentMachine.getName())) {
               mcs.add(0, machines[i]);
            } else {
               mcs.add(machines[i]);
            }
         }
      }

      machines = server.getCluster().getCandidateMachinesForMigratableServers();
      if (machines != null) {
         for(i = 0; i < machines.length; ++i) {
            if (!mcs.contains(machines[i])) {
               if (machines[i].getName().equals(this.currentMachine.getName())) {
                  mcs.add(0, machines[i]);
               } else {
                  mcs.add(machines[i]);
               }
            }
         }
      }

      if (DEBUG) {
         p("Merged candidate machine list is: ");

         for(i = 0; i < mcs.size(); ++i) {
            p(((MachineMBean)mcs.get(i)).getName());
         }
      }

      return mcs;
   }

   public ServerMBean getServer() {
      return this.server;
   }

   MachineMBean getCurrentMachine() {
      return this.currentMachine;
   }

   MachineMBean getPreviousMachine() {
      return this.previousMachine;
   }

   private void migrateToANewMachine() {
      if (this.candidateMachines.size() > 0) {
         ++this.serverMigrationAttemptsCount;
         int currentMachineIndex = this.candidateMachines.indexOf(this.currentMachine);
         int newIndex = (currentMachineIndex + 1) % this.candidateMachines.size();
         this.previousMachine = this.currentMachine;
         this.currentMachine = (MachineMBean)this.candidateMachines.get(newIndex);
         if (DEBUG) {
            p(this.server.getName() + " is migrating from " + this.previousMachine.getName() + " to " + this.currentMachine.getName());
         }

         try {
            this.monitor.setServerLocation(this.server.getName(), this.currentMachine.getName());
         } catch (RemoteException var4) {
         }

      }
   }

   void serverPreviouslyUnresponsive() throws ServerMigrationException {
      String currentStatus = this.getCurrentStatus();
      if (DEBUG) {
         p("serverPreviouslyUnresponsive. Current status of " + this + " is " + currentStatus);
      }

      if (currentStatus.equals("RUNNING")) {
         this.migrationComplete();
      } else {
         this.serverUnresponsive();
      }
   }

   void serverUnresponsive() throws ServerMigrationException {
      if (this.serverMigrationAttemptsCount > this.candidateMachines.size()) {
         throw new ServerMigrationException("Failed to start the migratable server on one of the candidate machines", (Throwable)null);
      } else {
         if (!this.task.isTaskRunning()) {
            this.task.changeRunningState(true);
            String currentServerMachine = this.monitor.getCurrentMachine(this.server.getName());
            if (currentServerMachine != null && !currentServerMachine.equals(this.getCurrentMachine().getName())) {
               this.currentMachine = this.monitor.getMachine(currentServerMachine);
               if (DEBUG) {
                  p("Resetting MigratableServerState current machine to  " + currentServerMachine + " for server " + this.server.getName());
               }
            }

            if (DEBUG) {
               p("Restarting server " + this.server.getName());
            }

            WorkManagerFactory.getInstance().getSystem().schedule(this.task);
         }

      }
   }

   private boolean isRestartable() {
      if (this.serverMigrationAttemptsCount != 0 && this.serverMigrationAttemptsCount % this.candidateMachines.size() == 0) {
         try {
            Thread.sleep(this.getMillisToSleepBetweenRetryAttempts());
         } catch (InterruptedException var2) {
         }
      }

      if (this.getAdditionalRetryAttempts() == -1) {
         return true;
      } else {
         return this.serverMigrationAttemptsCount <= this.candidateMachines.size() * (this.getAdditionalRetryAttempts() + 1);
      }
   }

   private void startServer() throws IOException {
      NodeManagerLifecycleServiceGenerator generator = (NodeManagerLifecycleServiceGenerator)GlobalServiceLocator.getServiceLocator().getService(NodeManagerLifecycleServiceGenerator.class, new Annotation[0]);
      NodeManagerLifecycleService nm = generator.getInstance(this.getCurrentMachine());
      ServerMigrationRuntimeMBeanImpl.getInstance().migrationStarted(this);

      try {
         if (DEBUG) {
            p("Sending start command to nm for " + this.server.getName());
         }

         nm.start(this.server).waitForFinish();
      } catch (InterruptedException var4) {
      }

   }

   private void migrationComplete() {
      this.markAsMigratableAgain();
      ServerMigrationRuntimeMBeanImpl.getInstance().migrationCompleted(this);
   }

   void markAsMigratableAgain() {
      this.serverMigrationAttemptsCount = 0;
   }

   private String getCurrentStatus() {
      try {
         NodeManagerLifecycleServiceGenerator generator = (NodeManagerLifecycleServiceGenerator)GlobalServiceLocator.getServiceLocator().getService(NodeManagerLifecycleServiceGenerator.class, new Annotation[0]);
         return generator.getInstance(this.getCurrentMachine()).getState(this.server);
      } catch (IOException var2) {
         return "UNKNOWN";
      }
   }

   private int getAdditionalRetryAttempts() {
      return this.server.getCluster().getAdditionalAutoMigrationAttempts();
   }

   private long getMillisToSleepBetweenRetryAttempts() {
      return this.server.getCluster().getMillisToSleepBetweenAutoMigrationAttempts();
   }

   boolean isTaskRunning() {
      return this.task.isTaskRunning();
   }

   protected static String getServerStatus(String currentStatus) {
      String serverStatus;
      if (!currentStatus.equals("RUNNING")) {
         serverStatus = currentStatus;
      } else {
         serverStatus = "UNKNOWN";
      }

      return serverStatus;
   }

   private static void p(Object o) {
      System.out.println("<" + new Date(System.currentTimeMillis()) + "><MigratableServerState> " + o);
   }

   private static class RestartTask implements Runnable {
      private final MigratableServerState serverState;
      private boolean isTaskRunning;

      private RestartTask(MigratableServerState serverState) {
         this.isTaskRunning = false;
         this.serverState = serverState;
      }

      public void run() {
         try {
            String currentStatus = MigratableServerState.getServerStatus(this.serverState.getCurrentStatus());

            MachineMBean originalMachine;
            for(originalMachine = this.serverState.currentMachine; currentStatus.equals("UNKNOWN") || currentStatus.equals("FAILED_NOT_RESTARTABLE"); currentStatus = this.serverState.getCurrentStatus()) {
               try {
                  if (MigratableServerState.DEBUG) {
                     MigratableServerState.p(this.serverState + " is restartable? " + this.serverState.isRestartable());
                  }

                  if (!this.serverState.isRestartable()) {
                     SingletonLogger.logServerMigrationFailed(this.serverState.server.getName(), originalMachine.getName());
                     break;
                  }

                  this.serverState.migrateToANewMachine();
                  SingletonLogger.logServerMigrationStarting(this.serverState.server.getName(), originalMachine.getName(), this.serverState.currentMachine.getName());
                  this.serverState.startServer();
               } catch (IOException var7) {
                  SingletonLogger.logServerMigrationTargetUnreachable(this.serverState.server.getName(), originalMachine.getName(), this.serverState.currentMachine.getName());
               }
            }

            if (currentStatus.equals("RUNNING")) {
               SingletonLogger.logServerMigrationFinished(this.serverState.server.getName(), originalMachine.getName(), this.serverState.currentMachine.getName());
               this.serverState.migrationComplete();
            }
         } finally {
            this.changeRunningState(false);
         }

      }

      private synchronized void changeRunningState(boolean value) {
         this.isTaskRunning = value;
      }

      private synchronized boolean isTaskRunning() {
         return this.isTaskRunning;
      }

      // $FF: synthetic method
      RestartTask(MigratableServerState x0, Object x1) {
         this(x0);
      }
   }
}
