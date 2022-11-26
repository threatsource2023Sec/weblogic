package weblogic.diagnostics.archive;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.i18n.DiagnosticsTextTextFormatter;
import weblogic.management.ManagementException;

public class DataRetirementByAgeTaskImpl extends DataRetirementTaskImpl {
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugDiagnosticArchiveRetirement");
   private EditableDataArchive archive;
   private long timeLimit;

   public DataRetirementByAgeTaskImpl(EditableDataArchive archive, long timeLimit) throws ManagementException {
      super("DataRetirementByAge_" + archive.getName());
      this.archive = archive;
      this.timeLimit = timeLimit;
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Created DataRetirementByAgeTaskImpl for " + archive.getName());
      }

   }

   public String getDescription() {
      return DiagnosticsTextTextFormatter.getInstance().getAgeBasedDataRetirementTaskDescriptionText();
   }

   public void run() {
      long t0 = System.currentTimeMillis();
      this.setBeginTime(t0);
      String archiveName = this.archive.getName();
      String desc = this.getDescription();
      DiagnosticsLogger.logDataRetirementOperationBegin(desc, archiveName);
      long cnt = 0L;

      try {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Perform age based retirement on " + archiveName + " timeLimit=" + this.timeLimit);
         }

         cnt = this.archive.retireDataRecords(this.timeLimit, this);
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Deleted " + cnt + " records from " + archiveName);
         }
      } catch (Exception var9) {
         this.setError(var9);
         DiagnosticsLogger.logAgeBasedDataRetirementError(archiveName, var9);
      }

      long t1 = System.currentTimeMillis();
      DiagnosticsLogger.logDataRetirementOperationEnd(desc, archiveName, cnt, t1 - t0);
      this.setEndTime(t1);
   }
}
