package javax.resource.cci;

import javax.resource.ResourceException;

public interface LocalTransaction {
   void begin() throws ResourceException;

   void commit() throws ResourceException;

   void rollback() throws ResourceException;
}
