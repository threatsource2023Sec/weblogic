package weblogic.jms;

import weblogic.jms.common.CDSRouter;
import weblogic.jms.common.LeaderManager;
import weblogic.jms.dispatcher.InvocableManagerDelegate;

public class JMSServiceSingleton {
   private static final String MESSAGE_LOG_NON_DURABLE_PROP = "weblogic.jms.message.logging.logNonDurableSubscriber";
   private static final String MESSAGE_LOG_DESTINATIONS_ALL_PROP = "weblogic.jms.message.logging.destinations.all";
   private boolean messageLogNonDurableSubscriber;
   private boolean messageLogAll;

   public boolean isMessageLogNonDurableSubscriber() {
      return this.messageLogNonDurableSubscriber;
   }

   public boolean isMessageLogAll() {
      return this.messageLogAll;
   }

   private JMSServiceSingleton() {
      InvocableManagerDelegate.delegate.addSingletonManager(21, LeaderManager.getLeaderManager());
      InvocableManagerDelegate.delegate.addSingletonManager(23, CDSRouter.getSingleton());
      String property = System.getProperty("weblogic.jms.message.logging.logNonDurableSubscriber");
      this.messageLogNonDurableSubscriber = property == null ? false : property.equalsIgnoreCase("true");
      property = System.getProperty("weblogic.jms.message.logging.destinations.all");
      this.messageLogAll = property == null ? false : property.equalsIgnoreCase("true");
   }

   public static final JMSServiceSingleton getService() {
      return JMSServiceSingleton.JMSServiceSingletonRef.singletonRef;
   }

   // $FF: synthetic method
   JMSServiceSingleton(Object x0) {
      this();
   }

   private static final class JMSServiceSingletonRef {
      private static final JMSServiceSingleton singletonRef = new JMSServiceSingleton();
   }
}
