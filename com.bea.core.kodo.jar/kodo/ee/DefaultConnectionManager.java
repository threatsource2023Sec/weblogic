package kodo.ee;

import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.security.auth.Subject;

public class DefaultConnectionManager implements ConnectionManager {
   public Object allocateConnection(ManagedConnectionFactory mcf, ConnectionRequestInfo cxRequestInfo) throws ResourceException {
      ManagedConnection mc = mcf.createManagedConnection((Subject)null, cxRequestInfo);
      return mc.getConnection((Subject)null, cxRequestInfo);
   }
}
