package weblogic.jms;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.Iterator;
import java.util.List;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.migration.MigrationManager;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.jms.common.JMSDebug;
import weblogic.management.configuration.util.PartitionManagerInterceptorAdapter;
import weblogic.management.configuration.util.ServerServiceInterceptor;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServiceFailureException;

@Service
@Interceptor
@ContractsProvided({JMSServicePostDeploymentPartitionLifecycleInterceptor.class, MethodInterceptor.class})
@ServerServiceInterceptor(JMSServicePostDeploymentImpl.class)
public class JMSServicePostDeploymentPartitionLifecycleInterceptor extends PartitionManagerInterceptorAdapter {
   private static AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static ComponentInvocationContextManager CICM;

   public void startPartitionInAdmin(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("JMSServicePostDeploymentPartitionLifecycleInterceptor.startPartitionInAdmin(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "startPartitionInAdmin");
      methodInvocation.proceed();
   }

   public void resumePartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("ENTER JMSServicePostDeploymentPartitionLifecycleInterceptor.resumePartition(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      this.startPartition(methodInvocation, partitionName);
   }

   public void startPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("ENTER JMSServicePostDeploymentPartitionLifecycleInterceptor.startPartition(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "startPartition");
      methodInvocation.proceed();
      this.startPostDeployments(partitionName);

      try {
         List mgs = ((MigrationManager)GlobalServiceLocator.getServiceLocator().getService(MigrationManager.class, new Annotation[0])).getMigratableTargetsMarkedNotReadyToActivate();
         if (mgs != null) {
            Iterator var4 = mgs.iterator();

            while(var4.hasNext()) {
               String s = (String)var4.next();
               ((MigrationManager)GlobalServiceLocator.getServiceLocator().getService(MigrationManager.class, new Annotation[0])).markMigratableTargetReadyToActivate(s);
            }
         }

      } catch (MultiException | IllegalStateException var6) {
         throw new ServiceFailureException(var6);
      }
   }

   public void suspendPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions) throws Throwable {
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("JMSServicePostDeploymentPartitionLifecycleInterceptor.suspendPartition(" + partitionName + ", timeout=" + timeout + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "suspendPartition");
      methodInvocation.proceed();
   }

   public void forceSuspendPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("JMSServicePostDeploymentPartitionLifecycleInterceptor.forceSuspendPartition(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "forceSuspendPartition");
      methodInvocation.proceed();
   }

   public void shutdownPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws Throwable {
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("JMSServicePostDeploymentPartitionLifecycleInterceptor.shutdownPartition(" + partitionName + ", timeout=" + timeout + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      this.stopPostDeployments(partitionName, false);
      methodInvocation.proceed();
   }

   public void forceShutdownPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("JMSServicePostDeploymentPartitionLifecycleInterceptor.forceShutdownPartition(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      this.stopPostDeployments(partitionName, true);
      methodInvocation.proceed();
   }

   private void startPostDeployments(String partitionName) throws Throwable {
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("SETUP_JMSServicePostDeploymentPartitionLifecycleInterceptor.startPostDeployments(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "startPostDeployments");
      JMSService service = JMSService.getJMSServiceWithPartitionName(partitionName);
      if (service != null) {
         service.getBEDeployer().postDeploymentsStart();
      }

   }

   private void stopPostDeployments(String partitionName, boolean force) throws Throwable {
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("TEARDOWN_JMSServicePostDeploymentPartitionLifecycleInterceptor.stopPostDeployments(" + partitionName + ", force=" + force + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "stopPostDeployments");
      JMSService service = JMSService.getJMSServiceWithPartitionName(partitionName);
      if (service != null) {
         service.getBEDeployer().postDeploymentsStop();
      }

   }

   private static void checkCIC(String partitionName, String method) throws ServiceFailureException {
      ComponentInvocationContext cic = CICM.getCurrentComponentInvocationContext();
      if (!cic.getPartitionName().equals(partitionName)) {
         throw new ServiceFailureException("Mismatched current invocation partition [" + cic + "], expected partition name " + partitionName + " on " + method + "() in JMSServicePostDeploymentPartitionLifecycleInterceptor");
      }
   }

   static {
      CICM = ComponentInvocationContextManager.getInstance(KERNEL_ID);
   }
}
