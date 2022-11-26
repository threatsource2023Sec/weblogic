package weblogic.diagnostics.accessor;

import java.io.IOException;
import java.util.Iterator;

public interface DiagnosticDataWriter {
   void writeDiagnosticData(ColumnInfo[] var1, Iterator var2) throws IOException;

   void close() throws IOException;
}
