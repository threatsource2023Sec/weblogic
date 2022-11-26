package weblogic.store;

import weblogic.common.CompletionRequest;

public interface PersistentStoreConnection {
   int NO_FLAGS = 0;
   int HINT_LAZY_WRITE = 1;
   int HINT_DEFERRED_WRITE = 2;
   int HINT_LONG_LIVED = 4;
   int DYNAMIC_CURSOR = 32;
   int STATIC_CURSOR = 64;

   PersistentHandle create(PersistentStoreTransaction var1, Object var2, int var3);

   PersistentHandle create(PersistentStoreTransaction var1, Object var2, int var3, long var4, long var6);

   void read(PersistentStoreTransaction var1, PersistentHandle var2, CompletionRequest var3);

   void read(PersistentStoreTransaction var1, PersistentHandle var2, CompletionRequest var3, boolean var4);

   boolean isHandleReadable(PersistentHandle var1);

   void update(PersistentStoreTransaction var1, PersistentHandle var2, Object var3, int var4);

   void update(PersistentStoreTransaction var1, PersistentHandle var2, Object var3, int var4, long var5, long var7);

   void delete(PersistentStoreTransaction var1, PersistentHandle var2, int var3);

   void delete(PersistentStoreTransaction var1, PersistentHandle var2, int var3, long var4, long var6);

   void close();

   void delete() throws PersistentStoreException;

   String getName();

   PersistentStore getStore();

   OperationStatistics getStatistics();

   Cursor createCursor(int var1) throws PersistentStoreException;

   public interface Cursor {
      PersistentStoreRecord next() throws PersistentStoreException;
   }
}
