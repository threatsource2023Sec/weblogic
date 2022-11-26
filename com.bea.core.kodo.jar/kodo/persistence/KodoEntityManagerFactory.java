package kodo.persistence;

import java.util.Map;
import kodo.remote.RemoteTransferListener;
import org.apache.openjpa.persistence.OpenJPAEntityManagerFactorySPI;

/** @deprecated */
public interface KodoEntityManagerFactory extends OpenJPAEntityManagerFactorySPI {
   StoreCache getStoreCache();

   StoreCache getStoreCache(String var1);

   QueryResultCache getQueryResultCache();

   KodoEntityManager createEntityManager();

   KodoEntityManager createEntityManager(Map var1);

   boolean startPersistenceServer();

   boolean joinPersistenceServer();

   boolean stopPersistenceServer();

   boolean isPersistenceServerRunning();

   void addTransferListener(RemoteTransferListener var1);

   void removeTransferListener(RemoteTransferListener var1);
}
