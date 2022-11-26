package weblogic.jms;

import java.security.AccessController;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.deployment.jms.JMSSessionPoolManager;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.PartitionUtils;
import weblogic.jms.extensions.JMSModuleHelper;
import weblogic.management.ManagementException;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.util.PartitionManagerInterceptorAdapter;
import weblogic.management.configuration.util.ServerServiceInterceptor;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean.State;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServiceFailureException;

@Service
@Interceptor
@ContractsProvided({JMSServicePartitionLifecycleInterceptor.class, MethodInterceptor.class})
@ServerServiceInterceptor(JMSServiceActivator.class)
public class JMSServicePartitionLifecycleInterceptor extends PartitionManagerInterceptorAdapter {
   public static final boolean PARTITION_LIFECYCLE_DEBUG = false;
   private static AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static ComponentInvocationContextManager CICM;
   private Object currentCFListenerLock = new Object();
   private ConnectionFactoryListener currentConnectionFactoryListener = null;

   public Object invoke(MethodInvocation methodInvocation) throws Throwable {
      String methodName = methodInvocation.getMethod().getName();
      if (methodName.equals("shutdownResourceGroup")) {
         return this.shutdownResourceGroup(methodInvocation);
      } else {
         return methodName.equals("forceShutdownResourceGroup") ? this.forceShutdownResourceGroup(methodInvocation) : super.invoke(methodInvocation);
      }
   }

   public void startPartitionInAdmin(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("JMSServicePartitionLifecycleInterceptor.startPartitionInAdmin(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      this.startPartition(methodInvocation, partitionName);
   }

   public void resumePartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("JMSServicePartitionLifecycleInterceptor.resumePartition(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "resumePartition");
      this.proceed(methodInvocation, partitionName, State.RESUMING);
   }

   public void startPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("JMSServicePartitionLifecycleInterceptor.startPartition(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      this.startJMSService(partitionName);
      this.proceed(methodInvocation, partitionName, State.STARTING);
   }

   public void suspendPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions) throws Throwable {
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("ENTER JMSServicePartitionLifecycleInterceptor.suspendPartition(" + partitionName + ", timeout=" + timeout + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "suspendPartition");
      this.proceed(methodInvocation, partitionName, State.SUSPENDING);
   }

   public void forceSuspendPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("ENTER JMSServicePartitionLifecycleInterceptor.forceSuspendPartition(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "forceSuspendPartition");
      this.proceed(methodInvocation, partitionName, State.FORCE_SUSPENDING, true);
   }

   public void shutdownPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws Throwable {
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("ENTER JMSServicePartitionLifecycleInterceptor.shutdownPartition(" + partitionName + ", timeout=" + timeout + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "shutdownPartition");
      this.proceed(methodInvocation, partitionName, State.SHUTTING_DOWN);
      this.shutdownJMSService(partitionName, false);
   }

   public void forceShutdownPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("ENTER JMSServicePartitionLifecycleInterceptor.forceShutdownPartition(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "forceShutdownPartition");
      this.proceed(methodInvocation, partitionName, State.FORCE_SHUTTING_DOWN, true);
      this.shutdownJMSService(partitionName, true);
   }

   private Object proceed(MethodInvocation methodInvocation, String partitionName, PartitionRuntimeMBean.State state) throws Throwable {
      return this.proceed(methodInvocation, partitionName, state, false);
   }

   private Object proceed(MethodInvocation methodInvocation, String partitionName, PartitionRuntimeMBean.State state, boolean force) throws Throwable {
      JMSService jmsService = JMSService.getJMSServiceWithPartitionName(partitionName);
      if (jmsService != null) {
         if (state != State.SHUTTING_DOWN && state != State.FORCE_SHUTTING_DOWN) {
            jmsService.setConnectionFactoryListener((ConnectionFactoryListener)null);
            this.setCurrentConnectionFactoryListener((ConnectionFactoryListener)null);
            jmsService = null;
         } else {
            PartitionMBean pmbean = this.getPartition(methodInvocation);
            ResourceGroupMBean rgmbean = this.getResourceGroup(methodInvocation);
            ConnectionFactoryListener l = new MyConnectionFactoryListener(pmbean, rgmbean, state, force);
            jmsService.setConnectionFactoryListener(l);
            this.setCurrentConnectionFactoryListener(l);
         }
      }

      Object var12;
      try {
         var12 = methodInvocation.proceed();
      } finally {
         if (jmsService != null) {
            this.setCurrentConnectionFactoryListener((ConnectionFactoryListener)null);
            jmsService.setConnectionFactoryListener((ConnectionFactoryListener)null);
         }

      }

      return var12;
   }

   private void startJMSService(String partitionName) throws Throwable {
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("SETUP_JMSServicePartitionLifecycleInterceptor.startJMSService(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "startJMSService");
      JMSLogger.logStartJMSServiceForPartition(partitionName);
      JMSService.createAndStartService();
   }

   private void shutdownJMSService(String partitionName, boolean force) throws ServiceFailureException {
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("TEARDOWN_JMSServicePartitionLifecycleInterceptor.shutdownJMSService(" + partitionName + ", force=" + force + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "shutdownJMSService");
      JMSLogger.logShutdownJMSServiceForPartition(partitionName);
      JMSService jmsService = JMSService.getJMSServiceWithPartitionName(partitionName);
      if (jmsService != null) {
         jmsService.stopPartitionWithNotification(force);
         JMSService.removeJMSService(partitionName);
      }

      JMSSessionPoolManager.removeSessionPoolManager(partitionName, force);
   }

   private static void checkCIC(String partitionName, String method) throws ServiceFailureException {
      ComponentInvocationContext cic = CICM.getCurrentComponentInvocationContext();
      if (!PartitionUtils.isSamePartition(cic.getPartitionName(), partitionName)) {
         throw new ServiceFailureException("Mismatched current invocation partition [" + cic + "], expected partition name " + partitionName + " on " + method + "() in JMSServicePartitionLifecycleInterceptor");
      }
   }

   private Object shutdownResourceGroup(MethodInvocation methodInvocation) throws Throwable {
      PartitionMBean pmbean = this.getPartition(methodInvocation);
      ResourceGroupMBean rgmbean = this.getResourceGroup(methodInvocation);
      if (rgmbean == null) {
         throw new ManagementException("ResourceGroupMBean is null on shutdownResourceGroup");
      } else {
         String partitionName = pmbean == null ? "DOMAIN" : pmbean.getName();
         String resourceGroupName = rgmbean.getName();
         checkCIC(partitionName, "shutdownResourceGroup");
         Object ret = this.proceed(methodInvocation, partitionName, State.SHUTTING_DOWN);
         JMSService jmsService = JMSService.getJMSServiceWithPartitionName(partitionName);
         if (jmsService != null) {
            jmsService.shutdownConnectionsForResourceGroup(rgmbean, false);
         }

         return ret;
      }
   }

   private Object forceShutdownResourceGroup(MethodInvocation methodInvocation) throws Throwable {
      PartitionMBean pmbean = this.getPartition(methodInvocation);
      ResourceGroupMBean rgmbean = this.getResourceGroup(methodInvocation);
      if (rgmbean == null) {
         throw new ManagementException("ResourceGroupMBean is null on forceShutdownResourceGroup");
      } else {
         String partitionName = pmbean == null ? "DOMAIN" : pmbean.getName();
         String resourceGroupName = rgmbean.getName();
         checkCIC(partitionName, "forceShutdownResourceGroup");
         Object ret = this.proceed(methodInvocation, partitionName, State.FORCE_SHUTTING_DOWN, true);
         JMSService jmsService = JMSService.getJMSServiceWithPartitionName(partitionName);
         if (jmsService != null) {
            jmsService.shutdownConnectionsForResourceGroup(rgmbean, true);
         }

         return ret;
      }
   }

   private void setCurrentConnectionFactoryListener(ConnectionFactoryListener o) {
      synchronized(this.currentCFListenerLock) {
         this.currentConnectionFactoryListener = o;
      }
   }

   static {
      CICM = ComponentInvocationContextManager.getInstance(KERNEL_ID);
   }

   private class MyConnectionFactoryListener implements ConnectionFactoryListener {
      private PartitionRuntimeMBean.State state;
      private PartitionMBean pmbean = null;
      private ResourceGroupMBean rgmbean = null;
      private boolean force = false;

      public MyConnectionFactoryListener(PartitionMBean pmbean, ResourceGroupMBean rgmbean, PartitionRuntimeMBean.State state, boolean force) {
         this.pmbean = pmbean;
         this.rgmbean = rgmbean;
         this.state = state;
         this.force = force;
      }

      public void onConnectionFactoryRemoval(String jndiName, WebLogicMBean deploymentScope) {
         synchronized(JMSServicePartitionLifecycleInterceptor.this.currentCFListenerLock) {
            if (JMSServicePartitionLifecycleInterceptor.this.currentConnectionFactoryListener != this) {
               return;
            }
         }

         if (JMSDebug.JMSConfig.isDebugEnabled()) {
            JMSDebug.JMSConfig.debug("JMSServicePartitionLifecycleInterceptor.onConnectionFactoryRemoval(jndiName=" + jndiName + ", deploymentScope=" + deploymentScope + ")[pmbean=" + this.pmbean + ", rgmbean=" + this.rgmbean + ", state=" + this.state + ", force=" + this.force + "]");
         }

         if (this.state == State.SHUTTING_DOWN || this.state == State.FORCE_SHUTTING_DOWN) {
            if (this.pmbean == null) {
               if (this.rgmbean == null) {
                  return;
               }

               if (!JMSModuleHelper.isScopeEqual(deploymentScope, this.rgmbean)) {
                  return;
               }
            }

            String partitionName = this.pmbean == null ? "DOMAIN" : this.pmbean.getName();
            if (JMSDebug.JMSConfig.isDebugEnabled()) {
               JMSDebug.JMSConfig.debug("JMSServicePartitionLifecycleInterceptor.onConnectionFactoryRemoval() destroySessionPool " + jndiName + ", on partition " + partitionName + (this.rgmbean == null ? "" : ", resource group " + this.rgmbean.getName()));
            }

            try {
               JMSSessionPoolManager.destroySessionPool(partitionName, jndiName, this.force);
            } catch (Exception var5) {
               if (JMSDebug.JMSConfig.isDebugEnabled()) {
                  JMSDebug.JMSConfig.debug("JMSServicePartitionLifecycleInterceptor.onConnectionFactoryRemoval(jndiName=" + jndiName + ", deploymentScope=" + deploymentScope + ")[state=" + this.state + "]: " + var5, var5);
               }
            }

         }
      }
   }
}
