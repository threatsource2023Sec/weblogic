package weblogic.management.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.ManagementLogger;
import weblogic.management.bootstrap.BootStrap;
import weblogic.management.filelock.FileLockHandle;
import weblogic.management.filelock.ManagementFileLockService;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.filelock.FileLockService;

public class ServerLocks {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationRuntime");
   private static FileLockHandle[] serverLocks;
   private static FileLockHandle serverLock;
   private static File[] serverLockFiles;
   private static File serverLockFile;

   public static void main(String[] args) throws Exception {
      getAllServerLocks();
      releaseAllServerLocks();
   }

   public static void getServerLock() throws ManagementException {
      String serverName = BootStrap.getServerName();
      if (serverName == null) {
         String[] servers = getServers();
         if (servers.length == 1) {
            serverName = servers[0];
         }
      }

      if (serverName != null) {
         serverLockFile = getServerLockFile(serverName);
      } else {
         serverLockFile = getDefaultServerLockFile();
      }

      try {
         serverLock = getServerLock(serverLockFile);
      } finally {
         if (serverLock == null) {
            serverLockFile = null;
         } else {
            ((FileLockService)LocatorUtilities.getService(FileLockService.class)).registerLockFile(serverLockFile);
         }

      }

   }

   public static synchronized void releaseServerLock() {
      if (serverLock != null) {
         try {
            serverLock.close();
            serverLock = null;
            serverLockFile.delete();
            ((FileLockService)LocatorUtilities.getService(FileLockService.class)).unregisterLockFile(serverLockFile);
            serverLockFile = null;
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("released " + serverLock);
            }
         } catch (IOException var1) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Exception releasing server lock " + var1, var1);
            }
         }
      }

   }

   public static synchronized void getAllServerLocks() throws ManagementException {
      if (serverLocks == null) {
         String[] servers = getServers();
         serverLocks = new FileLockHandle[servers.length + 1];
         serverLockFiles = new File[servers.length + 1];
         FileLockService fileLockService = (FileLockService)LocatorUtilities.getService(FileLockService.class);

         for(int i = 0; i < servers.length; ++i) {
            serverLockFiles[i] = getServerLockFile(servers[i]);

            try {
               serverLocks[i] = getServerLock(serverLockFiles[i]);
            } finally {
               if (serverLocks[i] == null) {
                  serverLockFiles[i] = null;
               } else {
                  fileLockService.registerLockFile(serverLockFiles[i]);
               }

            }
         }

         try {
            serverLockFiles[servers.length] = getDefaultServerLockFile();
            serverLocks[servers.length] = getServerLock(serverLockFiles[servers.length]);
         } finally {
            if (serverLocks[servers.length] == null) {
               serverLockFiles[servers.length] = null;
            } else {
               fileLockService.registerLockFile(serverLockFiles[servers.length]);
            }

         }

      }
   }

   public static synchronized void releaseAllServerLocks() {
      if (serverLocks != null) {
         FileLockService fileLockService = (FileLockService)LocatorUtilities.getService(FileLockService.class);

         for(int i = 0; i < serverLocks.length; ++i) {
            if (serverLocks[i] != null) {
               try {
                  serverLocks[i].close();
                  serverLocks[i] = null;
                  serverLockFiles[i].delete();
                  fileLockService.unregisterLockFile(serverLockFiles[i]);
                  serverLockFiles[i] = null;
               } catch (IOException var3) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Exception releasing all server locks " + var3, var3);
                  }
               }
            }
         }

         serverLocks = null;
         serverLockFiles = null;
      }
   }

   private static File getServerLockFile(String serverName) {
      String tmpDirName = DomainDir.getTempDirForServer(serverName);
      File tmpDir = new File(tmpDirName);
      if (!tmpDir.isAbsolute()) {
         File domainRoot = new File(DomainDir.getRootDir());
         new File(domainRoot, tmpDirName);
      }

      if (!tmpDir.exists()) {
         tmpDir.mkdirs();
      }

      String lockFileName = serverName + ".lok";
      File lockFile = new File(tmpDir, lockFileName);
      return lockFile;
   }

   private static FileLockHandle getServerLock(File lockFile) throws ManagementException {
      ManagementFileLockService lockService = (ManagementFileLockService)LocatorUtilities.getService(ManagementFileLockService.class);
      FileLockHandle result = null;

      try {
         result = lockService.getFileLock(lockFile, 1000L, false);
         long timeout = 60000L;

         for(int i = 0; i < 3 && result == null; ++i) {
            ManagementLogger.logCouldNotGetServerLockRetry("" + timeout / 1000L);
            result = lockService.getFileLock(lockFile, timeout, false);
         }
      } catch (Exception var6) {
         throw new ManagementException("Unable to obtain File lock on " + lockFile.getAbsolutePath() + " : " + var6);
      }

      if (result == null) {
         String msg = "Unable to obtain lock on " + lockFile.getAbsolutePath() + ". Server may already be running";
         throw new ManagementException(msg);
      } else {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Got lock for " + lockFile);
         }

         return result;
      }
   }

   private static File getDefaultServerLockFile() {
      File lockFile = new File(DomainDir.getRootDir(), "default_server.lok");
      return lockFile;
   }

   private static String[] getServers() {
      ArrayList result = new ArrayList();
      File[] servers = (new File(DomainDir.getServersDir())).listFiles();
      if (servers == null) {
         return new String[0];
      } else {
         for(int i = 0; i < servers.length; ++i) {
            if (servers[i].isDirectory()) {
               result.add(servers[i].getName());
            }
         }

         return (String[])((String[])result.toArray(new String[result.size()]));
      }
   }
}
