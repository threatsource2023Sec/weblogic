package weblogic.jms.common;

import weblogic.jms.JMSService;
import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.jms.dispatcher.Invocable;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.messaging.ID;
import weblogic.messaging.dispatcher.InvocableMonitor;
import weblogic.messaging.dispatcher.Request;

public final class Sequencer implements Invocable {
   private final PushTarget pushTarget;
   private final JMSDispatcher dispatcher;
   private final JMSID sequencerId;
   private final DispatcherPartition4rmic dispatcherPartition4rmic;
   private final InvocableMonitor invocableMonitor;
   private long expectedSequenceNumber = 1L;
   private boolean running;
   private JMSPushRequest firstRequest;
   private JMSPushRequest lastRequest;

   public Sequencer(PushTarget pushTarget, JMSDispatcher dispatcher, DispatcherPartition4rmic dispatcherPartition4rmic, InvocableMonitor invocableMonitor) {
      this.pushTarget = pushTarget;
      this.dispatcher = dispatcher;
      this.sequencerId = JMSService.getNextId();
      this.dispatcherPartition4rmic = dispatcherPartition4rmic;
      this.invocableMonitor = invocableMonitor;
   }

   public Sequencer(PushTarget pushTarget) {
      this.pushTarget = pushTarget;
      this.dispatcher = null;
      this.sequencerId = null;
      this.dispatcherPartition4rmic = null;
      this.invocableMonitor = null;
   }

   public JMSDispatcher getDispatcher() {
      return this.dispatcher;
   }

   public void changeExpectedSequenceNumberCanHaveRemainder(long sequenceNumber) {
      synchronized(this) {
         if (this.expectedSequenceNumber >= sequenceNumber) {
            return;
         }

         this.expectedSequenceNumber = sequenceNumber;
         this.expungeBeforeSequenceNumber();
         if (this.running) {
            return;
         }

         this.running = true;
      }

      this.deliverMessages();
   }

   private void expungeBeforeSequenceNumber() {
      assert Thread.holdsLock(this);

      while(this.firstRequest != null) {
         if (this.firstRequest.getBackEndSequenceNumber() >= this.expectedSequenceNumber) {
            return;
         }

         JMSPushRequest oldFirst = this.firstRequest;
         this.firstRequest = (JMSPushRequest)oldFirst.getNext();
         oldFirst.setNext((Request)null);
      }

      if (this.firstRequest == null) {
         this.lastRequest = null;
      }

   }

   public final void pushMessage(JMSPushRequest pushRequest) {
      synchronized(this) {
         JMSPushRequest request = pushRequest;

         while(request != null) {
            JMSPushEntry extraEntry = request.getFirstPushEntry().getNext();

            JMSPushEntry first;
            JMSPushRequest req;
            while(extraEntry != null) {
               first = extraEntry;
               extraEntry = extraEntry.getNext();
               first.setNext((JMSPushEntry)null);
               req = new JMSPushRequest(13, this.pushTarget.getJMSID(), request.getMessage(), first);
               this.sequenceRequest(req);
            }

            first = request.getFirstPushEntry();
            first.setNext((JMSPushEntry)null);
            request.setLastPushEntry(first);
            request.setInvocableId(this.pushTarget.getJMSID());
            req = request;
            request = (JMSPushRequest)request.getNext();
            req.setNext((Request)null);
            this.sequenceRequest(req);
         }

         if (this.running) {
            return;
         }

         this.running = true;
      }

      this.deliverMessages();
   }

   private void sequenceRequest(JMSPushRequest request) {
      assert Thread.holdsLock(this);

      assert request.getNext() == null;

      assert request.getFirstPushEntry().getNext() == null;

      long seqNum = request.getBackEndSequenceNumber();
      if (seqNum >= this.expectedSequenceNumber) {
         if (this.firstRequest == null) {
            this.firstRequest = this.lastRequest = request;
         } else if (this.lastRequest != null && seqNum > this.lastRequest.getBackEndSequenceNumber()) {
            this.lastRequest.setNext(request);
            this.lastRequest = request;
         } else {
            JMSPushRequest cur = this.firstRequest;

            JMSPushRequest prev;
            for(prev = null; cur != null && seqNum >= cur.getBackEndSequenceNumber(); cur = (JMSPushRequest)cur.getNext()) {
               prev = cur;
            }

            if (cur != null) {
               if (cur.getBackEndSequenceNumber() == seqNum) {
                  return;
               }

               request.setNext(cur);
               if (prev != null) {
                  prev.setNext(request);
               } else {
                  this.firstRequest = request;
               }
            } else {
               prev.setNext(request);
               this.lastRequest = request;
            }

         }
      }
   }

   private void deliverMessages() {
      JMSPushRequest firstToPush = null;

      while(true) {
         synchronized(this) {
            firstToPush = this.firstRequest;
            JMSPushRequest prev = null;

            while(true) {
               if (this.firstRequest == null || this.firstRequest.getBackEndSequenceNumber() != this.expectedSequenceNumber) {
                  if (prev == null) {
                     this.running = false;
                     return;
                  }

                  if (this.firstRequest == null) {
                     this.lastRequest = null;
                  } else {
                     prev.setNext((Request)null);
                  }
                  break;
               }

               this.firstRequest.getFirstPushEntry().setDispatcher(this.dispatcher);
               ++this.expectedSequenceNumber;
               prev = this.firstRequest;
               this.firstRequest = (JMSPushRequest)this.firstRequest.getNext();
            }
         }

         this.pushTarget.pushMessage(firstToPush);
      }
   }

   public JMSID getJMSID() {
      return this.sequencerId;
   }

   public ID getId() {
      return this.getJMSID();
   }

   public DispatcherPartition4rmic getDispatcherPartition4rmic() {
      return this.dispatcherPartition4rmic;
   }

   public InvocableMonitor getInvocableMonitor() {
      return this.invocableMonitor;
   }

   public int invoke(Request request) throws javax.jms.JMSException {
      switch (request.getMethodId()) {
         case 15629:
            this.pushMessage((JMSPushRequest)request);
            return Integer.MAX_VALUE;
         default:
            throw new JMSException("No such method " + request.getMethodId());
      }
   }

   public synchronized String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("[ ");
      if (this.firstRequest != null) {
         buf.append(" first=");
         buf.append(this.firstRequest.getBackEndSequenceNumber());
      }

      if (this.lastRequest != null) {
         buf.append(" last=");
         buf.append(this.lastRequest.getBackEndSequenceNumber());
      }

      int pendingCount = 0;

      for(JMSPushRequest r = this.firstRequest; r != null; r = (JMSPushRequest)r.getNext()) {
         buf.append(' ');
         buf.append(r.getBackEndSequenceNumber());
         buf.append(' ');
         ++pendingCount;
      }

      buf.append(" pending=");
      buf.append(pendingCount);
      buf.append(" expected=");
      buf.append(this.expectedSequenceNumber);
      buf.append(" ]");
      return buf.toString();
   }
}
