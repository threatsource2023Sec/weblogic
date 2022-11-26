package weblogic.jms.backend;

import java.util.Iterator;
import javax.jms.JMSException;
import weblogic.jms.JMSService;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSID;
import weblogic.jms.dd.DDHandler;
import weblogic.jms.dd.DDMember;
import weblogic.jms.dd.DDStatusListener;
import weblogic.timers.StopTimerListener;
import weblogic.timers.Timer;

public final class QueueForwardingManager implements StopTimerListener, DDStatusListener, ForwardingStatusListener {
   private long forwardingDelay;
   private Timer queueForwardingTimer;
   private DDMember forwardTo = null;
   private BEForwardingConsumer forwardingConsumer;
   private boolean forwardingTimerExpired = false;
   DDHandler ddHandler;
   DDMember member;

   public QueueForwardingManager(DDHandler ddHandler, DDMember member) {
      this.ddHandler = ddHandler;
      this.member = member;
      member.setIsForwardingUp(true);
      ddHandler.addStatusListener(this, 7);
   }

   public synchronized void statusChange() {
      boolean doForward = this.member.isDestinationUp() && !this.member.isConsumptionPaused() && !this.member.hasConsumers() && this.ddHandler.getForwardDelay() >= 0;
      if (doForward) {
         if (this.forwardTo != null) {
            this.forwardTo = this.ddHandler.findMemberByName(this.forwardTo.getName());
         }

         if (this.forwardTo == null) {
            this.start();
         } else if (!this.forwardTo.hasConsumers() && !this.pickAndForward()) {
            this.start();
         }
      } else if (!doForward && this.forwardingConsumer != null) {
         this.stop();
      }

   }

   private synchronized boolean pickForwardTo() {
      this.forwardTo = null;
      Iterator iter = this.ddHandler.memberCloneIterator();

      while(iter.hasNext()) {
         DDMember tryForwardTo = (DDMember)iter.next();
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("QueueForwardingManager.pickForwardTo() " + tryForwardTo.getName() + " hasConsumer " + tryForwardTo.hasConsumers() + " is up? " + tryForwardTo.isUp());
         }

         if (!tryForwardTo.getName().equals(this.member.getName()) && tryForwardTo.hasConsumers() && !tryForwardTo.isConsumptionPaused() && tryForwardTo.isUp()) {
            if (tryForwardTo.isLocal() && tryForwardTo.isPersistent()) {
               this.forwardTo = tryForwardTo;
               break;
            }

            if (this.forwardTo == null) {
               this.forwardTo = tryForwardTo;
            } else if (!this.forwardTo.isPersistent() && (tryForwardTo.isPersistent() || tryForwardTo.isLocal())) {
               this.forwardTo = tryForwardTo;
            }
         }
      }

      return this.forwardTo != null;
   }

   private synchronized void start() {
      if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
         JMSDebug.JMSDistTopic.debug("Start queue forwarding for " + this.member.getName() + ", forwardingConsumer: " + this.forwardingConsumer + ", ddHandler: " + this.ddHandler + ", resetDeliveryCount: " + this.ddHandler.getResetDeliveryCountOnForward() + ", queueForwardDelay: " + this.ddHandler.getForwardDelay());
      }

      if (this.forwardingConsumer == null) {
         JMSID id = JMSService.getNextId();
         this.forwardingConsumer = new BEForwardingConsumer(this.member.getDestination().getBackEnd(), id.toString(), id, ((BEQueueImpl)this.member.getDestination()).getKernelQueue(), this.ddHandler.getResetDeliveryCountOnForward());
         this.forwardingConsumer.setStatusListener(this);
      }

      this.forwardingDelay = (long)this.ddHandler.getForwardDelay() * 1000L;
      if (this.forwardingTimerExpired) {
         this.pickAndForward();
      } else if (this.forwardingDelay == 0L) {
         this.forwardingTimerExpired = true;
         this.pickAndForward();
      } else {
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("Started dist queue forwarding timer for " + this.member.getName() + " to " + this.forwardingDelay);
         }

         this.queueForwardingTimer = this.member.getDestination().getBackEnd().getTimerManager().schedule(this, this.forwardingDelay, this.forwardingDelay);
      }
   }

   private synchronized boolean pickAndForward() {
      if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
         JMSDebug.JMSDistTopic.debug("Trying to find candidate for forwarding for " + this.member.getName());
      }

      if (this.pickForwardTo()) {
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("Forwarding to " + this.forwardTo.getName() + " from " + this.member.getName());
         }

         try {
            this.forwardingConsumer.start(this.forwardTo.getDDImpl(), this.forwardTo.getName(), this.forwardTo.getRemoteSecurityMode());
            return true;
         } catch (JMSException var2) {
            this.forwardingFailed(this.forwardingConsumer);
            this.forwardTo = null;
            return false;
         }
      } else {
         if (this.forwardingConsumer != null && this.forwardingConsumer.isStarted()) {
            if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
               JMSDebug.JMSDistTopic.debug("Stop Forwarding from " + this.member.getName());
            }

            this.forwardingConsumer.stop();
         }

         return false;
      }
   }

   synchronized void stop() {
      this.forwardingTimerExpired = false;
      if (this.forwardTo != null) {
         this.forwardTo = null;
      }

      if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
         JMSDebug.JMSDistTopic.debug("Stopping dist queue forwarding for " + this.member.getName());
      }

      if (this.queueForwardingTimer != null) {
         this.queueForwardingTimer.cancel();
         this.queueForwardingTimer = null;
      }

      if (this.forwardingConsumer != null) {
         this.forwardingConsumer.stop();
         this.forwardingConsumer = null;
      }

   }

   public synchronized void forwardingFailed(BEForwardingConsumer consumer) {
      if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
         JMSDebug.JMSDistTopic.debug("QueueForwardingManager.forwardingFailed() reschedule forwarder " + consumer);
      }

      if (this.queueForwardingTimer != null) {
         this.queueForwardingTimer.cancel();
      }

      this.queueForwardingTimer = this.member.getDestination().getBackEnd().getTimerManager().schedule(this, this.forwardingDelay, this.forwardingDelay);
   }

   public synchronized void timerExpired(Timer timer) {
      this.forwardingTimerExpired = true;
      if (timer != null) {
         timer.cancel();
      }

      if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
         JMSDebug.JMSDistTopic.debug("Queue forwarding timer expired for " + this.member.getName());
      }

      if (this.forwardingConsumer == null) {
         if (JMSDebug.JMSDistTopic.isDebugEnabled() && this.member != null) {
            JMSDebug.JMSDistTopic.debug("Forwarding consumer null in timer handler for " + this.member.getName());
         }

      } else {
         this.pickAndForward();
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("No forwarding candidate found for " + this.member.getName() + ", an event will need to occur");
         }

      }
   }

   public void timerStopped(Timer timer) {
      this.stop();
   }

   public void statusChangeNotification(DDHandler ddHandler, int events) {
      if (ddHandler.findMemberByName(this.member.getName()) == null) {
         ddHandler.removeStatusListener(this);
         this.stop();
      } else {
         this.statusChange();
      }

   }

   public void shutdown() {
      if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
         JMSDebug.JMSDistTopic.debug("Shutdown queue forwarder " + this.ddHandler.getName() + ": this is " + this);
      }

      this.ddHandler.removeStatusListener(this);
      this.stop();
   }

   public String toString() {
      return "QueueForwardingManager: " + this.member.getName() + " within " + this.ddHandler.getName() + ", hash: " + this.hashCode();
   }
}
