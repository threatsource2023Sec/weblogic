package weblogic.work.concurrent.runtime;

import weblogic.j2ee.descriptor.wl.ManagedExecutorServiceBean;
import weblogic.j2ee.descriptor.wl.ManagedThreadFactoryBean;
import weblogic.management.configuration.BaseExecutorServiceMBean;
import weblogic.management.configuration.BaseThreadFactoryMBean;
import weblogic.work.concurrent.spi.ConcurrentManagedObjectBuilder;
import weblogic.work.concurrent.utils.ConcurrentUtils;

public abstract class ConcurrentBuilderSetting {
   private String partitionName;

   public ConcurrentBuilderSetting(String partitionName) {
      this.partitionName = partitionName;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   protected abstract void doConfig(ConcurrentManagedObjectBuilder var1, String var2, String var3);

   public void defaultExecutorConfig(ConcurrentManagedObjectBuilder builder) {
      this.defaultConfig(builder, ConcurrentUtils.getDefaultMESInfo().getName());
      this.createExecutorDaemonThreadManager(builder, 10);
   }

   public void defaultScheduledExecutorConfig(ConcurrentManagedObjectBuilder builder) {
      this.defaultConfig(builder, ConcurrentUtils.getDefaultMSESInfo().getName());
      this.createExecutorDaemonThreadManager(builder, 10);
   }

   public void defaultMTFConfig(ConcurrentManagedObjectBuilder builder) {
      this.defaultConfig(builder, ConcurrentUtils.getDefaultMTFInfo().getName());
      this.createMTFDaemonThreadManager(builder, 10);
   }

   public void defaultContextServiceConfig(ConcurrentManagedObjectBuilder builder) {
      this.defaultConfig(builder, ConcurrentUtils.getDefaultCSInfo().getName());
   }

   private void defaultConfig(ConcurrentManagedObjectBuilder builder, String name) {
      builder.name(name);
      builder.priority(5);
      this.doConfig(builder, (String)null, (String)null);
   }

   public void config(ConcurrentManagedObjectBuilder builder, BaseExecutorServiceMBean bean, String moduleId) {
      builder.name(bean.getName());
      builder.priority(bean.getLongRunningPriority());
      this.createExecutorDaemonThreadManager(builder, bean.getMaxConcurrentLongRunningRequests());
      this.doConfig(builder, moduleId, bean.getDispatchPolicy());
   }

   public void config(ConcurrentManagedObjectBuilder builder, ManagedExecutorServiceBean bean, String moduleId) {
      builder.name(bean.getName());
      builder.priority(bean.getLongRunningPriority());
      this.createExecutorDaemonThreadManager(builder, bean.getMaxConcurrentLongRunningRequests());
      this.doConfig(builder, moduleId, bean.getDispatchPolicy());
   }

   public void config(ConcurrentManagedObjectBuilder builder, BaseThreadFactoryMBean bean, String moduleId) {
      builder.name(bean.getName());
      builder.priority(bean.getPriority());
      this.createMTFDaemonThreadManager(builder, bean.getMaxConcurrentNewThreads());
      this.doConfig(builder, moduleId, (String)null);
   }

   public void config(ConcurrentManagedObjectBuilder builder, ManagedThreadFactoryBean bean, String moduleId) {
      builder.name(bean.getName());
      builder.priority(bean.getPriority());
      this.createMTFDaemonThreadManager(builder, bean.getMaxConcurrentNewThreads());
      this.doConfig(builder, moduleId, (String)null);
   }

   private void createExecutorDaemonThreadManager(ConcurrentManagedObjectBuilder builder, int maxThreads) {
      ExecutorDaemonThreadManagerImpl daemonThreadManager = new ExecutorDaemonThreadManagerImpl(builder.getName(), maxThreads, this.partitionName);
      builder.daemonThreadManager(daemonThreadManager);
   }

   private void createMTFDaemonThreadManager(ConcurrentManagedObjectBuilder builder, int maxThreads) {
      MTFDaemonThreadManagerImpl daemonThreadManager = new MTFDaemonThreadManagerImpl(builder.getName(), maxThreads, this.partitionName);
      builder.daemonThreadManager(daemonThreadManager);
   }
}
