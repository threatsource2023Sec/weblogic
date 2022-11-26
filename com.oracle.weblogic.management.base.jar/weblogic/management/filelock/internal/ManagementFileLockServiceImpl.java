package weblogic.management.filelock.internal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import javax.inject.Inject;
import javax.inject.Named;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.ManagementLogger;
import weblogic.management.filelock.FileLockHandle;
import weblogic.management.filelock.ManagementFileLockService;
import weblogic.utils.filelock.FileLockService;

@Service
@Named
public class ManagementFileLockServiceImpl implements ManagementFileLockService {
   @Inject
   private FileLockService fileLockService;
   private ConfigurationFileLock configurationLockFile = null;

   public FileLockHandle getFileLock(File lockFile, long timeout) throws IOException {
      return this.getFileLock(lockFile, timeout, true);
   }

   public FileLockHandle getFileLock(File lockFile) throws IOException {
      return this.getFileLock(lockFile, 300000L, true);
   }

   public FileLockHandle getFileLock(File lockFile, long timeout, boolean logMsg) throws IOException {
      boolean success = false;
      FileOutputStream os = null;
      FileChannel channel = null;

      FileLockHandle var9;
      try {
         os = new FileOutputStream(lockFile);
         channel = os.getChannel();
         FileLockHandle retVal = this.getFileLock(lockFile, os, channel, timeout, logMsg);
         if (retVal != null) {
            success = true;
         }

         var9 = retVal;
         return var9;
      } catch (FileNotFoundException var23) {
         var9 = null;
      } finally {
         if (!success) {
            if (channel != null) {
               try {
                  channel.close();
               } catch (IOException var22) {
               }
            }

            if (os != null) {
               try {
                  os.close();
               } catch (IOException var21) {
               }
            }
         }

      }

      return var9;
   }

   private FileLockHandle getFileLock(File originalFile, FileOutputStream stream, FileChannel channel, long timeoutInMillis, boolean logMsg) {
      if (timeoutInMillis < 0L) {
         timeoutInMillis = Long.MAX_VALUE;
      }

      long currentTime = System.currentTimeMillis();
      long lastMsgTime = currentTime;
      long timeoutTime = currentTime + timeoutInMillis;
      FileLock fileLock = null;

      for(String reason = ""; fileLock == null && currentTime <= timeoutTime; currentTime = System.currentTimeMillis()) {
         try {
            fileLock = channel.tryLock();
         } catch (Throwable var19) {
            reason = var19.toString();
         }

         if (fileLock != null) {
            break;
         }

         long remainingTime = timeoutTime - currentTime;
         if (remainingTime <= 0L) {
            break;
         }

         if (logMsg && currentTime - lastMsgTime >= 10000L) {
            lastMsgTime = currentTime;
            ManagementLogger.logRetryFileLock2(originalFile.getAbsolutePath(), reason);
         }

         try {
            Thread.sleep(remainingTime > 100L ? 100L : remainingTime);
         } catch (InterruptedException var18) {
            throw new RuntimeException(var18);
         }
      }

      return fileLock == null ? null : new FileLockHandleImpl(originalFile, stream, channel, fileLock);
   }

   public synchronized FileLockHandle getConfigFileLock(long timeout) throws IOException {
      FileLockHandle retVal = null;
      ConfigurationFileLock localCFL = this.configurationLockFile;
      if (localCFL == null) {
         localCFL = new ConfigurationFileLock(this, this.fileLockService);
      }

      FileLockHandle var5;
      try {
         retVal = localCFL.lockIt(timeout);
         var5 = retVal;
      } finally {
         if (retVal != null) {
            this.configurationLockFile = localCFL;
         }

      }

      return var5;
   }

   public FileLockHandle getConfigFileLock() throws IOException {
      return this.getConfigFileLock(300000L);
   }

   synchronized void releaseConfigLock() {
      this.configurationLockFile = null;
   }

   public String toString() {
      return "ManagementFileLockServiceImpl(" + System.identityHashCode(this) + ")";
   }
}
