package weblogic.application.info;

import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContext;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.utils.PathUtils;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;

public final class InfoManager {
   private static InfoManager instance = new InfoManager();

   private InfoManager() {
   }

   public static InfoManager getInstance() {
      return instance;
   }

   public String getApplicationDeployedRootDir() {
      if (PathUtils.USE_NEW_DEPLOYED_DIR) {
         ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
         if (cic != null && !cic.isGlobalRuntime()) {
            String partition = cic.getPartitionName();
            return PathUtils.getUserAppRootDirForPartition(partition);
         } else {
            return PathUtils.getUserAppRootDirForPartition((String)null);
         }
      } else {
         return null;
      }
   }

   public String getApplicationParam(String appParamName) {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      if (cic == null) {
         throw new IllegalStateException("CIC is not initialized");
      } else {
         String appId = cic.getApplicationId();
         if (appId == null) {
            throw new IllegalStateException("Cannot find the current application id");
         } else {
            ApplicationAccess applicationAccess = ApplicationAccess.getApplicationAccess();
            ApplicationContext context = applicationAccess.getApplicationContext(appId);
            if (context == null) {
               throw new IllegalStateException("Cannot find the application context for application: " + appId);
            } else {
               boolean isEar = ((ApplicationContextInternal)context).isEar();
               if (!isEar) {
                  throw new IllegalStateException("The application is not an .ear application");
               } else {
                  return context.getApplicationParameter(appParamName);
               }
            }
         }
      }
   }
}
