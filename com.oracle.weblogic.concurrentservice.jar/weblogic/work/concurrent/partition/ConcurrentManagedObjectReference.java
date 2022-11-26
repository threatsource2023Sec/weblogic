package weblogic.work.concurrent.partition;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ManagedExecutorServiceMBean;
import weblogic.management.configuration.ManagedScheduledExecutorServiceMBean;
import weblogic.management.configuration.ManagedThreadFactoryMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.WorkManagerRuntimeMBean;
import weblogic.work.concurrent.ConcurrencyLogger;
import weblogic.work.concurrent.ConcurrentManagedObject;
import weblogic.work.concurrent.runtime.ApplicationObjectAndRuntimeManager;
import weblogic.work.concurrent.runtime.ConcurrentManagedObjectsRuntimeMBeanImpl;
import weblogic.work.concurrent.runtime.RuntimeAccessUtils;
import weblogic.work.concurrent.runtime.RuntimeMBeanRegister;
import weblogic.work.concurrent.spi.ConcurrentManagedObjectBuilder;
import weblogic.work.concurrent.utils.ConcurrentUtils;

public abstract class ConcurrentManagedObjectReference extends ApplicationObjectAndRuntimeManager.ApplicationObjectAndRuntime {
   protected static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConcurrent");
   protected static final PartitionObjectAndRuntimeManager concurrentObjectAndRuntimeManager = PartitionObjectAndRuntimeManager.getInstance();

   public ConcurrentManagedObjectReference() {
      super((ConcurrentManagedObject)null, (RuntimeMBeanDelegate)null);
   }

   public ConcurrentManagedObject getOrCreateObject() {
      if (this.object != null) {
         return this.object;
      } else {
         String partitionName = RuntimeAccessUtils.getCurrentPartitionName();
         synchronized(concurrentObjectAndRuntimeManager) {
            if (this.object != null) {
               return this.object;
            } else {
               ConcurrentManagedObjectsRuntimeMBeanImpl concurrentRuntime;
               try {
                  concurrentRuntime = RuntimeAccessUtils.getConcurrentManagedObjectsRuntime();
               } catch (ManagementException var6) {
                  ConcurrencyLogger.logPartitionCMOCreationError();
                  throw new RuntimeException(var6);
               }

               ConcurrentManagedObject var10000;
               try {
                  RuntimeMBeanRegister register = new PartitionRuntimeMBeanRegister(concurrentRuntime);
                  this.createObject(partitionName, register);
                  this.object.start();
                  var10000 = this.object;
               } catch (DeploymentException var7) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug(var7.getMessage(), var7);
                  }

                  return null;
               }

               return var10000;
            }
         }
      }
   }

   public void set(ConcurrentManagedObject object, RuntimeMBeanDelegate runtime) {
      this.object = object;
      this.runtimeMBean = runtime;
   }

   protected abstract void createObject(String var1, RuntimeMBeanRegister var2) throws DeploymentException;

   public abstract String getName();

   static class ContextServiceReference extends ConcurrentManagedObjectReference {
      protected void createObject(String partitionName, RuntimeMBeanRegister register) throws DeploymentException {
         PartitionConcurrentBuilderSetting builderFactory = new PartitionConcurrentBuilderSetting(partitionName);
         ConcurrentManagedObjectBuilder builder = new ConcurrentManagedObjectBuilder();
         builderFactory.defaultContextServiceConfig(builder);
         concurrentObjectAndRuntimeManager.createContextService(builder, this);
      }

      public String getName() {
         return ConcurrentUtils.getDefaultCSInfo().getName();
      }
   }

   static class ManagedThreadFactoryReference extends ConcurrentManagedObjectReference {
      private ManagedThreadFactoryMBean bean;

      public ManagedThreadFactoryReference(ManagedThreadFactoryMBean bean) {
         this.bean = bean;
      }

      protected void createObject(String partitionName, RuntimeMBeanRegister register) throws DeploymentException {
         PartitionConcurrentBuilderSetting builderFactory = new PartitionConcurrentBuilderSetting(partitionName);
         ConcurrentManagedObjectBuilder builder = new ConcurrentManagedObjectBuilder();
         if (this.bean == null) {
            builderFactory.defaultMTFConfig(builder);
         } else {
            builderFactory.config(builder, this.bean, (String)null);
         }

         concurrentObjectAndRuntimeManager.createManagedThreadFactory(builder, register, this);
      }

      public String getName() {
         return this.bean == null ? ConcurrentUtils.getDefaultMTFInfo().getName() : this.bean.getName();
      }
   }

   static class ManagedScheduledExecutorServiceReference extends ConcurrentManagedObjectReference {
      private ManagedScheduledExecutorServiceMBean bean;

      public ManagedScheduledExecutorServiceReference(ManagedScheduledExecutorServiceMBean bean) {
         this.bean = bean;
      }

      protected void createObject(String partitionName, RuntimeMBeanRegister register) throws DeploymentException {
         PartitionConcurrentBuilderSetting builderFactory = new PartitionConcurrentBuilderSetting(partitionName);
         ConcurrentManagedObjectBuilder builder = new ConcurrentManagedObjectBuilder();
         if (this.bean == null) {
            builderFactory.defaultScheduledExecutorConfig(builder);
         } else {
            builderFactory.config(builder, this.bean, (String)null);
         }

         WorkManagerRuntimeMBean wmRuntimeMBean = RuntimeAccessUtils.getPartitionWorkManagerRuntimeMBean(partitionName, builder.getWorkManager().getName());
         concurrentObjectAndRuntimeManager.createManagedScheduledExecutor(builder, register, wmRuntimeMBean, this);
      }

      public String getName() {
         return this.bean == null ? ConcurrentUtils.getDefaultMSESInfo().getName() : this.bean.getName();
      }
   }

   static class ManagedExecutorServiceReference extends ConcurrentManagedObjectReference {
      private ManagedExecutorServiceMBean bean;

      public ManagedExecutorServiceReference(ManagedExecutorServiceMBean bean) {
         this.bean = bean;
      }

      protected void createObject(String partitionName, RuntimeMBeanRegister register) throws DeploymentException {
         PartitionConcurrentBuilderSetting builderFactory = new PartitionConcurrentBuilderSetting(partitionName);
         ConcurrentManagedObjectBuilder builder = new ConcurrentManagedObjectBuilder();
         if (this.bean == null) {
            builderFactory.defaultExecutorConfig(builder);
         } else {
            builderFactory.config(builder, this.bean, (String)null);
         }

         WorkManagerRuntimeMBean wmRuntimeMBean = RuntimeAccessUtils.getPartitionWorkManagerRuntimeMBean(partitionName, builder.getWorkManager().getName());
         concurrentObjectAndRuntimeManager.createManagedExecutor(builder, register, wmRuntimeMBean, this);
      }

      public String getName() {
         return this.bean == null ? ConcurrentUtils.getDefaultMESInfo().getName() : this.bean.getName();
      }
   }
}
