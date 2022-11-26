package weblogic.diagnostics.accessor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class JSONDataWriter extends AbstractDiagnosticDataWriter {
   private static final int INDENT_FACTOR = 2;

   JSONDataWriter(OutputStream out) {
      this(out, 0L);
   }

   JSONDataWriter(OutputStream out, long maxItems) {
      super(out, maxItems);
   }

   public void writeDiagnosticData(ColumnInfo[] cols, Iterator iter) throws IOException {
      try {
         this.writer.append("{");
         this.writer.newLine();
         this.writer.append("\"records\": [");
         this.writer.newLine();
         int count = 0;

         label95:
         while(true) {
            DataRecord dataRecord;
            if (iter.hasNext()) {
               dataRecord = (DataRecord)iter.next();
               Object[] rawValues = dataRecord.getValues();
               JSONObject jsonObj = new JSONObject();
               int i = 0;

               while(true) {
                  if (i >= cols.length) {
                     this.writer.append(jsonObj.toString(2));
                     ++count;
                     if (this.maxItems <= 0L || (long)count < this.maxItems) {
                        if (iter.hasNext()) {
                           this.writer.append(",");
                        }

                        this.writer.newLine();
                        continue label95;
                     }
                     break;
                  }

                  ColumnInfo ci = cols[i];
                  jsonObj.put(ci.getColumnName(), rawValues[i]);
                  ++i;
               }
            }

            this.writer.append("]");
            if (iter.hasNext()) {
               dataRecord = (DataRecord)iter.next();
               Object nextRecordId = dataRecord.get(0);
               this.writer.append(",");
               this.writer.newLine();
               this.writer.append("\"nextRecordId\": " + nextRecordId);
            }

            this.writer.newLine();
            this.writer.append("}");
            this.writer.flush();
            return;
         }
      } catch (JSONException var12) {
         throw new IOException(var12);
      } finally {
         this.writer.flush();
      }
   }
}
