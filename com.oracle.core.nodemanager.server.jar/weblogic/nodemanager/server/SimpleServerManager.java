package weblogic.nodemanager.server;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import weblogic.nodemanager.common.ConfigException;
import weblogic.nodemanager.common.StartupConfig;
import weblogic.nodemanager.plugin.ProcessManagementPlugin;
import weblogic.nodemanager.plugin.SimpleProcessPlugin;

public class SimpleServerManager implements ServerManagerI {
   private final SimpleProcessPlugin processPlugin;
   protected final DomainManager domainMgr;
   protected final String serverName;

   public SimpleServerManager(SimpleProcessPlugin plugin, DomainManager domainMgr, String name) throws ConfigException, IOException {
      assert plugin != null : "plugin null";

      assert domainMgr != null : "DomainManager null";

      assert name != null : "Server name null";

      this.processPlugin = plugin;
      this.domainMgr = domainMgr;
      this.serverName = name;
   }

   public void recoverServer() throws ConfigException, IOException {
      throw new UnsupportedOperationException();
   }

   public boolean start(Properties props, Properties runtimeProps) throws InterruptedException, ConfigException, IOException {
      this.processPlugin.start(this.serverName, runtimeProps);
      return true;
   }

   public StartupConfig getStartupConfig() {
      throw new UnsupportedOperationException();
   }

   public StartupConfig saveStartupConfig(Properties props) throws ConfigException, IOException {
      throw new UnsupportedOperationException();
   }

   public String getState() {
      String state = "UNKNOWN";

      try {
         state = getStateString(this.processPlugin.getState(this.serverName));
      } catch (IOException var3) {
         NMServer.nmLog.log(Level.WARNING, "Could not get state info from plugin!", var3);
      }

      return state;
   }

   public boolean kill(Properties prop) throws IOException, InterruptedException {
      this.processPlugin.stop(this.serverName, prop);
      return true;
   }

   public void printThreadDump(Properties prop) throws IOException, InterruptedException {
      throw new UnsupportedOperationException("Method printThreadDump not implemented !");
   }

   public String getProgress(Properties prop, long timeout) throws InterruptedException, IOException {
      throw new UnsupportedOperationException("Method getProgress not implemented !");
   }

   public boolean softRestart(Properties prop) throws UnsupportedOperationException, IOException {
      this.processPlugin.softRestart(this.serverName, prop);
      return true;
   }

   public DomainManager getDomainManager() {
      return this.domainMgr;
   }

   public Customizer getCustomizer() {
      throw new UnsupportedOperationException();
   }

   public ServerDir getServerDir() {
      throw new UnsupportedOperationException();
   }

   public String getServerName() {
      return this.serverName;
   }

   public void remove() throws IOException {
   }

   public void initState() throws IOException {
   }

   public boolean isManagedByThisHost() {
      return false;
   }

   public void log(Level level, String msg, Throwable thrown) {
      throw new UnsupportedOperationException();
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

   public boolean shutdownForRestart() throws Exception {
      return true;
   }

   public String toString() {
      return "SimpleServerManager(" + this.serverName + "," + System.identityHashCode(this) + ")";
   }

   public boolean isServerConfigured() {
      throw new UnsupportedOperationException();
   }
}
