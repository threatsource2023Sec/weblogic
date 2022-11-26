package weblogic.connector.external;

import java.util.Properties;
import javax.resource.ResourceException;

public interface SuspendableEndpointFactory {
   void suspend(Properties var1) throws ResourceException;

   void resume(Properties var1) throws ResourceException;

   boolean isSuspended();

   void disconnect() throws ResourceException;
}
