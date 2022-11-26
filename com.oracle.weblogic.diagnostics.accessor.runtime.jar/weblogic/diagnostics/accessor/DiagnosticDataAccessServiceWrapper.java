package weblogic.diagnostics.accessor;

import com.bea.logging.LogFileChangeListener;
import com.bea.logging.LoggingService;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.query.QueryException;
import weblogic.management.ManagementException;

public class DiagnosticDataAccessServiceWrapper implements DiagnosticDataAccessService, LogFileChangeListener {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticAccessor");
   private DiagnosticDataAccessService delegate;
   private String logicalName;
   private String logType;
   private Map arguments;
   private boolean delegateStale = false;

   DiagnosticDataAccessServiceWrapper(DiagnosticDataAccessService delegate, String logicalName, String logType, Map args) {
      this.delegate = delegate;
      this.logicalName = logicalName;
      this.logType = logType;
      this.arguments = new HashMap();
      this.arguments.putAll(args);
      LoggingService.getInstance().registerLogFileChangeListener(this);
   }

   public void logFilePathsChanged(String oldFilePath, String oldRotationDir, String newFilePath, String newRotationDir) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("logFilePathsChanged: oldFilePath=" + oldFilePath + " oldRotationDir=" + oldRotationDir + " newFilePath=" + newFilePath + " newRotationDir=" + newRotationDir + " prevFilePath=" + this.arguments.get("logFilePath") + " prevRotationDir=" + this.arguments.get("logRotationDir"));
      }

      File old_path = new File(oldFilePath);
      File old_rotationDir = oldRotationDir != null ? new File(oldRotationDir) : old_path.getParentFile();
      File f_path = new File((String)this.arguments.get("logFilePath"));
      String prevRotationDir = (String)this.arguments.get("logRotationDir");
      File f_rotationDir = prevRotationDir != null ? new File(prevRotationDir) : f_path.getParentFile();
      if (f_path.equals(old_path) && f_rotationDir.equals(old_rotationDir)) {
         this.arguments.put("logFilePath", newFilePath);
         this.arguments.put("logRotationDir", newRotationDir);
         synchronized(this) {
            this.delegateStale = true;
         }
      }

   }

   private void recreateDelegate() throws Exception {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Recreating delegate for accessor " + this.logicalName + " with " + this.arguments);
      }

      if (this.delegate != null) {
         this.delegate.close();
      }

      this.delegate = DiagnosticDataAccessServiceFactory.createDiagnosticDataAccessService_internal(this.logicalName, this.logType, (ColumnInfo[])null, this.arguments);
   }

   public String getName() {
      return this.getDelegate().getName();
   }

   public String getDescription() {
      return this.getDelegate().getDescription();
   }

   public ColumnInfo[] getColumns() {
      return this.getDelegate().getColumns();
   }

   public long getEarliestAvailableTimestamp() {
      return this.getDelegate().getEarliestAvailableTimestamp();
   }

   public long getLatestAvailableTimestamp() {
      return this.getDelegate().getLatestAvailableTimestamp();
   }

   public String[] getAttributeNames() {
      return this.getDelegate().getAttributeNames();
   }

   public Object getAttribute(String attributeName, Object[] params) throws InvalidParameterException, MissingParameterException {
      return this.getDelegate().getAttribute(attributeName, params);
   }

   public Iterator getDataRecords(String query) throws QueryException, DiagnosticDataAccessException {
      return this.getDelegate().getDataRecords(query);
   }

   public Iterator getDataRecords(long startTime, long endTime, String query) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException {
      return this.getDelegate().getDataRecords(startTime, endTime, query);
   }

   public Iterator getDataRecords(long startRecordId, long endrecordId, long endTime, String query) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException {
      return this.getDelegate().getDataRecords(startRecordId, endrecordId, endTime, query);
   }

   public int getDataRecordCount(String query) throws QueryException, DiagnosticDataAccessException {
      return this.getDelegate().getDataRecordCount(query);
   }

   public int getDataRecordCount(long startTime, long endTime, String query) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException {
      return this.getDelegate().getDataRecordCount(startTime, endTime, query);
   }

   public int getDataRecordCount(long startRecordId, long endRecordId, long endTime, String query) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException {
      return this.getDelegate().getDataRecordCount(startRecordId, endRecordId, endTime, query);
   }

   public int deleteDataRecords(long startTime, long endTime, String query) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException {
      return this.getDelegate().deleteDataRecords(startTime, endTime, query);
   }

   public void close() throws DiagnosticDataAccessException, ManagementException {
      this.getDelegate().close();
      LoggingService.getInstance().unregisterLogFileChangeListener(this);
   }

   public long getLatestKnownRecordID() throws DiagnosticDataAccessException {
      return this.getDelegate().getLatestKnownRecordID();
   }

   public boolean isTimestampAvailable() {
      return this.getDelegate().isTimestampAvailable();
   }

   synchronized DiagnosticDataAccessService getDelegate() {
      if (this.delegateStale) {
         try {
            this.recreateDelegate();
         } catch (Exception var2) {
            DiagnosticsLogger.logAccessorFailedToAdaptToLogfileChange(this.logicalName, var2);
         }
      }

      return this.delegate;
   }

   synchronized void setDelegate(DiagnosticDataAccessService archive) {
      this.delegate = archive;
   }
}
