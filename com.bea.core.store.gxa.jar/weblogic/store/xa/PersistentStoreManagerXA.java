package weblogic.store.xa;

import java.util.HashMap;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreManager;
import weblogic.store.RuntimeHandler;
import weblogic.store.StoreWritePolicy;
import weblogic.store.common.PartitionNameUtils;
import weblogic.store.io.PersistentStoreIO;
import weblogic.store.io.file.FileStoreIO;
import weblogic.store.xa.internal.PersistentStoreXAImpl;

public class PersistentStoreManagerXA {
   public static PersistentStoreXA makeXAStore(String name, String dirName, String overrideResourceName, boolean autoCreateDirs, RuntimeHandler runtimeHandler) throws PersistentStoreException {
      if (name != null && dirName != null) {
         if (overrideResourceName != null) {
            throw new AssertionError("Override resource name is " + overrideResourceName + ", expect null");
         } else {
            String shortName = PartitionNameUtils.stripDecoratedPartitionName(name);
            PersistentStoreIO io = new FileStoreIO(shortName, dirName, autoCreateDirs);
            return new PersistentStoreXAImpl(name, io, overrideResourceName, runtimeHandler);
         }
      } else {
         throw new PersistentStoreException("Name and directory name may not be null");
      }
   }

   public static PersistentStoreXA makeXAStore(String name, String dirName, String overrideResourceName, boolean autoCreateDirs) throws PersistentStoreException {
      return makeXAStore(name, dirName, overrideResourceName, autoCreateDirs, (RuntimeHandler)null);
   }

   public static PersistentStoreXA createFileStore(String name, String directory, String overrideResourceName, StoreWritePolicy writePolicy, boolean autoCreate, RuntimeHandler runtimeHandler) throws PersistentStoreException {
      HashMap config = new HashMap();
      config.put("SynchronousWritePolicy", writePolicy);
      return createFileStore(name, directory, overrideResourceName, autoCreate, runtimeHandler, config);
   }

   public static PersistentStoreXA createFileStore(String name, String directory, String overrideResourceName, boolean autoCreate, RuntimeHandler runtimeHandler, HashMap config) throws PersistentStoreException {
      PersistentStoreXA newStore = makeXAStore(name, directory, overrideResourceName, autoCreate, runtimeHandler);
      newStore.open(config);
      PersistentStoreManager manager = PersistentStoreManager.getManager();
      synchronized(manager) {
         if (manager.storeExists(name)) {
            newStore.close();
            throw new PersistentStoreException("The persistent store " + name + " already exists");
         } else {
            manager.addStore(name, newStore);
            return newStore;
         }
      }
   }

   public static PersistentStoreXA createFileStore(String name, String directory, String overrideResourceName, String storeWritePolicy, boolean autoCreate) throws PersistentStoreException {
      return createFileStore(name, directory, overrideResourceName, StoreWritePolicy.getPolicy(storeWritePolicy), autoCreate, (RuntimeHandler)null);
   }

   public static PersistentStoreXA createFileStore(String name, String directory, StoreWritePolicy writePolicy, boolean autoCreate, RuntimeHandler runtimeHandler) throws PersistentStoreException {
      return createFileStore(name, directory, (String)null, writePolicy, autoCreate, runtimeHandler);
   }

   public static PersistentStoreXA createFileStore(String name, String directory, StoreWritePolicy writePolicy, boolean autoCreate) throws PersistentStoreException {
      return createFileStore(name, directory, writePolicy, autoCreate, (RuntimeHandler)null);
   }
}
