package weblogic.jms.bridge;

import javax.resource.ResourceException;

public interface AdapterConnectionMetaData {
   String getProductName() throws ResourceException;

   String getProductVersion() throws ResourceException;

   String getUserName() throws ResourceException;

   boolean implementsMDBTransaction() throws ResourceException;

   boolean isXAConnection() throws ResourceException;
}
