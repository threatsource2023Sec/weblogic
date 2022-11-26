package weblogic.nodemanager.server;

import java.util.Properties;
import weblogic.nodemanager.common.CoherenceStartupConfig;
import weblogic.nodemanager.common.ConfigException;
import weblogic.nodemanager.common.StartupConfig;
import weblogic.nodemanager.util.ProcessControl;

public class CoherenceCustomizer implements Customizer {
   private final ProcessControl processControl;

   public CoherenceCustomizer(ProcessControl processControl) {
      this.processControl = processControl;
   }

   public boolean isAlive(ServerManagerI serverMgr, String pid) {
      return this.processControl != null && this.processControl.isProcessAlive(pid);
   }

   public boolean isNoStartupConfigAWarning() {
      return false;
   }

   public boolean isAdminServer(StartupConfig conf) {
      return false;
   }

   public boolean isSystemComponent() {
      return false;
   }

   public StartupConfig createStartupConfig(Properties props) throws ConfigException {
      return new CoherenceStartupConfig(props);
   }

   public Customizer.InstanceCustomizer createInstanceCustomizer(ServerManagerI serverMgr, StartupConfig conf) {
      CoherenceProcessBuilder builder = new CoherenceProcessBuilder(serverMgr, conf);
      return new CoherenceInstanceCustomizer(serverMgr, conf, builder);
   }
}
