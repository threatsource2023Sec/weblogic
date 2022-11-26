package weblogic.workarea.spi;

import java.io.IOException;
import weblogic.workarea.WorkContext;
import weblogic.workarea.WorkContextOutput;

public interface WorkContextEntry {
   WorkContextEntry NULL_CONTEXT = new WorkContextEntryImpl((String)null, (WorkContext)null, 1);

   WorkContext getWorkContext();

   String getName();

   int getPropagationMode();

   boolean isOriginator();

   void write(WorkContextOutput var1) throws IOException;
}
