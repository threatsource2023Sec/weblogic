package weblogic.diagnostics.accessor.parser;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import weblogic.diagnostics.accessor.ColumnInfo;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.type.UnexpectedExceptionHandler;

public class GenericRecordParser implements LogRecordParser {
   private ColumnInfo[] columnInfos;
   private int timestampColumnIndex;

   public GenericRecordParser(ColumnInfo[] columnInfos) {
      this(columnInfos, identifyTimestampColumn(columnInfos));
   }

   public GenericRecordParser(ColumnInfo[] columnInfos, int timestampColumnIndex) {
      this.columnInfos = columnInfos;
      this.timestampColumnIndex = timestampColumnIndex;
   }

   private static int identifyTimestampColumn(ColumnInfo[] columnInfos) {
      int size = columnInfos != null ? columnInfos.length : 0;

      for(int i = 0; i < size; ++i) {
         if ("TIMESTAMP".equals(columnInfos[i].getColumnName())) {
            return i;
         }
      }

      return -1;
   }

   public ColumnInfo[] getColumnInfos() {
      return this.columnInfos;
   }

   public byte[] getRecordMarker() {
      return DEFAULT_RECORD_MARKER;
   }

   public int getTimestampColumnIndex() {
      return this.timestampColumnIndex;
   }

   public DataRecord parseRecord(byte[] buf, int offset, int len) {
      DataRecord retVal = null;
      InputStreamReader in = null;

      try {
         in = new InputStreamReader(new ByteArrayInputStream(buf, offset, len));
         DefaultLogLexer lexer = new DefaultLogLexer(in);
         DefaultLogParser parser = new DefaultLogParser(lexer);
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
            Object tsObj = dataRecord.get(this.timestampColumnIndex);
            if (tsObj != null) {
               retVal = Long.parseLong(tsObj.toString());
            }
         } catch (Exception var5) {
         }

         return retVal;
      }
   }
}
