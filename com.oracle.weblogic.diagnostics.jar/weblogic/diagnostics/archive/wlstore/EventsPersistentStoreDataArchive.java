package weblogic.diagnostics.archive.wlstore;

import java.util.Iterator;
import weblogic.diagnostics.accessor.DiagnosticDataAccessException;
import weblogic.diagnostics.archive.ArchiveConstants;
import weblogic.diagnostics.archive.DiagnosticStoreRepository;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.query.QueryException;
import weblogic.management.ManagementException;
import weblogic.store.PersistentStoreException;

public class EventsPersistentStoreDataArchive extends PersistentStoreDataArchive {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticArchive");

   public EventsPersistentStoreDataArchive(String name, String storeDirectory, boolean readOnly) throws PersistentStoreException, ManagementException {
      super(name, ArchiveConstants.getColumns(1), "WLS_EVENTS", storeDirectory, readOnly);
   }

   public String getDescription() {
      return "Diagnostic Events";
   }

   public Iterator getDataRecords(String query) throws QueryException, DiagnosticDataAccessException {
      return this.getDataRecords(0L, Long.MAX_VALUE, query);
   }

   public Iterator getDataRecords(long startTime, long endTime, String query) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException {
      return new EventsPersistentRecordIterator(this, startTime, endTime, query);
   }

   public Iterator getDataRecords(long startRecordId, long endrecordId, long endTime, String query) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException {
      return new EventsPersistentRecordIterator(this, startRecordId, endrecordId, endTime, query);
   }

   private static void usage() {
      DebugLogger.println("java [-Dverbose=true] " + EventsPersistentStoreDataArchive.class.getName() + "storeDirectory lowTimestamp highTimestamp [queryString]");
      DebugLogger.println("For example:");
      DebugLogger.println("java [-Dverbose=true] " + EventsPersistentStoreDataArchive.class.getName() + " c:/mydomain/servers/myserver/data/store/diagnostics 0 99999999999999 \"MONITOR LIKE '%Before%'\"");
   }

   public static void main(String[] args) throws Exception {
      if (args.length < 3) {
         usage();
      } else {
         String storeDir = args[0];
         long lo = Long.parseLong(args[1]);
         long hi = Long.parseLong(args[2]);
         String query = args.length > 3 ? args[3] : null;
         if (!DiagnosticStoreRepository.storeFileExists(storeDir)) {
            DebugLogger.println("Specified store directory " + storeDir + " does not exist or does not contain the diagnostic store file.");
         } else {
            boolean verbose = Boolean.getBoolean("verbose");
            boolean doDelete = Boolean.getBoolean("doDelete");
            boolean byID = Boolean.getBoolean("byID");
            String archiveName = System.getProperty("Name");
            if (archiveName == null) {
               archiveName = "EventsDataArchive";
            }

            EventsPersistentStoreDataArchive archive = new EventsPersistentStoreDataArchive(archiveName, storeDir, !doDelete);
            int cnt = 0;
            long t0 = System.currentTimeMillis();
            if (doDelete) {
               cnt = archive.deleteDataRecords(lo, hi, query);
            } else {
               Iterator it;
               if (byID) {
                  it = archive.getDataRecords(lo, hi, Long.MAX_VALUE, query);
               } else {
                  it = archive.getDataRecords(lo, hi, query);
               }

               for(; it.hasNext(); ++cnt) {
                  Object obj = it.next();
                  if (verbose) {
                     DebugLogger.println("RECORD>>>> " + obj);
                  }
               }
            }

            long t1 = System.currentTimeMillis();
            if (doDelete) {
               DebugLogger.println("Deleted " + cnt + " matching records in " + (t1 - t0) + " ms");
            } else {
               DebugLogger.println("Found " + cnt + " matches in " + (t1 - t0) + " ms");
            }

            archive.close();
         }
      }
   }
}
