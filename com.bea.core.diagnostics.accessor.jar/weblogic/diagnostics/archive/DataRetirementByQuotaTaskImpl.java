package weblogic.diagnostics.archive;

import weblogic.diagnostics.accessor.AccessRuntime;
import weblogic.diagnostics.accessor.AccessorConfiguration;
import weblogic.diagnostics.accessor.AccessorConfigurationProvider;
import weblogic.diagnostics.accessor.DataAccessRuntime;
import weblogic.diagnostics.accessor.EditableAccessorConfiguration;
import weblogic.diagnostics.archive.wlstore.PersistentStoreDataArchive;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.i18n.DiagnosticsTextTextFormatter;
import weblogic.management.ManagementException;

public class DataRetirementByQuotaTaskImpl extends DataRetirementTaskImpl {
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugDiagnosticArchiveRetirement");

   public DataRetirementByQuotaTaskImpl() throws ManagementException {
      super("DataRetirementBySize");
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Created DataRetirementByQuotaTaskImpl");
      }

   }

   public String getDescription() {
      return DiagnosticsTextTextFormatter.getInstance().getSizeBasedDataRetirementTaskDescriptionText();
   }

   public void run() {
      this.setBeginTime(System.currentTimeMillis());

      try {
         AccessRuntime accessRuntime = AccessRuntime.getAccessorInstance();
         String[] accessorNames = accessRuntime.getAvailableDiagnosticDataAccessorNames();
         int size = accessorNames != null ? accessorNames.length : 0;

         for(int i = 0; i < size; ++i) {
            this.performDataRetirement(accessRuntime, accessorNames[i]);
         }
      } catch (Exception var5) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Could not find AccessRuntime", var5);
         }
      }

      this.setEndTime(System.currentTimeMillis());
   }

   private void performDataRetirement(AccessRuntime accessRuntime, String archiveName) {
      try {
         if (!this.isRunning()) {
            return;
         }

         AccessorConfigurationProvider config = accessRuntime.getAccessorConfigurationProvider();
         AccessorConfiguration accConfig = config.getAccessorConfiguration(archiveName);
         boolean doit = false;
         if (accConfig instanceof EditableAccessorConfiguration) {
            EditableAccessorConfiguration eaConf = (EditableAccessorConfiguration)accConfig;
            if (eaConf.isParticipantInSizeBasedDataRetirement()) {
               doit = true;
            }
         }

         if (!doit) {
            return;
         }

         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Performing size based retirement on " + archiveName);
         }

         long t0 = System.currentTimeMillis();
         String desc = this.getDescription();
         DiagnosticsLogger.logDataRetirementOperationBegin(desc, archiveName);
         int cnt = 0;
         DataAccessRuntime runtime = (DataAccessRuntime)accessRuntime.lookupDataAccessRuntime(archiveName);
         if (runtime.getDiagnosticDataAccessService() instanceof PersistentStoreDataArchive) {
            PersistentStoreDataArchive archive = (PersistentStoreDataArchive)runtime.getDiagnosticDataAccessService();
            cnt = archive.retireOldestRecords(this);
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("Size based retirement: Deleted " + cnt + " records from " + archiveName);
            }
         }

         long t1 = System.currentTimeMillis();
         DiagnosticsLogger.logDataRetirementOperationEnd(desc, archiveName, (long)cnt, t1 - t0);
      } catch (Exception var13) {
         DiagnosticsLogger.logSizeBasedDataRetirementError(archiveName, var13);
      }

   }
}
