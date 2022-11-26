package weblogic.diagnostics.archive.wlstore;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.query.QueryException;

public final class GenericPersistentRecordIterator extends PersistentRecordIterator {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticArchive");

   GenericPersistentRecordIterator(GenericPersistentStoreDataArchive archive, long startTime, long endTime, String queryString) throws QueryException {
      super(archive, startTime, endTime, queryString);
   }

   GenericPersistentRecordIterator(GenericPersistentStoreDataArchive archive, long startId, long endId, long endTime, String queryString) throws QueryException {
      super(archive, startId, endId, endTime, queryString);
   }
}
