package weblogic.workarea;

import java.io.DataOutput;
import java.io.IOException;

public interface WorkContextOutput extends DataOutput {
   void writeASCII(String var1) throws IOException;

   void writeContext(WorkContext var1) throws IOException;
}
