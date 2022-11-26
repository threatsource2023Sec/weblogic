package weblogic.jms.common;

import weblogic.jms.JMSEnvironment;
import weblogic.kernel.KernelStatus;

public final class JMSWorkManager {
   public static final JMSWorkManager THE_ONE = new JMSWorkManager();
   public static final String JMS_EXECUTE_QUEUE_NAME = "JmsDispatcher";
   public static final String JMS_1WAY_EXECUTE_QUEUE_NAME = "JmsAsyncQueue";
   public static final String JMS_ASYNCSEND_EXECUTE_QUEUE_NAME = "JmsAsyncSend";
   private static final String dispatcherWorkManagerName;
   private static final String dispatcherOnewayWorkManagerName;
   private static final String jmsSessionOnMessageWMName;

   public static boolean isThinclient() {
      return JMSEnvironment.getJMSEnvironment().isThinClient();
   }

   public static String getJMSSessionOnMessageWMName() {
      return jmsSessionOnMessageWMName;
   }

   public static String getDispatcherWorkManagerName() {
      return dispatcherWorkManagerName;
   }

   public static String getDispatcherOnewayWorkManagerName() {
      return dispatcherOnewayWorkManagerName;
   }

   static {
      if (KernelStatus.isServer()) {
         dispatcherWorkManagerName = "JmsDispatcher";
         dispatcherOnewayWorkManagerName = "JmsAsyncQueue";
         jmsSessionOnMessageWMName = "JmsAsyncQueue";
      } else {
         dispatcherWorkManagerName = "default";
         dispatcherOnewayWorkManagerName = "default";
         jmsSessionOnMessageWMName = "default";
      }

   }
}
