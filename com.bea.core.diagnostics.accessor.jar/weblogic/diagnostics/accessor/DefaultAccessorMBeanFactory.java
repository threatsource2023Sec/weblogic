package weblogic.diagnostics.accessor;

import java.io.File;
import java.util.Map;
import weblogic.diagnostics.accessor.parser.LogRecordParser;
import weblogic.diagnostics.accessor.runtime.AccessRuntimeMBean;
import weblogic.diagnostics.accessor.runtime.ArchiveRuntimeMBean;
import weblogic.diagnostics.accessor.runtime.DataAccessRuntimeMBean;
import weblogic.diagnostics.accessor.runtime.DataRetirementTaskRuntimeMBean;
import weblogic.diagnostics.archive.DataRetirementByAgeTaskImpl;
import weblogic.diagnostics.archive.DiagnosticDbstoreArchiveRuntime;
import weblogic.diagnostics.archive.DiagnosticFileArchiveRuntime;
import weblogic.diagnostics.archive.DiagnosticWlstoreArchiveRuntime;
import weblogic.diagnostics.archive.EditableDataArchive;
import weblogic.diagnostics.archive.dbstore.JdbcDataArchive;
import weblogic.diagnostics.archive.filestore.FileDataArchive;
import weblogic.diagnostics.archive.filestore.GenericLogFileArchive;
import weblogic.diagnostics.archive.wlstore.GenericPersistentStoreDataArchive;
import weblogic.diagnostics.archive.wlstore.PersistentStoreDataArchive;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.logging.Loggable;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;

public class DefaultAccessorMBeanFactory implements AccessorMBeanFactory {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticAccessor");
   private AccessorConfigurationProvider confProvider;
   private RuntimeMBean accessorParent;

   DefaultAccessorMBeanFactory(AccessorConfigurationProvider confProvider, RuntimeMBean accessorParent) {
      this.confProvider = confProvider;
      this.accessorParent = accessorParent;
   }

   public ArchiveRuntimeMBean createDiagnosticArchiveRuntime(DiagnosticDataAccessService archive) throws ManagementException {
      RuntimeMBean parent = this.accessorParent;
      ArchiveRuntimeMBean archiveRuntime = null;
      if (archive instanceof FileDataArchive) {
         archiveRuntime = new DiagnosticFileArchiveRuntime((FileDataArchive)archive, parent);
      } else if (archive instanceof JdbcDataArchive) {
         archiveRuntime = new DiagnosticDbstoreArchiveRuntime((JdbcDataArchive)archive, parent);
      } else {
         if (!(archive instanceof PersistentStoreDataArchive)) {
            throw new ManagementException("Unknown archive type: " + archive);
         }

         archiveRuntime = new DiagnosticWlstoreArchiveRuntime((PersistentStoreDataArchive)archive, parent);
      }

      return (ArchiveRuntimeMBean)archiveRuntime;
   }

   public DataAccessRuntimeMBean createDiagnosticDataAccessRuntime(String logicalName, ColumnInfo[] columns, AccessRuntimeMBean parent) throws ManagementException {
      try {
         AccessorConfiguration accConf = this.confProvider.getAccessorConfiguration(logicalName);
         if (accConf == null) {
            throw new UnknownLogTypeException(logicalName);
         } else {
            DataAccessRuntime.DiagnosticDataAccessServiceStruct ddas = DataAccessRuntime.createDiagnosticDataAccessService(logicalName, columns);
            DiagnosticDataAccessService service = ddas.getDiagnosticDataAccessService();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Creating DiagnosticDataAccessRuntime with Name=" + logicalName);
            }

            DataAccessRuntime ddar = new DataAccessRuntime(logicalName, parent, service);
            ddar.setDataArchiveParameters(ddas.getCreateParams());
            return ddar;
         }
      } catch (Exception var8) {
         Loggable l = DiagnosticsLogger.logErrorCreatingDiagnosticDataRuntimeLoggable(logicalName, var8);
         l.log();
         throw new ManagementException(l.getMessageBody());
      }
   }

   public DiagnosticDataAccessService createDiagnosticDataAccessService(String logicalName, String logType, ColumnInfo[] columns, Map params) throws UnknownLogTypeException, DataAccessServiceCreateException {
      DiagnosticDataAccessService service = null;

      try {
         AccessorConfiguration accConf = this.confProvider.getAccessorConfiguration(logicalName);
         if (accConf instanceof LogAccessorConfiguration) {
            LogAccessorConfiguration logAccConf = (LogAccessorConfiguration)accConf;
            LogRecordParser recordParser = logAccConf.getRecordParser();
            service = new GenericLogFileArchive(logicalName, new File(logAccConf.getLogFilePath()), new File(logAccConf.getLogFileRotationDirectory()), new File(this.confProvider.getStoreDirectory()), recordParser);
         } else {
            if (!(accConf instanceof EditableAccessorConfiguration)) {
               throw new UnknownLogTypeException(logicalName);
            }

            service = new GenericPersistentStoreDataArchive(logicalName, accConf.getColumns(), this.confProvider.getStoreDirectory(), false);
         }

         return (DiagnosticDataAccessService)service;
      } catch (Exception var9) {
         throw new DataAccessServiceCreateException(var9);
      }
   }

   public DataRetirementTaskRuntimeMBean createRetirementByAgeTask(DiagnosticDataAccessService archive, long endTime) throws ManagementException {
      DataRetirementTaskRuntimeMBean task = null;
      if (archive instanceof EditableDataArchive) {
         task = new DataRetirementByAgeTaskImpl((EditableDataArchive)archive, endTime);
      }

      return task;
   }

   public void destroyDiagnosticArchiveRuntime(ArchiveRuntimeMBean archiveRuntime) {
   }

   public AccessRuntimeMBean createDiagnosticAccessRuntime(AccessorConfigurationProvider confProvider, AccessorSecurityProvider securityProvider, RuntimeMBean parent) throws ManagementException {
      return new AccessRuntime(this, confProvider, securityProvider, parent);
   }

   public String[] getAvailableDiagnosticDataAccessorNames() {
      return this.confProvider.getAccessorNames();
   }
}
