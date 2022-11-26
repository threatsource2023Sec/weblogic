package weblogic.iiop.interceptors;

import javax.validation.constraints.NotNull;
import weblogic.iiop.EndPoint;
import weblogic.iiop.contexts.SendingContextRunTime;
import weblogic.iiop.contexts.ServiceContextList;
import weblogic.iiop.protocol.CorbaInputStream;

public class SendingContextRuntimeInterceptor implements ServerContextInterceptor {
   public void handleReceivedRequest(@NotNull ServiceContextList serviceContexts, @NotNull EndPoint endPoint, CorbaInputStream inputStream) {
      SendingContextRunTime scrt = (SendingContextRunTime)serviceContexts.getServiceContext(6);
      if (scrt != null) {
         endPoint.setRemoteCodeBaseIOR(scrt.getCodeBase());
      }

   }
}
