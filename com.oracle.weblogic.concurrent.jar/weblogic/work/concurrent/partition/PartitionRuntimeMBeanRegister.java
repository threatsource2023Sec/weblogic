package weblogic.work.concurrent.partition;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.wl.ManagedExecutorServiceBean;
import weblogic.j2ee.descriptor.wl.ManagedScheduledExecutorServiceBean;
import weblogic.j2ee.descriptor.wl.ManagedThreadFactoryBean;
import weblogic.management.ManagementException;
import weblogic.management.runtime.ConcurrentManagedObjectsRuntimeMBean;
import weblogic.management.runtime.WorkManagerRuntimeMBean;
import weblogic.work.concurrent.ManagedExecutorServiceImpl;
import weblogic.work.concurrent.ManagedScheduledExecutorServiceImpl;
import weblogic.work.concurrent.ManagedThreadFactoryImpl;
import weblogic.work.concurrent.runtime.ManagedExecutorServiceRuntimeMBeanImpl;
import weblogic.work.concurrent.runtime.ManagedScheduledExecutorServiceRuntimeMBeanImpl;
import weblogic.work.concurrent.runtime.ManagedThreadFactoryRuntimeMBeanImpl;
import weblogic.work.concurrent.runtime.RuntimeMBeanRegister;

public class PartitionRuntimeMBeanRegister extends RuntimeMBeanRegister {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConcurrent");
   private final ConcurrentManagedObjectsRuntimeMBean concurrentRuntime;

   public PartitionRuntimeMBeanRegister(ConcurrentManagedObjectsRuntimeMBean concurrentRuntime) {
      this.concurrentRuntime = concurrentRuntime;
   }

   public ManagedExecutorServiceRuntimeMBeanImpl createManagedExecutorRuntimeMBean(ManagedExecutorServiceImpl executor, WorkManagerRuntimeMBean wmRuntimeMBean) throws ManagementException {
      ManagedExecutorServiceRuntimeMBeanImpl runtimeMBean = new ManagedExecutorServiceRuntimeMBeanImpl(executor, this.concurrentRuntime, wmRuntimeMBean);
      this.concurrentRuntime.addManagedExecutorServiceRuntime(runtimeMBean);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(runtimeMBean + " for " + runtimeMBean.getName() + " is created and installed under " + this.concurrentRuntime);
      }

      return runtimeMBean;
   }

   public ManagedScheduledExecutorServiceRuntimeMBeanImpl createManagedScheduleExecutorRuntimeMBean(ManagedScheduledExecutorServiceImpl executor, WorkManagerRuntimeMBean wmRuntimeMBean) throws ManagementException {
      ManagedScheduledExecutorServiceRuntimeMBeanImpl runtimeMBean = new ManagedScheduledExecutorServiceRuntimeMBeanImpl(executor, this.concurrentRuntime, wmRuntimeMBean);
      this.concurrentRuntime.addManagedScheduledExecutorServiceRuntime(runtimeMBean);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(runtimeMBean + " for " + runtimeMBean.getName() + " is created and installed under " + this.concurrentRuntime);
      }

      return runtimeMBean;
   }

   public ManagedThreadFactoryRuntimeMBeanImpl createManagedThreadFactoryRuntimeMBean(ManagedThreadFactoryImpl factory) throws ManagementException {
      ManagedThreadFactoryRuntimeMBeanImpl runtimeMBean = new ManagedThreadFactoryRuntimeMBeanImpl(factory, this.concurrentRuntime);
      this.concurrentRuntime.addManagedThreadFactoryRuntime(runtimeMBean);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(runtimeMBean + " for " + runtimeMBean.getName() + " is created and installed under " + this.concurrentRuntime);
      }

      return runtimeMBean;
   }

   public ManagedExecutorServiceBean[] getManagedExecutorBeans() {
      return null;
   }

   public ManagedScheduledExecutorServiceBean[] getManagedScheduledExecutorBeans() {
      return null;
   }

   public ManagedThreadFactoryBean[] getManagedThreadFactoryBeans() {
      return null;
   }
}
