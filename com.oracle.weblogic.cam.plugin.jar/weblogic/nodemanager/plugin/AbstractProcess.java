package weblogic.nodemanager.plugin;

import java.io.IOException;
import java.util.Properties;

public abstract class AbstractProcess implements ProcessManagementPlugin.Process {
   public abstract void start(Properties var1) throws IOException;

   public abstract void destroy(Properties var1);

   public abstract boolean isAlive();

   public abstract String getProcessId();

   public abstract void waitForProcessDeath();

   public void softRestart(Properties p) throws UnsupportedOperationException {
      throw new UnsupportedOperationException();
   }
}
