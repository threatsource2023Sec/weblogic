package weblogic.jms.adapter;

import java.io.Serializable;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.security.auth.Subject;

public class JMSConnectionManager implements ConnectionManager, Serializable {
   static final long serialVersionUID = -451611469432230705L;

   public Object allocateConnection(ManagedConnectionFactory mcf, ConnectionRequestInfo info) throws ResourceException {
      ManagedConnection mc = mcf.createManagedConnection((Subject)null, info);
      return mc.getConnection((Subject)null, info);
   }
}
