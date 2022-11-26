package weblogic.diagnostics.accessor;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.naming.NamingException;
import weblogic.diagnostics.archive.ArchiveConstants;
import weblogic.diagnostics.archive.dbstore.EventsJdbcDataArchive;
import weblogic.diagnostics.archive.dbstore.HarvestedJdbcDataArchive;
import weblogic.diagnostics.archive.filestore.AccessLogFileDataArchive;
import weblogic.diagnostics.archive.filestore.DataSourceLogFileDataArchive;
import weblogic.diagnostics.archive.filestore.JMSLogFileDataArchive;
import weblogic.diagnostics.archive.filestore.ServerLogFileDataArchive;
import weblogic.diagnostics.archive.filestore.UnformattedLogFileDataArchive;
import weblogic.diagnostics.archive.wlstore.EventsPersistentStoreDataArchive;
import weblogic.diagnostics.archive.wlstore.GenericPersistentStoreDataArchive;
import weblogic.diagnostics.archive.wlstore.HarvestedPersistentStoreDataArchive;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.kernel.Kernel;
import weblogic.management.ManagementException;
import weblogic.store.PersistentStoreException;

public final class DiagnosticDataAccessServiceFactory implements AccessorConstants {
   public static DiagnosticDataAccessService createDiagnosticDataAccessService(String logicalName, String logType, Map args) throws UnknownLogTypeException, DataAccessServiceCreateException {
      return createDiagnosticDataAccessService(logicalName, logType, (ColumnInfo[])null, args);
   }

   public static DiagnosticDataAccessService createDiagnosticDataAccessService(String logicalName, String logType, ColumnInfo[] columns, Map args) throws UnknownLogTypeException, DataAccessServiceCreateException {
      DiagnosticDataAccessService accessService = createDiagnosticDataAccessService_internal(logicalName, logType, columns, args);
      String filePath = (String)args.get("logFilePath");
      String logRotationDir = (String)args.get("logRotationDir");
      if (filePath != null) {
         accessService = new DiagnosticDataAccessServiceWrapper((DiagnosticDataAccessService)accessService, logicalName, logType, args);
      }

      return (DiagnosticDataAccessService)accessService;
   }

   static DiagnosticDataAccessService createDiagnosticDataAccessService_internal(String logicalName, String logType, ColumnInfo[] columns, Map args) throws UnknownLogTypeException, DataAccessServiceCreateException {
      try {
         if (logType.equals("ServerLog")) {
            return createServerLogDataAccessService(logicalName, args);
         } else if (logType.equals("DomainLog")) {
            return createDomainLogDataAccessService(logicalName, args);
         } else if (logType.equals("DataSourceLog")) {
            return createDataSourceLogDataAccessService(logicalName, args);
         } else if (logType.equals("HarvestedDataArchive")) {
            return createHarvestedDataAccessService(logicalName, args);
         } else if (logType.equals("EventsDataArchive")) {
            return createEventsDataAccessService(logicalName, args);
         } else if (logType.equals("HTTPAccessLog")) {
            return createHTTPAccessLogDataAccessService(logicalName, args);
         } else if (logType.equals("WebAppLog")) {
            return createWebAppLogDataAccessService(logicalName, args);
         } else if (logType.equals("ConnectorLog")) {
            return createConnectorLogDataAccessService(logicalName, args);
         } else if (!logType.equals("JMSMessageLog") && !logType.equals("JMSSAFMessageLog")) {
            if (logType.equals("CUSTOM")) {
               return createGenericDataAccessService(logicalName, columns, args);
            } else {
               throw new UnknownLogTypeException(logType);
            }
         } else {
            return createJMSMessageLogDataAccessService(logicalName, args);
         }
      } catch (Exception var5) {
         throw new DataAccessServiceCreateException(var5);
      }
   }

   private static DiagnosticDataAccessService createServerLogDataAccessService(String logicalName, Map args) throws IOException, ManagementException {
      String filePath = (String)args.get("logFilePath");
      File archiveFile = new File(filePath);
      String wldfStoreDirPath = (String)args.get("storeDir");
      File wldfStoreDirFile = wldfStoreDirPath != null ? new File(wldfStoreDirPath) : null;
      File rotationDirFile = archiveFile.getParentFile();
      String logRotationDir = (String)args.get("logRotationDir");
      if (logRotationDir != null && logRotationDir.length() > 0) {
         rotationDirFile = new File(logRotationDir);
      }

      return new ServerLogFileDataArchive(logicalName, new File(filePath), rotationDirFile, wldfStoreDirFile, false);
   }

   private static DiagnosticDataAccessService createDomainLogDataAccessService(String logicalName, Map args) throws IOException, ManagementException {
      String filePath = (String)args.get("logFilePath");
      File archiveFile = new File(filePath);
      String wldfStoreDirPath = (String)args.get("storeDir");
      File wldfStoreDirFile = wldfStoreDirPath != null ? new File(wldfStoreDirPath) : null;
      File rotationDirFile = archiveFile.getParentFile();
      String logRotationDir = (String)args.get("logRotationDir");
      if (logRotationDir != null && logRotationDir.length() > 0) {
         rotationDirFile = new File(logRotationDir);
      }

      return new ServerLogFileDataArchive(logicalName, new File(filePath), rotationDirFile, wldfStoreDirFile, !Kernel.isServer());
   }

   private static DiagnosticDataAccessService createDataSourceLogDataAccessService(String logicalName, Map args) throws IOException, ManagementException {
      String filePath = (String)args.get("logFilePath");
      File archiveFile = new File(filePath);
      String wldfStoreDirPath = (String)args.get("storeDir");
      File wldfStoreDirFile = wldfStoreDirPath != null ? new File(wldfStoreDirPath) : null;
      File rotationDirFile = archiveFile.getParentFile();
      String logRotationDir = (String)args.get("logRotationDir");
      if (logRotationDir != null && logRotationDir.length() > 0) {
         rotationDirFile = new File(logRotationDir);
      }

      return new DataSourceLogFileDataArchive(logicalName, new File(filePath), rotationDirFile, wldfStoreDirFile, !Kernel.isServer());
   }

   private static DiagnosticDataAccessService createHTTPAccessLogDataAccessService(String logicalName, Map args) throws IOException, ManagementException {
      String filePath = (String)args.get("logFilePath");
      File archiveFile = new File(filePath);
      String wldfStoreDirPath = (String)args.get("storeDir");
      File wldfStoreDirFile = wldfStoreDirPath != null ? new File(wldfStoreDirPath) : null;
      File rotationDirFile = archiveFile.getParentFile();
      String logRotationDir = (String)args.get("logRotationDir");
      if (logRotationDir != null && logRotationDir.length() > 0) {
         rotationDirFile = new File(logRotationDir);
      }

      ColumnInfo[] dataCols = ArchiveConstants.getColumns(4);
      int dateColumnIndex = -1;
      int timestampColumnIndex = 4;
      int type = true;
      if (args.containsKey("elfFields")) {
         int timestampColumnIndex = true;
         String elfFields = (String)args.get("elfFields");
         String[] colNames = elfFields.trim().split("\\s+");
         return new AccessLogFileDataArchive(logicalName, new File(filePath), rotationDirFile, wldfStoreDirFile, colNames, !Kernel.isServer());
      } else {
         return new AccessLogFileDataArchive(logicalName, new File(filePath), rotationDirFile, wldfStoreDirFile, "dd/MMM/yyyy:HH:mm:ss Z", dataCols, dateColumnIndex, timestampColumnIndex, !Kernel.isServer());
      }
   }

   private static DiagnosticDataAccessService createHarvestedDataAccessService(String logicalName, Map args) throws PersistentStoreException, NamingException, ManagementException {
      String jndiName;
      if (args.containsKey("jndiName")) {
         jndiName = (String)args.get("jndiName");
         String schemaName = (String)args.get("schemaName");

         try {
            return new HarvestedJdbcDataArchive(logicalName, jndiName, schemaName);
         } catch (Exception var5) {
            DiagnosticsLogger.logErrorInitializingJDBCArchive(logicalName, var5);
         }
      }

      jndiName = (String)args.get("storeDir");
      return new HarvestedPersistentStoreDataArchive(logicalName, jndiName, !Kernel.isServer());
   }

   private static DiagnosticDataAccessService createEventsDataAccessService(String logicalName, Map args) throws PersistentStoreException, NamingException, ManagementException {
      String jndiName;
      if (args.containsKey("jndiName")) {
         jndiName = (String)args.get("jndiName");
         String schemaName = (String)args.get("schemaName");

         try {
            return new EventsJdbcDataArchive(logicalName, jndiName, schemaName);
         } catch (Exception var5) {
            DiagnosticsLogger.logErrorInitializingJDBCArchive(logicalName, var5);
         }
      }

      jndiName = (String)args.get("storeDir");
      return new EventsPersistentStoreDataArchive(logicalName, jndiName, !Kernel.isServer());
   }

   private static DiagnosticDataAccessService createGenericDataAccessService(String logicalName, ColumnInfo[] columns, Map args) throws PersistentStoreException, ManagementException {
      String wldfStoreDirPath = (String)args.get("storeDir");
      if (wldfStoreDirPath == null) {
         throw new PersistentStoreException("Missing diagnostic store directory");
      } else {
         int size = columns != null ? columns.length : 0;
         if (size > 0) {
            ColumnInfo col = columns[0];
            if (!col.getColumnName().equals("RECORDID") || col.getColumnType() != 2) {
               throw new PersistentStoreException("Missing first column RECORDID of type COLTYPE_LONG");
            }

            col = size > 1 ? columns[1] : null;
            if (col == null || !col.getColumnName().equals("TIMESTAMP") || col.getColumnType() != 2) {
               throw new PersistentStoreException("Missing second column TIMESTAMP of type COLTYPE_LONG");
            }
         } else {
            columns = null;
         }

         return new GenericPersistentStoreDataArchive(logicalName, columns, wldfStoreDirPath, !Kernel.isServer());
      }
   }

   private static DiagnosticDataAccessService createWebAppLogDataAccessService(String logicalName, Map args) throws IOException, ManagementException {
      String filePath = (String)args.get("logFilePath");
      File archiveFile = new File(filePath);
      String wldfStoreDirPath = (String)args.get("storeDir");
      File wldfStoreDirFile = wldfStoreDirPath != null ? new File(wldfStoreDirPath) : null;
      File rotationDirFile = archiveFile.getParentFile();
      String logRotationDir = (String)args.get("logRotationDir");
      if (logRotationDir != null && logRotationDir.length() > 0) {
         rotationDirFile = new File(logRotationDir);
      }

      return new ServerLogFileDataArchive(logicalName, new File(filePath), rotationDirFile, wldfStoreDirFile, !Kernel.isServer());
   }

   private static DiagnosticDataAccessService createConnectorLogDataAccessService(String logicalName, Map args) throws IOException, ManagementException {
      String filePath = (String)args.get("logFilePath");
      File archiveFile = new File(filePath);
      String wldfStoreDirPath = (String)args.get("storeDir");
      File wldfStoreDirFile = wldfStoreDirPath != null ? new File(wldfStoreDirPath) : null;
      File rotationDirFile = archiveFile.getParentFile();
      String logRotationDir = (String)args.get("logRotationDir");
      if (logRotationDir != null && logRotationDir.length() > 0) {
         rotationDirFile = new File(logRotationDir);
      }

      return new UnformattedLogFileDataArchive(logicalName, new File(filePath), rotationDirFile, wldfStoreDirFile, !Kernel.isServer());
   }

   private static DiagnosticDataAccessService createJMSMessageLogDataAccessService(String logicalName, Map args) throws IOException, ManagementException {
      String filePath = (String)args.get("logFilePath");
      File archiveFile = new File(filePath);
      String wldfStoreDirPath = (String)args.get("storeDir");
      File wldfStoreDirFile = wldfStoreDirPath != null ? new File(wldfStoreDirPath) : null;
      File rotationDirFile = archiveFile.getParentFile();
      String logRotationDir = (String)args.get("logRotationDir");
      if (logRotationDir != null && logRotationDir.length() > 0) {
         rotationDirFile = new File(logRotationDir);
      }

      return new JMSLogFileDataArchive(logicalName, new File(filePath), rotationDirFile, wldfStoreDirFile, !Kernel.isServer());
   }
}
