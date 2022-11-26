package weblogic.store.gxa;

import java.util.List;
import weblogic.store.xa.PersistentStoreXA;

public interface GXAResource {
   PersistentStoreXA getPersistentStore();

   boolean isTracingEnabled();

   String getName();

   GXATransaction enlist() throws GXAException;

   GXATransaction enlist(boolean var1) throws GXAException;

   GXALocalTransaction beginLocalTransaction() throws GXAException;

   void addNewOperation(GXATransaction var1, GXAOperation var2) throws GXAException;

   void addRecoveredOperation(GXAOperation var1);

   void addRecoveredOperations(List var1) throws ClassCastException;
}
