package weblogic.diagnostics.archive.filestore;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.type.UnexpectedExceptionHandler;

public class DataSourceLogRecordParser implements RecordParser {
   private static final String DATE_FORMAT = System.getProperty("weblogic.diagnostics.archive.filestore.DataSourceLogRecordParser.dateformat", "EEE MMM d HH:mm:ss z yyyy");

   public DataRecord parseRecord(byte[] buf, int offset, int len) {
      DataRecord retVal = null;
      InputStreamReader in = null;

      try {
         in = new InputStreamReader(new ByteArrayInputStream(buf, offset, len));
         LogLexer lexer = new LogLexer(in);
         DataSourceLogFileParser parser = new DataSourceLogFileParser(lexer);
         retVal = parser.getNextLogEntry();
      } catch (Exception var16) {
         DiagnosticsLogger.logLogRecordParseError(new String(buf, offset, len), var16);
      } finally {
         if (in != null) {
            try {
               in.close();
            } catch (Exception var15) {
               UnexpectedExceptionHandler.handle("Could not close stream", var15);
            }
         }

      }

      return retVal;
   }

   public long getTimestamp(DataRecord dataRecord) {
      long retVal = 0L;
      if (dataRecord == null) {
         return 0L;
      } else {
         try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            Date date = dateFormat.parse(dataRecord.get(3).toString(), new ParsePosition(0));
            retVal = date.getTime();
         } catch (Exception var6) {
         }

         return retVal;
      }
   }
}
