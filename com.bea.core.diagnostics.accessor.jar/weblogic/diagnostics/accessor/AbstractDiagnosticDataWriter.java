package weblogic.diagnostics.accessor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public abstract class AbstractDiagnosticDataWriter implements DiagnosticDataWriter {
   protected static final int DEFAULT_MAX_ITEMS = 0;
   protected static final int RECORDID_COL_IDX = 0;
   protected BufferedWriter writer;
   protected long maxItems = 0L;

   AbstractDiagnosticDataWriter(OutputStream out) {
      this.writer = new BufferedWriter(new OutputStreamWriter(out));
   }

   AbstractDiagnosticDataWriter(OutputStream out, long maxItems) {
      this.writer = new BufferedWriter(new OutputStreamWriter(out));
      this.maxItems = maxItems;
   }

   AbstractDiagnosticDataWriter(BufferedWriter writer) {
      this.writer = writer;
   }

   public void close() throws IOException {
      this.writer.flush();
      this.writer.close();
   }
}
