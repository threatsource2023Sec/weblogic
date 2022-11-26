package weblogic.jms.dotnet.proxy;

import weblogic.server.ServiceFailureException;

public interface ProxyManager {
   void resume() throws ServiceFailureException;

   void shutdown(boolean var1) throws ServiceFailureException;
}
