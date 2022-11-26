package weblogic.security.pki.revocation.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import javax.security.auth.x500.X500Principal;

final class DeleteInvalidCrlFromCacheRunnable implements Runnable {
   private final AbstractCertRevocContext context;
   private final File crlCacheDir;
   private final CrlCacheAccessor crlCacheAccessor;
   private static final int INDEX_INITIAL_CAPACITY = 96;
   private static final Map crlFileIndex = new ConcurrentHashMap(96);

   DeleteInvalidCrlFromCacheRunnable(AbstractCertRevocContext context, File crlCacheDir, CrlCacheAccessor crlCacheAccessor) {
      Util.checkNotNull("AbstractCertRevocContext", context);
      Util.checkNotNull("crlCacheDir", crlCacheDir);
      Util.checkNotNull("crlCacheAccessor", crlCacheAccessor);
      crlCacheDir = new File(crlCacheDir, "crls");
      this.context = context;
      this.crlCacheDir = crlCacheDir;
      this.crlCacheAccessor = crlCacheAccessor;
   }

   public void run() {
      try {
         this.syncIndex();
         this.deleteInvalid();
      } catch (Exception var2) {
         if (this.context.isLoggable(Level.FINE)) {
            this.context.log(Level.FINE, var2, "Exception while checking for invalid CRLs within directory \"{0}\".", this.crlCacheDir);
         }
      }

   }

   private void syncIndex() {
      Map crlFileIndexCopy = new HashMap(crlFileIndex);
      File[] crlFiles = this.crlCacheDir.listFiles(Util.CRL_FILES_ONLY_FILTER);
      if (null != crlFiles) {
         File[] var3 = crlFiles;
         int var4 = crlFiles.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            File crlFile = var3[var5];
            if (null != crlFile) {
               IndexValue cachedValue = (IndexValue)crlFileIndex.get(crlFile);
               if (null == cachedValue) {
                  IndexValue newValue = null;

                  try {
                     newValue = this.getIndexValue(crlFile);
                  } catch (Exception var10) {
                     if (this.context.isLoggable(Level.FINE)) {
                        this.context.log(Level.FINE, var10, "Exception while indexing found CRL in cache: file={0}", crlFile);
                     }
                  }

                  if (null != newValue) {
                     crlFileIndex.put(crlFile, newValue);
                  }

                  Util.backgroundTaskSleep();
               } else {
                  crlFileIndexCopy.remove(crlFile);
               }
            }
         }

         if (!crlFileIndexCopy.isEmpty()) {
            Iterator var11 = crlFileIndexCopy.keySet().iterator();

            while(var11.hasNext()) {
               File key = (File)var11.next();
               crlFileIndex.remove(key);
            }
         }

      }
   }

   private void deleteInvalid() {
      if (!crlFileIndex.isEmpty()) {
         int refreshPeriodPercent = this.context.getCrlCacheRefreshPeriodPercent();
         Iterator var2 = crlFileIndex.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            if (this.isInvalid(entry, refreshPeriodPercent)) {
               this.deleteFromCrlCache(entry);
               Util.backgroundTaskSleep();
            }
         }
      }

   }

   private boolean isInvalid(Map.Entry entry, int refreshPeriodPercent) {
      IndexValue value = (IndexValue)entry.getValue();
      Date nextUpdate = value.getNextUpdate();
      if (null == nextUpdate) {
         return true;
      } else {
         long thisUpdateTime = value.getThisUpdate().getTime();
         long nextUpdateTime = nextUpdate.getTime();
         if (nextUpdateTime <= thisUpdateTime) {
            return true;
         } else {
            long refreshPeriod = nextUpdateTime - thisUpdateTime;
            long adjustedRefreshPeriod = refreshPeriod * (long)refreshPeriodPercent / 100L;
            long adjustedNextUpdateTime = thisUpdateTime + adjustedRefreshPeriod;
            long nowTime = (new Date()).getTime();
            return nowTime >= adjustedNextUpdateTime;
         }
      }
   }

   private void deleteFromCrlCache(Map.Entry entry) {
      IndexValue value = (IndexValue)entry.getValue();

      try {
         this.crlCacheAccessor.deleteCrl(value.getIssuerX500Name(), value.getThisUpdate());
         if (this.context.isLoggable(Level.FINEST)) {
            this.context.log(Level.FINEST, "Deleted CRL from cache: file={0}, {1}", entry.getKey(), entry.getValue());
         }
      } catch (Exception var4) {
         if (this.context.isLoggable(Level.FINE)) {
            this.context.log(Level.FINE, var4, "Exception while deleting CRL from cache: file={0}, {1}", entry.getKey(), entry.getValue());
         }
      }

   }

   private IndexValue getIndexValue(File crlFile) throws Exception {
      X509CRL crl = null;
      Exception exception = null;
      FileInputStream fis = null;

      try {
         fis = new FileInputStream(crlFile);
         CertificateFactory cf = CertificateFactory.getInstance("X.509");
         crl = (X509CRL)cf.generateCRL(fis);
      } catch (OutOfMemoryError var17) {
         exception = new RuntimeException(var17);
      } catch (Exception var18) {
         exception = var18;
      } finally {
         if (null != fis) {
            try {
               fis.close();
            } catch (IOException var16) {
            }
         }

      }

      IndexValue result = null;
      if (null != exception) {
         throw (Exception)exception;
      } else {
         if (null == crl) {
            if (this.context.isLoggable(Level.FINER)) {
               this.context.log(Level.FINER, "Reading CRL from file \"{0}\", no generated CRL.", crlFile);
            }
         } else {
            X500Principal issuer = crl.getIssuerX500Principal();
            if (null == issuer && this.context.isLoggable(Level.FINER)) {
               this.context.log(Level.FINER, "Reading CRL from file \"{0}\", no issuer.", crlFile);
            }

            Date thisUpdate = crl.getThisUpdate();
            if (null == thisUpdate && this.context.isLoggable(Level.FINER)) {
               this.context.log(Level.FINER, "Reading CRL from file \"{0}\", no ThisUpdate.", crlFile);
            }

            Date nextUpdate = crl.getNextUpdate();
            if (null != issuer && null != thisUpdate) {
               result = new IndexValue(issuer, thisUpdate, nextUpdate);
            }
         }

         return result;
      }
   }

   private static final class IndexValue {
      private final Date thisUpdate;
      private final Date nextUpdate;
      private final X500Principal issuerX500Name;

      private IndexValue(X500Principal issuerX500Name, Date thisUpdate, Date nextUpdate) {
         Util.checkNotNull("issuerX500Name", issuerX500Name);
         Util.checkNotNull("thisUpdate", thisUpdate);
         this.issuerX500Name = issuerX500Name;
         this.thisUpdate = thisUpdate;
         this.nextUpdate = nextUpdate;
      }

      public X500Principal getIssuerX500Name() {
         return this.issuerX500Name;
      }

      public Date getThisUpdate() {
         return this.thisUpdate;
      }

      public Date getNextUpdate() {
         return this.nextUpdate;
      }

      public String toString() {
         String pattern = "Issuer={0}, ThisUpdate={1}, NextUpdate={2}";
         return MessageFormat.format("Issuer={0}, ThisUpdate={1}, NextUpdate={2}", this.issuerX500Name, this.thisUpdate, this.nextUpdate);
      }

      // $FF: synthetic method
      IndexValue(X500Principal x0, Date x1, Date x2, Object x3) {
         this(x0, x1, x2);
      }
   }
}
