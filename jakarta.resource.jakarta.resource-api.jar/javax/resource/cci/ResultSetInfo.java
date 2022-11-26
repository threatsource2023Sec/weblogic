package javax.resource.cci;

import javax.resource.ResourceException;

public interface ResultSetInfo {
   boolean updatesAreDetected(int var1) throws ResourceException;

   boolean insertsAreDetected(int var1) throws ResourceException;

   boolean deletesAreDetected(int var1) throws ResourceException;

   boolean supportsResultSetType(int var1) throws ResourceException;

   boolean supportsResultTypeConcurrency(int var1, int var2) throws ResourceException;

   boolean othersUpdatesAreVisible(int var1) throws ResourceException;

   boolean othersDeletesAreVisible(int var1) throws ResourceException;

   boolean othersInsertsAreVisible(int var1) throws ResourceException;

   boolean ownUpdatesAreVisible(int var1) throws ResourceException;

   boolean ownInsertsAreVisible(int var1) throws ResourceException;

   boolean ownDeletesAreVisible(int var1) throws ResourceException;
}
