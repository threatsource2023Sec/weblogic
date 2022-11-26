package weblogic.work.concurrent.context;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.work.concurrent.utils.ConcurrentUtils;

public class ContextCache {
   private static Cache cache = new Cache();
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConcurrentContext");

   public static void removeData(String appId, String moduleId) {
      cache.removeData(appId, moduleId);
   }

   public static String putContext(Object context) {
      return cache.putData(context);
   }

   public static Object getContext(String key) {
      return cache.getData(key);
   }

   public static void removeData(String partitionName) {
      cache.removeData(partitionName);
   }

   static class Cache extends AbstractDataStorage {
      private long seqID;

      public synchronized String putData(Object data) {
         if (data == null) {
            if (ContextCache.debugLogger.isDebugEnabled()) {
               ContextCache.debugLogger.debug("putContext: null");
            }

            return null;
         } else {
            ContextData newContext = new ContextData();
            newContext.setData(data);
            Iterator it = this.cache.entrySet().iterator();

            while(it.hasNext()) {
               Map.Entry entry = (Map.Entry)it.next();
               AbstractDataStorage.WrappedData wrapped = (AbstractDataStorage.WrappedData)entry.getValue();
               if (wrapped == null) {
                  it.remove();
               } else {
                  Object val = wrapped.getData();
                  if (val == null) {
                     if (ContextCache.debugLogger.isDebugEnabled()) {
                        ContextCache.debugLogger.debug("putContext: WeakReference removed with key " + (String)entry.getKey());
                     }

                     it.remove();
                  } else if (newContext.isSameContext(wrapped)) {
                     if (ContextCache.debugLogger.isDebugEnabled()) {
                        ContextCache.debugLogger.debug("putContext: return with exist key=" + (String)entry.getKey() + ", data=" + data);
                     }

                     return (String)entry.getKey();
                  }
               }
            }

            StringBuilder builder = new StringBuilder();
            builder.append(System.currentTimeMillis());
            builder.append((long)(this.seqID++));
            String key = builder.toString();
            this.cache.put(key, newContext);
            if (ContextCache.debugLogger.isDebugEnabled()) {
               ContextCache.debugLogger.debug("putContext: key=" + key + " data=" + newContext);
            }

            return key;
         }
      }

      public synchronized Object getData(String key) {
         AbstractDataStorage.WrappedData data = (AbstractDataStorage.WrappedData)this.cache.get(key);
         if (data == null) {
            if (ContextCache.debugLogger.isDebugEnabled()) {
               ContextCache.debugLogger.debug("getContext: no item with key=" + key);
            }

            return null;
         } else {
            Object val = data.getData();
            if (val == null) {
               if (ContextCache.debugLogger.isDebugEnabled()) {
                  ContextCache.debugLogger.debug("getContext: WeakReference removed with key=" + key);
               }

               this.cache.remove(key);
            }

            return val;
         }
      }

      static class ContextData extends AbstractDataStorage.WrappedData {
         private WeakReference data;
         private static ComponentInvocationContextManager manager = ComponentInvocationContextManager.getInstance();

         ContextData() {
            ComponentInvocationContext invocation = manager.getCurrentComponentInvocationContext();
            this.setPartitionName(invocation.getPartitionName());
            this.setAppId(invocation.getApplicationId());
            this.setModuleId(invocation.getModuleName());
         }

         boolean isSameContext(AbstractDataStorage.WrappedData param) {
            return param.getData() == this.getData() && ConcurrentUtils.isSameString(this.getPartitionName(), param.getPartitionName()) && ConcurrentUtils.isSameString(this.getAppId(), param.getAppId()) && ConcurrentUtils.isSameString(this.getModuleId(), param.getModuleId());
         }

         public Object getData() {
            return this.data.get();
         }

         public void setData(Object data) {
            this.data = new WeakReference(data);
         }
      }
   }
}
