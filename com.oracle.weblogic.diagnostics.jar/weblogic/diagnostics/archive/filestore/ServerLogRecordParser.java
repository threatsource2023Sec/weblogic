package weblogic.diagnostics.archive.filestore;

import com.bea.logging.LogMessageFormatter;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.type.UnexpectedExceptionHandler;

public class ServerLogRecordParser implements RecordParser {
   public DataRecord parseRecord(byte[] buf, int offset, int len) {
      DataRecord retVal = null;
      InputStreamReader in = null;

      try {
         in = new InputStreamReader(new ByteArrayInputStream(buf, offset, len));
         LogLexer lexer = new LogLexer(in);
         ServerLogFileParser parser = new ServerLogFileParser(lexer);
         parser.setLogLexer(lexer);
         parser.setCompatibilityMode(LogMessageFormatter.isLogFormatCompatibilityEnabled());
         retVal = parser.getNextServerLogEntry();
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
            retVal = Long.parseLong(dataRecord.get(10).toString());
         } catch (Exception var5) {
         }

         return retVal;
      }
   }

   static Map parseSupplementalAttributes(String suppAttrs) {
      Map props = new HashMap();
      Pattern p = Pattern.compile("\\[.*?\\]");
      Matcher m = p.matcher(suppAttrs);

      while(m.find()) {
         String s = m.group();
         s = s.substring(1, s.length() - 1);
         s = s.trim();
         int index = s.indexOf(58);
         if (index > 0) {
            String key = s.substring(0, index);
            key = key.trim();
            String value = s.substring(index + 1);
            value = value.trim();
            if (key != null && !key.isEmpty()) {
               props.put(key, value);
            }
         }
      }

      return props;
   }
}
