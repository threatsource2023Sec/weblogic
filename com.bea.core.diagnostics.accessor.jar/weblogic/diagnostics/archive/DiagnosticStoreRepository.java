package weblogic.diagnostics.archive;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreManager;
import weblogic.store.RuntimeHandler;
import weblogic.store.StoreWritePolicy;
import weblogic.store.xa.PersistentStoreManagerXA;
import weblogic.store.xa.PersistentStoreXA;
import weblogic.utils.PropertyHelper;

public final class DiagnosticStoreRepository {
   private static final String STORE_NAME = "WLS_DIAGNOSTICS";
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticArchive");
   private static final StoreWritePolicy WRITE_POLICY;
   private static final DiagnosticStoreRepository SINGLETON;
   private static final int RETRY_COUNT;
   private static final long RETRY_DELAY;
   private String storeDirectory;
   private PersistentStoreXA store;
   private boolean valid;

   public static DiagnosticStoreRepository getInstance() {
      return SINGLETON;
   }

   public boolean isValid() {
      return this.valid;
   }

   public synchronized PersistentStoreXA getStore(String storeDir) throws PersistentStoreException {
      return this.getStore(storeDir, (HashMap)null, (RuntimeHandler)null);
   }

   public synchronized PersistentStoreXA getStore(String storeDir, HashMap config, RuntimeHandler runtimeHandler) throws PersistentStoreException {
      File filePath;
      try {
         filePath = new File(storeDir);
         filePath = filePath.getCanonicalFile();
         storeDir = filePath.toString();
      } catch (IOException var10) {
         DiagnosticsLogger.logUnableToFindPathToStoreDir(storeDir);
         this.storeDirectory = storeDir;
         return null;
      }

      filePath = new File(storeDir, "WLS_DIAGNOSTICS.DAT");
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("DiagnosticStoreRepository: previous storeDir : " + this.storeDirectory);
         DEBUG.debug("DiagnosticStoreRepository: storeDir: " + storeDir);
      }

      if (this.store == null && this.storeDirectory == null) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("DiagnosticStoreRepository: createFileStore");
         }

         if (config == null) {
            config = new HashMap();
         }

         config.put("SynchronousWritePolicy", WRITE_POLICY);
         config.put("OpenFailuresAreFatal", Boolean.FALSE);
         Throwable ex = null;

         for(int i = 0; this.store == null && i <= RETRY_COUNT; ++i) {
            try {
               this.store = PersistentStoreManagerXA.createFileStore("WLS_DIAGNOSTICS", storeDir, (String)null, true, runtimeHandler, config);
               this.valid = true;
            } catch (Throwable var11) {
               ex = var11;
               if (i < RETRY_COUNT) {
                  DiagnosticsLogger.logRetryStorefileOpen(filePath.toString(), var11.getMessage());

                  try {
                     Thread.sleep(RETRY_DELAY);
                  } catch (InterruptedException var9) {
                  }
               }
            }
         }

         if (this.store == null) {
            DiagnosticsLogger.logFailedToOpenStorefile(filePath.toString(), RETRY_COUNT, ex);
         }

         this.storeDirectory = storeDir;
      }

      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("DiagnosticStoreRepository returning store: " + this.store);
      }

      return this.store;
   }

   public synchronized void close() throws PersistentStoreException {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Closing diagnostic store");
      }

      if (this.store != null) {
         PersistentStoreManager manager = PersistentStoreManager.getManager();
         manager.closeFileStore("WLS_DIAGNOSTICS");
      }

      this.store = null;
      this.storeDirectory = null;
      this.valid = false;
   }

   public static boolean storeFileExists(String storeDirectory) {
      File storeDir = new File(storeDirectory);
      if (storeDir.isDirectory()) {
         String[] names = storeDir.list();
         int size = names != null ? names.length : 0;

         for(int i = 0; i < size; ++i) {
            if (names[i].startsWith("WLS_DIAGNOSTICS")) {
               return true;
            }
         }
      }

      return false;
   }

   static {
      WRITE_POLICY = StoreWritePolicy.DISABLED;
      SINGLETON = new DiagnosticStoreRepository();
      RETRY_COUNT = PropertyHelper.getInteger("weblogic.diagnostics.store.open.retrycount", 10);
      RETRY_DELAY = PropertyHelper.getLong("weblogic.diagnostics.store.open.retrydelay", 1000L);
   }
}
