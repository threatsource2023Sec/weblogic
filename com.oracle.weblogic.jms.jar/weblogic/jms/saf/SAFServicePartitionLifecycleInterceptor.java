package weblogic.jms.saf;

import java.security.AccessController;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.jms.common.JMSDebug;
import weblogic.management.configuration.util.PartitionManagerInterceptorAdapter;
import weblogic.management.configuration.util.ServerServiceInterceptor;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServiceFailureException;

@Service
@Interceptor
@ContractsProvided({SAFServicePartitionLifecycleInterceptor.class, MethodInterceptor.class})
@ServerServiceInterceptor(SAFService.class)
public class SAFServicePartitionLifecycleInterceptor extends PartitionManagerInterceptorAdapter {
   private static AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static ComponentInvocationContextManager CICM;

   public void startPartitionInAdmin(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("SAFServicePartitionLifecycleInterceptor.startPartitionInAdmin(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      this.startPartition(methodInvocation, partitionName);
   }

   public void resumePartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("SAFServicePartitionLifecycleInterceptor.resumePartition(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "resumePartition");
      methodInvocation.proceed();
   }

   public void startPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("SAFServicePartitionLifecycleInterceptor.startPartition(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      this.startSAFService(partitionName);
      methodInvocation.proceed();
   }

   public void suspendPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions) throws Throwable {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("ENTER SAFServicePartitionLifecycleInterceptor.suspendPartition(" + partitionName + ", timeout=" + timeout + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "suspendPartition");
      methodInvocation.proceed();
   }

   public void forceSuspendPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("ENTER SAFServicePartitionLifecycleInterceptor.forceSuspendPartition(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "forceSuspendPartition");
      methodInvocation.proceed();
   }

   public void shutdownPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws Throwable {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("ENTER SAFServicePartitionLifecycleInterceptor.shutdownPartition(" + partitionName + ", timeout=" + timeout + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "shutdownPartition");
      methodInvocation.proceed();
      this.shutdownSAFService(partitionName, false);
   }

   public void forceShutdownPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("ENTER SAFServicePartitionLifecycleInterceptor.forceShutdownPartition(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "forceShutdownPartition");
      methodInvocation.proceed();
      this.shutdownSAFService(partitionName, true);
   }

   private void startSAFService(String partitionName) throws Throwable {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("SETUP_SAFServicePartitionLifecycleInterceptor.startSAFService(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "startSAFService");
      SAFService.getSAFService();
   }

   private void shutdownSAFService(String partitionName, boolean force) throws ServiceFailureException {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("TEARDOWN_SAFServicePartitionLifecycleInterceptor.shutdownSAFService(" + partitionName + ", force=" + force + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "shutdownSAFService");
      SAFService service = SAFService.removeSAFService(partitionName);
      if (service != null) {
         service.stop();
      }

   }

   private static void checkCIC(String partitionName, String method) throws ServiceFailureException {
      ComponentInvocationContext cic = CICM.getCurrentComponentInvocationContext();
      if (!cic.getPartitionName().equals(partitionName)) {
         throw new ServiceFailureException("Mismatched current invocation partition [" + cic + "], expected partition name " + partitionName + " on " + method + "() in SAFServicePartitionLifecycleInterceptor");
      }
   }

   static {
      CICM = ComponentInvocationContextManager.getInstance(KERNEL_ID);
   }
}
