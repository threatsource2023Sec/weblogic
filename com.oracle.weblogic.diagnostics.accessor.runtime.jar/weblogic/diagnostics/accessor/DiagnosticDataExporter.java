package weblogic.diagnostics.accessor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsTextTextFormatter;
import weblogic.diagnostics.query.QueryException;
import weblogic.diagnostics.utils.DateUtils;
import weblogic.management.ManagementException;

public final class DiagnosticDataExporter implements AccessorConstants {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticAccessor");
   private static final String SEP = "/";

   public static void exportDiagnosticData(String logicalName, String logName, String logRotationDir, String storeDir, String query, String exportFileName, String elfFields, long beginTimestamp, long endTimestamp, String format, String last) throws DiagnosticDataAccessException {
      String[] tokens = logicalName.split("/");
      String logType = tokens[0];
      debugLogger.debug("LogType = " + logType);
      debugLogger.debug("LogName = " + logName);
      HashMap map = new HashMap();
      if (logType.equals("ServerLog") || logType.equals("DomainLog") || logType.equals("HTTPAccessLog") || logType.equals("WebAppLog") || logType.equals("ConnectorLog") || logType.equals("JMSMessageLog") || logType.equals("JMSSAFMessageLog")) {
         map.put("logFilePath", logName);
         map.put("logRotationDir", logRotationDir);
      }

      map.put("storeDir", storeDir);
      if (elfFields != null && elfFields.length() > 0) {
         map.put("elfFields", elfFields);
      }

      DiagnosticDataAccessService ddas = null;

      try {
         ddas = DiagnosticDataAccessServiceFactory.createDiagnosticDataAccessService(logicalName, logType, map);
      } catch (UnknownLogTypeException var50) {
         throw new DiagnosticDataAccessException(var50);
      } catch (DataAccessServiceCreateException var51) {
         throw new DiagnosticDataAccessException(var51);
      }

      if (last != null && !last.isEmpty()) {
         long[] range = DateUtils.getTimestampRange(last);
         beginTimestamp = range[0];
         endTimestamp = range[1];
      }

      DebugLogger.println(DiagnosticsTextTextFormatter.getInstance().getQueryTimestampRangeMsg((new Date(beginTimestamp)).toString(), (new Date(endTimestamp)).toString()));

      try {
         ColumnInfo[] cols = ddas.getColumns();
         Iterator iter = null;

         try {
            iter = ddas.getDataRecords(beginTimestamp, endTimestamp, query);
         } catch (QueryException var49) {
            throw new DiagnosticDataAccessException(var49);
         }

         FileOutputStream fos = null;

         try {
            if (!exportFileName.endsWith(format)) {
               exportFileName = exportFileName + "." + format;
            }

            File f = new File(exportFileName);
            fos = new FileOutputStream(f.getCanonicalFile());
            DebugLogger.println(DiagnosticsTextTextFormatter.getInstance().getExportingDiagnosticDataMsgText(exportFileName));
         } catch (IOException var54) {
            throw new DiagnosticDataAccessException(var54);
         }

         DiagnosticDataWriter writer = null;

         try {
            if (format.equals("txt")) {
               writer = new TextDataWriter(fos);
            } else if (format.equals("csv")) {
               writer = new CSVDataWriter(fos);
            } else {
               writer = new XMLDataWriter(fos, true);
            }

            ((DiagnosticDataWriter)writer).writeDiagnosticData(cols, iter);
         } catch (Exception var48) {
            throw new DiagnosticDataAccessException(var48);
         } finally {
            if (writer != null) {
               try {
                  ((DiagnosticDataWriter)writer).close();
               } catch (IOException var47) {
                  throw new DiagnosticDataAccessException(var47);
               }
            }

         }
      } finally {
         try {
            if (ddas != null) {
               ddas.close();
            }
         } catch (ManagementException var52) {
            throw new DiagnosticDataAccessException(var52);
         }

      }

   }
}
