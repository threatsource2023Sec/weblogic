package weblogic.security.providers.authentication;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.security.service.SecurityManager;

public class IDCSFilterAccess {
   private Map filterServices = null;
   private static IDCSFilterAccess SINGLETON = null;

   private IDCSFilterAccess() {
      this.filterServices = new ConcurrentHashMap();
   }

   public static synchronized IDCSFilterAccess getInstance() {
      if (SINGLETON == null) {
         SINGLETON = new IDCSFilterAccess();
      }

      return SINGLETON;
   }

   public boolean registerFilterService(String key, IDCSFilterService idcsFilterService) {
      SecurityManager.checkKernelPermission();
      if (key == null) {
         return false;
      } else {
         this.filterServices.put(key, idcsFilterService);
         return true;
      }
   }

   public void unregisterFilterService(String key) {
      SecurityManager.checkKernelPermission();
      if (key != null && this.filterServices.containsKey(key)) {
         this.filterServices.remove(key);
      }
   }

   public IDCSFilterService getFilterService(String key) {
      return key != null && this.filterServices.containsKey(key) ? (IDCSFilterService)this.filterServices.get(key) : null;
   }
}
