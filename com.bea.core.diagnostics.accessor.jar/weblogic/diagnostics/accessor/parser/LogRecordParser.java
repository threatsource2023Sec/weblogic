package weblogic.diagnostics.accessor.parser;

import weblogic.diagnostics.accessor.ColumnInfo;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.archive.filestore.RecordParser;

public interface LogRecordParser extends RecordParser {
   byte[] DEFAULT_RECORD_MARKER = "####".getBytes();

   byte[] getRecordMarker();

   int getTimestampColumnIndex();

   ColumnInfo[] getColumnInfos();

   DataRecord parseRecord(byte[] var1, int var2, int var3);

   long getTimestamp(DataRecord var1);
}
