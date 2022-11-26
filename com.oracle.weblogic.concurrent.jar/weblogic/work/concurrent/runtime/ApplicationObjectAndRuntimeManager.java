package weblogic.work.concurrent.runtime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.work.concurrent.ConcurrencyLogger;
import weblogic.work.concurrent.ConcurrentManagedObject;
import weblogic.work.concurrent.spi.ConcurrentManagedObjectBuilder;

public class ApplicationObjectAndRuntimeManager extends ConcurrentObjectAndRuntimeManager {
   private final String appId;

   public ApplicationObjectAndRuntimeManager(String appId) {
      this.appId = appId;
   }

   protected String getPrefix(ConcurrentManagedObjectBuilder builder) {
      return builder.getModuleId();
   }

   protected void checkCreateObject(Map map, String objectName, String simpleClassName) throws DeploymentException {
      if (map.containsKey(objectName)) {
         throw new DeploymentException(String.format("%s %s already exists", simpleClassName, objectName));
      }
   }

   protected ConcurrentObjectAndRuntimeManager.ConcurrentObjectAndRuntime createConcurrentObjectAndRuntime(ConcurrentManagedObject object, RuntimeMBeanDelegate mbean) {
      return new ApplicationObjectAndRuntime(object, mbean);
   }

   public ConcurrentManagedObject getObject(String className, String prefix, String name) {
      Map map = (Map)this.typeToObjectMapMap.get(className);
      if (map == null) {
         return null;
      } else {
         ConcurrentObjectAndRuntimeManager.ConcurrentObjectAndRuntime info = (ConcurrentObjectAndRuntimeManager.ConcurrentObjectAndRuntime)map.get(this.getObjectName(prefix, name));
         if (info == null && prefix != null) {
            info = (ConcurrentObjectAndRuntimeManager.ConcurrentObjectAndRuntime)map.get(this.getObjectName((String)null, name));
         }

         if (info == null) {
            ConcurrencyLogger.logConcurrentObjectNotFound(this.appId, prefix, name);
            return null;
         } else {
            return info.getObject();
         }
      }
   }

   public List getAllConcurrentManagedObjects() {
      List cmolist = new ArrayList();
      List mapList = new ArrayList(this.typeToObjectMapMap.values());
      Iterator var3 = mapList.iterator();

      while(var3.hasNext()) {
         Map map = (Map)var3.next();
         List objAndRuntimeList = new ArrayList(map.values());
         Iterator var6 = objAndRuntimeList.iterator();

         while(var6.hasNext()) {
            ConcurrentObjectAndRuntimeManager.ConcurrentObjectAndRuntime info = (ConcurrentObjectAndRuntimeManager.ConcurrentObjectAndRuntime)var6.next();
            ConcurrentManagedObject object = info.getObject();
            cmolist.add(object);
         }
      }

      return cmolist;
   }

   public void clear() {
      Iterator var1 = this.typeToObjectMapMap.values().iterator();

      while(var1.hasNext()) {
         Map map = (Map)var1.next();
         Iterator var3 = map.values().iterator();

         while(var3.hasNext()) {
            ConcurrentObjectAndRuntimeManager.ConcurrentObjectAndRuntime objAndRuntime = (ConcurrentObjectAndRuntimeManager.ConcurrentObjectAndRuntime)var3.next();

            try {
               objAndRuntime.unregister();
            } catch (ManagementException var6) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Failed to unregister " + objAndRuntime.getObject(), var6);
               }
            }
         }

         map.clear();
      }

   }

   public String toString() {
      return super.toString() + "(" + this.appId + ")";
   }

   public static class ApplicationObjectAndRuntime implements ConcurrentObjectAndRuntimeManager.ConcurrentObjectAndRuntime {
      protected volatile ConcurrentManagedObject object;
      protected volatile RuntimeMBeanDelegate runtimeMBean;

      public ConcurrentManagedObject getObject() {
         return this.object;
      }

      public ConcurrentManagedObject getOrCreateObject() {
         return this.object;
      }

      public ApplicationObjectAndRuntime(ConcurrentManagedObject object, RuntimeMBeanDelegate mbean) {
         this.object = object;
         this.runtimeMBean = mbean;
      }

      public void unregister() throws ManagementException {
         if (this.runtimeMBean != null) {
            this.runtimeMBean.unregister();
         }

      }

      public void set(ConcurrentManagedObject object, RuntimeMBeanDelegate runtime) {
         throw new IllegalStateException("ApplicationObjectAndRuntimeManager does not support lazy creation of ConcurrentManagedObject.");
      }
   }
}
