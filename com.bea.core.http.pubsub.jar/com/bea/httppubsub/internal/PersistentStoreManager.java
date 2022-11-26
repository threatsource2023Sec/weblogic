package com.bea.httppubsub.internal;

import weblogic.store.PersistentStore;
import weblogic.store.PersistentStoreException;

public interface PersistentStoreManager {
   PersistentStore getStore(String var1) throws PersistentStoreException;

   PersistentStore getDefaultStore() throws PersistentStoreException;
}
