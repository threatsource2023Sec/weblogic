package weblogic.diagnostics.accessor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

public class TextDataWriter extends AbstractDiagnosticDataWriter {
   TextDataWriter(OutputStream out) {
      this(out, 0L);
   }

   TextDataWriter(OutputStream out, long maxItems) {
      super(out, maxItems);
   }

   public void writeDiagnosticData(ColumnInfo[] cols, Iterator iter) throws IOException {
      int count = 0;

      try {
         while(iter.hasNext()) {
            DataRecord dataRecord = (DataRecord)iter.next();
            String text = dataRecord.geRawData();
            if (text == null || text.isEmpty()) {
               text = dataRecord.toString();
            }

            this.writer.append(text);
            this.writer.flush();
            ++count;
            if (this.maxItems > 0L && (long)count >= this.maxItems && iter.hasNext()) {
               dataRecord = (DataRecord)iter.next();
               this.writer.append("nextRecordId=" + dataRecord.get(0));
               break;
            }
         }
      } finally {
         this.writer.flush();
      }

   }
}
