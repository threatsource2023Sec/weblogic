package weblogic.store.xa;

import weblogic.store.ObjectHandler;
import weblogic.store.PersistentMap;
import weblogic.store.PersistentStore;
import weblogic.store.PersistentStoreException;
import weblogic.store.gxa.GXAResource;

public interface PersistentStoreXA extends PersistentStore {
   PersistentMap createPersistentMapXA(String var1) throws PersistentStoreException;

   PersistentMap createPersistentMapXA(String var1, ObjectHandler var2) throws PersistentStoreException;

   GXAResource getGXAResource() throws PersistentStoreException;
}
