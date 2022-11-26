package weblogic.store;

import weblogic.common.CompletionRequest;

public interface PersistentMapAsyncTX extends PersistentMap {
   void put(Object var1, Object var2, CompletionRequest var3);

   boolean put(Object var1, Object var2, PersistentStoreTransaction var3) throws PersistentStoreException;

   void put(Object var1, Object var2, PersistentStoreTransaction var3, CompletionRequest var4);

   void get(Object var1, CompletionRequest var2);

   Object get(Object var1, PersistentStoreTransaction var2) throws PersistentStoreException;

   void get(Object var1, PersistentStoreTransaction var2, CompletionRequest var3);

   Object getForUpdate(Object var1, PersistentStoreTransaction var2) throws PersistentStoreException;

   void getForUpdate(Object var1, PersistentStoreTransaction var2, CompletionRequest var3);

   void remove(Object var1, CompletionRequest var2);

   void remove(Object var1, Object var2, CompletionRequest var3);

   boolean remove(Object var1, PersistentStoreTransaction var2) throws PersistentStoreException;

   void remove(Object var1, PersistentStoreTransaction var2, CompletionRequest var3);

   void putIfAbsent(Object var1, Object var2, CompletionRequest var3);

   void putIfAbsent(Object var1, Object var2, PersistentStoreTransaction var3, CompletionRequest var4);

   PersistentStoreTransaction begin();
}
