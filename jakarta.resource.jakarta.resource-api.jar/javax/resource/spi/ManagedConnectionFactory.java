package javax.resource.spi;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Set;
import javax.resource.ResourceException;
import javax.security.auth.Subject;

public interface ManagedConnectionFactory extends Serializable {
   Object createConnectionFactory(ConnectionManager var1) throws ResourceException;

   Object createConnectionFactory() throws ResourceException;

   ManagedConnection createManagedConnection(Subject var1, ConnectionRequestInfo var2) throws ResourceException;

   ManagedConnection matchManagedConnections(Set var1, Subject var2, ConnectionRequestInfo var3) throws ResourceException;

   void setLogWriter(PrintWriter var1) throws ResourceException;

   PrintWriter getLogWriter() throws ResourceException;

   int hashCode();

   boolean equals(Object var1);
}
