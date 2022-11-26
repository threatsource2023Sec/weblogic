package weblogic.diagnostics.archive.wlstore;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.query.QueryException;

public final class EventsPersistentRecordIterator extends PersistentRecordIterator {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticArchive");

   EventsPersistentRecordIterator(EventsPersistentStoreDataArchive archive, long startTime, long endTime, String queryString) throws QueryException {
      super(archive, startTime, endTime, queryString);
   }

   EventsPersistentRecordIterator(EventsPersistentStoreDataArchive archive, long startId, long endId, long endTime, String queryString) throws QueryException {
      super(archive, startId, endId, endTime, queryString);
   }
}
