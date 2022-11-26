package weblogic.iiop.interceptors;

import weblogic.iiop.EndPoint;
import weblogic.iiop.contexts.CodeSetServiceContext;
import weblogic.iiop.contexts.ServiceContextList;
import weblogic.iiop.protocol.CorbaInputStream;

public class CodeSetContextInterceptor implements ServerContextInterceptor {
   public void handleReceivedRequest(ServiceContextList serviceContexts, EndPoint endPoint, CorbaInputStream inputStream) {
      if (!endPoint.getFlag(16)) {
         CodeSetServiceContext context = (CodeSetServiceContext)serviceContexts.getServiceContext(1);
         if (context != null) {
            endPoint.setCodeSets(context.getCharCodeSet(), context.getWcharCodeSet());
            endPoint.setFlag(32);
            if (inputStream != null) {
               inputStream.setCodeSets(context.getCharCodeSet(), context.getWcharCodeSet());
            }
         }

      }
   }
}
