package weblogic.nodemanager.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import weblogic.nodemanager.NodeManagerTextTextFormatter;
import weblogic.nodemanager.common.ConfigException;
import weblogic.nodemanager.common.StartupConfig;
import weblogic.nodemanager.common.StateInfo;
import weblogic.nodemanager.plugin.ProcessManagementPlugin.Process;
import weblogic.nodemanager.util.ConcurrentFile;
import weblogic.nodemanager.util.ProcessControl;
import weblogic.nodemanager.util.ProgressData;
import weblogic.security.internal.encryption.ClearOrEncryptedService;

class ServerManager implements ServerManagerI {
   private final Customizer customizer;
   protected final DomainManager domainMgr;
   protected final String serverName;
   private final ServerDir serverDir;
   private final String[] logParams;
   private String serverType;
   private ServerMonitorI monitor;
   private boolean recoveryNeeded;
   private volatile StartupConfig conf;
   private boolean managedByThisHost;

   public ServerManager(Customizer customizer, DomainManager domainMgr, String name, String serverType) throws ConfigException, IOException {
      this(customizer, domainMgr, name, serverType, (Properties)null);
   }

   public ServerManager(Customizer customizer, DomainManager domainMgr, String name, String serverType, Properties startupProperties) throws ConfigException, IOException {
      assert customizer != null : "Customizer null";

      assert domainMgr != null : "DomainManager null";

      assert name != null : "Server name null";

      assert serverType != null : "Server type null";

      this.customizer = customizer;
      this.domainMgr = domainMgr;
      this.serverName = name;
      this.serverType = serverType;
      DomainDir dd = domainMgr.getDomainDir();
      this.serverDir = dd.getServerDir(this.serverName, serverType);
      this.logParams = new String[]{domainMgr.getDomainName(), this.serverName};
      this.loadStartupConfig(startupProperties);
      this.initialize();
   }

   private ServerMonitorI createServerMonitor(StartupConfig conf) {
      return new ServerMonitor(this, conf);
   }

   private void initialize() throws ConfigException, IOException {
      NMServerConfig nmConf = this.domainMgr.getNMServer().getConfig();
      ProcessControl pc = nmConf.getProcessControl();
      ConcurrentFile lockFile = this.serverDir.getLockFile();
      boolean lockFileExists = lockFile.exists();
      this.log(Level.FINEST, " Lock file " + lockFile + " exists:" + lockFileExists);
      this.managedByThisHost = !nmConf.isDomainsDirRemoteSharingEnabled() || this.conf.getNMHostName() == null || this.isStartedByNMConfigured(this.conf);
      this.log(Level.FINEST, " Is the server managed by this host? " + this.managedByThisHost);
      boolean restart = false;
      if (this.isManagedByThisHost()) {
         if (pc != null && lockFileExists) {
            String pid = lockFile.readLine();
            this.log(Level.FINEST, " Checking if process from lock file is running: " + pid);
            if (this.customizer.isAlive(this, pid)) {
               this.log(Level.INFO, this.getNodeManagerTextTextFormatter().msgMonitoringServer(pid));
               this.log(Level.INFO, " Initializing ServerMonitor for " + this + " : with config : " + this.conf);
               this.createAndSetNewServerMonitor();
               this.deleteProgressFile();
               this.monitor.start(pid);
            } else {
               this.detectAndAdjustStateIfNecessary();
               if (nmConf.isCrashRecoveryEnabled()) {
                  restart = true;
               } else {
                  lockFile.delete();
               }
            }
         } else {
            this.detectAndAdjustStateIfNecessary();
         }

         boolean restartOnce = "RestartOnce".equals(this.conf.getProperty("RestartOnce"));
         if (restart || restartOnce) {
            if (restartOnce) {
               Properties serverProps = this.conf.getProperties();
               serverProps.remove("RestartOnce");
               this.conf = this.saveStartupConfig(serverProps);
            }

            this.log(Level.FINEST, " restart: " + this.getServerName() + " due to " + (restart ? "CrashRecoveryEnabled flag" : "RestartAll flag"));
            if (this.customizer.isAdminServer(this.conf)) {
               this.startServerWithWait(Process.RESTART_PROPERTIES);
            } else {
               this.recoveryNeeded = true;
            }
         }
      }

   }

   private void createAndSetNewServerMonitor() {
      Class var1 = ServerMonitor.class;
      synchronized(ServerMonitor.class) {
         this.monitor = this.createServerMonitor(this.conf);
      }
   }

   private void detectAndAdjustStateIfNecessary() {
      ConcurrentFile stateFile = this.getServerDir().getStateFile();
      if (stateFile.exists() && stateFile.canRead() && stateFile.canWrite()) {
         try {
            StateInfo stateInfo = new StateInfo();
            ServerMonitor.loadStateInfoFile(stateInfo, stateFile);
            String oldState = stateInfo.getState();
            if (StateInfo.adjustedShutDownState(stateInfo) || StateInfo.adjustedFailedStateWhenProcessDied(stateInfo)) {
               this.ensureMonitorCreated();
               this.monitor.initState(stateInfo);
               this.log(Level.INFO, this.getNodeManagerTextTextFormatter().adjustedState(this.getServerName(), oldState, stateInfo.getState()));
            }
         } catch (IOException var4) {
            this.log(Level.FINEST, "cannot adjust state file " + stateFile.toString() + " due to " + var4, var4);
         }
      } else {
         this.log(Level.FINEST, "Server " + this.getServerName() + "'s state file " + stateFile.toString() + " either does not exist or cannot read/write. skip adjusting state.");
      }

   }

   private void ensureMonitorCreated() {
      Class var1 = ServerMonitor.class;
      synchronized(ServerMonitor.class) {
         if (this.monitor == null) {
            this.monitor = this.createServerMonitor(this.conf);
         }

      }
   }

   public void recoverServer() throws ConfigException, IOException {
      if (this.recoveryNeeded) {
         this.log(Level.INFO, this.getNodeManagerTextTextFormatter().getRecoveringServerProcess());
         this.loadStartupConfig((Properties)null);
         this.startServer(Process.RESTART_PROPERTIES);
         this.recoveryNeeded = false;
      } else if (this.managedByThisHost) {
         this.ensureMonitorCreated();
         if (this.monitor.isCleanupAfterCrashNeeded()) {
            this.monitor.cleanup();
         }
      }

   }

   public boolean start(Properties props, Properties runtimeProps) throws InterruptedException, ConfigException, IOException {
      synchronized(this) {
         if (this.monitor != null && !this.monitor.isFinished()) {
            boolean notFinished = true;
            if (!this.monitor.isRunningState()) {
               synchronized(this.monitor) {
                  try {
                     this.monitor.wait(5000L);
                  } catch (InterruptedException var14) {
                  }
               }

               notFinished = !this.monitor.isFinished();
            }

            if (notFinished) {
               this.log(Level.FINEST, "start: monitor is not Finished yet. can not start again");
               return false;
            }
         }

         this.makeDir(this.serverDir.getLogsDir());
         this.makeDir(this.serverDir.getSecurityDir());
         this.makeDir(this.serverDir.getNMDataDir());
         this.makeDir(this.serverDir.getTmpDir());
         if (props == null && this.domainMgr.getNMServer().getConfig().isDomainsDirRemoteSharingEnabled()) {
            props = new Properties();
         }

         if (props == null || props.isEmpty()) {
            if (this.serverDir.getStartupConfigFile().exists()) {
               if (props != null) {
                  FileInputStream fis = null;

                  try {
                     props.load(new FileInputStream(this.serverDir.getStartupConfigFile()));
                  } finally {
                     if (fis != null) {
                        ((FileInputStream)fis).close();
                     }

                  }
               }
            } else if (this.customizer.isNoStartupConfigAWarning()) {
               this.logNoStartupConfig();
            }
         }

         if (props != null) {
            this.conf = this.saveStartupConfig(props);
            this.createAndSetNewServerMonitor();
         }

         if (this.conf == null) {
            this.loadStartupConfig((Properties)null);
            this.createAndSetNewServerMonitor();
         }

         if (this.monitor != null && this.monitor.isFinished()) {
            this.createAndSetNewServerMonitor();
         }

         this.startServer(runtimeProps);
      }

      this.monitor.waitForStarted();
      return true;
   }

   private void logNoStartupConfig() throws IOException {
      this.log(Level.WARNING, this.getNodeManagerTextTextFormatter().noStartupConfig(this.getServerName(), this.serverDir.getStartupConfigFile().getCanonicalPath()));
   }

   private void startServer(Properties runtimeProperties) throws IOException {
      String outFile = this.conf.getServerOutFile();
      if (outFile != null) {
         this.serverDir.setOutFile(outFile);
      }

      String errFile = this.conf.getServerErrFile();
      if (errFile != null) {
         this.serverDir.setErrFile(errFile);
      }

      this.ensureMonitorCreated();
      this.deleteProgressFile();
      this.monitor.start(runtimeProperties);
   }

   private void startServerWithWait(Properties props) throws IOException {
      this.log(Level.INFO, this.getNodeManagerTextTextFormatter().getRecoveringServerProcess());
      this.startServer(props);
      this.monitor.waitForStarted();
   }

   private void makeDir(File dir) throws IOException {
      Class var2 = ServerManager.class;
      synchronized(ServerManager.class) {
         if (!dir.isDirectory()) {
            this.log(Level.INFO, this.getNodeManagerTextTextFormatter().getCreatingDirectory(dir.getPath()));
            if (!dir.mkdirs() && !dir.isDirectory()) {
               throw new IOException(this.getNodeManagerTextTextFormatter().getErrorCreatingDirectory(dir.getPath()));
            }
         }

      }
   }

   public StartupConfig getStartupConfig() {
      return this.conf;
   }

   private StartupConfig loadStartupConfig(Properties props) throws ConfigException, IOException {
      if (props == null) {
         props = new Properties();
      }

      File file = this.serverDir.getStartupConfigFile();
      if (file.exists()) {
         FileInputStream is = new FileInputStream(file);

         try {
            props.load(is);
         } finally {
            is.close();
         }

         this.log(Level.INFO, this.getNodeManagerTextTextFormatter().getStartupPropertiesLoaded(file.getPath()));
      }

      this.conf = this.customizer.createStartupConfig(props);
      return this.conf;
   }

   public StartupConfig saveStartupConfig(Properties props) throws ConfigException, IOException {
      this.saveBootIdentity(props);
      if (this.domainMgr.getNMServer().getConfig().isDomainsDirRemoteSharingEnabled()) {
         props.put("NMHostName", this.getNMHostName());
      }

      this.conf = this.customizer.createStartupConfig(props);
      this.writeStartupConfig(props);
      return this.conf;
   }

   private void writeStartupConfig(Properties props) throws IOException {
      File propsFile = this.serverDir.getStartupConfigFile();
      FileOutputStream os = new FileOutputStream(propsFile);

      try {
         props.store(os, "Server startup properties");
      } finally {
         os.close();
      }

      this.log(Level.INFO, this.getNodeManagerTextTextFormatter().getStartupPropertiesSaved(propsFile.getPath()));
   }

   private void saveBootIdentity(Properties props) throws IOException {
      String user = (String)props.remove("username");
      if (user == null) {
         user = (String)props.remove("Username");
      }

      String pass = (String)props.remove("password");
      if (pass == null) {
         pass = (String)props.remove("Password");
      }

      ClearOrEncryptedService encryptor = this.domainMgr.getEncryptor();
      Properties bootProps = new Properties();
      if (user != null && pass != null) {
         bootProps.setProperty("username", encryptor.encrypt(user));
         bootProps.setProperty("password", encryptor.encrypt(pass));
         String t = (String)props.remove("TrustKeyStore");
         if (t != null) {
            bootProps.setProperty("TrustKeyStore", t);
         }

         t = (String)props.remove("CustomTrustKeyStoreFileName");
         if (t != null) {
            bootProps.setProperty("CustomTrustKeyStoreFileName", t);
         }

         t = (String)props.remove("CustomTrustKeyStoreType");
         if (t != null) {
            bootProps.setProperty("CustomTrustKeyStoreType", t);
         }

         t = (String)props.remove("CustomTrustKeyStorePassPhrase");
         if (t != null) {
            bootProps.setProperty("CustomTrustKeyStorePassPhrase", t);
         }

         t = (String)props.remove("JavaStandardTrustKeyStorePassPhrase");
         if (t != null) {
            bootProps.setProperty("JavaStandardTrustKeyStorePassPhrase", t);
         }

         File bootFile = this.serverDir.getNMBootIdentityFile();
         FileOutputStream os = new FileOutputStream(bootFile);

         try {
            bootProps.store(os, (String)null);
         } finally {
            os.close();
         }

         this.log(Level.INFO, this.getNodeManagerTextTextFormatter().getBootIdentitySaved(bootFile.getPath()));
      }

   }

   public String getState() {
      if (!this.managedByThisHost && !this.isServerIPConfigured()) {
         return null;
      } else {
         this.ensureMonitorCreated();
         return this.monitor.getCurrentStateInfo().getState();
      }
   }

   public boolean isServerIPConfigured() {
      if (this.conf == null) {
         this.log(Level.INFO, " Config is null ");
         return false;
      } else {
         return this.conf == null || this.conf.getServerIPList() != null;
      }
   }

   public synchronized boolean kill(Properties prop) throws IOException, InterruptedException {
      if (this.monitor != null && !this.monitor.isFinished()) {
         this.monitor.kill(prop);
         return true;
      } else {
         this.log(Level.FINEST, "kill: monitor is " + (this.monitor == null ? "null" : "Finished") + ". cannot kill the server.");
         return false;
      }
   }

   public synchronized void printThreadDump(Properties prop) throws IOException, InterruptedException {
      if (this.monitor != null && !this.monitor.isFinished()) {
         this.monitor.printThreadDump(prop);
      } else {
         this.log(Level.FINEST, "printThreadDump: monitor is " + (this.monitor == null ? "null" : "Finished") + ". cannot print thread dump.");
         throw new RuntimeException("No active server monitor found.");
      }
   }

   public synchronized String getProgress(Properties prop, long waitTime) throws IOException, InterruptedException {
      File progressFile = this.serverDir.getProgressFile();
      this.waitForFile(progressFile, waitTime);
      ProgressData progressData = new ProgressData(progressFile);

      String var6;
      try {
         var6 = progressData.read();
      } finally {
         progressData.close();
      }

      return var6;
   }

   public synchronized boolean softRestart(Properties prop) throws UnsupportedOperationException, IOException {
      if (this.monitor != null && !this.monitor.isFinished() && this.monitor.isStarted()) {
         this.monitor.softRestart(prop);
         return true;
      } else {
         this.log(Level.FINEST, "softRestart: monitor is " + (this.monitor == null ? "null" : "Finished") + ". cannot softRestart the server.");
         return false;
      }
   }

   public DomainManager getDomainManager() {
      return this.domainMgr;
   }

   public Customizer getCustomizer() {
      return this.customizer;
   }

   public String getServerName() {
      return this.serverName;
   }

   public ServerDir getServerDir() {
      return this.serverDir;
   }

   public void log(Level level, String msg, Throwable thrown) {
      if (this.getLogger().isLoggable(level)) {
         LogRecord lr = new LogRecord(level, msg);
         lr.setParameters(this.logParams);
         if (thrown != null) {
            lr.setThrown(thrown);
         }

         this.getLogger().log(lr);
      }
   }

   public void remove() throws IOException {
      if (this.monitor != null && this.monitor.isStarted() && !this.monitor.isFinished()) {
         this.log(Level.FINEST, "start: monitor is not Finished yet. can not remove");
         throw new IllegalStateException(this.getNodeManagerTextTextFormatter().getServerAlreadyRunningOrStarting(this.getServerName(), this.serverType));
      } else {
         this.serverDir.remove();
      }
   }

   public void initState() throws IOException {
      String currentState = this.getState();
      if (currentState != null && !"UNKNOWN".equals(currentState)) {
         this.log(Level.FINE, "Will not initialize state when current state is set: " + currentState);
      } else {
         StateInfo stateInfo = new StateInfo();
         stateInfo.setState("SHUTDOWN");
         File nmDataDir = this.serverDir.getNMDataDir();
         if (!nmDataDir.exists()) {
            this.makeDir(nmDataDir);
         }

         try {
            this.monitor.initState(stateInfo);
         } catch (IOException var5) {
            this.log(Level.FINEST, "initState file " + this.getServerDir().getStateFile() + " was not saved! " + var5);
         }
      }

   }

   private void log(Level level, String msg) {
      this.log(level, msg, (Throwable)null);
   }

   private String getNMHostName() {
      String nmHostName = this.domainMgr.getNMServer().getConfig().getListenAddress();

      try {
         if (nmHostName != null && !nmHostName.equals("localhost") && !nmHostName.equals("127.0.0.1") && !nmHostName.equals("0.0.0.0")) {
            nmHostName = InetAddress.getByName(nmHostName).getCanonicalHostName();
         } else {
            nmHostName = InetAddress.getLocalHost().getCanonicalHostName();
         }
      } catch (Exception var3) {
      }

      return nmHostName;
   }

   private NodeManagerTextTextFormatter getNodeManagerTextTextFormatter() {
      return ServerManager.LoggerHolder.getInstance().getTextFormatter();
   }

   private Logger getLogger() {
      return ServerManager.LoggerHolder.getInstance().getLogger();
   }

   private boolean isStartedByNMConfigured(StartupConfig conf) {
      return this.getNMHostName().equals(conf.getNMHostName());
   }

   ServerMonitorI getMonitor() {
      return this.monitor;
   }

   boolean isRecoveryNeeded() {
      return this.recoveryNeeded;
   }

   public boolean isManagedByThisHost() {
      return this.managedByThisHost;
   }

   public boolean shutdownForRestart() throws Exception {
      synchronized(this) {
         if (this.monitor != null && !this.monitor.isFinished()) {
            Properties props = this.conf.getProperties();
            props.setProperty("RestartOnce", "RestartOnce");
            this.writeStartupConfig(props);
            this.log(Level.FINEST, "shutdown for restart has saved RestartOnce");
            this.monitor.kill((Properties)null);
            return true;
         } else {
            this.log(Level.FINEST, "shutdownForRestart: monitor is " + (this.monitor == null ? "null" : "Finished") + ". cannot kill the server.");
            return false;
         }
      }
   }

   public boolean isServerConfigured() {
      if (this.getServerDir() != null) {
         ServerDir srvDir = this.getServerDir();
         File bootFile = srvDir.getBootIdentityFile();
         File nmBootFile = srvDir.getNMBootIdentityFile();
         if ((bootFile.exists() || nmBootFile.exists()) && this.getStartupConfig() != null) {
            return true;
         }
      }

      return false;
   }

   private static boolean fileExists(File waitForMe) {
      return waitForMe.exists() && waitForMe.canRead() && waitForMe.length() > 0L;
   }

   private boolean deleteProgressFile() {
      File progressFile = this.serverDir.getProgressFile();
      return fileExists(progressFile) ? progressFile.delete() : false;
   }

   private void waitForFile(File waitForMe, long maxTime) throws IOException {
      long elapsedTime = 0L;

      boolean foundFile;
      for(foundFile = fileExists(waitForMe); !foundFile && elapsedTime < maxTime; foundFile = fileExists(waitForMe)) {
         long currentElapsedTime = System.currentTimeMillis();

         try {
            Thread.sleep(50L);
         } catch (InterruptedException var10) {
            throw new RuntimeException(var10);
         }

         currentElapsedTime = System.currentTimeMillis() - currentElapsedTime;
         elapsedTime += currentElapsedTime;
      }

      if (!foundFile) {
         throw new IOException("Could not find progress data for " + this.serverName + " after waiting " + maxTime + " milliseconds");
      }
   }

   private static class LoggerHolder {
      private static final LoggerHolder instance = new LoggerHolder();
      private static final Logger nmLog = Logger.getLogger("weblogic.nodemanager");
      private static final NodeManagerTextTextFormatter nmText = NodeManagerTextTextFormatter.getInstance();

      public static LoggerHolder getInstance() {
         return instance;
      }

      public NodeManagerTextTextFormatter getTextFormatter() {
         return nmText;
      }

      public Logger getLogger() {
         return nmLog;
      }
   }
}
