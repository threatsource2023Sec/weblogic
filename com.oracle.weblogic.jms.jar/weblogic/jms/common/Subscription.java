package weblogic.jms.common;

import weblogic.jms.backend.BEConsumerImpl;

public interface Subscription {
   void addSubscriber(BEConsumerImpl var1);

   int getSubscribersTotalCount();

   int getSubscribersHighCount();

   void removeSubscriber(JMSID var1);

   int getSubscribersCount();

   boolean isNoLocal();

   String getSelector();
}
