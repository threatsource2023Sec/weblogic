package weblogic.messaging.saf.store;

import java.util.HashMap;
import weblogic.management.configuration.PersistentStoreMBean;
import weblogic.management.utils.GenericDeploymentManager;
import weblogic.management.utils.GenericManagedDeployment;
import weblogic.messaging.saf.SAFException;

public final class SAFStoreManager {
   private static final SAFStoreManager SINGLETON = new SAFStoreManager();
   private static final HashMap stores = new HashMap();

   private SAFStoreManager() {
   }

   public static SAFStoreManager getManager() {
      return SINGLETON;
   }

   public SAFStore createSAFStore(PersistentStoreMBean storeMBean, String safAgentOrJMSServerName, boolean isRA, GenericManagedDeployment deployment, String alternativeAgentName) throws SAFException {
      String storeName = storeMBean == null ? null : storeMBean.getName();
      if (storeMBean != null && deployment.isClustered() && deployment.isDistributed()) {
         storeName = GenericDeploymentManager.getDecoratedDistributedInstanceName(storeMBean, deployment.getInstanceName());
      }

      return findOrCreateSAFStore(storeName, safAgentOrJMSServerName, alternativeAgentName, isRA);
   }

   private static synchronized SAFStore findOrCreateSAFStore(String storeName, String safAgentOrJMSServerName, String alternativeAgentName, boolean isRA) throws SAFException {
      String realName = safAgentOrJMSServerName + (isRA ? ".RA" : ".SA");
      String alternativeRealName = alternativeAgentName == null ? null : alternativeAgentName + (isRA ? ".RA" : ".SA");
      SAFStore store = (SAFStore)stores.get(realName);
      if (store != null) {
         return store;
      } else {
         Object store;
         if (alternativeAgentName == null) {
            store = new SAFStore(storeName, realName, isRA);
         } else {
            store = new SAFStoreAdvanced(storeName, safAgentOrJMSServerName, realName, isRA, alternativeRealName);
         }

         stores.put(realName, store);
         return (SAFStore)store;
      }
   }
}
