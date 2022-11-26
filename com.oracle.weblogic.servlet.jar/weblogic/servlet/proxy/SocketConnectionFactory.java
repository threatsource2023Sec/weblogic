package weblogic.servlet.proxy;

import weblogic.common.ResourceException;
import weblogic.common.resourcepool.PooledResource;
import weblogic.common.resourcepool.PooledResourceFactory;
import weblogic.common.resourcepool.PooledResourceInfo;

public class SocketConnectionFactory implements PooledResourceFactory {
   private final String host;
   private final int port;

   public SocketConnectionFactory(String host, int port) {
      this.host = host;
      this.port = port;
   }

   public PooledResource createResource(PooledResourceInfo info) throws ResourceException {
      return new SocketConnResource(this.host, this.port);
   }

   public void refreshResource(PooledResource res) throws ResourceException {
      SocketConnResource conn = (SocketConnResource)res;
      conn.destroy();
      conn.initialize();
   }
}
