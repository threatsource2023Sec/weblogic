package weblogic.connector.deploy;

import weblogic.application.ApplicationContextInternal;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.common.Debug;
import weblogic.connector.external.RAInfo;
import weblogic.connector.utils.RuntimeAccessUtils;
import weblogic.utils.classloaders.GenericClassLoader;

public class ClassLoaderUtil {
   public static GenericClassLoader getClassloader4RA(RAInfo raInfo, ApplicationContextInternal appCtx, GenericClassLoader raClassloader) {
      if (raInfo.isEnableGlobalAccessToClasses()) {
         if (raInfo.isCustomizedClassloading()) {
            ConnectorLogger.logClassloaderStructureIgnored(appCtx.getApplicationId(), raInfo.getDisplayName());
         }

         Debug.classloading("Target ClassLoader is Partition ClassLoader.");
         return appCtx.getPartitionClassLoader();
      } else {
         if (appCtx.isEar()) {
            if (raInfo.isCustomizedClassloading()) {
               return raClassloader;
            }

            if (RuntimeAccessUtils.getDomainMBean().isEnableEECompliantClassloadingForEmbeddedAdapters()) {
               ConnectorLogger.logUseAppClassloader(appCtx.getApplicationId(), raInfo.getDisplayName());
               return appCtx.getAppClassLoader();
            }

            ConnectorLogger.logUseRAClassloader(appCtx.getApplicationId(), raInfo.getDisplayName());
         }

         return raClassloader;
      }
   }
}
