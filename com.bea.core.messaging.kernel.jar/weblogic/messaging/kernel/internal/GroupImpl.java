package weblogic.messaging.kernel.internal;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.messaging.kernel.Group;
import weblogic.messaging.kernel.GroupOwner;
import weblogic.messaging.kernel.Queue;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.utils.collections.EmbeddedList;

public final class GroupImpl implements Group, NakedTimerListener {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugMessagingKernel");
   private final String name;
   private final QueueImpl queue;
   private final TimerManager limitedTimerManager;
   private final EmbeddedList list = new EmbeddedList();
   private Object owner;
   private long idleTime = Long.MAX_VALUE;
   private long timeout;
   private Timer timer;
   private int received;

   GroupImpl(String name, QueueImpl queue) {
      this.name = name;
      this.queue = queue;
      this.timeout = 30000L;
      this.limitedTimerManager = ((KernelImpl)queue.getKernel()).getLimitedTimerManager();
   }

   public Queue getQueue() {
      return this.queue;
   }

   public String getName() {
      return this.name;
   }

   public int size() {
      return this.list.size();
   }

   public void timerExpired(Timer timer) {
      long diff = System.currentTimeMillis();
      synchronized(this.queue) {
         if (this.idleTime != Long.MAX_VALUE) {
            if (timer != null) {
               timer.cancel();
            }

            if ((diff -= this.idleTime) < this.timeout) {
               this.timer = this.limitedTimerManager.schedule(this, this.timeout - diff);
            } else {
               this.queue.deleteGroup(this);
            }
         }
      }
   }

   void addGroupRef(MessageReference msgRef) {
      GroupReference ref = new GroupReference(msgRef, this);
      msgRef.setGroupRef(ref);
   }

   boolean contains(MessageReference msgRef) {
      GroupReference ref = msgRef.getGroupRef();
      return ref != null && this.list.contains(ref);
   }

   void add(MessageReference msgRef) {
      GroupReference ref = msgRef.getGroupRef();
      if (ref == null) {
         ref = new GroupReference(msgRef, this);
         msgRef.setGroupRef(ref);
      }

      assert ref.getGroup() == this;

      this.list.add(ref);
      GroupReference previous = (GroupReference)ref.getPrev();
      if (previous == null) {
         this.idleTime = Long.MAX_VALUE;
      } else if (!previous.getMessageReference().isReceived()) {
         msgRef.setState(8);
      }

   }

   boolean allocate(MessageReference element, Object owner) {
      assert element.getGroupRef() != null;

      assert element.getGroupRef().getGroup() == this;

      assert owner != null;

      if (this.owner == null) {
         assert this.received == 0;

         this.owner = owner;
         if (logger.isDebugEnabled()) {
            logger.debug("group allocate first to " + owner);
         }
      } else {
         if (this.owner != owner) {
            return false;
         }

         if (this.owner instanceof GroupOwner && ((GroupOwner)this.owner).exposeOnlyOneMessage() && this.received > 0) {
            return false;
         }

         if (logger.isDebugEnabled()) {
            if (this.owner instanceof GroupOwner) {
               logger.debug("group allocated with expseOnlyOneMessage:" + this.received + ", size:" + this.list.size() + ", exposeOnlyOneMessage " + this.owner);
            } else {
               logger.debug("group allocated, received " + (this.received + 1) + " " + owner);
            }
         }
      }

      GroupReference ref = element.getGroupRef();
      if ((ref = (GroupReference)ref.getNext()) != null) {
         ref.getMessageReference().clearState(8);
      }

      ++this.received;

      assert this.received > 0;

      return true;
   }

   MessageReference free(MessageReference element, int clearState) {
      assert Thread.holdsLock(element.getQueue());

      assert element.getGroupRef() != null;

      assert element.getGroupRef().getGroup() == this;

      assert element.isReceived();

      element.setState(8);
      if (clearState != 0) {
         element.clearState(clearState);
      }

      MessageReference next = this.next(element);
      if (next != null) {
         next.setState(8);
      }

      assert this.received > 0;

      --this.received;
      if (this.received == 0) {
         this.ejectRedeliverMessages();
      }

      if (this.received > 0) {
         this.owner = this;
         if (logger.isDebugEnabled()) {
            logger.debug("group free/marked unavailable pending rollbacks " + this.name + (next == null ? " (next ORDERED), pending " : " pending ") + this.received);
         }

         return null;
      } else {
         assert this.received == 0;

         this.owner = null;
         if (this.list.isEmpty()) {
            return null;
         } else {
            MessageReference returnElement = this.first();
            if (logger.isDebugEnabled()) {
               logger.debug("group free now available " + this.name + " (first ORDERED cleared)");
            }

            assert returnElement.isOrdered() && !returnElement.isReceived();

            returnElement.clearState(8);
            return returnElement;
         }
      }
   }

   private boolean ejectRedeliverMessages() {
      if (this.list.isEmpty()) {
         return false;
      } else {
         do {
            MessageReference element = this.first();
            if (!element.isRedirectable()) {
               return false;
            }

            if (element.isOrdered()) {
               element.clearState(8);
            }

            this.list.remove(element.getGroupRef());
            element.setGroupRef((GroupReference)null);
            element.getQueue().getRedirector().scheduleRedirection(element);
         } while(!this.list.isEmpty());

         this.idleTime = System.currentTimeMillis();
         if (this.timer == null) {
            this.timer = this.limitedTimerManager.schedule(this, this.timeout);
         }

         return true;
      }
   }

   MessageReference next(MessageReference element) {
      assert element.getGroupRef() != null;

      if (element.getGroupRef().getGroup() != this) {
         return null;
      } else {
         GroupReference nextRef = (GroupReference)((GroupReference)element.getGroupRef().getNext());
         return nextRef == null ? null : nextRef.getMessageReference();
      }
   }

   MessageReference prev(MessageReference element) {
      assert element.getGroupRef() != null;

      if (element.getGroupRef().getGroup() != this) {
         return null;
      } else {
         GroupReference prevRef = (GroupReference)((GroupReference)element.getGroupRef().getPrev());
         return prevRef == null ? null : prevRef.getMessageReference();
      }
   }

   private MessageReference first() {
      return ((GroupReference)this.list.get(0)).getMessageReference();
   }

   MessageReference remove(MessageReference element) {
      GroupReference ref = element.getGroupRef();

      assert ref.getGroup() == this;

      if (element.isOrdered()) {
         element.clearState(8);
      }

      element.setGroupRef((GroupReference)null);
      boolean clearedReceived;
      if (element.isReceived()) {
         assert this.received > 0;

         clearedReceived = --this.received == 0;
      } else {
         clearedReceived = false;
      }

      MessageReference previous;
      if (ref.getPrev() != null) {
         previous = ((GroupReference)ref.getPrev()).getMessageReference();
      } else {
         previous = null;
      }

      MessageReference next;
      if (ref.getNext() != null) {
         next = ((GroupReference)ref.getNext()).getMessageReference();
      } else {
         next = null;
      }

      this.list.remove(ref);
      boolean ejectAlreadySetTimer;
      if (this.received == 0) {
         ejectAlreadySetTimer = this.ejectRedeliverMessages();
      } else {
         ejectAlreadySetTimer = false;
      }

      if (this.list.isEmpty()) {
         assert this.received == 0;

         this.owner = null;
         if (!ejectAlreadySetTimer) {
            this.idleTime = System.currentTimeMillis();
            if (this.timer == null) {
               this.timer = this.limitedTimerManager.schedule(this, this.timeout);
            }
         }

         return null;
      } else {
         if (previous != null && previous.getGroup() == null) {
            previous = null;
         }

         if (next != null && next.getGroup() == null) {
            next = null;
         }

         if (previous == null && next == null) {
            next = this.first();
         }

         if (clearedReceived || previous == null && !next.isReceived()) {
            assert this.received == 0;

            this.owner = null;
            if (clearedReceived) {
               next = this.first();
            }
         } else {
            if (next == null || !next.isOrdered()) {
               return null;
            }

            if (previous != null && (!previous.isReceived() || previous.isOrdered())) {
               return null;
            }
         }

         if (next.isOrdered()) {
            next.clearState(8);
         }

         return next;
      }
   }

   public String toString() {
      return "group " + this.name + " " + super.toString();
   }
}
