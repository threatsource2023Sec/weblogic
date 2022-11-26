package weblogic.store;

import weblogic.store.internal.PersistentStoreImpl;

public interface RuntimeHandler {
   RuntimeUpdater createStoreMBean(PersistentStoreImpl var1) throws PersistentStoreException;

   void registerConnectionMBean(RuntimeUpdater var1, PersistentStoreConnection var2) throws PersistentStoreException;

   void unregisterConnectionMBean(RuntimeUpdater var1, PersistentStoreConnection var2) throws PersistentStoreException;

   void unregisterStoreMBean(RuntimeUpdater var1) throws PersistentStoreException;

   long getJTAAbandonTimeoutMillis();

   String getDomainName();
}
