package weblogic.t3.srvr;

import javax.inject.Inject;
import org.glassfish.hk2.runlevel.ChangeableRunLevelFuture;
import org.glassfish.hk2.runlevel.ErrorInformation;
import org.glassfish.hk2.runlevel.ProgressStartedListener;
import org.glassfish.hk2.runlevel.RunLevelController;
import org.glassfish.hk2.runlevel.RunLevelFuture;
import org.glassfish.hk2.runlevel.RunLevelListener;
import weblogic.common.internal.VersionInfoFactory;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.server.ServerLifecycleException;
import weblogic.server.ServiceFailureException;

public abstract class LifecycleListener implements RunLevelListener, ProgressStartedListener {
   @Inject
   protected RunLevelController rls;
   private final DebugLogger debugSLCWLDF = DebugLogger.getDebugLogger("DebugServerLifeCycle");
   protected long svrStart = 0L;

   public void onCancelled(RunLevelFuture runLevelFuture, int i) {
      if (this.isDebugEnabled()) {
         String upDown = "neutral";
         if (runLevelFuture.isUp()) {
            upDown = "up";
         } else if (runLevelFuture.isDown()) {
            upDown = "down";
         }

         this.debugSLCWLDF.debug("Lifecycle operation has been CANCELLED at level " + i + " in direction " + upDown);
      }

   }

   public void onError(RunLevelFuture runLevelFuture, ErrorInformation errorInformation) {
      if (this.isDebugEnabled()) {
         String serverService = errorInformation.getFailedDescriptor() == null ? "null" : errorInformation.getFailedDescriptor().getName();
         this.debugSLCWLDF.debug("Server Service " + serverService + " failed with exception " + errorInformation.getError().getMessage());
      }

      Throwable sfe = this.stripException(errorInformation.getError());
      WebLogicServer t3srvr = T3Srvr.getT3Srvr();
      boolean alreadyFailed = t3srvr.isLifecycleExceptionThrown();
      t3srvr.setLifecycleExceptionThrown(true);
      if (t3srvr.isSvrStarting()) {
         if (alreadyFailed) {
            return;
         }

         ServerStartupTimer.cancelStartupTimeBomb();
         if (sfe instanceof ServiceFailureException) {
            t3srvr.setFailedStateFromCallback(sfe, false);
         } else {
            t3srvr.setFailedStateFromCallback(sfe, true);
         }
      } else {
         this.onErrorShutdown(sfe, runLevelFuture, errorInformation);
      }

   }

   public void onProgress(ChangeableRunLevelFuture runLevelFuture, int currentRunLevel) {
      try {
         switch (currentRunLevel) {
            case 0:
               this.processShutdownLifecycleEvent(runLevelFuture);
               break;
            case 5:
               this.processStartingLifecycleEvent(runLevelFuture);
               break;
            case 10:
               this.processStandbyLifecycleEvent(runLevelFuture);
               break;
            case 15:
               this.processAdminLifecycleEvent(runLevelFuture);
               break;
            case 20:
               this.processRunningLifecycleEvent(runLevelFuture);
         }
      } catch (Throwable var4) {
         if (T3Srvr.getT3Srvr().isShuttingDown()) {
            T3Srvr.getT3Srvr().setFailedState(var4, true, true);
         } else {
            T3Srvr.getT3Srvr().setFallbackState(0);
            runLevelFuture.changeProposedLevel(0);
            T3Srvr.getT3Srvr().setFailedState(var4, true);
         }
      }

   }

   public void onProgressStarting(ChangeableRunLevelFuture runLevelFuture, int currentRunLevel) {
      if (runLevelFuture.isDown() || runLevelFuture.isUp() && currentRunLevel == 15) {
         this.onProgress(runLevelFuture, currentRunLevel);
      }

   }

   private void processShutdownLifecycleEvent(ChangeableRunLevelFuture runLevelFuture) {
      if (T3Srvr.getT3Srvr().isSvrStarting()) {
         if (this.isDebugEnabled()) {
            this.debug("ServerShutdown : The SHUTDOWN Lifecycle State has Successfully been Achieved");
         }

         this.svrStart = System.currentTimeMillis();
         VersionInfoFactory.initialize(true);
      } else {
         if (this.isDebugEnabled()) {
            this.debug("ServerShutdown : Starting Destruction of all Services that constitute the SHUTDOWN Lifecycle State");
         }

         T3Srvr.getT3Srvr().setPreventShutdownHook();
         this.terminateServer();
      }

   }

   private void processStartingLifecycleEvent(ChangeableRunLevelFuture runLevelFuture) throws ServiceFailureException, ServerLifecycleException {
      if (T3Srvr.getT3Srvr().isSvrStarting()) {
         if (this.isDebugEnabled()) {
            this.debug("ServerStarting : The STARTING Lifecycle State has Successfully been Achieved");
         }

         this.onStartingLifecycleBoot(runLevelFuture);
      } else {
         if (this.isDebugEnabled()) {
            this.debug("ServerStarting : Starting Destruction of all Services that constitute the STARTING Lifecycle State");
         }

         this.onStartingLifecycleShutdown(runLevelFuture);
      }

   }

   private void processStandbyLifecycleEvent(ChangeableRunLevelFuture runLevelFuture) throws ServiceFailureException {
      if (T3Srvr.getT3Srvr().isSvrStarting()) {
         if (this.isDebugEnabled()) {
            this.debug("ServerStandby : The STANDBY Lifecycle State has Successfully been Achieved");
         }

         T3Srvr.getT3Srvr().setLockoutManager();

         try {
            this.onStandbyLifecycleBoot(runLevelFuture);
         } catch (ServerLifecycleException var3) {
            throw new ServiceFailureException(var3);
         }
      } else {
         if (this.isDebugEnabled()) {
            this.debug("ServerStandby : Starting Destruction of all Services that constitute the STANDBY Lifecycle State");
         }

         this.onStandbyLifecycleShutdown(runLevelFuture);
      }

   }

   private void processAdminLifecycleEvent(ChangeableRunLevelFuture runLevelFuture) throws ServiceFailureException {
      if (T3Srvr.getT3Srvr().isSvrStarting()) {
         if (this.isDebugEnabled()) {
            this.debug("ServerAdmin : The ADMIN Lifecycle State has Successfully been Achieved");
         }

         this.onAdminLifecycleBoot(runLevelFuture);
      } else {
         this.onAdminLifecycleShutdown(runLevelFuture);
      }

   }

   private void processRunningLifecycleEvent(ChangeableRunLevelFuture runLevelFuture) throws ServiceFailureException {
      if (T3Srvr.getT3Srvr().isSvrStarting()) {
         this.onRunningLifecycleBoot(runLevelFuture);
      } else {
         this.onRunningLifecycleShutdown(runLevelFuture);
      }

   }

   static void setSvrStarting(boolean starting) {
      T3Srvr.getT3Srvr().setSvrStarting(starting);
   }

   private Throwable stripException(Throwable thr) {
      Throwable workThr;
      for(workThr = thr; workThr.getCause() != null && !(workThr instanceof ServiceFailureException); workThr = workThr.getCause()) {
      }

      return workThr instanceof ServiceFailureException ? workThr : thr;
   }

   private void terminateServer() {
      synchronized(T3Srvr.getT3Srvr()) {
         T3Srvr.getT3Srvr().notifyAll();
      }
   }

   protected void debug(String str) {
      this.debugSLCWLDF.debug(str);
   }

   protected boolean isDebugEnabled() {
      return this.debugSLCWLDF.isDebugEnabled();
   }

   protected abstract void onStartingLifecycleBoot(ChangeableRunLevelFuture var1) throws ServerLifecycleException;

   protected abstract void onStartingLifecycleShutdown(ChangeableRunLevelFuture var1);

   protected abstract void onStandbyLifecycleBoot(ChangeableRunLevelFuture var1) throws ServerLifecycleException;

   protected abstract void onStandbyLifecycleShutdown(ChangeableRunLevelFuture var1);

   protected abstract void onAdminLifecycleBoot(ChangeableRunLevelFuture var1);

   protected abstract void onAdminLifecycleShutdown(ChangeableRunLevelFuture var1);

   protected abstract void onRunningLifecycleBoot(ChangeableRunLevelFuture var1);

   protected abstract void onRunningLifecycleShutdown(ChangeableRunLevelFuture var1);

   protected abstract void onErrorBoot();

   protected abstract void onErrorShutdown(Throwable var1, RunLevelFuture var2, ErrorInformation var3);

   protected int convertStateToRunLevel(int nextState) {
      byte nextRunlevel;
      switch (nextState) {
         case 2:
            nextRunlevel = 20;
            break;
         case 3:
            nextRunlevel = 10;
            break;
         case 17:
            nextRunlevel = 15;
            break;
         default:
            T3Srvr.getT3Srvr().setFailedStateFromCallback(new ServerLifecycleException("Unknown state: " + nextState), false);
            nextRunlevel = 0;
      }

      return nextRunlevel;
   }
}
