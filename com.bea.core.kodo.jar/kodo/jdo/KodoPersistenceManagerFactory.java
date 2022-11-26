package kodo.jdo;

import javax.jdo.PersistenceManagerFactory;
import kodo.remote.RemoteTransferListener;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.kernel.ConnectionRetainModes;
import org.apache.openjpa.lib.util.Closeable;

public interface KodoPersistenceManagerFactory extends PersistenceManagerFactory, ConnectionRetainModes, Closeable {
   OpenJPAConfiguration getConfiguration();

   Object putUserObject(Object var1, Object var2);

   Object getUserObject(Object var1);

   KodoPersistenceManager getPersistenceManager(boolean var1, int var2);

   KodoPersistenceManager getPersistenceManager(String var1, String var2, boolean var3, int var4);

   KodoDataStoreCache getDataStoreCache(String var1);

   QueryResultCache getQueryResultCache();

   boolean startPersistenceServer();

   boolean joinPersistenceServer();

   boolean stopPersistenceServer();

   boolean isPersistenceServerRunning();

   String getConnectionPassword();

   void addTransferListener(RemoteTransferListener var1);

   void removeTransferListener(RemoteTransferListener var1);
}
