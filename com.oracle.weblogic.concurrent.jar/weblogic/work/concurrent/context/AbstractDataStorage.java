package weblogic.work.concurrent.context;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.work.concurrent.utils.ConcurrentUtils;

public abstract class AbstractDataStorage {
   protected HashMap cache = new HashMap();

   public abstract String putData(Object var1);

   public synchronized Object getData(String key) {
      WrappedData data = (WrappedData)this.cache.get(key);
      return data == null ? null : data.getData();
   }

   public synchronized void removeData(String appId, String moduleId) {
      Iterator it = this.cache.entrySet().iterator();

      while(true) {
         while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            WrappedData cached = (WrappedData)entry.getValue();
            if (cached == null) {
               it.remove();
            } else if (ConcurrentUtils.isSameString(cached.getAppId(), appId) && (moduleId == null || ConcurrentUtils.isSameString(cached.getModuleId(), moduleId))) {
               it.remove();
            }
         }

         return;
      }
   }

   public synchronized void removeData(String partitionName) {
      Iterator it = this.cache.entrySet().iterator();

      while(true) {
         WrappedData cached;
         do {
            if (!it.hasNext()) {
               return;
            }

            Map.Entry entry = (Map.Entry)it.next();
            cached = (WrappedData)entry.getValue();
         } while(cached != null && !ConcurrentUtils.isSameString(cached.getPartitionName(), partitionName));

         it.remove();
      }
   }

   public static class WrappedData {
      private String partitionName;
      private String appId;
      private String moduleId;
      private String compName;
      private Object data;

      public String getAppId() {
         return this.appId;
      }

      public void setAppId(String appId) {
         this.appId = appId;
      }

      public String getModuleId() {
         return this.moduleId;
      }

      public void setModuleId(String moduleId) {
         this.moduleId = moduleId;
      }

      public String getPartitionName() {
         return this.partitionName;
      }

      public void setPartitionName(String partitionName) {
         this.partitionName = partitionName;
      }

      public String getCompName() {
         return this.compName;
      }

      public void setCompName(String compName) {
         this.compName = compName;
      }

      public Object getData() {
         return this.data;
      }

      public void setData(Object data) {
         this.data = data;
      }

      public String toString() {
         return "(pName = " + this.getPartitionName() + ", appId = " + this.getAppId() + ", moduleId = " + this.getModuleId() + ", compName = " + this.getCompName() + ", data = " + this.getData() + ")";
      }
   }
}
