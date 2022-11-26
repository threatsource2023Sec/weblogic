package weblogic.nodemanager.server;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import weblogic.nodemanager.common.ConfigException;
import weblogic.nodemanager.common.StartupConfig;

public interface ServerManagerI {
   ServerDir getServerDir();

   DomainManager getDomainManager();

   String getServerName();

   Customizer getCustomizer();

   void log(Level var1, String var2, Throwable var3);

   void recoverServer() throws ConfigException, IOException;

   boolean shutdownForRestart() throws Exception;

   boolean start(Properties var1, Properties var2) throws InterruptedException, ConfigException, IOException;

   StartupConfig getStartupConfig();

   StartupConfig saveStartupConfig(Properties var1) throws ConfigException, IOException;

   String getState();

   boolean kill(Properties var1) throws InterruptedException, IOException;

   void printThreadDump(Properties var1) throws InterruptedException, IOException;

   String getProgress(Properties var1, long var2) throws InterruptedException, IOException;

   boolean softRestart(Properties var1) throws IOException;

   void remove() throws IOException;

   void initState() throws IOException;

   boolean isManagedByThisHost();

   boolean isServerConfigured();
}
