package weblogic.diagnostics.archive;

import java.io.IOException;
import java.util.Collection;

public interface DataWriter {
   void writeData(Collection var1) throws IOException;
}
