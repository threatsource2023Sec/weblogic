package javax.resource.cci;

import javax.resource.ResourceException;

public interface Interaction {
   void close() throws ResourceException;

   Connection getConnection();

   boolean execute(InteractionSpec var1, Record var2, Record var3) throws ResourceException;

   Record execute(InteractionSpec var1, Record var2) throws ResourceException;

   ResourceWarning getWarnings() throws ResourceException;

   void clearWarnings() throws ResourceException;
}
