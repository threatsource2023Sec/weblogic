package weblogic.nodemanager.server;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import weblogic.nodemanager.NodeManagerTextTextFormatter;
import weblogic.nodemanager.common.ConfigException;
import weblogic.nodemanager.common.StartupConfig;
import weblogic.nodemanager.common.StateInfo;
import weblogic.nodemanager.plugin.ProcessManagementPlugin;
import weblogic.nodemanager.util.ConcurrentFile;
import weblogic.nodemanager.util.Drainer;
import weblogic.nodemanager.util.Platform;
import weblogic.nodemanager.util.ProcessControl;

class ServerMonitor implements ServerMonitorI, Runnable {
   private final ServerManagerI serverMgr;
   private final ConcurrentFile lockFile;
   private final ConcurrentFile stateFile;
   private final StateInfo stateInfo;
   private StartupConfig conf;
   private ProcessManagementPlugin.Process proc;
   private boolean started;
   private boolean killing;
   private boolean finished;
   private int stateCheckCount;
   private boolean killed;
   private boolean startupAborted;
   private long lastBaseStartTime;
   private volatile Customizer.InstanceCustomizer customizer;
   private final ProcessControl processControl;
   private volatile IOException saveStateFailedIOException = null;
   private int stateCheckInterval = 500;
   private volatile long stateInfoTimeStamp = 0L;
   private static final NodeManagerTextTextFormatter nmText = NodeManagerTextTextFormatter.getInstance();
   private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy_MM_dd_'T'HHmmss");
   private static String COMSPEC = null;
   private static boolean IS_WINDOWS;

   public ServerMonitor(ServerManagerI sm, StartupConfig conf) {
      this.serverMgr = sm;
      this.lockFile = sm.getServerDir().getLockFile();
      this.stateFile = sm.getServerDir().getStateFile();
      this.stateInfo = new StateInfo();
      this.stateCheckInterval = sm.getDomainManager().getNMServer().getConfig().getStateCheckInterval();
      this.conf = conf;
      this.processControl = this.getProcessControl();
      this.customizer = this.serverMgr.getCustomizer().createInstanceCustomizer(sm, conf);
      this.finished = true;
   }

   private synchronized Thread startMonitor(ProcessManagementPlugin.Process givenProcess) throws IOException {
      this.setProcess(givenProcess);
      Thread t = new Thread(this, "server monitor");
      t.start();
      return t;
   }

   private void prepareForMonitoring() {
      this.killed = false;
      this.finished = false;
      this.started = false;
      this.startupAborted = false;
      this.killing = false;
   }

   public synchronized Thread start(Properties prop) throws IOException {
      this.prepareForMonitoring();
      ProcessManagementPlugin.Process theProcess = this.startProcess(prop);
      return this.startMonitor(theProcess);
   }

   public synchronized Thread start(String pid) throws IOException {
      ProcessManagementPlugin.Process theProcess = this.customizer.createProcess(pid);
      this.prepareForMonitoring();
      if (this.lastBaseStartTime == 0L) {
         this.lastBaseStartTime = System.currentTimeMillis() / 1000L;
      }

      return this.startMonitor(theProcess);
   }

   public synchronized boolean isFinished() {
      return this.finished;
   }

   public synchronized boolean isStarted() {
      return this.started;
   }

   public synchronized boolean isRunningState() {
      return "RUNNING".equals(this.stateInfo.getState()) || "ADMIN".equals(this.stateInfo.getState());
   }

   private synchronized boolean isKilled() {
      return this.killed;
   }

   public boolean isCleanupAfterCrashNeeded() throws IOException {
      if (this.stateInfo == null || this.stateInfo.getState() == null) {
         this.loadStateInfo();
      }

      return this.stateInfo != null && this.stateInfo.getState() != null && this.stateInfo.getState().equals("RUNNING") && this.proc == null;
   }

   public void cleanup() {
      try {
         this.customizer.afterCrashCleanUp();
      } catch (Throwable var2) {
         this.log(Level.FINEST, "The server cleanup failed.", var2);
      }

      ServerDir serverDir = this.serverMgr.getServerDir();
      serverDir.getLockFile().delete();
      serverDir.getStateFile().delete();
      serverDir.getPidFile().delete();
      serverDir.getURLFile().delete();
   }

   public synchronized boolean isStartupAborted() {
      return this.startupAborted;
   }

   public StateInfo getCurrentStateInfo() {
      if (!this.stateFile.exists()) {
         this.stateInfo.setTemporaryState("UNKNOWN");
      } else {
         try {
            this.loadStateInfo();
         } catch (IOException var8) {
            this.fine("caught exception attempting to update and load state: " + var8);
         }

         if (this.saveStateFailedIOException != null) {
            this.fine("previous state saving failed, need to rebuild the state file");
            boolean isServerAlive = false;
            String pid = null;
            if (this.serverMgr.isManagedByThisHost() && this.lockFile.exists() && this.processControl != null) {
               try {
                  pid = this.lockFile.readLine();
                  isServerAlive = this.serverMgr.getCustomizer().isAlive(this.serverMgr, pid);
               } catch (IOException var7) {
                  isServerAlive = false;
               }
            }

            if (!isServerAlive) {
               if (!StateInfo.adjustedShutDownState(this.stateInfo) && !StateInfo.adjustedFailedStateWhenProcessDied(this.stateInfo)) {
                  this.saveStateFailedIOException = null;
               } else {
                  try {
                     this.saveStateFile();
                  } catch (IOException var6) {
                  }
               }

               this.lockFile.delete();
            } else {
               if (!this.isRunningState()) {
                  this.stateInfo.setState("UNKNOWN");

                  try {
                     this.saveStateFile();
                  } catch (IOException var5) {
                  }
               }

               if (this.isFinished() && pid != null) {
                  try {
                     this.log(Level.WARNING, nmText.msgFailedSavingStateForAliveServer(pid));
                     this.start(pid);
                  } catch (IOException var4) {
                     this.fine("caught exception attempting to restart monitor thread:" + var4);
                  }
               }
            }
         }
      }

      return this.stateInfo;
   }

   public synchronized void softRestart(Properties prop) throws IOException {
      if (this.proc != null) {
         this.proc.softRestart(prop);
      }
   }

   public synchronized boolean kill(Properties prop) throws InterruptedException, IOException {
      if (this.proc == null) {
         return false;
      } else {
         long timeout = 0L;
         if (this.processControl == null) {
            this.fine("Killing non native process");
            this.proc.destroy(prop);
            this.killing = true;
            NMServerConfig nmConf = this.serverMgr.getDomainManager().getNMServer().getConfig();
            if (nmConf.isStartScriptEnabled()) {
               timeout = nmConf.getProcessDestroyTimeout();
            }
         } else if (this.proc.isAlive()) {
            ConcurrentFile pidFile = this.serverMgr.getServerDir().getPidFile();

            String pid;
            try {
               pid = pidFile.readLine();
               this.fine("Read process id of " + pid);
            } catch (IOException var10) {
               return false;
            }

            this.fine("Calling kill on the process control for " + pid);
            if (!this.processControl.killProcess(pid)) {
               this.killing = true;
               this.fine("Process control killProcess return false");
               return false;
            }
         }

         this.killed = true;
         this.fine("Waiting for server to be killed");
         long begin = 0L;
         long to = timeout;
         if (timeout != 0L) {
            begin = System.currentTimeMillis();
         }

         while(!this.finished) {
            this.wait(to);
            if (timeout != 0L) {
               if (this.finished) {
                  break;
               }

               long end = System.currentTimeMillis();
               to -= end - begin;
               if (to <= 0L) {
                  this.killing = false;
                  throw new IOException(nmText.getProcessDestroyFailed(timeout));
               }

               begin = end;
            }
         }

         this.killing = false;
         this.fine("Finished killing process");
         return true;
      }
   }

   public synchronized void printThreadDump(Properties prop) throws InterruptedException, IOException {
      if (this.proc == null) {
         throw new RuntimeException("No process found");
      } else {
         String pid = null;
         if (this.proc.isAlive()) {
            try {
               ConcurrentFile pidFile = this.serverMgr.getServerDir().getPidFile();
               pid = pidFile.readLine();
               if (pid == null) {
                  pid = this.calculateProcessID(this.serverMgr.getServerName());
                  this.fine("Calculated process id " + pid + ". Read process id was null");
               } else {
                  this.fine("Read process id of " + pid);
               }
            } catch (Exception var10) {
               pid = this.calculateProcessID(this.serverMgr.getServerName());
               this.fine("Calculated process id " + pid + ". Process id could not be read " + var10.getMessage());
            }

            if (pid == null) {
               throw new RuntimeException("Cannot determine process id.");
            } else {
               Object outputStream;
               ProcessBuilder processBuilder;
               if (Platform.isUnix()) {
                  processBuilder = new ProcessBuilder(new String[]{"kill", "-QUIT", pid});
                  outputStream = new ByteArrayOutputStream();
                  this.fine("Calling kill -QUIT " + pid + "..");
               } else {
                  String command = this.getJStackCommand(this.serverMgr.getDomainManager().getNMServer().getConfig().getWlsStartupJavaHome());
                  processBuilder = new ProcessBuilder(new String[]{command, pid});
                  File jstackOutput = new File(this.serverMgr.getServerDir().getLogsDir(), this.serverMgr.getServerName() + "_jstack_" + dateFormatter.format(new Date()) + ".txt");
                  outputStream = new FileOutputStream(jstackOutput);
                  this.fine("Calling " + command + " " + pid + "..");
               }

               Process proc = processBuilder.start();
               proc.getOutputStream().close();
               Drainer outDrainer = new Drainer(proc.getInputStream(), (OutputStream)outputStream);
               outDrainer.start();
               ByteArrayOutputStream error = new ByteArrayOutputStream();
               Drainer errDrainer = new Drainer(proc.getErrorStream(), error);
               errDrainer.start();
               int retVal = proc.waitFor();
               outDrainer.join();
               errDrainer.join();
               if (retVal != 0) {
                  throw new IOException(error.toString());
               }
            }
         } else {
            throw new RuntimeException("Process is dead.");
         }
      }
   }

   String getJStackCommand(String javaHome) {
      Path path = FileSystems.getDefault().getPath(javaHome);
      if (path.endsWith("jre")) {
         path = path.getParent();
      }

      return path + File.separator + "bin" + File.separator + "jstack";
   }

   private String calculateProcessID(String serverName) throws IOException, InterruptedException {
      if (Platform.isWindows() && COMSPEC == null) {
         throw new RuntimeException("Cannot determine pid: COMSPEC variable not present in the environment.");
      } else {
         ProcessBuilder processBuilder = null;
         if (Platform.isWindows()) {
            processBuilder = new ProcessBuilder(new String[]{COMSPEC, "/C", "wmic.exe", "PROCESS", "get", "Processid,CommandLine", "/VALUE"});
         } else {
            if (!Platform.isUnix()) {
               throw new UnsupportedOperationException("Method calculateProcessID not implemented for this platform.");
            }

            processBuilder = new ProcessBuilder(new String[]{"/bin/sh", "-c", "ps -eo pid,command | grep java"});
         }

         Process proc = processBuilder.start();
         proc.getOutputStream().close();
         PIDDrainer pidDrainer = Platform.isWindows() ? new PIDDrainerWindows(proc.getInputStream(), this.serverMgr.getServerName()) : new PIDDrainerUnix(proc.getInputStream(), this.serverMgr.getServerName());
         ((PIDDrainer)pidDrainer).start();
         ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
         Drainer errDrainer = new Drainer(proc.getErrorStream(), errorStream);
         errDrainer.start();
         int retVal = proc.waitFor();
         ((PIDDrainer)pidDrainer).join();
         errDrainer.join();
         if (retVal != 0) {
            throw new RuntimeException(errorStream.toString());
         } else {
            return ((PIDDrainer)pidDrainer).getProcessID();
         }
      }
   }

   public void run() {
      try {
         this.runMonitor();
      } catch (Throwable var4) {
         this.severe(nmText.msgErrorUnexpected(), var4);
      }

      synchronized(this) {
         this.fine("runMonitor returned, setting finished=true and notifying waiters");
         this.finished = true;
         this.notifyAll();
      }
   }

   public synchronized void waitForStarted() throws IOException {
      while(true) {
         if (!this.isStarted() && !this.isFinished()) {
            try {
               this.wait();
               continue;
            } catch (InterruptedException var2) {
            }
         }

         if (this.isStarted() && !this.isStartupAborted()) {
            return;
         }

         throw new IOException(nmText.getServerFailedToStart());
      }
   }

   private void runMonitor() throws IOException, InterruptedException, ConfigException {
      ProcessManagementPlugin.Process theProcess;
      synchronized(this) {
         theProcess = this.proc;
      }

      int count = 0;

      do {
         String pid = theProcess.getProcessId();
         boolean startupFailed = false;
         if (pid != null) {
            this.lockFile.writeLine(pid);
         }

         this.fine("Wrote process id " + pid);

         while(theProcess.isAlive()) {
            Thread.sleep((long)this.stateCheckInterval);
            this.checkStateInfo();
         }

         this.info(nmText.serverIsNotAlive(this.serverMgr.getServerName(), pid));
         theProcess.waitForProcessDeath();
         if (this.getRotateOnShutdown()) {
            LogFileRotationUtil.rotateServerFiles(this.serverMgr, this.conf);
         }

         this.fine("Process died.");
         this.checkStateInfo();
         this.lockFile.delete();
         if (this.isKilled()) {
            this.fine("Process is killed " + pid + " with " + nmText.msgKilled());
            this.info(nmText.msgKilled());
            this.stateInfo.setState("SHUTDOWN");
            break;
         }

         if (!this.stateInfo.isStarted()) {
            this.info(nmText.getStartupFailedRestartable());
            startupFailed = true;
         }

         if (StateInfo.adjustedShutDownState(this.stateInfo)) {
            this.info(nmText.msgShutDown());
            break;
         }

         this.stateInfo.setState("FAILED");
         this.fine("get latest startup configuration before deciding/trying to restart the server");
         this.conf = this.serverMgr.getStartupConfig();
         if (!this.conf.isAutoRestart()) {
            this.info(nmText.msgWarnIgnoreFailed());
            this.stateInfo.setState("FAILED_NOT_RESTARTABLE");
            break;
         }

         long curTime = System.currentTimeMillis() / 1000L;
         if (curTime - this.lastBaseStartTime > (long)this.conf.getRestartInterval() && !startupFailed) {
            count = 0;
            this.lastBaseStartTime = 0L;
         }

         ++count;
         if (count > this.conf.getRestartMax()) {
            this.info(nmText.msgWarnRestartMax());
            this.stateInfo.setState("FAILED_NOT_RESTARTABLE");
            break;
         }

         this.saveStateFile();
         this.info(nmText.msgInfoRestarting(count));
         if (this.conf.getRestartDelaySeconds() > 0) {
            this.info(nmText.getSleepForRestartDelay(this.conf.getRestartDelaySeconds()));
            this.stateInfo.setState("FAILED");
            Thread.sleep((long)(this.conf.getRestartDelaySeconds() * 1000));
            if (this.isKilled()) {
               break;
            }
         }

         theProcess = this.startProcess(weblogic.nodemanager.plugin.ProcessManagementPlugin.Process.RESTART_PROPERTIES);
      } while(theProcess != null);

      this.saveStateFile();
      this.setProcess((ProcessManagementPlugin.Process)null);
   }

   private void saveStateFile() throws IOException {
      try {
         this.stateInfo.save(this.stateFile);
         this.saveStateFailedIOException = null;
      } catch (IOException var2) {
         this.fine("caught exception attempting to save state: " + var2);
         this.saveStateFailedIOException = var2;
         throw var2;
      }
   }

   private boolean getRotateOnShutdown() {
      String propName = "weblogic.nodemanager.RotateServerOutOnShutdown";
      return IS_WINDOWS && System.getProperty(propName) == null ? true : Boolean.getBoolean(propName);
   }

   private void setProcess(ProcessManagementPlugin.Process process) {
      synchronized(this) {
         this.proc = process;
      }
   }

   synchronized ProcessManagementPlugin.Process startProcess(Properties props) throws IOException {
      boolean saveState = false;

      try {
         if (this.killed) {
            return null;
         } else {
            this.customizer = this.serverMgr.getCustomizer().createInstanceCustomizer(this.serverMgr, this.conf);
            this.customizer.preStart();
            ProcessManagementPlugin.Process theProcess = this.customizer.createProcess();
            saveState = true;
            this.stateInfo.set("STARTING", false, false);
            this.saveStateFile();
            theProcess.start(props);
            if (this.lastBaseStartTime == 0L) {
               this.lastBaseStartTime = System.currentTimeMillis() / 1000L;
            }

            this.setProcess(theProcess);
            this.notifyAll();
            return theProcess;
         }
      } catch (Throwable var5) {
         this.finished = true;
         if (saveState) {
            this.stateInfo.set("FAILED_NOT_RESTARTABLE", false, true);
            this.saveStateFile();
         }

         IOException ioe = var5 instanceof IOException ? (IOException)var5 : (IOException)(new IOException()).initCause(var5);
         throw ioe;
      }
   }

   public static void loadStateInfoFile(StateInfo tmpInfo, ConcurrentFile tmpStateFile) throws IOException {
      tmpInfo.load(tmpStateFile);
   }

   public void initState(StateInfo tmpInfo) throws IOException {
      this.stateInfo.set(tmpInfo.getState(), tmpInfo.isStarted(), tmpInfo.isFailed());
      this.saveStateFile();
   }

   private synchronized void checkStateInfo() throws IOException {
      boolean oldStarted = this.stateInfo.isStarted();
      this.loadStateInfo();
      ++this.stateCheckCount;
      if (!oldStarted && this.stateInfo.isStarted()) {
         this.info(nmText.serverStarted(this.serverMgr.getServerName()));
      }

      if (this.killing && this.stateCheckCount * this.stateCheckInterval % 30000 == 0) {
         this.fine("Server being killed, last state is " + this.stateInfo);
      }

      if (this.stateInfo.isStartupAborted()) {
         this.startupAborted = true;
      }

      if (!this.started && this.stateInfo.isStarted()) {
         this.started = true;
         this.notifyAll();
      }

   }

   private void loadStateInfo() throws IOException {
      long tmpModified = this.stateFile.lastModified();
      if (tmpModified != this.stateInfoTimeStamp) {
         try {
            synchronized(this.customizer) {
               this.customizer.ensureStateInfo(this.stateInfo, this.stateFile);
            }

            loadStateInfoFile(this.stateInfo, this.stateFile);
         } catch (FileNotFoundException var6) {
         }
      }
   }

   private void log(Level level, String msg, Throwable thrown) {
      this.serverMgr.log(level, msg, thrown);

      try {
         synchronized(this) {
            if (this.proc != null && this.proc.isAlive()) {
               return;
            }
         }
      } catch (Throwable var7) {
         this.serverMgr.log(Level.FINEST, "SystemMonitor.log(): Unexcepted exception from plug isAlive(). skip logging message to system compoment log file:" + var7, var7);
         return;
      }

      this.customizer.log(level, msg, thrown);
   }

   private void log(Level level, String msg) {
      this.log(level, msg, (Throwable)null);
   }

   private void info(String msg) {
      this.log(Level.INFO, msg);
   }

   private void fine(String msg) {
      this.log(Level.FINEST, msg);
   }

   private void severe(String msg, Throwable e) {
      this.log(Level.SEVERE, msg, e);
   }

   private ProcessControl getProcessControl() {
      if (this.serverMgr.getCustomizer().isSystemComponent()) {
         return null;
      } else {
         NMServerConfig sc = this.serverMgr.getDomainManager().getNMServer().getConfig();
         return sc.isNativeVersionEnabled() ? sc.getProcessControl() : null;
      }
   }

   static {
      IS_WINDOWS = File.separatorChar == '\\';
      Map env = System.getenv();
      Iterator var1 = env.keySet().iterator();

      while(var1.hasNext()) {
         String property = (String)var1.next();
         if (property.equalsIgnoreCase("COMSPEC")) {
            COMSPEC = (String)env.get(property);
         }
      }

   }

   private class PIDDrainerUnix extends PIDDrainer {
      PIDDrainerUnix(InputStream is, String serverName) {
         super(is, serverName);
      }

      public String determineProcessID(String cmdLine, BufferedReader reader) {
         return cmdLine.substring(0, cmdLine.indexOf(" "));
      }
   }

   private class PIDDrainerWindows extends PIDDrainer {
      PIDDrainerWindows(InputStream is, String serverName) {
         super(is, serverName);
      }

      public boolean matchServerProcess(String line) {
         return line.startsWith("CommandLine=") && super.matchServerProcess(line);
      }

      public String determineProcessID(String cmdLine, BufferedReader reader) throws IOException {
         String line;
         for(line = reader.readLine(); line != null && !line.startsWith("ProcessId="); line = reader.readLine()) {
         }

         return line != null && line.startsWith("ProcessId=") ? line.substring("ProcessId=".length()) : null;
      }
   }

   private abstract class PIDDrainer extends Thread {
      private InputStream in;
      private String serverName;
      private String processID;

      PIDDrainer(InputStream is, String serverName) {
         this.in = is;
         this.serverName = serverName;
         this.processID = null;
      }

      public String getProcessID() {
         return this.processID;
      }

      public boolean matchServerProcess(String line) {
         return line.contains("java") && line.contains("weblogic.Server") && (line.contains("-Dweblogic.Name=" + this.serverName) || line.contains("-Dweblogic.name=" + this.serverName));
      }

      public abstract String determineProcessID(String var1, BufferedReader var2) throws IOException;

      public void run() {
         InputStreamReader isReader = null;
         BufferedReader bufferedReader = null;

         try {
            isReader = new InputStreamReader(this.in);
            bufferedReader = new BufferedReader(isReader);

            for(String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
               if (this.matchServerProcess(line)) {
                  this.processID = this.determineProcessID(line, bufferedReader);
                  return;
               }
            }

         } catch (IOException var14) {
            ServerMonitor.this.fine("caught exception attempting to determine pid: " + var14);
         } finally {
            this.closeReader(bufferedReader);
            this.closeReader(isReader);

            try {
               this.in.close();
            } catch (IOException var13) {
            }

         }
      }

      public void closeReader(Reader reader) {
         try {
            if (reader != null) {
               reader.close();
            }
         } catch (IOException var3) {
         }

      }
   }
}
