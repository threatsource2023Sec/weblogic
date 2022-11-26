package weblogic.diagnostics.archive;

import weblogic.diagnostics.accessor.ColumnInfo;
import weblogic.diagnostics.accessor.DiagnosticDataAccessException;
import weblogic.diagnostics.accessor.runtime.DataRetirementTaskRuntimeMBean;
import weblogic.diagnostics.query.QueryException;

public abstract class EditableDataArchive extends DataArchive {
   private int dataRetirementCycles;
   private long lastDataRetirementStartTime;
   private long lastDataRetirementTime;
   private long dataRetirementTotalTime;
   private long retiredRecordCount;

   public EditableDataArchive(String name, ColumnInfo[] columns, boolean readOnly) {
      super(name, columns, readOnly);
   }

   public int getDataRetirementCycles() {
      return this.dataRetirementCycles;
   }

   public long getDataRetirementTotalTime() {
      return this.dataRetirementTotalTime;
   }

   public long getLastDataRetirementStartTime() {
      return this.lastDataRetirementStartTime;
   }

   public long getLastDataRetirementTime() {
      return this.lastDataRetirementTime;
   }

   public long getRetiredRecordCount() {
      return this.retiredRecordCount;
   }

   public long retireDataRecords(long endTime, DataRetirementTaskRuntimeMBean task) throws DiagnosticDataAccessException, UnsupportedOperationException {
      long retVal = 0L;

      try {
         long t0 = System.currentTimeMillis();
         retVal = (long)this.deleteDataRecords(0L, endTime, (String)null, task);
         long t1 = System.currentTimeMillis();
         this.updateRetirementStatistics(t0, t1, retVal);
         return retVal;
      } catch (QueryException var10) {
         throw new DiagnosticDataAccessException(var10);
      }
   }

   protected void updateRetirementStatistics(long startTime, long endTime, long count) {
      ++this.dataRetirementCycles;
      this.lastDataRetirementStartTime = startTime;
      this.lastDataRetirementTime = endTime - startTime;
      this.dataRetirementTotalTime += this.lastDataRetirementTime;
      this.retiredRecordCount += count;
   }
}
