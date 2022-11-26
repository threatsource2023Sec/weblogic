package weblogic.nodemanager.server;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import weblogic.nodemanager.util.ConcurrentFile;
import weblogic.nodemanager.util.Platform;

public class ServerDir extends File {
   private static final long serialVersionUID = -4275216369335919512L;
   private DomainDir domainDir;
   private File logsDir;
   private File securityDir;
   private File nmDataDir;
   private File dataDir;
   private String outFileName;
   private String errFileName;
   private File progressFile;
   public static final String STARTUP_CONFIG_FILE = "startup.properties";
   public static final String BOOT_IDENTITY_FILE_NAME = "boot.properties";
   public static final String NODEMANAGER_DIR_NAME = "nodemanager";
   public static final String LOCK_FILE_EXT = ".lck";
   public static final String URL_FILE_EXT = ".url";
   public static final String STATE_FILE_EXT = ".state";
   public static final String PID_FILE_EXT = ".pid";
   public static final String LOG_FILE_EXT = ".log";
   public static final String OUT_FILE_EXT = ".out";
   public static final String CONFIG_PREV = "config_prev";
   public static final String DOMAIN_BAK = "domain_bak";
   private static final String PROGRESS_EXT = ".prg";
   public static final Logger nmLog = Logger.getLogger("weblogic.nodemanager");

   public ServerDir(DomainDir domainDir, String serverName) {
      this(domainDir, domainDir.getServersDir(), serverName);
   }

   public ServerDir(DomainDir domainDir, File serversDir, String serverName) {
      super(serversDir, serverName);
      this.domainDir = domainDir;
      this.dataDir = new File(this.getPath(), "data");
      this.nmDataDir = new File(this.dataDir, "nodemanager");
      this.logsDir = new File(this.getPath(), "logs");
      this.securityDir = new File(this.getPath(), "security");
      this.progressFile = new File(this.nmDataDir, this.getName() + ".prg");
   }

   public DomainDir getDomainDir() {
      return this.domainDir;
   }

   public ConcurrentFile getLockFile() {
      File f = new File(this.nmDataDir, this.getName() + ".lck");
      return Platform.getConcurrentFile(f.getPath());
   }

   public ConcurrentFile getStateFile() {
      File f = new File(this.nmDataDir, this.getName() + ".state");
      return Platform.getConcurrentFile(f.getPath());
   }

   public ConcurrentFile getPidFile() {
      File f = new File(this.nmDataDir, this.getName() + ".pid");
      return Platform.getConcurrentFile(f.getPath());
   }

   public ConcurrentFile getURLFile() {
      File f = new File(this.nmDataDir, this.getName() + ".url");
      return Platform.getConcurrentFile(f.getPath());
   }

   public File getLogFile() {
      return new File(this.logsDir, this.getName() + ".log");
   }

   public File getOutFile() {
      if (this.outFileName == null) {
         return new File(this.logsDir, this.getName() + ".out");
      } else {
         File file = new File(this.outFileName);
         if (!file.exists()) {
            File dir = file.getParentFile();
            if (dir != null && !dir.exists()) {
               dir.mkdirs();
            }
         }

         return file;
      }
   }

   public static String getOutFile(String serverName) {
      return (new File(new File(new File("servers", serverName), "logs"), serverName + ".out")).getPath();
   }

   public File getErrFile() {
      if (this.errFileName == null) {
         return new File(this.logsDir, this.getName() + ".out");
      } else {
         File file = new File(this.errFileName);
         if (!file.isAbsolute()) {
            file = new File(this.logsDir, this.errFileName);
         }

         return file;
      }
   }

   public File getStartupConfigFile() {
      return new File(this.nmDataDir, "startup.properties");
   }

   public File getBootIdentityFile() {
      return new File(this.securityDir, "boot.properties");
   }

   public File getNMBootIdentityFile() {
      return new File(this.nmDataDir, "boot.properties");
   }

   public File getTmpDir() {
      return new File(this.getPath(), "tmp");
   }

   public File getDomainBakDir() {
      return new File(this.domainDir.getServersDir(), "domain_bak");
   }

   public File getConfigPrevDir() {
      return new File(this.getDomainBakDir(), "config_prev");
   }

   public File getLogsDir() {
      return this.logsDir;
   }

   public File getSecurityDir() {
      return this.securityDir;
   }

   public File getNMDataDir() {
      return this.nmDataDir;
   }

   public File getDataDir() {
      return this.dataDir;
   }

   public void setOutFile(String outFile) {
      this.outFileName = outFile;
   }

   public void setErrFile(String errFile) {
      this.errFileName = errFile;
   }

   public File getProgressFile() {
      return this.progressFile;
   }

   public void remove() {
      this.removeFile(this.getProgressFile());
      this.removeFile(this.getLockFile());
      this.removeFile(this.getStateFile());
      this.removeFile(this.getPidFile());
      this.removeFile(this.getURLFile());
      this.removeFile(this.getLogFile());
      this.removeFile(this.getOutFile());
      this.removeFile(this.getErrFile());
      this.removeFile(this.getStartupConfigFile());
      this.removeFile(this.getBootIdentityFile());
      this.removeDir(this.getTmpDir());
      this.removeDir(this.getLogsDir());
      this.removeDir(this.getSecurityDir());
      this.removeDir(this.getNMDataDir());
      this.removeDir(this.getDataDir());
      this.removeDir(this);
   }

   private void removeFile(File toBeRemoved) {
      if (toBeRemoved.exists()) {
         String path = toBeRemoved.getPath();
         boolean removed = toBeRemoved.delete();
         nmLog.log(Level.FINEST, (removed ? "Removed " : "Unable to remove") + " file at: " + path);
      }
   }

   private void removeDir(File dirToRemove) {
      if (dirToRemove.exists()) {
         File[] files = dirToRemove.listFiles();
         if (files != null) {
            File[] var3 = files;
            int var4 = files.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               File tmp = var3[var5];
               this.removeFile(tmp);
            }
         }

         this.removeFile(dirToRemove);
      }
   }
}
