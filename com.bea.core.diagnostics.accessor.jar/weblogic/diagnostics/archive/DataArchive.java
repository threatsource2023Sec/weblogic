package weblogic.diagnostics.archive;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.diagnostics.accessor.AccessRuntime;
import weblogic.diagnostics.accessor.AccessorMBeanFactory;
import weblogic.diagnostics.accessor.ColumnInfo;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.accessor.DiagnosticDataAccessException;
import weblogic.diagnostics.accessor.DiagnosticDataAccessService;
import weblogic.diagnostics.accessor.InvalidParameterException;
import weblogic.diagnostics.accessor.MissingParameterException;
import weblogic.diagnostics.accessor.runtime.ArchiveRuntimeMBean;
import weblogic.diagnostics.accessor.runtime.DataRetirementTaskRuntimeMBean;
import weblogic.diagnostics.query.QueryException;
import weblogic.diagnostics.query.UnknownVariableException;
import weblogic.diagnostics.query.VariableIndexResolver;
import weblogic.management.ManagementException;
import weblogic.utils.time.Timer;

public abstract class DataArchive implements DiagnosticDataAccessService, VariableIndexResolver {
   private String name;
   private ColumnInfo[] columns;
   private boolean readOnly;
   private Map columnIndices = new HashMap();
   protected Timer elapsedTimer = Timer.createTimer();
   protected ArchiveRuntimeMBean archiveRuntime;
   protected boolean isClosed;
   private long recordSeekCount;
   private long recordSeekTime;
   private long retrievedRecordCount;
   private long recordRetrievalTime;

   public abstract void close() throws DiagnosticDataAccessException, ManagementException;

   public DataArchive(String name, ColumnInfo[] columns, boolean readOnly) {
      this.name = name;
      this.columns = columns;
      this.readOnly = readOnly;
      int columnCount = columns != null ? columns.length : 0;

      for(int i = 0; i < columnCount; ++i) {
         this.columnIndices.put(columns[i].getColumnName(), new Integer(i));
      }

   }

   protected void registerRuntimeMBean() throws ManagementException {
      if (AccessRuntime.isInitialized()) {
         AccessRuntime accessRuntime = AccessRuntime.getAccessorInstance();
         AccessorMBeanFactory accMBeanFactory = accessRuntime.getAccessorMBeanFactory();
         this.archiveRuntime = accMBeanFactory.createDiagnosticArchiveRuntime(this);
      }

   }

   protected void unregisterRuntimeMBean() throws ManagementException {
      if (this.archiveRuntime != null) {
         AccessRuntime accessRuntime = AccessRuntime.getAccessorInstance();
         AccessorMBeanFactory accMBeanFactory = accessRuntime.getAccessorMBeanFactory();
         accMBeanFactory.destroyDiagnosticArchiveRuntime(this.archiveRuntime);
         this.archiveRuntime = null;
      }

   }

   public String getName() {
      return this.name;
   }

   public boolean isReadOnly() {
      return this.readOnly;
   }

   public ColumnInfo[] getColumns() {
      int size = this.columns != null ? this.columns.length : 0;
      ColumnInfo[] cols = new ColumnInfo[size];
      if (size > 0) {
         System.arraycopy(this.columns, 0, cols, 0, size);
      }

      return cols;
   }

   public String[] getAttributeNames() {
      return null;
   }

   public Object getAttribute(String attributeName, Object[] params) throws InvalidParameterException, MissingParameterException {
      return null;
   }

   public long getEarliestAvailableTimestamp() {
      return -1L;
   }

   public long getLatestAvailableTimestamp() {
      return System.currentTimeMillis();
   }

   public Object resolveVariable(DataRecord dataRecord, int varIndex) {
      return dataRecord.get(varIndex);
   }

   public Object resolveVariable(DataRecord dataRecord, String name) throws UnknownVariableException {
      Integer ind = (Integer)this.columnIndices.get(name);
      if (ind == null && name.startsWith("COL")) {
         try {
            int len = name.length();
            ind = new Integer(Integer.parseInt(name.substring(3, len)));
            this.columnIndices.put(name, ind);
         } catch (Exception var6) {
         }
      }

      try {
         return dataRecord.get(ind);
      } catch (Throwable var5) {
         throw new UnknownVariableException("Unknown variable: " + name);
      }
   }

   public int getVariableIndex(String varName) throws UnknownVariableException {
      Integer ind = (Integer)this.columnIndices.get(varName);
      if (ind == null) {
         throw new UnknownVariableException("Unknown column " + varName + " in archive " + this.getName());
      } else {
         return ind;
      }
   }

   private int countRecords(Iterator it) {
      int count = 0;
      if (it != null) {
         while(it.hasNext()) {
            Object rec = it.next();
            ++count;
         }
      }

      return count;
   }

   public int getDataRecordCount(String query) throws QueryException, DiagnosticDataAccessException {
      return this.countRecords(this.getDataRecords(query));
   }

   public int getDataRecordCount(long startTime, long endTime, String query) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException {
      return this.countRecords(this.getDataRecords(startTime, endTime, query));
   }

   public int getDataRecordCount(long startRecordId, long endRecordId, long endTime, String query) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException {
      return this.countRecords(this.getDataRecords(startRecordId, endRecordId, endTime, query));
   }

   public int deleteDataRecords(long startTime, long endTime, String query) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException {
      return this.deleteDataRecords(startTime, endTime, query, (DataRetirementTaskRuntimeMBean)null);
   }

   public int deleteDataRecords(long startTime, long endTime, String query, DataRetirementTaskRuntimeMBean task) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException {
      throw new UnsupportedOperationException("Archive " + this.getName() + " does not support record deletion");
   }

   public long getRecordSeekCount() {
      return this.recordSeekCount;
   }

   public synchronized void incrementRecordSeekCount(long incr) {
      this.recordSeekCount += incr;
   }

   public long getRecordSeekTime() {
      return this.recordSeekTime;
   }

   public synchronized void incrementRecordSeekTime(long incr) {
      this.recordSeekTime += incr;
   }

   public long getRetrievedRecordCount() {
      return this.retrievedRecordCount;
   }

   public synchronized void incrementRetrievedRecordCount(long incr) {
      this.retrievedRecordCount += incr;
   }

   public long getRecordRetrievalTime() {
      return this.recordRetrievalTime;
   }

   public synchronized void incrementRecordRetrievalTime(long incr) {
      this.recordRetrievalTime += incr;
   }

   public long getLatestKnownRecordID() throws DiagnosticDataAccessException, UnsupportedOperationException {
      throw new UnsupportedOperationException("Archive " + this.getName() + " does not support getKnownLatestRecordID");
   }

   public void updateRecord(long recordId, Map changeMap) throws DiagnosticDataAccessException, UnsupportedOperationException {
      throw new UnsupportedOperationException("Archive " + this.getName() + " does not support record update");
   }

   public boolean isTimestampAvailable() {
      return true;
   }
}
