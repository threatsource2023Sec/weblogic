package weblogic.iiop.interceptors;

import javax.validation.constraints.NotNull;
import weblogic.iiop.EndPoint;
import weblogic.iiop.contexts.ServiceContextList;
import weblogic.iiop.protocol.CorbaInputStream;

public interface ServerContextInterceptor {
   void handleReceivedRequest(@NotNull ServiceContextList var1, @NotNull EndPoint var2, CorbaInputStream var3);
}
