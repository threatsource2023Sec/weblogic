package weblogic.security.pki.revocation.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;

final class ImportCrlFromFileRunnable implements Runnable {
   private final File importDir;
   private final CrlCacheAccessor crlCacheAccessor;
   private final LogListener logger;

   ImportCrlFromFileRunnable(File importDir, CrlCacheAccessor crlCacheAccessor, LogListener logger) {
      Util.checkNotNull("importDir", importDir);
      Util.checkNotNull("crlCacheAccessor", crlCacheAccessor);
      this.importDir = importDir;
      this.crlCacheAccessor = crlCacheAccessor;
      this.logger = logger;
   }

   public void run() {
      File lastFile = null;

      try {
         File[] crlFiles = this.importDir.listFiles(Util.CRL_FILES_ONLY_FILTER);
         if (null == crlFiles) {
            return;
         }

         File[] var3 = crlFiles;
         int var4 = crlFiles.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            File crlFile = var3[var5];
            if (null != crlFile) {
               if (null != this.logger && this.logger.isLoggable(Level.FINEST)) {
                  this.logger.log(Level.FINEST, "Trying to import CRL from file \"{0}\".", crlFile);
               }

               this.loadCrl(crlFile);
               this.delete(crlFile);
               if (null != this.logger && this.logger.isLoggable(Level.FINEST)) {
                  this.logger.log(Level.FINEST, "Successfully imported CRL from file \"{0}\".", crlFile);
               }

               Util.backgroundTaskSleep();
            }
         }
      } catch (Exception var7) {
         if (null != this.logger && this.logger.isLoggable(Level.FINE)) {
            this.logger.log(Level.FINE, var7, "Trying to import CRLs from directory \"{0}\", last attempted file \"{1}\".", this.importDir, lastFile);
         }
      }

   }

   private void delete(File crlFile) {
      try {
         if (crlFile.delete()) {
            if (null != this.logger && this.logger.isLoggable(Level.FINEST)) {
               this.logger.log(Level.FINEST, "Successfully deleted CRL file \"{0}\".", crlFile);
            }
         } else if (null != this.logger && this.logger.isLoggable(Level.FINE)) {
            this.logger.log(Level.FINE, "Unable to delete CRL file \"{0}\".", crlFile);
         }
      } catch (Exception var3) {
         if (null != this.logger && this.logger.isLoggable(Level.FINE)) {
            this.logger.log(Level.FINE, var3, "Error occurred while deleting CRL file \"{0}\".", crlFile);
         }
      }

   }

   private void loadCrl(File crlFile) {
      FileInputStream fis = null;

      try {
         fis = new FileInputStream(crlFile);
         boolean cacheUpdated = this.crlCacheAccessor.loadCrl(fis);
         if (null != this.logger && this.logger.isLoggable(Level.FINEST)) {
            this.logger.log(Level.FINEST, "Loaded CRL cache from file \"{0}\", cacheUpdated={1}.", crlFile, cacheUpdated);
         }
      } catch (Exception var12) {
         if (null != this.logger && this.logger.isLoggable(Level.FINE)) {
            this.logger.log(Level.FINE, var12, "Error occurred trying to load CRL cache from file \"{0}\".", crlFile);
         }
      } finally {
         if (null != fis) {
            try {
               fis.close();
            } catch (IOException var11) {
            }
         }

      }

   }
}
