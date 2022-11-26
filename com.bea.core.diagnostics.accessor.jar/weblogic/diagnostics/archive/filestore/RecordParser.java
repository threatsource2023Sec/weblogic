package weblogic.diagnostics.archive.filestore;

import weblogic.diagnostics.accessor.DataRecord;

public interface RecordParser {
   DataRecord parseRecord(byte[] var1, int var2, int var3);

   long getTimestamp(DataRecord var1);
}
