package javax.resource.spi;

import javax.resource.ResourceException;

public interface LazyAssociatableConnectionManager {
   void associateConnection(Object var1, ManagedConnectionFactory var2, ConnectionRequestInfo var3) throws ResourceException;

   void inactiveConnectionClosed(Object var1, ManagedConnectionFactory var2);
}
