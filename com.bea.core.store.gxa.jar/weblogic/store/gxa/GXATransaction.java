package weblogic.store.gxa;

import weblogic.store.PersistentStoreTransaction;

public interface GXATransaction {
   int INITIALIZING = 0;
   int ACTIVE = 1;
   int PREPARING = 2;
   int PREPARED = 3;
   int ROLLBACK = 4;
   int ROLLBACKONLY = 5;
   int COMMIT = 6;

   PersistentStoreTransaction getStoreTransaction();

   GXAResource getGXAResource();

   int getStatus();

   String getStatusAsString();

   boolean isRecovered();

   GXid getGXid();

   long getMillisSinceBeginIffTimedOut();

   Object putProperty(String var1, String var2, Object var3);

   Object getProperty(String var1, String var2);

   Object removeProperty(String var1, String var2);

   GXAOperation[] getGXAOperations();
}
