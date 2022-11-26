package weblogic.work.concurrent.spi;

import java.util.ArrayList;
import java.util.List;
import weblogic.work.concurrent.context.AbstractDataStorage;
import weblogic.work.concurrent.utils.ConcurrentUtils;

public class ContextProviderManager {
   private static ContextProviders cpCache = new ContextProviders();

   public static void setContextProviders(String partitionName, String appId, String moduleId, String compName, ContextProvider... contextProviders) {
      if (appId == null) {
         throw new IllegalArgumentException("appId can not be null");
      } else {
         List cpList = new ArrayList();
         if (contextProviders != null) {
            ContextProvider[] var6 = contextProviders;
            int var7 = contextProviders.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               ContextProvider cp = var6[var8];
               if (cp != null) {
                  cpList.add(cp);
               }
            }
         }

         setContextProviders(partitionName, appId, moduleId, compName, (List)cpList);
      }
   }

   private static void setContextProviders(String partitionName, String appId, String moduleId, String compName, List cpList) {
      AbstractDataStorage.WrappedData wrapped = new AbstractDataStorage.WrappedData();
      wrapped.setPartitionName(partitionName);
      wrapped.setAppId(appId);
      wrapped.setModuleId(moduleId);
      wrapped.setCompName(compName);
      wrapped.setData(cpList);
      cpCache.putData(wrapped);
   }

   public static List getContextProviders(String appId, String moduleId, String compName) {
      if (appId == null) {
         return null;
      } else {
         AbstractDataStorage.WrappedData tmp = new AbstractDataStorage.WrappedData();
         tmp.setAppId(appId);
         tmp.setModuleId(moduleId);
         tmp.setCompName(compName);
         String key = ContextProviderManager.ContextProviders.genKey(tmp);
         Object data = cpCache.getData(key);
         if (data == null) {
            tmp.setCompName((String)null);
            key = ContextProviderManager.ContextProviders.genKey(tmp);
            data = cpCache.getData(key);
            if (data == null) {
               tmp.setModuleId((String)null);
               key = ContextProviderManager.ContextProviders.genKey(tmp);
               data = cpCache.getData(key);
            }
         }

         return (List)data;
      }
   }

   public static void removeContextProviders(String appId, String moduleId) {
      cpCache.removeData(appId, moduleId);
   }

   public static void removeContextProviders(String partitionName) {
      cpCache.removeData(partitionName);
   }

   static class ContextProviders extends AbstractDataStorage {
      public String putData(Object data) {
         AbstractDataStorage.WrappedData wrapped = null;
         if (data instanceof AbstractDataStorage.WrappedData) {
            wrapped = (AbstractDataStorage.WrappedData)data;
         } else {
            wrapped = new AbstractDataStorage.WrappedData();
            wrapped.setData(data);
         }

         String key = genKey(wrapped);
         this.cache.put(key, wrapped);
         return key;
      }

      public static String genKey(AbstractDataStorage.WrappedData wrapped) {
         return ConcurrentUtils.genKey(wrapped.getAppId(), wrapped.getModuleId(), wrapped.getCompName());
      }
   }
}
