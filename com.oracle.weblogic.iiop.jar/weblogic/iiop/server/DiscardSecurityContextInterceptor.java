package weblogic.iiop.server;

import weblogic.iiop.EndPoint;
import weblogic.iiop.contexts.DiscardSecurityContext;
import weblogic.iiop.contexts.ServiceContextList;
import weblogic.iiop.interceptors.ServerContextInterceptor;
import weblogic.iiop.protocol.CorbaInputStream;

class DiscardSecurityContextInterceptor implements ServerContextInterceptor {
   public void handleReceivedRequest(ServiceContextList contextList, EndPoint endPoint, CorbaInputStream inputStream) {
      DiscardSecurityContext sc = (DiscardSecurityContext)contextList.getServiceContext(1111834893);
      if (sc != null) {
         ((ServerEndPoint)endPoint).removeSecurityContext(sc.getSecurityContextId());
      }

   }
}
