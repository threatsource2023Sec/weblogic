package com.oracle.batch.connector.impl;

import com.ibm.jbatch.spi.TaggedJobExecution;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.JobInstance;
import javax.batch.runtime.StepExecution;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.management.ManagementException;
import weblogic.management.runtime.BatchJobRepositoryException;
import weblogic.management.runtime.BatchJobRepositoryRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.ServerRuntimeMBean;

public class BatchJobRepositoryRuntimeMBeanImpl extends RuntimeMBeanDelegate implements BatchJobRepositoryRuntimeMBean {
   private String partitionName;

   public BatchJobRepositoryRuntimeMBeanImpl(ServerRuntimeMBean serverRuntimeMBean) throws ManagementException {
      this.partitionName = "DOMAIN";
      serverRuntimeMBean.setBatchJobRepositoryRuntime(this);
   }

   public BatchJobRepositoryRuntimeMBeanImpl(PartitionRuntimeMBean partitionRuntimeMBean) throws ManagementException {
      super(partitionRuntimeMBean.getName());
      this.partitionName = partitionRuntimeMBean.getName();
      partitionRuntimeMBean.setBatchJobRepositoryRuntime(this);
   }

   public Collection getJobDetails() throws BatchJobRepositoryException {
      new ArrayList();
      ComponentInvocationContext cic = null;

      Collection result;
      try {
         BatchConfigBeanHelper.markCallerAdminPrefix(this.partitionName);
         if (this.partitionName != null) {
            ComponentInvocationContextManager cicMgr = ComponentInvocationContextManager.getInstance();
            cic = cicMgr.createComponentInvocationContext(this.partitionName);
            ManagedInvocationContext mic = cicMgr.setCurrentComponentInvocationContext(cic);
            Throwable var5 = null;

            try {
               result = this.getJobDetails(cic);
            } catch (Throwable var21) {
               var5 = var21;
               throw var21;
            } finally {
               if (mic != null) {
                  if (var5 != null) {
                     try {
                        mic.close();
                     } catch (Throwable var20) {
                        var5.addSuppressed(var20);
                     }
                  } else {
                     mic.close();
                  }
               }

            }
         } else {
            result = this.getJobDetails(cic);
         }
      } finally {
         BatchConfigBeanHelper.resetCallerAnAdmin();
      }

      return result;
   }

   private Collection getJobDetails(ComponentInvocationContext cic) throws BatchJobRepositoryException {
      Collection result = new ArrayList();

      try {
         JobOperator jobOperator = BatchRuntime.getJobOperator();
         Iterator var4 = jobOperator.getJobNames().iterator();

         while(var4.hasNext()) {
            String jobName = (String)var4.next();
            Iterator var6 = jobOperator.getJobInstances(jobName, 0, Integer.MAX_VALUE).iterator();

            while(var6.hasNext()) {
               JobInstance jobInstance = (JobInstance)var6.next();
               Iterator var8 = jobOperator.getJobExecutions(jobInstance).iterator();

               while(var8.hasNext()) {
                  JobExecution jobExecution = (JobExecution)var8.next();
                  if (this.checkIfVisible(cic, jobExecution)) {
                     List data = new ArrayList();
                     data.add(jobName);
                     if (jobExecution instanceof TaggedJobExecution) {
                        data.add(this.extractApplicationName(cic, jobExecution));
                     } else {
                        data.add(jobExecution.getJobName());
                     }

                     data.add(Long.toString(jobInstance.getInstanceId()));
                     data.add(Long.toString(jobExecution.getExecutionId()));
                     data.add(jobExecution.getBatchStatus() != null ? jobExecution.getBatchStatus().toString() : " ");
                     data.add(jobExecution.getStartTime() != null ? jobExecution.getStartTime().toString() : " ");
                     data.add(jobExecution.getEndTime() != null ? jobExecution.getEndTime().toString() : " ");
                     data.add(jobExecution.getExitStatus() != null ? jobExecution.getExitStatus().toString() : " ");
                     result.add(data.toArray(new String[data.size()]));
                  }
               }
            }
         }

         return result;
      } catch (Throwable var11) {
         throw new BatchJobRepositoryException(var11);
      }
   }

   public Collection getJobExecutions(long instanceId) throws BatchJobRepositoryException {
      new ArrayList();
      ComponentInvocationContext cic = null;

      Collection result;
      try {
         BatchConfigBeanHelper.markCallerAdminPrefix(this.partitionName);
         if (this.partitionName != null) {
            ComponentInvocationContextManager cicMgr = ComponentInvocationContextManager.getInstance();
            cic = cicMgr.createComponentInvocationContext(this.partitionName);
            ManagedInvocationContext mic = cicMgr.setCurrentComponentInvocationContext(cic);
            Throwable var7 = null;

            try {
               result = this.getJobExecutions(instanceId, cic);
            } catch (Throwable var23) {
               var7 = var23;
               throw var23;
            } finally {
               if (mic != null) {
                  if (var7 != null) {
                     try {
                        mic.close();
                     } catch (Throwable var22) {
                        var7.addSuppressed(var22);
                     }
                  } else {
                     mic.close();
                  }
               }

            }
         } else {
            result = this.getJobExecutions(instanceId, cic);
         }
      } finally {
         BatchConfigBeanHelper.resetCallerAnAdmin();
      }

      return result;
   }

   private Collection getJobExecutions(long instanceId, ComponentInvocationContext cic) throws BatchJobRepositoryException {
      Collection result = new ArrayList();

      try {
         JobOperator jobOperator = BatchRuntime.getJobOperator();
         JobInstance jobInstance = jobOperator.getJobInstance(instanceId);
         if (jobInstance != null) {
            Iterator var7 = jobOperator.getJobExecutions(jobInstance).iterator();

            while(var7.hasNext()) {
               JobExecution jobExecution = (JobExecution)var7.next();
               if (this.checkIfVisible(cic, jobExecution)) {
                  List data = new ArrayList();
                  data.add(jobExecution.getJobName());
                  data.add(Long.toString(jobInstance.getInstanceId()));
                  data.add(Long.toString(jobExecution.getExecutionId()));
                  data.add(jobExecution.getBatchStatus() != null ? jobExecution.getBatchStatus().toString() : " ");
                  data.add(jobExecution.getStartTime() != null ? jobExecution.getStartTime().toString() : " ");
                  data.add(jobExecution.getEndTime() != null ? jobExecution.getEndTime().toString() : " ");
                  data.add(jobExecution.getExitStatus() != null ? jobExecution.getExitStatus().toString() : " ");
                  result.add(data.toArray(new String[data.size()]));
               }
            }
         }

         return result;
      } catch (Throwable var10) {
         throw new BatchJobRepositoryException(var10);
      }
   }

   public Collection getStepExecutions(long executionId) throws BatchJobRepositoryException {
      new ArrayList();
      ComponentInvocationContext cic = null;

      Collection result;
      try {
         BatchConfigBeanHelper.markCallerAdminPrefix(this.partitionName);
         if (this.partitionName != null) {
            ComponentInvocationContextManager cicMgr = ComponentInvocationContextManager.getInstance();
            cic = cicMgr.createComponentInvocationContext(this.partitionName);
            ManagedInvocationContext mic = cicMgr.setCurrentComponentInvocationContext(cic);
            Throwable var7 = null;

            try {
               result = this.getStepExecutions(executionId, cic);
            } catch (Throwable var23) {
               var7 = var23;
               throw var23;
            } finally {
               if (mic != null) {
                  if (var7 != null) {
                     try {
                        mic.close();
                     } catch (Throwable var22) {
                        var7.addSuppressed(var22);
                     }
                  } else {
                     mic.close();
                  }
               }

            }
         } else {
            result = this.getStepExecutions(executionId, cic);
         }
      } finally {
         BatchConfigBeanHelper.resetCallerAnAdmin();
      }

      return result;
   }

   private Collection getStepExecutions(long executionId, ComponentInvocationContext cic) throws BatchJobRepositoryException {
      Collection result = new ArrayList();

      try {
         JobOperator jobOperator = BatchRuntime.getJobOperator();
         if (this.checkIfVisible(cic, jobOperator.getJobExecution(executionId))) {
            Iterator var6 = jobOperator.getStepExecutions(executionId).iterator();

            while(var6.hasNext()) {
               StepExecution se = (StepExecution)var6.next();
               List data = new ArrayList();
               data.add(se.getStepName());
               data.add(Long.toString(se.getStepExecutionId()));
               data.add(se.getBatchStatus() != null ? se.getBatchStatus().toString() : " ");
               data.add(se.getStartTime() != null ? se.getStartTime().toString() : " ");
               data.add(se.getEndTime() != null ? se.getEndTime().toString() : " ");
               data.add(se.getExitStatus() != null ? se.getExitStatus().toString() : " ");
               result.add(data.toArray(new String[data.size()]));
            }
         }

         return result;
      } catch (Throwable var9) {
         throw new BatchJobRepositoryException(var9);
      }
   }

   private boolean checkIfVisible(ComponentInvocationContext cic, JobExecution jobExecution) {
      if (jobExecution instanceof TaggedJobExecution) {
         String tag = ((TaggedJobExecution)jobExecution).getTagName();
         return WLSBatchSecurityHelper.extractPartitionId(tag).equals(cic.getPartitionId());
      } else {
         return false;
      }
   }

   private String extractApplicationName(ComponentInvocationContext cic, JobExecution jobExecution) {
      if (jobExecution instanceof TaggedJobExecution) {
         String tag = ((TaggedJobExecution)jobExecution).getTagName();
         return WLSBatchSecurityHelper.extractPartitionId(tag).equals(cic.getPartitionId()) ? WLSBatchSecurityHelper.extractApplicationName(tag) : null;
      } else {
         return null;
      }
   }
}
