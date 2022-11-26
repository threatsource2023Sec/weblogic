package weblogic.connector.outbound;

import java.security.AccessController;
import javax.resource.ResourceException;
import javax.resource.spi.ManagedConnection;
import weblogic.connector.common.RAInstanceManager;
import weblogic.connector.security.outbound.SecurityContext;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class NoTxConnectionHandler extends ConnectionHandlerBaseImpl {
   NoTxConnectionHandler(ManagedConnection mc, ConnectionPool connPool, SecurityContext secCtx, ConnectionInfo connectionInfo) {
      super(mc, connPool, secCtx, connectionInfo, "NoTransaction");
      this.addConnectionRuntimeMBean();
   }

   public void enListResource() throws ResourceException {
   }

   protected void initializeConnectionEventListener() {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      RAInstanceManager raInstanceManager = this.connPool.getRAInstanceManager();
      raInstanceManager.getAdapterLayer().addConnectionEventListener(this.managedConnection, NoTxConnectionEventListener.create(this, "NoTransConnEventListener", raInstanceManager.getPartitionName()), kernelId);
   }
}
