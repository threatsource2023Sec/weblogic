package com.bea.httppubsub.internal;

import weblogic.store.PersistentStore;
import weblogic.store.PersistentStoreException;

public class DefaultPersistentStoreManager implements PersistentStoreManager {
   private static final PersistentStoreManager instance;
   private weblogic.store.PersistentStoreManager delegate = weblogic.store.PersistentStoreManager.getManager();

   public static PersistentStoreManager getInstance() {
      return instance;
   }

   private DefaultPersistentStoreManager() {
   }

   public PersistentStore getStore(String name) throws PersistentStoreException {
      if (name == null) {
         return null;
      } else {
         PersistentStore store = this.delegate.getStoreByLogicalName(name);
         if (store == null) {
            store = this.delegate.getStore(name);
         }

         return store;
      }
   }

   public PersistentStore getDefaultStore() throws PersistentStoreException {
      return this.delegate.getDefaultStore();
   }

   static {
      String managerClassName = System.getProperty("com.bea.httppubsub.internal.PersistentStoreManager.impl");
      if (managerClassName == null) {
         instance = new DefaultPersistentStoreManager();
      } else {
         try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Class managerClass;
            if (loader != null) {
               managerClass = loader.loadClass(managerClassName);
            } else {
               managerClass = Class.forName(managerClassName);
            }

            instance = (PersistentStoreManager)managerClass.newInstance();
         } catch (Exception var3) {
            throw new Error("Failed to initialize Pub/Sub Server PersistentStoreManager.", var3);
         }
      }

   }
}
