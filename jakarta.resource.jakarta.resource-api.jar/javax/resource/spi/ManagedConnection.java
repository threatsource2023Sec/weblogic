package javax.resource.spi;

import java.io.PrintWriter;
import javax.resource.ResourceException;
import javax.security.auth.Subject;
import javax.transaction.xa.XAResource;

public interface ManagedConnection {
   Object getConnection(Subject var1, ConnectionRequestInfo var2) throws ResourceException;

   void destroy() throws ResourceException;

   void cleanup() throws ResourceException;

   void associateConnection(Object var1) throws ResourceException;

   void addConnectionEventListener(ConnectionEventListener var1);

   void removeConnectionEventListener(ConnectionEventListener var1);

   XAResource getXAResource() throws ResourceException;

   LocalTransaction getLocalTransaction() throws ResourceException;

   ManagedConnectionMetaData getMetaData() throws ResourceException;

   void setLogWriter(PrintWriter var1) throws ResourceException;

   PrintWriter getLogWriter() throws ResourceException;
}
