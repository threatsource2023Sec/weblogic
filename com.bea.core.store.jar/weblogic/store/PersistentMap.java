package weblogic.store;

import java.util.Set;

public interface PersistentMap {
   boolean put(Object var1, Object var2) throws PersistentStoreException;

   Object get(Object var1) throws PersistentStoreException;

   boolean remove(Object var1) throws PersistentStoreException;

   Set keySet() throws PersistentStoreException;

   int size() throws PersistentStoreException;

   boolean isEmpty() throws PersistentStoreException;

   boolean containsKey(Object var1) throws PersistentStoreException;

   void delete() throws PersistentStoreException;
}
