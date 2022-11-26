package weblogic.connector.outbound;

import java.lang.ref.Reference;
import javax.resource.spi.ManagedConnection;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.ResourceCleanupHandler;
import weblogic.connector.security.outbound.SecurityContext;

public interface ConnectionHandler extends ResourceCleanupHandler {
   void closeConnection(Object var1) throws ResourceException;

   void connectionFinalized(Reference var1);

   void destroyConnection();

   int getNumActiveConns();

   int getActiveHandlesHighCount();

   int getHandlesCreatedTotalCount();

   boolean isInTransaction();

   String getPoolName();

   ManagedConnection getManagedConnection();

   ConnectionPool getPool();

   ConnectionInfo getConnectionInfo();

   Object createConnectionHandle(SecurityContext var1) throws javax.resource.ResourceException;

   void associateConnectionHandle(Object var1) throws javax.resource.ResourceException;

   void dissociateHandles();

   void cleanup() throws javax.resource.ResourceException;

   void destroy();

   boolean shouldBeDiscard();

   boolean isConnectionErrorOccurred();

   void setConnectionErrorOccurred(boolean var1);
}
