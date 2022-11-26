package javax.resource.spi;

import java.io.Serializable;
import javax.resource.ResourceException;

public interface ConnectionManager extends Serializable {
   Object allocateConnection(ManagedConnectionFactory var1, ConnectionRequestInfo var2) throws ResourceException;
}
