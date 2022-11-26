package com.oracle.batch.connector.impl;

import com.ibm.jbatch.spi.ExecutorServiceProvider;
import java.util.concurrent.ExecutorService;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.work.concurrent.ffapi.ConcurrentManagedObjectRegistry;

public class BatchJobExecutorService implements ExecutorServiceProvider {
   public ExecutorService getExecutorService() {
      ComponentInvocationContext cic = this.getCurrentComponentInvocationContext();
      String executorServiceName = cic != null && !cic.isGlobalRuntime() ? this.getPartitionScopedBatchExecutorServiceName(cic.getPartitionId()) : this.getDomainScopedBatchExecutorServiceName();
      return this.getExecutorServiceFromConcurrentManagedObjectRegistry(executorServiceName);
   }

   private ComponentInvocationContext getCurrentComponentInvocationContext() {
      return ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
   }

   private String getDomainScopedBatchExecutorServiceName() {
      return BatchConfigBeanHelper.getDomainMBean().getBatchJobsExecutorServiceName();
   }

   private String getPartitionScopedBatchExecutorServiceName(String partitionId) {
      DomainMBean domainMBean = BatchConfigBeanHelper.getDomainMBean();
      PartitionMBean partitionMBean = domainMBean.findPartitionByID(partitionId);
      return partitionMBean.getBatchJobsExecutorServiceName();
   }

   private ExecutorService getExecutorServiceFromConcurrentManagedObjectRegistry(String executorServiceName) {
      try {
         return ConcurrentManagedObjectRegistry.INSTANCE.lookupManagedExecutorService(executorServiceName);
      } catch (IllegalArgumentException var3) {
         return ConcurrentManagedObjectRegistry.INSTANCE.lookupManagedExecutorService((String)null);
      }
   }
}
