package weblogic.diagnostics.accessor;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import weblogic.diagnostics.accessor.data.IncrementalDataReader;
import weblogic.diagnostics.accessor.data.IncrementalDataReaderFactory;
import weblogic.diagnostics.accessor.runtime.AccessRuntimeMBean;
import weblogic.diagnostics.accessor.runtime.DataAccessRuntimeMBean;
import weblogic.diagnostics.collections.BulkIteratorImpl;
import weblogic.diagnostics.collections.DataIteratorImpl;
import weblogic.diagnostics.collections.IteratorCollector;
import weblogic.diagnostics.collections.IteratorNotFoundException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.i18n.DiagnosticsTextTextFormatter;
import weblogic.diagnostics.query.QueryException;
import weblogic.logging.Loggable;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class DataAccessRuntime extends RuntimeMBeanDelegate implements DataAccessRuntimeMBean, AccessorConstants {
   private static final Random randomNumberGenerator = new Random();
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticAccessor");
   private static final HashSet VALID_FORMATS = new HashSet() {
      {
         this.add("csv");
         this.add("txt");
         this.add("xml");
         this.add("json");
      }
   };
   private static final DiagnosticsTextTextFormatter DIAG_TXT_FORMATTER = DiagnosticsTextTextFormatter.getInstance();
   private static final IteratorCollector iteratorCollector = IteratorCollector.getInstance();
   private DiagnosticDataAccessService delegate;
   private Map columnTypes = null;
   private Map columnInfos = null;
   private Map columnIndices = null;
   private long counter = 0L;
   private Map dataArchiveParameters;
   private static AccessorMBeanFactory accessorFactory;
   private static AccessorConfigurationProvider confProvider;
   private static AccessorSecurityProvider securityProvider;

   static void initialize(AccessorMBeanFactory accFactory, AccessorConfigurationProvider cfProvider, AccessorSecurityProvider secProvider) throws AccessorException {
      if (accessorFactory == null && confProvider == null) {
         accessorFactory = accFactory;
         confProvider = cfProvider;
         securityProvider = secProvider;
      } else {
         throw new AccessorException("DataAccessRuntime already initialized");
      }
   }

   private void ensureUserAuthorized(int action) throws ManagementException {
      try {
         if (securityProvider != null) {
            securityProvider.ensureUserAuthorized(action);
         }

      } catch (Exception var3) {
         throw new ManagementException(var3.getMessage(), var3);
      }
   }

   static DiagnosticDataAccessServiceStruct createDiagnosticDataAccessService(String logicalName) throws UnknownLogTypeException, DataAccessServiceCreateException {
      return createDiagnosticDataAccessService(logicalName, (ColumnInfo[])null);
   }

   static DiagnosticDataAccessServiceStruct createDiagnosticDataAccessService(String logicalName, ColumnInfo[] columns) throws UnknownLogTypeException, DataAccessServiceCreateException {
      String[] tokens = logicalName.split("/");
      String logType = tokens[0];
      Map params = confProvider.getAccessorConfiguration(logicalName).getAccessorParameters();
      DiagnosticDataAccessService service = accessorFactory.createDiagnosticDataAccessService(logicalName, logType, columns, params);
      return new DiagnosticDataAccessServiceStruct(service, params);
   }

   DataAccessRuntime(String name, AccessRuntimeMBean parentArg, DiagnosticDataAccessService service) throws ManagementException {
      super(name, parentArg);
      this.delegate = service;
   }

   public Iterator retrieveDataRecords(String query) throws ManagementException {
      this.ensureUserAuthorized(2);

      try {
         return new DataIteratorImpl(new BulkIteratorImpl(this.delegate.getDataRecords(query)));
      } catch (Exception var4) {
         Loggable l = DiagnosticsLogger.logErrorExecutingDiagnosticQueryLoggable(query, var4);
         l.log();
         throw new ManagementException(l.getMessageBody());
      }
   }

   public Iterator retrieveDataRecords(long beginTimeStamp, long endTimeStamp, String query) throws ManagementException {
      this.ensureUserAuthorized(2);

      try {
         return this.getIterator(beginTimeStamp, endTimeStamp, query);
      } catch (Exception var8) {
         Loggable l = DiagnosticsLogger.logErrorExecutingDiagnosticQueryLoggable(query, var8);
         l.log();
         throw new ManagementException(l.getMessageBody());
      }
   }

   private Iterator getIterator(long beginTimeStamp, long endTimeStamp, String query) throws UnsupportedOperationException, QueryException, DiagnosticDataAccessException {
      return new DataIteratorImpl(new BulkIteratorImpl(this.delegate.getDataRecords(beginTimeStamp, endTimeStamp, query)));
   }

   public Iterator retrieveDataRecords(long beginTimeStamp, String query) throws ManagementException {
      return this.retrieveDataRecords(beginTimeStamp, Long.MAX_VALUE, query);
   }

   public Iterator retrieveDataRecords(long beginRecordId, long endRecordId, long endTimeStamp, String query) throws ManagementException {
      this.ensureUserAuthorized(2);

      try {
         return this.getIterator(beginRecordId, endRecordId, endTimeStamp, query);
      } catch (Exception var10) {
         Loggable l = DiagnosticsLogger.logErrorExecutingDiagnosticQueryLoggable(query, var10);
         l.log();
         throw new ManagementException(l.getMessageBody());
      }
   }

   private Iterator getIterator(long beginRecordId, long endRecordId, long endTimeStamp, String query) throws UnsupportedOperationException, QueryException, DiagnosticDataAccessException {
      return new DataIteratorImpl(new BulkIteratorImpl(this.delegate.getDataRecords(beginRecordId, endRecordId, endTimeStamp, query)));
   }

   public DiagnosticDataAccessService getDiagnosticDataAccessService() {
      return this.delegate;
   }

   public ColumnInfo[] getColumns() throws ManagementException {
      this.ensureUserAuthorized(2);
      return this.delegate.getColumns();
   }

   public Map getColumnInfoMap() throws ManagementException {
      this.ensureUserAuthorized(2);
      if (this.columnInfos == null) {
         this.columnInfos = new HashMap();
         ColumnInfo[] columns = this.delegate.getColumns();

         for(int i = 0; i < columns.length; ++i) {
            ColumnInfo col = columns[i];
            this.columnInfos.put(col.getColumnName(), col);
         }
      }

      return this.columnInfos;
   }

   public Map getColumnTypeMap() throws ManagementException {
      this.ensureUserAuthorized(2);
      if (this.columnTypes == null) {
         this.columnTypes = new HashMap();
         ColumnInfo[] columns = this.delegate.getColumns();

         for(int i = 0; i < columns.length; ++i) {
            ColumnInfo col = columns[i];
            this.columnTypes.put(col.getColumnName(), col.getColumnTypeName());
         }
      }

      return this.columnTypes;
   }

   public Map getColumnIndexMap() throws ManagementException {
      this.ensureUserAuthorized(2);
      if (this.columnIndices == null) {
         this.columnIndices = new HashMap();
         ColumnInfo[] columns = this.delegate.getColumns();

         for(int i = 0; i < columns.length; ++i) {
            ColumnInfo col = columns[i];
            this.columnIndices.put(col.getColumnName(), i);
         }
      }

      return this.columnIndices;
   }

   public long getEarliestAvailableTimestamp() throws ManagementException {
      return this.delegate.getEarliestAvailableTimestamp();
   }

   public long getLatestAvailableTimestamp() throws ManagementException {
      return this.delegate.getLatestAvailableTimestamp();
   }

   public String openCursor(String query) throws ManagementException {
      return this.openCursor(0L, Long.MAX_VALUE, query, 300000L);
   }

   public String openCursor(String query, long cursorTimeout) throws ManagementException {
      return this.openCursor(0L, Long.MAX_VALUE, query, cursorTimeout);
   }

   public String openCursor(long beginTimestamp, long endTimestamp, String query) throws ManagementException {
      return this.openCursor(beginTimestamp, endTimestamp, query, 300000L);
   }

   public String openCursor(long beginTimestamp, long endTimestamp, String query, long cusorTimeout) throws ManagementException {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Invoked openCursor() with beginTimestamp = " + beginTimestamp + " endTimestamp = " + endTimestamp + " query = " + query + " cusorTimeout = " + cusorTimeout);
      }

      this.ensureUserAuthorized(2);

      try {
         String handle = this.getName() + "Iterator-" + this.counter++ + '-' + randomNumberGenerator.nextLong();
         if (this.counter == Long.MAX_VALUE) {
            this.counter = 0L;
         }

         Iterator iter = this.delegate.getDataRecords(beginTimestamp, endTimestamp, query);
         iteratorCollector.registerIterator(handle, iter, cusorTimeout);
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Opened handle " + handle);
         }

         return handle;
      } catch (Exception var10) {
         Loggable l = DiagnosticsLogger.logErrorExecutingDiagnosticQueryLoggable(query, var10);
         l.log();
         throw new ManagementException(l.getMessageBody());
      }
   }

   public String openCursor(long beginRecordId, long endRecordId, long endTimestamp, String query) throws ManagementException {
      return this.openCursor(beginRecordId, endRecordId, endTimestamp, query, 300000L);
   }

   public String openCursor(long beginRecordId, long endRecordId, long endTimestamp, String query, long cusorTimeout) throws ManagementException {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Invoked openCursor() with beginRecordId = " + beginRecordId + " endRecordId = " + endRecordId + " query = " + query + " cusorTimeout = " + cusorTimeout);
      }

      this.ensureUserAuthorized(2);

      try {
         String handle = this.getName() + "Iterator-" + this.counter++ + '-' + randomNumberGenerator.nextLong();
         if (this.counter == Long.MAX_VALUE) {
            this.counter = 0L;
         }

         Iterator iter = this.delegate.getDataRecords(beginRecordId, endRecordId, endTimestamp, query);
         iteratorCollector.registerIterator(handle, iter, cusorTimeout);
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Opened handle " + handle);
         }

         return handle;
      } catch (Exception var12) {
         Loggable l = DiagnosticsLogger.logErrorExecutingDiagnosticQueryLoggable(query, var12);
         l.log();
         throw new ManagementException(l.getMessageBody());
      }
   }

   public int getDataRecordCount(String query) throws ManagementException {
      this.ensureUserAuthorized(2);

      try {
         return this.delegate.getDataRecordCount(query);
      } catch (Exception var4) {
         Loggable l = DiagnosticsLogger.logErrorExecutingDiagnosticQueryLoggable(query, var4);
         l.log();
         throw new ManagementException(l.getMessageBody());
      }
   }

   public int getDataRecordCount(long startTime, long endTime, String query) throws ManagementException {
      this.ensureUserAuthorized(2);

      try {
         return this.delegate.getDataRecordCount(startTime, endTime, query);
      } catch (Exception var8) {
         Loggable l = DiagnosticsLogger.logErrorExecutingDiagnosticQueryLoggable(query, var8);
         l.log();
         throw new ManagementException(l.getMessageBody());
      }
   }

   public int getDataRecordCount(long startRecordId, long endRecordId, long endTime, String query) throws ManagementException {
      this.ensureUserAuthorized(2);

      try {
         return this.delegate.getDataRecordCount(startRecordId, endRecordId, endTime, query);
      } catch (Exception var10) {
         Loggable l = DiagnosticsLogger.logErrorExecutingDiagnosticQueryLoggable(query, var10);
         l.log();
         throw new ManagementException(l.getMessageBody());
      }
   }

   public boolean hasMoreData(String cursorName) throws ManagementException {
      this.ensureUserAuthorized(2);

      try {
         Iterator iter = iteratorCollector.getIterator(cursorName);
         boolean hasMore = iter.hasNext();
         if (!hasMore) {
            this.closeCursor(cursorName);
         }

         return hasMore;
      } catch (IteratorNotFoundException var4) {
         Loggable l = DiagnosticsLogger.logCursorNotFoundLoggable(cursorName);
         throw new ManagementException(l.getMessageBody());
      }
   }

   public Object[] fetch(String cursorName) throws ManagementException {
      return this.fetch(cursorName, 100);
   }

   public Object[] fetch(String cursorName, int maxItems) throws ManagementException {
      this.ensureUserAuthorized(2);

      try {
         ArrayList list = new ArrayList(maxItems);
         Iterator iter = iteratorCollector.getIterator(cursorName);

         while(iter.hasNext()) {
            Object[] data = ((DataRecord)iter.next()).getValues();
            list.add(data);
            if (list.size() == maxItems) {
               break;
            }
         }

         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Returning " + list.size() + " items in result for query with handle " + cursorName);
         }

         return list.toArray();
      } catch (IteratorNotFoundException var6) {
         Loggable l = DiagnosticsLogger.logCursorNotFoundLoggable(cursorName);
         throw new ManagementException(l.getMessageBody());
      }
   }

   public void closeCursor(String cursorName) throws ManagementException {
      this.ensureUserAuthorized(2);
      iteratorCollector.deregisterIterator(cursorName);
   }

   public int deleteDataRecords(long startTime, long endTime, String queryString) throws ManagementException {
      this.ensureUserAuthorized(4);

      try {
         return this.delegate.deleteDataRecords(startTime, endTime, queryString);
      } catch (DiagnosticDataAccessException var7) {
         throw new ManagementException(var7.getMessage(), var7);
      } catch (QueryException var8) {
         throw new ManagementException(var8.getMessage(), var8);
      } catch (UnsupportedOperationException var9) {
         throw new ManagementException(var9.getMessage(), var9);
      }
   }

   public Map getDataArchiveParameters() {
      return this.dataArchiveParameters;
   }

   public void setDataArchiveParameters(Map params) {
      this.dataArchiveParameters = params;
   }

   public void closeArchive() throws ManagementException {
      try {
         this.delegate.close();
      } catch (DiagnosticDataAccessException var2) {
         throw new ManagementException(var2);
      }
   }

   public void setDiagnosticDataAccessService(DiagnosticDataAccessService delegate) {
      this.delegate = delegate;
   }

   public long getLatestRecordId() throws ManagementException {
      try {
         return this.delegate.getLatestKnownRecordID();
      } catch (Exception var2) {
         throw new ManagementException(var2);
      }
   }

   public boolean isTimestampAvailable() throws ManagementException {
      try {
         return this.delegate.isTimestampAvailable();
      } catch (Exception var2) {
         throw new ManagementException(var2);
      }
   }

   public String openQueryResultDataStream(long beginTimestamp, long endTimestamp, String query, String format) throws ManagementException {
      if (!VALID_FORMATS.contains(format)) {
         throw new IllegalArgumentException(DIAG_TXT_FORMATTER.getInvalidDiagnosticDataExportFormatMsg(format));
      } else {
         DiagnosticDataWriter writer = null;

         String var12;
         try {
            final File tempFile = File.createTempFile("wldf-accessor-query-result", ".xml");
            String fileName = tempFile.getAbsolutePath();
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(tempFile));
            if (format.equals("txt")) {
               writer = new TextDataWriter(outputStream);
            } else if (format.equals("csv")) {
               writer = new CSVDataWriter(outputStream);
            } else {
               writer = new XMLDataWriter(outputStream, true);
            }

            ((DiagnosticDataWriter)writer).writeDiagnosticData(this.getColumns(), this.retrieveDataRecords(beginTimestamp, endTimestamp, query));
            IncrementalDataReader reader = IncrementalDataReaderFactory.getInstance().initializeIncrementalDataReader(fileName, new FileInputStream(tempFile));
            reader.setCloseAction(new IncrementalDataReader.CloseAction() {
               public void run() {
                  if (!tempFile.delete() && DataAccessRuntime.DEBUG.isDebugEnabled()) {
                     DataAccessRuntime.DEBUG.debug("Unable to delete temporary file " + tempFile);
                  }

               }
            });
            var12 = fileName;
         } catch (Exception var20) {
            throw new ManagementException(var20);
         } finally {
            if (writer != null) {
               try {
                  ((DiagnosticDataWriter)writer).close();
               } catch (IOException var21) {
                  if (DEBUG.isDebugEnabled()) {
                     DEBUG.debug("Error closing temp file output stream", var21);
                  }
               }
            }

         }

         return var12;
      }
   }

   public byte[] getNextQueryResultDataChunk(String name) throws ManagementException {
      try {
         IncrementalDataReader reader = IncrementalDataReaderFactory.getInstance().getIncrementalDataReader(name);
         return reader.getNextDataChunk();
      } catch (Exception var3) {
         throw new ManagementException(var3);
      }
   }

   public void closeQueryResultDataStream(String name) throws ManagementException {
      try {
         IncrementalDataReaderFactory.getInstance().closeIncrementalDataReader(name);
      } catch (Exception var3) {
         throw new ManagementException(var3);
      }
   }

   public void streamDiagnosticData(OutputStream outputStream, long beginTimeStamp, long endTimeStamp, String query, String format, long maxItems) throws ManagementException {
      if (!VALID_FORMATS.contains(format)) {
         throw new IllegalArgumentException(DIAG_TXT_FORMATTER.getInvalidDiagnosticDataExportFormatMsg(format));
      } else {
         try {
            this.ensureUserAuthorized(2);
            OutputStream outputStream = new BufferedOutputStream(outputStream);
            Object writer;
            if (format.equals("txt")) {
               writer = new TextDataWriter(outputStream, maxItems);
            } else {
               writer = new JSONDataWriter(outputStream, maxItems);
            }

            ((DiagnosticDataWriter)writer).writeDiagnosticData(this.getColumns(), this.getIterator(beginTimeStamp, endTimeStamp, query));
         } catch (Exception var11) {
            if (var11 instanceof ManagementException) {
               throw (ManagementException)var11;
            } else {
               throw new ManagementException(var11.getLocalizedMessage(), var11, false);
            }
         }
      }
   }

   public void streamDiagnosticDataFromRecord(OutputStream outputStream, long beginRecordId, long endTimeStamp, String query, String format, long maxItems) throws ManagementException {
      if (!VALID_FORMATS.contains(format)) {
         throw new IllegalArgumentException(DIAG_TXT_FORMATTER.getInvalidDiagnosticDataExportFormatMsg(format));
      } else {
         try {
            this.ensureUserAuthorized(2);
            OutputStream outputStream = new BufferedOutputStream(outputStream);
            Object writer;
            if (format.equals("txt")) {
               writer = new TextDataWriter(outputStream, maxItems);
            } else {
               writer = new JSONDataWriter(outputStream, maxItems);
            }

            ((DiagnosticDataWriter)writer).writeDiagnosticData(this.getColumns(), this.getIterator(beginRecordId, Long.MAX_VALUE, endTimeStamp, query));
         } catch (Exception var11) {
            if (var11 instanceof ManagementException) {
               throw (ManagementException)var11;
            } else {
               throw new ManagementException(var11.getLocalizedMessage(), var11, false);
            }
         }
      }
   }

   static class DiagnosticDataAccessServiceStruct {
      private DiagnosticDataAccessService service;
      private Map createParams;

      public DiagnosticDataAccessServiceStruct(DiagnosticDataAccessService ddas, Map params) {
         this.service = ddas;
         this.createParams = params;
      }

      public DiagnosticDataAccessService getDiagnosticDataAccessService() {
         return this.service;
      }

      public Map getCreateParams() {
         return this.createParams;
      }
   }
}
