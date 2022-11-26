package weblogic.connector.common;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.monitoring.ServiceRuntimeMBeanImpl;
import weblogic.connector.utils.PartitionUtils;
import weblogic.management.ManagementException;
import weblogic.management.configuration.util.PartitionManagerInterceptorAdapter;
import weblogic.management.configuration.util.ServerServiceInterceptor;
import weblogic.management.runtime.ConnectorServiceRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.server.ServiceFailureException;

@Service
@Interceptor
@ContractsProvided({ConnectorServiceInterceptor.class, MethodInterceptor.class})
@ServerServiceInterceptor(ConnectorServiceActivator.class)
public class ConnectorServiceInterceptor extends PartitionManagerInterceptorAdapter {
   public void startPartitionInAdmin(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      Debug.service("ConnectorServiceInterceptor.startPartitionInAdmin(" + partitionName + "), current cic=" + PartitionUtils.getCurrentCICInfo());
      checkCIC(partitionName, "startPartitionInAdmin");
      this.createConnectorRuntimeMBean(partitionName, methodInvocation);
      methodInvocation.proceed();
   }

   public void startPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      Debug.service("ConnectorServiceInterceptor.startPartition(" + partitionName + "), current cic=" + PartitionUtils.getCurrentCICInfo());
      checkCIC(partitionName, "startPartition");
      this.createConnectorRuntimeMBean(partitionName, methodInvocation);
      methodInvocation.proceed();
   }

   private void createConnectorRuntimeMBean(String partitionName, MethodInvocation methodInvocation) throws ManagementException, ServiceFailureException {
      PartitionRuntimeMBean partitionRuntime = (PartitionRuntimeMBean)this.getPartitionRuntime(methodInvocation);
      ServiceRuntimeMBeanImpl connectorMbean = null;
      if (partitionRuntime == null) {
         String msg = ConnectorLogger.logPartitionRuntimeMBeanNotFoundException(partitionName);
         throw new ManagementException(msg);
      } else {
         synchronized(partitionRuntime) {
            connectorMbean = (ServiceRuntimeMBeanImpl)partitionRuntime.getConnectorServiceRuntime();
            if (connectorMbean == null) {
               Debug.service("Create ConnectorServiceRuntimeMBean for the partition: " + partitionName);
               connectorMbean = new ServiceRuntimeMBeanImpl();
               partitionRuntime.setConnectorServiceRuntime(connectorMbean);
            } else {
               throw new ServiceFailureException("ConnectorServiceRuntimeMBean for partition " + partitionName + " has already been set");
            }
         }
      }
   }

   public void resumePartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      Debug.service("ConnectorServiceInterceptor.resumePartition(" + partitionName + "), current cic=" + PartitionUtils.getCurrentCICInfo());
      checkCIC(partitionName, "resumePartition");
      methodInvocation.proceed();
   }

   public void suspendPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions) throws Throwable {
      Debug.service("ConnectorServiceInterceptor.suspendPartition(" + partitionName + "), current cic=" + PartitionUtils.getCurrentCICInfo());
      checkCIC(partitionName, "suspendPartition");
      methodInvocation.proceed();
   }

   public void forceShutdownPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      Debug.service("ConnectorServiceInterceptor.forceShutdownPartition(" + partitionName + "), current cic=" + PartitionUtils.getCurrentCICInfo());
      checkCIC(partitionName, "forceShutdownPartition");
      methodInvocation.proceed();
      this.shutdownConnectorService(partitionName, methodInvocation);
   }

   public void shutdownPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws Throwable {
      Debug.service("ConnectorServiceInterceptor.shutdownPartition(" + partitionName + "), current cic=" + PartitionUtils.getCurrentCICInfo());
      checkCIC(partitionName, "shutdownPartition");
      methodInvocation.proceed();
      this.shutdownConnectorService(partitionName, methodInvocation);
   }

   private void shutdownConnectorService(String partitionName, MethodInvocation methodInvocation) throws ManagementException {
      PartitionRuntimeMBean partitionRuntime = (PartitionRuntimeMBean)this.getPartitionRuntime(methodInvocation);
      if (partitionRuntime == null) {
         String msg = ConnectorLogger.logPartitionRuntimeMBeanNotFoundException(partitionName);
         throw new ManagementException(msg);
      } else {
         try {
            synchronized(partitionRuntime) {
               ServiceRuntimeMBeanImpl connectorService = (ServiceRuntimeMBeanImpl)partitionRuntime.getConnectorServiceRuntime();
               if (connectorService != null) {
                  connectorService.unregister();
                  partitionRuntime.setConnectorServiceRuntime((ConnectorServiceRuntimeMBean)null);
               }

            }
         } catch (ManagementException var8) {
            Debug.logConnectorServiceShutdownError(var8.toString());
            throw new ManagementException(var8);
         }
      }
   }

   private static void checkCIC(String partitionName, String method) throws ServiceFailureException {
      String currentPartitionName = PartitionUtils.getPartitionName();
      if (!currentPartitionName.equals(partitionName)) {
         throw new ServiceFailureException("Mismatched current invocation partition [" + PartitionUtils.getCurrentCICInfo() + "], expected partition name " + partitionName + " on " + method + "() in ConnectorServiceInterceptor");
      }
   }
}
