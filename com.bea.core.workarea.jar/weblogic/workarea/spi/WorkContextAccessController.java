package weblogic.workarea.spi;

import weblogic.workarea.WorkContextMap;

public abstract class WorkContextAccessController {
   public static final int CREATE = 0;
   public static final int READ = 1;
   public static final int UPDATE = 2;
   public static final int DELETE = 3;
   public static final int CALLBACK = 4;
   private static WorkContextAccessController SINGLETON;

   protected WorkContextAccessController() {
      if (SINGLETON != null) {
         throw new IllegalStateException("Cannot register two instances of WorkContextAccessController");
      } else {
         SINGLETON = this;
      }
   }

   public static boolean isAccessAllowed(String key, int type) {
      return getAccessController().checkAccess(key, type);
   }

   public static WorkContextMap getPriviledgedWorkContextMap(WorkContextMap map) {
      return getAccessController().getPriviledgedWrapper(map);
   }

   protected boolean checkAccess(String key, int type) {
      return true;
   }

   protected WorkContextMap getPriviledgedWrapper(WorkContextMap map) {
      return map;
   }

   private static WorkContextAccessController getAccessController() {
      return SINGLETON == null ? WorkContextAccessController.WorkContextAccessControllerHolder.INSTANCE : SINGLETON;
   }

   private static class WorkContextAccessControllerHolder {
      static final WorkContextAccessController INSTANCE = new WorkContextAccessController() {
      };
   }
}
