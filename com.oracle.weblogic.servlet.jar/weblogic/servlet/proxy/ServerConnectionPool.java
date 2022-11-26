package weblogic.servlet.proxy;

import java.util.Properties;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.PooledResourceFactory;
import weblogic.common.resourcepool.ResourcePoolImpl;

public class ServerConnectionPool extends ResourcePoolImpl {
   private final SocketConnectionFactory factory;

   ServerConnectionPool(String host, int port) {
      this.factory = new SocketConnectionFactory(host, port);
   }

   public PooledResourceFactory initPooledResourceFactory(Properties initInfo) throws ResourceException {
      return this.factory;
   }
}
