package weblogic.diagnostics.archive;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.query.Query;
import weblogic.diagnostics.query.QueryException;
import weblogic.diagnostics.query.QueryFactory;
import weblogic.diagnostics.query.UnknownVariableException;
import weblogic.diagnostics.query.VariableResolver;
import weblogic.utils.time.Timer;

public abstract class RecordIterator implements Iterator, VariableResolver {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticArchive");
   protected DataArchive archive;
   protected long startTime;
   protected long endTime;
   protected long startId;
   protected long endId;
   protected Query query;
   protected List dataRecords;
   protected DataRecord currentDataRecord;
   protected boolean useTimestamp;
   private long currentRecordId;
   private int currentIndex;
   private boolean noMoreData;
   private Timer timer;

   protected abstract long findFirstRecord(long var1, boolean var3);

   protected abstract void fill();

   public RecordIterator(DataArchive archive, long startTime, long endTime, String queryString) throws QueryException {
      this(archive, startTime, endTime, 0L, 0L, true, queryString);
   }

   public RecordIterator(DataArchive archive, long startId, long endId, long endTime, String queryString) throws QueryException {
      this(archive, 0L, endTime, startId, endId, false, queryString);
   }

   private RecordIterator(DataArchive archive, long startTime, long endTime, long startId, long endId, boolean useTimestamp, String queryString) throws QueryException {
      this.dataRecords = new ArrayList();
      this.noMoreData = false;
      this.timer = Timer.createTimer();
      long t0 = this.timer.timestamp();
      this.archive = archive;
      this.startTime = startTime;
      this.endTime = endTime;
      this.startId = startId;
      this.endId = endId;
      this.useTimestamp = useTimestamp;
      if (useTimestamp) {
         if (endTime <= startTime) {
            this.noMoreData = true;
         }
      } else if (endId <= startId) {
         this.noMoreData = true;
      }

      if (DEBUG.isDebugEnabled()) {
         DebugLogger.println("RecordIterator: archive=" + archive.getName() + " useTimestamp=" + useTimestamp + " startTime=" + startTime + " endTime=" + endTime + " startId=" + startId + " endId=" + endId + " noMoreData=" + this.noMoreData);
      }

      long startValue;
      if (!this.noMoreData) {
         if (queryString != null && queryString.trim().length() > 0) {
            this.query = QueryFactory.createQuery(archive, queryString);
         }

         startValue = useTimestamp ? startTime : startId;
         this.currentRecordId = this.findFirstRecord(startValue, useTimestamp);
      }

      startValue = this.timer.timestamp();
      archive.incrementRecordSeekCount(1L);
      archive.incrementRecordSeekTime(startValue - t0);
   }

   protected long getCurrentRecordId() {
      long retVal = (long)(this.currentRecordId++);
      return retVal;
   }

   public boolean hasNext() {
      if (this.currentIndex < this.dataRecords.size()) {
         return true;
      } else {
         this.fetchMore();
         return this.dataRecords.size() > 0;
      }
   }

   public Object next() throws NoSuchElementException {
      long t0 = this.timer.timestamp();
      if (this.currentIndex >= this.dataRecords.size()) {
         this.fetchMore();
         if (this.dataRecords.size() <= 0) {
            throw new NoSuchElementException("No more elements");
         }
      }

      Object retVal = this.dataRecords.get(this.currentIndex++);
      long t1 = this.timer.timestamp();
      this.archive.incrementRetrievedRecordCount(1L);
      this.archive.incrementRecordRetrievalTime(t1 - t0);
      return retVal;
   }

   private void fetchMore() {
      this.dataRecords = new ArrayList();
      this.currentIndex = 0;
      if (!this.noMoreData) {
         this.fill();
      }

   }

   public void remove() throws UnsupportedOperationException, IllegalStateException {
      throw new UnsupportedOperationException("Remove operation not supported");
   }

   public Object resolveVariable(String name) throws UnknownVariableException {
      return this.currentDataRecord != null ? this.archive.resolveVariable(this.currentDataRecord, name) : null;
   }

   public Object resolveVariable(int index) throws UnknownVariableException {
      return this.currentDataRecord != null ? this.archive.resolveVariable(this.currentDataRecord, index) : null;
   }

   public int resolveInteger(int index) throws UnknownVariableException {
      Object o = this.resolveVariable(index);
      int val = 0;
      if (o != null) {
         if (o instanceof Number) {
            return ((Number)o).intValue();
         }

         try {
            val = Integer.parseInt(o.toString());
         } catch (Exception var5) {
         }
      }

      return val;
   }

   public long resolveLong(int index) throws UnknownVariableException {
      Object o = this.resolveVariable(index);
      long val = 0L;
      if (o != null) {
         if (o instanceof Number) {
            return ((Number)o).longValue();
         }

         try {
            val = Long.parseLong(o.toString());
         } catch (Exception var6) {
         }
      }

      return val;
   }

   public float resolveFloat(int index) throws UnknownVariableException {
      Object o = this.resolveVariable(index);
      float val = 0.0F;
      if (o != null) {
         if (o instanceof Number) {
            return ((Number)o).floatValue();
         }

         try {
            val = Float.parseFloat(o.toString());
         } catch (Exception var5) {
         }
      }

      return val;
   }

   public double resolveDouble(int index) throws UnknownVariableException {
      Object o = this.resolveVariable(index);
      double val = 0.0;
      if (o != null) {
         if (o instanceof Number) {
            return ((Number)o).doubleValue();
         }

         try {
            val = Double.parseDouble(o.toString());
         } catch (Exception var6) {
         }
      }

      return val;
   }

   public String resolveString(int index) throws UnknownVariableException {
      Object o = this.resolveVariable(index);
      return o != null ? o.toString() : null;
   }

   public int resolveInteger(String varName) throws UnknownVariableException {
      return this.resolveInteger(this.archive.getVariableIndex(varName));
   }

   public long resolveLong(String varName) throws UnknownVariableException {
      return this.resolveLong(this.archive.getVariableIndex(varName));
   }

   public float resolveFloat(String varName) throws UnknownVariableException {
      return this.resolveFloat(this.archive.getVariableIndex(varName));
   }

   public double resolveDouble(String varName) throws UnknownVariableException {
      return this.resolveDouble(this.archive.getVariableIndex(varName));
   }

   public String resolveString(String varName) throws UnknownVariableException {
      return this.resolveString(this.archive.getVariableIndex(varName));
   }
}
