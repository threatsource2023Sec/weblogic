package weblogic.messaging.saf.internal;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.configuration.util.PartitionManagerInterceptorAdapter;
import weblogic.management.configuration.util.ServerServiceInterceptor;
import weblogic.messaging.saf.common.SAFDebug;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServiceFailureException;

@Service
@Interceptor
@ContractsProvided({SAFServerServicePartitionLifecycleInterceptor.class, MethodInterceptor.class})
@ServerServiceInterceptor(SAFServerService.class)
public class SAFServerServicePartitionLifecycleInterceptor extends PartitionManagerInterceptorAdapter {
   private static AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static ComponentInvocationContextManager CICM;

   public void startPartitionInAdmin(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (SAFDebug.SAFAdmin.isDebugEnabled()) {
         SAFDebug.SAFAdmin.debug("SAFServerServicePartitionLifecycleInterceptor.startPartitionInAdmin(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      this.startPartition(methodInvocation, partitionName);
   }

   public void resumePartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (SAFDebug.SAFAdmin.isDebugEnabled()) {
         SAFDebug.SAFAdmin.debug("SAFServerServicePartitionLifecycleInterceptor.resumePartition(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "resumePartition");
      checkServerService();
      methodInvocation.proceed();
   }

   public void startPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (SAFDebug.SAFAdmin.isDebugEnabled()) {
         SAFDebug.SAFAdmin.debug("SAFServerServicePartitionLifecycleInterceptor.startPartition(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkServerService();
      this.startSAFServerService(partitionName);
      methodInvocation.proceed();
   }

   public void suspendPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions) throws Throwable {
      if (SAFDebug.SAFAdmin.isDebugEnabled()) {
         SAFDebug.SAFAdmin.debug("ENTER SAFServerServicePartitionLifecycleInterceptor.suspendPartition(" + partitionName + ", timeout=" + timeout + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "suspendPartition");
      methodInvocation.proceed();
   }

   public void forceSuspendPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (SAFDebug.SAFAdmin.isDebugEnabled()) {
         SAFDebug.SAFAdmin.debug("ENTER SAFServerServicePartitionLifecycleInterceptor.forceSuspendPartition(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "forceSuspendPartition");
      methodInvocation.proceed();
   }

   public void shutdownPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws Throwable {
      if (SAFDebug.SAFAdmin.isDebugEnabled()) {
         SAFDebug.SAFAdmin.debug("ENTER SAFServerServicePartitionLifecycleInterceptor.shutdownPartition(" + partitionName + ", timeout=" + timeout + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "shutdownPartition");
      checkServerService();
      methodInvocation.proceed();
      this.shutdownSAFServerService(partitionName, false);
   }

   public void forceShutdownPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (SAFDebug.SAFAdmin.isDebugEnabled()) {
         SAFDebug.SAFAdmin.debug("ENTER SAFServerServicePartitionLifecycleInterceptor.forceShutdownPartition(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "forceShutdownPartition");
      checkServerService();
      methodInvocation.proceed();
      this.shutdownSAFServerService(partitionName, true);
   }

   private void startSAFServerService(String partitionName) throws Throwable {
      if (SAFDebug.SAFAdmin.isDebugEnabled()) {
         SAFDebug.SAFAdmin.debug("SETUP_SAFServerServicePartitionLifecycleInterceptor.startSAFServerService(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "startSAFServerService");
      SAFServerService.getService();
   }

   private void shutdownSAFServerService(String partitionName, boolean force) throws ServiceFailureException {
      if (SAFDebug.SAFAdmin.isDebugEnabled()) {
         SAFDebug.SAFAdmin.debug("TEARDOWN_SAFServerServicePartitionLifecycleInterceptor.shutdownSAFServerService(" + partitionName + ", force=" + force + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "shutdownSAFServerService");
      SAFServerService service = SAFServerService.removeService(partitionName);
      if (service != null) {
         service.stop(force);
      }

   }

   private static void checkCIC(String partitionName, String method) throws ServiceFailureException {
      ComponentInvocationContext cic = CICM.getCurrentComponentInvocationContext();
      if (!cic.getPartitionName().equals(partitionName)) {
         throw new ServiceFailureException("Mismatched current invocation partition [" + cic + "], expected partition name " + partitionName + " on " + method + "() in SAFServerServicePartitionLifecycleInterceptor");
      }
   }

   private static void checkServerService() throws ServiceFailureException {
      SAFServerService service = (SAFServerService)GlobalServiceLocator.getServiceLocator().getService(SAFServerService.class, new Annotation[0]);
      if (!service.isStarted()) {
         throw new ServiceFailureException("Server service SAFServerService is not in running state");
      }
   }

   static {
      CICM = ComponentInvocationContextManager.getInstance(KERNEL_ID);
   }
}
