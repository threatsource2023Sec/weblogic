package weblogic.server.channels;

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
import weblogic.protocol.ServerChannelManager;
import weblogic.protocol.configuration.ChannelHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServiceFailureException;

@Service
@Interceptor
@ContractsProvided({ChannelServicePartitionLifecycleInterceptor.class, MethodInterceptor.class})
@ServerServiceInterceptor(ChannelService.class)
public class ChannelServicePartitionLifecycleInterceptor extends PartitionManagerInterceptorAdapter {
   private static AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static ComponentInvocationContextManager CICM;

   public void startPartitionInAdmin(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (ChannelHelper.DEBUG) {
         ChannelHelper.p("ChannelServicePartitionLifecycleInterceptor.startPartitionInAdmin(" + partitionName + "), current cic = " + CICM.getCurrentComponentInvocationContext());
      }

      validateCIC(partitionName, "startPartitionInAdmin");
      methodInvocation.proceed();
   }

   public void resumePartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (ChannelHelper.DEBUG) {
         ChannelHelper.p("ChannelServicePartitionLifecycleInterceptor.resumePartition(" + partitionName + "), current cic = " + CICM.getCurrentComponentInvocationContext());
      }

      validateCIC(partitionName, "resumePartition");
      this.startPartition(methodInvocation, partitionName);
   }

   public void startPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (ChannelHelper.DEBUG) {
         ChannelHelper.p("ChannelServicePartitionLifecycleInterceptor.startPartition(" + partitionName + "), current cic = " + CICM.getCurrentComponentInvocationContext());
      }

      validateCIC(partitionName, "startPartition");
      ChannelService cs = (ChannelService)ServerChannelManager.getServerChannelManager();
      cs.createPartitionServerChannels(partitionName);
      methodInvocation.proceed();
   }

   public void suspendPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions) throws Throwable {
      if (ChannelHelper.DEBUG) {
         ChannelHelper.p("ChannelServicePartitionLifecycleInterceptor.suspendPartition(" + partitionName + ", timeout = " + timeout + "), current cic = " + CICM.getCurrentComponentInvocationContext());
      }

      validateCIC(partitionName, "suspendPartition");
      methodInvocation.proceed();
   }

   public void shutdownPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws Throwable {
      if (ChannelHelper.DEBUG) {
         ChannelHelper.p("ChannelServicePartitionLifecycleInterceptor.shutdownPartition(" + partitionName + ", timeout = " + timeout + "), current cic = " + CICM.getCurrentComponentInvocationContext());
      }

      validateCIC(partitionName, "shutdownPartition");
      methodInvocation.proceed();
      ChannelService cs = (ChannelService)ServerChannelManager.getServerChannelManager();
      cs.removePartitionServerChannels(partitionName, false);
   }

   public void forceSuspendPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (ChannelHelper.DEBUG) {
         ChannelHelper.p("ChannelServicePartitionLifecycleInterceptor.forceSuspendPartition(" + partitionName + "), current cic = " + CICM.getCurrentComponentInvocationContext());
      }

      validateCIC(partitionName, "forceSuspendPartition");
      methodInvocation.proceed();
   }

   public void forceShutdownPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (ChannelHelper.DEBUG) {
         ChannelHelper.p("ChannelServicePartitionLifecycleInterceptor.forceShutdownPartition(" + partitionName + "), current cic = " + CICM.getCurrentComponentInvocationContext());
      }

      validateCIC(partitionName, "forceShutdownPartition");
      methodInvocation.proceed();
      ChannelService cs = (ChannelService)ServerChannelManager.getServerChannelManager();
      cs.removePartitionServerChannels(partitionName, true);
   }

   private static void validateCIC(String partitionName, String method) throws ServiceFailureException {
      ComponentInvocationContext cic = CICM.getCurrentComponentInvocationContext();
      if (!cic.getPartitionName().equals(partitionName)) {
         throw new ServiceFailureException("Mismatched current invocation partition [" + cic + "], expected partition name " + partitionName + " on " + method + "() in ChannelServicePartitionLifecycleInterceptor");
      }
   }

   static {
      CICM = ComponentInvocationContextManager.getInstance(KERNEL_ID);
   }
}
