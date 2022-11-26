package weblogic.nodemanager.server;

import java.io.IOException;
import java.util.Properties;
import weblogic.nodemanager.common.ConfigException;
import weblogic.nodemanager.common.StartupConfig;
import weblogic.nodemanager.common.StateInfo;
import weblogic.nodemanager.common.SystemComponentStartupConfig;
import weblogic.nodemanager.plugin.ProcessManagementPlugin;
import weblogic.nodemanager.plugin.ProcessManagementPlugin.SystemComponentState;
import weblogic.nodemanager.util.ConcurrentFile;

public class SystemComponentCustomizer implements Customizer {
   private final ProcessManagementPlugin plugin;

   SystemComponentCustomizer(ProcessManagementPlugin plugin) {
      if (plugin == null) {
         throw new NullPointerException();
      } else {
         this.plugin = plugin;
      }
   }

   public boolean isAlive(ServerManagerI serverMgr, String pid) {
      return this.plugin.isAlive(pid);
   }

   public boolean isNoStartupConfigAWarning() {
      return false;
   }

   public boolean isAdminServer(StartupConfig conf) {
      return false;
   }

   public boolean isSystemComponent() {
      return true;
   }

   public StartupConfig createStartupConfig(Properties props) throws ConfigException {
      return new SystemComponentStartupConfig(props);
   }

   public Customizer.InstanceCustomizer createInstanceCustomizer(ServerManagerI serverMgr, StartupConfig conf) {
      ProcessManagementPlugin.SystemComponentManager mgr = this.plugin.createSystemComponentManager(serverMgr.getServerName());
      return new SystemComponentInstanceCustomizer(serverMgr, mgr);
   }

   static class SystemComponentInstanceCustomizer extends DecoratedSystemComponentManager implements Customizer.InstanceCustomizer {
      private SystemComponentInstanceCustomizer(ServerManagerI serverMgr, ProcessManagementPlugin.SystemComponentManager mgr) {
         super(serverMgr, mgr);
      }

      public void afterCrashCleanUp() throws IOException {
      }

      public void preStart() throws IOException {
      }

      public void ensureStateInfo(StateInfo stateInfo, ConcurrentFile stateFile) throws IOException {
         String storedStateString = stateInfo.getState();
         ProcessManagementPlugin.SystemComponentState state = this.getState();
         if (state != null || storedStateString == null) {
            String stateString = state == null ? "UNKNOWN" : getStateString(state);
            if (!stateString.equals(storedStateString)) {
               String oldStateInfoString = stateInfo.toString();
               stateInfo.set(stateString, isStarted(stateInfo, state), state == SystemComponentState.FAILED);
               this.log("SystemComponentInstanceCustomizer.ensureStateInfo(): set StateInfo from " + oldStateInfoString + " to " + stateInfo);
               stateInfo.save(stateFile);
            }

         }
      }

      static boolean isStarted(StateInfo oldStateInfo, ProcessManagementPlugin.SystemComponentState newState) {
         return oldStateInfo.isStarted() || SystemComponentState.RUNNING == newState || SystemComponentState.RESTART_REQUIRED == newState || getStateString(SystemComponentState.RUNNING).equals(oldStateInfo.getState()) || getStateString(SystemComponentState.RESTART_REQUIRED).equals(oldStateInfo.getState());
      }

      private static String getStateString(ProcessManagementPlugin.SystemComponentState state) {
         switch (state) {
            case STARTING:
               return "STARTING";
            case RUNNING:
               return "RUNNING";
            case RESTART_REQUIRED:
               return "RESTART_REQUIRED";
            case STOPPING:
               return "SHUTTING_DOWN";
            case STOPPED:
               return "SHUTDOWN";
            case FAILED:
               return "FAILED";
            default:
               throw new IllegalStateException();
         }
      }

      // $FF: synthetic method
      SystemComponentInstanceCustomizer(ServerManagerI x0, ProcessManagementPlugin.SystemComponentManager x1, Object x2) {
         this(x0, x1);
      }
   }
}
