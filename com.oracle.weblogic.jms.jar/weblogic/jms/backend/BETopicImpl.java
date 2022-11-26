package weblogic.jms.backend;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.jms.InvalidClientIDException;
import javax.jms.InvalidSelectorException;
import javax.jms.JMSException;
import javax.jms.ServerSessionPool;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.j2ee.descriptor.wl.TopicBean;
import weblogic.jms.JMSLogger;
import weblogic.jms.JMSService;
import weblogic.jms.backend.udd.SyntheticTopicBean;
import weblogic.jms.common.ConsumerReconnectInfo;
import weblogic.jms.common.DSManager;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.DurableSubscription;
import weblogic.jms.common.InvalidSubscriptionSharingException;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSDestinationSecurity;
import weblogic.jms.common.JMSDiagnosticImageSource;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSSQLExpression;
import weblogic.jms.common.JMSSQLFilter;
import weblogic.jms.common.JMSSecurityHelper;
import weblogic.jms.common.NonDurableSubscription;
import weblogic.jms.common.SingularAggregatableManager;
import weblogic.jms.common.Subscription;
import weblogic.jms.frontend.FEClientIDSingularAggregatable;
import weblogic.jms.multicast.JMSTMSocket;
import weblogic.management.runtime.JMSDurableSubscriberRuntimeMBean;
import weblogic.messaging.kernel.Destination;
import weblogic.messaging.kernel.InvalidExpressionException;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.KernelRequest;
import weblogic.messaging.kernel.Queue;
import weblogic.messaging.kernel.Quota;
import weblogic.messaging.kernel.Topic;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.security.WLSPrincipals;
import weblogic.store.PersistentHandle;

public final class BETopicImpl extends BEDestinationImpl {
   private static final String DURABLE_SUB_PREFIX = "_weblogic.jms.DS.";
   private static final String SHARABLE_NON_DURABLE_SUB_PREFIX = "_weblogic.jms.sharable.NDS.";
   private Topic topic;
   private final HashMap durableRuntimeMBeans = new HashMap();
   private int multicastConsumerCount;
   private boolean messageLogging;
   private String multicastAddress;
   private int multicastPort;
   private byte multicastTTL;
   private InetAddress multicastGroup;
   private BEMulticastConsumer multicastConsumer;
   private HashMap nonDurableSubscriptions = new HashMap();
   private TopicBean topicBean;
   private boolean isSysPropSetForLimit;

   public BETopicImpl(TopicBean topicBean, BackEnd backEnd, String name, boolean temporary, JMSDestinationSecurity jmsDestinationSecurity) throws JMSException {
      super(backEnd, name, temporary, jmsDestinationSecurity);
      this.topicBean = topicBean;
      Topic kernelTopic = backEnd.findKernelTopic(name);
      if (kernelTopic == null) {
         kernelTopic = backEnd.createKernelTopic(name, (Map)null);
      }

      this.setKernel(kernelTopic);
   }

   protected void setKernel(Topic topic) throws JMSException {
      super.setKernel(topic);
      this.topic = topic;
   }

   public void open() throws JMSException {
      DestinationImpl destinationImpl = this.getDestinationImpl();
      String multicastAddress = this.getMulticastAddress();
      if (multicastAddress != null && multicastAddress.length() != 0) {
         try {
            this.setMulticastGroup(InetAddress.getByName(multicastAddress));
         } catch (UnknownHostException var5) {
            throw new JMSException("MulticastAddress is not valid");
         }

         destinationImpl.setMulticastAddress(multicastAddress);
         destinationImpl.setPort(this.getMulticastPort());
         this.backEnd.getJmsService().openMulticastSendSocket();
      }

      super.open();

      try {
         this.topic.setFilter(new JMSSQLFilter(this.topic.getKernel()));
         this.topic.setProperty("RedirectionListener", this);
         this.initSubLimit();
      } catch (KernelException var4) {
         throw new weblogic.jms.common.JMSException(var4);
      }

      if (this.isMessageLoggingEnabled() && !this.backEnd.isMemoryLow()) {
         this.messageLogging = true;
      }

   }

   public int getDestinationTypeIndicator() {
      return this.isTemporary() ? 8 : 2;
   }

   private HashMap getSubscriptionQueueProperties() {
      HashMap properties = new HashMap();
      properties.put("MaximumMessageSize", new Integer(this.maximumMessageSize));
      properties.put("Quota", this.topic.getProperty("Quota"));
      properties.put("RedirectionListener", this);
      return properties;
   }

   public Queue createSubscriptionQueue(String queueName, boolean durable) throws JMSException {
      return this.createSubscriptionQueue(queueName, durable, 0);
   }

   Queue createSubscriptionQueue(String queueName, boolean durable, int subscriptionSharingPolicy) throws JMSException {
      HashMap properties = this.getSubscriptionQueueProperties();
      if (!durable) {
         properties.put("StatisticsMode", "Bypass");
      }

      try {
         Queue subscriberQueue = null;
         synchronized(this.backEnd) {
            if (durable || subscriptionSharingPolicy == 1) {
               subscriberQueue = this.backEnd.findKernelQueue(queueName);
            }

            if (subscriberQueue == null) {
               subscriberQueue = this.backEnd.createKernelQueue(queueName, properties);
            } else if (subscriptionSharingPolicy != 1) {
               subscriberQueue.setProperties(properties);
            }

            subscriberQueue.setComparator(this.comparator);
            return subscriberQueue;
         }
      } catch (KernelException var9) {
         throw new weblogic.jms.common.JMSException(var9);
      }
   }

   public void activateNewSubscriptionQueue(Queue subscriberQueue, BEConsumerImpl consumer, JMSSQLExpression expression, boolean durable) throws JMSException {
      boolean supportLogging = durable || this.backEnd.getJmsService().shouldMessageLogNonDurableSubscriber();

      try {
         subscriberQueue.setProperties(this.getSubscriptionQueueProperties());
      } catch (KernelException var7) {
         throw new weblogic.jms.common.JMSException(var7);
      }

      this.activateSubscriptionQueue(subscriberQueue, consumer, expression, supportLogging, durable);
   }

   public void activateSubscriptionQueue(Queue subscriberQueue, BEConsumerImpl consumer, JMSSQLExpression expression, boolean supportLogging, boolean durable) throws JMSException {
      try {
         if (supportLogging && consumer != null) {
            subscriberQueue.addListener(consumer);
            if (this.isMessageLoggingEnabled() && !this.backEnd.isMemoryLow()) {
               subscriberQueue.setProperty("Logging", new Integer(15));
            }
         }

         subscriberQueue.setProperty("Durable", new Boolean(durable && this.backEnd.isStoreEnabled()));
         subscriberQueue.suspend(this.destination.getMask());
         subscriberQueue.resume(16384);
         KernelRequest request = new KernelRequest();
         this.topic.subscribe(subscriberQueue, expression, request);
         request.getResult();
      } catch (InvalidExpressionException var7) {
         throw new InvalidSelectorException(var7.toString());
      } catch (KernelException var8) {
         throw new weblogic.jms.common.JMSException(var8);
      }
   }

   public void setDestinationKeysList(List destinationKeysList) {
      ArrayList consumersCopy;
      synchronized(this) {
         super.setDestinationKeysList(destinationKeysList);
         consumersCopy = new ArrayList(this.consumers);
      }

      Iterator i = consumersCopy.iterator();

      while(i.hasNext()) {
         Queue kernelQueue = ((BEConsumerImpl)i.next()).getKernelQueue();
         if (kernelQueue != null) {
            kernelQueue.setComparator(this.comparator);
         }
      }

   }

   protected int getAdjustedExpirationPolicy(boolean alreadyReported) {
      return (this.expirationPolicy == 2 || this.expirationPolicy == 4) && alreadyReported ? 1 : this.expirationPolicy;
   }

   private static String getSubscriptionQueueName(JMSID id, String clientId, String subscriptionName, boolean isDurable) throws JMSException {
      return getSubscriptionQueueName(id, clientId, subscriptionName, isDurable, 0, 0, (String)null, (String)null);
   }

   private String getSubscriptionQueueName(BEConsumerCreateRequest createRequest, Subscription subscription, String destinationName, String backEndName) throws JMSException {
      return this.isSharableNonDurableSub(createRequest) ? ((NonDurableSubscription)subscription).getSubscriptionQueueName() : getSubscriptionQueueName(createRequest.getConsumerId(), createRequest.getClientId(), createRequest.getName(), createRequest.isDurable(), createRequest.getClientIdPolicy(), createRequest.getSubscriptionSharingPolicy(), destinationName, backEndName);
   }

   private static String getSubscriptionQueueName(JMSID id, String clientId, String subscriptionName, boolean isDurable, int clientIdPolicy, int subscriptionSharingPolicy, String destinationName, String backEndName) throws JMSException {
      if (clientId != null && clientId.length() == 0) {
         throw new JMSException("Zero length client id");
      } else {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("BETopicImpl: getSubscriptionQueueName: cleintId = " + clientId + " subscriptionName = " + subscriptionName + " isDurable = " + isDurable + " client id policy = " + clientIdPolicy + " subscriptionSharingPolicy = " + subscriptionSharingPolicy + " topic name = " + destinationName + " JMS Server name = " + backEndName);
         }

         if (isDurable) {
            StringBuffer buf = new StringBuffer();
            buf.append("_weblogic.jms.DS.");
            buf.append(clientId == null ? "" : clientId);
            buf.append('.');
            buf.append(subscriptionName);
            if (clientIdPolicy == 1) {
               buf.append("@" + destinationName + "@" + backEndName);
            }

            return buf.toString();
         } else {
            return id.toString();
         }
      }
   }

   protected BEConsumerImpl createConsumer(BESessionImpl session, boolean started, BEConsumerCreateRequest createRequest) throws JMSException {
      return this.createConsumer(session, started, createRequest, (Subscription)null);
   }

   protected BEConsumerImpl createConsumer(BESessionImpl session, boolean started, BEConsumerCreateRequest createRequest, Subscription sub) throws JMSException {
      this.checkShutdownOrSuspendedNeedLock("create consumer");
      JMSID sessionId = null;
      JMSID connectionId;
      if (session != null) {
         connectionId = session.getConnection().getJMSID();
         sessionId = session.getJMSID();
      } else {
         connectionId = null;
      }

      boolean durable = createRequest.isDurable();
      boolean multicast = false;
      boolean sharedNondurble = this.isSharableNonDurableSub(createRequest);
      int flags = 0;
      if (!durable) {
         flags |= 4;
      }

      if (session != null && session.getAcknowledgeMode() == 128) {
         if (this.multicastGroup == null) {
            throw new weblogic.jms.common.JMSException("Topic " + this.name + " does not support MULTICAST_NO_ACKNOWLEDGE delivery mode");
         }

         flags |= 16;
         multicast = true;
      }

      JMSDebug.JMSBackEnd.debug("BETopicImpl.createConsumer(): connectionId=" + connectionId + ", sessionId=" + sessionId + " on " + this);
      JMSSQLExpression expression = null;
      Queue subscriberQueue = null;
      if (!multicast) {
         expression = new JMSSQLExpression(createRequest.getSelector(), createRequest.getNoLocal(), connectionId, createRequest.getNoLocal() && !durable ? sessionId : null, createRequest.getClientId(), createRequest.getClientIdPolicy());
         if (sharedNondurble) {
            sub = this.findOrCreateSharableNonDurableSubscription(createRequest, (NonDurableSubscription)sub);
         }

         subscriberQueue = this.createSubscriptionQueue(this.getSubscriptionQueueName(createRequest, sub, this.getName(), this.getBackEnd().getName()), durable, createRequest.getSubscriptionSharingPolicy());
      }

      if (durable && this.backEnd.isStoreEnabled()) {
         DurableSubscription durSub = (DurableSubscription)sub;
         if (!WLSPrincipals.isKernelUsername(JMSSecurityHelper.getSimpleAuthenticatedName())) {
            this.getJMSDestinationSecurity().checkReceivePermission(JMSSecurityHelper.getCurrentSubject());
         }
      }

      boolean fullyUp = false;
      BEConsumerImpl consumer = null;
      Subscription subscription = null;
      String subName = BEConsumerImpl.clientIdPlusName(createRequest.getClientId(), createRequest.getName(), createRequest.getClientIdPolicy(), ((javax.jms.Topic)this.getDestination()).getTopicName(), this.getBackEnd().getName());
      boolean var28 = false;

      try {
         var28 = true;
         consumer = new BEConsumerImpl(session, this, subscriberQueue, flags, false, createRequest, sub);
         this.addConsumer(consumer);
         if (!multicast) {
            subscription = consumer.getSubscription();
            boolean doActivate = true;
            if (subscription != null && createRequest.getSubscriptionSharingPolicy() == 1) {
               synchronized(subscription) {
                  if (durable && ((Subscription)subscription).getSubscribersCount() > 1) {
                     doActivate = false;
                  } else if (sharedNondurble) {
                     if (!((NonDurableSubscription)subscription).isActivated()) {
                        this.activateNewSubscriptionQueue(subscriberQueue, consumer, expression, durable);
                        ((NonDurableSubscription)subscription).setActivated(true);
                     }

                     doActivate = false;
                  }
               }
            }

            if (doActivate) {
               this.activateNewSubscriptionQueue(subscriberQueue, consumer, expression, durable);
            }
         }

         fullyUp = true;
         var28 = false;
      } finally {
         if (var28) {
            if (!fullyUp) {
               deleteFailedConsumer(consumer, durable);
            } else if (durable) {
               if (subscription == null) {
                  subscription = this.backEnd.getDurableSubscription(subName);
               }

               if (subscription != null) {
                  synchronized(subscription) {
                     if (((DurableSubscription)subscription).isPending()) {
                        ((DurableSubscription)subscription).setPending(false);
                        if (((DurableSubscription)subscription).hasWaits()) {
                           subscription.notifyAll();
                        }
                     }
                  }
               }
            }

         }
      }

      if (!fullyUp) {
         deleteFailedConsumer(consumer, durable);
      } else if (durable) {
         if (subscription == null) {
            subscription = this.backEnd.getDurableSubscription(subName);
         }

         if (subscription != null) {
            synchronized(subscription) {
               if (((DurableSubscription)subscription).isPending()) {
                  ((DurableSubscription)subscription).setPending(false);
                  if (((DurableSubscription)subscription).hasWaits()) {
                     subscription.notifyAll();
                  }
               }
            }
         }
      }

      if (started) {
         consumer.start();
      }

      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Created a new consumer with ID " + createRequest.getConsumerId() + " on topic " + this.name);
      }

      return consumer;
   }

   static void deleteFailedConsumer(BEConsumerImpl consumer, boolean durable) {
      if (consumer != null) {
         try {
            if (durable) {
               consumer.doDurableSubscriptionCleanup(consumer.getDestination().getBackEnd().getDurableSubscription(consumer.getName()), true, false, true, false);
            } else {
               consumer.close(0L);
            }
         } catch (JMSException var3) {
         }

      }
   }

   void recoverDurableSubscription(PersistentHandle persistentHandle, String clientId, int clientIdPolicy, String subscriptionName, JMSSQLExpression expression) throws JMSException {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Recovered a durable subscription " + subscriptionName + " on topic " + this.name);
      }

      Queue subscriberQueue = this.createSubscriptionQueue(getSubscriptionQueueName((JMSID)null, clientId, subscriptionName, true, clientIdPolicy, 0, this.getName(), this.backEnd.getName()), true);
      BEConsumerCreateRequest createRequest = new BEConsumerCreateRequest((JMSID)null, (JMSID)null, (JMSID)null, clientId, clientIdPolicy, subscriptionName, true, (JMSID)null, expression.getSelector(), expression.isNoLocal(), 0, 0, this.getRedeliveryDelay(), (String)null, (ConsumerReconnectInfo)null, 0);
      BEConsumerImpl consumer = null;
      boolean var17 = false;

      try {
         var17 = true;
         consumer = new BEConsumerImpl((BESessionImpl)null, this, subscriberQueue, 0, true, createRequest);
         consumer.close(0L);
         consumer.setPersistentHandle(persistentHandle);
         this.addConsumer(consumer);
         this.activateSubscriptionQueue(subscriberQueue, consumer, expression, true, true);
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Recovered a durable subscription on topic " + this.name);
            var17 = false;
         } else {
            var17 = false;
         }
      } finally {
         if (var17) {
            if (consumer != null && consumer.getSubscription() != null) {
               synchronized(consumer.getSubscription()) {
                  ((DurableSubscription)consumer.getSubscription()).resetSubscribersCount();
                  if (((DurableSubscription)consumer.getSubscription()).isPending()) {
                     ((DurableSubscription)consumer.getSubscription()).setPending(false);
                     if (((DurableSubscription)consumer.getSubscription()).hasWaits()) {
                        consumer.getSubscription().notifyAll();
                     }
                  }
               }
            }

         }
      }

      if (consumer != null && consumer.getSubscription() != null) {
         synchronized(consumer.getSubscription()) {
            ((DurableSubscription)consumer.getSubscription()).resetSubscribersCount();
            if (((DurableSubscription)consumer.getSubscription()).isPending()) {
               ((DurableSubscription)consumer.getSubscription()).setPending(false);
               if (((DurableSubscription)consumer.getSubscription()).hasWaits()) {
                  consumer.getSubscription().notifyAll();
               }
            }
         }
      }

   }

   BEConnectionConsumerImpl createConnectionConsumer(JMSID id, ServerSessionPool ssp, String clientId, String name, String selector, boolean noLocal, int messagesMaximum, long redeliveryDelay, boolean isDurable, boolean started) throws JMSException {
      this.checkShutdownOrSuspendedNeedLock("create connection consumer");
      int flags = 0;
      if (clientId == null || name == null) {
         flags |= 4;
      }

      if (isDurable) {
         isDurable = false;
         name = null;
      }

      JMSSQLExpression expression = new JMSSQLExpression(selector);
      Queue subscriberQueue = this.createSubscriptionQueue(getSubscriptionQueueName(id, clientId, name, isDurable), isDurable);
      BEConnectionConsumerImpl consumer = null;

      try {
         consumer = new BEConnectionConsumerImpl(id, this, ssp, subscriberQueue, selector, noLocal, clientId, name, isDurable, messagesMaximum, redeliveryDelay, flags);
         this.addConsumer(consumer);
         boolean supportLogging = isDurable || this.backEnd.getJmsService().shouldMessageLogNonDurableSubscriber();
         this.activateSubscriptionQueue(subscriberQueue, consumer, expression, supportLogging, isDurable);
      } catch (JMSException var17) {
         deleteFailedConsumer(consumer, isDurable);
         throw var17;
      }

      if (started) {
         consumer.start();
      }

      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Created a new ConnectionConsumer with ID " + id + " on topic " + this.name);
      }

      return consumer;
   }

   DurableSubscription findDurableSubscriber(String clientId, String name, String selector, boolean noLocal, int flag, int clientIdPolicy, int subscriptionSharingPolicy) throws JMSException {
      String subName = BEConsumerImpl.clientIdPlusName(clientId, name, clientIdPolicy, ((javax.jms.Topic)this.getDestination()).getTopicName(), this.getBackEnd().getName());
      DurableSubscription durSub = this.backEnd.getDurableSubscription(subName);
      DurableSubscription sub = durSub;
      if (durSub != null && flag == 1) {
         DurableSubscription testSubscription;
         if (clientIdPolicy != 0) {
            testSubscription = new DurableSubscription(subName, this.destinationImpl, selector, noLocal, clientIdPolicy, subscriptionSharingPolicy);
            if (durSub.equals(testSubscription)) {
               if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                  JMSDebug.JMSBackEnd.debug("Found existing durable subscription " + subName + " on topic " + this.name);
               }

               return durSub;
            } else if (durSub.getSubscribersCount() > 0) {
               throw new JMSException("Cannot change the details of a durable subscription when it is in use");
            } else {
               try {
                  if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                     JMSDebug.JMSBackEnd.debug("Deleting existing durable subscription " + subName + " on topic " + this.name);
                  }

                  sub.getConsumer().delete(false, true);
                  return null;
               } catch (JMSException var15) {
                  throw new weblogic.jms.common.JMSException("Old subscription can not be removed", var15);
               }
            }
         } else {
            sub = DSManager.manager().lookup(BEConsumerImpl.JNDINameForSubscription(subName));
            if (sub != null) {
               testSubscription = new DurableSubscription(subName, this.destinationImpl, selector, noLocal, clientIdPolicy, subscriptionSharingPolicy);
               Vector dsVector = sub.getDSVector();

               for(int i = 0; i < dsVector.size(); ++i) {
                  DurableSubscription foundSubscription = (DurableSubscription)dsVector.elementAt(i);
                  if (foundSubscription.equalsForSerialized(testSubscription)) {
                     if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                        JMSDebug.JMSBackEnd.debug("Found existing durable subscription " + subName + " on topic " + this.name);
                     }

                     return durSub;
                  }
               }
            }

            return null;
         }
      } else {
         if (JMSDebug.JMSBackEnd.isDebugEnabled() && durSub != null) {
            JMSDebug.JMSBackEnd.debug("Found existing durable subscription " + subName + " on topic " + this.name);
         }

         return durSub;
      }
   }

   NonDurableSubscription findNonDurableSubscriberJMS2(String clientId, String subscriptionName, String selector, boolean noLocal, int clientIdPolicy, int subscriptionSharingPolicy) throws InvalidSubscriptionSharingException, JMSException {
      NonDurableSubscription testSubscription = new NonDurableSubscription(clientId, subscriptionName, this.destinationImpl, selector, noLocal, clientIdPolicy, subscriptionSharingPolicy);
      NonDurableSubscription sub;
      synchronized(this.nonDurableSubscriptions) {
         sub = this.getSharableNonDurableSubscription(testSubscription);
         if (subscriptionName == null || clientIdPolicy != 0) {
            return sub;
         }
      }

      String key = BEConsumerImpl.clientIdPlusName(testSubscription.getClientId(), testSubscription.getSubscriptionName());
      this.getBackEnd().findJMS2NonDurableSharedSubscription(key, testSubscription);
      return sub;
   }

   public synchronized void addConsumer(BEConsumerCommon c) throws JMSException {
      super.addConsumer(c);
      BEConsumerImpl consumer = (BEConsumerImpl)c;
      if (consumer.isDurable()) {
         this.durableRuntimeMBeans.put(consumer.getName(), consumer.getDurableSubscriberMbean());
      }

      if (consumer.isMulticastSubscriber()) {
         ++this.multicastConsumerCount;
         if (this.multicastConsumerCount > 0 && this.multicastConsumer == null) {
            this.startMulticastConsumer();
         }
      }

   }

   public void unsubscribe(Queue kernelQueue, boolean blocking) throws JMSException {
      try {
         KernelRequest request = new KernelRequest();
         synchronized(kernelQueue) {
            this.topic.unsubscribe(kernelQueue, request);
            request.getResult();
            request = new KernelRequest();
            kernelQueue.delete(request);
         }

         if (blocking) {
            request.getResult();
         }

      } catch (KernelException var7) {
         throw new weblogic.jms.common.JMSException("Error deleting a topic subscription", var7);
      }
   }

   public void removeConsumer(BEConsumerImpl consumer, boolean blocking) throws JMSException {
      synchronized(this) {
         if (consumer.isDurable() && consumer.getPersistentHandle() != null) {
            this.durableRuntimeMBeans.remove(consumer.getName());
            this.backEnd.getDurableSubscriptionStore().deleteSubscription(consumer.getPersistentHandle());
         }

         if (consumer.isMulticastSubscriber()) {
            --this.multicastConsumerCount;
            if (this.multicastConsumerCount == 0) {
               this.stopMulticastConsumer();
            }
         }
      }

      if (!consumer.isDurable() && (consumer.getClientID() != null || consumer.getSubscriptionName() != null)) {
         this.removeSharableNonDurableSubscriber(consumer);
      }

      int subscriberCount;
      if (!consumer.isMulticastSubscriber() && (consumer.getSubscription() == null || consumer.getSubscription().getSubscribersCount() == 0) || !consumer.isDurable() && consumer.getSubscriptionSharingPolicy() == 0) {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            subscriberCount = 0;
            if (consumer.getSubscription() != null) {
               subscriberCount = consumer.getSubscription().getSubscribersCount();
            }

            JMSDebug.JMSBackEnd.debug("BETopicImpl.removeConsumer() " + this + " consumer: " + consumer + " unsubscribe isMulticastSubscriber " + consumer.isMulticastSubscriber() + " subscription " + consumer.getSubscription() + " isDurable " + consumer.isDurable() + " getSubscriptionSharingPolicy() " + consumer.getSubscriptionSharingPolicy() + " getClientIdPolicy() " + consumer.getClientIdPolicy() + " subscriberCount " + subscriberCount);
         }

         this.unsubscribe(consumer.getUnsubscribeQueue(), blocking);
      } else if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         subscriberCount = 0;
         if (consumer.getSubscription() != null) {
            subscriberCount = consumer.getSubscription().getSubscribersCount();
         }

         JMSDebug.JMSBackEnd.debug("BETopicImpl.removeConsumer() " + this + " consumer: " + consumer + " SKIPPING unsubscribe!!! isMulticastSubscriber " + consumer.isMulticastSubscriber() + " subscription " + consumer.getSubscription() + " isDurable " + consumer.isDurable() + " getSubscriptionSharingPolicy() " + consumer.getSubscriptionSharingPolicy() + " getClientIdPolicy() " + consumer.getClientIdPolicy() + " subscriberCount " + subscriberCount);
      }

      synchronized(this) {
         if (!this.consumers.contains(consumer)) {
            return;
         }
      }

      super.removeConsumer(consumer, blocking);
   }

   public void removeConsumer(BEConsumerImpl consumer, boolean blocking, boolean removeFromDestinationOnly) throws JMSException {
      if (removeFromDestinationOnly) {
         super.removeConsumer(consumer, blocking);
      } else {
         this.removeConsumer(consumer, blocking);
      }

   }

   private synchronized List getConsumerQueues() {
      List queueList = new ArrayList(this.consumers.size() + 1);
      Iterator i = this.consumers.iterator();

      while(i.hasNext()) {
         Queue kernelQueue = ((BEConsumerImpl)i.next()).getKernelQueue();
         if (kernelQueue != null) {
            queueList.add(kernelQueue);
         }
      }

      if (this.multicastConsumer != null) {
         queueList.add(this.multicastConsumer.getQueue());
      }

      return queueList;
   }

   protected void suspendKernelDestination(int mask) throws JMSException {
      super.suspendKernelDestination(mask);
      Iterator i = this.getConsumerQueues().iterator();
      KernelException lastException = null;

      while(i.hasNext()) {
         try {
            ((Queue)i.next()).suspend(mask);
         } catch (KernelException var5) {
            lastException = var5;
         }
      }

      if (lastException != null) {
         throw new weblogic.jms.common.JMSException(lastException);
      }
   }

   protected void resumeKernelDestination(int mask) throws JMSException {
      super.resumeKernelDestination(mask);
      KernelException lastException = null;
      Iterator i = this.getConsumerQueues().iterator();

      while(i.hasNext()) {
         try {
            ((Queue)i.next()).resume(mask);
         } catch (KernelException var5) {
            lastException = var5;
         }
      }

      if (lastException != null) {
         throw new weblogic.jms.common.JMSException(lastException);
      }
   }

   protected void closeAllConsumers(String reason) {
      super.closeAllConsumers(reason);
      this.stopMulticastConsumer();
   }

   public String getMulticastAddress() {
      return this.multicastAddress;
   }

   public void setMulticastAddress(String multicastAddress) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled() && multicastAddress != null && multicastAddress.length() != 0) {
         JMSDebug.JMSBackEnd.debug("Topic " + this.name + " setting multicastAddress to " + multicastAddress);
      }

      this.multicastAddress = multicastAddress;
   }

   public int getMulticastPort() {
      return this.multicastPort;
   }

   public void setMulticastPort(int port) {
      this.multicastPort = port;
   }

   public void setMulticastTimeToLive(int ttl) {
      this.multicastTTL = (byte)ttl;
   }

   public int getMulticastTimeToLive() {
      return this.multicastTTL;
   }

   public void setMessagesLimitOverride(long subscriptionMessagesLimit) {
      if (this.getStateValue() != 0 && !this.isSysPropSetForLimit) {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("BETopicImpl.initSubLimit " + this.name + ": dynamically changing sublimit to " + subscriptionMessagesLimit);
         }

         try {
            this.topic.setProperty("SubscriptionMessagesLimit", subscriptionMessagesLimit);
         } catch (KernelException var4) {
            var4.printStackTrace();
         }

      }
   }

   public void setMulticastGroup(InetAddress group) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled() && group != null) {
         JMSDebug.JMSBackEnd.debug("Topic " + this.name + " setting multicast group to " + group);
      }

      this.multicastGroup = group;
   }

   public synchronized JMSDurableSubscriberRuntimeMBean[] getDurableSubscribers() {
      if (this.durableRuntimeMBeans.isEmpty()) {
         return new JMSDurableSubscriberRuntimeMBean[0];
      } else {
         JMSDurableSubscriberRuntimeMBean[] ret = new JMSDurableSubscriberRuntimeMBean[this.durableRuntimeMBeans.size()];
         this.durableRuntimeMBeans.values().toArray(ret);
         return ret;
      }
   }

   public void createDurableSubscriber(String clientID, String subscriptionName, String selector, boolean noLocal) throws JMSException {
      this.createDurableSubscriber(clientID, 0, subscriptionName, selector, noLocal, 0);
   }

   public void createDurableSubscriber(String clientID, int clientIdPolicy, String subscriptionName, String selector, boolean noLocal, int subscriptionSharingPolicy) throws JMSException {
      this.checkShutdownOrSuspendedNeedLock("create durable subscriber");
      if (clientIdPolicy == 0) {
         this.reserveClientID(clientID);
      }

      try {
         DurableSubscription durSub = this.findDurableSubscriber(clientID, subscriptionName, selector, noLocal, 1, clientIdPolicy, subscriptionSharingPolicy);
         if (durSub != null) {
            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               JMSDebug.JMSBackEnd.debug("createDurableSubscriber(): found a sub: sub's sharingpolicy = " + durSub.getSubscriptionSharingPolicy() + "request's sharingpolicy = " + subscriptionSharingPolicy);
            }

            if (subscriptionSharingPolicy != durSub.getSubscriptionSharingPolicy() && durSub.getSubscribersCount() != 0) {
               throw new JMSException("Cannot change the sharing policy on an active subscriptions");
            }
         }

         if (durSub == null || subscriptionSharingPolicy == 1) {
            BEConsumerCreateRequest createRequest = new BEConsumerCreateRequest((JMSID)null, (JMSID)null, (JMSID)null, clientID, clientIdPolicy, subscriptionName, true, (JMSID)null, selector, noLocal, 0, 0, -1L, (String)null, (ConsumerReconnectInfo)null, subscriptionSharingPolicy);
            BEConsumerImpl consumer = this.createConsumer((BESessionImpl)null, false, createRequest, durSub);
            consumer.close(0L);
         }
      } finally {
         if (clientIdPolicy == 0) {
            this.releaseClientID(clientID);
         }

      }

   }

   private synchronized void startMulticastConsumer() throws JMSException {
      if (this.multicastConsumer == null) {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Starting multicasting for the JMS topic " + this.getName());
         }

         JMSID id = JMSService.getNextId();
         JMSTMSocket socket = this.backEnd.getJmsService().getMulticastSocket();
         if (socket == null) {
            throw new JMSException("Failed to start multicasting for JMS Topic " + this.getName());
         } else {
            Queue consumerQueue = this.createSubscriptionQueue(id.toString(), false);
            this.multicastConsumer = new BEMulticastConsumer(this.backEnd, consumerQueue, this.destinationImpl, this.multicastGroup, this.multicastPort, this.multicastTTL, socket);
            this.activateSubscriptionQueue(consumerQueue, (BEConsumerImpl)null, new JMSSQLExpression(), false, false);

            try {
               this.multicastConsumer.start();
            } catch (JMSException var5) {
               this.multicastConsumer = null;
               throw var5;
            }
         }
      }
   }

   private synchronized void stopMulticastConsumer() {
      if (this.multicastConsumer != null) {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Stopping multicasting for the JMS topic");
         }

         try {
            this.multicastConsumer.stop();
            this.unsubscribe(this.multicastConsumer.getQueue(), false);
            this.multicastConsumer = null;
         } catch (JMSException var2) {
            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               JMSDebug.JMSBackEnd.debug("Error stopping multicast consumer: " + var2);
            }
         }

      }
   }

   public final void setMessageLoggingEnabled(boolean value) {
      if ((!super.isMessageLoggingEnabled() || !value) && (super.isMessageLoggingEnabled() || value)) {
         super.setMessageLoggingEnabled(value);

         try {
            if (value && !this.backEnd.isMemoryLow()) {
               this.resumeMessageLogging();
            } else if (!value) {
               this.suspendMessageLogging();
            }
         } catch (JMSException var3) {
         }

      }
   }

   public void resumeMessageLogging() throws JMSException {
      this.messageLogging = true;
      Map consumersCopy = this.getConsumersClone();
      Iterator itr = consumersCopy.values().iterator();

      while(true) {
         BEConsumerImpl consumer;
         do {
            if (!itr.hasNext()) {
               return;
            }

            consumer = (BEConsumerImpl)itr.next();
         } while(!this.backEnd.getJmsService().shouldMessageLogNonDurableSubscriber() && !consumer.isDurable());

         addPropertyFlags(consumer.getKernelQueue(), "Logging", 15);
      }
   }

   public void suspendMessageLogging() throws JMSException {
      this.messageLogging = false;
      Map consumersCopy = this.getConsumersClone();
      Iterator itr = consumersCopy.values().iterator();

      while(true) {
         BEConsumerImpl consumer;
         do {
            if (!itr.hasNext()) {
               return;
            }

            consumer = (BEConsumerImpl)itr.next();
         } while(!this.backEnd.getJmsService().shouldMessageLogNonDurableSubscriber() && !consumer.isDurable());

         removePropertyFlags(consumer.getKernelQueue(), "Logging", 15);
      }
   }

   public void setQuota(Quota kernelQuota) throws BeanUpdateFailedException {
      HashMap properties = new HashMap();

      try {
         properties.put("Quota", kernelQuota);
         this.getKernelDestination().setProperties(properties);
         Map consumers = this.getConsumersClone();
         Iterator itr = consumers.keySet().iterator();

         while(itr.hasNext()) {
            BEConsumerImpl consumer = (BEConsumerImpl)itr.next();
            if (!consumer.isMulticastSubscriber()) {
               Destination destination = consumer.getKernelQueue();
               destination.setProperties(properties);
            }
         }

      } catch (KernelException var7) {
         throw new BeanUpdateFailedException("Messaging Kernel failed to act on the quota" + kernelQuota);
      }
   }

   private void initSubLimit() throws KernelException {
      long msgsLimit = -1L;
      String topicConfigName = null;
      String sysProp = null;
      if (this.topicBean instanceof SyntheticTopicBean) {
         topicConfigName = ((SyntheticTopicBean)this.topicBean).getUDDestinationName();
      } else {
         topicConfigName = this.topicBean.getName();
      }

      try {
         sysProp = "weblogic.jms.topic.DurableSubscriptionMessagesLimit." + this.moduleName + "." + topicConfigName;
         String sysLimit = System.getProperty(sysProp);
         if (sysLimit == null) {
            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               JMSDebug.JMSBackEnd.debug("BETopicImpl.initSubLimit " + this.name + ": MessagesLimit is not configured on topic level via -D properties.");
            }

            sysProp = "weblogic.jms.topic.DurableSubscriptionMessagesLimit";
            sysLimit = System.getProperty(sysProp);
         }

         if (sysLimit == null) {
            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               JMSDebug.JMSBackEnd.debug("BETopicImpl.initSubLimit " + this.name + ": MessagesLimit is not configured via -D properties.");
            }
         } else {
            this.isSysPropSetForLimit = true;

            try {
               msgsLimit = Long.parseLong(sysLimit);
               if (msgsLimit <= 0L) {
                  JMSLogger.logInvalidSubscriptionLimit(this.name, this.moduleName, sysLimit, sysProp);
               }
            } catch (NumberFormatException var7) {
               JMSLogger.logInvalidSubscriptionLimit(this.name, this.moduleName, sysLimit, sysProp);
            }
         }
      } catch (Exception var8) {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("BETopicImpl.initSubLimit " + this.name + ": got error, no msgsLimit set", var8);
         }
      }

      if (!this.isSysPropSetForLimit) {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("BETopicImpl.initSubLimit " + this.name + ": use sublimit from TopicBean");
         }

         msgsLimit = this.topicBean.getTopicSubscriptionParams().getMessagesLimitOverride();
      }

      if (msgsLimit > 0L) {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("BETopicImpl.initSubLimit " + this.name + ": set sublimit as " + msgsLimit);
         }

         this.topic.setProperty("SubscriptionMessagesLimit", msgsLimit);
         if (!this.isTemporary()) {
            JMSLogger.logSubscriptionLimit(this.name, this.moduleName, msgsLimit);
         }
      }

   }

   NonDurableSubscription getSharableNonDurableSubscription(NonDurableSubscription sub) {
      synchronized(this.nonDurableSubscriptions) {
         return (NonDurableSubscription)this.nonDurableSubscriptions.get(sub);
      }
   }

   NonDurableSubscription addNonDurableSubscriber(NonDurableSubscription sub) throws JMSException {
      String subkey = null;
      String subname = sub.getSubscriptionName();
      if (subname != null) {
         subkey = BEConsumerImpl.clientIdPlusName(sub.getClientId(), subname);
      }

      synchronized(this.nonDurableSubscriptions) {
         if (subkey != null) {
            this.getBackEnd().addJMS2NonDurableSharedSubscription(subkey, sub);
         }

         NonDurableSubscription subFound = this.getSharableNonDurableSubscription(sub);
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("BETopicImpl: addSharableNonDurableSubscriber subFound=" + subFound);
         }

         if (subFound != null && subFound.equals(sub)) {
            subFound.addSubscriber((BEConsumerImpl)null);
            return subFound;
         } else {
            sub.addSubscriber((BEConsumerImpl)null);
            this.nonDurableSubscriptions.put(sub, sub);
            return sub;
         }
      }
   }

   long getEffectiveSubLimit() {
      return (Long)this.topic.getProperty("SubscriptionMessagesLimit");
   }

   private void removeSharableNonDurableSubscriber(BEConsumerImpl consumer) throws JMSException {
      NonDurableSubscription testSub = new NonDurableSubscription(consumer.getClientID(), consumer.getSubscriptionName(), consumer.getDestination().getDestinationImpl(), consumer.getSelector(), consumer.getNoLocal(), consumer.getClientIdPolicy(), consumer.getSubscriptionSharingPolicy());
      String subkey = null;
      String subname = testSub.getSubscriptionName();
      if (subname != null) {
         subkey = BEConsumerImpl.clientIdPlusName(testSub.getClientId(), subname);
      }

      synchronized(this.nonDurableSubscriptions) {
         NonDurableSubscription sub = this.getSharableNonDurableSubscription(testSub);
         if (sub != null) {
            boolean cleanup = false;
            synchronized(sub) {
               sub.removeSubscriber((JMSID)null);
               if (sub.getSubscribersCount() <= 0) {
                  this.nonDurableSubscriptions.remove(sub);
                  cleanup = true;
               }
            }

            if (cleanup && subkey != null) {
               this.getBackEnd().removeJMS2NonDurableSharedSubscription(subkey, testSub);
            }

         }
      }
   }

   private String getNextSharableNonDurableSubName(String clientId, String subscriptionName) {
      return "_weblogic.jms.sharable.NDS." + (clientId == null ? "" : clientId) + (subscriptionName == null ? "" : "." + subscriptionName) + "." + JMSService.getNextId().toString() + "@" + this.name + "@" + this.backEnd.getName();
   }

   public boolean isMessageLogging() {
      return this.messageLogging;
   }

   public void dump(JMSDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeStartElement("Topic");
      super.dump(imageSource, xsw);
      xsw.writeEndElement();
   }

   private boolean isSharableNonDurableSub(BEConsumerCreateRequest createRequest) {
      return !createRequest.isDurable() && (createRequest.getName() != null || createRequest.getClientId() != null) && createRequest.getSubscriptionSharingPolicy() == 1;
   }

   private Subscription findOrCreateSharableNonDurableSubscription(BEConsumerCreateRequest createRequest, NonDurableSubscription sub) throws JMSException {
      synchronized(this.nonDurableSubscriptions) {
         if (sub == null) {
            sub = this.findNonDurableSubscriberJMS2(createRequest.getClientId(), createRequest.getName(), createRequest.getSelector(), createRequest.getNoLocal(), createRequest.getClientIdPolicy(), createRequest.getSubscriptionSharingPolicy());
         }

         if (sub == null) {
            String subName = this.getNextSharableNonDurableSubName(createRequest.getClientId(), createRequest.getName());
            sub = new NonDurableSubscription(createRequest.getClientId(), createRequest.getName(), this.destinationImpl, createRequest.getSelector(), createRequest.getNoLocal(), createRequest.getClientIdPolicy(), createRequest.getSubscriptionSharingPolicy(), subName);
         }

         this.addNonDurableSubscriber(sub);
         return sub;
      }
   }

   private void reserveClientID(String clientID) throws JMSException {
      JMSService jmsService = this.backEnd.getJmsService();
      SingularAggregatableManager sm = jmsService.getSingularAggregatableManagerWithJMSException();
      String jndiName = "weblogic.jms.connection.clientid." + clientID;
      FEClientIDSingularAggregatable focascia = new FEClientIDSingularAggregatable(clientID, JMSService.getNextId());
      String reason;
      if ((reason = sm.singularBind(jndiName, focascia)) != null) {
         throw new InvalidClientIDException("Client id, " + clientID + ", is in use.  The reason for rejection is \"" + reason + "\"");
      }
   }

   private void releaseClientID(String clientID) throws JMSException {
      JMSService jmsService = this.backEnd.getJmsService();
      SingularAggregatableManager sm = jmsService.getSingularAggregatableManagerWithJMSException();
      String jndiName = "weblogic.jms.connection.clientid." + clientID;

      try {
         sm.singularUnbind(jndiName);
      } catch (JMSException var6) {
         throw new weblogic.jms.common.JMSException("Unable to unbind client id " + clientID + " from JNDI", var6);
      }
   }
}
