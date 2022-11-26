package com.bea.common.store.service;

import com.bea.common.store.service.config.StoreServicePropertiesConfigurator;
import java.util.Properties;
import javax.jdo.PersistenceManagerFactory;

public interface RemoteCommitProvider {
   void initialize(Properties var1, Properties var2, StoreServicePropertiesConfigurator var3);

   void addRemoteCommitListener(PersistenceManagerFactory var1, Class var2, RemoteCommitListener var3);

   void shutdown();
}
