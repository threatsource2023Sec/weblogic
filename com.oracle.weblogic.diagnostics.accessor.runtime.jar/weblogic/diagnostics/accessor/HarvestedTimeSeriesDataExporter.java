package weblogic.diagnostics.accessor;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsTextTextFormatter;
import weblogic.diagnostics.utils.DateUtils;

public class HarvestedTimeSeriesDataExporter {
   private static AccessorMsgTextFormatter txtFmt = AccessorMsgTextFormatter.getInstance();

   public static void exportHarvestedTimeSeriesDataOffline(String moduleName, long beginTimestamp, long endTimestamp, String exportFilePath, String dateTimePattern, String storeDir, String last) throws Exception {
      if (moduleName != null && !moduleName.isEmpty()) {
         File exportFile = new File(exportFilePath);
         if (!exportFile.isDirectory() && !exportFile.isHidden()) {
            if (beginTimestamp < 0L) {
               beginTimestamp = 0L;
            }

            if (endTimestamp < 0L) {
               endTimestamp = Long.MAX_VALUE;
            }

            if (last != null && !last.isEmpty()) {
               long[] range = DateUtils.getTimestampRange(last);
               beginTimestamp = range[0];
               endTimestamp = range[1];
            }

            DebugLogger.println(DiagnosticsTextTextFormatter.getInstance().getQueryTimestampRangeMsg((new Date(beginTimestamp)).toString(), (new Date(endTimestamp)).toString()));
            String query = "WLDFMODULE='" + moduleName + "'";
            Map params = new HashMap();
            params.put("storeDir", storeDir);
            DiagnosticDataAccessService dataAccessService = DiagnosticDataAccessServiceFactory.createDiagnosticDataAccessService("HarvestedDataArchive", "HarvestedDataArchive", params);
            Iterator dataRecords = dataAccessService.getDataRecords(beginTimestamp, endTimestamp, query);
            HarvestedDataCSVWriter csvWriter = new HarvestedDataCSVWriter(exportFile, dateTimePattern);

            try {
               csvWriter.open();
               csvWriter.writeTimeSeriesData(dataRecords);
            } finally {
               csvWriter.close();
            }

            System.out.println();
            System.out.println(txtFmt.getDumpDiagnosticDataCaptureComplete());
         } else {
            throw new IllegalArgumentException(txtFmt.getMergeDiagnosticDataInvalidOutputFileName(exportFilePath));
         }
      } else {
         throw new IllegalArgumentException(txtFmt.getWLDFModuleNameEmptyMsgText());
      }
   }
}
