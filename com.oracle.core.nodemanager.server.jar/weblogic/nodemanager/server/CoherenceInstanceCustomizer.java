package weblogic.nodemanager.server;

import java.io.IOException;
import weblogic.nodemanager.common.StartupConfig;
import weblogic.nodemanager.common.StateInfo;
import weblogic.nodemanager.plugin.ProcessManagementPlugin;
import weblogic.nodemanager.util.ConcurrentFile;

public class CoherenceInstanceCustomizer extends InternalInstanceCustomizer {
   public CoherenceInstanceCustomizer(ServerManagerI serverMgr, StartupConfig conf, WLSProcessBuilder processBuilder) {
      super(serverMgr, conf, processBuilder);
   }

   public String getStartString(StringBuilder text) {
      return nmText.msgStartingType("Coherence", text.toString());
   }

   public void afterCrashCleanUp() throws IOException {
   }

   public void preStart() throws IOException {
   }

   public void ensureStateInfo(StateInfo stateInfo, ConcurrentFile stateFile) throws IOException {
   }

   public ProcessManagementPlugin.SystemComponentState getState() {
      throw new UnsupportedOperationException();
   }
}
