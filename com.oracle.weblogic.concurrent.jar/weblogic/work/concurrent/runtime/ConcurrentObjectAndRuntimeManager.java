package weblogic.work.concurrent.runtime;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.concurrent.ContextService;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.concurrent.ManagedThreadFactory;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.WorkManagerRuntimeMBean;
import weblogic.work.WorkManager;
import weblogic.work.concurrent.ConcurrencyLogger;
import weblogic.work.concurrent.ConcurrentManagedObject;
import weblogic.work.concurrent.ContextServiceImpl;
import weblogic.work.concurrent.ManagedExecutorServiceImpl;
import weblogic.work.concurrent.ManagedScheduledExecutorServiceImpl;
import weblogic.work.concurrent.ManagedThreadFactoryImpl;
import weblogic.work.concurrent.spi.ConcurrentManagedObjectBuilder;

public abstract class ConcurrentObjectAndRuntimeManager {
   private static final String DELIMITER = "@";
   protected static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConcurrent");
   protected Map managedExecutors = Collections.synchronizedMap(new HashMap());
   protected Map managedScheduledExecutors = Collections.synchronizedMap(new HashMap());
   protected Map contextServices = Collections.synchronizedMap(new HashMap());
   protected Map managedThreadFactories = Collections.synchronizedMap(new HashMap());
   protected Map typeToObjectMapMap = new HashMap();

   public ConcurrentObjectAndRuntimeManager() {
      this.typeToObjectMapMap.put(ManagedThreadFactory.class.getName(), this.managedThreadFactories);
      this.typeToObjectMapMap.put(ManagedExecutorService.class.getName(), this.managedExecutors);
      this.typeToObjectMapMap.put(ManagedScheduledExecutorService.class.getName(), this.managedScheduledExecutors);
      this.typeToObjectMapMap.put(ContextService.class.getName(), this.contextServices);
   }

   protected abstract String getPrefix(ConcurrentManagedObjectBuilder var1);

   protected abstract void checkCreateObject(Map var1, String var2, String var3) throws DeploymentException;

   protected abstract ConcurrentObjectAndRuntime createConcurrentObjectAndRuntime(ConcurrentManagedObject var1, RuntimeMBeanDelegate var2);

   public ManagedThreadFactory getManagedThreadFactory(String name, String prefix) {
      ConcurrentObjectAndRuntime result = (ConcurrentObjectAndRuntime)this.managedThreadFactories.get(this.getObjectName(prefix, name));
      return result == null ? null : (ManagedThreadFactory)result.getOrCreateObject();
   }

   public ManagedExecutorService getManagedExecutorService(String name, String prefix) {
      ConcurrentObjectAndRuntime result = (ConcurrentObjectAndRuntime)this.managedExecutors.get(this.getObjectName(prefix, name));
      return result == null ? null : (ManagedExecutorService)result.getOrCreateObject();
   }

   public ManagedScheduledExecutorService getManagedScheduledExecutorService(String name, String prefix) {
      ConcurrentObjectAndRuntime result = (ConcurrentObjectAndRuntime)this.managedScheduledExecutors.get(this.getObjectName(prefix, name));
      return result == null ? null : (ManagedScheduledExecutorService)result.getOrCreateObject();
   }

   public ContextService getContextService(String name, String prefix) {
      ConcurrentObjectAndRuntime result = (ConcurrentObjectAndRuntime)this.contextServices.get(this.getObjectName(prefix, name));
      return result == null ? null : (ContextService)result.getOrCreateObject();
   }

   protected String getObjectName(String prefix, String name) {
      return prefix != null ? prefix + "@" + name : name;
   }

   public ManagedThreadFactoryImpl createManagedThreadFactory(ConcurrentManagedObjectBuilder builder, RuntimeMBeanRegister register, ConcurrentObjectAndRuntime objectAndRuntime) throws DeploymentException {
      String objectName = this.getObjectName(this.getPrefix(builder), builder.getName());
      this.checkCreateObject(this.managedThreadFactories, objectName, ManagedThreadFactory.class.getSimpleName());
      ConcurrencyLogger.logCreatingMTF(builder.getModuleId(), builder.getAppId(), builder.getName(), builder.getPartitionName());
      ManagedThreadFactoryImpl mtf = (ManagedThreadFactoryImpl)builder.buildManagedThreadFactory();
      ManagedThreadFactoryRuntimeMBeanImpl runtimeMBean = null;

      try {
         runtimeMBean = register.createManagedThreadFactoryRuntimeMBean(mtf);
      } catch (ManagementException var8) {
         throw new DeploymentException(var8);
      }

      if (objectAndRuntime == null) {
         this.managedThreadFactories.put(objectName, this.createConcurrentObjectAndRuntime(mtf, runtimeMBean));
      } else {
         objectAndRuntime.set(mtf, runtimeMBean);
      }

      return mtf;
   }

   public ContextServiceImpl createContextService(ConcurrentManagedObjectBuilder builder, ConcurrentObjectAndRuntime objectAndRuntime) throws DeploymentException {
      String objectName = this.getObjectName(this.getPrefix(builder), builder.getName());
      this.checkCreateObject(this.contextServices, objectName, ContextService.class.getSimpleName());
      ConcurrencyLogger.logCreatingContextService(builder.getModuleId(), builder.getAppId(), builder.getName(), builder.getPartitionName());
      ContextServiceImpl cs = (ContextServiceImpl)builder.buildContextService();
      if (objectAndRuntime == null) {
         this.contextServices.put(objectName, this.createConcurrentObjectAndRuntime(cs, (RuntimeMBeanDelegate)null));
      } else {
         objectAndRuntime.set(cs, (RuntimeMBeanDelegate)null);
      }

      return cs;
   }

   public ConcurrentManagedObject createManagedExecutor(ConcurrentManagedObjectBuilder builder, RuntimeMBeanRegister register, WorkManagerRuntimeMBean wmRuntimeMBean, ConcurrentObjectAndRuntime objectAndRuntime) throws DeploymentException {
      String objectName = this.getObjectName(this.getPrefix(builder), builder.getName());
      this.checkCreateObject(this.managedExecutors, objectName, ManagedExecutorService.class.getSimpleName());
      WorkManager workManager = builder.getWorkManager();
      ManagedExecutorServiceImpl executor = (ManagedExecutorServiceImpl)builder.buildManagedExecutorService();
      ConcurrencyLogger.logCreatingMES(builder.getPartitionName(), builder.getAppId(), builder.getModuleId(), workManager.getName(), builder.getName());
      ManagedExecutorServiceRuntimeMBeanImpl runtimeMBean = null;

      try {
         runtimeMBean = register.createManagedExecutorRuntimeMBean(executor, wmRuntimeMBean);
      } catch (ManagementException var10) {
         throw new DeploymentException(var10);
      }

      if (objectAndRuntime == null) {
         this.managedExecutors.put(objectName, this.createConcurrentObjectAndRuntime(executor, runtimeMBean));
      } else {
         objectAndRuntime.set(executor, runtimeMBean);
      }

      return executor;
   }

   public ConcurrentManagedObject createManagedScheduledExecutor(ConcurrentManagedObjectBuilder builder, RuntimeMBeanRegister register, WorkManagerRuntimeMBean wmRuntimeMBean, ConcurrentObjectAndRuntime objectAndRuntime) throws DeploymentException {
      String objectName = this.getObjectName(this.getPrefix(builder), builder.getName());
      this.checkCreateObject(this.managedScheduledExecutors, objectName, ManagedScheduledExecutorService.class.getSimpleName());
      WorkManager workManager = builder.getWorkManager();
      ManagedScheduledExecutorServiceImpl executor = (ManagedScheduledExecutorServiceImpl)builder.buildManagedScheduledExecutorService();
      ConcurrencyLogger.logCreatingMSES(builder.getPartitionName(), builder.getAppId(), builder.getModuleId(), workManager.getName(), builder.getName());
      ManagedScheduledExecutorServiceRuntimeMBeanImpl runtimeMBean = null;

      try {
         runtimeMBean = register.createManagedScheduleExecutorRuntimeMBean(executor, wmRuntimeMBean);
      } catch (ManagementException var10) {
         throw new DeploymentException(var10);
      }

      if (objectAndRuntime == null) {
         this.managedScheduledExecutors.put(objectName, this.createConcurrentObjectAndRuntime(executor, runtimeMBean));
      } else {
         objectAndRuntime.set(executor, runtimeMBean);
      }

      return executor;
   }

   public ConcurrentObjectAndRuntime removeObject(String jsr236Class, String name, String prefix) {
      Map map = (Map)this.typeToObjectMapMap.get(jsr236Class);
      if (map == null) {
         return null;
      } else {
         ConcurrentObjectAndRuntime target = (ConcurrentObjectAndRuntime)map.remove(this.getObjectName(prefix, name));
         if (target == null) {
            return null;
         } else {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Removing " + target.getObject() + " with key " + this.getObjectName(prefix, name) + "from " + this);
            }

            try {
               target.unregister();
            } catch (ManagementException var7) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug(var7.getMessage(), var7);
               }
            }

            return target;
         }
      }
   }

   public interface ConcurrentObjectAndRuntime {
      ConcurrentManagedObject getObject();

      ConcurrentManagedObject getOrCreateObject();

      void unregister() throws ManagementException;

      void set(ConcurrentManagedObject var1, RuntimeMBeanDelegate var2) throws IllegalStateException;
   }
}
