package weblogic.management.patching.commands;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.patching.PatchingDebugLogger;
import weblogic.management.workflow.MutableBoolean;
import weblogic.management.workflow.MutableString;
import weblogic.management.workflow.command.AbstractCommand;
import weblogic.management.workflow.command.SharedState;
import weblogic.nodemanager.mbean.NodeManagerRuntime;

public abstract class AbstractRestartNodeManagerCommand extends AbstractCommand {
   private static final long serialVersionUID = 2992226054880446875L;
   public static final long DEFAULT_NM_RESTART_TIMEOUT;
   public static final long DEFAULT_NM_RECONNECT_TIMEOUT;
   protected static final long DEFAULT_POLL_INTERVAL;
   @SharedState
   protected transient String machineName;
   @SharedState
   protected long timeoutMillis;
   @SharedState
   protected Boolean isAdminServer;
   @SharedState
   protected transient MutableString serverActivationTime;
   @SharedState
   protected transient MutableBoolean directorySwitchPerformed;

   public AbstractRestartNodeManagerCommand() {
      this.timeoutMillis = DEFAULT_NM_RESTART_TIMEOUT;
      this.isAdminServer = false;
   }

   protected void restartAsync(String className) throws Exception {
      try {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug(className + ".execute called for machine: " + this.machineName);
         }

         MachineMBean machine = (new MachineBasedUtils()).getMachineMBean(this.machineName);
         NodeManagerRuntime nmr = NodeManagerRuntime.getInstance(machine);
         nmr.restartNMAsyncAndUpdate();
      } catch (IOException var4) {
         CommandException ce = new CommandException(PatchingMessageTextFormatter.getInstance().getRestartNodeManagerFailure(this.machineName, var4.getClass() + ":" + var4.getMessage()));
         ce.initCause(var4);
         throw ce;
      }
   }

   protected void verifyNM(String className) throws Exception {
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug(className + ".execute called for machine: " + this.machineName);
      }

      MachineMBean machine = (new MachineBasedUtils()).getMachineMBean(this.machineName);
      NodeManagerRuntime nmr = NodeManagerRuntime.getInstance(machine);
      boolean connected = this.checkNMReconnect(nmr);
      if (!connected) {
         long timeoutInMinutes = TimeUnit.MINUTES.convert(DEFAULT_NM_RECONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
         CommandException ce = new CommandException(PatchingMessageTextFormatter.getInstance().getConnectNodeManagerFailure(this.machineName, timeoutInMinutes));
         throw ce;
      }
   }

   protected boolean checkNMReconnect(final NodeManagerRuntime nmr) {
      TimeoutUtils timeoutUtils = new TimeoutUtils();
      long timeout = timeoutUtils.convertTimeoutByPercentageFactor(DEFAULT_NM_RECONNECT_TIMEOUT);
      long pollInterval = timeoutUtils.convertIntervalByFactorIfLarger(DEFAULT_POLL_INTERVAL);
      StatusPollerResult nmReconnectStatusPoller = new StatusPollerResult() {
         public IOException cache;

         public boolean checkStatus() {
            try {
               nmr.getVersion();
               return true;
            } catch (IOException var2) {
               this.cache = var2;
               return false;
            }
         }

         public String getPollingDescription() {
            return "NMReconnect";
         }

         public IOException getResult() {
            return this.cache;
         }
      };
      if ((new TimedStatusPoller()).pollStatusWithTimeout(timeout, pollInterval, nmReconnectStatusPoller)) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Checking for NMReconnect has exceeded timeout and cache is " + nmReconnectStatusPoller.getResult());
            PatchingDebugLogger.debug("NodeManager might have restarted, but we are temporarily unable to contact it.  Will proceed with next workflow operation");
         }

         return false;
      } else {
         return true;
      }
   }

   static {
      DEFAULT_NM_RESTART_TIMEOUT = TimeUnit.MILLISECONDS.convert(20L, TimeUnit.MINUTES);
      DEFAULT_NM_RECONNECT_TIMEOUT = TimeUnit.MILLISECONDS.convert(50L, TimeUnit.MINUTES);
      DEFAULT_POLL_INTERVAL = TimeUnit.MILLISECONDS.convert(2L, TimeUnit.SECONDS);
   }
}
