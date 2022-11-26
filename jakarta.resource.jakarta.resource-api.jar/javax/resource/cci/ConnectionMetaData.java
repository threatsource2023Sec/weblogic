package javax.resource.cci;

import javax.resource.ResourceException;

public interface ConnectionMetaData {
   String getEISProductName() throws ResourceException;

   String getEISProductVersion() throws ResourceException;

   String getUserName() throws ResourceException;
}
