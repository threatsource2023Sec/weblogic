package weblogic.jms.forwarder.dd;

import weblogic.store.PersistentStoreException;
import weblogic.store.xa.PersistentStoreXA;

public interface DDForwardStore {
   PersistentStoreXA getStore();

   DDLBTable getDDLBTable();

   void addOrUpdateDDLBTable(DDLBTable var1) throws PersistentStoreException;

   void removeDDLBTable() throws PersistentStoreException;

   void close();

   void poisoned();

   boolean isPoisoned();
}
