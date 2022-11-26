package weblogic.t3.srvr;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import org.glassfish.hk2.runlevel.ChangeableRunLevelFuture;
import org.glassfish.hk2.runlevel.ErrorInformation;
import org.glassfish.hk2.runlevel.RunLevelFuture;
import org.jvnet.hk2.annotations.Service;
import weblogic.kernel.T3SrvrLogger;
import weblogic.management.provider.RuntimeAccess;
import weblogic.server.ServerLifecycleException;
import weblogic.server.ServerStates;
import weblogic.server.ShutdownParametersBean;

@Service
@Singleton
public class ServerLifecycleListener extends LifecycleListener {
   @Inject
   private WebLogicServer t3srvr;
   @Inject
   private Provider runtimeAccess;
   private String startupMode;
   private long startupTimeout;
   private long beginStartTime;
   private boolean gracefulShutdown = true;
   private boolean isGracefulShutdownTimeoutRunning = false;
   private Throwable gracefulShutdownFailed = null;

   protected void onStartingLifecycleBoot(ChangeableRunLevelFuture runLevelFuture) throws ServerLifecycleException {
      this.startupMode = ((RuntimeAccess)this.runtimeAccess.get()).getServer().getStartupMode();
      this.startupTimeout = SrvrUtilities.getStartupTimeout();
      this.beginStartTime = System.currentTimeMillis();
      if (this.isDebugEnabled()) {
         this.debug("Startup Config Parameters: StartupMode = " + this.startupMode + ", StartupTimeout = " + this.startupTimeout);
         this.debug("Shutdown Timeout Parameters: GracefulShutdown Timout = " + SrvrUtilities.getGracefulShutdownTimeout());
      }

      ServerStartupTimer.startTimeBomb(this.startupTimeout - (System.currentTimeMillis() - this.svrStart), SrvrUtilities.getStartupTimeoutNumOfThreadDump(), SrvrUtilities.getStartupTimeoutThreadDumpInterval());
      int nextState = this.getLifeCycleStateInteger(this.startupMode);
      int nextRunlevel = this.convertStateToRunLevel(nextState);
      runLevelFuture.changeProposedLevel(nextRunlevel);
   }

   protected void onStartingLifecycleShutdown(ChangeableRunLevelFuture runLevelFuture) {
   }

   protected void onStandbyLifecycleBoot(ChangeableRunLevelFuture runLevelFuture) throws ServerLifecycleException {
      if ("STANDBY".equalsIgnoreCase(this.startupMode)) {
         T3Srvr.getT3Srvr().getStartupMode();
         ServerStartupTimer.cancelStartupTimeBomb();
         this.t3srvr.setStarted(true);
         T3SrvrLogger.logServerStarted1(ServerStates.SERVERSTATES[3]);
         this.t3srvr.logStartupStatistics();
         this.t3srvr.setState(3);
      } else {
         this.t3srvr.setState(3);
         this.t3srvr.setState(1);
      }

   }

   protected void onStandbyLifecycleShutdown(ChangeableRunLevelFuture runLevelFuture) {
   }

   protected void onAdminLifecycleBoot(ChangeableRunLevelFuture runLevelFuture) {
      if ((!"ADMIN".equalsIgnoreCase(this.startupMode) || "RESUMING".equalsIgnoreCase(this.t3srvr.getState())) && !this.t3srvr.isAbortStartupAfterAdminState()) {
         this.t3srvr.setFallbackState(17);
         if (!"ADMIN".equalsIgnoreCase(this.t3srvr.getState()) && !"RESUMING".equalsIgnoreCase(this.t3srvr.getState())) {
            this.t3srvr.setState(17);
         }

         if (!"RESUMING".equalsIgnoreCase(this.t3srvr.getState())) {
            this.t3srvr.setState(6);
         }
      } else {
         if (T3Srvr.getT3Srvr().getState().equalsIgnoreCase(ServerStates.SERVERSTATES[6])) {
            this.t3srvr.setFallbackState(17);
         } else {
            ServerStartupTimer.cancelStartupTimeBomb();
         }

         this.t3srvr.setStarted(true);
         T3SrvrLogger.logServerStarted1(ServerStates.SERVERSTATES[17]);
         this.t3srvr.logStartupStatistics();
         if (!"ADMIN".equalsIgnoreCase(this.t3srvr.getState())) {
            this.t3srvr.setState(17);
         }

         runLevelFuture.changeProposedLevel(15);
      }

   }

   public void onAdminLifecycleShutdown(ChangeableRunLevelFuture runLevelFuture) {
      synchronized(this.t3srvr) {
         if (this.t3srvr.getState() != "FORCE_SHUTTING_DOWN" && this.t3srvr.getState() != "SHUTTING_DOWN" && this.t3srvr.getState() != "ADMIN" && this.gracefulShutdownFailed == null) {
            this.t3srvr.setState(17);
         }
      }

      if (this.t3srvr.isShuttingDown()) {
         if (this.isDebugEnabled()) {
            this.debug("ServerAdmin : Starting Destruction of all Services that constitute the ADMIN Lifecycle State");
         }

         if (this.gracefulShutdown) {
            if (!this.isGracefulShutdownTimeoutRunning) {
               ServerGracefulShutdownTimer.startTimeBomb(SrvrUtilities.getGracefulShutdownTimeout(), SrvrUtilities.getStartupTimeoutNumOfThreadDump(), SrvrUtilities.getGracefulShutdownTimeoutThreadDumpInterval());
               SrvrUtilities.setGracefulShutdownOverride(-1L);
               this.isGracefulShutdownTimeoutRunning = true;
            }
         } else if (this.gracefulShutdownFailed != null) {
            runLevelFuture.changeProposedLevel(0);
         }
      } else {
         synchronized(this.t3srvr) {
            if (this.t3srvr.getState() != "ADMIN") {
               this.t3srvr.setState(17);
            }
         }

         if (this.gracefulShutdownFailed == null) {
            ServerGracefulShutdownTimer.cancelGracefulShutdownTimeBomb();
            this.isGracefulShutdownTimeoutRunning = false;
         }
      }

   }

   protected void onRunningLifecycleBoot(ChangeableRunLevelFuture runLevelFuture) {
      if (this.isDebugEnabled()) {
         this.debug("ServerRunning : The RUNNING Lifecycle State has Successfully been Achieved");
      }

      this.t3srvr.setStartupTime(System.currentTimeMillis() - this.t3srvr.getStartTime());
      this.t3srvr.setStartupTime(System.currentTimeMillis() - this.beginStartTime);
      ServerStartupTimer.cancelStartupTimeBomb();
      this.t3srvr.setFallbackState(-1);
      this.t3srvr.setStarted(true);
      T3SrvrLogger.logServerStarted1(ServerStates.SERVERSTATES[2]);
      this.t3srvr.logStartupStatistics();
      this.t3srvr.setState(2);
      SrvrUtilities.invokeRunningStateListeners();
   }

   protected void onRunningLifecycleShutdown(ChangeableRunLevelFuture runLevelFuture) {
      if (this.isDebugEnabled()) {
         this.debug("ServerRunning : Starting Destruction of all Services that constitute the RUNNING Lifecycle State");
      }

      if (this.gracefulShutdown) {
         ServerGracefulShutdownTimer.startTimeBomb(SrvrUtilities.getGracefulShutdownTimeout(), SrvrUtilities.getGracefulShutdownTimeoutNumOfThreadDump(), SrvrUtilities.getGracefulShutdownTimeoutThreadDumpInterval());
         SrvrUtilities.setGracefulShutdownOverride(-1L);
         this.isGracefulShutdownTimeoutRunning = true;
         this.gracefulShutdownFailed = null;
      }

   }

   public void setGracefulShutdown(boolean graceful) {
      this.gracefulShutdown = graceful;
   }

   private int getLifeCycleStateInteger(String state) {
      int idx = 0;

      for(int i = 0; i < ServerStates.SERVERSTATES.length; ++i) {
         if (state.compareToIgnoreCase(ServerStates.SERVERSTATES[i]) == 0) {
            idx = i;
            break;
         }
      }

      return idx;
   }

   protected void onErrorBoot() {
   }

   protected void onErrorShutdown(Throwable th, RunLevelFuture runLevelFuture, ErrorInformation errorInformation) {
      if (this.gracefulShutdown) {
         this.gracefulShutdownFailed = th;
         this.gracefulShutdown = false;
         T3SrvrLogger.logShuttingDownOnFailure();
         this.t3srvr.setFailedState(th, true, true);
         Map shutdownDirectives = new HashMap();
         shutdownDirectives.put("Graceful", Boolean.FALSE);
         shutdownDirectives.put("ignore.sessions", Boolean.TRUE);
         ShutdownParametersBean.getInstance().setShutdownDirectives(shutdownDirectives);
      }

   }

   void checkGracefulShutdownFailure() throws ServerLifecycleException {
      Throwable th = this.gracefulShutdownFailed;
      this.gracefulShutdownFailed = null;
      if (th != null) {
         if (th instanceof ServerLifecycleException) {
            throw (ServerLifecycleException)th;
         } else {
            throw new ServerLifecycleException(th);
         }
      }
   }
}
