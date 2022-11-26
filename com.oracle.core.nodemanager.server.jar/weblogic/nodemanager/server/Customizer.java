package weblogic.nodemanager.server;

import java.io.IOException;
import java.util.Properties;
import weblogic.nodemanager.common.ConfigException;
import weblogic.nodemanager.common.StartupConfig;
import weblogic.nodemanager.common.StateInfo;
import weblogic.nodemanager.plugin.ProcessManagementPlugin;
import weblogic.nodemanager.util.ConcurrentFile;

public interface Customizer {
   boolean isAlive(ServerManagerI var1, String var2);

   boolean isAdminServer(StartupConfig var1);

   boolean isSystemComponent();

   StartupConfig createStartupConfig(Properties var1) throws ConfigException;

   boolean isNoStartupConfigAWarning();

   InstanceCustomizer createInstanceCustomizer(ServerManagerI var1, StartupConfig var2);

   public interface InstanceCustomizer extends ProcessManagementPlugin.SystemComponentManager {
      void afterCrashCleanUp() throws IOException;

      void preStart() throws IOException;

      void ensureStateInfo(StateInfo var1, ConcurrentFile var2) throws IOException;
   }
}
