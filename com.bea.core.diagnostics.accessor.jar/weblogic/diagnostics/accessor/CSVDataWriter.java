package weblogic.diagnostics.accessor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

public class CSVDataWriter extends AbstractDiagnosticDataWriter {
   private static final String CSV_DELIMITER = ",";

   CSVDataWriter(OutputStream out) {
      super(out);
   }

   public void writeDiagnosticData(ColumnInfo[] cols, Iterator iter) throws IOException {
      StringBuilder header = new StringBuilder();
      int noOfCols = cols.length;

      for(int i = 0; i < noOfCols; ++i) {
         ColumnInfo col = cols[i];
         header.append(col.getColumnName());
         if (i < noOfCols - 1) {
            header.append(",");
         }
      }

      this.writer.write(header.toString());
      this.writer.newLine();
      if (iter != null) {
         try {
            while(iter.hasNext()) {
               DataRecord dataRecord = (DataRecord)iter.next();
               StringBuilder record = new StringBuilder();
               Object[] values = dataRecord.getValues();
               int size = values != null ? values.length : 0;

               for(int i = 0; i < size; ++i) {
                  Object value = values[i];
                  String strVal = value != null ? value.toString() : "";
                  record.append("\"");
                  record.append(strVal);
                  record.append("\"");
                  if (i < noOfCols - 1) {
                     record.append(",");
                  }
               }

               this.writer.write(record.toString());
               this.writer.newLine();
               this.writer.flush();
            }
         } finally {
            this.writer.flush();
         }

      }
   }
}
