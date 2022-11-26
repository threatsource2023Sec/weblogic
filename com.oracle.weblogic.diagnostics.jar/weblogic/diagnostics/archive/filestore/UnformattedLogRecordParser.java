package weblogic.diagnostics.archive.filestore;

import weblogic.diagnostics.accessor.DataRecord;

public class UnformattedLogRecordParser implements RecordParser {
   public DataRecord parseRecord(byte[] buf, int offset, int len) {
      if (len <= 0) {
         return null;
      } else {
         byte last = buf[offset + len - 1];
         if (last != 13 && last != 10) {
            return null;
         } else {
            String line = (new String(buf, offset, len)).trim();
            if (line.length() == 0) {
               return null;
            } else {
               Object dummyRecordId = null;
               Object[] data = new Object[]{dummyRecordId, line};
               return new DataRecord(data);
            }
         }
      }
   }

   public long getTimestamp(DataRecord dataRecord) {
      return 0L;
   }
}
