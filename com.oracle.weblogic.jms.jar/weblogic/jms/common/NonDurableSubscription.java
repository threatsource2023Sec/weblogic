package weblogic.jms.common;

import weblogic.jms.backend.BEConsumerImpl;

public final class NonDurableSubscription implements Subscription {
   private String clientId;
   private String subscriptionName;
   private DestinationImpl destinationImpl;
   private String selector;
   private boolean noLocal;
   private String subscriptionQueueName;
   private int clientIdPolicy;
   private int subscriptionSharingPolicy;
   private int numSubscribers;
   private int subscribersTotalCount;
   private int subscribersHighCount;
   private volatile boolean activated;

   public synchronized int getSubscribersTotalCount() {
      return this.subscribersTotalCount;
   }

   public synchronized int getSubscribersHighCount() {
      return this.subscribersHighCount;
   }

   public NonDurableSubscription(String clientId, String subscriptionName, DestinationImpl destinationImpl, String selector, boolean noLocal, int clientIdPolicy, int subscriptionSharingPolicy) {
      this(clientId, subscriptionName, destinationImpl, selector, noLocal, clientIdPolicy, subscriptionSharingPolicy, (String)null);
   }

   public NonDurableSubscription(String clientId, String subscriptionName, DestinationImpl destinationImpl, String selector, boolean noLocal, int clientIdPolicy, int subscriptionSharingPolicy, String subscriptionQueueName) {
      this.clientId = clientId;
      this.subscriptionName = subscriptionName;
      this.destinationImpl = destinationImpl;
      if (selector != null && selector.trim().length() > 0) {
         this.selector = selector;
      }

      this.noLocal = noLocal;
      this.clientIdPolicy = clientIdPolicy;
      this.subscriptionSharingPolicy = subscriptionSharingPolicy;
      this.subscriptionQueueName = subscriptionQueueName;
   }

   public synchronized void addSubscriber(BEConsumerImpl consumer) {
      ++this.numSubscribers;
      ++this.subscribersTotalCount;
      if (this.numSubscribers > this.subscribersHighCount) {
         this.subscribersHighCount = this.numSubscribers;
      }

   }

   public synchronized void removeSubscriber(JMSID consumerId) {
      --this.numSubscribers;
   }

   public synchronized int getSubscribersCount() {
      return this.numSubscribers;
   }

   public boolean isNoLocal() {
      return this.noLocal;
   }

   public String getSelector() {
      return this.selector;
   }

   public DestinationImpl getDestinationImpl() {
      return this.destinationImpl;
   }

   public String getClientId() {
      return this.clientId;
   }

   public int getClientIdPolicy() {
      return this.clientIdPolicy;
   }

   public int getSubscriptionSharingPolicy() {
      return this.subscriptionSharingPolicy;
   }

   public String getSubscriptionQueueName() {
      return this.subscriptionQueueName;
   }

   public String getSubscriptionName() {
      return this.subscriptionName;
   }

   public boolean equals(Object o) {
      if (!(o instanceof NonDurableSubscription)) {
         return false;
      } else {
         NonDurableSubscription sub = (NonDurableSubscription)o;
         return DurableSubscription.noLocalAndSelectorMatch(this, sub.noLocal, sub.selector) && (this.destinationImpl != null || sub.destinationImpl == null) && (this.destinationImpl == null || sub.destinationImpl != null) && !test2StringNotEqual(this.clientId, sub.clientId) && !test2StringNotEqual(this.subscriptionName, sub.subscriptionName) && this.clientIdPolicy == sub.clientIdPolicy ? Destination.equalsForDS(this.destinationImpl, sub.destinationImpl) : false;
      }
   }

   public int hashCode() {
      int ret = 0;
      if (this.clientId != null) {
         ret = this.clientId.hashCode();
      }

      if (this.subscriptionName != null) {
         if (this.clientId == null) {
            ret = this.subscriptionName.hashCode();
         } else {
            ret = ret * 31 + this.subscriptionName.hashCode();
         }
      }

      if (this.selector != null) {
         ret = ret * 31 + this.selector.hashCode();
      }

      if (this.noLocal) {
         ret = ret * 31 + 1;
      }

      ret = ret * 31 + this.clientIdPolicy;
      ret = ret * 31 + this.destinationImpl.getName().hashCode();
      return ret;
   }

   private static final boolean test2StringNotEqual(String str1, String str2) {
      return (str1 != null || str2 != null) && (str1 == null || !str1.equals(str2));
   }

   public String toString() {
      return "NonDurableSubscription((" + this.clientId + (this.subscriptionName == null ? "" : ", " + this.subscriptionName) + ") :" + this.clientIdPolicy + this.destinationImpl + ":" + this.selector + ":" + this.noLocal + ":" + this.subscriptionSharingPolicy + ")";
   }

   public boolean isActivated() {
      return this.activated;
   }

   public void setActivated(boolean activated) {
      this.activated = activated;
   }
}
