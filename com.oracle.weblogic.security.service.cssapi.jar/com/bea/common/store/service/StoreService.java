package com.bea.common.store.service;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public interface StoreService {
   String SERVICE_NAME = "com.bea.common.store.service.StoreService";

   PersistenceManager getPersistenceManager() throws StoreInitializationException, StoreNotFoundException;

   PersistenceManagerFactory getPersistenceManagerFactory();

   void addRemoteCommitListener(Class var1, RemoteCommitListener var2);

   String getStoreId();

   boolean isMySQLUsed();
}
