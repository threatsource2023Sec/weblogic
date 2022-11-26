package javax.resource.spi;

import javax.resource.ResourceException;

public interface ManagedConnectionMetaData {
   String getEISProductName() throws ResourceException;

   String getEISProductVersion() throws ResourceException;

   int getMaxConnections() throws ResourceException;

   String getUserName() throws ResourceException;
}
