package weblogic.workarea.spi;

import java.io.IOException;
import weblogic.workarea.WorkContextInput;
import weblogic.workarea.WorkContextOutput;

public interface WorkContextMapInterceptor {
   void sendRequest(WorkContextOutput var1, int var2) throws IOException;

   void sendResponse(WorkContextOutput var1, int var2) throws IOException;

   void receiveRequest(WorkContextInput var1) throws IOException;

   void receiveResponse(WorkContextInput var1) throws IOException;

   WorkContextMapInterceptor copyThreadContexts(int var1);

   void restoreThreadContexts(WorkContextMapInterceptor var1);

   WorkContextMapInterceptor suspendThreadContexts();

   void resumeThreadContexts(WorkContextMapInterceptor var1);

   int version();
}
