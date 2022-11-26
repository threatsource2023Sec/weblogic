package weblogic.store;

public interface PersistentStoreRecord {
   PersistentHandle getHandle();

   Object getData() throws PersistentStoreException;
}
