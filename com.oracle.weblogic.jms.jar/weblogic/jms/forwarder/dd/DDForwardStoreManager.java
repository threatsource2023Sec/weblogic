package weblogic.jms.forwarder.dd;

import java.util.HashMap;

public class DDForwardStoreManager {
   public static DDForwardStoreManager singleton = new DDForwardStoreManager();
   public HashMap storeMap = new HashMap();

   private DDForwardStoreManager() {
   }

   public synchronized void addStore(DDForwardStore ddForwardStore) {
      this.storeMap.put(ddForwardStore.getStore().getName(), ddForwardStore);
   }

   public synchronized DDForwardStore getStore(String storeName) {
      return (DDForwardStore)this.storeMap.get(storeName);
   }
}
