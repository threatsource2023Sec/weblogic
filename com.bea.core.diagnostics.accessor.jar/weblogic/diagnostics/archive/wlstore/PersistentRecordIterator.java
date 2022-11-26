package weblogic.diagnostics.archive.wlstore;

import java.util.Collection;
import java.util.Iterator;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.archive.RecordIterator;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.query.QueryException;
import weblogic.store.PersistentHandle;
import weblogic.store.PersistentStoreException;

public abstract class PersistentRecordIterator extends RecordIterator {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticArchive");
   private PersistentHandle currentRecordHandle;
   private int recordIdIndex;
   private int timestampIndex;
   private int columnCount;

   PersistentRecordIterator(PersistentStoreDataArchive archive, long startTime, long endTime, String queryString) throws QueryException {
      super(archive, startTime, endTime, queryString);
      this.initialize();
   }

   PersistentRecordIterator(PersistentStoreDataArchive archive, long startId, long endId, long endTime, String queryString) throws QueryException {
      super(archive, startId, endId, endTime, queryString);
      this.initialize();
   }

   private void initialize() throws QueryException {
      this.columnCount = this.archive.getColumns().length;
      this.recordIdIndex = this.archive.getVariableIndex("RECORDID");
      this.timestampIndex = this.archive.getVariableIndex("TIMESTAMP");
   }

   protected int getRecordIdIndex() {
      return this.recordIdIndex;
   }

   protected int getTimestampIndex() {
      return this.timestampIndex;
   }

   protected long findFirstRecord(long value, boolean useTimestamp) {
      long retVal = -1L;

      try {
         PersistentStoreDataArchive theArchive = (PersistentStoreDataArchive)this.archive;
         this.currentRecordHandle = theArchive.findRecordHandle(value, useTimestamp);
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("PersistentRecordIterator: currentRecordHandle=" + this.currentRecordHandle);
         }

         if (this.currentRecordHandle != null) {
            RecordWrapper wrapper = theArchive.readRecord(this.currentRecordHandle);
            retVal = wrapper.startId;
         }
      } catch (Exception var8) {
         DiagnosticsLogger.logRecordNotFoundError(value, var8);
      }

      return retVal;
   }

   protected void fill() {
      PersistentStoreDataArchive persistentArchive = (PersistentStoreDataArchive)this.archive;

      while(this.currentRecordHandle != null && this.dataRecords.size() == 0) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("PersistentRecordIterator: Attemping to fill ...");
         }

         try {
            RecordWrapper wrapper = persistentArchive.readRecord(this.currentRecordHandle);
            wrapper.getDataObject(persistentArchive);
            boolean do_process = false;
            boolean end_reached = wrapper.timestamp >= this.endTime;
            if (this.useTimestamp) {
               if (wrapper.isTimestampWithinRange(this.startTime, this.endTime)) {
                  do_process = true;
               }
            } else {
               if (wrapper.isIdWithinRange(this.startId, this.endId)) {
                  do_process = true;
               }

               if (this.endId <= wrapper.startId) {
                  end_reached = true;
               }
            }

            if (end_reached) {
               this.currentRecordHandle = null;
               break;
            }

            if (do_process) {
               this.processRecord(wrapper);
            }

            this.currentRecordHandle = wrapper.next;
         } catch (PersistentStoreException var5) {
            DiagnosticsLogger.logRecordReadError(var5);
            this.currentRecordHandle = null;
         } catch (ClassCastException var6) {
            DiagnosticsLogger.logRecordReadError(var6);
            this.currentRecordHandle = null;
         }
      }

   }

   protected void processRecord(RecordWrapper wrapper) {
      Object dataObject = wrapper.getDataObject((PersistentStoreDataArchive)this.archive);
      if (dataObject instanceof Snapshot) {
         Snapshot snapshot = (Snapshot)dataObject;
         Collection data = snapshot.getData();
         int idIndex = 0;
         int tsIndex = 1;
         if (data != null) {
            Iterator it = data.iterator();

            while(true) {
               DataRecord dataRecord;
               while(true) {
                  if (!it.hasNext()) {
                     return;
                  }

                  dataRecord = (DataRecord)it.next();
                  Long recIdL;
                  long recId;
                  if (this.useTimestamp) {
                     recIdL = (Long)dataRecord.get(tsIndex);
                     recId = recIdL != null ? recIdL : 0L;
                     if (recId >= this.startTime) {
                        if (this.endTime <= recId) {
                           return;
                        }
                        break;
                     }
                  } else {
                     recIdL = (Long)dataRecord.get(idIndex);
                     recId = recIdL != null ? recIdL : 0L;
                     if (recId >= this.startId) {
                        if (this.endId <= recId) {
                           return;
                        }
                        break;
                     }
                  }
               }

               this.addRecord(dataRecord);
            }
         }
      } else if (dataObject instanceof DataRecord) {
         DataRecord dataRecord = (DataRecord)dataObject;
         this.addRecord(dataRecord);
      }

   }

   private void addRecord(DataRecord dataRecord) {
      this.currentDataRecord = dataRecord;

      try {
         if (this.query == null || this.query.executeQuery(this)) {
            Object[] data = dataRecord.getValues();
            int size = data != null ? data.length : 0;
            if (size < this.columnCount) {
               Object[] tmp = new Object[this.columnCount];
               System.arraycopy(data, 0, tmp, 0, size);
               dataRecord.setValues(tmp);
            }

            this.dataRecords.add(dataRecord);
         }
      } catch (QueryException var5) {
         DiagnosticsLogger.logQueryExecutionError(var5);
      }

   }
}
