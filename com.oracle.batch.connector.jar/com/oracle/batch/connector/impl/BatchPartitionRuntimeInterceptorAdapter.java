package com.oracle.batch.connector.impl;

import java.security.AccessController;
import javax.annotation.PostConstruct;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ManagementException;
import weblogic.management.configuration.util.PartitionManagerInterceptorAdapter;
import weblogic.management.configuration.util.PartitionManagerPartitionAPI;
import weblogic.management.configuration.util.Setup;
import weblogic.management.configuration.util.Teardown;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.BatchJobRepositoryRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

@Service
@Setup
@Teardown
@Interceptor
@PartitionManagerPartitionAPI
@ContractsProvided({MethodInterceptor.class, BatchPartitionRuntimeInterceptorAdapter.class})
public class BatchPartitionRuntimeInterceptorAdapter extends PartitionManagerInterceptorAdapter {
   private static final DebugLogger _debugLogger = DebugLogger.getDebugLogger("DebugBatchConnector");
   private RuntimeAccess runtimeAccess;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   @PostConstruct
   private void initialize() {
      if (ManagementService.getRuntimeAccess(kernelId) != null) {
         this.runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      }

   }

   public void startPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      methodInvocation.proceed();
      this.createPartitionJobRepositoryRuntimeMBean(partitionName);
   }

   public void startPartitionInAdmin(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      methodInvocation.proceed();
      this.createPartitionJobRepositoryRuntimeMBean(partitionName);
   }

   public void resumePartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      methodInvocation.proceed();
      this.createPartitionJobRepositoryRuntimeMBean(partitionName);
   }

   public void suspendPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions) throws Throwable {
      methodInvocation.proceed();
      this.removePartitionJobRepositoryRuntimeMBean(partitionName);
   }

   public void forceSuspendPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      methodInvocation.proceed();
      this.removePartitionJobRepositoryRuntimeMBean(partitionName);
   }

   public void shutdownPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws Throwable {
      methodInvocation.proceed();
      this.removePartitionJobRepositoryRuntimeMBean(partitionName);
   }

   public void forceShutdownPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      methodInvocation.proceed();
      this.removePartitionJobRepositoryRuntimeMBean(partitionName);
   }

   private void createPartitionJobRepositoryRuntimeMBean(String partitionName) throws ManagementException {
      try {
         if (this.runtimeAccess != null) {
            ServerRuntimeMBean serverRuntimeMBean = this.runtimeAccess.getServerRuntime();
            if (serverRuntimeMBean != null) {
               PartitionRuntimeMBean partitionRuntimeMBean = serverRuntimeMBean.lookupPartitionRuntime(partitionName);
               if (partitionRuntimeMBean != null && partitionRuntimeMBean.getBatchJobRepositoryRuntime() == null) {
                  BatchJobRepositoryRuntimeMBeanImpl batchJobRepositoryRuntimeMBean = new BatchJobRepositoryRuntimeMBeanImpl(partitionRuntimeMBean);
                  if (_debugLogger.isDebugEnabled()) {
                     _debugLogger.debug("** PartitionLifecycle.name: partition name = " + partitionRuntimeMBean.getName() + "; partition id = " + partitionRuntimeMBean.getPartitionID() + "; partition state = " + partitionRuntimeMBean.getState() + "; mbean: " + batchJobRepositoryRuntimeMBean);
                  }
               }
            }
         }
      } catch (Exception var5) {
         _debugLogger.debug("** createPartitionJobRepositoryRuntimeMBean: Threw exception", var5);
      }

   }

   private void removePartitionJobRepositoryRuntimeMBean(String partitionName) throws ManagementException {
      try {
         if (this.runtimeAccess != null) {
            ServerRuntimeMBean serverRuntimeMBean = this.runtimeAccess.getServerRuntime();
            if (serverRuntimeMBean != null) {
               PartitionRuntimeMBean partitionRuntimeMBean = serverRuntimeMBean.lookupPartitionRuntime(partitionName);
               if (partitionRuntimeMBean != null && partitionRuntimeMBean.getBatchJobRepositoryRuntime() != null) {
                  partitionRuntimeMBean.setBatchJobRepositoryRuntime((BatchJobRepositoryRuntimeMBean)null);
                  if (_debugLogger.isDebugEnabled()) {
                     _debugLogger.debug("** [removePartitionJobRepositoryRuntimeMBean] PartitionLifecycle.name: partition name = " + partitionRuntimeMBean.getName() + "; partition id = " + partitionRuntimeMBean.getPartitionID() + "; partition state = " + partitionRuntimeMBean.getState());
                  }
               }
            }
         }
      } catch (Exception var4) {
         _debugLogger.debug("** createPartitionJobRepositoryRuntimeMBean: Threw exception", var4);
      }

   }
}
