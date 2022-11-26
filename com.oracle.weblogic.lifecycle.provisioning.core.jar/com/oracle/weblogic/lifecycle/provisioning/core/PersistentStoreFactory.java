package com.oracle.weblogic.lifecycle.provisioning.core;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import org.glassfish.hk2.api.Factory;
import org.jvnet.hk2.annotations.Optional;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.ServerService;
import weblogic.store.PersistentStore;
import weblogic.store.PersistentStoreManager;

@Service
@Singleton
public class PersistentStoreFactory implements Factory {
   public PersistentStoreFactory() {
      this((ServerService)null);
   }

   @Inject
   public PersistentStoreFactory(@Optional @Named("DefaultStoreService") ServerService defaultStoreService) {
   }

   @Singleton
   public PersistentStore provide() {
      PersistentStoreManager persistentStoreManager = PersistentStoreManager.getManager();

      assert persistentStoreManager != null : "PersistentStoreManager.getManager() == null";

      PersistentStore returnValue = persistentStoreManager.getDefaultStore();

      assert returnValue != null : "persistentStoreManager.getDefaultStore() == null";

      return returnValue;
   }

   public void dispose(PersistentStore persistentStore) {
   }
}
