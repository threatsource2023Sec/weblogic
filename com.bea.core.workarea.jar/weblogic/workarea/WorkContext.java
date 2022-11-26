package weblogic.workarea;

import java.io.IOException;

public interface WorkContext {
   void writeContext(WorkContextOutput var1) throws IOException;

   void readContext(WorkContextInput var1) throws IOException;
}
