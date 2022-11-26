package weblogic.management.filelock.internal;

import java.io.File;
import java.io.IOException;
import weblogic.management.bootstrap.BootStrap;
import weblogic.management.filelock.FileLockHandle;
import weblogic.utils.filelock.FileLockService;

public class ConfigurationFileLock {
   private int count = 0;
   private File configLockFile;
   private FileLockHandle underlyingLock;
   private final ManagementFileLockServiceImpl parent;
   private final FileLockService fileLockService;

   ConfigurationFileLock(ManagementFileLockServiceImpl parent, FileLockService fileLockService) {
      this.parent = parent;
      this.fileLockService = fileLockService;
   }

   public FileLockHandle lockIt(long timeout) throws IOException {
      int oldCount = this.count++;
      if (oldCount > 0) {
         return new ConfigurationFileLockHandle();
      } else {
         boolean success = false;

         ConfigurationFileLockHandle var6;
         try {
            this.configLockFile = new File(BootStrap.getConfigLockFileName());
            this.underlyingLock = this.parent.getFileLock(this.configLockFile, timeout);
            ConfigurationFileLockHandle retVal;
            if (this.underlyingLock == null) {
               retVal = null;
               return retVal;
            }

            this.fileLockService.registerLockFile(this.configLockFile);
            retVal = new ConfigurationFileLockHandle();
            success = true;
            var6 = retVal;
         } finally {
            if (!success) {
               --this.count;
            }

         }

         return var6;
      }
   }

   private void unlockIt() throws IOException {
      --this.count;
      if (this.count <= 0) {
         this.parent.releaseConfigLock();
         this.underlyingLock.close();
         this.configLockFile.delete();
         this.fileLockService.unregisterLockFile(this.configLockFile);
      }

   }

   private class ConfigurationFileLockHandle implements FileLockHandle {
      private boolean closed;

      private ConfigurationFileLockHandle() {
         this.closed = false;
      }

      public void close() throws IOException {
         synchronized(ConfigurationFileLock.this.parent) {
            if (!this.closed) {
               this.closed = true;
               ConfigurationFileLock.this.unlockIt();
            }
         }
      }

      public String toString() {
         return "ConfigurationFileLockHandle(" + System.identityHashCode(this) + ")";
      }

      public void finalize() throws Throwable {
         this.close();
      }

      // $FF: synthetic method
      ConfigurationFileLockHandle(Object x1) {
         this();
      }
   }
}
