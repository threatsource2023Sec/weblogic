package weblogic.nodemanager.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import weblogic.nodemanager.NodeManagerTextTextFormatter;
import weblogic.nodemanager.common.StartupConfig;
import weblogic.nodemanager.util.ProcessControl;
import weblogic.nodemanager.util.UnixProcessControl;

public class LogFileRotationUtil {
   private static final Logger nmLog = Logger.getLogger("weblogic.nodemanager");
   private static final NodeManagerTextTextFormatter nmText = NodeManagerTextTextFormatter.getInstance();

   static void rotateServerFiles(ServerManagerI serverManager, StartupConfig conf) throws IOException {
      DomainManager domainManager = serverManager.getDomainManager();
      String domainName = domainManager.getDomainName();
      String serverName = serverManager.getServerName();
      ServerDir serverDir = serverManager.getServerDir();
      File outFile = serverDir.getOutFile();
      File errFile;
      if (outFile.exists()) {
         errFile = rotateLogFile(outFile);
         info(domainName, serverName, nmText.getRotatedOutputLog(errFile.getPath()));
      }

      serverDir.getOutFile().createNewFile();
      changeOwnership(serverManager, conf, serverDir.getOutFile());
      serverDir.getDomainDir().getConfigFile().createNewFile();
      changeOwnership(serverManager, conf, serverDir.getDomainDir().getConfigFile());
      errFile = serverDir.getErrFile();
      boolean useDifferentFiles = !errFile.equals(outFile);
      if (useDifferentFiles) {
         if (errFile.exists()) {
            File rotated = rotateLogFile(errFile);
            info(domainName, serverName, nmText.getRotatedMsg(rotated.getPath()));
         }

         serverDir.getErrFile().createNewFile();
         changeOwnership(serverManager, conf, serverDir.getErrFile());
      } else {
         info(domainName, serverName, nmText.getRotatedErrorLogMsg());
      }

   }

   private static File rotateLogFile(File file) throws IOException {
      if (!file.exists()) {
         throw new FileNotFoundException(file.toString());
      } else {
         File rotatedFile = getRotatedFile(file);
         rotatedFile.delete();
         if (!file.renameTo(rotatedFile) && !file.canWrite()) {
            throw new IOException(nmText.getRotationError(file.toString(), rotatedFile.toString()));
         } else {
            return rotatedFile;
         }
      }
   }

   private static File getRotatedFile(File logFile) {
      File dir = logFile.getAbsoluteFile().getParentFile();
      String name = logFile.getName();
      int nameLen = name.length();
      int max = 0;
      File[] files = dir.listFiles();

      for(int i = 0; i < files.length; ++i) {
         File file = files[i];
         if (file.isFile() && file.getName().startsWith(name)) {
            String s = file.getName().substring(nameLen);

            try {
               int n = Integer.parseInt(s);
               if (n > max) {
                  max = n;
               }
            } catch (NumberFormatException var10) {
            }
         }
      }

      ++max;
      if (max > 99999) {
         max = 0;
      }

      name = name + max / 10000 % 10 + max / 1000 % 10 + max / 100 % 10 + max / 10 % 10 + max % 10;
      return new File(dir, name);
   }

   static void changeDirOwnerships(ServerManagerI serverManager, StartupConfig conf) {
      ServerDir serverDir = serverManager.getServerDir();
      changeOwnership(serverManager, conf, serverDir);
      changeOwnership(serverManager, conf, serverDir.getLogsDir());
      changeOwnership(serverManager, conf, serverDir.getSecurityDir());
      changeOwnership(serverManager, conf, serverDir.getDataDir());
      changeOwnership(serverManager, conf, serverDir.getNMDataDir());
      changeOwnership(serverManager, conf, serverDir.getTmpDir());
   }

   private static void changeOwnership(ServerManagerI serverManager, StartupConfig conf, File file) {
      NMServer nmServer = serverManager.getDomainManager().getNMServer();
      String domainName = serverManager.getDomainManager().getDomainName();
      String serverName = serverManager.getServerName();
      ProcessControl pc = nmServer.getConfig().getProcessControl();
      if (pc instanceof UnixProcessControl) {
         UnixProcessControl upc = (UnixProcessControl)pc;
         NMServerConfig nmConf = serverManager.getDomainManager().getNMServer().getConfig();
         String uid = conf.getUid();
         if (uid == null) {
            uid = nmConf.getWlsStartupServerUID();
         }

         String gid = conf.getGid();
         if (gid == null) {
            gid = nmConf.getWlsStartupServerGID();
         }

         if (uid != null || gid != null) {
            if (upc.changeFileOwnership(file, uid, gid)) {
               info(domainName, serverName, nmText.getChangeFileOwnershipSucceeded(file.getPath(), uid == null ? "" : uid, gid == null ? "" : gid));
            } else {
               warning(domainName, serverName, nmText.getChangeFileOwnershipFailed(file.getPath(), uid == null ? "" : uid, gid == null ? "" : gid));
            }
         }
      }

   }

   private static void log(String domainName, String serverName, Level level, String msg, Throwable thrown) {
      LogRecord lr = new LogRecord(level, msg);
      lr.setParameters(new String[]{domainName, serverName});
      if (thrown != null) {
         lr.setThrown(thrown);
      }

      nmLog.log(lr);
   }

   public static void log(String domainName, String serverName, Level level, String msg) {
      log(domainName, serverName, level, msg, (Throwable)null);
   }

   private static void info(String domainName, String serverName, String msg) {
      log(domainName, serverName, Level.INFO, msg);
   }

   private static void finest(String domainName, String serverName, String msg) {
      log(domainName, serverName, Level.FINEST, msg);
   }

   private static void warning(String domainName, String serverName, String msg) {
      log(domainName, serverName, Level.WARNING, msg);
   }

   private static void severe(String domainName, String serverName, String msg, Throwable e) {
      log(domainName, serverName, Level.SEVERE, msg, e);
   }

   private static void severe(String domainName, String serverName, String msg) {
      log(domainName, serverName, Level.SEVERE, msg);
   }
}
