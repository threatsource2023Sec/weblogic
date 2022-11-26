package weblogic.jms.dispatcher;

import java.security.AccessController;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.jms.JMSEnvironment;
import weblogic.jms.common.JMSDebug;
import weblogic.messaging.common.JMSCICHelper;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.messaging.dispatcher.DispatcherProxy;
import weblogic.rjvm.JVMID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class ServerDispatcherManager implements InitDispatcherManager {
   private final JMSEnvironment jmsEnvironment;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public ServerDispatcherManager(JMSEnvironment jmsEnvironment) {
      this.jmsEnvironment = jmsEnvironment;
   }

   public int abstractThreadPoolSize(boolean oldExecuteQueueStyle) {
      int jmsThreadPoolSize;
      if (oldExecuteQueueStyle) {
         jmsThreadPoolSize = JMSDispatcherManager.getJMSThreadPoolSize();
         if (jmsThreadPoolSize < 5) {
            jmsThreadPoolSize = 5;
         }
      } else {
         jmsThreadPoolSize = 1;
      }

      return jmsThreadPoolSize;
   }

   public ManagedInvocationContext pushCIC(ComponentInvocationContext cic) {
      ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(cic);
      return mic;
   }

   public boolean isServer() {
      return true;
   }

   public String getObjectHandlerClassName() {
      return "weblogic.jms.dispatcher.DispatcherObjectHandler";
   }

   public DispatcherId getLocalDispatcherId() {
      return this.jmsEnvironment.getLocalDispatcherId();
   }

   public boolean isServerLocalRJVM(weblogic.messaging.dispatcher.DispatcherRemote dispatcherRemote) {
      if (dispatcherRemote instanceof DispatcherProxy) {
         JVMID jvmid = ((DispatcherProxy)dispatcherRemote).getRJVM().getID();
         boolean isLocal = jvmid.isLocal();
         if (JMSDebug.JMSDispatherUtilsVerbose.isDebugEnabled()) {
            JMSDebug.JMSDispatherUtilsVerbose.debug("debug ServerDispatcherManager.isServerLocalRJVM " + isLocal + " from " + jvmid + " on:" + this.getLocalDispatcherId() + dispatcherRemote.getClass().getSimpleName());
         }

         return isLocal;
      } else {
         if (JMSDebug.JMSDispatherUtilsVerbose.isDebugEnabled()) {
            JMSDebug.JMSDispatherUtilsVerbose.debug("debug ServerDispatcherManager.isServerLocalRJVM default true on:" + this.getLocalDispatcherId() + " " + dispatcherRemote.getClass().getSimpleName());
         }

         return true;
      }
   }

   public DispatcherPartitionContext createDispatcherPartitionContext(String partitionId, String partitionName, boolean oldExecuteQueueStyle, InvocableManagerDelegate invocableManagerDelegate, ComponentInvocationContext componentInvocationContext) {
      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         JMSDebug.JMSCommon.debug("ServerDispatcherManager cic " + this.debugCIC(componentInvocationContext));
      }

      return JMSDispatcherManager.getRawSingleton().createDispatcherPartitionContext(partitionId, partitionName, oldExecuteQueueStyle, this, invocableManagerDelegate, componentInvocationContext);
   }

   private String debugCIC(ComponentInvocationContext cic) {
      return cic + " id-class " + (cic != null ? System.identityHashCode(cic) + cic.getClass().getName() : "Nil");
   }
}
