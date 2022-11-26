package javax.resource.spi;

import javax.resource.ResourceException;

public interface DissociatableManagedConnection {
   void dissociateConnections() throws ResourceException;
}
