package weblogic.nodemanager.plugin;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import weblogic.admin.plugin.Plugin;

public interface ProcessManagementPlugin extends Plugin {
   void init(Provider var1);

   Provider getProvider();

   SystemComponentManager createSystemComponentManager(String var1);

   boolean isAlive(String var1);

   public static enum SystemComponentState {
      STARTING,
      RUNNING,
      RESTART_REQUIRED,
      STOPPING,
      STOPPED,
      FAILED;
   }

   public interface Process {
      Properties RESTART_PROPERTIES = new Properties();

      void start(Properties var1) throws IOException;

      void destroy(Properties var1) throws IOException;

      boolean isAlive();

      void softRestart(Properties var1) throws UnsupportedOperationException, IOException;

      String getProcessId();

      void waitForProcessDeath();
   }

   public interface SystemComponentManager {
      SystemComponentState getState();

      Process createProcess() throws IOException;

      Process createProcess(String var1) throws IOException;

      void log(Level var1, String var2, Throwable var3);
   }
}
