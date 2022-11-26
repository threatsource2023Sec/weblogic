package weblogic.security.pki.revocation.common;

import java.io.File;
import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import javax.security.auth.x500.X500Principal;

final class CrlCacheUpdater {
   private static final long IMPORT_CRL_FROM_FILE_PERIOD_MILLIS = getTimerPeriodMillis("weblogic.security.pki.revocation.importCrlFromFilePeriodMillis", 60000L);
   private static final Object importCrlFromFileSync = new Object();
   private static volatile Timer importCrlFromFileTimer = null;
   private static final long DELETE_INVALID_CRL_FROM_CACHE_PERIOD_MILLIS = getTimerPeriodMillis("weblogic.security.pki.revocation.deleteInvalidCrlFromCachePeriodMillis", 300000L);
   private static final Object deleteInvalidCrlFromCacheSync = new Object();
   private static volatile Timer deleteInvalidCrlFromCacheTimer = null;

   static void startAllMaintenanceTasks(CrlCacheAccessor crlcache, AbstractCertRevocContext context) {
      startImportCrlFromFile(crlcache, context);
      startDeleteInvalidCrlFromCache(crlcache, context);
   }

   static void cancelAllMaintenanceTasks(LogListener logger) {
      cancelImportCrlFromFile(logger);
      cancelDeleteInvalidCrlFromCache(logger);
   }

   static boolean isAllMaintenanceTasksActive() {
      boolean active = isImportCrlFromFileActive();
      active &= isDeleteInvalidCrlFromCacheActive();
      return active;
   }

   static boolean updateCrlCacheFromDP(X509Certificate certToCheck, CrlCacheAccessor crlCacheAccessor, AbstractCertRevocContext context) {
      Util.checkNotNull("certToCheck", certToCheck);
      Util.checkNotNull("crlCacheAccessor", crlCacheAccessor);
      Util.checkNotNull("AbstractCertRevocContext", context);
      X500Principal issuerDn = certToCheck.getIssuerX500Principal();
      Util.checkNotNull("certToCheck issuer", issuerDn);
      long dpDownloadTimeoutSecs = context.getCrlDpDownloadTimeout(issuerDn);
      if (context.isLoggable(Level.FINEST)) {
         context.log(Level.FINEST, "CrlDpDownloadTimeout={0}", dpDownloadTimeoutSecs);
      }

      long dpDownloadTimeout = dpDownloadTimeoutSecs * 1000L;
      URI dpUrl = context.getCrlDpUrl(issuerDn);
      if (context.isLoggable(Level.FINEST)) {
         context.log(Level.FINEST, "CrlDpUrl={0}", dpUrl);
      }

      AbstractCertRevocConstants.AttributeUsage dpUrlUsage = context.getCrlDpUrlUsage(issuerDn);
      if (context.isLoggable(Level.FINEST)) {
         context.log(Level.FINEST, "CrlDpUrlUsage={0}", dpUrlUsage);
      }

      Object waitingObject = new Object();
      synchronized(waitingObject) {
         DownloadCrlFromDpRunnable task = new DownloadCrlFromDpRunnable(crlCacheAccessor, waitingObject, certToCheck, context.getLogListener(), dpUrl, dpUrlUsage, dpDownloadTimeout);
         context.schedule(task);
         long remainingWait = dpDownloadTimeout <= 0L ? Long.MAX_VALUE : dpDownloadTimeout;
         long startMillis = System.currentTimeMillis();
         long roomMillis = Long.MAX_VALUE - startMillis;

         for(long endMillis = roomMillis <= remainingWait ? Long.MAX_VALUE : startMillis + remainingWait; task.isRunning() && remainingWait > 0L; remainingWait = endMillis - System.currentTimeMillis()) {
            try {
               waitingObject.wait(remainingWait);
            } catch (InterruptedException var23) {
            }
         }

         return task.isCrlCacheUpdated();
      }
   }

   private static boolean isImportCrlFromFileActive() {
      return null != importCrlFromFileTimer;
   }

   private static boolean startImportCrlFromFile(CrlCacheAccessor crlCacheAccessor, AbstractCertRevocContext context) {
      if (null != importCrlFromFileTimer) {
         return true;
      } else {
         synchronized(importCrlFromFileSync) {
            if (null != importCrlFromFileTimer) {
               return true;
            } else {
               Util.checkNotNull("crlCacheAccessor", crlCacheAccessor);
               Util.checkNotNull("AbstractCertRevocContext", context);
               if (context.isLoggable(Level.FINEST)) {
                  context.log(Level.FINEST, "Attempting to start automatic checking for CRL files to import.");
               }

               try {
                  File importDir = context.getCrlCacheImportDir();
                  ensureCrlCacheDir(importDir);
                  String dbDirPath = importDir.getAbsolutePath();
                  if (context.isLoggable(Level.FINEST)) {
                     context.log(Level.FINEST, "CrlCacheImportDir=\"{0}\"", dbDirPath);
                  }

                  ImportCrlFromFileRunnable task = new ImportCrlFromFileRunnable(importDir, crlCacheAccessor, context.getLogListener());
                  long delay = IMPORT_CRL_FROM_FILE_PERIOD_MILLIS;
                  long period = IMPORT_CRL_FROM_FILE_PERIOD_MILLIS;
                  importCrlFromFileTimer = context.scheduleWithFixedDelay(task, delay, period);
                  if (null == importCrlFromFileTimer) {
                     throw new IllegalStateException("Scheduler returned null importCrlFromFileTimer.");
                  }
               } catch (Exception var11) {
                  if (context.isLoggable(Level.FINE)) {
                     context.log(Level.FINE, var11, "Unable to start automatic checking for CRL files to import.");
                  }

                  return false;
               }

               if (context.isLoggable(Level.FINEST)) {
                  context.log(Level.FINEST, "Successfully started automatic checking for CRL files to import.");
               }

               return true;
            }
         }
      }
   }

   private static void cancelImportCrlFromFile(LogListener logger) {
      if (null != importCrlFromFileTimer) {
         synchronized(importCrlFromFileSync) {
            if (null != importCrlFromFileTimer) {
               if (null != logger && logger.isLoggable(Level.FINEST)) {
                  logger.log(Level.FINEST, "Attempting to stop automatic checking for CRL files to import.");
               }

               try {
                  importCrlFromFileTimer.cancel();
                  importCrlFromFileTimer = null;
                  if (null != logger && logger.isLoggable(Level.FINEST)) {
                     logger.log(Level.FINEST, "Successfully stopped automatic checking for CRL files to import.");
                  }
               } catch (Exception var4) {
                  if (null != logger && logger.isLoggable(Level.FINE)) {
                     logger.log(Level.FINE, var4, "Error occured while stopping automatic checking for CRL files to import.");
                  }
               }

            }
         }
      }
   }

   private static boolean isDeleteInvalidCrlFromCacheActive() {
      return null != deleteInvalidCrlFromCacheTimer;
   }

   private static boolean startDeleteInvalidCrlFromCache(CrlCacheAccessor crlCacheAccessor, AbstractCertRevocContext context) {
      if (null != deleteInvalidCrlFromCacheTimer) {
         return true;
      } else {
         synchronized(deleteInvalidCrlFromCacheSync) {
            if (null != deleteInvalidCrlFromCacheTimer) {
               return true;
            } else {
               Util.checkNotNull("crlCacheAccessor", crlCacheAccessor);
               Util.checkNotNull("AbstractCertRevocContext", context);
               if (context.isLoggable(Level.FINEST)) {
                  context.log(Level.FINEST, "Attempting to start automatic deleting of invalid CRLs in cache.");
               }

               try {
                  File dbDirectory = context.getCrlCacheTypeFileDir();
                  ensureCrlCacheDir(dbDirectory);
                  String dbDirPath = dbDirectory.getAbsolutePath();
                  if (context.isLoggable(Level.FINEST)) {
                     context.log(Level.FINEST, "CrlCacheTypeFileDir=\"{0}\"", dbDirPath);
                  }

                  DeleteInvalidCrlFromCacheRunnable task = new DeleteInvalidCrlFromCacheRunnable(context, dbDirectory, crlCacheAccessor);
                  long delay = DELETE_INVALID_CRL_FROM_CACHE_PERIOD_MILLIS;
                  long period = DELETE_INVALID_CRL_FROM_CACHE_PERIOD_MILLIS;
                  deleteInvalidCrlFromCacheTimer = context.scheduleWithFixedDelay(task, delay, period);
                  if (null == deleteInvalidCrlFromCacheTimer) {
                     throw new IllegalStateException("Scheduler returned null deleteInvalidCrlFromCacheTimer.");
                  }
               } catch (Exception var11) {
                  if (context.isLoggable(Level.FINE)) {
                     context.log(Level.FINE, var11, "Unable to start automatic deleting of invalid CRLs in cache.");
                  }

                  return false;
               }

               if (context.isLoggable(Level.FINEST)) {
                  context.log(Level.FINEST, "Successfully started automatic deleting of invalid CRLs in cache.");
               }

               return true;
            }
         }
      }
   }

   private static void cancelDeleteInvalidCrlFromCache(LogListener logger) {
      if (null != deleteInvalidCrlFromCacheTimer) {
         synchronized(deleteInvalidCrlFromCacheSync) {
            if (null != deleteInvalidCrlFromCacheTimer) {
               if (null != logger && logger.isLoggable(Level.FINEST)) {
                  logger.log(Level.FINEST, "Attempting to stop automatic deleting of invalid CRLs in cache.");
               }

               try {
                  deleteInvalidCrlFromCacheTimer.cancel();
                  deleteInvalidCrlFromCacheTimer = null;
                  if (null != logger && logger.isLoggable(Level.FINEST)) {
                     logger.log(Level.FINEST, "Successfully stopped automatic deleting of invalid CRLs in cache.");
                  }
               } catch (Exception var4) {
                  if (null != logger && logger.isLoggable(Level.FINE)) {
                     logger.log(Level.FINE, var4, "Error occured while stopping automatic deleting of invalid CRLs in cache.");
                  }
               }

            }
         }
      }
   }

   static void ensureCrlCacheDir(File dir) {
      if (null == dir) {
         throw new IllegalArgumentException("Unexpected null directory used by CRL cache.");
      } else {
         if (dir.exists()) {
            if (!dir.isDirectory()) {
               throw new IllegalArgumentException("Directory reference for CRL cache is not a directory: \"" + dir.getAbsolutePath() + "\".");
            }
         } else {
            dir.mkdirs();
            if (!dir.exists()) {
               throw new IllegalStateException("Unable to create CRL cache directory: \"" + dir.getAbsolutePath() + "\".");
            }
         }

      }
   }

   private static long getTimerPeriodMillis(String systemPropertyName, long defaultValue) {
      if (defaultValue < 0L) {
         defaultValue = 0L;
      }

      Long result = Long.getLong(systemPropertyName, defaultValue);
      return null != result && result >= 0L ? result : defaultValue;
   }
}
