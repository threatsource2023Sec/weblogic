package weblogic.jms.dispatcher;

import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.jms.JMSEnvironment;
import weblogic.messaging.common.JMSCICHelper;
import weblogic.messaging.dispatcher.DispatcherId;

public final class ClientDispatcherManager implements InitDispatcherManager {
   private final JMSEnvironment jmsEnvironment;

   public ClientDispatcherManager(JMSDispatcherManager jmsDispatcherManager, JMSEnvironment jmsEnvironment) {
      this.jmsEnvironment = jmsEnvironment;
   }

   public void init() {
      this.jmsEnvironment.createJmsJavaOid();
   }

   public int abstractThreadPoolSize(boolean oldExecuteQueueStyle) {
      int jmsThreadPoolSize = 0;
      if (this.jmsEnvironment.isThinClient()) {
         String poolSize = null;

         try {
            poolSize = System.getProperty("weblogic.JMSThreadPoolSize");
         } catch (SecurityException var6) {
         }

         if (poolSize != null) {
            try {
               jmsThreadPoolSize = Integer.parseInt(poolSize);
            } catch (NumberFormatException var5) {
            }

            if (jmsThreadPoolSize < 5 && jmsThreadPoolSize > 0) {
               jmsThreadPoolSize = 5;
            }
         }
      } else {
         jmsThreadPoolSize = JMSDispatcherManager.getJMSThreadPoolSize();
      }

      return jmsThreadPoolSize;
   }

   public ManagedInvocationContext pushCIC(ComponentInvocationContext cic) {
      ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC((ComponentInvocationContext)null);
      return mic;
   }

   public boolean isServer() {
      return false;
   }

   public String getObjectHandlerClassName() {
      return "weblogic.jms.dispatcher.ClientDispatcherObjectHandler";
   }

   public DispatcherId getLocalDispatcherId() {
      return this.jmsEnvironment.getLocalDispatcherId();
   }

   public boolean isServerLocalRJVM(weblogic.messaging.dispatcher.DispatcherRemote dispatcherRemote) {
      return false;
   }
}
