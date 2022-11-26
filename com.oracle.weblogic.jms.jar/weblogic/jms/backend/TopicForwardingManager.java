package weblogic.jms.backend;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import javax.jms.JMSException;
import weblogic.jms.JMSService;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSSQLExpression;
import weblogic.jms.dd.DDHandler;
import weblogic.jms.dd.DDManager;
import weblogic.jms.dd.DDMember;
import weblogic.jms.dd.DDStatusListener;
import weblogic.messaging.kernel.Event;
import weblogic.messaging.kernel.EventListener;
import weblogic.messaging.kernel.GroupAddEvent;
import weblogic.messaging.kernel.GroupEvent;
import weblogic.messaging.kernel.GroupRemoveEvent;
import weblogic.messaging.kernel.Queue;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class TopicForwardingManager implements EventListener, DDStatusListener, ForwardingStatusListener {
   private Map forwardGroupCounters = new HashMap();
   private Map forwarders = new HashMap();
   private static final String SYSTEM_DIST_SUBSCRIBER_CLIENT = "WeblogicJmsDistributedTopic";
   DDHandler ddHandler;
   DDMember member;
   BEUOOTopicState beUOOTopicState;
   WorkManager topicForwarderWorkManager;
   private final Object refreshMembersLock = new Object();
   private int state;
   private TimerManager timerManagerRefreshMembers = null;
   private static final String TFMNAME = "TopicForwardingTimerManager.";
   private static final long REFRESH_MEMBERS_RETRY_DELAY = 10000L;
   private static final int REFRESH_IS_RUNNING = 1;
   private static final int REFRESH_NEEDS_RESCHEDULING_ONCOMPLETION = 2;
   private static final int REFRESH_IS_SCHEDULED = 4;
   private static final int DEACTIVATING = 8;

   public TopicForwardingManager(DDHandler ddHandler, DDMember member, BEDestinationImpl destination, WorkManager topicForwarderWorkManager) {
      this.ddHandler = ddHandler;
      this.member = member;
      member.setIsForwardingUp(true);
      ddHandler.addStatusListener(this, 19);
      if ("PathService".equals(ddHandler.getUnitOfOrderRouting())) {
         this.beUOOTopicState = new BEUOOTopicState(destination, ddHandler);
      }

      destination.setExtension(this.beUOOTopicState);
      this.topicForwarderWorkManager = topicForwarderWorkManager;
      if (this.topicForwarderWorkManager == null) {
         this.topicForwarderWorkManager = WorkManagerFactory.getInstance().getSystem();
      }

      this.timerManagerRefreshMembers = TimerManagerFactory.getTimerManagerFactory().getTimerManager("TopicForwardingTimerManager." + destination.getBackEnd().getName(), this.topicForwarderWorkManager);
   }

   public void statusChangeNotification(DDHandler ddHandler, int events) {
      if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
         JMSDebug.JMSDistTopic.debug("TopicForwardingManager.statusChangeNotification() " + ddHandler.getName() + " events " + eventsPrint(events));
      }

      if ((events & 16) != 0) {
         this.deactivate();
      } else if ((events & 1) != 0 && ddHandler.findMemberByName(this.member.getName()) == null) {
         this.deactivate();
      } else {
         this.refreshMembers();
         this.member.setIsForwardingUp(true);
      }
   }

   private void deactivate() {
      if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
         JMSDebug.JMSDistTopic.debug("Deactivate on topic forwarder for " + this.member.getName() + " within " + this.ddHandler.getName());
      }

      synchronized(this.refreshMembersLock) {
         this.setState(8);
      }

      this.ddHandler.removeStatusListener(this);
      LinkedList toDeactivate = new LinkedList();
      Iterator iter = null;
      synchronized(this.forwarders) {
         iter = this.forwarders.values().iterator();

         while(true) {
            if (iter == null || !iter.hasNext()) {
               break;
            }

            Forwarder forwarder = (Forwarder)iter.next();
            toDeactivate.add(forwarder);
            iter.remove();
         }
      }

      iter = toDeactivate.iterator();

      while(iter != null && iter.hasNext()) {
         Forwarder forwarder = (Forwarder)iter.next();
         forwarder.deactivate();
         iter.remove();
      }

   }

   private void refreshMembers() {
      if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
         JMSDebug.JMSDistTopic.debug("Refreshing members for " + this.ddHandler.getName() + " with respect to " + this.member.getName() + ": this is " + this);
      }

      synchronized(this.refreshMembersLock) {
         if ((this.state & 8) != 0) {
            JMSDebug.JMSDistTopic.debug("Refresh members halted, Forwarder is DEACTIVATING for " + this.ddHandler.getName());
            return;
         }
      }

      Iterator iter = null;
      LinkedList toRemove = new LinkedList();
      synchronized(this.forwarders) {
         iter = ((HashMap)((HashMap)this.forwarders).clone()).values().iterator();
      }

      DDMember forwarder;
      while(iter != null && iter.hasNext()) {
         Forwarder forwarder = (Forwarder)iter.next();
         forwarder = this.ddHandler.findMemberByName(forwarder.getName());
         if (forwarder == null) {
            if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
               JMSDebug.JMSDistTopic.debug(forwarder.getName() + " is no longer a member of " + this.ddHandler.getName() + " so I won't forward from " + this.member.getName());
            }

            forwarder.deactivate();
            toRemove.add(forwarder.getName());
         }
      }

      iter = toRemove.iterator();
      synchronized(this.forwarders) {
         while(iter != null && iter.hasNext()) {
            String forwardTo = (String)iter.next();
            Forwarder forwarder = (Forwarder)this.forwarders.get(forwardTo);
            if (forwarder != null && forwarder.isDeactivated()) {
               this.forwarders.remove(forwardTo);
            }
         }
      }

      iter = this.ddHandler.memberCloneIterator();

      while(true) {
         while(true) {
            Forwarder forwarder;
            DDMember forwardTo;
            do {
               do {
                  if (!iter.hasNext()) {
                     return;
                  }

                  forwardTo = (DDMember)iter.next();
                  if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
                     JMSDebug.JMSDistTopic.debug("Looking for match between forwardTo " + forwardTo.getName() + " and member " + this.member.getName());
                  }
               } while(forwardTo.getName().equals(this.member.getName()));

               forwarder = null;
               synchronized(this.forwarders) {
                  forwarder = (Forwarder)this.forwarders.get(forwardTo.getName());
               }

               if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
                  JMSDebug.JMSDistTopic.debug("Found match, forwarder " + forwarder);
               }

               if (forwarder != null) {
                  break;
               }

               try {
                  if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
                     JMSDebug.JMSDistTopic.debug("Creating a forwarder to " + forwardTo.getName() + " from " + this.member.getName() + " within " + this.ddHandler.getName());
                  }

                  forwarder = new Forwarder(forwardTo);
                  synchronized(this.forwarders) {
                     this.forwarders.put(forwardTo.getName(), forwarder);
                  }
               } catch (JMSException var11) {
               }
            } while(forwarder == null);

            if (!forwarder.isStarted() && !forwarder.isDeactivated() && forwardTo.isUp() && this.member.isDestinationUp()) {
               if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
                  JMSDebug.JMSDistTopic.debug("Start topic forwarding, local " + this.member.isDestinationUp() + " and remote member " + forwardTo.getName() + " are both up");
               }

               forwarder.start(forwardTo);
            } else if (forwarder.isStarted() && (!forwardTo.isUp() || !this.member.isDestinationUp())) {
               if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
                  JMSDebug.JMSDistTopic.debug("Stop forwarding " + this.member.getName() + " forwardTo.isUp: " + forwardTo.isUp() + " member.isDestinationUp: " + this.member.isDestinationUp());
               }

               forwarder.stop();
            }
         }
      }
   }

   public void shutdown() {
      if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
         JMSDebug.JMSDistTopic.debug("Shutdown topic forwarder for " + this.ddHandler.getName() + " with respect to " + this.member.getName() + ": this is " + this);
      }

      this.deactivate();
   }

   public void onEvent(Event event) {
      if (event instanceof GroupEvent) {
         boolean increment = event instanceof GroupAddEvent;

         assert increment ^ event instanceof GroupRemoveEvent;

         String groupName = ((GroupEvent)event).getGroup().getName();
         synchronized(this) {
            Counter counter = (Counter)this.forwardGroupCounters.get(groupName);
            if (increment) {
               if (counter != null) {
                  counter.increment();
                  return;
               }

               this.forwardGroupCounters.put(groupName, new Counter(1));
            } else {
               if (counter == null || counter.decrement() != 0) {
                  return;
               }

               this.forwardGroupCounters.remove(groupName);
            }
         }

         if (this.beUOOTopicState != null) {
            if (increment) {
               this.beUOOTopicState.groupAddEvent(groupName);
            } else {
               this.beUOOTopicState.groupRemoveEvent(groupName);
            }
         }

      }
   }

   public String toString() {
      return "TopicForwardingManager: " + this.member.getName() + " within " + this.ddHandler.getName() + ", hash: " + this.hashCode();
   }

   private static String systemSubscriberName(String remoteTopicName, String localTopicName) {
      StringBuffer ret = new StringBuffer();
      ret.append("WeblogicJmsDistributedTopic");
      ret.append("@Remote@");
      ret.append(remoteTopicName);
      ret.append("@Local@");
      ret.append(localTopicName);
      return ret.toString();
   }

   private void setState(int flag) {
      this.state |= flag;
   }

   private void clearState(int flag) {
      this.state &= ~flag;
   }

   public synchronized void forwardingFailed(BEForwardingConsumer consumer) {
      if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
         JMSDebug.JMSDistTopic.debug(this + ": TopicForwardingManager.forwardingFailed() consumer: " + consumer);
      }

      synchronized(this.refreshMembersLock) {
         if ((this.state & 8) != 0) {
            if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
               JMSDebug.JMSDistTopic.debug(this + ", Going down, state: " + this.getStateString());
            }

            this.clearState(2);
         } else if ((this.state & 5) != 0) {
            if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
               JMSDebug.JMSDistTopic.debug(this + ", needs reschedule on completion, state: " + this.getStateString());
            }

            this.setState(2);
         } else {
            this.clearState(2);
            this.setState(4);
            this.timerManagerRefreshMembers.schedule(new RefreshMembersThread(), 0L);
            if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
               JMSDebug.JMSDistTopic.debug(this + ": state: " + this.getStateString() + ", scheduling refreshMembers with no delay");
            }
         }

      }
   }

   private String getStateString() {
      StringBuffer buf = new StringBuffer();
      buf.append(this.state);
      buf.append(":");
      if (this.state == 0) {
         buf.append("CLEAR");
      } else {
         if ((this.state & 1) != 0) {
            buf.append(" REFRESH_IS_RUNNING|");
         }

         if ((this.state & 2) != 0) {
            buf.append("REFRESH_NEEDS_RESCHEDULING_ONCOMPLETION|");
         }

         if ((this.state & 4) != 0) {
            buf.append("REFRESH_IS_SCHEDULED");
         }

         if ((this.state & 8) != 0) {
            buf.append("DEACTIVATING");
         }
      }

      return buf.toString();
   }

   private static String eventsPrint(int events) {
      String buf = null;
      if ((events & 1) != 0) {
         buf = append(buf, "MEMBERSHIP_CHANGE");
      }

      if ((events & 2) != 0) {
         buf = append(buf, "MEMBER_STATUS_CHANGE");
      }

      if ((events & 4) != 0) {
         buf = append(buf, "DD_PARAM_CHANGE");
      }

      if ((events & 8) != 0) {
         buf = append(buf, "ACTIVATE");
      }

      if ((events & 16) != 0) {
         buf = append(buf, "DEACTIVATE");
      }

      return buf;
   }

   private static String append(String base, String add) {
      return base == null ? add : base + "|" + add;
   }

   protected final class Forwarder {
      private String name;
      private Queue subscriberQueue;
      private BEForwardingConsumer forwardingConsumer;
      private BETopicImpl topicImpl;
      private boolean deactivated = false;

      Forwarder(DDMember forwardTo) throws JMSException {
         this.name = forwardTo.getName();
         String queueName = TopicForwardingManager.systemSubscriberName(this.name, TopicForwardingManager.this.member.getName());
         JMSSQLExpression expression = new JMSSQLExpression((String)null, false, (JMSID)null, true);
         this.topicImpl = (BETopicImpl)TopicForwardingManager.this.member.getDestination();
         if (this.topicImpl == null) {
            this.stop();
            throw new JMSException("Error while creating a system subscriber, member has no valid destination");
         } else {
            if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
               JMSDebug.JMSDistTopic.debug("Creating new system subscriber queue " + queueName + " for forwarding to " + this.name + " from " + TopicForwardingManager.this.member.getName());
            }

            try {
               this.subscriberQueue = this.topicImpl.createSubscriptionQueue(queueName, true);
               this.topicImpl.activateSubscriptionQueue(this.subscriberQueue, (BEConsumerImpl)null, expression, false, true);
            } catch (JMSException var6) {
               if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
                  JMSDebug.JMSDistTopic.debug("Exception while creating a system subscriber: " + queueName, var6);
               }

               this.stop();
               throw var6;
            }

            if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
               JMSDebug.JMSDistTopic.debug("Created new system subscriber queue " + queueName + " for forwarding to " + this.name + " from " + TopicForwardingManager.this.member.getName());
            }

            JMSID id = JMSService.getNextId();
            if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
               JMSDebug.JMSDistTopic.debug("Creating system subscriber " + this.name + " beUOOTopicState " + TopicForwardingManager.this.beUOOTopicState);
            }

            this.forwardingConsumer = new BEForwardingConsumer(TopicForwardingManager.this.member.getDestination().getBackEnd(), this.name, id, this.subscriberQueue);
            if (TopicForwardingManager.this.beUOOTopicState != null) {
               BEDestinationImpl.addPropertyFlags(this.forwardingConsumer.getQueue(), "Logging", 16);
               this.forwardingConsumer.getQueue().addListener(TopicForwardingManager.this);
            } else {
               this.forwardingConsumer.setStatusListener(TopicForwardingManager.this);
            }

         }
      }

      boolean isStarted() {
         return this.forwardingConsumer.isStarted();
      }

      void start(DDMember forwardTo) {
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("Starting system subscriber to " + this.subscriberQueue.getName() + " for " + this.name + " from " + TopicForwardingManager.this.member.getName());
         }

         DestinationImpl ddImpl = DDManager.findDDImplByMemberName(this.name);
         if (ddImpl == null) {
            if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
               JMSDebug.JMSDistTopic.debug("Cannot find the DDImpl for " + this.name + " to accept forwarding from " + TopicForwardingManager.this.member.getName());
            }

         } else {
            try {
               synchronized(TopicForwardingManager.this) {
                  this.forwardingConsumer.start(ddImpl, ddImpl.getMemberName(), forwardTo.getRemoteSecurityMode());
               }
            } catch (JMSException var6) {
            }

         }
      }

      String getName() {
         return this.name;
      }

      synchronized void stop() {
         if (this.forwardingConsumer != null) {
            if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
               JMSDebug.JMSDistTopic.debug("Stopping system subscriber to " + this.subscriberQueue.getName() + " for " + this.name + " from " + TopicForwardingManager.this.member.getName());
            }

            this.forwardingConsumer.stop();
         }

      }

      synchronized boolean isDeactivated() {
         return this.deactivated;
      }

      synchronized void deactivate() {
         this.deactivated = true;
         this.stop();
         if (this.subscriberQueue != null) {
            DDMember member = TopicForwardingManager.this.member;
            if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
               JMSDebug.JMSDistTopic.debug("Permanently removing system subscriber to " + this.subscriberQueue.getName() + " for " + this.name + " from " + member.getName());
            }

            try {
               this.topicImpl.unsubscribe(this.subscriberQueue, false);
            } catch (JMSException var3) {
               if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
                  JMSDebug.JMSDistTopic.debug("Exception while shutting down forwarder", var3);
               }
            }

            this.subscriberQueue = null;
         }

      }
   }

   private final class RefreshMembersThread implements NakedTimerListener {
      RefreshMembersThread() {
      }

      public void timerExpired(Timer timer) {
         if (timer != null) {
            timer.cancel();
         }

         synchronized(TopicForwardingManager.this.refreshMembersLock) {
            TopicForwardingManager.this.clearState(4);
            if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
               JMSDebug.JMSDistTopic.debug(this + ": TopicForwardingManager.RefreshMembersThread(), timer: " + timer + " state: " + TopicForwardingManager.this.getStateString());
            }

            if ((TopicForwardingManager.this.state & 8) != 0) {
               return;
            }

            if ((TopicForwardingManager.this.state & 1) != 0) {
               TopicForwardingManager.this.setState(2);
               return;
            }

            TopicForwardingManager.this.setState(1);
         }

         boolean var12 = false;

         try {
            var12 = true;
            TopicForwardingManager.this.refreshMembers();
            TopicForwardingManager.this.member.setIsForwardingUp(true);
            var12 = false;
         } finally {
            if (var12) {
               synchronized(TopicForwardingManager.this.refreshMembersLock) {
                  TopicForwardingManager.this.clearState(1);
                  if ((TopicForwardingManager.this.state & 2) != 0) {
                     TopicForwardingManager.this.clearState(2);
                     if ((TopicForwardingManager.this.state & 8) != 0) {
                        return;
                     }

                     TopicForwardingManager.this.setState(4);
                     TopicForwardingManager.this.timerManagerRefreshMembers.schedule(TopicForwardingManager.this.new RefreshMembersThread(), 10000L);
                     if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
                        JMSDebug.JMSDistTopic.debug(this + ": TopicForwardingManager.RefreshMembersThread() state:" + TopicForwardingManager.this.getStateString() + ", scheduling refreshMembers");
                     }
                  }

               }
            }
         }

         synchronized(TopicForwardingManager.this.refreshMembersLock) {
            TopicForwardingManager.this.clearState(1);
            if ((TopicForwardingManager.this.state & 2) != 0) {
               TopicForwardingManager.this.clearState(2);
               if ((TopicForwardingManager.this.state & 8) != 0) {
                  return;
               }

               TopicForwardingManager.this.setState(4);
               TopicForwardingManager.this.timerManagerRefreshMembers.schedule(TopicForwardingManager.this.new RefreshMembersThread(), 10000L);
               if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
                  JMSDebug.JMSDistTopic.debug(this + ": TopicForwardingManager.RefreshMembersThread() state:" + TopicForwardingManager.this.getStateString() + ", scheduling refreshMembers");
               }
            }

         }
      }
   }

   public static final class Counter {
      private int value;

      public Counter(int value) {
         this.value = value;
      }

      public int increment() {
         return ++this.value;
      }

      public int decrement() {
         return --this.value;
      }
   }
}
