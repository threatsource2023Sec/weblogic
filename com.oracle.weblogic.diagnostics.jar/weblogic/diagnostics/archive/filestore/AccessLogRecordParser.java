package weblogic.diagnostics.archive.filestore;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.type.UnexpectedExceptionHandler;

final class AccessLogRecordParser implements RecordParser {
   private int timestampColumnIndex;
   private String dateFormatString;

   AccessLogRecordParser() {
   }

   AccessLogRecordParser(String dateFormatString, int timestampColumnIndex) {
      this.timestampColumnIndex = timestampColumnIndex;
      this.dateFormatString = dateFormatString;
   }

   public DataRecord parseRecord(byte[] buf, int offset, int len) {
      DataRecord retVal = null;
      InputStreamReader in = null;

      try {
         in = new InputStreamReader(new ByteArrayInputStream(buf, offset, len));
         AccessLogLexer lexer = new AccessLogLexer(in);
         AccessLogFileParser parser = new AccessLogFileParser(lexer);
         retVal = parser.getNextAccessLogEntry();
      } catch (Exception var16) {
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
      if (dataRecord != null && this.dateFormatString != null) {
         try {
            ParsePosition pos = new ParsePosition(0);
            SimpleDateFormat dateFormat = new SimpleDateFormat(this.dateFormatString);
            String val = dataRecord.get(this.timestampColumnIndex).toString();
            Date date = dateFormat.parse(val, pos);
            if (date != null) {
               retVal = date.getTime();
            }
         } catch (Throwable var8) {
         }

         return retVal;
      } else {
         return 0L;
      }
   }
}
