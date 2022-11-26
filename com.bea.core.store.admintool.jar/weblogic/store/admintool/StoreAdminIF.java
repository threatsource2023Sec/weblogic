package weblogic.store.admintool;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import weblogic.store.PersistentStoreException;
import weblogic.store.StoreWritePolicy;
import weblogic.store.xa.PersistentStoreXA;

public interface StoreAdminIF {
   PersistentStoreXA openFileStore(String var1, String var2, StoreWritePolicy var3, boolean var4) throws PersistentStoreException;

   PersistentStoreXA openJDBCStore(String var1, String var2, String var3, String var4, String var5, String var6, String var7, Properties var8) throws PersistentStoreException;

   void closeStore(PersistentStoreXA var1) throws PersistentStoreException;

   String[] getAllConnections(PersistentStoreXA var1) throws PersistentStoreException;

   void copyConnections(PersistentStoreXA var1, PersistentStoreXA var2, boolean var3, String[] var4) throws PersistentStoreException;

   void deleteConnections(PersistentStoreXA var1, String[] var2) throws PersistentStoreException;

   void dumpConnections(PersistentStoreXA var1, String var2, String[] var3, boolean var4) throws PersistentStoreException, IOException;

   int rsAttach(String var1, String var2, int var3) throws IOException;

   int rsAttachToDaemon(int var1) throws IOException;

   void rsDetach() throws IOException;

   boolean isAttached() throws IOException;

   int rsGetAttachedDaemonIndex() throws IOException;

   HashMap rsListDaemons() throws IOException;

   int rsShutdownDaemon(int var1, boolean var2, boolean var3) throws IOException;

   HashMap rsListGlobalRegions() throws IOException;

   HashMap rsListLocalRegions() throws IOException;

   HashMap rsListRegion(String var1) throws IOException;

   int rsDeleteRegion(String var1, boolean var2) throws IOException;

   void rsClean() throws IOException;
}
