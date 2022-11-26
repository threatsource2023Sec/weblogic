package weblogic.diagnostics.archive.filestore;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.type.UnexpectedExceptionHandler;

public class JMSLogRecordParser implements RecordParser {
   public DataRecord parseRecord(byte[] buf, int offset, int len) {
      DataRecord retVal = null;
      InputStreamReader in = null;

      try {
         in = new InputStreamReader(new ByteArrayInputStream(buf, offset, len));
         LogLexer lexer = new LogLexer(in);
         JMSLogFileParser parser = new JMSLogFileParser(lexer);
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
            retVal = Long.parseLong(dataRecord.get(4).toString());
         } catch (Exception var5) {
         }

         return retVal;
      }
   }
}
