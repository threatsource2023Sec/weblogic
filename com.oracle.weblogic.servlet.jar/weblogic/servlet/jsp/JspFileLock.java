package weblogic.servlet.jsp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import weblogic.management.filelock.FileLockHandle;
import weblogic.management.filelock.ManagementFileLockService;
import weblogic.utils.LocatorUtilities;

public class JspFileLock {
   private static final int DEFAULT_TIMEOUT = 10;
   private File outputDir;
   private File lockFile;
   private FileLockHandle lock;

   public JspFileLock(File outputDir) {
      this.outputDir = outputDir;
   }

   public void lock() throws IOException, FileNotFoundException {
      this._lock(10L);
   }

   private void _lock(long timeout) throws IOException, FileNotFoundException {
      if (this.lock == null) {
         this.lockFile = this.getLockFile();
         this.lock = getLock(this.lockFile, timeout);
      }
   }

   public void lock(boolean spin) throws IOException, FileNotFoundException {
      if (!spin) {
         this._lock(1L);
      } else {
         this.lock();
      }

   }

   public boolean isLocked() {
      return this.lock != null;
   }

   private static FileLockHandle getLock(File lockFile, long timeout) throws IOException, FileNotFoundException {
      if (lockFile == null) {
         throw new IllegalArgumentException();
      } else {
         ManagementFileLockService lockService = (ManagementFileLockService)LocatorUtilities.getService(ManagementFileLockService.class);
         FileLockHandle result = null;

         do {
            result = lockService.getFileLock(lockFile, timeout);
         } while(result == null);

         return result;
      }
   }

   private File getLockFile() {
      return new File(this.outputDir, ".jsp-compiling.lock");
   }

   public void release() throws IOException {
      this.lock.close();
      this.lock = null;
      this.lockFile.delete();
   }
}
